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
        (20, 'Donovan Mitchell', 'donovan.mitchell@example.com', '89123456'),
        (21, 'Anthony Edwards', 'anthony.edwards@example.com', '80123456'),
        (22, 'LaMelo Ball', 'lamelo.ball@example.com', '81345678'),
        (23, 'Larry Bird', 'larry.bird@example.com', '83345678'),
        (24, 'Magic Johnson', 'magic.johnson@example.com', '84345678'),
        (25, 'Paige Bueckers', 'paige.bueckers@example.com', '85345678'),
        (26, 'Sabrina Ionescu', 'sabrina.ionescu@example.com', '86345678'),
        (27, 'Breanna Stewart', 'brenna.stewart@example.com', '87345678'),
        (28, 'Diana Taurasi', 'diana.taurasi@example.com', '88345678'),
        (29, 'Sue Bird', 'sue.bird@example.com', '89345678'),
        (30, 'Candace Parker', 'candace.parker@example.com', '80345678'),
        (31, 'Maya Moore', 'maya.moore@example.com', '81456789'),
        (32, 'Caitlin Clark', 'caitlin.clark@example.com', '83456789'),
        (33, 'Nikola Jokic', 'nikola.jokic@example.com', '84456789'),
        (34, 'Joel Embiid', 'joel.embiid@example.com', '85456789'),
        (35, 'Klay Thompson', 'klay.thompson@example.com', '86456789'),
        (36, 'Draymond Green', 'draymond.green@example.com', '87456789'),
        (37, 'Buddy Hield', 'buddy.hield@example.com', '88456789'),
        (38, 'DeAaron Fox', 'deaaron.fox@example.com', '89456789'),
        (39, 'Victor Wembanyama', 'victor.wembanyama@example.com', '80456789'),
        (40, 'Jalen Brunson', 'jalen.brunson@example.com', '81567890'),
        (41, 'Jalen Brown', 'jalen.brown@example.com', '83567890'),
        (42, 'Tony Parker', 'tony.parker@example.com', '84567890'),
        (43, 'Manu Ginobili', 'manu.ginobili@example.com', '85567890'),
        (44, 'Tim Duncan', 'tim.duncan@example.com', '86567890'),
        (45, 'Andrew Wiggins', 'andrew.wiggins@example.com', '87567890'),
        (46, 'Karl Anthony Towns', 'karl.anthony.towns@examples.com', '88567890'),
        (47, 'Jrue Holiday', 'jrue.holiday@example.com', '89567890'),
        (48, 'Zion Williamson', 'zion.williamson@example.com', '80567890'),
        (49, 'Brandon Ingram', 'brandon.ingram@example.com', '81678901'),
        (50, 'Lonzo Ball', 'lonzo.ball@example.com', '83678901');


-- Insert sample properties
INSERT INTO property (property_id, address, postal_code)
VALUES (1, '123 Changi Road, East', '427466'),
       (2, '456 Jurong West Street, West', '640456'),
       (3, '11 Mount Faber Rd, South', '099209'),
       (4, '900 Dunearn Rd, Central', '589473'),
       (5, '767 Pasir Ris Street 71', '510767'),
       (6, '132 Joo Seng Rd', '368358'),
       (7, '261 Waterloo St', '180261'),
       (8, '20 Bendemeer Rd', '339914'),
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
       (24, '700B Ang Mo Kio Ave 6', '562700'),
       (25, '1211 Upper Boon Keng Rd', '387313'),
       (26, '49 Toa Payoh Rise', '298105'),
       (27, '581 Pasir Ris Street 53', '510581'),
       (28, '24 Bah Soon Pah Rd', '769968'),
       (29, '41 Sunbird Rd', '487158'),
       (30, '244 Simei Street 5', '520244'),
       (31, '226 Tampines St 23', '521226'),
       (32, '102B Punggol Fld', '822102'),
       (33, '299B Compassvale St', '542299'),
       (34, '31 Fernvale Rd, HIGH PARK RESIDENCES', '797417'),
       (35, '287D Jurong East Street 21', '604287'),
       (36, '211 Jurong East Street 21', '600211'),
       (37, '102 Bukit Batok West Ave 6', '650102'),
       (38, '626 Bukit Batok Central, Block 626', '650626'),
       (39, '376 Bukit Batok Street 31, #05-12', '650376'),
       (40, '162 Woodlands Street 13', '730162'),
       (41, '589 Woodlands Drive 16', '730589'),
       (42, '74 Jln Kelabu Asap', '278267'),
       (43, '53 Commonwealth Dr', '142053'),
       (44, '145 Mei Ling St, Block 145', '140145'),
       (45, '76A Redhill Rd', '151076'),
       (46, '19 Jln Membina', '163019'),
       (47, '68a Neil Rd', '088836'),
       (48, 'Anson Rd, #80', '079907'),
       (49, '18 Marina Blvd','018980'),
       (50, '38 Handy Rd','229239'),
       (51, '641 Rowell Rd', '200641'),
       (52, '25 Kim Keat Cl', '328933'),
       (53, '60 Kim Keat Rd', '328827'),
       (54, '61 Merpati Rd', '379312');

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
        (21, 21),
        (22, 22),
        (23, 23),
        (24, 24),
        (25, 25),
        (26, 26),
        (27, 27),
        (28, 28),
        (29, 29),
        (30, 30),
        (31, 31),
        (32, 32),
        (33, 33),
        (34, 34),
        (35, 35),
        (36, 36),
        (37, 37),
        (38, 38),
        (39, 39),
        (40, 40),
        (41, 41),
        (42, 42),
        (43, 43),
        (44, 44),
        (45, 45),
        (46, 46),
        (47, 47),
        (48, 48),
        (49, 49),
        (50, 50),
        (1, 51),
        (2, 52),
        (3, 53),
        (4, 54);

-- Insert sample employees
INSERT INTO employee (employee_id, name, home_address, phone_number, email, short_bio, al_balance, mc_balance, password)
VALUES (1, 'Marky McFly', '4 Tampines Central 5, Singapore 529510, East', '91234567', 'marky.mcfly@example.com', 'Time traveler, occasional cleaner.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (2, 'Betty Broomstick', '60 Yishun Ave 4, Singapore 769027, North', '92234567', 'betty.broomstick@example.com', 'Sweeps like a storm!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (3, 'Charlie Mopster', '391A Orchard Rd, Singapore 238873, Central', '93234567', 'charlie.mopster@example.com', 'Champion floor mopper.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (4, 'Lenny Laundry', '117B Canberra Cres, #01-370, Singapore 752117, North', '94234567', 'lenny.laundry@example.com', 'Folding expert.', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (5, 'Harriet Hoover', '710 Clementi West Street 2, Block 710, Singapore 120710, West', '95234567', 'harriet.hoover@example.com', 'Suction master!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (6, 'Darryl So', '36 Punggol Field, Singapore 828814, North-East', '96234567', 'darryl.so@example.com', 'Swiper no swiping!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (7, 'Gwen Low', '61 Hougang Ave 8, Singapore 538787, North-East', '97234567', 'gwen.low@example.com', 'Sweep your problems away!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (8, 'Dylan Toe', '1 Bukit Batok St 25, Singapore 658882, West', '98234567', 'dylan.toe@example.com', 'High on cleaning!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (9, 'Yan Yee', '2 Simei St 3, Singapore 529889, East', '99234567', 'yan.yee@example.com', 'Cleaning ninja!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (10, 'Jasmine Tan', '3 Woodlands St 13, Singapore 738600, North', '90234567', 'jasmine.tan@example.com', 'Cleaning fairy!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (11, 'Yolk Sim', '156 Tampines Street 12, Singapore 521156, East', '91123456', 'yolk.sim@example.com', 'Cleaning wizard!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (12, 'Iris Lee', '17 Seletar Rd, #01 23, Singapore 807019, North-East', '92123456', 'iris.lee@example.com', 'Cleaning sorceress!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (13, 'Seth Yup', '1 Jalan Anak Bukit, Singapore 588998, West', '93123456', 'seth.yup@example.com', 'Cleaning guru!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
        (14, 'Pooh Jun Sui', '2 Jalan Bukit Merah, Singapore 150002, Central', '94123456', 'pooh.jun.sui@example.com', 'Honey Cleaner!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (15, 'Seng Da', '2 Jalan Bukit Merah, Singapore 150002, Central', '95123456', 'seng.da@example.com', 'Overnight warrior!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO'),
       (16, 'Adam Apple', '2 Jalan Bukit Merah, Singapore 150002, Central', '96123456', 'adam.apple@example.com', 'Cleaning Specialist!', 5, 8, '$2a$12$tBNct/zE2xNJ29sGKa/d4OGhgyJhKY55PPdPpGKs/3GxmsjAffotO');

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
    -- Week 1 (November 4 - November 10)
    (1, 1, 1, 'W_3RM_HDB', '2024-11-04', '09:00:00', 'PENDING', 3),  -- Monday morning
    (2, 2, 2, 'W_4RM_HDB', '2024-11-04', '18:00:00', 'PENDING', 4),  -- Monday night
    (3, 3, 3, 'W_MAISONETTE_HDB', '2024-11-04', '19:00:00', 'PENDING', 3),  -- Monday night
    (4, 4, 4, 'W_5RM_CONDO', '2024-11-05', '18:00:00', 'PENDING', 4),  -- Tuesday night
    (5, 5, 5, 'W_2RM_CONDO', '2024-11-06', '18:00:00', 'PENDING', 3),  -- Wednesday night
    (6, 6, 6, 'W_3RM_HDB', '2024-11-06', '19:00:00', 'PENDING', 3),  -- Wednesday night
    (7, 7, 7, 'W_4RM_CONDO', '2024-11-07', '09:00:00', 'PENDING', 4),  -- Thursday morning
    (8, 8, 8, 'W_5RM_HDB', '2024-11-07', '13:00:00', 'PENDING', 4),  -- Thursday afternoon
    (9, 9, 9, 'W_LANDED', '2024-11-08', '13:00:00', 'PENDING', 4),  -- Friday afternoon
    (10, 10, 10, 'W_3RM_HDB', '2024-11-08', '18:00:00', 'PENDING', 3),  -- Friday night
    (11, 11, 11, 'W_3RM_CONDO', '2024-11-09', '09:00:00', 'PENDING', 3),  -- Saturday morning
    (12, 12, 12, 'W_4RM_HDB', '2024-11-09', '18:00:00', 'PENDING', 4),  -- Saturday night
    (13, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-10', '09:00:00', 'PENDING', 3),  -- Sunday morning
    (14, 14, 14, 'W_3RM_HDB', '2024-11-10', '13:00:00', 'PENDING', 3),  -- Sunday afternoon
    (85, 21, 21, 'W_2RM_CONDO', '2024-11-04', '08:00:00', 'PENDING', 3),  -- Monday
    (86, 22, 22, 'W_3RM_HDB', '2024-11-05', '13:00:00', 'PENDING', 3),    -- Tuesday
    (87, 23, 23, 'W_5RM_HDB', '2024-11-06', '08:00:00', 'PENDING', 4),    -- Wednesday
    (88, 24, 24, 'W_4RM_HDB', '2024-11-07', '08:00:00', 'PENDING', 4),    -- Thursday
    (89, 25, 25, 'W_LANDED', '2024-11-08', '18:00:00', 'PENDING', 4),     -- Friday
    (90, 26, 26, 'W_MAISONETTE_CONDO', '2024-11-09', '13:00:00', 'PENDING', 3.5),  -- Saturday
    (91, 27, 27, 'W_5RM_CONDO', '2024-11-10', '09:00:00', 'PENDING', 3),  -- Sunday
    (92, 28, 28, 'W_3RM_CONDO', '2024-11-05', '08:00:00', 'PENDING', 4),  -- Tuesday
    (93, 29, 29, 'W_4RM_CONDO', '2024-11-07', '13:00:00', 'PENDING', 4),  -- Thursday
    (94, 30, 30, 'W_5RM_HDB', '2024-11-08', '08:00:00', 'PENDING', 4),    -- Friday
    (95, 31, 31, 'W_3RM_CONDO', '2024-11-10', '07:30:00', 'PENDING', 4),  -- Sunday
    (96, 32, 32, 'W_MAISONETTE_HDB', '2024-11-04', '18:00:00', 'PENDING', 3), -- Monday
    (97, 33, 33, 'W_LANDED', '2024-11-06', '08:00:00', 'PENDING', 4),     -- Wednesday
    (98, 34, 34, 'W_5RM_CONDO', '2024-11-07', '09:00:00', 'PENDING', 3),  -- Thursday
    (99, 35, 35, 'W_3RM_HDB', '2024-11-09', '08:00:00', 'PENDING', 4),    -- Saturday
    (100, 36, 36, 'W_4RM_HDB', '2024-11-05', '08:00:00', 'PENDING', 4),   -- Tuesday
    (101, 37, 37, 'W_MAISONETTE_HDB', '2024-11-04', '14:00:00', 'PENDING', 3), -- Monday
    (102, 38, 38, 'W_5RM_HDB', '2024-11-08', '08:00:00', 'PENDING', 4),   -- Friday
    (103, 39, 39, 'W_2RM_CONDO', '2024-11-07', '08:00:00', 'PENDING', 3), -- Thursday
    (104, 40, 40, 'W_4RM_HDB', '2024-11-06', '18:00:00', 'PENDING', 4),   -- Wednesday

    -- Week 2 (November 11 - November 17)
    (15, 1, 1, 'W_3RM_HDB', '2024-11-11', '09:00:00', 'PENDING', 3),  -- Monday morning
    (16, 2, 2, 'W_4RM_HDB', '2024-11-11', '18:00:00', 'PENDING', 4),  -- Monday night
    (17, 3, 3, 'W_MAISONETTE_HDB', '2024-11-11', '19:00:00', 'PENDING', 3),  -- Monday night
    (18, 4, 4, 'W_5RM_CONDO', '2024-11-12', '18:00:00', 'PENDING', 4),  -- Tuesday night
    (19, 5, 5, 'W_2RM_CONDO', '2024-11-13', '18:00:00', 'PENDING', 3),  -- Wednesday night
    (20, 6, 6, 'W_3RM_HDB', '2024-11-13', '19:00:00', 'PENDING', 3),  -- Wednesday night
    (21, 7, 7, 'W_4RM_CONDO', '2024-11-14', '09:00:00', 'PENDING', 4),  -- Thursday morning
    (22, 8, 8, 'W_5RM_HDB', '2024-11-14', '13:00:00', 'PENDING', 4),  -- Thursday afternoon
    (23, 9, 9, 'W_LANDED', '2024-11-15', '13:00:00', 'PENDING', 4),  -- Friday afternoon
    (24, 10, 10, 'W_3RM_HDB', '2024-11-15', '18:00:00', 'PENDING', 3),  -- Friday night
    (25, 11, 11, 'W_3RM_CONDO', '2024-11-16', '09:00:00', 'PENDING', 3),  -- Saturday morning
    (26, 12, 12, 'W_4RM_HDB', '2024-11-16', '18:00:00', 'PENDING', 4),  -- Saturday night
    (27, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-17', '09:00:00', 'PENDING', 3),  -- Sunday morning
    (28, 14, 14, 'W_3RM_HDB', '2024-11-17', '13:00:00', 'PENDING', 3),  -- Sunday afternoon
    (105, 21, 21, 'W_2RM_CONDO', '2024-11-11', '08:00:00', 'PENDING', 3),
    (106, 22, 22, 'W_3RM_HDB', '2024-11-12', '13:00:00', 'PENDING', 3),
    (107, 23, 23, 'W_5RM_HDB', '2024-11-13', '08:00:00', 'PENDING', 4),
    (108, 24, 24, 'W_4RM_HDB', '2024-11-14', '08:00:00', 'PENDING', 4),
    (109, 25, 25, 'W_LANDED', '2024-11-15', '18:00:00', 'PENDING', 4),
    (110, 26, 26, 'W_MAISONETTE_CONDO', '2024-11-16', '13:00:00', 'PENDING', 3.5),
    (111, 27, 27, 'W_5RM_CONDO', '2024-11-17', '09:00:00', 'PENDING', 3),
    (112, 28, 28, 'W_3RM_CONDO', '2024-11-12', '08:00:00', 'PENDING', 4),
    (113, 29, 29, 'W_4RM_CONDO', '2024-11-14', '13:00:00', 'PENDING', 4),
    (114, 30, 30, 'W_5RM_HDB', '2024-11-15', '08:00:00', 'PENDING', 4),
    (115, 31, 31, 'W_3RM_CONDO', '2024-11-17', '07:30:00', 'PENDING', 4),
    (116, 32, 32, 'W_MAISONETTE_HDB', '2024-11-11', '18:00:00', 'PENDING', 3),
    (117, 33, 33, 'W_LANDED', '2024-11-13', '08:00:00', 'PENDING', 4),
    (118, 34, 34, 'W_5RM_CONDO', '2024-11-14', '09:00:00', 'PENDING', 3),
    (119, 35, 35, 'W_3RM_HDB', '2024-11-16', '08:00:00', 'PENDING', 4),
    (120, 36, 36, 'W_4RM_HDB', '2024-11-12', '08:00:00', 'PENDING', 4),
    (121, 37, 37, 'W_MAISONETTE_HDB', '2024-11-11', '14:00:00', 'PENDING', 3),
    (122, 38, 38, 'W_5RM_HDB', '2024-11-15', '08:00:00', 'PENDING', 4),
    (123, 39, 39, 'W_2RM_CONDO', '2024-11-14', '08:00:00', 'PENDING', 3),
    (124, 40, 40, 'W_4RM_HDB', '2024-11-13', '18:00:00', 'PENDING', 4),

    -- Week 3 (November 18 - November 24)
    (29, 1, 1, 'W_3RM_HDB', '2024-11-18', '09:00:00', 'PENDING', 3),  -- Monday morning
    (30, 2, 2, 'W_4RM_HDB', '2024-11-18', '18:00:00', 'PENDING', 4),  -- Monday night
    (31, 3, 3, 'W_MAISONETTE_HDB', '2024-11-18', '19:00:00', 'PENDING', 3),  -- Monday night
    (32, 4, 4, 'W_5RM_CONDO', '2024-11-19', '18:00:00', 'PENDING', 4),  -- Tuesday night
    (33, 5, 5, 'W_2RM_CONDO', '2024-11-20', '18:00:00', 'PENDING', 3),  -- Wednesday night
    (34, 6, 6, 'W_3RM_HDB', '2024-11-20', '19:00:00', 'PENDING', 3),  -- Wednesday night
    (35, 7, 7, 'W_4RM_CONDO', '2024-11-21', '09:00:00', 'PENDING', 4),  -- Thursday morning
    (36, 8, 8, 'W_5RM_HDB', '2024-11-21', '13:00:00', 'PENDING', 4),  -- Thursday afternoon
    (37, 9, 9, 'W_LANDED', '2024-11-22', '13:00:00', 'PENDING', 4),  -- Friday afternoon
    (38, 10, 10, 'W_3RM_HDB', '2024-11-22', '18:00:00', 'PENDING', 3),  -- Friday night
    (39, 11, 11, 'W_3RM_CONDO', '2024-11-23', '09:00:00', 'PENDING', 3),  -- Saturday morning
    (40, 12, 12, 'W_4RM_HDB', '2024-11-23', '18:00:00', 'PENDING', 4),  -- Saturday night
    (41, 13, 13, 'W_MAISONETTE_CONDO', '2024-11-24', '09:00:00', 'PENDING', 3),  -- Sunday morning
    (42, 14, 14, 'W_3RM_HDB', '2024-11-24', '13:00:00', 'PENDING', 3),  -- Sunday afternoon
    (125, 21, 21, 'W_2RM_CONDO', '2024-11-18', '08:00:00', 'PENDING', 3),
    (126, 22, 22, 'W_3RM_HDB', '2024-11-19', '13:00:00', 'PENDING', 3),
    (127, 23, 23, 'W_5RM_HDB', '2024-11-20', '08:00:00', 'PENDING', 4),
    (128, 24, 24, 'W_4RM_HDB', '2024-11-21', '08:00:00', 'PENDING', 4),
    (129, 25, 25, 'W_LANDED', '2024-11-22', '18:00:00', 'PENDING', 4),
    (130, 26, 26, 'W_MAISONETTE_CONDO', '2024-11-23', '13:00:00', 'PENDING', 3.5),
    (131, 27, 27, 'W_5RM_CONDO', '2024-11-24', '09:00:00', 'PENDING', 3),
    (132, 28, 28, 'W_3RM_CONDO', '2024-11-19', '08:00:00', 'PENDING', 4),
    (133, 29, 29, 'W_4RM_CONDO', '2024-11-21', '13:00:00', 'PENDING', 4),
    (134, 30, 30, 'W_5RM_HDB', '2024-11-22', '08:00:00', 'PENDING', 4),
    (135, 31, 31, 'W_3RM_CONDO', '2024-11-24', '07:30:00', 'PENDING', 4),
    (136, 32, 32, 'W_MAISONETTE_HDB', '2024-11-18', '18:00:00', 'PENDING', 3),
    (137, 33, 33, 'W_LANDED', '2024-11-20', '08:00:00', 'PENDING', 4),
    (138, 34, 34, 'W_5RM_CONDO', '2024-11-21', '09:00:00', 'PENDING', 3),
    (139, 35, 35, 'W_3RM_HDB', '2024-11-23', '08:00:00', 'PENDING', 4),
    (140, 36, 36, 'W_4RM_HDB', '2024-11-19', '08:00:00', 'PENDING', 4),
    (141, 37, 37, 'W_MAISONETTE_HDB', '2024-11-18', '14:00:00', 'PENDING', 3),
    (142, 38, 38, 'W_5RM_HDB', '2024-11-22', '08:00:00', 'PENDING', 4),
    (143, 39, 39, 'W_2RM_CONDO', '2024-11-21', '08:00:00', 'PENDING', 3),
    (144, 40, 40, 'W_4RM_HDB', '2024-11-20', '18:00:00', 'PENDING', 4),


    -- Week 4 (November 25 - December 1)
    (43, 1, 1, 'W_3RM_HDB', '2024-11-25', '09:00:00', 'PENDING', 3),  -- Monday morning
    (44, 2, 2, 'W_4RM_HDB', '2024-11-25', '18:00:00', 'PENDING', 4),  -- Monday night
    (45, 3, 3, 'W_MAISONETTE_HDB', '2024-11-25', '19:00:00', 'PENDING', 3),  -- Monday night
    (46, 4, 4, 'W_5RM_CONDO', '2024-11-26', '18:00:00', 'PENDING', 4),  -- Tuesday night
    (47, 5, 5, 'W_2RM_CONDO', '2024-11-27', '18:00:00', 'PENDING', 3),  -- Wednesday night
    (48, 6, 6, 'W_3RM_HDB', '2024-11-27', '19:00:00', 'PENDING', 3),  -- Wednesday night
    (49, 7, 7, 'W_4RM_CONDO', '2024-11-28', '09:00:00', 'PENDING', 4),  -- Thursday morning
    (50, 8, 8, 'W_5RM_HDB', '2024-11-28', '13:00:00', 'PENDING', 4),  -- Thursday afternoon
    (51, 9, 9, 'W_LANDED', '2024-11-29', '13:00:00', 'PENDING', 4),  -- Friday afternoon
    (52, 10, 10, 'W_3RM_HDB', '2024-11-29', '18:00:00', 'PENDING', 3),  -- Friday night
    (53, 11, 11, 'W_3RM_CONDO', '2024-11-30', '09:00:00', 'PENDING', 3),  -- Saturday morning
    (54, 12, 12, 'W_4RM_HDB', '2024-11-30', '18:00:00', 'PENDING', 4),  -- Saturday night
    (55, 13, 13, 'W_MAISONETTE_CONDO', '2024-12-01', '09:00:00', 'PENDING', 3),  -- Sunday morning
    (56, 14, 14, 'W_3RM_HDB', '2024-12-01', '13:00:00', 'PENDING', 3),  -- Sunday afternoon
    (145, 21, 21, 'W_2RM_CONDO', '2024-11-25', '08:00:00', 'PENDING', 3),
    (146, 22, 22, 'W_3RM_HDB', '2024-11-26', '13:00:00', 'PENDING', 3),
    (147, 23, 23, 'W_5RM_HDB', '2024-11-27', '08:00:00', 'PENDING', 4),
    (148, 24, 24, 'W_4RM_HDB', '2024-11-28', '08:00:00', 'PENDING', 4),
    (149, 25, 25, 'W_LANDED', '2024-11-29', '18:00:00', 'PENDING', 4),
    (150, 26, 26, 'W_MAISONETTE_CONDO', '2024-11-30', '13:00:00', 'PENDING', 3.5),
    (151, 27, 27, 'W_5RM_CONDO', '2024-12-01', '09:00:00', 'PENDING', 3),
    (152, 28, 28, 'W_3RM_CONDO', '2024-11-26', '08:00:00', 'PENDING', 4),
    (153, 29, 29, 'W_4RM_CONDO', '2024-11-28', '13:00:00', 'PENDING', 4),
    (154, 30, 30, 'W_5RM_HDB', '2024-11-29', '08:00:00', 'PENDING', 4),
    (155, 31, 31, 'W_3RM_CONDO', '2024-12-01', '07:30:00', 'PENDING', 4),
    (156, 32, 32, 'W_MAISONETTE_HDB', '2024-11-25', '18:00:00', 'PENDING', 3),
    (157, 33, 33, 'W_LANDED', '2024-11-27', '08:00:00', 'PENDING', 4),
    (158, 34, 34, 'W_5RM_CONDO', '2024-11-28', '09:00:00', 'PENDING', 3),
    (159, 35, 35, 'W_3RM_HDB', '2024-11-30', '08:00:00', 'PENDING', 4),
    (160, 36, 36, 'W_4RM_HDB', '2024-11-26', '08:00:00', 'PENDING', 4),
    (161, 37, 37, 'W_MAISONETTE_HDB', '2024-11-25', '14:00:00', 'PENDING', 3),
    (162, 38, 38, 'W_5RM_HDB', '2024-11-29', '08:00:00', 'PENDING', 4),
    (163, 39, 39, 'W_2RM_CONDO', '2024-11-28', '08:00:00', 'PENDING', 3),
    (164, 40, 40, 'W_4RM_HDB', '2024-11-27', '18:00:00', 'PENDING', 4),

    -- Bi-Weekly Jobs (6 properties in Weeks 1 and 3)
    (57, 15, 15, 'BW_3RM_HDB', '2024-11-05', '09:00:00', 'PENDING', 3),
    (58, 16, 16, 'BW_4RM_CONDO', '2024-11-05', '13:00:00', 'PENDING', 4),
    (59, 17, 17, 'BW_5RM_HDB', '2024-11-05', '14:00:00', 'PENDING', 4),
    (60, 18, 18, 'BW_MAISONETTE_CONDO', '2024-11-06', '09:00:00', 'PENDING', 3),
    (61, 19, 19, 'BW_3RM_HDB', '2024-11-06', '13:00:00', 'PENDING', 3),
    (62, 20, 20, 'BW_4RM_HDB', '2024-11-06', '14:00:00', 'PENDING', 4),
    (165, 41, 41, 'BW_2RM_CONDO', '2024-11-04', '08:00:00', 'PENDING', 3),
    (166, 42, 42, 'BW_3RM_HDB', '2024-11-05', '14:00:00', 'PENDING', 3),
    (167, 43, 43, 'BW_5RM_HDB', '2024-11-06', '13:00:00', 'PENDING', 4),
    (168, 44, 44, 'BW_4RM_HDB', '2024-11-07', '08:00:00', 'PENDING', 4),
    (169, 45, 45, 'BW_LANDED', '2024-11-08', '13:00:00', 'PENDING', 4),
    (170, 46, 46, 'BW_MAISONETTE_CONDO', '2024-11-09', '09:00:00', 'PENDING', 3),

    -- Repeat Bi-Weekly Jobs for Week 3
    (63, 15, 15, 'BW_3RM_HDB', '2024-11-19', '09:00:00', 'PENDING', 3),
    (64, 16, 16, 'BW_4RM_CONDO', '2024-11-19', '13:00:00', 'PENDING', 4),
    (65, 17, 17, 'BW_5RM_HDB', '2024-11-19', '14:00:00', 'PENDING', 4),
    (66, 18, 18, 'BW_MAISONETTE_CONDO', '2024-11-20', '09:00:00', 'PENDING', 3),
    (67, 19, 19, 'BW_3RM_HDB', '2024-11-20', '13:00:00', 'PENDING', 3),
    (68, 20, 20, 'BW_4RM_HDB', '2024-11-20', '14:00:00', 'PENDING', 4),
    (171, 41, 41, 'BW_2RM_CONDO', '2024-11-18', '08:00:00', 'PENDING', 3),
    (172, 42, 42, 'BW_3RM_HDB', '2024-11-19', '14:00:00', 'PENDING', 3),
    (173, 43, 43, 'BW_5RM_HDB', '2024-11-20', '13:00:00', 'PENDING', 4),
    (174, 44, 44, 'BW_4RM_HDB', '2024-11-21', '08:00:00', 'PENDING', 4),
    (175, 45, 45, 'BW_LANDED', '2024-11-22', '13:00:00', 'PENDING', 4),
    (176, 46, 46, 'BW_MAISONETTE_CONDO', '2024-11-23', '09:00:00', 'PENDING', 3),

    -- Additional Bi-Weekly Jobs (4 properties in Weeks 2 and 4)
    (69, 1, 51, 'BW_3RM_CONDO', '2024-11-12', '09:00:00', 'PENDING', 3),
    (70, 2, 52, 'BW_5RM_HDB', '2024-11-12', '13:00:00', 'PENDING', 4),
    (71, 3, 53, 'BW_2RM_CONDO', '2024-11-12', '14:00:00', 'PENDING', 3),
    (72, 4, 54, 'BW_LANDED', '2024-11-13', '09:00:00', 'PENDING', 4),
    (177, 47, 47, 'BW_5RM_CONDO', '2024-11-12', '08:00:00', 'PENDING', 3),
    (178, 48, 48, 'BW_4RM_CONDO', '2024-11-14', '13:00:00', 'PENDING', 4),
    (179, 49, 49, 'BW_3RM_CONDO', '2024-11-15', '09:00:00', 'PENDING', 4),
    (180, 50, 50, 'BW_MAISONETTE_HDB', '2024-11-16', '08:00:00', 'PENDING', 3),

    -- Repeat Bi-Weekly Jobs for Week 4
    (73, 1, 51, 'BW_3RM_CONDO', '2024-11-26', '09:00:00', 'PENDING', 3),
    (74, 2, 52, 'BW_5RM_HDB', '2024-11-26', '13:00:00', 'PENDING', 4),
    (75, 3, 53, 'BW_2RM_CONDO', '2024-11-26', '14:00:00', 'PENDING', 3),
    (76, 4, 54, 'BW_LANDED', '2024-11-27', '09:00:00', 'PENDING', 4),
    (181, 47, 47, 'BW_5RM_CONDO', '2024-11-26', '08:00:00', 'PENDING', 3),
    (182, 48, 48, 'BW_4RM_CONDO', '2024-11-28', '13:00:00', 'PENDING', 4),
    (183, 49, 49, 'BW_3RM_CONDO', '2024-11-29', '09:00:00', 'PENDING', 4),
    (184, 50, 50, 'BW_MAISONETTE_HDB', '2024-11-30', '08:00:00', 'PENDING', 3),

    -- Insert completed sample jobs (4 properties in Weeks 2 and 4 of October)
    (77, 1, 51, 'BW_3RM_CONDO', '2024-10-15', '09:00:00', 'COMPLETED', 3),
    (78, 2, 52, 'BW_5RM_HDB', '2024-10-15', '10:00:00', 'COMPLETED', 4),
    (79, 3, 53, 'BW_2RM_CONDO', '2024-10-15', '13:00:00', 'COMPLETED', 3),
    (80, 4, 54, 'BW_LANDED', '2024-10-15', '13:00:00', 'COMPLETED', 4),

    -- Repeat Bi-Weekly Jobs for Week 4
    (81, 1, 51, 'BW_3RM_CONDO', '2024-10-29', '09:00:00', 'COMPLETED', 3),
    (82, 2, 52, 'BW_5RM_HDB', '2024-10-29', '10:00:00', 'COMPLETED', 4),
    (83, 3, 53, 'BW_2RM_CONDO', '2024-10-29', '13:00:00', 'COMPLETED', 3),
    (84, 4, 54, 'BW_LANDED', '2024-10-29', '13:00:00', 'COMPLETED', 4);

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

-- Insert sample employee leaves
INSERT INTO employee_leaves (employee_leave_id, employee_id, leave_type, application_date_time, start_date, end_date, status, comments, mc_proof_uploaded, mc_proof_img)
VALUES (1, 1, 'MC', '2024-09-01 10:00:00', '2024-09-05', '2024-09-07', 'APPROVED', 'Medical leave for flu', TRUE, 'mc_proof_1.jpg'),
       (2, 2, 'AL', '2024-09-10 09:00:00', '2024-09-15', '2024-09-20', 'APPROVED', 'Annual leave for vacation', FALSE, NULL),
       (3, 3, 'MC', '2024-10-01 11:00:00', '2024-10-05', '2024-10-07', 'PENDING', 'Medical leave for surgery', TRUE, 'mc_proof_2.jpg'),
       (4, 4, 'AL', '2024-10-15 08:00:00', '2024-10-20', '2024-10-25', 'REJECTED', 'Annual leave for personal reasons', FALSE, NULL),
       (5, 5, 'MC', '2024-11-01 10:00:00', '2024-11-05', '2024-11-07', 'APPROVED', 'Medical leave for injury', TRUE, 'mc_proof_3.jpg');

-- Insert sample subscription
INSERT INTO `subscription` (`subscription_id`, `client_id`, `property_id`, `package_id`, `package_type`, `job_day`, `job_starttime`, `job_endtime`, `subscription_status`)
VALUES
    (1,1, 1,'W_3RM_HDB', 'WEEKLY', 'MONDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (2, 2, 2,'W_4RM_HDB', 'WEEKLY', 'MONDAY', '18:00:00', '22:00:00', 'ACTIVE'),
    (3, 3, 3,'W_MAISONETTE_HDB', 'WEEKLY', 'MONDAY', '19:00:00', '22:00:00', 'ACTIVE'),
    (4, 4, 4,'W_5RM_CONDO', 'WEEKLY', 'TUESDAY', '18:00:00', '22:00:00', 'ACTIVE'),
    (5, 5, 5,'W_2RM_CONDO', 'WEEKLY', 'WEDNESDAY', '18:00:00', '21:00:00', 'ACTIVE'),
    (6, 6, 6,'W_3RM_HDB', 'WEEKLY', 'WEDNESDAY', '19:00:00', '22:00:00', 'ACTIVE'),
    (7, 7, 7,'W_4RM_CONDO', 'WEEKLY', 'THURSDAY', '09:00:00', '13:00:00', 'ACTIVE'),
    (8, 8, 8,'W_5RM_HDB', 'BI_WEEKLY', 'THURSDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (9, 9, 9,'W_LANDED', 'WEEKLY', 'FRIDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (10, 10, 10,'W_3RM_HDB', 'WEEKLY', 'FRIDAY', '18:00:00', '21:00:00', 'ACTIVE'),
    (11, 11, 11,'W_3RM_CONDO', 'BI_WEEKLY', 'SATURDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (12, 12, 12,'W_4RM_HDB', 'WEEKLY', 'SATURDAY', '18:00:00', '22:00:00', 'ACTIVE'),
    (13, 13, 13,'W_MAISONETTE_CONDO', 'WEEKLY', 'SUNDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (14, 14, 14,'W_3RM_HDB', 'WEEKLY', 'SUNDAY', '13:00:00', '16:00:00', 'ACTIVE'),
    (15, 15, 15,'BW_3RM_HDB', 'BI_WEEKLY', 'TUESDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (16, 16, 16,'BW_4RM_CONDO', 'BI_WEEKLY', 'TUESDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (17, 17, 17,'BW_5RM_HDB', 'BI_WEEKLY', 'TUESDAY', '14:00:00', '18:00:00', 'ACTIVE'),
    (18, 18, 18,'BW_MAISONETTE_CONDO', 'BI_WEEKLY', 'WEDNESDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (19, 19, 19,'BW_3RM_HDB', 'BI_WEEKLY', 'WEDNESDAY', '13:00:00', '16:00:00', 'ACTIVE'),
    (20, 20, 20,'BW_4RM_HDB', 'BI_WEEKLY', 'WEDNESDAY', '14:00:00', '18:00:00', 'ACTIVE'),

    -- Weekly packages 21-40 with unique housing types and start times as requested
    (21, 21, 21,'W_2RM_CONDO', 'WEEKLY', 'MONDAY', '08:00:00', '11:00:00', 'ACTIVE'),
    (22, 22, 22,'W_3RM_HDB', 'WEEKLY', 'TUESDAY', '13:00:00', '16:00:00', 'ACTIVE'),
    (23, 23, 23,'W_5RM_HDB', 'WEEKLY', 'WEDNESDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (24, 24, 24,'W_4RM_HDB', 'WEEKLY', 'THURSDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (25, 25, 25,'W_LANDED', 'WEEKLY', 'FRIDAY', '18:00:00', '22:00:00', 'ACTIVE'),
    (26, 26, 26,'W_MAISONETTE_CONDO', 'WEEKLY', 'SATURDAY', '13:00:00', '16:30:00', 'ACTIVE'),
    (27, 27, 27,'W_5RM_CONDO', 'WEEKLY', 'SUNDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (28, 28, 28,'W_3RM_CONDO', 'WEEKLY', 'TUESDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (29, 29, 29,'W_4RM_CONDO', 'WEEKLY', 'THURSDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (30, 30, 30,'W_5RM_HDB', 'WEEKLY', 'FRIDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (31, 31, 31,'W_3RM_CONDO', 'WEEKLY', 'SUNDAY', '07:30:00', '11:30:00', 'ACTIVE'),
    (32, 32, 32,'W_MAISONETTE_HDB', 'WEEKLY', 'MONDAY', '18:00:00', '21:00:00', 'ACTIVE'),
    (33, 33, 33,'W_LANDED', 'WEEKLY', 'WEDNESDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (34, 34, 34,'W_5RM_CONDO', 'WEEKLY', 'THURSDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (35, 35, 35,'W_3RM_HDB', 'WEEKLY', 'SATURDAY', '08:00:00', '11:00:00', 'ACTIVE'),
    (36, 36, 36,'W_4RM_HDB', 'WEEKLY', 'TUESDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (37, 37, 37,'W_MAISONETTE_HDB', 'WEEKLY', 'MONDAY', '14:00:00', '17:00:00', 'ACTIVE'),
    (38, 38, 38,'W_5RM_HDB', 'WEEKLY', 'FRIDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (39, 39, 39,'W_2RM_CONDO', 'WEEKLY', 'THURSDAY', '08:00:00', '11:00:00', 'ACTIVE'),
    (40, 40, 40,'W_4RM_HDB', 'WEEKLY', 'WEDNESDAY', '18:00:00', '22:00:00', 'ACTIVE'),

    -- Biweekly packages 41-50 with unique housing types and start times
    (41, 41, 41,'BW_2RM_CONDO', 'BI_WEEKLY', 'MONDAY', '08:00:00', '11:00:00', 'ACTIVE'),
    (42, 42, 42,'BW_3RM_HDB', 'BI_WEEKLY', 'TUESDAY', '14:00:00', '17:00:00', 'ACTIVE'),
    (43, 43, 43,'BW_5RM_HDB', 'BI_WEEKLY', 'WEDNESDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (44, 44, 44,'BW_4RM_HDB', 'BI_WEEKLY', 'THURSDAY', '08:00:00', '12:00:00', 'ACTIVE'),
    (45, 45, 45,'BW_LANDED', 'BI_WEEKLY', 'FRIDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (46, 46, 46,'BW_MAISONETTE_CONDO', 'BI_WEEKLY', 'SATURDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (47, 47, 47,'BW_5RM_CONDO', 'BI_WEEKLY', 'SUNDAY', '08:00:00', '11:00:00', 'ACTIVE'),
    (48, 48, 48,'BW_4RM_CONDO', 'BI_WEEKLY', 'TUESDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (49, 49, 49,'BW_3RM_CONDO', 'BI_WEEKLY', 'THURSDAY', '09:00:00', '13:00:00', 'ACTIVE'),
    (50, 50, 50,'BW_MAISONETTE_HDB', 'BI_WEEKLY', 'FRIDAY', '08:00:00', '11:00:00', 'ACTIVE'),

    -- Second properties
    (51, 1, 51,'BW_3RM_CONDO', 'BI_WEEKLY', 'TUESDAY', '09:00:00', '12:00:00', 'ACTIVE'),
    (52, 2, 52,'BW_5RM_HDB', 'BI_WEEKLY', 'TUESDAY', '13:00:00', '17:00:00', 'ACTIVE'),
    (53, 3, 53,'BW_2RM_CONDO', 'BI_WEEKLY', 'TUESDAY', '14:00:00', '17:00:00', 'ACTIVE'),
    (54, 4, 54,'BW_LANDED', 'BI_WEEKLY', 'WEDNESDAY', '09:00:00', '13:00:00', 'ACTIVE');
