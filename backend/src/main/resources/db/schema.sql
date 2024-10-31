DROP TABLE IF EXISTS `employee_statistic`;
DROP TABLE IF EXISTS `employee_event`;
DROP TABLE IF EXISTS `job_employee`;
DROP TABLE IF EXISTS `admin_employee`;
DROP TABLE IF EXISTS `client_property`;
DROP TABLE IF EXISTS `employee_leaves`; 
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `subscription`;
DROP TABLE IF EXISTS `property`;
DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `selected_package`;
DROP TABLE IF EXISTS `distance_matrix`;
DROP TABLE IF EXISTS `client`;


CREATE TABLE IF NOT EXISTS `employee` (
    `employee_id` int  NOT NULL AUTO_INCREMENT ,
    `name` varchar(120)  NOT NULL ,
    `home_address` varchar(200)  NOT NULL ,
    `phone_number` varchar(20)  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `short_bio` varchar(255)  NOT NULL ,
    `al_balance` int  NOT NULL ,
    `mc_balance` int  NOT NULL ,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`employee_id`)
);

CREATE TABLE IF NOT EXISTS `admin` (
    `admin_id` int  NOT NULL AUTO_INCREMENT,
    `name` varchar(120)  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `phone_number` varchar(20)  NOT NULL ,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`admin_id`)
);

CREATE TABLE IF NOT EXISTS `admin_employee` (
    `employee_id` int  NOT NULL,
    `admin_id` int  NOT NULL,
    PRIMARY KEY (`employee_id`, `admin_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`admin_id`) REFERENCES `admin`(`admin_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `client` (
    `client_id` int  NOT NULL AUTO_INCREMENT,
    `name` varchar(120)  NOT NULL ,
    `email` varchar(200)  NOT NULL ,
    `phone_number` varchar(20)  NOT NULL ,
    `client_address` varchar(200)  NOT NULL ,
    `gender` ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    `client_age` int  NOT NULL ,
    `join_date` date  NOT NULL ,
    PRIMARY KEY (`client_id`)
);

CREATE TABLE IF NOT EXISTS `property` (
    `property_id` int  NOT NULL AUTO_INCREMENT,
    `address` varchar(200)  NOT NULL ,
    `postal_code` varchar(6)  NOT NULL ,
    PRIMARY KEY (`property_id`)
);

CREATE TABLE IF NOT EXISTS `distance_matrix` (
    `distance_id` int  NOT NULL AUTO_INCREMENT,
    `origin` varchar(200)  NOT NULL ,
    `destination` varchar(200)  NOT NULL ,
    `time_taken` int  NOT NULL ,
    PRIMARY KEY (`distance_id`)
);

CREATE TABLE IF NOT EXISTS `client_property` (
    `client_id` int  NOT NULL ,
    `property_id` int  NOT NULL,
    PRIMARY KEY (`client_id`, `property_id`),
    FOREIGN KEY (`client_id`) REFERENCES `client`(`client_id`) ON DELETE CASCADE,
    FOREIGN KEY (`property_id`) REFERENCES `property`(`property_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `selected_package` (
    `package_id` varchar(25) NOT NULL ,
    `package_type` ENUM('WEEKLY', 'BI_WEEKLY')  NOT NULL ,
    `price` int  NOT NULL ,
    `house_size` ENUM('TWO_ROOM', 'THREE_ROOM', 'FOUR_ROOM', 'FIVE_ROOM', 'MAISONETTE', 'LANDED')  NOT NULL ,
    `house_type` ENUM('HDB', 'CONDO', 'LANDED')  NOT NULL ,
    `hourly_rate` int  NOT NULL ,
    `pax` int  NOT NULL ,
    `hours` int  NOT NULL ,
    PRIMARY KEY (`package_id`)
) ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `job` (
    `job_id` int  NOT NULL AUTO_INCREMENT,
    `client_id` int  NOT NULL ,
    `property_id` int  NOT NULL ,
    `package_id` varchar(25) NOT NULL,
    `date` date  NOT NULL ,
    `start_time` time  NOT NULL ,
    `status` ENUM('PENDING', 'SCHEDULED', 'CANCELLED', 'IN_PROGRESS', 'ACTION_REQUIRED', 'COMPLETED')  NOT NULL ,
    `actual_duration` int  NOT NULL ,
    `arrival_proof_uploaded` BOOLEAN NOT NULL,
    `completion_proof_uploaded` BOOLEAN NOT NULL,
    PRIMARY KEY (`job_id`),
    FOREIGN KEY (`client_id`) REFERENCES `client`(`client_id`) ON DELETE CASCADE,
    FOREIGN KEY (`property_id`) REFERENCES `property`(`property_id`) ON DELETE CASCADE,
    FOREIGN KEY (`package_id`) REFERENCES `selected_package`(`package_id`) ON DELETE CASCADE
)ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `job_employee` (
    `employee_id` int  NOT NULL ,
    `job_id` int  NOT NULL,
    `reasoning` varchar(1000) NULL,
    PRIMARY KEY (`employee_id`, `job_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`job_id`) REFERENCES `job`(`job_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `employee_event` (
    `event_id` int  NOT NULL AUTO_INCREMENT,
    `employee_id` int  NOT NULL,
    `event_type` ENUM('JOB_COMPLETED', 'OVERTIME', 'AL_TAKEN', 'MC_TAKEN', 'UNPAID_LEAVE') NOT NULL,
    `event_date` date NOT NULL,
    `job_id` int NULL,
    `duration` int NULL,
    `mc_used` int NULL,
    `al_used` int NULL,
    PRIMARY KEY (`event_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE,
    FOREIGN KEY (`job_id`) REFERENCES `job`(`job_id`) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `employee_statistic` (
    `stat_id` int  NOT NULL AUTO_INCREMENT,
    `event_id` int  NOT NULL ,
    `employee_id` int  NOT NULL ,
    `start_date` date  NOT NULL ,
    `end_date` date  NOT NULL ,
    `period_type` ENUM('WEEKLY', 'MONTHLY', 'YEARLY')  NOT NULL ,
    `jobs_completed` int  NOT NULL ,
    `clients_served` int  NOT NULL ,
    `new_jobs` int  NOT NULL ,
    `cancelled_jobs` int  NOT NULL ,
    `rescheduled_jobs` int  NOT NULL ,
    `working_hours` int  NOT NULL ,
    `overtime_hours` int  NOT NULL ,
    `mc_used` int  NOT NULL ,
    `al_used` int  NOT NULL ,
    PRIMARY KEY (`stat_id`),
    FOREIGN KEY (`event_id`) REFERENCES `employee_event`(`event_id`) ON DELETE CASCADE,
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `employee_leaves` (
    `employee_leave_id` INT NOT NULL AUTO_INCREMENT,
    `employee_id` INT NOT NULL,
    `leave_type` ENUM('MC', 'AL') NOT NULL,
    `application_date_time` DATETIME NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `status` ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    `comments` VARCHAR(255),
    `mc_proof_uploaded` BOOLEAN NOT NULL DEFAULT FALSE,
    `mc_proof_img` BLOB,
    PRIMARY KEY (`employee_leave_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`) ON DELETE CASCADE
) ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `distance_matrix` (
    `distance_id` int  NOT NULL AUTO_INCREMENT,
    `origin` varchar(200)  NOT NULL ,
    `destination` varchar(200)  NOT NULL ,
    `time_taken` int  NOT NULL ,	
    PRIMARY KEY (`distance_id`)
);

CREATE TABLE IF NOT EXISTS `subscription` (
    `subscription_id` INT NOT NULL AUTO_INCREMENT,
    `client_id` INT NOT NULL,
    `property_id` INT NOT NULL,
    `package_id` VARCHAR(25) NOT NULL,
    `package_type` ENUM('WEEKLY', 'BI_WEEKLY') NOT NULL,
    `job_day` ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY',
                   'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    `job_starttime` TIME NOT NULL,
    `job_endtime` TIME NOT NULL,
    `subscription_status` ENUM('ACTIVE', 'PAUSED', 'CANCELLED') NOT NULL,
    PRIMARY KEY (`subscription_id`),
    FOREIGN KEY (`client_id`) REFERENCES `client`(`client_id`) ON DELETE CASCADE,
    FOREIGN KEY (`property_id`) REFERENCES `property`(`property_id`) ON DELETE CASCADE,
    FOREIGN KEY (`package_id`) REFERENCES `selected_package`(`package_id`) ON DELETE CASCADE
    ) ROW_FORMAT=DYNAMIC;