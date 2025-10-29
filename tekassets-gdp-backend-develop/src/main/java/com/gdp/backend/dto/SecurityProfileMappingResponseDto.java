package com.gdp.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class SecurityProfileMappingResponseDto {

    String current_Username;

    ArrayList<Integer> unAnsweredlist;

    public SecurityProfileMappingResponseDto(String current_Username, ArrayList<Integer> unAnsweredlist) {
        this.current_Username = current_Username;
        this.unAnsweredlist = unAnsweredlist;
    }

    public String getCurrent_Username() {
        return current_Username;
    }

    public void setCurrent_Username(String current_Username) {
        this.current_Username = current_Username;
    }

    public ArrayList<Integer> getUnAnsweredlist() {
        return unAnsweredlist;
    }

    public void setUnAnsweredlist(ArrayList<Integer> unAnsweredlist) {
        this.unAnsweredlist = unAnsweredlist;
    }
}
