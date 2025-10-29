--Staging server data ready steps:
--Add roles to role table
INSERT INTO dbo.Role(Role, CreatedBy, CreatedAt, ModifiedBy, ModifiedAt, Active, Deleted ) VALUES ('Admin', 'Bulk',CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 1, 0);
INSERT INTO dbo.Role(Role, CreatedBy, CreatedAt, ModifiedBy, ModifiedAt, Active, Deleted ) VALUES ('Global Edit Access', 'Bulk', CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 1, 0);
INSERT INTO dbo.Role(Role, CreatedBy, CreatedAt, ModifiedBy, ModifiedAt, Active, Deleted ) VALUES ('Global View Access', 'Bulk', CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 1, 0);

--Create ADMIN USER who can later add User’s to this application from UI(replace email with amin’s ----email)
  DECLARE @ADMIN_ROLE_ID as INT, @ADMIN_EMAIL VARCHAR (100);
  SET @ADMIN_ROLE_ID = (SELECT Id FROM Role R WHERE R.Role = 'Admin');
  SET @ADMIN_EMAIL = 'rshrivastav@teksystems.com';

INSERT INTO dbo.GDPUser(Username, Active, LastLoginDate, RoleId, Deleted, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy )
  VALUES (@ADMIN_EMAIL,
  1, null,
  @ADMIN_ROLE_ID,
  0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
 
SET @ADMIN_EMAIL = 'bannayol@teksystems.com';
INSERT INTO dbo.GDPUser(Username, Active, LastLoginDate, RoleId, Deleted, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy )
  VALUES (@ADMIN_EMAIL,
  1, null,
  @ADMIN_ROLE_ID,
  0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

PRINT 'Added Admins'

--Update opportunity table HasProject column if mapping for it in ProjectOpportunityMapper Exists
UPDATE Opportunity SET HasProject=1 WHERE Id IN(SELECT OpportunityId FROM ProjectOpportunityMapper);
PRINT 'SET Oppotunity HasProject =1 if Project is mapped in ProjectOpportunityMapper'

--ADD Security Questions
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Does Client agreement require dedicated team room space? (No other Client Support team in the room)', 0, 1, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('How are we connecting to the Client environment?', 1, 2, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Does Client agreement permit remote work from home? (Outside of Pandemic conditions)', 0, 3, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Per Client Agreement, will the team have access to Sensitive Information?', 1, 4, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Does Client Agreement permit offshore delivery of services?', 0, 5, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('What are the client compliance requirements?', 1, 6, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Does the client agreement include specific business continuity requirements?', 0, 7, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityQuestions( Question, Multiselect, Position, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ('Is Cloud environment used in support of project?', 0, 8, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

--Add Answers
DECLARE @SecurityQuestionId AS INT;

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Does Client agreement require dedicated team room space? (No other Client Support team in the room)');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='How are we connecting to the Client environment?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Client VPN', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Site to Site VPN (Tunnel)', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Citrix or other VDI', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Direct access to client systems', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Does Client agreement permit remote work from home? (Outside of Pandemic conditions)');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Per Client Agreement, will the team have access to Sensitive Information?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'PII', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'PHI', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Pay Card Data (PCI)', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No Sensitive Data', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Other Sensitive Information', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Does Client Agreement permit offshore delivery of services?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='What are the client compliance requirements?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'ISO 27001 Certification', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'HITRUST Certification', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Other', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Does the client agreement include specific business continuity requirements?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes', 1, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');

SET @SecurityQuestionId = (SELECT Id FROM SecurityQuestions WHERE Question='Is Cloud environment used in support of project?');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'No', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes-TEK', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
INSERT INTO dbo.SecurityAnswer( SecurityQuestionId, Answer, MandatoryComment, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy ) VALUES ( @SecurityQuestionId, 'Yes-Customer', 0, CURRENT_TIMESTAMP, 'Bulk', CURRENT_TIMESTAMP, 'Bulk');
PRINT 'Security Answers inserted with question ids'

UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Agreement', 'agreement');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Client', 'client');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Environment', 'environment');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Support', 'support');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Sensitive Information', 'sensitive information');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Cloud', 'cloud');
UPDATE SecurityQuestions SET Question = REPLACE(Question, 'Pandemic', 'pandemic');
PRINT 'Updated Security Questions spelling of agreement and client and environment'

--IMPORTANT!!
--CREATE FOREIGN KEY IN DeliveryMilestones
--ADD DeliveryMilestoneTypeId(Int)
--FK to Column to [dbo].[DeliveryMilestone].[Id]

--Adding column:
--ALTER TABLE DeliveryMilestones 
--ADD DeliveryMilestoneTypeId INT NULL;
----Adding Foreign Key Constraint:
--ALTER TABLE DeliveryMilestones
--ADD CONSTRAINT FK_DeliveryMilestones_DeliveryMilestoneType FOREIGN KEY (DeliveryMilestoneTypeId) REFERENCES DeliveryMilestoneType (Id);

 
--UPDATE CREADTED BY AND MODIFIED BY
--Project Status:


UPDATE ProjectStatus SET CreatedBy = REPLACE(REPLACE(CreatedBy, 'CORPORATE\', ''), 'CORPORATE\', '')
WHERE CreatedBy like 'CORPORATE\%' ;

UPDATE ProjectStatus SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, 'CORPORATE\', ''), 'CORPORATE\', '')
WHERE ModifiedBy like 'CORPORATE\%';


--Resource:

UPDATE Resource SET CreatedBy = REPLACE(REPLACE(CreatedBy, 'CORPORATE\', ''), 'CORPORATE\', '')
WHERE CreatedBy like 'CORPORATE\%' ;

UPDATE Resource SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, 'CORPORATE\', ''), 'CORPORATE\', '')
WHERE ModifiedBy like 'CORPORATE\%';

--DeliveryModel:

UPDATE DeliveryModel SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE DeliveryModel SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- DeliveryOrganizationType:

UPDATE DeliveryOrganizationType SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE DeliveryOrganizationType SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- Designation:
UPDATE Designation SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE Designation SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- Location:

UPDATE Location SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE Location SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- Opportunity:

UPDATE Opportunity SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE Opportunity SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- OpportunityType:
UPDATE OpportunityType SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE OpportunityType SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- PrimaryServiceType:
UPDATE PrimaryServiceType SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE PrimaryServiceType SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- Project:
UPDATE Project SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE Project SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- ProjectRiskSurvey:
UPDATE ProjectRiskSurvey SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE ProjectRiskSurvey SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';
-- ResourceLocation:
UPDATE ResourceLocation SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE ResourceLocation SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%'; 

-- SalesOrganization:
UPDATE SalesOrganization SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE SalesOrganization SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

-- StaffingSalesRegion:
UPDATE StaffingSalesRegion SET CreatedBy = REPLACE(REPLACE(CreatedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE CreatedBy like '%CORPORATE\%' ;

UPDATE StaffingSalesRegion SET ModifiedBy = REPLACE(REPLACE(ModifiedBy, '%CORPORATE\%', ''), 'CORPORATE\', '')
WHERE ModifiedBy like '%CORPORATE\%';

PRINT 'UPDATED CreatedBy and ModifiedBy removing CORPORATE from username'
 
--UPDATE ProjectRiskSurvey with new system values
-- RISKSurvey Indicator value

UPDATE [ProjectRiskSurvey] SET RiskIndicator = (

CASE WHEN RiskIndicator = 'High Risk' Then 'red'
WHEN RiskIndicator = 'Moderate Risk' Then 'orange'
WHEN RiskIndicator = 'Low Risk' Then 'green' END)
FROM [ProjectRiskSurvey];
PRINT 'Update ProjectRiskSurvey.RiskIndicator'

--ContractType
UPDATE ProjectRiskSurvey  
		SET ContractType = CASE  
                        WHEN ContractType = 'Time & Materials' THEN 'Time & Material' 
                        WHEN ContractType = 'Time & Materials not to exceed' THEN 'Time & Material not to exceed' 
                        WHEN ContractType = 'Fixed Price / Per Unit (milestones / deliverables)' THEN 'Fixed Price - for milestones, time periods, deliverables' 
                        ELSE ContractType
                    END 
		WHERE ContractType IN ('Time & Materials', 'Time & Materials not to exceed', 'Fixed Price / Per Unit (milestones / deliverables)');

--Location of service
UPDATE ProjectRiskSurvey  
		SET Location = CASE  
                        WHEN Location = 'On/nearshore TEKsystems facility or remote location' THEN 'On-/near-shore TEKsystems facility or remote location' 
                        WHEN Location = 'Mix of client, on/nearshore TEKsystems facility, or remote location' THEN 'Mix of client, on-/near-shore TEKsystems facility, or remote location' 
                        WHEN Location = 'Offshore TEKsystems facility' THEN 'Off-shore TEKsystems facility' 
                        WHEN Location = 'Mix of client, on/near/offshore TEKsystems facility, or remote location' THEN 'Mix of client, on-/near-/off-shore TEKsystems facility, or remote location'
						ELSE Location
                    END 
		WHERE Location IN ('On/nearshore TEKsystems facility or remote location',
		'Mix of client, on/nearshore TEKsystems facility, or remote location',
		'Mix of client, on/near/offshore TEKsystems facility, or remote location',
		'Offshore TEKsystems facility');

--Has Unique Challanges
UPDATE ProjectRiskSurvey  
		SET HasUniqueChallenges = 'No - Location, model, and practice involvement are standard'
		WHERE HasUniqueChallenges = 'No – Location, model, and practice involvement are standard';

-- Customer credit score
UPDATE ProjectRiskSurvey  
		SET CustomerCreditScore = 'Not Available'
		WHERE CustomerCreditScore = 'Not available';

--Update Phases with new mapping
UPDATE ProjectStatus SET CurrentPhase =
CASE WHEN CurrentPhase='Initiation' THEN 'Startup'
	 WHEN CurrentPhase='Planning' THEN 'Startup'
	 WHEN CurrentPhase='Closing' THEN 'Closeout'
	 ELSE CurrentPhase END
WHERE CurrentPhase IN ('Initiation', 'Planning', 'Closing');
UPDATE ProjectStatus SET CurrentPhase = 'Startup' WHERE CurrentPhase = 'Program Initiation';
PRINT 'UPDATED More Spellings'

--Flex IT to Flexible Capacity

--replacing Flex IT(EMEA) with Flex IT (North America)
UPDATE Project
SET DeliveryOrganizationTypeID = (SELECT TOP 1 Id FROM DeliveryOrganizationType WHERE DeliveryOrganizationTypeName = 'Flex IT (North America)')
WHERE DeliveryOrganizationTypeID = (SELECT TOP 1 Id FROM DeliveryOrganizationType WHERE DeliveryOrganizationTypeName = 'Flex IT (EMEA)');

--renaming Flex IT(NA) to Flexible Capactiy
UPDATE DeliveryOrganizationType SET DeliveryOrganizationTypeName = 'Flexible Capacity'
WHERE DeliveryOrganizationTypeName = 'Flex IT (North America)';

--Disabling the Flex IT (EMEA)
UPDATE DeliveryOrganizationType SET Active = 0, Deleted = 1
WHERE DeliveryOrganizationTypeName = 'Flex IT (EMEA)'
PRINT 'Flex IT to Flexible Capacity'

--BDM and BDM/AM combined to BDM/AM/SAM

UPDATE ProjectResourceMapper
SET ResourceRoleId = (SELECT Id FROM ResourceRole WHERE ResourceRoleName = 'BDM')
WHERE ResourceRoleId = (SELECT Id FROM ResourceRole WHERE ResourceRoleName = 'BDM/AM')

UPDATE ResourceRole SET ResourceRoleName = 'BDM/AM/SAM'
WHERE ResourceRoleName = 'BDM/AM'

UPDATE ResourceRole SET ResourceRoleName = 'BDM / AM / SAM'
WHERE ResourceRoleName = 'BDM/AM/SAM'
GO

UPDATE ResourceRole SET Active = 0, Deleted = 1
WHERE ResourceRoleName = 'BDM'

UPDATE ResourceRole SET ResourceRoleName = 'Solution Executive'
WHERE ResourceRoleName = 'Solution Engineer'

UPDATE ResourceRole SET EngagementType = 'COMMON'

PRINT 'Update Resource Roles'
 
--UPDATING ProjectName AND ClientName containing HTML entities.

GO

/****** Object:  UserDefinedFunction [dbo].[HTMLDecode]    Script Date: 6/10/2021 3:08:12 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
 

GO
--dropping if function already exists
IF object_id('fnDeUrl', 'FN') IS NOT NULL
BEGIN
    DROP FUNCTION [dbo].[fnDeUrl]
END
GO

CREATE FUNCTION dbo.fnDeURL
(
    @URL VARCHAR(8000)
)
RETURNS VARCHAR(8000)
AS
BEGIN
    DECLARE @Position INT,
        @Base CHAR(16),
        @High TINYINT,
        @Low TINYINT,
        @Pattern CHAR(21)

    SELECT  @Base = '0123456789abcdef',
        @Pattern = '%[%][0-9a-f][0-9a-f]%',
        @URL = REPLACE(@URL, '+', ' '),
        @Position = PATINDEX(@Pattern, @URL)

    WHILE @Position > 0
        SELECT  @High = CHARINDEX(SUBSTRING(@URL, @Position + 1, 1), @Base COLLATE Latin1_General_CI_AS),
            @Low = CHARINDEX(SUBSTRING(@URL, @Position + 2, 1), @Base COLLATE Latin1_General_CI_AS),
            @URL = STUFF(@URL, @Position, 3, CHAR(16 * @High + @Low - 17)),
            @Position = PATINDEX(@Pattern, @URL)

    RETURN  @URL
END

GO
--updating project smp site and archived data smp site
UPDATE Project SET SMPSite = dbo.fnDeUrl(SMPSite);
UPDATE GDP_Archived_Practice_Details SET SMP_Site = dbo.fnDeUrl(SMP_Site);
PRINT 'Updated SMPSite Links'
GO
--dropping when not in use
IF object_id('fnDeUrl', 'FN') IS NOT NULL
BEGIN
    DROP FUNCTION [dbo].[fnDeUrl]
END
GO
 

--DeliveryModel Flex IT=> Co-managed

UPDATE Project SET DeliveryModelId = (SELECT Id FROM DeliveryModel WHERE DeliveryModelName= 'Co-Managed')
WHERE DeliveryModelId = (SELECT Id FROM DeliveryModel WHERE DeliveryModelName= 'Flex IT')

UPDATE Opportunity SET DeliveryModelId = (SELECT Id FROM DeliveryModel WHERE DeliveryModelName= 'Co-Managed')
WHERE DeliveryModelId = (SELECT Id FROM DeliveryModel WHERE DeliveryModelName= 'Flex IT')

UPDATE DeliveryModel SET Active = 0
WHERE Id = (SELECT Id FROM DeliveryModel WHERE DeliveryModelName= 'Flex IT')

PRINT 'UPDATE Delivery Model Flex IT to Co-Managed'

--GRANT Access To Users.

BEGIN
	IF EXISTS
	(SELECT DISTINCT UserId FROM Resource WHERE JOBCODE IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess)
	AND UserId != '' AND UserId+'@teksystems.com' NOT IN (SELECT Username FROM GDPUser) AND Active = 1)
	BEGIN
		DECLARE @AlegibleResource TABLE(
			UserId VARCHAR(100) NULL,
			Active bit NULL,
			RoleId int NULL,
			DELETED bit NULL
		);
		PRINT 'To give the access to the newly added resources'
		INSERT @AlegibleResource(UserId, Active, RoleId, DELETED)
		SELECT R.UserId, R.Active, 
		CASE 
			WHEN R.JobCode IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess WHERE AccessRole = 'Global View Access')
			THEN (SELECT TOP 1 Id FROM Role WHERE Role = 'Global View Access')
			WHEN R.JobCode IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess WHERE AccessRole = 'Global Edit Access')
			THEN (SELECT TOP 1 Id FROM Role WHERE Role = 'Global Edit Access')
		END,
		0
		FROM Resource R WHERE R.UserId!='' AND R.UserId+'@teksystems.com' NOT IN (SELECT DISTINCT Username FROM GDPUser) AND R.Active = 1 
		AND R.JobCode IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess);

		INSERT GDPUser(Username,Active, RoleId, Deleted, CreatedBy, ModifiedBy, CreatedAt, ModifiedAt)
		SELECT AR.UserId+'@teksystems.com', AR.Active, AR.RoleId, AR.DELETED, 'GDP Data Sync', 'GDP Data Sync', GETDATE(), GETDATE()
		FROM @AlegibleResource AR;

		--Updating access in Resource table.
		UPDATE Resource SET Access = CASE
			WHEN JobCode IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess WHERE AccessRole = 'Global View Access')
			THEN 'Global View Access'
			WHEN JobCode IN (SELECT DISTINCT JobCode FROM JobCodeWithAccess WHERE AccessRole = 'Global Edit Access')
			THEN 'Global Edit Access'
			ELSE NULL
		END

		PRINT 'Access given to the newly added resources'

	END
	ELSE
	BEGIN
		PRINT 'No new resource is found in the Resource table to give access'
	END
END
 
