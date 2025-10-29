package com.gdp.backend.mapper;

import com.gdp.backend.dto.AdminManageAccessDto;
import com.gdp.backend.dto.AdminManageAccessEditDto;
import com.gdp.backend.dto.AdminTabDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Designation;
import com.gdp.backend.model.Resource;
import com.gdp.backend.model.ResourceLocation;
import com.gdp.backend.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class AdminTabMapper {

    private AdminTabMapper() throws ActionFailureException {
        throw new ActionFailureException(EStatusCode.ERROR_ON_INSTANTIATION.name());
    }

    public static AdminTabDto getAdminTabDto(Object[] columns, String requestedTab) {
        AdminTabDto adminTabDto = new AdminTabDto();
        adminTabDto.setId((Integer) columns[0]);
        adminTabDto.setActive((Boolean) columns[1]);
        adminTabDto.setName((String) columns[2]);
        if(requestedTab.equals("Client")){
            adminTabDto.setOpcoId((Integer) columns[3]);
        }
        return adminTabDto;
    }

    public static AdminManageAccessDto getAdminManageAccessDto(Object[] columns) {
        AdminManageAccessDto adminManageAccessDto = new AdminManageAccessDto();
        adminManageAccessDto.setId((Integer) columns[0]);
        adminManageAccessDto.setEmployeeName((String) columns[7]);
        adminManageAccessDto.setEmployeeId((String) columns[1]);
        adminManageAccessDto.setUsername((String) columns[6]);
        adminManageAccessDto.setJobCode((String) columns[3]);
        adminManageAccessDto.setJobTitle((String) columns[10]);
        adminManageAccessDto.setSupervisor((String) columns[8]);
        adminManageAccessDto.setLocation((String) columns[9]);
        adminManageAccessDto.setHireDate((Date) columns[4]);
        adminManageAccessDto.setAccess((String) columns[2]);
        adminManageAccessDto.setActive((Boolean) columns[5]);
        adminManageAccessDto.setTerminationDate((Date) columns[11]);
        adminManageAccessDto.setOpcoName((String) columns[12]);
        adminManageAccessDto.setResourceLocationId((Integer) columns[13]);
        return adminManageAccessDto;
    }

    public static Resource getResourceEntity(AdminManageAccessEditDto adminManageAccessEditDto) {
        Resource resource = new Resource();
        resource.setId(adminManageAccessEditDto.getId());
        resource.setEmployeeId(adminManageAccessEditDto.getEmployeeId());
        resource.setFirstName(adminManageAccessEditDto.getFirstName());
        resource.setMiddleName(adminManageAccessEditDto.getMiddleName());
        resource.setLastName(adminManageAccessEditDto.getLastName());
        if (adminManageAccessEditDto.getDesignationId() != null) {
            Designation designation = new Designation();
            designation.setId(adminManageAccessEditDto.getDesignationId());
            resource.setDesignation(designation);
        }
        resource.setHireDate(adminManageAccessEditDto.getHireDate());
        resource.setTerminationDate(adminManageAccessEditDto.getTerminationDate());
        resource.setUserId(adminManageAccessEditDto.getUsername());
        resource.setActive(adminManageAccessEditDto.isActive());
        if (adminManageAccessEditDto.getSupervisorId() != null) {
            Resource supervisor = new Resource();
            supervisor.setId(adminManageAccessEditDto.getSupervisorId());
            resource.setSupervisor(supervisor);
        }
        resource.setJobCode(adminManageAccessEditDto.getJobCode());
        if (adminManageAccessEditDto.getResourceLocationId() != null) {
            ResourceLocation resourceLocation = new ResourceLocation();
            resourceLocation.setId(adminManageAccessEditDto.getResourceLocationId());
            resource.setResourceLocation(resourceLocation);
        }
        return resource;
    }

}
