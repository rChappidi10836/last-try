package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.enums.UserAccess;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.AdminTabMapper;
import com.gdp.backend.model.*;
import com.gdp.backend.repository.*;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gdp.backend.common.Constants.*;
import static com.gdp.backend.common.Queries.*;

/**
 * This Service class used for retrieve,delete,update and add the information related to the Admin and User.
 * @author gdp
 *
 */
@Service
public class AdminService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GDPUserRepository gdpUserRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ResourceLocationRepository resourceLocationRepository;

    @Autowired
    DesignationRepository designationRepository;
    @Autowired
    OpcoRepository opcoRepository;


    /**
     * This Service would be use for the getting the list of Admin data info.
     * @param requestedTab request related to the list Admin data thats needs to populate.
     * @param pageNumber this is number of current page.
     * @param pageSize this is number of items which would be populate.
     * @return adminTabListResponseDto this class contains Admin Response.
     * @throws ActionFailureException exception would be throws if Admin data not found.
     */
    public AdminTabListResponseDto getListOfRequestedTab(String requestedTab, int pageNumber, int pageSize,int opcoId)
            throws ActionFailureException {
        AdminTabListResponseDto adminTabListResponseDto = new AdminTabListResponseDto();

        Query finalQuery = entityManager.createNativeQuery(getQuery(requestedTab, TRUE, null,opcoId));
        adminTabListResponseDto.setActiveTotalCount(finalQuery.getResultStream().count());
        finalQuery.setFirstResult((pageNumber) * pageSize);
        finalQuery.setMaxResults(pageSize);
        List<Object[]> entryList;
        try {
            entryList = finalQuery.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<AdminTabDto> adminTabDtoList = new ArrayList<>(entryList.size());
        for (Object[] row : entryList) {
            adminTabDtoList.add(AdminTabMapper.getAdminTabDto(row, requestedTab));
        }

        finalQuery = entityManager.createNativeQuery(getQuery(requestedTab, FALSE, null,opcoId));
        adminTabListResponseDto.setInActiveTotalCount(finalQuery.getResultStream().count());
        finalQuery.setFirstResult((pageNumber) * pageSize);
        finalQuery.setMaxResults(pageSize);
        System.out.println(finalQuery);

        try {
            entryList = finalQuery.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (Object[] row : entryList) {
            adminTabDtoList.add(AdminTabMapper.getAdminTabDto(row, requestedTab));
        }
        System.out.println(adminTabDtoList);
        adminTabListResponseDto.setAdminTabDtoList(adminTabDtoList);

        return adminTabListResponseDto;
    }

    /**
     * This Service would be use for the getting the Query data.
     * @param requestedTab request related to the list Admin data thats needs to populate.
     * @param active is the boolean value either true or false.
     * @param search data thats needs to search.
     * @return query holds the Admin data.
     */
    private String getQuery(String requestedTab, String active, String search,int opcoId) {
        StringBuilder query = new StringBuilder();
        // create dynamic query for requested tab
        query.append(Queries.ADMIN_QUERY_SELECT_ID_ACTIVE);
        query.append(requestedTab);
        query.append(Constants.NAME_CLAUSE);
        if(requestedTab.equals("Client")){
            query.append(", ");
            query.append(Constants.OPCO_CLAUSE);
        }
        query.append(Constants.FROM_CLAUSE);
        query.append(requestedTab);
        query.append(Constants.WHERE_CLAUSE);
        if (search != null) {
            query.append(requestedTab);
            query.append(Constants.NAME_CLAUSE);
            query.append(Constants.LIKE);
            query.append(Constants.LIKE_OPERATOR);
            query.append(search);
            query.append(Constants.PATTERN);
            query.append(Constants.AND_CLAUSE);
        }
        query.append(Constants.ACTIVE);
        query.append(Constants.EQUALS);
        query.append(active);
        if(requestedTab.equals("Client")) {
            query.append(" "+OPCO_ID);
            query.append(opcoId);
        }
        query.append(AND_CLAUSE);
        query.append(Constants.DELETED);
        query.append(Constants.EQUALS);
        query.append(FALSE);
        query.append(Constants.ORDER_BY_CLAUSE);
        query.append(requestedTab);
        query.append(Constants.NAME_CLAUSE);

        return query.toString();
    }

    /**
     * This Service would be use for the getting the list of Admin table search information.
     * @param requestedTab request related to the list of Admin data needs to populate.
     * @param search data which needs to be search.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @return response this Service contains list of data which found as per search.
     * @throws ActionFailureException exception would be throws if search data not found.
     */
    public AdminTabListResponseDto searchInRequestedTab(String requestedTab, String search, int pageNumber, int pageSize,int opcoId) throws ActionFailureException {
        AdminTabListResponseDto adminTabListResponseDto = new AdminTabListResponseDto();
        if (!StringUtils.isEmpty(search)) {
            Query finalQuery = entityManager.createNativeQuery(getQuery(requestedTab, TRUE, search,opcoId));
            adminTabListResponseDto.setActiveTotalCount(finalQuery.getResultStream().count());
            finalQuery.setFirstResult((pageNumber) * pageSize);
            finalQuery.setMaxResults(pageSize);
            List<Object[]> entryList;
            try {
                entryList = finalQuery.getResultList();
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
            List<AdminTabDto> adminTabDtoList = new ArrayList<>(entryList.size());
            for (Object[] row : entryList) {
                adminTabDtoList.add(AdminTabMapper.getAdminTabDto(row,requestedTab));
            }

            finalQuery = entityManager.createNativeQuery(getQuery(requestedTab, FALSE, search,opcoId));
            adminTabListResponseDto.setInActiveTotalCount(finalQuery.getResultStream().count());
            finalQuery.setFirstResult((pageNumber) * pageSize);
            finalQuery.setMaxResults(pageSize);
            try {
                entryList = finalQuery.getResultList();
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
            for (Object[] row : entryList) {
                adminTabDtoList.add(AdminTabMapper.getAdminTabDto(row,requestedTab));
            }

            adminTabListResponseDto.setAdminTabDtoList(adminTabDtoList);
        }
        return adminTabListResponseDto;
    }

    /**
     * This Service would be use for the saving the Admin data into the table.
     * @param entity is the admin data which need to populate.
     * @param adminTabDto request Parameter.
     * @throws ActionFailureException exception would be throws if data not saved.
     */
    @Transactional
    public void saveToDictionaryTable(String entity, AdminTabDto adminTabDto) throws ActionFailureException {
        if (adminTabDto.getId() == null)
            entityManager.merge(getSavingObject(entity, adminTabDto));
        else
            updateDictionaryTable(entity, adminTabDto);
    }
    @Transactional
    public void saveToClientTable(String entity, AdminTabDto adminTabDto) throws ActionFailureException {
        Client client = new Client();

        client.setId(adminTabDto.getId());
        client.setClientName(adminTabDto.getName());
        client.setActive(adminTabDto.getActive());
        client.setOpcoid(adminTabDto.getOpcoId());
        clientRepository.save(client);
    }

    /**
     * This method would be use for the getting the Admin data.
     * @param entity is the admin data which need to populate.
     * @param adminTabDto request Parameter.
     * @return response this class contains save of Admin data.
     * @throws ActionFailureException exception would be throws if data not saved.
     */
    @Transactional
    public Object getSavingObject(String entity, AdminTabDto adminTabDto) throws ActionFailureException {
        try {
            Class aClass = Class.forName(Constants.BASE_PACKAGE + entity);
            Constructor constructor = aClass.getConstructor(Integer.class, String.class, Boolean.class);
            return constructor.newInstance(adminTabDto.getId(), adminTabDto.getName(), adminTabDto.getActive());
        } catch (ReflectiveOperationException ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This method would be use for the saving the Admin data into the table.
     * @param entity is the admin data which need to populate.
     * @param adminTabDto request Parameter.
     * @throws ActionFailureException exception would be throws if data not saved.
     */
    @Transactional
    public void updateDictionaryTable(String entity, AdminTabDto adminTabDto) throws ActionFailureException {
        StringBuilder query = new StringBuilder();
        query.append(Queries.UPDATE);
        query.append(entity);
        query.append(Queries.SET);
        query.append(entity);
        query.append(Queries.NAME_ACTIVE);
        query.append(entity);
        query.append(NAME_CLAUSE);
        query.append(WHERE);
        System.out.println(query.toString());
        int count = entityManager.createNativeQuery(query.toString())
                .setParameter(1, adminTabDto.getName())
                .setParameter(2, adminTabDto.getActive())
                .setParameter(3, CurrentUsernameUtil.getCurrentUsername())
                .setParameter(4, adminTabDto.getId())
                .executeUpdate();
        if (count != 1) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name());
        }
    }

    /**
     * This Service would be use for the delete the Admin data from the table.
     * @param entity is the admin data which need to populate.
     * @param adminTabDto request related to the delete the Admin data.
     * @throws ActionFailureException exception would be throws if admin data not deleted.
     */
    @Transactional
    public void deleteFromDictionaryTable(String entity, AdminTabDto adminTabDto) throws ActionFailureException {
        if ((DeliveryOrganizationType.class.getSimpleName().equals(entity)
                && hasDeliveryOrgTypeReference(adminTabDto.getId()))
                || (DeliveryModel.class.getSimpleName().equals(entity)
                && hasDeliveryModelReference(adminTabDto.getId()))) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_DETELE.name());
        }
        StringBuilder query = new StringBuilder();
        query.append(Queries.UPDATE);
        query.append(entity);
        query.append(Queries.SET_DELETED_WHERE);
        int count = entityManager.createNativeQuery(query.toString()).setParameter(1, adminTabDto.getId()).executeUpdate();
        if (count != 1) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_DETELE.name());
        }
    }

    /**
     * This Service would be use for the delete the Admin data from the table.
     * @param id this is admin id.
     * @return boolean value either DeliveryModelId available or not.
     */
    private boolean hasDeliveryModelReference(Integer id) {
        Optional<List<Project>> projectsByDeliveryModelId = projectsRepository.findByDeliveryModelId(id);
        return projectsByDeliveryModelId.isPresent();
    }

    /**
     * This Service would be use for the delete the Admin data from the table.
     * @param id id this is admin id.
     * @return boolean value either OrganizationTypeId available or not.
     */
    private boolean hasDeliveryOrgTypeReference(Integer id) {
        Optional<List<Project>> byDeliveryOrganizationTypeId = projectsRepository.findByDeliveryOrganizationTypeId(id);
        return byDeliveryOrganizationTypeId.isPresent();
    }

    /**
     * This Service would be use for update the existing user data and add if data not there.
     * @param userRole This is user role.
     * @throws ActionFailureException exception would be throws if user data not updated.
     */
    @Transactional
    public void updateUserAccess(UserRole userRole) throws ActionFailureException {
        Integer resourceId = userRole.getResourceId();
        String access = userRole.getAccess();
        Optional<Role> role = roleRepository.findByRole(getAccessValue(access));
        if (!role.isPresent()) {
            throw new ActionFailureException("Trying to set invalid role!");
        }
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        if (resource.isPresent()) {
            resource.get().setAccess(getAccessValue(access));
            resourceRepository.save(resource.get());
        } else {
            throw new ActionFailureException("Resource not found!!");
        }
        String OpcoId =resource.get().getOpcoid();
        Query domain_query = entityManager.createNativeQuery(Queries.GET_OPCO_DOMAINS);
        domain_query.setParameter(1, OpcoId);
        String[] domains=domain_query.getSingleResult().toString().split(",");
        Query query = entityManager.createNativeQuery(Queries.GET_USERNAME_FROM_GDPUSER_TABLE, GDPUser.class);
        Optional<GDPUser> gdpUser=null;
        for(String i:domains) {
            query.setParameter(1,resource.get().getUserId()+ "@"+i);
            gdpUser = query.getResultList().stream().findFirst();
            if(gdpUser.isPresent()){
                break;
            }
        }
        if (gdpUser.isPresent() && role.isPresent()) {
            gdpUser.get().setRoles(role.get());
            if (role.get().getRole().equals(UserAccess.REVOKE_ACCESS.getUserAccess())) {
                gdpUser.get().setDeleted(true);
                gdpUser.get().setEnabled(false);
            } else {
                gdpUser.get().setDeleted(false);
                gdpUser.get().setEnabled(true);
            }
            gdpUserRepository.save(gdpUser.get());
        } else {
            GDPUser user = new GDPUser();
            user.setUsername(resource.get().getUserId()+"@"+domains[0]);
            user.setRoles(role.get());
            if (role.get().getRole().equals(UserAccess.REVOKE_ACCESS.getUserAccess())) {
                user.setDeleted(true);
                user.setEnabled(false);
            }
            user.setEnabled(true);
            user.setDeleted(false);
            gdpUserRepository.save(user);
        }
    }
    @Transactional
    public String searchGDPUser(String username, Integer UserID) throws ActionFailureException {
        Optional<Resource> resource = resourceRepository.findById(UserID);
        String OpcoId =resource.get().getOpcoid();
        Query domain_query = entityManager.createNativeQuery(Queries.GET_OPCO_DOMAINS);
        domain_query.setParameter(1, OpcoId);
        String[] domains=domain_query.getSingleResult().toString().split(",");
        Query query = entityManager.createNativeQuery(Queries.GET_USERNAME_FROM_GDPUSER_TABLE, GDPUser.class);
        Optional<GDPUser> gdpUser=null;
        for(String i:domains) {
            query.setParameter(1, resource.get().getUserId()+ "@" + i);
            gdpUser = query.getResultList().stream().findFirst();
            if (gdpUser.isPresent()) {
                break;
            }
        }
        if (gdpUser.isPresent() ) {
            return "User Found";
        } else {
            return "User Not found";
        }
    }

    @Transactional
    public List<Client> checkIfClientExists(String AccountName, Boolean Active, Integer opcoId) throws ActionFailureException{
        List<Client> clients=clientRepository.findaccList(AccountName,Active,opcoId);
        return clients;
    }
    /**
     * This method would be use for getting the Access value.
     * @param access is the access data.
     * @return admin or user.
     */
    private String getAccessValue(String access) {
        switch (access) {
            case "Admin":
                return UserAccess.ADMIN.getUserAccess();
            case "Global Edit Access":
                return UserAccess.GLOBAL_EDIT_ACCESS.getUserAccess();
            case "Global View Access":
                return UserAccess.GLOBAL_VIEW_ACCESS.getUserAccess();
            case "Executive":
                return UserAccess.EXECUTIVE.getUserAccess();
            case "Revoke Access":
                return UserAccess.REVOKE_ACCESS.getUserAccess();
            default:
                return null;
        }
    }

    /**
     * This service would be use for the getting and searching the list of Admin Manage access data.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @param searchKey data need to search.
     * @return AdminManageAccessListDto is the response of the services.
     * @throws ActionFailureException exception would be throws if Resource search data not found.
     */
    public AdminManageAccessListDto getAdminManageAccessList(int pageNumber, int pageSize, String opcoId,String searchKey) throws ActionFailureException {
        AdminManageAccessListDto adminManageAccessListDto = new AdminManageAccessListDto();
        StringBuilder searchClause = new StringBuilder(" AND (R.FirstName LIKE CONCAT('%','");
        searchClause.append(searchKey).append("','%') OR R.LastName LIKE CONCAT('%','").append(searchKey).append("','%') ")
                .append("OR R.UserId LIKE CONCAT('%', '").append(searchKey).append("','%'))");
        List<Object[]> activeList;

        try {
            StringBuilder query=new StringBuilder();
            Query countQuery;
            Query finalquery;
            if (StringUtils.isEmpty(searchKey)){
                query.append(Queries.ADMIN_MANAGE_ACCESS_GET_QUERY);
                query.append(WHERE_CLAUSE+R_ACTIVE);
                query.append(VALUE_FOR_IS_ACTIVE);
                query.append(SPACE+Constants.AND_CLAUS_ROPCOID);
                query.append(opcoId);
                query.append(ORDER_BY_EMPLOYEE);
                finalquery = entityManager.createNativeQuery(query.toString());
                countQuery = entityManager.createNativeQuery(ADMIN_MANAGE_ACCESS_COUNT_QUERY+"1"+" AND R.OpcoId="+opcoId);
                System.out.println("Testing:"+finalquery);
            } else {
                query.append(Queries.ADMIN_MANAGE_ACCESS_SEARCH_QUERY);
                query.append(Constants.SPACE+ Constants.R_ACTIVE);
                query.append(VALUE_FOR_IS_ACTIVE);
                query.append(Constants.SPACE+Constants.AND_CLAUS_ROPCOID);
                query.append(opcoId);
                query.append(Constants.ORDER_BY_EMPLOYEE);
                finalquery = entityManager.createNativeQuery(query.toString());
                finalquery.setParameter(1, searchKey).setParameter(2, searchKey).setParameter(3, searchKey);
                countQuery = entityManager.createNativeQuery(ADMIN_MANAGE_ACCESS_COUNT_QUERY+"1"+" AND R.OpcoId="+opcoId+searchClause);
                System.out.println("Testing"+finalquery);
            }
            adminManageAccessListDto.setActiveTotalCount(Long.valueOf(countQuery.getResultList().get(0).toString()));
            finalquery.setFirstResult((pageNumber) * pageSize);
            finalquery.setMaxResults(pageSize);
            activeList = finalquery.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<AdminManageAccessDto> adminManageAccessDtoActiveList = new ArrayList<>(activeList.size());
        for (Object[] row : activeList) {
            adminManageAccessDtoActiveList.add(AdminTabMapper.getAdminManageAccessDto(row));
        }

        List<Object[]> inactiveList;
        try {
            StringBuilder query=new StringBuilder();
            Query finalquery;
            Query countQuery;
            if (StringUtils.isEmpty(searchKey)) {
                query.append(Queries.ADMIN_MANAGE_ACCESS_GET_QUERY);
                query.append(WHERE_CLAUSE+R_ACTIVE);
                query.append(VALUE_FOR_IS_NOT_ACTIVE);
                query.append(SPACE+Constants.AND_CLAUS_ROPCOID);
                query.append(opcoId);
                query.append(ORDER_BY_EMPLOYEE);

                finalquery = entityManager.createNativeQuery(query.toString());
                countQuery = entityManager.createNativeQuery(ADMIN_MANAGE_ACCESS_COUNT_QUERY+"0 "+" AND R.OpcoId="+opcoId);
            } else {
                query.append(Queries.ADMIN_MANAGE_ACCESS_SEARCH_QUERY);
                query.append(Constants.SPACE+ Constants.R_ACTIVE);
                query.append(VALUE_FOR_IS_NOT_ACTIVE);
                query.append(Constants.SPACE+Constants.AND_CLAUS_ROPCOID);
                query.append(opcoId);
                query.append(Constants.ORDER_BY_EMPLOYEE);
                finalquery = entityManager.createNativeQuery(query.toString());
                finalquery.setParameter(1, searchKey).setParameter(2, searchKey).setParameter(3, searchKey);
                countQuery = entityManager.createNativeQuery(ADMIN_MANAGE_ACCESS_COUNT_QUERY+"0 "+" AND R.OpcoId="+opcoId+ searchClause);
            }
            List result = countQuery.getResultList();
            adminManageAccessListDto.setInactiveTotalCount(Long.valueOf((result.get(0)).toString()));
            finalquery.setFirstResult((pageNumber) * pageSize);
            finalquery.setMaxResults(pageSize);
            inactiveList = finalquery.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<AdminManageAccessDto> adminManageAccessDtoInactiveList = new ArrayList<>(inactiveList.size());
        for (Object[] row : inactiveList) {
            adminManageAccessDtoInactiveList.add(AdminTabMapper.getAdminManageAccessDto(row));
        }

        List<AdminManageAccessDto> resourceList = Stream.concat(adminManageAccessDtoActiveList.stream(), adminManageAccessDtoInactiveList.stream()).collect(Collectors.toList());
        adminManageAccessListDto.setResources(resourceList);

        return adminManageAccessListDto;
    }

    /**
     * This Service would be use for the delete the Resource data.
     * @param resourceId is the corresponding resourceId which needs to delete.
     * @throws ActionFailureException exception would be throws if Resource data not deleted.
     */
    public void deleteResource(int resourceId) throws ActionFailureException {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        if (resource.isPresent()) {
            resource.get().setActive(false);
            resource.get().setDeleted(Constants.TRUE);
            resourceRepository.save(resource.get());
        } else {
            throw new ActionFailureException("Resource not found.");
        }
    }

    /**
     * This Service would be use for the retrieve the list of designation data.
     * @return response this class retrieve the list of designation data.
     * @throws ActionFailureException exception would be throws, if data not found.
     */
    public List<DesignationDto> getDesignationsList() throws ActionFailureException {
        List<DesignationDto> designationList;
        try {
            designationList = entityManager.createNativeQuery(GET_ACTIVE_DESIGNATIONS_QUERY, "DesignationDto").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        return designationList;
    }

    /**
     * This Service would be use for the getting the list of resource data.
     * @param search data which needs to be search.
     * @return this class retrieve the list of designation data.
     * @throws ActionFailureException exception would be throws, if data not found.
     */
    public List<ResourceDto> getResourceList(String search,String opcoId) throws ActionFailureException {
        List<ResourceDto> resourceList;
        try {StringBuilder query = new StringBuilder();
            query.append(Queries.SEARCH_ACTIVE_RESOURCES_QUERY);
            query.append(WHERE_CLAUSE);
            query.append("(");
            query.append(Constants.FIRST_NAME + Constants.LIKE + Constants.LIKE_OPERATOR+ search + Constants.PATTERN);
            query.append(OR);
            query.append(Constants.LAST_NAME + Constants.LIKE + Constants.LIKE_OPERATOR+ search + Constants.PATTERN);
            query.append(")");
            query.append(Constants.AND_CLAUSE);
            query.append(Constants.IS_ACTIVE);
            if(!(StringUtils.isEmpty(opcoId))){
                query.append(Constants.OPCO_ID);
                query.append(opcoId);
                }
            Query finalQuery = entityManager.createNativeQuery(query.toString(),"ResourceDto");
            resourceList = finalQuery.getResultList();

            //Query query = entityManager.createNativeQuery(SEARCH_ACTIVE_RESOURCES_QUERY, "ResourceDto");
           // query.setParameter(1, search).setParameter(2, search);
           // System.out.println("testing in getResource list ");
           // resourceList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        return resourceList;
    }

    /**
     * This Service would be use for the saving EditManageAccess data.
     * @param adminManageAccessEditDto EditManageAccess data.
     */
    @Transactional
    public void addEditManageAccess(AdminManageAccessEditDto adminManageAccessEditDto) {

        Resource resource = getResourceEntity(adminManageAccessEditDto);
        resourceRepository.save(resource);
    }

    /**
     * This Service would be use for delete the Project.
     * @param projectId is the existing Project.
     */
    @Transactional
    public void deleteProject(int projectId){
        Optional<Project> project = projectsRepository.findById(projectId);
        if(project.isPresent()){
            project.get().setActive(false);
            projectsRepository.save(project.get());
        }

    }

    /**
     * This methos would be use for the getting the Resource Entity data.
     * @param adminManageAccessEditDto is the Manage Access data.
     * @return Resource data.
     */
    public Resource getResourceEntity(AdminManageAccessEditDto adminManageAccessEditDto) {
        Resource resource = new Resource();
        System.out.println("data is :"+adminManageAccessEditDto.toString());
        resource.setId(adminManageAccessEditDto.getId());
        resource.setEmployeeId(adminManageAccessEditDto.getEmployeeId());
        resource.setFirstName(adminManageAccessEditDto.getFirstName());
        resource.setMiddleName(adminManageAccessEditDto.getMiddleName());
        resource.setLastName(adminManageAccessEditDto.getLastName());
        if (adminManageAccessEditDto.getDesignationId() != null) {
            Optional<Designation> designation = designationRepository.findById(adminManageAccessEditDto.getDesignationId());
            resource.setDesignation(designation.orElse(null));
        }
        resource.setHireDate(adminManageAccessEditDto.getHireDate());
        resource.setTerminationDate(adminManageAccessEditDto.getTerminationDate());
        resource.setUserId(adminManageAccessEditDto.getUsername());
        resource.setActive(adminManageAccessEditDto.isActive());
        if (adminManageAccessEditDto.getSupervisorId() != null) {
            Optional<Resource> supervisor = resourceRepository.findById(adminManageAccessEditDto.getSupervisorId());
            resource.setSupervisor(supervisor.orElse(null));
        }
        resource.setJobCode(adminManageAccessEditDto.getJobCode());
        if (adminManageAccessEditDto.getResourceLocationId() != null) {
            Optional<ResourceLocation> resourceLocation = resourceLocationRepository.findById(adminManageAccessEditDto.getResourceLocationId());
            resource.setResourceLocation(resourceLocation.orElse(null));
        }
        resource.setOpcoid(adminManageAccessEditDto.getOpcoId());
        resource.setAccess(adminManageAccessEditDto.getAccess());
        resource.setDeleted(Constants.FALSE);
        return resource;

    }

//    public List<OpcoDto> getOpcoList() throws ActionFailureException {
//        List<OpcoDto> opcoList;
//        try {
//            opcoList = entityManager.createNativeQuery(GET_OPERATING_COMPANY_QUERY, "OpcoDto").getResultList();
//        } catch (Exception ex) {
//            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
//        }
//        return opcoList;
//    }


}
