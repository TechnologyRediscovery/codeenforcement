-- AUTHOR: ERIC DARSOW
-- TURTLE CREEK VALLEY COUNCIL OF GOVERNMENTS CODE ENFORCEMENT DATABASE
-- PRIMARY DATABASE CREATION SCRIPT
-- COPATIBLE WITH POSTGRESQL V.10

--BEGIN;



CREATE TABLE municipality
  (
    muniCode             INTEGER NOT NULL,
    muniName             character varying (50) NOT NULL ,
    address_street       character varying (100) ,
    address_city         character varying (100) ,
    address_state        character varying (2) DEFAULT 'PA',
    address_zip          character varying (20) ,
    phone                character varying (12) ,
    fax                  character varying (12) ,
    email                character varying (200) ,   
    managerName          character varying (100) ,
    managerPhone         character varying (12) ,
    population           INTEGER,
    activeInProgram      boolean
  ) ;

ALTER TABLE municipality ADD CONSTRAINT municipality_pk PRIMARY KEY ( muniCode ) ;


CREATE TYPE personType AS ENUM (
    'CogStaff',
    'NonCogOfficial', 
    'MuniStaff',
    'Tenant',
    'OwnerOccupant',
    'OwnerNonOccupant',
    'Manager',
    'ElectedOfficial',
    'Public',
    'LawEnforcement',
    'Other'
) ;

CREATE TYPE role AS ENUM (
    'Developer',
    'SysAdmin',
    'CogStaff',
    'EnforcementOfficial',
    'MuniStaff',
    'MuniReader',
    'Public'

) ;

CREATE TYPE casephase as ENUM
(
    'PrelimInvestigationPending',
    'NoticeDelivery',
    'InitialComplianceTimeframe',
    'SecondaryComplianceTimeframe',
    'AwaitingHearingDate',
    'HearingPreparation',
    'InitialPostHearingComplianceTimeframe',
    'SecondaryPostHearingComplianceTimeframe',
    'InactiveHolding',
    'Closed'
) ;

-- This event type enum is used by the case manager to determine how to process various event creation events
-- PhaseChange events require special processing on the case to determine the appropriate case status
-- to place the case after the event has been logged

CREATE TYPE ceEventType as ENUM
(
    'Originaion',
    'Action',
    'PhaseChange',
    'Closing',
    'Timeline',
    'Communication',
    'Meeting',
    'Custom'
) ;



CREATE SEQUENCE IF NOT EXISTS person_personIDSeq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

-- Storing various kinds of persons who aren't users in the system: see login for system users
CREATE TABLE person
(
    personID                   INTEGER DEFAULT nextval('person_personIDSeq') NOT NULL,
    personType                 personType,
    muni_muniCode              INTEGER NOT NULL,
    fName                      character varying (100) ,
    lName                      character varying (100) ,
    jobTitle                   character varying (100) ,
    phoneCell                  character varying (11) ,
    phoneHome                  character varying (11) ,
    phoneWork                  character varying (11) ,
    email                      character varying (200) ,
    address_street             character varying (100) ,
    address_city               character varying (100) ,
    address_zip                character varying (20) ,
    address_state              character varying (2) DEFAULT 'PA',
    notes                      character varying (2000),
    lastUpdated                TIMESTAMP WITH TIME ZONE,
    expiryDate                 TIMESTAMP WITH TIME ZONE,
    isActive                   boolean DEFAULT TRUE,
    isUnder18                  boolean DEFAULT FALSE


) ;

ALTER TABLE person ADD CONSTRAINT personID_pk PRIMARY KEY ( personID ) ;

ALTER TABLE person ADD CONSTRAINT municipality_fk FOREIGN KEY ( muni_muniCode ) REFERENCES municipality ( muniCode ) ;



CREATE SEQUENCE IF NOT EXISTS actionrqstissuetype_issueTypeID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE actionrqstissuetype
  (
    issueTypeID     INTEGER DEFAULT nextval('actionrqstissuetype_issueTypeID_seq') NOT NULL ,
    typeName        character varying (50) ,
    typeDescription character varying (100) ,
    muni_muniCode   INTEGER,
    notes           character varying (100)
  ) ;

ALTER TABLE actionrqstissuetype ADD CONSTRAINT actionrqstissuetype_pk PRIMARY KEY ( issueTypeID ) ;

ALTER TABLE actionrqstissuetype ADD CONSTRAINT acrreqisstype_muniCode_fk FOREIGN KEY ( muni_muniCode ) REFERENCES municipality ( muniCode ) ;


--****************************************************************
--****************************PROPERTY TABLES ********************
--****************************************************************


CREATE SEQUENCE IF NOT EXISTS propertyusetype_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;


CREATE TABLE propertyusetype
  (
    propertyUseTypeID       INTEGER DEFAULT nextval('propertyusetype_seq') NOT NULL ,
    name                    VARCHAR (50) NOT NULL ,
    description             VARCHAR (100)
  ) ;

ALTER TABLE propertyusetype ADD CONSTRAINT propertyusetype_pk PRIMARY KEY ( propertyUseTypeID ) ;


-- THE CENTRAL TABLE for the system: property. Note that properties don't have an autogenerated ID number
-- since this should exist outside the system and be transferred in. If there is conflict in these IDs, we've 
-- got to sort that out in the data source

CREATE TABLE property
  (
    propertyID                      INTEGER NOT NULL ,
    municipality_muniCode           INTEGER , --fk
    parID                           character varying (50) NOT NULL,

    lotAndBlock                     character varying (50) NOT NULL,
    address                         character varying (50) NOT NULL,
    propertyUseType_propertyUseID   INTEGER,

    rental                          boolean ,
    multiUnit                       boolean ,
    useGroup                        character varying (10),

    constructionType                character varying (10),
    countyCode                      character varying (3) DEFAULT '02',
    apartmentNo                     INTEGER  -- deprecated: keep for transfer from access db
  ) ;

ALTER TABLE property ADD CONSTRAINT property_pk PRIMARY KEY ( propertyID ) ;

ALTER TABLE property ADD CONSTRAINT property_muniCode_fk FOREIGN KEY ( municipality_muniCode ) REFERENCES municipality (muniCode) ;


CREATE SEQUENCE IF NOT EXISTS propertyexternaldata_extDataID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

CREATE TABLE propertyexternaldata
(
    extDataID                       INTEGER DEFAULT nextval('propertyexternaldata_extDataID_seq') NOT NULL,
    property_propertyID             INTEGER NOT NULL, -- fk
    ownerName                       character varying (200),
    ownerPhone                      character varying (11),
    address_street                  character varying (100) ,
    address_cityStateZip            character varying (500) ,
    address_city                    character varying (100) ,
    address_state                   character varying (2),
    address_zip                     character varying (12) ,
    salePrice                       NUMERIC,
    saleYear                        INTEGER,
    assessedLandValue               NUMERIC,
    assessedBuildingValue           NUMERIC,
    assessmentYear                  INTEGER,
    useCode                         character varying (100),
    yearBuilt                       INTEGER,
    livingArea                      INTEGER,
    condition                       character varying (100),
    taxStatus                       character varying (100) ,
    taxStatusYear                   INTEGER,
    notes                           character varying (2000),
    lastUpdated                     TIMESTAMP WITH TIME ZONE
) ;

ALTER TABLE propertyexternaldata ADD CONSTRAINT propertyexternaldata_extDataID_pk PRIMARY KEY (extDataID) ;

ALTER TABLE propertyexternaldata ADD CONSTRAINT propertyexternaldata_propID_fk FOREIGN KEY (property_propertyID) REFERENCES property ( propertyID );


CREATE SEQUENCE IF NOT EXISTS propertunit_unitID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

-- property units are created on a by-property basis for use in CE and occ inspections 
-- A person of type tenant must have an associated unit for properties with multiple units
-- and this behavior is enforced by a required person types evaluated by the person coordinator

CREATE TABLE propertyunit
(
    unitID                          INTEGER DEFAULT nextval('propertunit_unitID_seq') NOT NULL,
    unitNumber                      character varying (10),
    property_propertyID             INTEGER NOT NULL,
    notes                           character varying (2000)
) ;


ALTER TABLE propertyunit ADD CONSTRAINT unitID_pk PRIMARY KEY ( unitID );

ALTER TABLE propertyunit ADD CONSTRAINT propertyunit_propertyID FOREIGN KEY (property_propertyID) REFERENCES property (propertyID);

-- Bridge table between property and person to facilitate a many-to-many relationship (inward to outward crow's feet)
CREATE TABLE propertyperson
(
    property_propertyID            INTEGER NOT NULL, 
    person_personID                INTEGER NOT NULL

) ;

-- composite primary key
ALTER TABLE propertyperson ADD CONSTRAINT propertyperson_propID_pk PRIMARY KEY (property_propertyID, person_personID) ;

ALTER TABLE propertyperson add constraint propertyperson_propID_fk FOREIGN KEY (property_propertyID) REFERENCES property (propertyID) ;

ALTER TABLE propertyperson ADD CONSTRAINT propertyperson_personID_fk FOREIGN KEY (person_personID) REFERENCES person (personID) ;


-- bridge table between propertyunit and person
CREATE TABLE propertyunitperson
(
    propertyUnit_unitID             INTEGER NOT NULL,
    person_personID                 INTEGER NOT NULL
) ;

-- composite primary key
ALTER TABLE propertyunitperson ADD CONSTRAINT propertyunitperson_unitID_pk PRIMARY KEY ( propertyUnit_unitID, person_personID ) ;

ALTER TABLE propertyunitperson ADD CONSTRAINT propertyunitperson_unitID_fk FOREIGN KEY ( propertyUnit_unitID ) REFERENCES propertyunit (unitID) ;

ALTER TABLE propertyunitperson ADD CONSTRAINT propertyunitperson_personID_fk FOREIGN KEY ( person_personID ) REFERENCES person ( personID ) ;



CREATE SEQUENCE IF NOT EXISTS ceactionrequest_requestid_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

-- DB generator of seemingly inconsistent public control codes
-- Current implementation creates this in the server before inserting
-- CREATE SEQUENCE IF NOT EXISTS ceactionrequest__requestPublicc_seq
--     START WITH 1963
--     INCREMENT BY 27
--     MINVALUE 1963
--     NO MAXVALUE
--     CACHE 1;

-- Code enforcement action request stores data related to any submitted action request
-- Filling this out will generate an event 
-- NOTE: It is the job of the case table to know about the existence of a ceactionrequest entry

CREATE TABLE ceactionrequest
  (
    requestID                INTEGER DEFAULT nextval('ceactionrequest_requestid_seq') NOT NULL ,
    requestPublicCC          INTEGER , --DEFAULT nextval('ceactionrequest__requestPublicc_seq'),
    muni_muniCode            INTEGER NOT NULL ,
    property_propertyID      INTEGER NOT NULL,
    issueType_issueTypeID    INTEGER NOT NULL ,
    actRequestor_requestorID INTEGER ,
    cecase_caseID            INTEGER ,
    submittedTimestamp       TIMESTAMP WITH TIME ZONE NOT NULL ,
    dateOfRecord             DATE NOT NULL,
    notataddress             boolean ,
    addressOfConcern         character varying (1000) ,
    requestDescription       character varying (2000) NOT NULL ,
    isUrgent                 boolean DEFAULT FALSE ,
    anonymityRequested       boolean DEFAULT FALSE ,
    cogInternalNotes         character varying (2000),
    muniInternalNotes        character varying (2000),
    publicExternalNotes      character varying (2000)
  ) ;

ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_requestID_pk PRIMARY KEY ( requestID ) ;

ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_requestorID_fk FOREIGN KEY ( actrequestor_requestorID ) REFERENCES person ( personID ) ;

ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_issueTypeID_fk FOREIGN KEY ( issueType_issueTypeID ) REFERENCES actionRqstIssueType ( issueTypeID ) ;

ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_muni_fk FOREIGN KEY ( muni_muniCode ) REFERENCES municipality ( muniCode ) ;

ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_prop_fk FOREIGN KEY ( property_propertyID ) REFERENCES property (propertyID) ;



-- *****************************************************************************

-- SYSTEM LEVEL TABLES



-- these roles for the basis of user access privileges in the system
-- users from the public with no access permissions are given the role 'Public'



CREATE SEQUENCE IF NOT EXISTS login_userid_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;


CREATE TABLE login
  (
    userID                     INTEGER DEFAULT nextval('login_userid_seq') NOT NULL ,
    userRole                   role NOT NULL,
    username                   character varying (30) NOT NULL,
    password                   character varying (30) NOT NULL,
    muni_muniCode              INTEGER NOT NULL, --Foreign Key from municipality
    fName                      character varying (100) NOT NULL ,
    lName                      character varying (100) NOT NULL,
    workTitle                  character varying (100) , 
    phoneCell                  character varying (11) ,
    phoneHome                  character varying (11) ,
    phoneWork                  character varying (11) ,
    email                      character varying (200) NOT NULL,
    address_street             character varying (100) ,
    address_city               character varying (100) ,
    address_zip                character varying (100) ,
    address_state              character varying (2) DEFAULT 'PA',
    notes                      character varying (2000),
    activityStartDate          TIMESTAMP WITH TIME ZONE, -- these could be used for tracking employee status
    activityStopDate           TIMESTAMP WITH TIME ZONE,
    accessPermitted            boolean DEFAULT TRUE
  ) ;

ALTER TABLE login ADD CONSTRAINT login_pk PRIMARY KEY ( userid ) ;

ALTER TABLE login ADD CONSTRAINT login_muniCode_fk FOREIGN KEY ( muni_muniCode ) REFERENCES municipality ( muniCode ) ;

CREATE SEQUENCE IF NOT EXISTS coglog_logeEntryID_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE coglog
(
    logEntryID              INTEGER DEFAULT nextval('coglog_logeEntryID_seq') NOT NULL,
    timeOfEntry             TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    user_userID             INTEGER ,-- fk 
    sessionID               INTEGER, 
    category                character varying (100),
    notes                   character varying (2000)                           

) ;

ALTER TABLE coglog ADD CONSTRAINT coglog_logentryID_pk PRIMARY KEY (logentryID) ;

ALTER TABLE coglog ADD CONSTRAINT coglog_user_userID_fk FOREIGN KEY (user_userID) REFERENCES login ( userID) ;



CREATE SEQUENCE IF NOT EXISTS enforcementofficial_officialID_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;

CREATE TABLE enforcementofficial
(
    officialID                  INTEGER DEFAULT nextval('enforcementofficial_officialID_seq'),
    login_userID                INTEGER NOT NULL, --fk
    badgeNumber                 character varying (10),
    oriNumber                   character varying (20) ,
    notes                       character varying (1000)

) ;

ALTER TABLE enforcementofficial ADD CONSTRAINT enforcementofficial_officialID_pk PRIMARY KEY (officialID) ;

ALTER TABLE enforcementofficial ADD CONSTRAINT enforcementofficial_userID_fk FOREIGN KEY ( login_userID ) REFERENCES login (userID) ;

--*************** EVENTS ************************


CREATE SEQUENCE IF NOT EXISTS ceeventcategory_categoryID_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;

CREATE TABLE ceeventcategory 
(
    categoryID                   INTEGER DEFAULT nextval('ceeventcategory_categoryID_seq') NOT NULL,
    categoryType                 ceEventType NOT NULL,
    title                        character varying (100),
    description                  character varying (1000)

);

ALTER TABLE ceeventcategory ADD CONSTRAINT ceeventcategory_categoryID_pk PRIMARY KEY (categoryID);


CREATE SEQUENCE IF NOT EXISTS cecase_caseID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

CREATE TABLE cecase 
(
    caseID                          INTEGER DEFAULT nextval('cecase_caseID_seq') NOT NULL,
    ceCasePublicCC                  INTEGER NOT NULL,
    property_propertyID             INTEGER NOT NULL,
    propertyunit_unitID             INTEGER , -- propertyunit foriegn key
    login_userID                    INTEGER , -- the case owner
    caseName                        character varying (200) , -- this is a human-friendly case title
    casePhase                       casephase NOT NULL , -- the central case flow tracking field
    originationDate                 TIMESTAMP WITH TIME ZONE, -- inherited from the one event of type 'Origination'
    closingdate                     TIMESTAMP WITH TIME ZONE , -- inerited from the one event of type 'Closing'
    notes                           character varying (2000)

) ;

ALTER TABLE cecase ADD CONSTRAINT cecase_caseID_pk PRIMARY KEY (caseID);

ALTER TABLE cecase ADD CONSTRAINT cecase_propertyID_fk FOREIGN KEY ( property_propertyID) REFERENCES property (propertyID) ;

-- I hope this doesn't create an unwanted cycle in the system. May need some debugging
ALTER TABLE cecase ADD CONSTRAINT cecase_unitID_fk FOREIGN KEY ( propertyunit_unitID ) REFERENCES propertyunit (unitID) ;

ALTER TABLE cecase ADD CONSTRAINT cecase_login_userID_fk FOREIGN KEY (login_userid) REFERENCES login (userID) ;

-- added after cecase to allow ddl script to run without error
ALTER TABLE ceactionrequest ADD CONSTRAINT ceactionrequest_caseID FOREIGN KEY (cecase_caseID) REFERENCES cecase (caseID);



CREATE SEQUENCE IF NOT EXISTS ceevent_eventID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;


-- events have names and event names have types that are used for processing events
CREATE TABLE ceevent
  (
    eventID                     INTEGER DEFAULT nextval('ceevent_eventID_seq') NOT NULL ,
    ceeventCategory_catID       INTEGER NOT NULL , -- fk from ceeventcateogry
    cecase_caseID               INTEGER NOT NULL , -- fk from cecase
    dateOfRecord                TIMESTAMP WITH TIME ZONE ,
    eventTimeStamp              TIMESTAMP WITH TIME ZONE ,
    eventDescription            character varying (2000) ,
    login_userid                INTEGER NOT NULL, --fk from login
    discloseToMunicipality      boolean DEFAULT TRUE,
    discloseToPublic            boolean DEFAULT FALSE, 
    activeEvent                 boolean DEFAULT TRUE, -- used to flag "bad" events
    notes                       character varying (2000)

  ) ;

ALTER TABLE ceevent ADD CONSTRAINT ceevent_eventID_pk PRIMARY KEY (eventID);

ALTER TABLE ceevent ADD CONSTRAINT ceevent_ceeventcategory_fk FOREIGN KEY ( ceeventCategory_catID ) REFERENCES ceeventcategory (categoryID) ;

ALTER TABLE ceevent ADD CONSTRAINT ceevent_ceCaseID_fk FOREIGN KEY (cecase_caseID) REFERENCES cecase (caseID) ;

ALTER TABLE ceevent ADD CONSTRAINT ceevent_login_userID FOREIGN KEY ( login_userid ) REFERENCES login (userID) ;

-- bridge table to allow many-to-many relationship between case and person
CREATE TABLE ceeventperson 
(
    ceevent_eventID             INTEGER NOT NULL ,
    person_personID             INTEGER NOT NULL ,
    roleInEvent                 character varying (500) 
) ;

ALTER TABLE ceeventperson ADD CONSTRAINT ceeventperson_pk PRIMARY KEY (ceevent_eventID, person_personID) ;

ALTER TABLE ceeventperson ADD CONSTRAINT ceeventperson_ceevent_eventID_fk FOREIGN KEY ( ceevent_eventID ) REFERENCES ceevent ( eventID ) ;

ALTER TABLE ceeventperson ADD CONSTRAINT ceeventperson_person_personID_fk FOREIGN KEY ( person_personID ) REFERENCES person ( personID ) ;

-- ********************* VIOLATION CORNER *****************************************



CREATE SEQUENCE IF NOT EXISTS codesource_sourceID_seq
    START WITH 10
    INCREMENT BY 1
    MINVALUE 10
    NO MAXVALUE
    CACHE 1;

-- Stores information related to the origin of a set of municpal codes, such as the IPMC
CREATE TABLE codesource 
(
    sourceID        INTEGER DEFAULT nextval('codesource_sourceID_seq' ) NOT NULL,
    name            character varying (100) NOT NULL,
    year            INTEGER NOT NULL,
    description     character varying (200),
    isActive        boolean DEFAULT TRUE,
    URL             character varying (500),
    notes           character varying (1000)

) ; 

ALTER TABLE codesource ADD CONSTRAINT codesource_sourceID_pk PRIMARY KEY ( sourceID);

CREATE SEQUENCE IF NOT EXISTS codeelementtype_cvTypeID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

-- this functions as a type table to be used in the sorting and management of code bits
CREATE TABLE codeelementtype
  (
    cdelTypeID      INTEGER DEFAULT nextval('codeelementtype_cvTypeID_seq') NOT NULL ,
    name            VARCHAR (50) NOT NULL,
    description     VARCHAR (100)
  ) ;

ALTER TABLE codeelementType ADD CONSTRAINT codeelementtype_cvTypeID_pk PRIMARY KEY ( cdelTypeID ) ;

CREATE SEQUENCE IF NOT EXISTS codeset_codeSetID_seq
    START WITH 10
    INCREMENT BY 1
    MINVALUE 10
    NO MAXVALUE
    CACHE 1;

-- since munis have their own set of ordinances, they can together be grouped as a set and viewed as a unit for
-- assignment to a cecase

CREATE TABLE codeset
(
    codeSetID                   INTEGER DEFAULT nextval('codeset_codeSetID_seq') NOT NULL,
    name                        character varying (100),
    description                 character varying (1000),
    municipality_muniCode       INTEGER -- foreign key from municipality
) ;

ALTER TABLE codeset ADD CONSTRAINT codeset_codeSetID_pk PRIMARY KEY ( codeSetID) ;

ALTER TABLE codeset ADD CONSTRAINT codeset_muniCode_fk FOREIGN KEY (municipality_muniCode) REFERENCES municipality (muniCode) ;



CREATE SEQUENCE IF NOT EXISTS codeelement_elementID_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;

-- stores data on individual code violations extracted from municipal code and adopted national/international code standards

CREATE TABLE codeelement
  (
    elementID                   INTEGER DEFAULT nextval('codeelement_elementID_seq') NOT NULL ,
    codeelementtype_cdelTypeID  INTEGER NOT NULL , -- fk from codeelementtype
    codesource_sourceID         INTEGER NOT NULL, -- fk from codesource
    ordchapterNo                INTEGER NOT NULL ,
    ordchapterTitle             character varying (500),
    ordSecNum                   character varying (10),
    ordsecTitle                 character varying (500),
    ordSubSecNum                character varying (10),
    ordSubSecTitle              character varying (500),
    ordTechnicalText            character varying (2000) NOT NULL,
    ordHumanFriendlyText        character varying (2000),
    defaultPenalty              NUMERIC, -- fees, etc.
    isActive                    boolean DEFAULT TRUE ,
    isEnforcementPriority       boolean DEFAULT FALSE,
    resourceURL                 character varying (500) ,
    inspectionTips              character varying (2000) ,
    dateCreated                 TIMESTAMP WITH TIME ZONE
  ) ;

ALTER TABLE codeelement ADD CONSTRAINT codeelement_pk PRIMARY KEY ( elementID ) ;

ALTER TABLE codeelement ADD CONSTRAINT codeelement_cdeltype_cdelTypeID_fk FOREIGN KEY (codeelementtype_cdelTypeID) REFERENCES codeelementtype (cdelTypeID) ;

ALTER TABLE codeelement ADD CONSTRAINT codeelement_codesource_sourceID FOREIGN KEY (codesource_sourceID) REFERENCES codesource (sourceID) ;

CREATE SEQUENCE IF NOT EXISTS codesetelement_elementid_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;


-- bridging table to allow for codeset and codelement many-many relationships
CREATE TABLE codesetelement
(
    codeSetElementID            INTEGER NOT NULL, --pk
    codeset_codeSetID           INTEGER NOT NULL, --fk
    codelement_elementID        INTEGER NOT NULL, --fk
    elementMaxPenalty           NUMERIC,
    elementMinPenalty           NUMERIC,
    elementNormPenalty          NUMERIC,
    penaltyNotes                character varying (300),
    normDaysToComply            INTEGER NOT NULL,
    daysToComplyNotes           character varying (300)
) ;

ALTER TABLE codesetelement ADD CONSTRAINT codesetelement_codeSetElementID_pk PRIMARY KEY ( codeSetElementID ) ;

ALTER TABLE codesetelement ADD CONSTRAINT codeseetelement_setID_fk FOREIGN KEY (codeset_codeSetID) REFERENCES codeset (codeSetID) ;

ALTER TABLE codesetelement ADD CONSTRAINT codeseetelement_elementID_fk FOREIGN KEY (codelement_elementID) REFERENCES codeelement (elementID) ;
-- Stores information related to which code sections are violated in each cecase item
-- this is a briding table to facilitate many-to-many relationships between case and codeelement
-- with some extra attributes to flesh out the association
CREATE SEQUENCE IF NOT EXISTS codeviolation_violationID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

CREATE TABLE codeviolation
  (
    violationID                 INTEGER DEFAULT nextval('codeviolation_violationID_seq') NOT NULL,
    codeSetElement_elementID    INTEGER NOT NULL , -- foreign key from codeelement
    cecase_caseID               INTEGER NOT NULL , -- foreign key from cecase
    citation_citationID         INTEGER, --fk
    stipulatedComplianceDate    TIMESTAMP WITH TIME ZONE , -- auto generated base on the default compliance timeframe for each violation
    actualCompliancDate         TIMESTAMP WITH TIME ZONE , -- entered when a violationComplianceEvent is generated
    penalty                     NUMERIC,
    description                 character varying (2000),
    notes                       character varying (1000)
  ) ;

ALTER TABLE codeviolation ADD CONSTRAINT codeviolation_pk PRIMARY KEY ( violationID ) ;

ALTER TABLE codeviolation ADD CONSTRAINT codeviolation_cdsetel_elementID_fk FOREIGN KEY (cdel_elementID) REFERENCES codesetelement ( codeSetElementID ) ;

ALTER TABLE codeviolation ADD CONSTRAINT codeviolation_caseID_fk FOREIGN KEY (cecase_caseID) REFERENCES cecase (caseID) ;

ALTER TABLE codeviolation ADD CONSTRAINT codeviolation_citationID_fk FOREIGN KEY ( citation_citationID ) REFERECES citation (citationID) ;


CREATE SEQUENCE IF NOT EXISTS courtentity_entityID_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;

CREATE TABLE courtentity
(
    entityID                   INTEGER DEFAULT nextval('courtentity_entityID_seq') NOT NULL,
    entityOfficialNum          character varying (100) ,
    jurisdictionLevel          character varying (100) NOT NULL,
    muni_muniCode              INTEGER , --fk
    name                       character varying (1000) NOT NULL ,
    address_street             character varying (100) ,
    address_city               character varying (100) ,
    address_zip                character varying (100) ,
    address_state              character varying (2) ,
    county                     character varying (50) ,
    phone                      character varying (11) ,
    URL                        character varying (1000) ,
    notes                      character varying (2000) 
) ;

ALTER TABLE courtentity ADD CONSTRAINT courtentity_entityID_pk PRIMARY KEY (entityID) ;

ALTER TABLE courtentity ADD CONSTRAINT courtentity_muniCode_fk FOREIGN KEY (muni_muniCode) REFERENCES municipality (muniCode); 



CREATE SEQUENCE IF NOT EXISTS citation_citationID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

CREATE TABLE citation
(
    citationID                      INTEGER DEFAULT nextval('citation_citationID_seq') NOT NULL, 
    citationNo                      character varying (100), --collaboratively created with munis
    origin_courtentity_entityID     INTEGER NOT NULL, --fk
    cecase_caseID                   INTEGER NOT NULL, --fk
    enforcementofficial_officialID  INTEGER NOT NULL, --fk
    dateOfRecord                    TIMESTAMP WITH TIME ZONE,
    transTimeStamp                  TIMESTAMP WITH TIME ZONE,
    isActive                        boolean DEFAULT TRUE,
    notes                           character varying (2000)
    -- this is just a skeleton for a citation: more fields likely as system develops
) ;

ALTER TABLE citation ADD CONSTRAINT citation_citationID_pk PRIMARY KEY ( citationID );

ALTER TABLE citation ADD CONSTRAINT citation_courtentity_entityID_fk FOREIGN KEY (origin_courtentity_entityID ) REFERENCES courtentity ( entityID ) ;

ALTER TABLE citation ADD CONSTRAINT citation_cecase_caseID_fk FOREIGN KEY ( cecase_caseID ) REFERENCES cecase (caseID) ;

ALTER TABLE citation ADD CONSTRAINT citation_officialID_fk FOREIGN KEY (enforcementofficial_officialID) REFERENCES enforcementofficial (officialID);


-- Photos and document blob tables and connectors

-- Linking table between property and its related blobs


CREATE SEQUENCE IF NOT EXISTS photodoc_photoDocID_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;

CREATE TABLE photodoc
  (
    photoDocID          INTEGER DEFAULT nextval('photodoc_photoDocID_seq') NOT NULL ,
    photoDocDescription VARCHAR (100) ,
    photoDocDate        TIMESTAMP WITH TIME ZONE ,
    photoDocType_typeID INTEGER NOT NULL ,
    photoDocBlob        bytea
  ) ;
ALTER TABLE photoDoc ADD CONSTRAINT photoDoc_pk PRIMARY KEY ( photoDocID ) ;


CREATE TABLE propertyphotodoc
  (
    photodoc_photoDocID INTEGER NOT NULL ,
    property_propertyID INTEGER NOT NULL
  ) ;
ALTER TABLE propertyPhotoDoc ADD CONSTRAINT propertyPhotoDoc_pk PRIMARY KEY ( photoDoc_photoDocID, property_propertyID ) ;

ALTER TABLE propertyphotodoc ADD CONSTRAINT propertyphotodoc_pdid_fk FOREIGN KEY (photodoc_photoDocID) REFERENCES photodoc (photoDocID);

ALTER TABLE propertyphotodoc ADD CONSTRAINT propertyphotodoc_prop_fk FOREIGN KEY (property_propertyID) REFERENCES property (propertyID);


-- COMMIT;

