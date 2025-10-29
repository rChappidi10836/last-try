package com.gdp.backend.dto;

import java.util.List;

public class UserSearchResponseDto {

    private Long totalNumberOfElements;

    private List<UserSearchDto> userSearchDtoList;

    public Long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public void setTotalNumberOfElements(Long totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<UserSearchDto> getUserSearchDtoList() {
        return userSearchDtoList;
    }

    public void setUserSearchDtoList(List<UserSearchDto> userSearchDtoList) {
        this.userSearchDtoList = userSearchDtoList;
    }
}
