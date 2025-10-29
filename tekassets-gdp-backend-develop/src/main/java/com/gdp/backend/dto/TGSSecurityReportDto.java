package com.gdp.backend.dto;

import java.util.Date;

public class TGSSecurityReportDto {

    String projectName;
    String accountName;
    String CSL;
    String GDD;
    String practice;
    String Location;
    String SMPLink;
    String opportunityId;
    String GDPId;
    Date projectEndDate;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    String answer5;
    String answer6;
    String answer7;
    String answer8;
    String answer9;
    String answer10;

    public String getAnswer10() {
        return answer10;
    }

//    public void setAnswer10(String answer10) {
//        this.answer10 = answer10;
//    }




    Date modifiedAt;
    String modifiedBy;

//    public TGSSecurityReportDto(String modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }

    public TGSSecurityReportDto(String projectName, String accountName, String CSL, String GDD, String practice, String location, String SMPLink, String opportunityId, String GDPId, Date projectEndDate, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6, String answer7, String answer8, String answer9, String answer10, Date modifiedAt, String modifiedBy) {
        this.projectName = projectName;
        this.accountName = accountName;
        this.CSL = CSL;
        this.GDD = GDD;
        this.practice = practice;
        Location = location;
        this.SMPLink = SMPLink;
        this.opportunityId = opportunityId;
        this.GDPId = GDPId;
        this.projectEndDate = projectEndDate;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.answer6 = answer6;
        this.answer7 = answer7;
        this.answer8 = answer8;
        this.answer9 = answer9;
        this.answer10 = answer10;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCSL() {
        return CSL;
    }

    public String getGDD() { return GDD; }

    public String getPractice() {
        return practice;
    }

    public String getLocation() {
        return Location;
    }

    public String getSMPLink() {
        return SMPLink;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public String getGDPId() {
        return GDPId;
    }

    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public String getAnswer6() {
        return answer6;
    }

    public String getAnswer7() {
        return answer7;
    }

    public String getAnswer8() {
        return answer8;
    }

    public String getAnswer9() {
        return answer9;
    }

    public Date getModifiedAt() { return modifiedAt; }

    public String getModifiedBy() { return modifiedBy; }



    public static TGSSecurityReportDto tgsSecurityReportDtoMapper(Object[] columns) {
        return new TGSSecurityReportDto(
                (String) columns[0],
                (String) columns[1],
                (String) columns[2],
                (String) columns[3],
                (String) columns[4],
                (String) columns[5],
                (String) columns[6],
                (String) columns[7],
                (String) columns[8],
                (Date) columns[9],
                (String) columns[10],
                (String) columns[11],
                (String) columns[12],
                (String) columns[13],
                (String) columns[14],
                (String) columns[15],
                (String) columns[16],
                (String) columns[17],
                (String) columns[18],
                (String) columns[19],
                (Date) columns[20],
                (String) columns[21]
        );
    }

}
