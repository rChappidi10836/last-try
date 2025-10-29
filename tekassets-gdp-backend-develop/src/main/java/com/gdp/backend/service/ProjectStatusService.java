package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.ProjectStatusDto;
import com.gdp.backend.dto.ProjectStatusResponseDto;
import com.gdp.backend.dto.SearchFilterDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.repository.ProjectsRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gdp.backend.common.Utils.getFullName;

/**
 * This Service class used for getting Project Status details.
 * @author gdp
 *
 */
@Service
public class ProjectStatusService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectsRepository projectsRepository;

    /**
     * This method would be use for getting the project status default value.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @return this method would be return project Status Response data.
     * @throws ActionFailureException exception would be throws if project status data not found.
     */
    public ProjectStatusResponseDto getProjectStatusDefaultValue(int pageNumber, int pageSize , String opcoId ) throws ActionFailureException {
        ProjectStatusResponseDto projectStatusResponseDto = new ProjectStatusResponseDto();
        StringBuilder  dQuery = new StringBuilder(Queries.PROJECT_STATUS_DEFAULT_QUERY);
        dQuery.append(" AND C.OpcoId = "+opcoId);
        System.out.println(dQuery);
        Query query = entityManager.createNativeQuery(dQuery.toString());
        if (!(pageNumber == 0 && pageSize == 0)) {
            projectStatusResponseDto.setTotalNumberOfElements(projectsRepository.countDefaultProjectList(Integer.parseInt(opcoId)));
            query.setFirstResult((pageNumber) * pageSize);
            query.setMaxResults(pageSize);
        }
        List<Object[]> projectList;
        try {
            projectList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }

        Set<ProjectStatusDto> projectStatusDtoSet = new HashSet<>(projectList.size());

        for (Object[] row : projectList) {
            String cslName = getCslNameFromQuery(row);
            projectStatusDtoSet.add(new ProjectStatusDto(row, cslName, pageNumber, pageSize));
        }
        projectStatusResponseDto.setProjectStatusDtoList(projectStatusDtoSet);

        return projectStatusResponseDto;
    }

    /**
     * This Service would be use for getting the Project status details using search filter.
     * @param searchFilterDto is the search filter data.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @return this method would be return projectStatusResponseDto.
     * @throws ActionFailureException exception would be throws if project status details not found.
     */
    public ProjectStatusResponseDto getProjectStatusOnSearch(SearchFilterDto searchFilterDto
            , int pageNumber, int pageSize,String opcoId) throws ActionFailureException {
        System.out.println("ACTIVE STATUS"+searchFilterDto.toString());
        int active = searchFilterDto.getActive();
        Integer[] clientId = searchFilterDto.getClientId();
        Integer[] deliveryOrganizationTypeId = searchFilterDto.getDeliveryOrganizationTypeId();
        Integer[] cslId = searchFilterDto.getCslId();
        Integer[] gdmId = searchFilterDto.getGdmId();
        Integer[] gddId = searchFilterDto.getGddId();
        Integer[] programManagerId = searchFilterDto.getProgramManagerId();
        int gdpId = searchFilterDto.getProjectId();
        int psId = searchFilterDto.getPsId();
        String[] lodId= searchFilterDto.getLodId();
        String[]  deliveryModelId = searchFilterDto.getDmId();
        Integer[] businessUnitId = searchFilterDto.getBusinessUnitId();
        String orderByAttribute = searchFilterDto.getOrderByAttribute();
        String sortDirection = searchFilterDto.getSortDirection();
        ProjectStatusResponseDto projectStatusResponseDto = new ProjectStatusResponseDto();
        StringBuilder query = new StringBuilder();
        query.append(Queries.FILTERED_PROJECT_STATUS+Constants.AND_CLAUS_OPCOID+opcoId);
        if (active != Constants.ALL_PROJECTS) {
            query.append(Constants.AND_CLAUSE + Constants.ENGAGEMENT_STATUS).append(active);
        }
        if (clientId[0] != Constants.DEFAULT_ID) {
            query.append(Constants.AND_CLAUSE + Constants.CLIENT_ID).append(Arrays.toString(clientId).replaceAll("[\\[.\\].\\s+]", "")).append(")");
        }
        if (deliveryOrganizationTypeId[0] != Constants.DEFAULT_ID) {
            query.append(Constants.AND_CLAUSE + Constants.DELIVERY_ORGANIZATION_ID).append(Arrays.toString(deliveryOrganizationTypeId).replaceAll("[\\[.\\].\\s+]", "")).append(")");
        }
        if (cslId[0] != Constants.DEFAULT_ID) {
            query.append(" AND P.Id IN (SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND (RR.ResourceRoleName='Engagement Manager' OR RR.ResourceRoleName='Project Coordinator') JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN ( ").append(Arrays.toString(cslId).replaceAll("[\\[.\\].\\s+]", "")).append(")) ");
        }
        if (gdpId != Constants.DEFAULT_ID) {
            query.append(Constants.AND_CLAUSE + Constants.GDP_ID).append(gdpId);
        }
        if (gdmId[0] != Constants.DEFAULT_ID) {
            query.append(" AND P.Id IN (SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN (").append(Arrays.toString(gdmId).replaceAll("[\\[.\\].\\s+]", "")).append(")) ");
        }
        if (gddId[0] != Constants.DEFAULT_ID) {
            query.append(" AND P.Id IN (SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN (").append(Arrays.toString(gddId).replaceAll("[\\[.\\].\\s+]", "")).append(")) ");
        }
        if (programManagerId[0] != Constants.DEFAULT_ID) {
            query.append(" AND P.Id IN (SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Program Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN (").append(Arrays.toString(programManagerId).replaceAll("[\\[.\\].\\s+]", "")).append(")) ");
        }

        if(!lodId[0].equals( "-1")){
            query.append(Constants.AND_CLAUSE + Constants.LOCATION_ID).append(Arrays.toString(lodId).replaceAll("[\\[.\\].\\s+]", "")).append("))");;
        }
        if(businessUnitId[0]!=Constants.DEFAULT_ID){
            query.append(Constants.AND_CLAUSE + Constants.BU_ID).append(Arrays.toString(businessUnitId).replaceAll("[\\[.\\].\\s+]", "")).append(")");
        }
        if(psId != Constants.DEFAULT_ID){
            query.append(Constants.AND_CLAUSE + Constants.GDP_ID).append(psId);
        }
        if (!deliveryModelId[0].equals("-1")) {
            query.append(Constants.AND_CLAUSE + Constants.DELIVERY_MODEL_ID).append(Arrays.toString(deliveryModelId).replaceAll("[\\[.\\].\\s+]", "")).append(")");
        }


        String countQueryString = "SELECT COUNT(*) "+query.substring(query.indexOf("From"));

        switch (orderByAttribute) {
            case Constants.CLIENT_NAME_FRONTEND_REFERENCE:
                query.append(Constants.ORDER_BY_CLAUSE + Constants.CLIENT_NAME);
                break;
            case Constants.STATUS_FRONTEND_REFERENCE:
                query.append(Constants.ORDER_BY_CLAUSE + Constants.PROJECT_STATUS);
                break;
            case Constants.CSL_NAME_FRONTEND_REFERENCE:
                query.append(Constants.ORDER_BY_CLAUSE + Constants.RESOURCE_CSL_NAME);
                break;
            case Constants.STATUS_DATE_FRONTEND_REFERENCE:
                query.append(Constants.ORDER_BY_CLAUSE + Constants.WEEK_ENDING_DATE);
                break;
            default:
                query.append(Constants.ORDER_BY_CLAUSE + Constants.PROJECT_NAME);
        }

        if (!StringUtils.isEmpty(sortDirection)) {
            query.append(sortDirection.toUpperCase());
        }
        Query finalQuery = entityManager.createNativeQuery(query.toString());
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        if (!(pageNumber == 0 && pageSize == 0)) {
            projectStatusResponseDto.setTotalNumberOfElements(Long.valueOf((Integer) countQuery.getSingleResult()));
            finalQuery.setFirstResult((pageNumber) * pageSize);
            finalQuery.setMaxResults(pageSize);
        }
        List<Object[]> filteredProjectList;
        try {
            filteredProjectList = finalQuery.getResultList();

        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }

        if (CollectionUtils.isEmpty(filteredProjectList)) {
            throw new ActionFailureException(Constants.RECORD_NOT_FOUND);
        }

        Set<ProjectStatusDto> projectStatusDtoSet = new HashSet<>(filteredProjectList.size());

        for (Object[] row : filteredProjectList) {
            String cslName = getCslNameFromQuery(row);
            ProjectStatusDto dto =new ProjectStatusDto(row, cslName,  pageNumber, pageSize);
            dto.setGdd((String) row[19]);
            projectStatusDtoSet.add(dto);
        }
        projectStatusResponseDto.setProjectStatusDtoList(projectStatusDtoSet);
        return projectStatusResponseDto;
    }

    /**
     * This method would be use for getting the CslName from Query.
     * @param columns is the CslName.
     * @return this method would be return columns of CslName.
     */
    public String getCslNameFromQuery(Object[] columns) {
        return (String) columns[0];
    }

}

