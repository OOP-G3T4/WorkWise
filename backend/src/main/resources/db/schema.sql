DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
    `employee_id` int  NOT NULL ,
    `name` varchar(120)  NOT NULL ,
    `home_address` varchar(200)  NOT NULL ,
    `phone_number` int  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `short_bio` varchar(255)  NOT NULL ,
    `al_balance` int  NOT NULL ,
    `mc_balance` int  NOT NULL ,
    PRIMARY KEY (`employee_id`)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
    `admin_id` int  NOT NULL ,
    `name` varchar(120)  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `phone_number` int  NOT NULL ,
    PRIMARY KEY (`admin_id`)
);

DROP TABLE IF EXISTS `admin_employee`;
CREATE TABLE IF NOT EXISTS `admin_employee` (
    `employee_id` int  NOT NULL ,
    `admin_id` int  NOT NULL,
    PRIMARY KEY (`employee_id`, `admin_id`)
);

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
    `client_id` int  NOT NULL ,
    `name` varchar(120)  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `phone_number` int  NOT NULL ,
    PRIMARY KEY (`client_id`)
);

DROP TABLE IF EXISTS `client_property`;
CREATE TABLE IF NOT EXISTS `client_property` (
    `client_id` int  NOT NULL ,
    `property_id` int  NOT NULL,
    PRIMARY KEY (`client_id`, `property_id`)
);

DROP TABLE IF EXISTS `property`;
CREATE TABLE IF NOT EXISTS `property` (
    `property_id` int  NOT NULL ,
    `address` varchar(200)  NOT NULL ,
    `postal_code` int  NOT NULL ,
    PRIMARY KEY (`property_id`)
);

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
    `job_id` int  NOT NULL ,
    `client_id` int  NOT NULL ,
    `property_id` int  NOT NULL ,
    `package_id` varchar(20) NOT NULL ,
    `date` date  NOT NULL ,
    `start_time` time  NOT NULL ,
    `status` varchar(20)  NOT NULL ,
    `actual_duration` int  NOT NULL ,
    PRIMARY KEY (`job_id`)
);

DROP TABLE IF EXISTS `job_employee`;
CREATE TABLE IF NOT EXISTS `job_employee` (
    `employee_id` int  NOT NULL ,
    `job_id` int  NOT NULL,
    PRIMARY KEY (`employee_id`, `job_id`)
);

DROP TABLE IF EXISTS `package`;
CREATE TABLE IF NOT EXISTS `package` (
    `package_id` varchar(20)  NOT NULL ,
    `type` varchar(80)  NOT NULL ,
    `price` int  NOT NULL ,
    `house_size` varchar(50)  NOT NULL ,
    `house_type` varchar(50)  NOT NULL ,
    `hourly_rate` int  NOT NULL ,
    `pax` int  NOT NULL ,
    `hours` int  NOT NULL ,
    PRIMARY KEY (`package_id`)
) ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `employee_event`;
CREATE TABLE IF NOT EXISTS `employee_statistic` (
    `stat_id` int  NOT NULL ,
    `event_id` int  NOT NULL ,
    `employee_id` int  NOT NULL ,
    `start_date` date  NOT NULL ,
    `end_date` date  NOT NULL ,
    `period_type` varchar(10)  NOT NULL ,
    `jobs_completed` int  NOT NULL ,
    `clients_served` int  NOT NULL ,
    `new_jobs` int  NOT NULL ,
    `cancelled_jobs` int  NOT NULL ,
    `rescheduled_jobs` int  NOT NULL ,
    `working_hours` int  NOT NULL ,
    `overtime_hours` int  NOT NULL ,
    `mc_used` int  NOT NULL ,
    `al_used` int  NOT NULL ,
    PRIMARY KEY (`stat_id`)
);

DROP TABLE IF EXISTS `employee_event`;
CREATE TABLE IF NOT EXISTS `employee_event` (
    `event_id` int  NOT NULL,
    `employee_id` int  NOT NULL,
    `event_type` varchar(50) NOT NULL,
    `event_date` date NOT NULL,
    `job_id` int NULL,
    `duration` int NULL,
    `mc_used` int NULL,
    `al_used` int NULL,
    PRIMARY KEY (`event_id`)
);