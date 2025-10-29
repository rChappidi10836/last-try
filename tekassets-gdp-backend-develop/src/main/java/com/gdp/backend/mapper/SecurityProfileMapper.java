package com.gdp.backend.mapper;

import com.gdp.backend.dto.AllSecurityAnswersDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;

public class SecurityProfileMapper {

    private SecurityProfileMapper() throws ActionFailureException {
        throw new ActionFailureException(EStatusCode.ERROR_ON_INSTANTIATION.name());
    }

    public static AllSecurityAnswersDto mapToAllSecurityAnswersDto(Object[] columns) {
        AllSecurityAnswersDto allSecurityAnswersDto = new AllSecurityAnswersDto();
        allSecurityAnswersDto.setSecurityAnswerId((Integer) columns[0]);
        allSecurityAnswersDto.setSecurityAnswer((String) columns[1]);
        allSecurityAnswersDto.setSecurityQuestion((String) columns[2]);
        allSecurityAnswersDto.setSelected(false);
        return allSecurityAnswersDto;
    }
}
