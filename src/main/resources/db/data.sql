-- Insert data into database
-- Insert sample clients
INSERT INTO client (client_id, name, email, phone_number)
VALUES (1, 'Tommy Tallwalk', 'tommy.tallwalk@example.com', '82345678'),
       (2, 'Sally Shortstack', 'sally.shortstack@example.com', '81234567');

-- Insert sample properties
INSERT INTO property (property_id, address, postal_code)
VALUES (1, '123 Changi Road, East', '427466'),
       (2, '456 Jurong West Street, West', '640456');

-- Link properties to clients
INSERT INTO client_property (client_id, property_id)
VALUES (1, 1),  
       (2, 2);  

-- Insert sample employees
INSERT INTO employee (employee_id, name, home_address, phone_number, email, short_bio, al_balance, mc_balance)
VALUES (1, 'Marky McFly', '789 Tampines Avenue, East', '91234567', 'marky.mcfly@example.com', 'Time traveler, occasional cleaner.', 5, 8),
       (2, 'Betty Broomstick', '321 Yishun Ring Road, North', '92234567', 'betty.broomstick@example.com', 'Sweeps like a storm!', 5, 8),
       (3, 'Charlie Mopster', '654 Orchard Road, Central', '93234567', 'charlie.mopster@example.com', 'Champion floor mopper.', 5, 8),
       (4, 'Lenny Laundry', '888 Sembawang Road, North', '94234567', 'lenny.laundry@example.com', 'Folding expert.', 5, 8),
       (5, 'Harriet Hoover', '567 Clementi Avenue, West', '95234567', 'harriet.hoover@example.com', 'Suction master!', 5, 8);

-- Insert admin
INSERT INTO admin (admin_id, name, email, phone_number)
VALUES (1, 'Bossy McBossface', 'bossy.mcbossface@example.com', '96234567');

-- Link admin to employees
INSERT INTO admin_employee (admin_id, employee_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- Insert sample package
INSERT INTO package (package_id, type, price, house_size, house_type, hourly_rate, pax, hours)
VALUES ('W_3RM_HDB', 'Weekly', 276, '3-room', 'HDB', 23, 1, 3),
('W_4RM_HDB', 'Weekly', 336, '4-room', 'HDB', 21, 1, 4),
('W_3RM_CONDO', 'Weekly', 336, '3-room', 'CONDO', 21, 1, 4);

-- Insert sample jobs
INSERT INTO job (job_id, client_id, property_id, package_id, date, start_time, status, actual_duration)
VALUES (1, 1, 1, 'W_3RM_HDB', '2024-10-01', '10:00:00', 'Scheduled', 3),
       (2, 2, 2, 'W_3RM_CONDO', '2024-09-28', '11:00:00', 'Scheduled', 2);

-- Link jobs to employees
INSERT INTO job_employee (job_id, employee_id)
VALUES (1, 1), 
       (2, 2);