-- Insert data into database
-- Insert sample clients
INSERT INTO client (client_id, name, email, phone_number)
VALUES (1, 'Tommy Tallwalk', 'tommy.tallwalk@example.com', '82345678'),
        (2, 'Sally Shortstack', 'sally.shortstack@example.com', '81234567'),
        (3, 'Michael Jordan', 'michael.jordan@example.com', '82234567');

-- Insert sample properties
INSERT INTO property (property_id, address, postal_code)
VALUES (1, '123 Changi Road, East', '427466'),
        (2, '456 Jurong West Street, West', '640456'),
        (3, '11 Mount Faber Rd, South', '099209'),
        (4, '900 Dunearn Rd, Central', '589473'),
        (5, '767 Pasir Ris Street 71', '510767');


-- Link properties to clients
INSERT INTO client_property (client_id, property_id)
VALUES (1, 1),  
        (2, 2),
        (3, 3),
        (3, 4),
        (2, 5);

-- Insert sample employees
INSERT INTO employee (employee_id, name, home_address, phone_number, email, short_bio, al_balance, mc_balance, password)
VALUES (1, 'Marky McFly', '4 Tampines Central 5, Singapore 529510, East', '91234567', 'marky.mcfly@example.com', 'Time traveler, occasional cleaner.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (2, 'Betty Broomstick', '60 Yishun Ave 4, Singapore 769027, North', '92234567', 'betty.broomstick@example.com', 'Sweeps like a storm!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (3, 'Charlie Mopster', '391A Orchard Rd, Singapore 238873, Central', '93234567', 'charlie.mopster@example.com', 'Champion floor mopper.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (4, 'Lenny Laundry', '117B Canberra Cres, #01-370, Singapore 752117, North', '94234567', 'lenny.laundry@example.com', 'Folding expert.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (5, 'Harriet Hoover', '710 Clementi West Street 2, Block 710, Singapore 120710, West', '95234567', 'harriet.hoover@example.com', 'Suction master!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (6, 'Darryl So', '36 Punggol Field, Singapore 828814, North-East', '96234567', 'darryl.so@example.com', 'Swiper no swiping!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (7, 'Gwen Low', '61 Hougang Ave 8, Singapore 538787, North-East', '97234567', 'gwen.low@example.com', 'Sweep your problems away!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO');

-- Insert admin
INSERT INTO admin (admin_id, name, email, phone_number, password)
VALUES (1, 'Bossy McBossface', 'bossy.mcbossface@example.com', '96234567', '$2a$12$PBSUs2VxiM9/ShHyp7s6TOPNobyEgbGgzyyT8qmT2KieE6OthSTxq');

-- Link admin to employees
INSERT INTO admin_employee (admin_id, employee_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- Insert sample package
INSERT INTO selected_package (package_id, type, price, house_size, house_type, hourly_rate, pax, hours)
VALUES ('W_3RM_HDB', 'Weekly', 276, '3-room', 'HDB', 23, 1, 3),
        ('W_4RM_HDB', 'Weekly', 336, '4-room', 'HDB', 21, 1, 4),
        ('W_5RM_HDB', 'Weekly', 396, '5-room', 'HDB', 20, 1, 4),
        ('W_MAISONETTE_HDB', 'Weekly', 516, 'Maisonette', 'HDB', 20, 2, 3),
        ('W_2RM_CONDO', 'Weekly', 276, '2-room', 'CONDO', 23, 1, 3),
        ('W_3RM_CONDO', 'Weekly', 336, '3-room', 'CONDO', 21, 1, 4),
        ('W_4RM_CONDO', 'Weekly', 396, '4-room', 'CONDO', 20, 1, 4),
        ('W_5RM_CONDO', 'Weekly', 456, '5-room', 'CONDO', 20, 1, 4),
        ('W_MAISONETTE_CONDO', 'Weekly', 516, 'Maisonette', 'CONDO', 20, 2, 3),
        ('W_LANDED', 'Weekly', 576, 'Landed', 'Landed', 20, 2, 3);

-- Insert sample jobs
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES (1, 1, 1, 'W_3RM_HDB', '2024-10-11', '10:00:00', 'COMPLETED', 3),
        (2, 2, 2, 'W_3RM_CONDO', '2024-10-28', '11:00:00', 'COMPLETED', 4),
        (3, 2, 2, 'W_3RM_CONDO', '2024-10-15', '11:00:00', 'COMPLETED', 4),
        (4, 3, 4, 'W_LANDED', '2024-11-11', '09:00:00', 'PENDING', 3),
        (5, 3, 4, 'W_LANDED', '2024-11-18', '09:00:00', 'PENDING', 3),
        (6, 3, 4, 'W_LANDED', '2024-11-25', '08:00:00', 'PENDING', 3),
        (7, 3, 3, 'W_MAISONETTE_CONDO', '2024-11-28', '09:00:00', 'PENDING', 3),
        (8, 2, 2, 'W_3RM_CONDO', '2024-11-15', '08:00:00', 'PENDING', 4),
        (9, 1, 1, 'W_3RM_HDB', '2024-11-11', '09:00:00', 'PENDING', 3),
        (10, 3, 3, 'W_MAISONETTE_CONDO', '2024-11-11', '12:00:00', 'PENDING', 3),
        (11, 2, 2, 'W_3RM_CONDO', '2024-11-11', '14:30:00', 'PENDING', 4),
        (12, 3, 4, 'W_LANDED', '2024-11-11', '20:00:00', 'PENDING', 3),
        (13, 2, 5, 'W_4RM_HDB', '2024-11-11', '18:00:00', 'PENDING', 4),
        (14, 2, 5, 'W_4RM_HDB', '2024-11-11', '08:00:00', 'PENDING', 4);


-- Link jobs to employees
INSERT INTO job_employee (job_id, employee_id, reasoning)
VALUES (1, 1, NULL),
        (2, 2, NULL),
        (3, 1, NULL);

-- Insert sample events into EmployeeEvents
INSERT INTO employee_event (event_id, employee_id, event_type, event_date, job_id, duration, mc_used, al_used)
VALUES (1, 1, 'job_completed', '2024-09-15', 1, 3, NULL, NULL),  -- Completed job on 15th Sep 2024
        (2, 2, 'mc_taken', '2024-09-10', NULL, NULL, 2, NULL),    -- Took 2 days MC on 10th Sep 2024
        (3, 3, 'al_taken', '2024-09-20', NULL, NULL, NULL, 1),    -- Took 1 day AL on 20th Sep 2024
        (4, 4, 'overtime', '2024-09-25', NULL, 2, NULL, NULL),    -- Worked 2 hours overtime on 25th Sep 2024
        (5, 2, 'al_taken', '2024-11-12', NULL, NULL, NULL, 2);    -- Took 2 day AL on 12th Nov 2024