package com.gdp.backend.common;

public class Queries {


    public static final String PROJECT_STATUS_DEFAULT_QUERY = "Select\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') CSL,\n" +
            "P.Id,\n" +
            "P.ProjectName ,\n" +
            "C.ClientName ,\n" +
            "PS.WeekEndingDate ,\n" +
            "PS.OverAllStatus ,\n" +
            "PS.Summary ,\n" +
            "P.GDPId,\n" +
            "PS.DummyRecord,\n" +
            "P.StartDate,\n" +
            "P.EndDate,\n" +
            "C.OpcoId,\n"+
            "STUFF((SELECT DISTINCT '/ ' +COALESCE(L.LocationName, '')\n"+
            "FROM ProjectLocationMapper PLM\n"+
            "JOIN Location L ON L.Id = PLM.LocationId\n"+
            "WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 1,'') Location,\n"+
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') GDM,\n" +
            "DOT.DeliveryOrganizationTypeName Practice,\n" +
            "BU.BusinessUnitName  BusinessUnit,\n" +
            "PS.CurrentPhase,\n" +
            "P.PeopleSoftProjectId\n" +
            "From\n" +
            "Project P\n" +
            "JOIN\n" +
            "Client C\n" +
            "ON C.Id = P.ClientId\n" +
            "JOIN\n" +
            "ProjectStatus PS ON PS.ProjectId = P.Id\n" +
            "JOIN\n" +
            "(SELECT ProjectStatus.ProjectId, MAX(ProjectStatus.WeekEndingDate) WED\n" +
            "From ProjectStatus WHERE ProjectStatus.Active = 1\n" +
            "Group By ProjectId) Res\n" +
            "ON PS.ProjectId = Res.ProjectId\n" +
            "AND PS.WeekEndingDate = Res.WED\n" +
            "LEFT JOIN DeliveryOrganizationType DOT ON DOT.Id = P.DeliveryOrganizationTypeID\n" +
            "LEFT JOIN BusinessUnit BU ON BU.Id = P.BusinessUnitId\n" +
            "WHERE PS.Active = 1 AND  P.Active = 1 ";
//            +Constants.AND_CLAUS_OPCOID+Constants.ENGAGEMENT_OPCO_ID +"\n" +
//            "ORDER BY P.ProjectName";


    public static final String STAKE_HOLDERS_REPORT_DATA_EXCEL_QUERY = "SELECT P.ProjectName, C.ClientName, P.GDPId, P.PeopleSoftProjectId, P.PeopleSoftEngagementId, DM.DeliveryModelName, DOT.DeliveryOrganizationTypeName," +
            " STUFF((SELECT '/ '+ LOC.LocationName FROM ProjectLocationMapper PLM JOIN Location LOC ON LOC.Id = PLM.LocationId WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') Locations," +
            " STUFF((SELECT ', '+ COALESCE(OP.OpportunityId, '') FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity ID], P.SMPSite, BU.BusinessUnitName, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Program Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') PM, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') GDM, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager'  JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id  FOR XML PATH('')), 1, 2, '') CSL, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Delivery Lead'  JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') DL, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='BDM / AM / SAM' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id  FOR XML PATH('')), 1, 2, '') BDM, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='National Account Owner' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id  FOR XML PATH('')), 1, 2, '') [NATIONAL ACCOUNT OWNER], " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG POA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id  FOR XML PATH('')), 1, 2, '') [OSG POA], " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG BOA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id  FOR XML PATH('')), 1, 2, '') [OSG BOA]," +
            "SSR.SalesOrganizationName,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Account Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') [Account Manager] ,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Sales RD' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') SalesRD,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Solution Architect' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') [Solution Architect],\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Practice Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') PracticeDirector,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Practice Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') PracticeManager,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Solution Executive' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') SolutionExecutive,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Transformation Lead' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') TransformationLead,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Project Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') ProjectManager,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director ' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') GlobalDeliveryDirector,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Project Coordinator' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') ProjectCoordinator,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Training Coordinator' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=1 FOR XML PATH('')), 1, 2, '') TrainingCoordinator,\n" +
            "STUFF((SELECT ', '+OP.OpportunityName FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity Name], P.StartDate, P.EndDate, PS.CurrentPhase, PS.WeekEndingDate, PS.Schedule, PS.CSAT, PS.Budget, PS.ProjectRisk, PS.Resources, PS.OverAllStatus, PRS.RiskIndicator, PRS.ModifiedAt, SPAH.ModifiedAt as mod,P.Active, PS.DummyRecord, PS.ScheduleComments, PS.CSATComments, PS.BudgetComments, PS.ProjectRiskComments, PS.ResourcesComments, PS.Summary\n" +
            "FROM Project P LEFT JOIN Client C on C.Id = P.ClientId LEFT JOIN DeliveryModel DM on DM.Id = P.DeliveryModelId LEFT JOIN BusinessUnit BU on BU.Id = P.BusinessUnitId LEFT JOIN SalesOrganization SSR on SSR.Id = P.SalesOrganizationId LEFT JOIN ProjectStatus PS on PS.Active = 1 AND PS.ProjectId = P.Id AND PS.WeekEndingDate = ( SELECT MAX(WeekEndingDate) FROM ProjectStatus WHERE ProjectId = P.Id AND Active = 1) LEFT JOIN DeliveryOrganizationType DOT on DOT.Id = P.DeliveryOrganizationTypeID LEFT JOIN ProjectRiskSurvey PRS on PRS.ProjectId = P.Id AND PRS.ModifiedAt = ( SELECT MAX(ModifiedAt) FROM ProjectRiskSurvey WHERE ProjectId = P.Id )  LEFT JOIN SecurityProfileAccessHistory SPAH on SPAH.ProjectId=P.Id  AND SPAH.Id=(select max(Id) from securityprofileaccesshistory where projectid= p.id)";


    public static final String FILTERED_PROJECT_STATUS = "Select\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') CSL,\n" +
            "P.Id,\n" +
            "P.ProjectName ,\n" +
            "C.ClientName ,\n" +
            "PS.WeekEndingDate ,\n" +
            "PS.OverAllStatus ,\n" +
            "PS.Summary ,\n" +
            "P.GDPId,\n" +
            "PS.DummyRecord,\n" +
            "P.StartDate,\n" +
            "P.EndDate,\n" +
            "C.OpcoId,\n"+
            "STUFF((SELECT DISTINCT '/ ' +COALESCE(L.LocationName, '')\n"+
            "FROM ProjectLocationMapper PLM\n"+
            "JOIN Location L ON L.Id = PLM.LocationId\n"+
            "WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 1,'') Location,\n"+
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') GDM,\n" +
            "D.DeliveryOrganizationTypeName Practice,\n" +
            "BU.BusinessUnitName  BusinessUnit,\n" +
            "PS.CurrentPhase,\n" +
            "P.PeopleSoftProjectId,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM " +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Program Manager' " +
            "JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') PM, " +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') GDD,\n" +
            "DM.DeliveryModelName as DeliveryModel " +
            "From\n" +
            "Project P\n" +
            "JOIN\n" +
            "Client C\n" +
            "ON C.Id = P.ClientId\n" +
            "JOIN\n" +
            "ProjectStatus PS ON PS.ProjectId = P.Id\n" +
            "JOIN\n" +
            "(SELECT ProjectStatus.ProjectId, MAX(ProjectStatus.WeekEndingDate) WED\n" +
            "From ProjectStatus WHERE ProjectStatus.Active = 1\n" +
            "Group By ProjectId) Res\n" +
            "ON PS.ProjectId = Res.ProjectId\n" +
            "AND PS.WeekEndingDate = Res.WED\n" +
//            "LEFT JOIN (SELECT R.Id, PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager' JOIN Resource R ON R.Id = PRM.ResourceId) R ON R.ProjectId = P.Id\n" +
//            "LEFT JOIN (SELECT gdm.Id, PRM.ProjectId FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource gdm ON gdm.Id = PRM.ResourceId) gdm ON gdm.ProjectId = P.Id\n" +
            "LEFT JOIN DeliveryOrganizationType D ON D.Id = P.DeliveryOrganizationTypeID\n" +
            "LEFT JOIN BusinessUnit BU ON BU.Id = P.BusinessUnitId\n" +
            "LEFT JOIN DeliveryModel DM ON DM.Id = P.DeliveryModelId \n"+
            "WHERE PS.Active = 1  ";
//            +Constants.AND_CLAUS_OPCOID+Constants.ENGAGEMENT_OPCO_ID;


    public static final String OPPORTUNITY_QUERY = "SELECT O.Id, O.PrimaryServiceTypeId, O.DeliveryModelId, O.DeliveryOrganizationTypeId, " +
            "O.OpportunityName, C.ClientName, O.OpportunityId, O.StartDate, O.ClosedDate, OT.OpportunityTypeName, " +
            "P.PrimaryServiceTypeName, DM.DeliveryModelName, D.DeliveryOrganizationTypeName, O.SalesOrganizationId, " +
            "O.LocationId, O.Target_Technology_Platform \n" +
            "FROM Opportunity O \n" +
            "LEFT JOIN Client C ON C.Id = O.ClientId \n" +
            "LEFT JOIN DeliveryOrganizationType D ON D.Id = O.DeliveryOrganizationTypeId \n" +
            "LEFT JOIN OpportunityType OT ON OT.Id = O.GSOpportunityTypeId \n" +
            "LEFT JOIN DeliveryModel DM ON DM.Id = O.DeliveryModelId \n" +
            "LEFT JOIN PrimaryServiceType P ON P.Id = O.PrimaryServiceTypeId \n" +
            "LEFT JOIN Location L ON L.Id = O.LocationId \n" +
            "WHERE O.ClosedDate >= DATEADD(month, -2, GETDATE()) AND (O.HasProject = 0 OR O.HasProject IS NULL) \n" +
            "AND C.OpcoId = ?1 \n" +
            "GROUP BY O.Id, O.PrimaryServiceTypeId, O.DeliveryModelId, O.DeliveryOrganizationTypeId, " +
            "O.OpportunityName, C.ClientName, O.OpportunityId, O.StartDate, O.ClosedDate, OT.OpportunityTypeName, " +
            "P.PrimaryServiceTypeName, DM.DeliveryModelName, D.DeliveryOrganizationTypeName, O.SalesOrganizationId, O.LocationId, O.Target_Technology_Platform \n" +
            "ORDER BY O.OpportunityName";


    public static final String ACCOUNT_DROPDOWN = "SELECT Distinct C.Id, C.ClientName\n" +
            "FROM Client C\n" +

            "INNER JOIN Project P ON C.Id = P.ClientId\n" +

            "WHERE ";



    public static final String GDP_DROPDOWN =
            "SELECT P.Id, P.GDPId " +
                    "FROM Project P, Client C " + 
                    "WHERE C.Id = P.ClientId AND " +  
                    "P.Id IN (SELECT P.Id FROM Project P WHERE ";

    public static final String CSL_DROPDOWN = "SELECT DISTINCT R.Id , R.firstName , R.MiddleName , R.lastName FROM Resource R\n" +
            "JOIN ProjectResourceMapper PRM ON PRM.ResourceId = R.Id\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId\n" +
            "WHERE (RR.ResourceRoleName='Engagement Manager' OR RR.ResourceRoleName='Project Coordinator')\n" +
            "AND PRM.ProjectId IN (SELECT P.Id FROM Project P join client c on p.clientid = c.id ";
    public static final String GDM_DROPDOWN = "SELECT DISTINCT R.Id , R.firstName , R.MiddleName , R.lastName FROM Resource R\n" +
            "JOIN ProjectResourceMapper PRM ON PRM.ResourceId = R.Id\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId\n" +
            "WHERE RR.ResourceRoleName = 'Global Delivery Manager'\n" +
            "AND PRM.ProjectId IN (SELECT P.Id FROM Project P join client c on p.clientid = c.id ";
    public static final String GDD_DROPDOWN = "SELECT DISTINCT R.Id , R.firstName , R.MiddleName , R.lastName FROM Resource R\n" +
            "JOIN ProjectResourceMapper PRM ON PRM.ResourceId = R.Id\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId\n" +
            "WHERE RR.ResourceRoleName = 'Global Delivery Director'\n" +
            "AND PRM.ProjectId IN (SELECT P.Id FROM Project P join client c on p.clientid = c.id ";

    public static final String PROGRAM_MANAGER_DROPDOWN ="SELECT DISTINCT R.Id , R.firstName , R.MiddleName , R.lastName FROM Resource R\n"+
    "JOIN ProjectResourceMapper PRM ON PRM.ResourceId = R.Id\n"+
    "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId\n"+
    "WHERE (RR.ResourceRoleName='Program Manager')\n"+
    "AND PRM.ProjectId IN (SELECT P.Id FROM Project P join client c on p.clientid = c.id ";


    public static final String DELIVERY_MODEL_DROPDOWN = "SELECT dm.Id , dm.DeliveryModelName FROM DeliveryModel dm WHERE dm.Active=1 AND dm.Deleted=0 ORDER BY dm.DeliveryModelName";
    public static final String SALES_ORGANIZATION_DROPDOWN = "SELECT so.Id , so.SalesOrganizationName FROM SalesOrganization so WHERE so.Active=1 AND so.Deleted=0 ORDER BY so.SalesOrganizationName";
    public static final String STAFFING_SALES_REGION_DROPDOWN = "SELECT ssr.Id , ssr.StaffingSalesRegionName FROM StaffingSalesRegion ssr WHERE ssr.Active=1 AND ssr.Deleted=0 ORDER BY ssr.StaffingSalesRegionName";
    public static final String BUSINESS_UNIT_DROPDOWN = "SELECT bu.Id , bu.BusinessUnitName FROM BusinessUnit bu WHERE bu.Active=1 AND bu.Deleted=0 ORDER BY bu.BusinessUnitName";
    public static final String PRACTICE_ENGAGEMENT_DROPDOWN = "SELECT dot.Id , dot.DeliveryOrganizationTypeName FROM DeliveryOrganizationType dot WHERE dot.Id IN " +
            "(SELECT P.DeliveryOrganizationTypeID FROM Project P WHERE";
    public static final String PRACTICE_ENGAGEMENT_DROPDOWN_DETAILS_SCREEN = "SELECT dot.Id , dot.DeliveryOrganizationTypeName FROM DeliveryOrganizationType dot WHERE dot.Active = 1 AND dot.Deleted = 0 ORDER BY dot.DeliveryOrganizationTypeName";
    public static final String LOCATION_OF_SERVICES_DROPDOWN = "SELECT l.Id , l.LocationName FROM Location l WHERE l.Active=1 AND l.Deleted=0 ORDER BY l.LocationName";
    public static final String CONTRACT_TYPE_DROPDOWN = "SELECT ct.Id , ct.ContractTypeName FROM ContractType ct WHERE ct.Active=1 AND ct.Deleted=0 ORDER BY ct.ContractTypeName";
    public static final String PRIMARY_SERVICE_TYPE_DROPDOWN = "SELECT pst.Id , pst.PrimaryServiceTypeName FROM PrimaryServiceType pst WHERE pst.Active=1 AND pst.Deleted=0 ORDER BY pst.PrimaryServiceTypeName";
    public static final String RESOURCE_ROLE_DROPDOWN = "SELECT rr.Id , rr.ResourceRoleName, rr.EngagementType FROM ResourceRole rr WHERE rr.Active=1 AND rr.Deleted=0 ORDER BY rr.ResourceRoleName";
    public static final String REPORT_LOG_WITH_PROJECT_ID = "SELECT DISTINCT pah.ModifiedAt, COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy FROM ProjectAccessHistory pah LEFT JOIN Resource R ON pah.ModifiedBy = R.UserId WHERE pah.ProjectId = ?1 ORDER BY pah.ModifiedAt DESC";
    public static final String RISK_SURVEY_REPORT_LOG_WITH_PROJECT_ID = "SELECT DISTINCT\n" +
            "rsa.ModifiedAt,\n" +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy \n" +
            "FROM RiskSurveyAccessHistory rsa\n" +
            "LEFT JOIN Resource R ON R.UserId = rsa.ModifiedBy\n" +
            "WHERE rsa.ProjectId = ?1 ORDER BY rsa.ModifiedAt DESC";
    public static final String LAST_GDP_ID = "SELECT TOP 1 p.GDPId FROM Project p ORDER BY CAST( p.GDPId AS DECIMAL(10) ) DESC";
    public static final String USER_SEARCH = "SELECT R.id , R.firstName , R.MiddleName , R.lastName , R.UserId FROM Resource R WHERE R.Active=1 AND R.Deleted=0 AND ";
    public static final String RESOURCES_WITH_PROJECT_ID = "SELECT DISTINCT R.Id , R.firstName , R.MiddleName , R.lastName , PRM.ResourceRoleId , RR.ResourceRoleName, PRM.isOtherTeamMember FROM ProjectResourceMapper PRM\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId \n" +
            "JOIN Project P ON P.Id = PRM.ProjectId \n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "WHERE P.Id = ?1 ORDER BY PRM.isOtherTeamMember";
    public static final String ATTACH_OPPORTUNITY_DETAILS = "Select DISTINCT P.ClientId, P.Id, P.ProjectName, C.ClientName From Project P\n" +
            "JOIN Client C ON C.Id = P.ClientId WHERE P.Active = 1 ORDER BY C.ClientName ";
    public static final String RISK_SURVEY_BY_PROJECT_ID = "SELECT Prs.ProjectId , P.ProjectName , C.ClientName, Prs.RiskIndicator, Prs.IsWarrantyApplicable, Prs.LiabilityLimits\n" +
            "  , Prs.ContractType , Prs.CustomerCreditScore , Prs.SensitiveData , Prs.IsComplianceAssociated\n" +
            "  , Prs.HardwareAndEquipment , Prs.IsCovered , Prs.Location , Prs.TypeOfService , Prs.ContractualDuration \n" +
            "  , Prs.IsEstablishedAccount , Prs.TotalProjectedRevenue , Prs.EngagementWithTek , Prs.Is3rdPartyDependent \n" +
            "  , Prs.HasUniqueChallenges , Prs.HighRiskCategory , Prs.Comments \n" +
            "  FROM ProjectRiskSurvey Prs JOIN Project P ON P.Id = Prs.ProjectId JOIN Client C ON C.Id = P.ClientId\n" +
            "  WHERE P.Id = ?1";
    public static final String PROJECT_AND_CLIENT_NAME_BY_PROJECT_ID = "SELECT P.ProjectName , C.ClientName FROM Project P JOIN Client C \n" +
            "ON C.Id = P.ClientId WHERE P.Id = ?1";
    public static final String SECURITY_PROFILE_BY_PROJECT_ID = "SELECT sq.Position, sq.Question, psp.Comments, sa.Answer, psp.SecurityAnswerId, sa.MandatoryComment , psp.Selected\n" +
            "  FROM ProjectSecurityProfile psp JOIN \n" +
            "  SecurityAnswer sa ON sa.Id = psp.SecurityAnswerId \n" +
            "  JOIN SecurityQuestions sq ON sq.Id = sa.SecurityQuestionId \n" +
            "  WHERE psp.ProjectId = ? ";
    public static final String ALL_ANSWERS_FOR_SECURITY_PROFILE = "SELECT sq.Position, sq.Question, sa.Answer, sa.Id , sa.MandatoryComment, sq.Multiselect\n" +
            "  FROM SecurityAnswer sa \n" +
            "  JOIN SecurityQuestions sq ON sq.Id = sa.SecurityQuestionId";
    public static final String SECURITY_PROFILE_REPORT_LOG_BY_PROJECT_ID = "SELECT spah.Question,\n" +
            "spah.Answer,\n" +
            "spah.UpdatedAnswer,\n" +
            "spah.ModifiedAt,\n" +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy \n" +
            "FROM SecurityProfileAccessHistory spah \n" +
            "LEFT JOIN Resource R ON R.UserId = spah.ModifiedBy\n" +
            "WHERE spah.ProjectId = ?\n" +
            "ORDER BY spah.ModifiedAt DESC";
    public static final String SECURITY_PROFILE_HEADER = "SELECT R.FirstName, R.MiddleName, R.LastName, RR.ResourceRoleName, \n" +
            "P.ProjectName, C.ClientName FROM Project P \n" +
            "  JOIN Client C ON C.id = P.ClientId\n" +
            "  JOIN ProjectResourceMapper PRM ON PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 \n" +
            "  JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "  JOIN Resource R ON R.Id = PRM.ResourceId\n" +
            "  WHERE P.Id = ? AND \n" +
            "  (RR.ResourceRoleName ='Engagement Manager' OR RR.ResourceRoleName ='Global Delivery Manager')";
    public static final String RISK_INDICATOR_BY_PROJECT_ID = "SELECT prs.RiskIndicator FROM ProjectRiskSurvey prs WHERE prs.ProjectId = ?";
    public static final String SECURITY_PROFILE_STATUS_BY_PROJECT_ID = "SELECT TOP 1 psp.ModifiedAt FROM ProjectSecurityProfile psp \n" +
            "  WHERE psp.ProjectId = ? ORDER BY psp.ModifiedAt DESC";
    public static final String SECURITY_PROFILE_ANSWERS_AND_COMMENTS_BY_PROJECT_ID = " SELECT sq.Position, sa.Answer , psp.Comments FROM ProjectSecurityProfile psp \n" +
            "  JOIN SecurityAnswer sa ON sa.Id = psp.SecurityAnswerId\n" +
            "  JOIN SecurityQuestions sq ON sq.Id = sa.SecurityQuestionId \n" +
            "  WHERE psp.ProjectId = ? AND psp.Selected = 1 ORDER BY sq.Position";
    public static final String STATUS_REPORT_BY_PROJECT_ID = "SELECT ps.SecurityProfileStatus, ps.DeliveryRiskIndicator,\n" +
            "ps.WeekEndingDate, ps.CurrentPhase , ps.Schedule , ps.CSAT , ps.Budget , ps.ProjectRisk,\n" +
            "ps.Resources , ps.OverAllStatus , ps.Summary , ps.ScheduleComments , ps.CSATComments,\n" +
            "ps.BudgetComments , ps.ProjectRiskComments , ps.ResourcesComments,\n" +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy ,\n" +
            "ps.ModifiedAt\n" +
            "FROM ProjectStatus ps\n" +
            "LEFT JOIN Resource R on R.UserId = ps.ModifiedBy\n" +
            "WHERE ps.ProjectId = ? AND ps.Active = 1 AND (ps.DummyRecord = 0 OR ps.DummyRecord IS NULL) ORDER BY ps.WeekEndingDate DESC";
    public static final String GET_DELIVERY_MILESTONES =
            "SELECT dm.Description , dm.Type, dm.StartDate , dm.EndDate , dm.Status ,\n" +
                    "dm.Comments,\n" +
                    "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy,\n" +
                    "dm.ModifiedAt, dm.WeekEndingDate , dm.Id, dm.Active, dm.DeliveryMilestoneTypeId\n" +
                    "FROM DeliveryMilestones dm\n" +
                    "LEFT JOIN Resource R ON R.UserId = dm.ModifiedBy\n" +
                    "WHERE dm.ProjectId = ? AND dm.Active = 1";
    public static final String GET_AGILE_SPRINT_MILESTONES =
            "SELECT asm.Track , asm.Sprint , asm.StartDate , asm.EndDate , asm.DeliveredStories ,\n" +
                    "asm.CommittedStoryPoints , asm.DeliveredStoryPoints , asm.AddedStories , asm.RemovedStories ,\n" +
                    "asm.Capacity , asm.Estimate , asm.SprintStatus ,\n" +
                    "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') ModifiedBy,\n" +
                    "asm.ModifiedAt , asm.WeekEndingDate , asm.Id, asm.Active, asm.GoalMet \n" +
                    "FROM AgileSprintMilestones asm\n" +
                    "LEFT JOIN Resource R ON R.UserId = asm.ModifiedBy\n" +
                    "WHERE asm.ProjectId = ? AND asm.Active = 1 ";
    public static final String GET_ALL_ANSWER_QUESTIONS_SECURITY_PROFILE =
            "SELECT sa.Id, sa.Answer ,sq.Question FROM SecurityAnswer sa JOIN SecurityQuestions sq ON sq.Id = sa.SecurityQuestionId \n" +
                    "ORDER BY sa.Id ";
    public static final String DELIVERY_HOURS_BY_PROJECT_ID = "SELECT DISTINCT dh.Id,r.FirstName, r.MiddleName, r.LastName, " +
            "dh.Hours,  dh.WeekEndingDate, r.Id ResourceId\n" +
            "FROM DeliveryHours dh  \n" +
            "JOIN Project p ON p.Id = dh.ProjectId \n" +
            "JOIN Resource r ON r.Id = dh.ResourceId \n" +
            "WHERE dh.ProjectId = ?";

    public static final String DELIVERY_HOURS_BY_PROJECT_ID_WED = "SELECT DISTINCT R.FirstName, R.MiddleName, R.LastName,\n" +
            "DH.Hours, DH.Id, DH.WeekEndingDate, PRM.ResourceId, (SELECT TOP 1 ResourceRoleName FROM ResourceRole WHERE Id = PRM.ResourceRoleId) ResourceRole \n" +
            "FROM ProjectResourceMapper PRM\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId\n" +
            "LEFT JOIN DeliveryHours DH ON DH.ProjectId = PRM.ProjectId AND DH.ResourceId = R.Id AND WeekEndingDate = ?2\n" +
            "WHERE PRM.ProjectId = ?1 AND PRM.ResourceRoleId IN (SELECT Id FROM ResourceRole WHERE ResourceRoleName IN ('Engagement Manager', 'Delivery Leader', 'Delivery Lead'))";

    public static final String DELIVERY_LEADERS_FOR_FLEX_IT_BY_PROJECT_ID = "SELECT DISTINCT R.Id ResourceId, R.FirstName,R.MiddleName, R.LastName,\n" +
            "(SELECT TOP 1 Hours FROM DeliveryHours WHERE ResourceId = R.Id AND ProjectId = PRM.ProjectId ORDER BY WeekEndingDate DESC) hours\n" +
            "FROM Resource R\n" +
            "JOIN ProjectResourceMapper PRM ON R.Id = PRM.ResourceId\n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId\n" +
            "Where (RR.ResourceRoleName = 'Delivery Leader' OR RR.ResourceRoleName = 'Engagement Manager' OR RR.ResourceRoleName = 'Delivery Lead')\n" +
            "AND PRM.ProjectId = ?";
    public static final String ADMIN_QUERY_SELECT_ID_ACTIVE = "SELECT Id, Active, ";
    public static final String UPDATE = "UPDATE ";
    public static final String SET_DELETED_WHERE = " SET Deleted = 1, Action = 'Deleted' WHERE Id = ?";
    public static final String SET = " SET ";
    public static final String NAME_ACTIVE = "Name = ? , Active = ?, Action = 'Updated', ModifiedBy = ?, ModifiedAt = CURRENT_TIMESTAMP, PreviousName = ";
    public static final String WHERE = "Where Id = ?";

    public static final String ADMIN_MANAGE_ACCESS_GET_QUERY = "Select R.Id ResourceId, R.EmployeeId, R.Access, " +
            "R.JobCode, R.HireDate, R.Active, R.UserId, " +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') EmployeeName, " +
            "COALESCE(S.LastName+',','' ) + S.Firstname + ' ' + COALESCE(S.MiddleName+ '','') SupervisorName, " +
            "RL.LocationName, D.DesignationName,  R.TerminationDate, O.OpcoName, R.ResourceLocationId  FROM Resource R \n" +
            "LEFT JOIN ResourceLocation RL ON R.ResourceLocationId = RL.Id \n" +
            "LEFT JOIN Resource S ON R.SupervisorId = S.Id\n" +
            "LEFT JOIN Designation D ON R.DesignationId = D.Id\n" +
            "LEFT JOIN Opco O ON R.OpcoId = O.Id\n";


    public static final String ADMIN_MANAGE_ACCESS_SEARCH_QUERY = "Select R.Id ResourceId, R.EmployeeId, R.Access, " +
            "R.JobCode, R.HireDate, R.Active, R.UserId, " +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') EmployeeName, " +
            "COALESCE(S.LastName+',','' ) + S.Firstname + ' ' + COALESCE(S.MiddleName+ '','') SupervisorName, " +
            "RL.LocationName, D.DesignationName, R.TerminationDate, O.OpcoName,  R.ResourceLocationId FROM Resource R \n" +
            "LEFT JOIN ResourceLocation RL ON R.ResourceLocationId = RL.Id \n" +
            "LEFT JOIN Resource S ON R.SupervisorId = S.Id\n" +
            "LEFT JOIN Designation D ON R.DesignationId = D.Id\n" +
            "LEFT JOIN Opco O ON R.OpcoId = O.Id\n"+
            "Where (R.FirstName LIKE CONCAT('%',?,'%') OR R.LastName LIKE CONCAT('%',?,'%') OR R.UserId LIKE CONCAT('%',?,'%') ) \n" +
            "AND ";

    public static final String ADMIN_MANAGE_ACCESS_COUNT_QUERY = "Select COUNT(*) FROM Resource R " +
            "LEFT JOIN ResourceLocation RL ON R.ResourceLocationId = RL.Id LEFT JOIN Resource S ON R.SupervisorId = S.Id LEFT JOIN Designation D ON R.DesignationId = D.Id " +
            "Where R.Active = ";

    public static final String GET_ACTIVE_DESIGNATIONS_QUERY = "SELECT Id, DesignationName from Designation\n" +
            "WHERE Active = 1 AND Deleted = 0";

    public static final String SEARCH_ACTIVE_RESOURCES_QUERY = "Select Id, EmployeeId, UserId, \n" +
            "COALESCE(LastName+',','' ) + FirstName + COALESCE(MiddleName+ '','') EmployeeName, \n" +
            "FirstName, MiddleName, LastName,OpcoId\n" +
            "FROM Resource \n" ;

    public static final String ADMIN_FETCH_EXCEL_DATA_QUERY = "SELECT %sName , PreviousName, Action, ModifiedBy, CreatedBy, ModifiedAt  FROM %s";

    public static final String REPORT_DATA_EXCEL_QUERY = "SELECT P.ProjectName, C.ClientName, P.GDPId, P.PeopleSoftProjectId, P.PeopleSoftEngagementId, DM.DeliveryModelName, \n" +
            "DOT.DeliveryOrganizationTypeName, \n" +
            "STUFF((SELECT '/ '+ LOC.LocationName FROM ProjectLocationMapper PLM JOIN Location LOC ON LOC.Id = PLM.LocationId WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') Locations, \n" +
            "STUFF((SELECT ', '+ COALESCE(OP.OpportunityId, '') FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity ID], \n" +
            "P.SMPSite, BU.BusinessUnitName,\n" +
            "STUFF((SELECT DISTINCT ', ' + PST.PrimaryServiceTypeName FROM PrimaryServiceType PST JOIN ProjectPrimaryServiceTypeMapper PPST ON PST.Id=PPST.PrimaryServiceTypeId WHERE PPST.ProjectId=P.Id FOR XML PATH('')), 1, 2, '') [Service Type],"+
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDM, \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Program Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 ), 1, 2, '') PM, \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDD, \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') CSL, \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='BDM / AM / SAM' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') BDM, \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='National Account Owner' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [NATIONAL ACCOUNT OWNER], \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG POA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG POA], \n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG BOA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG BOA], \n" +
            "SSR.SalesOrganizationName, \n" +
            "STUFF((SELECT ', '+ OP.OpportunityName FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity Name], \n" +
            "P.StartDate, P.EndDate, PS.CurrentPhase, PS.WeekEndingDate, PS.Schedule, PS.CSAT, PS.Budget, PS.ProjectRisk, PS.Resources, PS.OverAllStatus, PRS.RiskIndicator, PRS.ModifiedAt,SPAH.ModifiedAt as mod, P.Active, PS.DummyRecord, PS.ScheduleComments, PS.CSATComments, PS.BudgetComments, PS.ProjectRiskComments, PS.ResourcesComments, PS.Summary, \n" +
            "CASE WHEN P.Target_Technology_Platform IS NOT NULL THEN P.Target_Technology_Platform  ELSE '' END AS [Target Technology Platform]" +
            "FROM Project P \n" +
            "LEFT JOIN Client C on C.Id = P.ClientId \n" +
            "LEFT JOIN DeliveryModel DM on DM.Id = P.DeliveryModelId \n" +
            "LEFT JOIN BusinessUnit BU on BU.Id = P.BusinessUnitId \n" +
            "LEFT JOIN SalesOrganization SSR on SSR.Id = P.SalesOrganizationId \n" +
            "LEFT JOIN ProjectStatus PS on PS.Active = 1 AND PS.ProjectId = P.Id AND PS.WeekEndingDate = ( SELECT MAX(WeekEndingDate) FROM ProjectStatus WHERE ProjectId = P.Id AND Active = 1) \n" +
            "LEFT JOIN DeliveryOrganizationType DOT on DOT.Id = P.DeliveryOrganizationTypeID \n" +
            "LEFT JOIN ProjectRiskSurvey PRS on PRS.ProjectId = P.Id \n" +
            "AND PRS.ModifiedAt = ( SELECT MAX(ModifiedAt) FROM ProjectRiskSurvey WHERE ProjectId = P.Id ) \n" +
            // "LEFT JOIN ProjectSecurityProfile PSP on PSP.ProjectId = P.Id and psp.id = (select max(id) from projectsecurityprofile where projectid= p.id) AND PSP.ModifiedAt = (SELECT MAX(ModifiedAt) FROM ProjectSecurityProfile WHERE id = (select max(id) from projectsecurityprofile where projectid= p.id))";
            "LEFT JOIN SecurityProfileAccessHistory SPAH on SPAH.ProjectId=P.Id  AND SPAH.Id=(select max(Id) from securityprofileaccesshistory where projectid= p.id)";

    public static final String MILESTONE_DELIVERY_REPORT_EXCEL_QUERY = "SELECT\n" +
            "P.ProjectName,\n" +
            "C.ClientName,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDD, \n" +
            "P.GDPId,\n" +
            "(SELECT DeliveryMilestoneTypeName FROM DeliveryMilestoneType WHERE Id = DM.DeliveryMilestoneTypeId),\n" +
            "DM.Description,\n" +
            "DM.StartDate,\n" +
            "DM.EndDate,\n" +
            "DM.Comments,\n" +
            "DM.Status\n" +
            "FROM Project P\n" +
            "LEFT JOIN Client C on C.Id = P.ClientId\n" +
            "LEFT JOIN DeliveryMilestones DM on DM.ProjectId = P.Id AND DM.Active = 1 AND DM.Deleted = 0 AND DM.WeekEndingDate = (SELECT MAX(PS.WeekEndingDate) FROM ProjectStatus PS WHERE PS.ProjectId = P.Id AND PS.Active = 1) ";

    public static final String MILESTONE_AGILE_SPRINT_REPORT_EXCEL_QUERY = "SELECT\n" +
            "P.ProjectName,\n" +
            "C.ClientName,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDD, \n" +
            "P.GDPId,\n" +
            "ASM.Track,\n" +
            "ASM.Sprint,\n" +
            "ASM.StartDate,\n" +
            "ASM.EndDate,\n" +
            "ASM.DeliveredStories,\n" +
            "ASM.CommittedStoryPoints,\n" +
            "ASM.DeliveredStoryPoints,\n" +
            "ASM.AddedStories,\n" +
            "ASM.RemovedStories,\n" +
            "ASM.Capacity,\n" +
            "ASM.Estimate,\n" +
            "ASM.SprintStatus,\n" +
            "P.Active,\n" +
            "ASM.GoalMet\n" +
            "FROM Project P\n" +
            "LEFT JOIN Client C on C.Id = P.ClientId\n" +
            "LEFT JOIN AgileSprintMilestones ASM on ASM.ProjectId = P.Id AND ASM.Active=1 AND ASM.Deleted = 0 AND ASM.WeekEndingDate = (SELECT MAX(PS.WeekEndingDate) FROM ProjectStatus PS WHERE PS.ProjectId = P.Id AND Active = 1) ";

    public final static String TGS_IS_SECURITY_PROFILE_REPORT_QUERY = "SELECT\n" +
            "P.ProjectName,\n" +
            "C.ClientName,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM \n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 1, '') CSL,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM \n" +
            "JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director'\n" +
            "JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 1, '') GDD,\n" +
            "DOT.DeliveryOrganizationTypeName,\n" +
            "STUFF((SELECT '/ '+ LOC.LocationName FROM ProjectLocationMapper PLM\n" +
            "JOIN Location LOC ON LOC.Id = PLM.LocationId WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') Locations,\n" +
            "P.SMPSite,\n" +
            "STUFF((SELECT ', '+ OP.OpportunityId FROM ProjectOpportunityMapper POM\n" +
            "JOIN Opportunity OP ON OP.Id = POM.OpportunityId\n" +
            "WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 1, '') [Opportunity ID],\n" +
            "P.GDPId,\n" +
            "P.EndDate,\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=1)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 1],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=2)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 2],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=3)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 3],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=4)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 4],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=5)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 5],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=6)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 6],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=7)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 7],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=8)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 8],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=9)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 9],\n" +
            "STUFF((SELECT ', '+ SA.Answer FROM ProjectSecurityProfile PSP\n" +
            "LEFT JOIN SecurityAnswer SA ON SA.Id = PSP.SecurityAnswerId AND SA.SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Position=10)\n" +
            "WHERE PSP.ProjectId = P.Id AND Selected = 1 FOR XML PATH('')), 1, 1, '') [Answer 10],\n" +
            "t.ModifiedAt [Security Profile Date],\n" +
            "COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') [Last Updated User]\n" +
            "FROM Project P\n" +
            "LEFT JOIN Client C on C.Id = P.ClientId\n" +
            "LEFT JOIN DeliveryOrganizationType DOT on DOT.Id = P.DeliveryOrganizationTypeId\n" +
            "LEFT JOIN  (SELECT * FROM (SELECT  ProjectId,ModifiedAt,ModifiedBy FROM    (\n" +
            "SELECT  *, ROW_NUMBER() OVER (PARTITION BY ProjectId ORDER BY ModifiedAt DESC) rn\n" +
            "FROM SecurityProfileAccessHistory) x WHERE   x.rn = 1 ) as TempTable) t ON t.ProjectId=P.Id LEFT JOIN Resource R ON R.UserId = t.ModifiedBy";

    public static final String PMLC_SCORECARD_REPORT_QUERY = "SELECT \n" +
            "  C.ClientName, \n" +
            "  P.ProjectName, \n" +
            "  (\n" +
            "    SELECT \n" +
            "      TOP 1 RiskIndicator \n" +
            "    from \n" +
            "      ProjectRiskSurvey \n" +
            "    WHERE \n" +
            "      ProjectId = P.Id\n" +
            "  ) RiskIndicator, \n" +
            "  P.SMPSite, \n" +
            "  STUFF(\n" +
            "    (\n" +
            "      SELECT DISTINCT \n" +
            "        '/ ' + COALESCE(R.LastName + ',', '') + R.Firstname + ' ' + COALESCE(R.MiddleName + '', '') \n" +
            "      FROM \n" +
            "        ProjectResourceMapper PRM \n" +
            "        JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "        AND RR.ResourceRoleName = 'Global Delivery Manager' \n" +
            "        JOIN Resource R ON R.Id = PRM.ResourceId \n" +
            "      WHERE \n" +
            "        PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')\n" +
            "    ), \n" +
            "    1, \n" +
            "    1, \n" +
            "    ''\n" +
            "  ) GDM, \n" +
            "  STUFF(\n" +
            "    (\n" +
            "      SELECT DISTINCT \n" +
            "        '/ ' + COALESCE(R.LastName + ',', '') + R.Firstname + ' ' + COALESCE(R.MiddleName + '', '') \n" +
            "      FROM \n" +
            "        ProjectResourceMapper PRM \n" +
            "        JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "        AND RR.ResourceRoleName = 'Engagement Manager' \n" +
            "        JOIN Resource R ON R.Id = PRM.ResourceId \n" +
            "      WHERE \n" +
            "        PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')\n" +
            "    ), \n" +
            "    1, \n" +
            "    1, \n" +
            "    ''\n" +
            "  ) CSL, \n" +
            "  STUFF(\n" +
            "    (\n" +
            "      SELECT DISTINCT \n" +
            "        '/ ' + COALESCE(R.LastName + ',', '') + R.Firstname + ' ' + COALESCE(R.MiddleName + '', '') \n" +
            "      FROM \n" +
            "        ProjectResourceMapper PRM \n" +
            "        JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "        AND RR.ResourceRoleName = 'Global Delivery Director' \n" +
            "        JOIN Resource R ON R.Id = PRM.ResourceId \n" +
            "      WHERE \n" +
            "        PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')\n" +
            "    ), \n" +
            "    1, \n" +
            "    1, \n" +
            "    ''\n" +
            "  ) GDD, \n" +
            "  STUFF(\n" +
            "    (\n" +
            "      SELECT DISTINCT \n" +
            "        '/ ' + COALESCE(R.LastName + ',', '') + R.Firstname + ' ' + COALESCE(R.MiddleName + '', '') \n" +
            "      FROM \n" +
            "        ProjectResourceMapper PRM \n" +
            "        JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId \n" +
            "        AND RR.ResourceRoleName = 'Project Manager' \n" +
            "        JOIN Resource R ON R.Id = PRM.ResourceId \n" +
            "      WHERE \n" +
            "        PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')\n" +
            "    ), \n" +
            "    1, \n" +
            "    1, \n" +
            "    ''\n" +
            "  ) PM, \n" +
            "  PS.CurrentPhase, \n" +
            "  P.StartDate, \n" +
            "  P.EndDate, \n" +
            "  PS.WeekEndingDate,\n" +
            "  PS.Schedule, \n" +
            "  PS.CSAT, \n" +
            "  PS.Budget, \n" +
            "  PS.ProjectRisk, \n" +
            "  PS.Resources, \n" +
            "  PS.OverAllStatus, \n" +
            "  DM.DeliveryModelName, \n" +
            "  DOT.DeliveryOrganizationTypeName, P.GDPId, PS.DummyRecord\n" +
            "FROM \n" +
            "  Project P \n" +
            "  LEFT JOIN Client C on C.Id = P.ClientId \n" +
            "  LEFT JOIN DeliveryModel DM on DM.Id = P.DeliveryModelId \n" +
            "  LEFT JOIN ProjectStatus PS on PS.ProjectId = P.Id\n" +
            "  AND PS.Active = 1 \n" +
            "  LEFT JOIN DeliveryOrganizationType DOT on DOT.Id = P.DeliveryOrganizationTypeID\n" +
            "JOIN\n" +
            "(SELECT ProjectStatus.ProjectId, MAX(ProjectStatus.WeekEndingDate) WED\n" +
            "From ProjectStatus WHERE ProjectStatus.Active = 1\n" +
            "Group By ProjectId) Res\n" +
            "ON PS.ProjectId = Res.ProjectId\n" +
            "AND PS.WeekEndingDate = Res.WED\n" +
            "WHERE PS.CurrentPhase != 'Closed'";

    public static final String FIND_FULLNAME_FROM_USER_ID = "SELECT TOP 1 COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM Resource R WHERE R.UserId = ?1 AND R.Active = 1";

    public static final String MODIFIED_BY_LIST_OF_CSL_RISK_SURVEY = "SELECT COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') UpdatedBy," +
            " STUFF((SELECT DISTINCT ','+COALESCE(R.UserId+'@teksystems.com',',' ) FROM ProjectResourceMapper PRM" +
            " JOIN Resource R ON R.Id = PRM.ResourceId JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId" +
            " WHERE PRM.ProjectId = PSP.ProjectId AND RR.ResourceRoleName = 'Engagement Manager' FOR XML PATH('')), 1, 1, '') Csl FROM ProjectSecurityProfile PSP" +
            " JOIN Resource R ON R.UserId = PSP.ModifiedBy WHERE PSP.ProjectId = ?1 ORDER BY PSP.ModifiedAt offset 0 rows fetch next 1 rows only;";

    public static final String BUSINESS_UNIT_FILTER_DROPDOWN = "SELECT BU.Id, BU.BusinessUnitName FROM BusinessUnit BU WHERE BU.Id IN\n" +
            "(SELECT DISTINCT BusinessUnitId FROM Project P WHERE ";

    public static final String PS_PID_DROPDOWN = "SELECT P.Id , P.PeopleSoftProjectId FROM Project P WHERE P.PeopleSoftProjectId IS NOT NULL AND P.Id IN (SELECT P.Id FROM Project P WHERE ";

    public static final String MARK_AS_OTHER_TEAM_MEMBER = "UPDATE ProjectResourceMapper SET isOtherTeamMember = 1 WHERE ProjectId = ?1 AND ResourceId=?2 AND ResourceRoleId=?3";

    public static final String DELETE_RESOURCE_MAPPER_BY_PROJECT_ID = "DELETE FROM ProjectResourceMapper WHERE ProjectId = ?1";

    public static final String FIND_LAST_DH_FOR_RESOURCE = "SELECT TOP 1 DH.Hours FROM DeliveryHours DH WHERE DH.ProjectId = ?1 AND DH.ResourceId = ?2 ORDER BY WeekEndingDate DESC";

    public static final String ON_STATUS_REPORT_DELETE_DELIVERY_MILESTONES = "UPDATE DeliveryMilestones SET Active = 0 WHERE ProjectId = ?1 AND WeekEndingDate = ?2";
    public static final String ON_STATUS_REPORT_DELETE_AGILE_MILESTONES = "UPDATE AgileSprintMilestones SET Active = 0 WHERE ProjectId = ?1 AND WeekEndingDate = ?2";
    public static final String ON_STATUS_REPORT_DELETE_DELIVERY_HOURS = "DELETE DeliveryHours WHERE ProjectId = ?1 AND WeekEndingDate = ?2";

    public static final String TREND_REPORT_DATA_EXCEL_QUERY="select DOT.DeliveryOrganizationTypeName Practice,P.PeopleSoftProjectId,P.PeopleSoftEngagementId,P.GDPId,P.ProjectName,C.ClientName,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDM,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') CSL,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='BDM / AM / SAM' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') BDM,\n" +
            "STUFF((SELECT DISTINCT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDD,\n" +
            "  (\n" +
            "    SELECT \n" +
            "      TOP 1 RiskIndicator \n" +
            "    from \n" +
            "      ProjectRiskSurvey \n" +
            "    WHERE \n" +
            "      ProjectId = P.Id\n" +
            "  ) RiskIndicator, \n" +
            "PS.CurrentPhase,PS.OverAllStatus,PS.WeekEndingDate, PS.Summary  \n" +
            "FROM Project P\n" +
            "LEFT JOIN DeliveryOrganizationType DOT on DOT.Id = P.DeliveryOrganizationTypeId\n" +
            "LEFT JOIN Client C on C.Id = P.ClientId\n" +
            "LEFT JOIN DeliveryModel DM on DM.Id = P.DeliveryModelId\n" +
            "LEFT JOIN ProjectStatus PS on PS.Active = 1 AND PS.ProjectId = P.Id AND PS.WeekEndingDate in ( SELECT WeekEndingDate FROM ProjectStatus WHERE ProjectId = P.Id AND Active = 1 AND WeekEndingDate BETWEEN ?1 and ?2)";

    public static final String GET_OPERATING_COMPANY_QUERY = "SELECT Id, OpcoName from Opco\n" ;

    public static final String GET_OPCO_DOMAINS="SELECT O.OpcoEmailDomain FROM Opco O WHERE O.Id=?";

    public static final String GET_USERNAME_FROM_GDPUSER_TABLE="SELECT * FROM GDPUser WHERE Username=?";

    public static final String GET_LOCATION_NAME="SELECT DISTINCT L.Id,L.LocationName FROM Location L WHERE L.Id IN (Select M.LocationId FROM ProjectLocationMapper M WHERE M.ProjectId IN(SELECT P.Id FROM PROJECT P WHERE" ;

    public static final String DELIVERY_MODEL_FILTER= "SELECT DM.Id, DM.DeliveryModelName\n" +
            "FROM DeliveryModel DM \n" +
            "WHERE DM.Id IN (SELECT P.DeliveryModelId FROM Project P WHERE";

    private Queries() {
    }
}
