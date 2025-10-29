package com.gdp.backend.common;

import com.gdp.backend.model.GDPUser;

import java.util.Optional;

public class Constants {

    // SUCCESS MESSAGE
    public static final String SUCCESS = "Success";

    public static final  String OR ="OR ";
    public static final String RECORD_ADDED_SUCCESSFULLY = "RECORD_ADDED_SUCCESSFULLY";
    public static final String RECORD_UPDATED_SUCCESSFULLY = "RECORD_UPDATED_SUCCESSFULLY";
    // ERROR MESSAGE
    public static final String INTERNAL_SERVER_ERROR = "UNABLE_TO_PROCESS_REQUEST";
    public static final String RECORD_NOT_FOUND = "No records found!";
    public static final String PROJECT_NAME_EXISTS = "Project name already present";
    public static final String CLIENT_NOT_IN_REGION = "Client is not in selected Opco";
    public static final String BAD_REQUEST = "Please enter value to search!";
    // VALUES
    public static final String DEFAULT_PROJECT_STATUS = "";
    public static final String SEPARATOR = ",";
    public static final int ALL_PROJECTS = 2;
    public static final int DEFAULT_ID = -1;
    public static final String AND_CLAUSE = " AND ";
    public static final String ENGAGEMENT_STATUS = "P.Active = ";
    public static final String CLIENT_ID = "C.Id IN (";
    public static final String DELIVERY_ORGANIZATION_ID = "D.Id IN (";
    public static final String CSL_ID = "R.Id = ";
    public static final String OPCO_STATUS = "C.OpcoID=";
    public static final String VALUE_FOR_IS_ACTIVE = "1";
    public static final String VALUE_FOR_IS_NOT_ACTIVE = "0";
    public static final String R_ACTIVE= "R.Active= ";
    public static final String ORDER_BY_EMPLOYEE=" ORDER BY EmployeeName;";
    public static final String SPACE=" ";

    public static final String IS_ACTIVE="Active = 1";



    public static String RESOURCE_OPCO_ID = "";

    public static String ENGAGEMENT_OPCO_ID = "1";
    public static String AND_CLAUS_OPCOID = "AND C.OpcoId= ";

    public static String AND_CLAUS_ROPCOID = "AND R.OpcoId= ";

    public static String OPCO_ID = "AND OpcoId= ";

    public static Optional<GDPUser> USER;
    public static final String GDP_ID = "P.Id = ";

    public static final String LOCATION_ID = "P.Id IN( SELECT PLM.ProjectId FROM ProjectLocationMapper PLM WHERE PLM.LocationId IN ( ";
    public static final String DELIVERY_MODEL_ID = "DM.Id IN (";
    public static final String GDM_ID = "gdm.Id = ";
    public static final String BU_ID = "BU.Id IN ( ";
    public static final String CLIENT_NAME_FRONTEND_REFERENCE = "accountName";
    public static final String STATUS_FRONTEND_REFERENCE = "status";
    public static final String CSL_NAME_FRONTEND_REFERENCE = "csl";
    public static final String STATUS_DATE_FRONTEND_REFERENCE = "statusDate";
    public static final String ORDER_BY_CLAUSE = " ORDER BY ";
    public static final String CLIENT_NAME = "C.ClientName ";
    public static final String PROJECT_STATUS = "PS.OverAllStatus ";
    public static final String RESOURCE_FIRST_NAME = "R.FirstName ";

    public static final String FIRST_NAME = "FirstName ";
    public static final String RESOURCE_CSL_NAME = "CSL ";
    public static final String WEEK_ENDING_DATE = "PS.WeekEndingDate ";
    public static final String PROJECT_NAME = "P.ProjectName ";
    public static final String GDP_ID_PADDING = "0000000000";
    public static final String RESOURCE_LAST_NAME = "R.lastName ";

    public static final String LAST_NAME = "LastName ";
    public static final String RESOURCE_USER_ID = "R.UserId ";
    public static final String RESOURCE_OPCO = "R.OpcoId=";
    public static final String LIKE = "LIKE '";
    public static final String LIKE_OPERATOR = "%";
    public static final String PATTERN = "%' ";
    public static final String NEW_PROJECT_MAIL_SUBJECT = "**New Engagement Created in Global Delivery Portal**";
    public static final String ADD_OPPORTUNITY_MAIL_SUBJECT = "**Won Opportunity attached to existing Project in GS Delivery Portal**";
    public static final String MAIL_CREATED_BY = "Global Delivery Portal";
    public static final String NOT_APPLICABLE = "NA";
    public static final String NEW_PROJECT = "New Project";
    public static final String ADD_OPPORTUNITY = "Add Opportunity";
    public static final String RISK_SURVEY = "Risk Survey";
    public static final String SECURITY_PROFILE = "Security Profile";
    public static final String ENGAGEMENT_MANAGER = "Engagement Manager";
    public static final String SECURITY_PROFILE_MAIL_SUBJECT = "**Security Profile Updated**";
    public static final String FLEXIBLE_CAPACITY = "Flexible Capacity";
    public static final String DELIVERY_LEADER = "Delivery Leader";
    public static final String NAME_CLAUSE = "Name ";
    public static final String OPCO_CLAUSE="OpcoId ";
    public static final String FROM_CLAUSE = "FROM ";
    public static final String WHERE_CLAUSE = " WHERE ";
    public static final String DELETED = "Deleted";
    public static final String EQUALS = " = ";
    public static final String FALSE = "0";
    public static final String PASS_DUE_SECURITY_QUESTION = "There have been no updates to security profile since 60 days";
    public static final String PASS_DUE_SECURITY_ANSWER_UPDATED_FROM = "Dummy";
    public static final String PASS_DUE_SECURITY_ANSWER_UPDATED_TO = "Dummy";
    public static final String DELIVERY_MILESTONE = "Delivery";
    public static final String AGILE_SPRINT_MILESTONE = "Agile Sprint";
    public static final String ACTIVE = "Active";
    public static final String TRUE = "1";
    public static final String UPDATED_NAME_PATTERN = "Updated %s Name";
    public static final String OLD_NAME_PATTERN = "Old %s Name";
    public static final String ACTION = "Action";
    public static final String UPDATED_BY = "Updated By";
    public static final String UPDATED_DATE = "Updated Date";
    public static final String SERVICE_TYPE = "Service Type";
    public static final String DELIVERY_MODEL = "Delivery Model";
    public static final String STAFFING_SALES_REGION = "Staffing Sales Region";
    public static final String DELIVERY_ORGANIZATION = "Delivery Organization";
    public static final String LOCATION_OF_SERVICES = "Location of Delivery";
    public static final String CONTRACT_TYPE = "Contract Type";
    public static final String SALES_ORGANIZATION = "Sales Organization";
    public static final String MILESTONE_TYPE = "Milestone Type";
    public static final String UPDATED = "Updated";
    public static final String ACCOUNT = "Account";
    public static final String CLOSED = "Closed";
    public static final String DELIVERY_MILESTONE_ONLY_ACTIVE_STATUS = " AND DM.Status= 'Active' ";
    public static final String AGILE_MILESTONE_ONLY_ACTIVE_STATUS = " AND ASM.SprintStatus = 'Active' ";

    public static final String BASE_PACKAGE = "com.gdp.backend.model.";


    private Constants() {
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String HEADER_TOKEN_STRING = "apikey";

    public static final String USER_NAME = "preferred_username";
    public static final String BODY = "body";
    public static final String HELP_GUIDE_FILENAME = "GDPUserGuide";

}
