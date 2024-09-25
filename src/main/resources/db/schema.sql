DROP TABLE IF EXISTS `Employee`;
CREATE TABLE IF NOT EXISTS `Employee` (
    `EmployeeID` int  NOT NULL ,
    `Name` varchar(120)  NOT NULL ,
    `HomeAddress` varchar(200)  NOT NULL ,
    `PhoneNumber` int  NOT NULL ,
    `Email` varchar(200)  NOT NULL ,
    `ShortBio` varchar(255)  NOT NULL ,
    `ALBalance` int  NOT NULL ,
    `MCBalance` int  NOT NULL ,
    PRIMARY KEY (`EmployeeID`)
);

DROP TABLE IF EXISTS `Admin`;
CREATE TABLE IF NOT EXISTS `Admin` (
    `AdminID` int  NOT NULL ,
    `Name` varchar(120)  NOT NULL ,
    `Email` varchar(200)  NOT NULL ,
    `PhoneNumber` int  NOT NULL ,
    PRIMARY KEY (`AdminID`)
);

DROP TABLE IF EXISTS `AdminEmployees`;
CREATE TABLE IF NOT EXISTS `AdminEmployees` (
    `EmployeeID` int  NOT NULL ,
    `AdminID` int  NOT NULL,
    PRIMARY KEY (`EmployeeID`, `AdminID`)
);

DROP TABLE IF EXISTS `Client`;
CREATE TABLE IF NOT EXISTS `Client` (
    `ClientID` int  NOT NULL ,
    `Name` varchar(120)  NOT NULL ,
    `Email` varchar(200)  NOT NULL ,
    `PhoneNumber` int  NOT NULL ,
    PRIMARY KEY (`ClientID`)
);

DROP TABLE IF EXISTS `ClientProperties`;
CREATE TABLE IF NOT EXISTS `ClientProperties` (
    `ClientID` int  NOT NULL ,
    `PropertyID` int  NOT NULL,
    PRIMARY KEY (`ClientID`, `PropertyID`)
);

DROP TABLE IF EXISTS `Property`;
CREATE TABLE IF NOT EXISTS `Property` (
    `PropertyID` int  NOT NULL ,
    `Address` varchar(200)  NOT NULL ,
    `PostalCode` int  NOT NULL ,
    PRIMARY KEY (`PropertyID`)
);

DROP TABLE IF EXISTS `Job`;
CREATE TABLE IF NOT EXISTS `Job` (
    `JobID` int  NOT NULL ,
    `ClientID` int  NOT NULL ,
    `PropertyID` int  NOT NULL ,
    `PackageID` varchar(20) NOT NULL ,
    `Date` date  NOT NULL ,
    `StartTime` time  NOT NULL ,
    `Status` varchar(20)  NOT NULL ,
    `ActualDuration` int  NOT NULL ,
    PRIMARY KEY (`JobID`)
);

DROP TABLE IF EXISTS `JobEmployees`;
CREATE TABLE IF NOT EXISTS `JobEmployees` (
    `EmployeeID` int  NOT NULL ,
    `JobID` int  NOT NULL,
    PRIMARY KEY (`EmployeeID`, `JobID`)
);

DROP TABLE IF EXISTS `Package`;
CREATE TABLE IF NOT EXISTS `Package` (
    `PackageID` varchar(20)  NOT NULL ,
    `Type` varchar(80)  NOT NULL ,
    `Price` int  NOT NULL ,
    `HouseSize` varchar(50)  NOT NULL ,
    `HouseType` varchar(50)  NOT NULL ,
    `HourlyRate` int  NOT NULL ,
    `Pax` int  NOT NULL ,
    `Hours` int  NOT NULL ,
    PRIMARY KEY (`PackageID`)
) ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `EmployeeEvents`;
CREATE TABLE IF NOT EXISTS `EmployeeStatistics` (
    `StatID` int  NOT NULL ,
    `EventID` int  NOT NULL ,
    `EmployeeID` int  NOT NULL ,
    `StartDate` date  NOT NULL ,
    `EndDate` date  NOT NULL ,
    `PeriodType` varchar(10)  NOT NULL ,
    `JobsCompleted` int  NOT NULL ,
    `ClientsServed` int  NOT NULL ,
    `NewJobs` int  NOT NULL ,
    `CancelledJobs` int  NOT NULL ,
    `RescheduledJobs` int  NOT NULL ,
    `WorkingHours` int  NOT NULL ,
    `OvertimeHours` int  NOT NULL ,
    `MCUsed` int  NOT NULL ,
    `ALUsed` int  NOT NULL ,
    PRIMARY KEY (`StatID`)
);

DROP TABLE IF EXISTS `EmployeeEvents`;
CREATE TABLE IF NOT EXISTS `EmployeeEvents` (
    `EventID` int  NOT NULL,
    `EmployeeID` int  NOT NULL,
    `EventType` varchar(50) NOT NULL,
    `EventDate` date NOT NULL,
    `JobID` int NULL, 
    `Duration` int NULL,
    `MCUsed` int NULL,
    `ALUsed` int NULL,
    PRIMARY KEY (`EventID`)
);