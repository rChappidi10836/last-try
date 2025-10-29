package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.FilterDto;
import com.gdp.backend.dto.LocationDTO;
import com.gdp.backend.dto.ProjectFilterResponseDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.gdp.backend.common.Utils.getFullName;

/**
 * This Service class used for getting Project Filter details.
 * @author gdp
 *
 */
@Service
public class ProjectFilterService {

    @Autowired
    ProjectStatusService projectStatusService;

    @Autowired
    EntityManager entityManager;

    /**
     * This method would be use for getting the Filter Project Data based on Active or de-Active the Project.
     * @param active is indicate how many Project present in active state.
     * @param clientId is the id of project Client.
     * @param deliveryOrganizationTypeId is the field of delivery Organization TypeId.
     * @param cslId is the cslId.
     * @param gdmId is the gdmId.
     * @param businessId is the business UnitId.
     * @param projectId is the id of existing Project.
     * @return this method would be return Filter Project Data.
     */




    private String getProjectFilter(int active, String clientId, String deliveryOrganizationTypeId, String cslId, String gdmId, String gddId,String programManagerId, String businessId, int projectId, int psId,String lodId, String deliverymodelId){
        StringBuilder queryBuilder = new StringBuilder();
        if (active != 2) {
            queryBuilder.append(" P.active = ").append(active);
        } else {
            queryBuilder.append(" (P.active = 1 OR P.active = 0) ");
        }
        if (!clientId.contains("-1")) {
            queryBuilder.append(" AND P.ClientId  IN (").append(clientId).append(")");
        }
        if (!deliveryOrganizationTypeId.contains("-1")) {
            queryBuilder.append(" AND P.DeliveryOrganizationTypeID IN (").append(deliveryOrganizationTypeId).append(") ");
        }
        if (!cslId.contains("-1")) {
            queryBuilder.append(" AND P.Id IN(SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND (RR.ResourceRoleName='Engagement Manager' OR RR.ResourceRoleName='Project Coordinator') JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN ( ").append(cslId).append("))");
        }
        if (!gdmId.contains("-1")) {
            queryBuilder.append(" AND P.Id IN(SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN ( ").append(gdmId).append("))");
        }
        if (!gddId.contains("-1")) {
            queryBuilder.append(" AND P.Id IN(SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN ( ").append(gddId).append("))");
        }
        if (!programManagerId.contains("-1")) {
            queryBuilder.append(" AND P.Id IN(SELECT PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Program Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE R.Id IN ( ").append(programManagerId).append("))");
        }
        if(!businessId.contains("-1")){
            queryBuilder.append(" AND P.Id IN (SELECT Id FROM Project WHERE BusinessUnitId IN ( ").append(businessId).append("))");
        }
        if(projectId>0){
            queryBuilder.append(" AND P.Id = ").append(projectId);
        }
        if(psId>0){
            queryBuilder.append(" AND P.Id = ").append(psId);
        }
        if(!lodId.contains("-1")){
            queryBuilder.append(" AND P.Id IN (SELECT M.ProjectId FROM ProjectLocationMapper M WHERE M.LocationId IN( ").append(lodId).append("))");
        }
        if(!deliverymodelId.contains("-1")){
            queryBuilder.append(" AND P.DeliveryModelId IN (").append(deliverymodelId).append(") ");
        }
        return queryBuilder.toString();




    }

    /**
     * This Service would be use for populating the list of project data when we try to filter the project data.
     * @param active is indicate how many Project present in active state.
     * @param clientId is the id of project Client.
     * @param deliveryOrganizationTypeId s the field of delivery Organization TypeId.
     * @param cslId is the cslId of Project.
     * @param gdmId is the gdmId of Project.
     * @param businessUnitId is the UnitId of existing Project.
     * @param projectId is the id of existing Project.
     * @return this method would be return Filter Project resource Data.
     * @throws ActionFailureException exception would be throws if project data not found.
     */
    public ProjectFilterResponseDto getAllFiltersValue(int active, String clientId, String deliveryOrganizationTypeId, String cslId, String gdmId, String gddId,String programManagerId, String businessUnitId, int projectId, int psId , String opcoId,String lodId, String deliverymodelId) throws ActionFailureException {
        ProjectFilterResponseDto allFilterValues = new ProjectFilterResponseDto();
        List<FilterDto> account;
        try {
//
            StringBuilder account_queryBuilder = new StringBuilder(Queries.ACCOUNT_DROPDOWN);
          account_queryBuilder.append(Constants.OPCO_STATUS + opcoId +Constants.AND_CLAUSE);

//          account_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId, businessUnitId, projectId, psId)+ ") ORDER BY C.ClientName ");
//            Query account_query = entityManager.createNativeQuery(String.valueOf(account_queryBuilder));
            String projectFilterCondition = getProjectFilter(active, "-1", deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId, businessUnitId, projectId, psId,lodId, deliverymodelId);


            account_queryBuilder.append(projectFilterCondition + "\nORDER BY C.ClientName");

            account = entityManager.createNativeQuery(String.valueOf(account_queryBuilder), "ClientMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setAccountNameList(account);


//        List<FilterDto> account;
//        try {
//            String queryBuilder = "SELECT TOP(10) C.Id, C.ClientName\n" +
//                    "FROM Client C\n" +
//                    "INNER JOIN Project P ON C.Id = P.ClientId\n" +
//                    "WHERE P.active = 1 AND C.OpcoId = 1\n" +
//                    "ORDER BY C.ClientName";
//
//            String queryBuilder=Queries.ACCOUNT_DROPDOWN + getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId,programManagerId, businessUnitId, projectId, psId) + ") ORDER BY  C.ClientName";
//           // queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
//            account = entityManager.createNativeQuery(queryBuilder, "ClientMapping").getResultList();
//        List<FilterDto> account;
//        try {
//            StringBuilder account_queryBuilder = new StringBuilder(Queries.ACCOUNT_DROPDOWN);
//            account_queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
//            account_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId,programManagerId, businessUnitId, projectId, psId) + ") ORDER BY  C.ClientName ");
//            Query account_query = entityManager.createNativeQuery(String.valueOf(account_queryBuilder));
//        } catch (Exception ex) {
//            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
//        }
//
//        allFilterValues.setAccountNameList(account);
//        List<FilterDto> account = new ArrayList<>(); // Initialize as an empty list
//        try {
//            StringBuilder account_queryBuilder = new StringBuilder(Queries.ACCOUNT_DROPDOWN);
//           // account_queryBuilder.append(Constants.AND_CLAUS_OPCOID + opcoId + Constants.WHERE_CLAUSE);
//            account_queryBuilder.append(String.format(account_queryBuilder.toString(), opcoId));
//            account_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId, businessUnitId, projectId, psId) + ") ORDER BY  C.ClientName ");
//            Query account_query = entityManager.createNativeQuery(String.valueOf(account_queryBuilder));
//
//            // Populate the 'account' list based on the query result (assuming 'FilterDto' is the correct type)
//            account = account_query.getResultList();
//        } catch (Exception ex) {
//            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
//        }
////
//        allFilterValues.setAccountNameList(account);

        List<FilterDto> practiceEngagement;
        try {
            String queryBuilder = Queries.PRACTICE_ENGAGEMENT_DROPDOWN + getProjectFilter(active, clientId, "-1", cslId, gdmId, gddId,programManagerId, businessUnitId, projectId, psId,lodId, deliverymodelId) + ") ORDER BY dot.DeliveryOrganizationTypeName ";
            practiceEngagement = entityManager.createNativeQuery(queryBuilder, "PracticeEngagementMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setPracticeEngagementList(practiceEngagement);

        List<FilterDto> gdpId;
        try {
            StringBuilder queryBuilder = new StringBuilder(Queries.GDP_DROPDOWN);
            queryBuilder.append(Constants.OPCO_STATUS).append(opcoId).append(Constants.AND_CLAUSE);
            String projectFilterCondition = getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId,  programManagerId, businessUnitId, -1, psId,lodId, deliverymodelId) + ") ORDER BY P.GDPId ";

            queryBuilder.append(projectFilterCondition);

            String queryString = queryBuilder.toString();

            gdpId = entityManager.createNativeQuery(queryString, "ProjectMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setGdpIdList(gdpId);

        List<FilterDto> csl = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(Queries.CSL_DROPDOWN);
        queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
        queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, "-1", gdmId, gddId, programManagerId,businessUnitId, projectId, psId,lodId, deliverymodelId) + ") ORDER BY R.firstName ");
        Query query = entityManager.createNativeQuery(String.valueOf(queryBuilder));
        List<Object[]> cslObjectList;
        try {
            cslObjectList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (Object[] row : cslObjectList) {
            String cslName = getCslName(row);
            csl.add(new FilterDto(row, cslName));
        }
        allFilterValues.setCslList(csl);
        
        List<FilterDto> gdm = new ArrayList<>();
        StringBuilder gdm_queryBuilder = new StringBuilder(Queries.GDM_DROPDOWN);
        gdm_queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
        gdm_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, "-1", gddId, programManagerId,businessUnitId, projectId, psId,lodId, deliverymodelId) + ")  ORDER BY R.firstName ");
        Query gdm_query = entityManager.createNativeQuery(String.valueOf(gdm_queryBuilder));
        List<Object[]> gdmObjectList;
        try {
        	gdmObjectList = gdm_query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (Object[] row : gdmObjectList) {
            String gdmName = getCslName(row);
            gdm.add(new FilterDto(row, gdmName));
        }
        allFilterValues.setGdmList(gdm);

        List<FilterDto> gdd = new ArrayList<>();
        StringBuilder gdd_queryBuilder = new StringBuilder(Queries.GDD_DROPDOWN);
        gdd_queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
        gdd_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId,"-1",programManagerId,businessUnitId, projectId, psId,lodId, deliverymodelId) + ") ORDER BY R.firstName ");
        Query gdd_query = entityManager.createNativeQuery(String.valueOf(gdd_queryBuilder));
        List<Object[]> gddObjectList;
        try {
            gddObjectList = gdd_query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (Object[] row : gddObjectList) {
            String gddName = getCslName(row);
            gdd.add(new FilterDto(row, gddName));
        }
        allFilterValues.setGddList(gdd);
        List<FilterDto> programManager = new ArrayList<>();
        StringBuilder programManager_queryBuilder = new StringBuilder(Queries.PROGRAM_MANAGER_DROPDOWN);
        programManager_queryBuilder.append(Constants.AND_CLAUS_OPCOID+opcoId+Constants.WHERE_CLAUSE);
        programManager_queryBuilder.append(getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId,gddId,"-1",businessUnitId, projectId, psId,lodId, deliverymodelId) + ") ORDER BY R.firstName ");
        Query programManager_query = entityManager.createNativeQuery(String.valueOf(programManager_queryBuilder));
        List<Object[]> programManagerObjectList;
        try {
            System.out.println("gx4");
            programManagerObjectList = programManager_query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (Object[] row : programManagerObjectList) {
            String programManagerName = getCslName(row);
            programManager.add(new FilterDto(row, programManagerName));
        }
        allFilterValues.setProgramManager(programManager);

        List<FilterDto> businessUnits = new ArrayList<>();
        try {
            String buQueryBuilder = Queries.BUSINESS_UNIT_FILTER_DROPDOWN + getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId,"-1", projectId, psId,lodId, deliverymodelId) + ") ORDER BY BU.BusinessUnitName ";
            businessUnits = entityManager.createNativeQuery(buQueryBuilder, "BusinessUnitFilterDropdownMapping").getResultList();
        }catch (Exception ex){
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setBusinessUnitList(businessUnits);

        List<FilterDto> psProjectIds = new ArrayList<>();
        try {
            String psProjectId = Queries.PS_PID_DROPDOWN + getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId,businessUnitId, projectId, -1,lodId, deliverymodelId) + ") ORDER BY P.PeopleSoftProjectId ";
            psProjectIds = entityManager.createNativeQuery(psProjectId, "ProjectToProjectIdMapping").getResultList();
        }catch (Exception ex){
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setPsProjectIdList(psProjectIds);

        List<LocationDTO> locations = new ArrayList<>();
        try {
            String locationQuery = Queries.GET_LOCATION_NAME + getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId,businessUnitId, projectId, psId,"-1", deliverymodelId) + ")) ORDER BY L.LocationName ";
            locations = entityManager.createNativeQuery(locationQuery , "LocationOfServicesDropdownMapping").getResultList();
        }catch (Exception ex){
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setLocationList(locations);

        List<FilterDto> deliveryModels = new ArrayList<>();
        try {
            String dmQueryBuilder = Queries.DELIVERY_MODEL_FILTER + getProjectFilter(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId,businessUnitId, projectId, psId, lodId,"-1") + ") ORDER BY DM.DeliveryModelName ";
            deliveryModels = entityManager.createNativeQuery(dmQueryBuilder , "DeliveryModelDropdownMapping").getResultList();
        }catch (Exception ex){
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        allFilterValues.setDeliverymodelList(deliveryModels);

        return allFilterValues;





    }

    /**
     * This method would be use for getting the CslName from Query.
     * @param columns is the CslName.
     * @return this method would be return columns of CslName.
     */
    public String getCslName(Object[] columns) {
        return getFullName((String) columns[1], (String) columns[2], (String) columns[3]);
    }

}