package com.gdp.backend.dto;

public class ModifiedByListOfCSLDto {

    private String modifiedBy;

    private String[] CSLs;

    public ModifiedByListOfCSLDto(String modifiedBy, String[] CSLs) {
        this.modifiedBy = modifiedBy;
        this.CSLs = CSLs;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String[] getCSLs() {
        return CSLs;
    }

    public void setCSLs(String[] CSLs) {
        this.CSLs = CSLs;
    }

    public static ModifiedByListOfCSLDto mapModifiedByListOfCSLDto(Object[] columns){
        String[] arrayOfCSL = ((String)columns[1]).split(",");
        return new ModifiedByListOfCSLDto(
                (String)columns[0],
                arrayOfCSL
        );
    }


}
