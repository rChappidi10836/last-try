/*
*************************************************************************************************************************
This file is for keeping track of the changes applying to the Staging and Production Databases based on the Requirements
*************************************************************************************************************************
*/

/*
**********************************************************************************
Deployment Date: 03.08.2023
DataBase Changes Respetive to the Application Code Deployments to Production
**********************************************************************************
*/

/*  01
****************************************************************************
Changes To DataBase Tables For Customer Success Leader to Engagement Manager
****************************************************************************
*/

Use GDPDev

UPDATE [dbo].[ResourceRole] SET ResourceRoleName = 'Engagement Manager' WHERE Id=1

UPDATE [dbo].[JobCodeWithAccess] SET JobCodeDescription = 'Engagement Manager' WHERE JobCode='90047' AND Id=5

UPDATE [dbo].[JobCodeWithAccess] SET JobCodeDescription = 'Engagement Manager II' WHERE JobCode='90054' AND Id=6

UPDATE [dbo].[JobCodeWithAccess] SET JobCodeDescription = 'Sr. Engagement Manager' WHERE JobCode='8791' AND Id=26


/*  02
***************************************************************************
Changes To DataBase View For Customer Success Leader to Engagement Manager
***************************************************************************
*/

USE [TekGDP]
GO

/****** Object:  View [dbo].[ALL_PROJECT_DATA]    Script Date: 7/28/2023 2:05:23 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[ALL_PROJECT_DATA] AS
SELECT
P.ProjectName [Engagement Name],
C.ClientName [Account Name],
P.GDPId [GDP ID],
P.PeopleSoftEngagementId [PeopleSoft Engagement ID], 
P.PeopleSoftProjectId [PeopleSoft Project ID],
DM.DeliveryModelName [Delivery Model],
STUFF((SELECT ', '+ COALESCE(OP.OpportunityId, '') FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity ID],
P.SMPSite [SMP Link],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDM,
STUFF((SELECT  '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM
JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director'
JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') GDD,
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [EM],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='BDM / AM / SAM' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [BDM],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='National Account Owner' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [NATIONAL ACCOUNT OWNER],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG POA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG POA], 
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG BOA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG BOA],

SSR.SalesOrganizationName [Sales Organization],
FORMAT (P.StartDate, 'yyyy-MM-dd') [Start Date],
FORMAT (P.EndDate, 'yyyy-MM-dd') [End Date],
FORMAT (PS.WeekEndingDate, 'yyyy-MM-dd') [Status Date],
PS.CurrentPhase [Phase],
PS.OverAllStatus [Status Indicator],
PS.Schedule,
PS.ScheduleComments,
PS.CSAT,
PS.CSATComments,
PS.Budget,
PS.BudgetComments,
PS.ProjectRisk,
PS.ProjectRiskComments,
PS.Resources,
PS.ResourcesComments,
PRS.RiskIndicator [Risk Profile],
STUFF((SELECT DISTINCT '/ ' +COALESCE(L.LocationName, '')
FROM ProjectLocationMapper PLM
JOIN Location L ON L.Id = PLM.LocationId
WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 1,'') [Location],
PS.Summary [Summary],
DOT.DeliveryOrganizationTypeName [Practice],
BU.BusinessUnitName  [Business Unit]
From
Project P
JOIN
Client C
ON C.Id = P.ClientId
LEFT JOIN
ProjectStatus PS ON PS.ProjectId = P.Id AND PS.WeekEndingDate>'2020-12-31' AND (PS.DummyRecord = 0 OR PS.DummyRecord IS NULL) AND PS.Active = 1
LEFT JOIN DeliveryOrganizationType DOT ON DOT.Id = P.DeliveryOrganizationTypeID
LEFT JOIN BusinessUnit BU ON BU.Id = P.BusinessUnitId
LEFT JOIN DeliveryModel DM ON DM.Id = P.DeliveryModelId
LEFT JOIN SalesOrganization SSR on SSR.Id = P.SalesOrganizationId
LEFT JOIN ProjectRiskSurvey PRS on PRS.ProjectId = P.Id
WHERE  P.Active = 1


GO


/*  03  
****************************************************************************
Changes To Stored Procedure For Email Notifications to Project Manager Email
****************************************************************************
*/

USE [TekGDP]
GO

/****** Object:  StoredProcedure [dbo].[GDPSYNC]    Script Date: 7/28/2023 6:22:45 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[GDPSYNC]
AS

DECLARE @MailProfileName NVARCHAR(MAX) = 'TEKSQLJOBS'
DECLARE @MailRecipients NVARCHAR(MAX) =  'nrudrabhatla@teksystems.com;bannayol@teksystems.com;kduravas@teksystems.com'
DECLARE @MailSubject VARCHAR(MAX) = 'Global Delivery Portal: PeopleSoft Data Sync Report'
DECLARE @IsExecutionIDCreated BIT = 0
DECLARE @IsGetResourcesDataSuccess BIT = 0
DECLARE @IsGetProjectsDataSuccess BIT = 0
DECLARE @IsGetOpportunityDataSuccess BIT = 0
DECLARE @JobExecutionID INT = 0
DECLARE @MailContent NVARCHAR(MAX)
DECLARE @ProjectMailContent NVARCHAR(MAX)
DECLARE @ResourcesInserted INT = 0
DECLARE @ResourcesUpdated INT = 0
DECLARE @ProjectsUpdated INT = 0
DECLARE @OpportunitiesUpdated INT = 0
DECLARE @OpportunitiesInserted INT = 0
DECLARE @SyncInitializeError INT = 0
DECLARE @IsGetOutlookActualsDataSuccess BIT = 0 
DECLARE @OutlookActualsUpdated INT = 0 
DECLARE @IsGetWSRSuccess BIT = 0 
DECLARE @WSRUpdated INT = 0

--Master data variables
DECLARE @ResourceTypeInsertedCount INT = 0
DECLARE @CenterOfExcellenceTypeInsertedCount INT = 0
DECLARE @SalesRegionTypeInsertedCount INT = 0
DECLARE @deliveryOrgTypeInsertedCount INT = 0
DECLARE @deliveryModelTypeInsertedCount INT = 0
DECLARE @practiceEngagementTypeInsertedCount INT = 0
DECLARE @primaryServiceTypeInsertedCount INT = 0
DECLARE @opportunityTypeInsertedCount INT = 0
DECLARE @SalesStageTypeInsertedCount INT = 0
DECLARE @LocationOfServicesInsertedCount INT = 0
DECLARE @IsMasterDataSyncSuccess BIT = 1

BEGIN
	EXEC SYNC_INITIALIZATION
		@MailSubject, --input
		@MailRecipients, --input
		@MailProfileName, --input
		@IsExecutionIDCreated output,
		@ResourceTypeInsertedCount  output,
		@IsMasterDataSyncSuccess  output,
		@IsGetOpportunityDataSuccess   output,
		@salesRegionTypeInsertedCount   output,
		@deliveryOrgTypeInsertedCount  output,
		@deliveryModelTypeInsertedCount  output,
		@primaryServiceTypeInsertedCount  output,
		@opportunityTypeInsertedCount   output,
		@LocationOfServicesInsertedCount  output,
		@IsGetResourcesDataSuccess  output,
		@IsGetProjectsDataSuccess  output,
		@JobExecutionID  output
END

IF(@IsExecutionIDCreated = 1 AND @IsMasterDataSyncSuccess = 1 AND @IsGetResourcesDataSuccess = 1 AND @IsGetProjectsDataSuccess = 1)
BEGIN
	-- Sync all employee data 
	EXEC RESOURCE_SYNC
		@JobExecutionID, --input
		@ResourcesUpdated output,
		@ResourcesInserted output

	-- Grant Access to newly added users
	EXEC GRANT_ACCESS
	
	-- Sync Project with PS
	EXEC PROJECT_SYNC
		@JobExecutionId, --input
		@ProjectsUpdated output

	EXEC OPPORTUNITY_SYNC
		@JobExecutionID , --input
		@OpportunitiesUpdated output,
		@OpportunitiesInserted output


	--Below 'IF' condition states that once data comes at GDP end from BI server and after that any module sync fails we need to send mail to users stating what has succeeded and
	--what has failed.Because any failure or success will be at GDP end.
	IF(@IsGetResourcesDataSuccess = 1 OR @IsGetProjectsDataSuccess = 1 OR @IsGetOpportunityDataSuccess = 1)
	BEGIN
		--Send mail to the user about sync status
		SET @MailContent = dbo.BI_Func_Create_EMail_Content(@JobExecutionID, @ResourcesInserted, @ResourcesUpdated, @ProjectsUpdated,@OpportunitiesInserted,@OpportunitiesUpdated)
		EXEC msdb.dbo.sp_send_dbmail 
			@profile_name = @MailProfileName,
			@recipients= @MailRecipients,
			@subject = @MailSubject,
			@body = @MailContent,
			@body_format = 'HTML';
END

END



GO



/*  04
************************************************************************************
Changes To GDPUser Table For Access To 'Wittouck, Dries <dwittouck@teksystems.com>'
************************************************************************************
*/


USE [TEKGDP]

INSERT INTO GDPUser(Username, Active, RoleId, Deleted, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy)
VALUES('dwittouck@teksystems.com', 1, 2, 0, GETDATE(), 'MANUAL', GETDATE(), 'MANUAL');


/*  05:
****************************************************************************************************************
Deployment Date: 09-08-2023

Changes To ALL_PROJECT_DATA View to Add the New Column as LastStatusDate and Getting the Data in Sorted Format

Changes are applied to Staging & Production Database Servers

****************************************************************************************************************
*/


USE [TEKGDP]
GO

/****** Object:  View [dbo].[ALL_PROJECT_DATA]    Script Date: 8/9/2023 4:02:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


ALTER VIEW [dbo].[ALL_PROJECT_DATA] AS

/*** Created the Custom Table Extension (CTE) to Create New Column as [LastStatusDate] in View Based On Status Date   ***/
WITH CTE1
AS (
SELECT P.GDPId [GDPID], FORMAT (MAX(PS.WeekEndingDate), 'yyyy-MM-dd') [LastStatusDate]
FROM Project P
INNER JOIN ProjectStatus PS ON PS.ProjectId = P.Id
GROUP BY P.GDPId
)
SELECT
P.ProjectName [Engagement Name],
C.ClientName [Account Name],
P.GDPId [GDP ID],
P.PeopleSoftEngagementId [PeopleSoft Engagement ID], 
P.PeopleSoftProjectId [PeopleSoft Project ID],
DM.DeliveryModelName [Delivery Model],
STUFF((SELECT ', '+ COALESCE(OP.OpportunityId, '') FROM ProjectOpportunityMapper POM JOIN Opportunity OP ON OP.Id = POM.OpportunityId WHERE POM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') [Opportunity ID],
P.SMPSite [SMP Link],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') GDM,
STUFF((SELECT  '/ '+COALESCE(R.LastName+',','' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '','') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Global Delivery Director' JOIN Resource R ON R.Id = PRM.ResourceId AND PRM.isOtherTeamMember=0 WHERE PRM.ProjectId = P.Id FOR XML PATH('')), 1, 2, '') GDD,
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='Engagement Manager' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [EM],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='BDM / AM / SAM' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [BDM],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='National Account Owner' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [NATIONAL ACCOUNT OWNER],
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG POA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG POA], 
STUFF((SELECT '/ '+COALESCE(R.LastName+',', '' ) + R.Firstname + ' ' + COALESCE(R.MiddleName+ '', '') FROM ProjectResourceMapper PRM JOIN ResourceRole RR ON RR.Id = PRM.ResourceRoleId AND RR.ResourceRoleName='OSG BOA' JOIN Resource R ON R.Id = PRM.ResourceId WHERE PRM.ProjectId = P.Id AND PRM.isOtherTeamMember=0 FOR XML PATH('')), 1, 2, '') [OSG BOA],
SSR.SalesOrganizationName [Sales Organization],
FORMAT (P.StartDate, 'yyyy-MM-dd') [Start Date],
FORMAT (P.EndDate, 'yyyy-MM-dd') [End Date],
FORMAT (PS.WeekEndingDate, 'yyyy-MM-dd') [Status Date],
C1.LastStatusDate,
PS.CurrentPhase [Phase],
PS.OverAllStatus [Status Indicator],
PS.Schedule,
PS.ScheduleComments,
PS.CSAT,
PS.CSATComments,
PS.Budget,
PS.BudgetComments,
PS.ProjectRisk,
PS.ProjectRiskComments,
PS.Resources,
PS.ResourcesComments,
PRS.RiskIndicator [Risk Profile],
STUFF((SELECT DISTINCT '/ ' +COALESCE(L.LocationName, '')
FROM ProjectLocationMapper PLM
JOIN Location L ON L.Id = PLM.LocationId
WHERE PLM.ProjectId = P.Id FOR XML PATH('')), 1, 1,'') [Location],
PS.Summary [Summary],
DOT.DeliveryOrganizationTypeName [Practice],
BU.BusinessUnitName  [Business Unit]
From
Project P
JOIN
Client C
ON C.Id = P.ClientId
LEFT JOIN
ProjectStatus PS ON PS.ProjectId = P.Id AND PS.WeekEndingDate>'2020-12-31' AND (PS.DummyRecord = 0 OR PS.DummyRecord IS NULL) AND PS.Active = 1
LEFT JOIN DeliveryOrganizationType DOT ON DOT.Id = P.DeliveryOrganizationTypeID
LEFT JOIN BusinessUnit BU ON BU.Id = P.BusinessUnitId
LEFT JOIN DeliveryModel DM ON DM.Id = P.DeliveryModelId
LEFT JOIN SalesOrganization SSR on SSR.Id = P.SalesOrganizationId
LEFT JOIN ProjectRiskSurvey PRS on PRS.ProjectId = P.Id
INNER JOIN CTE1 C1 ON C1.GDPID = P.GDPID
WHERE  P.Active = 1


GO

/*** ***************************************************************************************************************************** ***/
