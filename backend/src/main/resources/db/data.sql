-- Insert sample clients
INSERT INTO client (client_id, name, email, phone_number)
VALUES (1, 'Tommy Tallwalk', 'tommy.tallwalk@example.com', '82345678'),
       (2, 'Sally Shortstack', 'sally.shortstack@example.com', '81234567'),
       (3, 'Michael Jordan', 'michael.jordan@example.com', '82234567'),
       (4, 'Stephen Curry', 'stephen.curry@example.com', '83234567'),
       (5, 'Kobe Bryant', 'kobe.bryant@example.com', '84234567'),
       (6, 'Luka Doncic', 'luka.doncic@example.com', '85234567'),
       (7, 'Jason Tatum', 'jason.tatum@example.com', '86234567'),
       (8, 'Lebron James', 'lebron.james@example.com', '87234567'),
       (9, 'Giannis Antetokounmpo', 'giannis.antetokounmpo@example.com', '88234567'),
       (10, 'Anthony Davis', 'anthony.davis@example.com', '89234567'),
       (11, 'Kevin Durant', 'kevin.durant@example.com', '80234567'),
       (12, 'James Harden', 'james.harden@example.com', '81123456'),
       (13, 'Kyrie Irving', 'kyrie.irving@example.com', '82123456'),
       (14, 'Damian Lillard', 'damian.lillard@example.com', '83123456'),
       (15, 'Kawhi Leonard', 'kawhi.leonard@example.com', '84123456'),
       (16, 'Paul George', 'paul.george@example.com', '85123456'),
       (17, 'Russell Westbrook', 'russell.westbrook@example.com', '86123456'),
       (18, 'Chris Paul', 'chris.paul@example.com', '87123456'),
       (19, 'Devin Booker', 'devin.booker@example.com', '88123456'),
       (20, 'Donovan Mitchell', 'donovan.mitchell@example.com', '89123456');

-- Insert sample properties
INSERT INTO property (property_id, address, postal_code)
VALUES (1, '123 Changi Road, East', '427466'),
       (2, '456 Jurong West Street, West', '640456'),
       (3, '11 Mount Faber Rd, South', '099209'),
       (4, '900 Dunearn Rd, Central', '589473'),
       (5, '767 Pasir Ris Street 71', '510767'),
       (6, '132 Joo Seng Rd', '368358'),
       (7, '261 Waterloo St', '180261'),
       (8, '20 Bendemeer Rd', '5339914'),
       (9, '624 Choa Chu Kang Street 62', '680624'),
       (10, '321 Yishun Central', '760321'),
       (11, '665C Punggol Dr.', '823665'),
       (12, '245 Tampines St. 21', '521245'),
       (13, '318 Clementi Ave 4', '120318'),
       (14, '363 Clementi Ave 2', '120363'),
       (15, '4B Bury Rd', '119825'),
       (16, '113 Bukit Purmei Rd', '090113'),
       (17, '10E Bedok South Ave 2', '464010'),
       (18, '531 Pasir Ris Dr 1', '510531'),
       (19, '7 Lor How Sun', '536564'),
       (20, '505 Bishan St.11', '570505'),
       (21, '868 Dunearn Rd', '589470'),
       (22, '10 Stirling Rd', '148954'),
       (23, 'Veerasamy Rd, Block 637', '200637'),
       (24, '700B Ang Mo Kio Ave 6', '562700');


-- Link properties to clients
INSERT INTO client_property (client_id, property_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10),
       (11, 11),
       (12, 12),
       (13, 13),
       (14, 14),
       (15, 15),
       (16, 16),
       (17, 17),
       (18,18),
       (19, 19),
       (20, 20),
       (1, 21),
       (2, 22),
       (3, 23),
       (4, 24);

-- Insert sample employees
INSERT INTO employee (employee_id, name, home_address, phone_number, email, short_bio, al_balance, mc_balance, password)
VALUES (1, 'Marky McFly', '4 Tampines Central 5, Singapore 529510, East', '91234567', 'marky.mcfly@example.com', 'Time traveler, occasional cleaner.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (2, 'Betty Broomstick', '60 Yishun Ave 4, Singapore 769027, North', '92234567', 'betty.broomstick@example.com', 'Sweeps like a storm!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (3, 'Charlie Mopster', '391A Orchard Rd, Singapore 238873, Central', '93234567', 'charlie.mopster@example.com', 'Champion floor mopper.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (4, 'Lenny Laundry', '117B Canberra Cres, #01-370, Singapore 752117, North', '94234567', 'lenny.laundry@example.com', 'Folding expert.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (5, 'Harriet Hoover', '710 Clementi West Street 2, Block 710, Singapore 120710, West', '95234567', 'harriet.hoover@example.com', 'Suction master!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (6, 'Darryl So', '36 Punggol Field, Singapore 828814, North-East', '96234567', 'darryl.so@example.com', 'Swiper no swiping!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (7, 'Gwen Low', '61 Hougang Ave 8, Singapore 538787, North-East', '97234567', 'gwen.low@example.com', 'Sweep your problems away!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (8, 'Dylan Teoh', '1 Bukit Batok St 25, Singapore 658882, West', '98234567', 'dylan.teoh@example.com', 'High on cleaning!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (9, 'Yan Yi', '2 Simei St 3, Singapore 529889, East', '99234567', 'yan.yi@example.com', 'Cleaning ninja!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (10, 'Jasmine Tan', '3 Woodlands St 13, Singapore 738600, North', '90234567', 'jasmine.tan@example.com', 'Cleaning fairy!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (11, 'Yok Sim', '156 Tampines Street 12, Singapore 521156, East', '91123456', 'yok.sim@example.com', 'Cleaning wizard!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (12, 'Iris Liew', '17 Seletar Rd, #01 23, Singapore 807019, North-East', '92123456', 'iris.liew@example.com', 'Cleaning sorceress!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO');

-- Insert admin
INSERT INTO admin (admin_id, name, email, phone_number, password)
VALUES (1, 'Bossy McBossface', 'bossyface@example.com', '96234567', '$2a$12$PBSUs2VxiM9/ShHyp7s6TOPNobyEgbGgzyyT8qmT2KieE6OthSTxq');

-- Link admin to employees
INSERT INTO admin_employee (admin_id, employee_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- Insert sample package
INSERT INTO selected_package (package_id, package_type, price, house_size, house_type, hourly_rate, pax, hours)
VALUES ('W_3RM_HDB', 'WEEKLY', 276, 'THREE_ROOM', 'HDB', 23, 1, 3),
        ('W_4RM_HDB', 'WEEKLY', 336, 'FOUR_ROOM', 'HDB', 21, 1, 4),
        ('W_5RM_HDB', 'WEEKLY', 352, 'FIVE_ROOM', 'HDB', 22, 1, 4),
        ('W_MAISONETTE_HDB', 'WEEKLY', 480, 'MAISONETTE', 'HDB', 20, 2, 3),
        ('W_2RM_CONDO', 'WEEKLY', 276, 'TWO_ROOM', 'CONDO', 23, 1, 3),
        ('W_3RM_CONDO', 'WEEKLY', 336, 'THREE_ROOM', 'CONDO', 21, 1, 4),
        ('W_4RM_CONDO', 'WEEKLY', 352, 'FOUR_ROOM', 'CONDO', 22, 1, 4),
        ('W_5RM_CONDO', 'WEEKLY', 480, 'FIVE_ROOM', 'CONDO', 20, 2, 3),
        ('W_MAISONETTE_CONDO', 'WEEKLY', 504, 'MAISONETTE', 'CONDO', 18, 2, 3.5),
        ('W_LANDED', 'WEEKLY', 576, 'LANDED', 'LANDED', 18, 2, 4),
        ('BW_3RM_HDB', 'BI_WEEKLY', 138, 'THREE_ROOM', 'HDB', 23, 1, 3),
        ('BW_4RM_HDB', 'BI_WEEKLY', 168, 'FOUR_ROOM', 'HDB', 21, 1, 4),
        ('BW_5RM_HDB', 'BI_WEEKLY', 176, 'FIVE_ROOM', 'HDB', 22, 1, 4),
        ('BW_MAISONETTE_HDB', 'BI_WEEKLY', 240, 'MAISONETTE', 'HDB', 20, 2, 3),
        ('BW_2RM_CONDO', 'BI_WEEKLY', 138, 'TWO_ROOM', 'CONDO', 23, 1, 3),
        ('BW_3RM_CONDO', 'BI_WEEKLY', 168, 'THREE_ROOM', 'CONDO', 21, 1, 4),
        ('BW_4RM_CONDO', 'BI_WEEKLY', 176, 'FOUR_ROOM', 'CONDO', 22, 1, 4),
        ('BW_5RM_CONDO', 'BI_WEEKLY', 240, 'FIVE_ROOM', 'CONDO', 20, 2, 3),
        ('BW_MAISONETTE_CONDO', 'BI_WEEKLY', 252, 'MAISONETTE', 'CONDO', 18, 2, 3.5),
        ('BW_LANDED', 'BI_WEEKLY', 288, 'LANDED', 'LANDED', 18, 2, 4);

-- Insert sample jobs
-- Weekly Jobs (14 properties, each inserted 4 times for weekly frequency)
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES
    -- Week 1 (November 4)
    (1, 1, 1, 'W_3RM_HDB', '2024-11-04', '09:00:00', 'PENDING', 3),
    (2, 2, 2, 'W_4RM_HDB', '2024-11-04', '10:00:00', 'PENDING', 4),
    (3, 3, 3, 'W_MAISONETTE_HDB', '2024-11-04', '09:00:00', 'PENDING', 3),
    (4, 4, 4, 'W_5RM_CONDO', '2024-11-04', '12:00:00', 'PENDING', 4),
    (5, 5, 5, 'W_2RM_CONDO', '2024-11-04', '13:00:00', 'PENDING', 3),
    (6, 6, 6, 'W_3RM_HDB', '2024-11-04', '14:00:00', 'PENDING', 3),
    (7, 7, 7, 'W_4RM_CONDO', '2024-11-04', '13:00:00', 'PENDING', 4),
    (8, 8, 8, 'W_5RM_HDB', '2024-11-04', '14:00:00', 'PENDING', 4),
    (9, 9, 9, 'W_LANDED', '2024-11-04', '17:00:00', 'PENDING', 4),
    (10, 10, 10, 'W_2RM_HDB', '2024-11-04', '19:00:00', 'PENDING', 3),
    (11, 11, 11, 'W_3RM_CONDO', '2024-11-04', '08:00:00', 'PENDING', 3),
    (12, 12, 12, 'W_4RM_HDB', '2024-11-04', '09:30:00', 'PENDING', 4),
    (13, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-04', '09:00:00', 'PENDING', 3),
    (14, 14, 14, 'W_3RM_HDB', '2024-11-04', '13:00:00', 'PENDING', 3),

    -- Week 2 (November 11)
    (15, 1, 1, 'W_3RM_HDB', '2024-11-11', '09:00:00', 'PENDING', 3),
    (16, 2, 2, 'W_4RM_HDB', '2024-11-11', '10:00:00', 'PENDING', 4),
    (17, 3, 3, 'W_MAISONETTE_HDB', '2024-11-11', '09:00:00', 'PENDING', 3),
    (18, 4, 4, 'W_5RM_CONDO', '2024-11-11', '12:00:00', 'PENDING', 4),
    (19, 5, 5, 'W_2RM_CONDO', '2024-11-11', '13:00:00', 'PENDING', 3),
    (20, 6, 6, 'W_3RM_HDB', '2024-11-11', '14:00:00', 'PENDING', 3),
    (21, 7, 7, 'W_4RM_CONDO', '2024-11-11', '13:00:00', 'PENDING', 4),
    (22, 8, 8, 'W_5RM_HDB', '2024-11-11', '14:00:00', 'PENDING', 4),
    (23, 9, 9, 'W_LANDED', '2024-11-11', '17:00:00', 'PENDING', 4),
    (24, 10, 10, 'W_2RM_HDB', '2024-11-11', '19:00:00', 'PENDING', 3),
    (25, 11, 11, 'W_3RM_CONDO', '2024-11-11', '08:00:00', 'PENDING', 3),
    (26, 12, 12, 'W_4RM_HDB', '2024-11-11', '09:30:00', 'PENDING', 4),
    (27, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-11', '09:00:00', 'PENDING', 3),
    (28, 14, 14, 'W_3RM_HDB', '2024-11-11', '13:00:00', 'PENDING', 3),

    -- Week 3 (November 18)
    (29, 1, 1, 'W_3RM_HDB', '2024-11-18', '09:00:00', 'PENDING', 3),
    (30, 2, 2, 'W_4RM_HDB', '2024-11-18', '10:00:00', 'PENDING', 4),
    (31, 3, 3, 'W_MAISONETTE_HDB', '2024-11-18', '09:00:00', 'PENDING', 3),
    (32, 4, 4, 'W_5RM_CONDO', '2024-11-18', '12:00:00', 'PENDING', 4),
    (33, 5, 5, 'W_2RM_CONDO', '2024-11-18', '13:00:00', 'PENDING', 3),
    (34, 6, 6, 'W_3RM_HDB', '2024-11-18', '14:00:00', 'PENDING', 3),
    (35, 7, 7, 'W_4RM_CONDO', '2024-11-18', '13:00:00', 'PENDING', 4),
    (36, 8, 8, 'W_5RM_HDB', '2024-11-18', '14:00:00', 'PENDING', 4),
    (37, 9, 9, 'W_LANDED', '2024-11-18', '17:00:00', 'PENDING', 4),
    (38, 10, 10, 'W_2RM_HDB', '2024-11-18', '19:00:00', 'PENDING', 3),
    (39, 11, 11, 'W_3RM_CONDO', '2024-11-18', '08:00:00', 'PENDING', 3),
    (40, 12, 12, 'W_4RM_HDB', '2024-11-18', '09:30:00', 'PENDING', 4),
    (41, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-18', '09:00:00', 'PENDING', 3),
    (42, 14, 14, 'W_3RM_HDB', '2024-11-18', '13:00:00', 'PENDING', 3),

    -- Week 4 (November 25)
    (43, 1, 1, 'W_3RM_HDB', '2024-11-25', '09:00:00', 'PENDING', 3),
    (44, 2, 2, 'W_4RM_HDB', '2024-11-25', '10:00:00', 'PENDING', 4),
    (45, 3, 3, 'W_MAISONETTE_HDB', '2024-11-25', '09:00:00', 'PENDING', 3),
    (46, 4, 4, 'W_5RM_CONDO', '2024-11-25', '12:00:00', 'PENDING', 4),
    (47, 5, 5, 'W_2RM_CONDO', '2024-11-25', '13:00:00', 'PENDING', 3),
    (48, 6, 6, 'W_3RM_HDB', '2024-11-25', '14:00:00', 'PENDING', 3),
    (49, 7, 7, 'W_4RM_CONDO', '2024-11-25', '13:00:00', 'PENDING', 4),
    (50, 8, 8, 'W_5RM_HDB', '2024-11-25', '14:00:00', 'PENDING', 4),
    (51, 9, 9, 'W_LANDED', '2024-11-25', '17:00:00', 'PENDING', 4),
    (52, 10, 10, 'W_2RM_HDB', '2024-11-25', '19:00:00', 'PENDING', 3),
    (53, 11, 11, 'W_3RM_CONDO', '2024-11-25', '08:00:00', 'PENDING', 3),
    (54, 12, 12, 'W_4RM_HDB', '2024-11-25', '09:30:00', 'PENDING', 4),
    (55, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-25', '09:00:00', 'PENDING', 3),
    (56, 14, 14, 'W_3RM_HDB', '2024-11-25', '13:00:00', 'PENDING', 3);

-- Bi-Weekly Jobs (6 properties in Weeks 1 and 3)
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES
    (57, 15, 15, 'BW_3RM_HDB', '2024-11-05', '09:00:00', 'PENDING', 3),
    (58, 16, 16, 'BW_4RM_CONDO', '2024-11-05', '10:00:00', 'PENDING', 4),
    (59, 17, 17, 'BW_5RM_HDB', '2024-11-05', '13:00:00', 'PENDING', 4),
    (60, 18, 18, 'BW_MAISONETTE_CONDO', '2024-11-05', '13:00:00', 'PENDING', 3),
    (61, 19, 19, 'BW_3RM_HDB', '2024-11-05', '17:00:00', 'PENDING', 3),
    (62, 20, 20, 'BW_4RM_HDB', '2024-11-05', '19:00:00', 'PENDING', 4),

    -- Repeat Bi-Weekly Jobs for Week 3
    (63, 15, 15, 'BW_3RM_HDB', '2024-11-19', '09:00:00', 'PENDING', 3),
    (64, 16, 16, 'BW_4RM_CONDO', '2024-11-19', '10:00:00', 'PENDING', 4),
    (65, 17, 17, 'BW_5RM_HDB', '2024-11-19', '13:00:00', 'PENDING', 4),
    (66, 18, 18, 'BW_MAISONETTE_CONDO', '2024-11-19', '13:00:00', 'PENDING', 3),
    (67, 19, 19, 'BW_3RM_HDB', '2024-11-19', '17:00:00', 'PENDING', 3),
    (68, 20, 20, 'BW_4RM_HDB', '2024-11-19', '19:00:00', 'PENDING', 4);

-- Additional Bi-Weekly Jobs (4 properties in Weeks 2 and 4)
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES
    (69, 1, 21, 'BW_3RM_CONDO', '2024-11-12', '09:00:00', 'PENDING', 3),
    (70, 2, 22, 'BW_5RM_HDB', '2024-11-12', '10:00:00', 'PENDING', 4),
    (71, 3, 23, 'BW_2RM_HDB', '2024-11-12', '13:00:00', 'PENDING', 3),
    (72, 4, 24, 'BW_LANDED', '2024-11-12', '13:00:00', 'PENDING', 4),

    -- Repeat Bi-Weekly Jobs for Week 4
    (73, 1, 21, 'BW_3RM_CONDO', '2024-11-26', '09:00:00', 'PENDING', 3),
    (74, 2, 22, 'BW_5RM_HDB', '2024-11-26', '10:00:00', 'PENDING', 4),
    (75, 3, 23, 'BW_2RM_HDB', '2024-11-26', '13:00:00', 'PENDING', 3),
    (76, 4, 24, 'BW_LANDED', '2024-11-26', '13:00:00', 'PENDING', 4);

-- Insert completed sample jobs (4 properties in Weeks 2 and 4 of October)
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES
    (77, 1, 21, 'BW_3RM_CONDO', '2024-10-15', '09:00:00', 'COMPLETED', 3),
    (78, 2, 22, 'BW_5RM_HDB', '2024-10-15', '10:00:00', 'COMPLETED', 4),
    (79, 3, 23, 'BW_2RM_HDB', '2024-10-15', '13:00:00', 'COMPLETED', 3),
    (80, 4, 24, 'BW_LANDED', '2024-10-15', '13:00:00', 'COMPLETED', 4),

    -- Repeat Bi-Weekly Jobs for Week 4
    (81, 1, 21, 'BW_3RM_CONDO', '2024-10-29', '09:00:00', 'COMPLETED', 3),
    (82, 2, 22, 'BW_5RM_HDB', '2024-10-29', '10:00:00', 'COMPLETED', 4),
    (83, 3, 23, 'BW_2RM_HDB', '2024-10-29', '13:00:00', 'COMPLETED', 3),
    (84, 4, 24, 'BW_LANDED', '2024-10-29', '13:00:00', 'COMPLETED', 4);

-- Link jobs to employees
INSERT INTO job_employee (job_id, employee_id, reasoning)
VALUES (77, 1, 'Testing purpose only'),
        (78, 2, 'Testing purpose only'),
        (79, 3, 'Testing purpose only'),
        (80, 4, 'Testing purpose only'),
        (81, 1, 'Testing purpose only'),
        (82, 2, 'Testing purpose only'),
        (83, 3, 'Testing purpose only'),
        (84, 4, 'Testing purpose only');

-- Insert sample events into EmployeeEvents
INSERT INTO employee_event (event_id, employee_id, event_type, event_date, job_id, duration, mc_used, al_used)
VALUES (1, 1, 'JOB_COMPLETED', '2024-09-15', 1, 3, NULL, NULL),  -- Completed job on 15th Sep 2024
        (2, 2, 'MC_TAKEN', '2024-09-10', NULL, NULL, 2, NULL),    -- Took 2 days MC on 10th Sep 2024
        (3, 3, 'AL_TAKEN', '2024-09-20', NULL, NULL, NULL, 1),    -- Took 1 day AL on 20th Sep 2024
        (4, 4, 'OVERTIME', '2024-09-25', NULL, 2, NULL, NULL),    -- Worked 2 hours overtime on 25th Sep 2024
        (5, 2, 'AL_TAKEN', '2024-11-12', NULL, NULL, NULL, 2);    -- Took 2 day AL on 12th Nov 2024