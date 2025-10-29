package com.gdp.backend.controller;

import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.repository.ResourceRepository;
import com.gdp.backend.service.AdminService;
import com.gdp.backend.service.DoSoftDeleteService;
import com.gdp.backend.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This Controller class used for retrieve,delete,update and add the information related to the Admin and User.
 * @author gdp
 *
 */
@RestController
@RequestMapping("admin")
@Secured({"ROLE_Admin","ROLE_Executive"})
public class AdminTabController {

    @Autowired
    ExcelService excelService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private DoSoftDeleteService doSoftDeleteService;

    /**
     * This API would be use for the getting the list of Admin table info.
     * @param requestedTab request related to the list Admin data needs to populate.
     * @param page this is number of current page.
     * @param size this is number of items which would be populate.
     * @return response this class contains Admin Response.
     * @throws ActionFailureException exception would be throws if data not found.
     */
    @GetMapping("/get-requested-tab-list")
    public ResponseEntity<Response<AdminTabListResponseDto>> getAdminTabList(@RequestParam("requestedTab") String requestedTab,
                                                                             @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value="opcoId",required=false) Integer opcoId) throws ActionFailureException {
        Response<AdminTabListResponseDto> response = new Response<>();
        if(requestedTab.equals("Client")){
            response.setData(Arrays.asList(adminService.getListOfRequestedTab(requestedTab, page, size,opcoId)));
        }else {
            response.setData(Arrays.asList(adminService.getListOfRequestedTab(requestedTab, page, size,0)));
        }
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the list of Admin table search information.
     * @param requestedTab request related to the list of Admin data needs to populate.
     * @param search data which needs to be search.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains list of data which found as per search.
     * @throws ActionFailureException exception would be throws if search data not found.
     */
    @GetMapping("/get-requested-tab-searched-list")
    public ResponseEntity<Response<AdminTabListResponseDto>> getAdminTabSearchedList(@RequestParam("requestedTab") String requestedTab, @RequestParam("search") String search,
                                                                                     @RequestParam("page") int page, @RequestParam("size") int size,@RequestParam(value="opcoId",required=false) Integer opcoId) throws ActionFailureException {
        Response<AdminTabListResponseDto> response = new Response<>();
        if(requestedTab.equals("Client")){
            response.setData(Arrays.asList(adminService.searchInRequestedTab(requestedTab, search, page, size,opcoId)));
        }else {
            response.setData(Arrays.asList(adminService.searchInRequestedTab(requestedTab, search, page, size,0)));
        }

        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the saving the Admin data in to the table.
     * @param requestedTab request related to the list of Admin data needs to populate.
     * @param adminTabDto request Parameter.
     * @return response this class contains save of Admin data.
     * @throws ActionFailureException exception would be throws if data not saved.
     */
    @PostMapping("/save-to-requested-tab")
    public ResponseEntity<Response> saveToRequestedTab(@RequestParam("requestedTab") String requestedTab, @RequestBody AdminTabDto adminTabDto) throws ActionFailureException {
        Response response = new Response();
        adminService.saveToDictionaryTable(requestedTab, adminTabDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save-client")
    public ResponseEntity<Response> saveClientToRequestedTab(@RequestParam("requestedTab") String requestedTab, @RequestBody AdminTabDto adminTabDto) throws ActionFailureException {
        Response response = new Response();
        adminService.saveToClientTable(requestedTab, adminTabDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the delete the Admin data from the table.
     * @param requestedTab request related to the delete the Admin data.
     * @param adminTabDto request related to the delete the Admin data.
     * @return response this class contains delete the Admin data.
     * @throws ActionFailureException exception would be throws if admin data not deleted.
     */
    @PostMapping("/delete-from-requested-tab")
    public ResponseEntity<Response> deleteAdminTabList(@RequestParam("requestedTab") String requestedTab, @RequestBody AdminTabDto adminTabDto) throws ActionFailureException {
        Response response = new Response<>();
        adminService.deleteFromDictionaryTable(requestedTab, adminTabDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for update the existing user data and add if data not there.
     * @param userRole This is user role.
     * @return response this class update the user data.
     * @throws ActionFailureException exception would be throws if user data not updated.
     */
    @PostMapping("/manage-user-role")
    public ResponseEntity<Response> updateUserRole(@RequestBody UserRole userRole) throws ActionFailureException {
        Response response = new Response();
        adminService.updateUserAccess(userRole);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search-gdp-user")
    public ResponseEntity<Response> searchGDPUser(@RequestParam("username") String username,@RequestParam("userID") Integer UserID) throws ActionFailureException {
        Response response = new Response();
        response.setSuccessMessage(adminService.searchGDPUser(username,UserID));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * This API would be use for the getting the list of Admin Manage data.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class retrieve the Manage access data.
     * @throws ActionFailureException exception would be throws if Manage access data not found.
     */
    @ViewAccess
    @GetMapping("/get-resource-list")
    public ResponseEntity<Response<AdminManageAccessListDto>> getAdminManageAccessList(@RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("opcoId") String opcoId) throws ActionFailureException {
        Response<AdminManageAccessListDto> response = new Response<>();
        response.setData(Arrays.asList(adminService.getAdminManageAccessList(page, size,opcoId,null)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the search the Resource data.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @param searchKey data need to search.
     * @return response this class return list of resource data.
     * @throws ActionFailureException exception would be throws if Resource search data not found.
     */
    @ViewAccess
    @GetMapping("/search-resources")
    public ResponseEntity<Response<AdminManageAccessListDto>> searchResources(@RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("opcoId") String opcoId, @RequestParam("searchKey") String searchKey) throws ActionFailureException {
        Response<AdminManageAccessListDto> response = new Response<>();
        response.setData(Arrays.asList(adminService.getAdminManageAccessList(page, size,opcoId, searchKey)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the delete the Resource data.
     * @param resourceId is the corresponding resourceId which needs to delete.
     * @return response this class delete the resource data.
     * @throws ActionFailureException exception would be throws if Resource data not deleted.
     */
    @DeleteMapping("/delete-resource/{resourceId}")
    public ResponseEntity<Response> deleteResource(@PathVariable Integer resourceId) throws ActionFailureException {
        Response response = new Response();
        adminService.deleteResource(resourceId);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the retrieve the list of designation data.
     * @return response this class retrieve the list of designation data.
     * @throws ActionFailureException exception would be throws, if data not found.
     */
    @GetMapping("/get-designation-list")
    public ResponseEntity<Response<List<DesignationDto>>> getDesignationsList() throws ActionFailureException {
        Response<DesignationDto> response = new Response<>();
        response.setData(adminService.getDesignationsList());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity(response, HttpStatus.OK);
    }
    /**
     * This API would be use for the getting the list of resource data.
     * @param search data which needs to be search.
     * @return response this class retrieve the list of designation data.
     * @throws ActionFailureException exception would be throws, if data not found.
     */
    @GetMapping("/get-supervisor-list")
    public ResponseEntity<Response<List<ResourceDto>>> getResourceList(@RequestParam("search") String search,@RequestParam("opcoId") String opcoId) throws ActionFailureException {
        Response<ResourceDto> response = new Response<>();
        response.setData(adminService.getResourceList(search,opcoId));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @GetMapping("/get-opco-list")
//    public ResponseEntity<Response<List<OpcoDto>>> getOpcoList() throws ActionFailureException {
//        Response<OpcoDto> response = new Response<>();
//        response.setData(adminService.getOpcoList());
//        response.setStatusCode(EStatusCode.SUCCESS.name());
//        return new ResponseEntity(response, HttpStatus.OK);
//    }
    /**
     * This API would be use for the saving EditManageAccess data.
     * @param adminManageAccessDto EditManageAccess data.
     * @return response this class saving EditManageAccess data.
     */
    @PostMapping("/save-resource")
    public ResponseEntity<Response> addEditManageAccess(@RequestBody AdminManageAccessEditDto adminManageAccessDto) {
        Response response = new Response<>();
        adminService.addEditManageAccess(adminManageAccessDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity(response, HttpStatus.OK);
    }
    /**
     * This API would be use for the getting Excel Sheet details.
     * @param requestedExcel is the request for ExcelSheet data.
     * @param response is the ExcelSheet data.
     * @return this class return the HttpStatus.
     * @throws ActionFailureException exception would be throws, if ExcelSheet not found.
     */
    @GetMapping("/export-to-excel/{requestedExcel}")
    public ResponseEntity<StreamingResponseBody> getExcelSheet(@PathVariable("requestedExcel") String requestedExcel,HttpServletResponse response,@RequestParam(value="active",required = false) Boolean active,@RequestParam(value="opcoId",required = false) Integer opcoId)
            throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                if (requestedExcel.equals("Client")) {
                    excelService.createExcelForExport(requestedExcel, outputStream,active, opcoId);
                } else if(requestedExcel.equals("DeliveryOrganizationType")) {
                    excelService.createExcelForExport(requestedExcel, outputStream,active, 0);
                }else{
                    excelService.createExcelForExport(requestedExcel, outputStream,false,0);
                }
            }catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.xlsx", requestedExcel))
                .body(bb);
    }

    /**
     * This API would be use for the getting Manage Access Excel details.
     * @param isActive is the boolean. 
     * @param response is HttpServletResponse.
     * @return this class return the HttpStatus.
     * @throws ActionFailureException exception would be throws, if ManageAccessExcel not found.
     */
    @GetMapping("/export-to-excel/manage-access")
    public ResponseEntity<StreamingResponseBody> getManageAccessExcel(@RequestParam("isActive") boolean isActive,@RequestParam("opcoId") String opcoId, HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createExcelForManageAccess(isActive,opcoId, outputStream);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ResourcesWithRoles.xlsx")
                .body(bb);
    }

    /**
     * This API would be use for delete the Project.
     * @param projectId is the existing Project.
     * @return response with HttpStatus.
     */
    @DeleteMapping("/delete-project/{projectId}")
    public ResponseEntity<Response<?>> deleteProject(@PathVariable Integer projectId) {
        Response<?> response = new Response<>();
        adminService.deleteProject(projectId);
        response.setStatusCode(HttpStatus.OK.toString());
        response.setSuccessMessage(Constants.DELETED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for check UserId is Exists or Not.
     * @param userId that needs to check.
     * @return response with HttpStatus.
     */
    @GetMapping("/check-userid-exists")
    public ResponseEntity<Boolean> checkIfUserIdExists(@RequestParam("userId") String userId,@RequestParam("opcoId") Integer OpcoId ) {
        boolean response = false;
        try {
            if (resourceRepository.findByUserIdWithOpcoId(userId,OpcoId).isPresent())
                response = true;
        } catch (IncorrectResultSizeDataAccessException e) {
            // there exists more than 1 row in db for this employeeId
            response = true;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for check EmployeeId is Exists or Not.
     * @param employeeId thats need to check.
     * @return response with HttpStatus.
     */
    @GetMapping("/check-employeeId-exists")
    public ResponseEntity<Boolean> checkIfEmployeeIdExists(@RequestParam("employeeId") String employeeId,@RequestParam("opcoId") Integer opcoId) {
        boolean response = false;
        try {
            if (resourceRepository.findByEmployeeId(employeeId,opcoId).isPresent())
                response = true;
        } catch (IncorrectResultSizeDataAccessException e) {
            // there exists more than 1 row in db for this employeeId
            response = true;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/check-account-exists")
    public ResponseEntity<Boolean> checkIfAcountNameExists(@RequestParam("AccountName") String AccountName,@RequestParam("Active") Boolean Active,@RequestParam("opcoId") Integer opcoId) throws ActionFailureException {
        boolean response = false;
        try {
            List clientList=adminService.checkIfClientExists(AccountName.trim(),Active,opcoId);
            if (!clientList.isEmpty())
                response = true;
        } catch (IncorrectResultSizeDataAccessException e) {
            response = true;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for download the Project Archive Data.
     * @return response with HttpStatus.
     * @throws ActionFailureException exception would be throws, if Project Archive Data is not found.
     */
    @GetMapping("/project-archived-data")
    public ResponseEntity<StreamingResponseBody> downloadProjectArchiveData() throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createProjectArchiveData(outputStream);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProjectArchivedData.xlsx")
                .body(bb);
    }

    /**
     * This API would be used for getting list of all the GDP Ids that are in active state.
     * @return response of this class contains GDP IDs based on condition or criteria.
     * @throws ActionFailureException exception would be thrown data not found.
     */
    @GetMapping("/get-gdp-list")
    public ResponseEntity<Response<GDPIdDto>> getGdpFilter() throws ActionFailureException {
        Response<GDPIdDto> response = new Response<>();
        response.setData(Arrays.asList(doSoftDeleteService.getGDPDetails()));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * This API would be used for updating the engagement record status to closed based on GDP IDs.
     * @return response of this class contains the response and status code.
     * @throws ActionFailureException exception would be thrown data not found.
     */
    @PostMapping("/soft-delete")
    public ResponseEntity<Response> doSoftDelete
    (@RequestBody Object GdpId) throws ActionFailureException {
        Response response = new Response<>();
        System.out.println(GdpId);
        ArrayList<String> deletedData= new ArrayList<>();
        deletedData=doSoftDeleteService.performSoftDelete((ArrayList<Integer>) GdpId);
        response.setData(deletedData);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
