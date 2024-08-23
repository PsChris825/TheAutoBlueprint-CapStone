DROP DATABASE IF EXISTS auto_blueprint;
CREATE DATABASE auto_blueprint;
USE auto_blueprint;

CREATE TABLE app_user (
    app_user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(2048) NOT NULL,
    enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE app_role (
    app_role_id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user_role (
    app_user_id INT NOT NULL,
    app_role_id INT NOT NULL,
    CONSTRAINT pk_app_user_role PRIMARY KEY (app_user_id, app_role_id),
    CONSTRAINT fk_app_user_role_user_id FOREIGN KEY (app_user_id)
        REFERENCES app_user(app_user_id),
    CONSTRAINT fk_app_user_role_role_id FOREIGN KEY (app_role_id)
        REFERENCES app_role(app_role_id)
);

CREATE TABLE car (
    car_id INT PRIMARY KEY AUTO_INCREMENT,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    engine VARCHAR(255),
    power INT,
    drive_type VARCHAR(255),
    transmission_type VARCHAR(255)
);

CREATE TABLE part (
    part_id INT PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255),
    manufacturer VARCHAR(255),
    OEM_number VARCHAR(255),
    weight DECIMAL(10, 2),
    details TEXT,
    category_id INT REFERENCES part_category(category_id) ON DELETE SET NULL,
    car_id INT REFERENCES car(car_id) ON DELETE SET NULL
);

CREATE TABLE plan_part (
    plan_part_id INT PRIMARY KEY AUTO_INCREMENT,
    part_id INT REFERENCES part(part_id) ON DELETE CASCADE,
    plan_id INT REFERENCES modification_plan(plan_id) ON DELETE CASCADE,
    price DECIMAL(10, 2),
    time_to_install INT,
    tutorial_url VARCHAR(255),
    supplier_url VARCHAR(255)
);

CREATE TABLE modification_plan (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    app_user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
    car_id INT REFERENCES car(car_id) ON DELETE CASCADE,
    plan_name VARCHAR(255),
    plan_description TEXT,
    plan_hours_of_completion INT,
    budget DECIMAL(10, 2),
    total_cost DECIMAL(10, 2),
    cost_versus_budget DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE part_category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE post (
    post_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
    username VARCHAR(50), -- Add username column
    title VARCHAR(255),
    post_description TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `comment` (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT REFERENCES post(post_id) ON DELETE CASCADE,
    user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
    username VARCHAR(50), -- Add username column
    comment_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SET SQL_SAFE_UPDATES = 0;
DELETE FROM app_user_role;
DELETE FROM app_user;
DELETE FROM plan_part;
DELETE FROM car;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO app_role (`name`) VALUES
    ('USER'),
    ('ADMIN');

-- Passwords are set to "P@ssw0rd!"
INSERT INTO app_user (username, password_hash, enabled) VALUES
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

INSERT INTO app_user_role (app_user_id, app_role_id) VALUES
    (1, 2),
    (2, 1);

INSERT INTO part_category (category_name) VALUES
    ('Accessories'),
    ('Air Conditioning'),
    ('Axle Drive'),
    ('Axle Mounting/Steering/Wheels'),
    ('Belt Drive'),
    ('Body'),
    ('Braking System'),
    ('Clutch/Attachment Parts'),
    ('Comfort Systems'),
    ('Cooling System'),
    ('Electrics'),
    ('Engine'),
    ('Exhaust System'),
    ('Filter'),
    ('Fuel Mixture Formation'),
    ('Fuel Supply System'),
    ('Headlight Cleaning'),
    ('Heating/Ventilation'),
    ('Interior'),
    ('Locking System'),
    ('Maintenance Service Parts'),
    ('Motorcycle'),
    ('Spark/Glow Ignition'),
    ('Steering'),
    ('Suspension/Damping'),
    ('Transmission'),
    ('Wheel Drive'),
    ('Wheels/Tires'),
    ('Window Cleaning');
    
INSERT INTO car (make, model, year, engine, power, drive_type, transmission_type) VALUES
('Ford', 'Mustang GT', 2020, '5.0L V8', 450, 'RWD', 'Manual'),
('Ford', 'Mustang GT', 2020, '5.0L V8', 450, 'RWD', 'Automatic'),
('Chevrolet', 'Camaro SS', 2020, '6.2L V8', 455, 'RWD', 'Manual'),
('Chevrolet', 'Camaro SS', 2020, '6.2L V8', 455, 'RWD', 'Automatic'),
('Dodge', 'Challenger R/T', 2020, '5.7L V8', 375, 'RWD', 'Manual'),
('Dodge', 'Challenger R/T', 2020, '5.7L V8', 375, 'RWD', 'Automatic'),
('BMW', 'M3', 2021, '3.0L I6 Twin-Turbo', 473, 'RWD', 'Manual'),
('BMW', 'M3', 2021, '3.0L I6 Twin-Turbo', 473, 'RWD', 'Automatic'),
('Audi', 'RS5', 2021, '2.9L V6 Twin-Turbo', 444, 'AWD', 'Automatic'),
('Mercedes-Benz', 'C63 AMG', 2020, '4.0L V8 Twin-Turbo', 469, 'RWD', 'Automatic'),
('Subaru', 'WRX STI', 2021, '2.5L H4 Turbo', 310, 'AWD', 'Manual'),
('Subaru', 'WRX STI', 2021, '2.5L H4 Turbo', 310, 'AWD', 'Automatic'),
('Nissan', 'GT-R', 2020, '3.8L V6 Twin-Turbo', 565, 'AWD', 'Automatic'),
('Toyota', 'Supra', 2021, '3.0L I6 Turbo', 382, 'RWD', 'Automatic'),
('Honda', 'Civic Type R', 2021, '2.0L I4 Turbo', 306, 'FWD', 'Manual'),
('Honda', 'Civic Type R', 2021, '2.0L I4 Turbo', 306, 'FWD', 'Automatic'),
('Porsche', '911 Carrera', 2020, '3.0L H6 Twin-Turbo', 379, 'RWD', 'Manual'),
('Porsche', '911 Carrera', 2020, '3.0L H6 Twin-Turbo', 379, 'RWD', 'Automatic'),
('Tesla', 'Model S', 2021, 'Electric', 1020, 'AWD', 'Automatic'),
('Tesla', 'Model 3', 2021, 'Electric', 480, 'AWD', 'Automatic'),
('Lamborghini', 'Huracan EVO', 2020, '5.2L V10', 631, 'AWD', 'Automatic'),
('Ferrari', '488 Pista', 2019, '3.9L V8 Twin-Turbo', 710, 'RWD', 'Automatic'),
('McLaren', '720S', 2020, '4.0L V8 Twin-Turbo', 710, 'RWD', 'Automatic'),
('Aston Martin', 'Vantage', 2020, '4.0L V8 Twin-Turbo', 503, 'RWD', 'Automatic'),
('Jaguar', 'F-Type R', 2021, '5.0L V8 Supercharged', 575, 'AWD', 'Automatic'),
('Alfa Romeo', 'Giulia Quadrifoglio', 2020, '2.9L V6 Twin-Turbo', 505, 'RWD', 'Manual'),
('Alfa Romeo', 'Giulia Quadrifoglio', 2020, '2.9L V6 Twin-Turbo', 505, 'RWD', 'Automatic'),
('Maserati', 'Ghibli Trofeo', 2021, '3.8L V8 Twin-Turbo', 580, 'RWD', 'Automatic'),
('Lexus', 'RC F', 2020, '5.0L V8', 472, 'RWD', 'Automatic'),
('Acura', 'NSX', 2020, '3.5L V6 Hybrid', 573, 'AWD', 'Automatic'),
('Audi', 'R8', 2021, '5.2L V10', 602, 'AWD', 'Automatic'),
('BMW', 'M4', 2021, '3.0L I6 Twin-Turbo', 503, 'RWD', 'Manual'),
('BMW', 'M4', 2021, '3.0L I6 Twin-Turbo', 503, 'RWD', 'Automatic'),
('Chevrolet', 'Corvette Z06', 2020, '6.2L V8', 650, 'RWD', 'Manual'),
('Chevrolet', 'Corvette Z06', 2020, '6.2L V8', 650, 'RWD', 'Automatic'),
('Dodge', 'Charger SRT Hellcat', 2020, '6.2L V8 Supercharged', 707, 'RWD', 'Automatic'),
('Ford', 'Shelby GT500', 2020, '5.2L V8 Supercharged', 760, 'RWD', 'Automatic'),
('Ford', 'Shelby GT350', 2020, '5.2L V8', 526, 'RWD', 'Manual'),
('Honda', 'NSX', 2020, '3.5L V6 Hybrid', 573, 'AWD', 'Automatic'),
('Hyundai', 'Veloster N', 2021, '2.0L I4 Turbo', 275, 'FWD', 'Manual'),
('Hyundai', 'Veloster N', 2021, '2.0L I4 Turbo', 275, 'FWD', 'Automatic'),
('Infiniti', 'Q60 Red Sport 400', 2020, '3.0L V6 Twin-Turbo', 400, 'RWD', 'Automatic'),
('Jaguar', 'XE SV Project 8', 2019, '5.0L V8 Supercharged', 592, 'AWD', 'Automatic'),
('Kia', 'Stinger GT', 2021, '3.3L V6 Twin-Turbo', 368, 'AWD', 'Automatic'),
('Lamborghini', 'Aventador SVJ', 2020, '6.5L V12', 759, 'AWD', 'Automatic'),
('Lotus', 'Evora GT', 2020, '3.5L V6 Supercharged', 416, 'RWD', 'Manual'),
('Lotus', 'Evora GT', 2020, '3.5L V6 Supercharged', 416, 'RWD', 'Automatic'),
('Mazda', 'MX-5 Miata', 2020, '2.0L I4', 181, 'RWD', 'Manual'),
('Mazda', 'MX-5 Miata', 2020, '2.0L I4', 181, 'RWD', 'Automatic'),
('Mercedes-AMG', 'GT R', 2020, '4.0L V8 Twin-Turbo', 577, 'RWD', 'Automatic'),
('Nissan', '370Z', 2020, '3.7L V6', 332, 'RWD', 'Manual'),
('Nissan', '370Z', 2020, '3.7L V6', 332, 'RWD', 'Automatic'),
('Porsche', '718 Cayman GT4', 2021, '4.0L H6', 414, 'RWD', 'Manual'),
('Porsche', '718 Cayman GT4', 2021, '4.0L H6', 414, 'RWD', 'Automatic'),
('Toyota', 'GR Yaris', 2021, '1.6L I3 Turbo', 257, 'AWD', 'Manual'),
('Volkswagen', 'Golf R', 2021, '2.0L I4 Turbo', 315, 'AWD', 'Manual'),
('Volkswagen', 'Golf R', 2021, '2.0L I4 Turbo', 315, 'AWD', 'Automatic');

INSERT INTO part (part_name, part_number, manufacturer, OEM_number, weight, details, category_id, car_id) VALUES
('High-Performance Air Filter', 'HP-AF-001', 'K&N', '33-5091', 1.25, 'Increases air flow to the engine, improving performance.', 14, 1),
('Ceramic Brake Pads', 'CBP-042', 'Brembo', 'P85029N', 3.50, 'High-temperature resistant brake pads for extreme performance.', 7, 2),
('Cold Air Intake System', 'CAI-003', 'AEM', '21-8129DC', 5.00, 'Increases horsepower by reducing air intake temperature.', 14, 3),
('Turbocharger Kit', 'TURBO-KT-008', 'Garrett', '471171-5007S', 15.50, 'Complete turbocharger upgrade kit for enhanced performance.', 12, 4),
('Performance Exhaust System', 'PES-021', 'Borla', '140616', 22.00, 'Reduces backpressure and enhances exhaust flow.', 13, 5),
('Adjustable Coilover Kit', 'ACO-054', 'KW Suspension', '35210032', 27.50, 'Allows height and damping adjustment for improved handling.', 25, 6),
('Carbon Fiber Hood', 'CF-HOOD-011', 'Seibon', 'HD1010', 18.00, 'Lightweight hood for reduced weight and improved performance.', 6, 7),
('LED Headlight Conversion Kit', 'LED-HL-056', 'Philips', '12342CVSM', 0.75, 'Brighter and more energy-efficient headlights.', 10, 8),
('Sport Clutch Kit', 'SP-CK-089', 'Exedy', '16038', 10.00, 'High-performance clutch kit for sport driving.', 8, 9),
('Performance Radiator', 'PER-RAD-023', 'Mishimoto', 'MMRAD-MUS8-15', 12.00, 'High-capacity radiator for improved cooling.', 10, 1),
('Aftermarket Wheels', 'WHEEL-042', 'Enkei', 'ENK-RPF1-18', 9.50, 'Lightweight wheels for improved handling.', 28, 2),
('Sports Tires', 'TIRE-019', 'Michelin', 'PS4S', 10.20, 'High-performance tires for superior grip.', 28, 3),
('Engine Control Unit (ECU)', 'ECU-101', 'Cobb', 'AP3-FOR-003', 0.60, 'Engine management system for tuning.', 11, 4),
('Fuel Pump', 'FP-033', 'Walbro', 'GSS342', 0.80, 'High-pressure fuel pump for increased fuel flow.', 16, 5),
('High-Performance Spark Plugs', 'SP-PLG-044', 'NGK', 'LFR7AIX', 0.20, 'Iridium spark plugs for better ignition.', 22, 6),
('Intercooler Kit', 'IC-KIT-078', 'Mishimoto', 'MMINT-WRX-08', 15.00, 'Upgraded intercooler for turbocharged engines.', 12, 7),
('Exhaust Manifold', 'EX-MAN-014', 'Tomei', '193082', 7.00, 'High-flow exhaust manifold for turbo engines.', 13, 8),
('Camshaft Kit', 'CAM-KIT-085', 'Brian Crower', 'BC0222', 8.50, 'Performance camshaft for increased power.', 11, 9),
('Throttle Body', 'THR-BDY-050', 'BBK', '1781', 1.75, 'Larger throttle body for increased air flow.', 12, 10),
('Air Suspension Kit', 'AIR-SUS-091', 'Air Lift', '75555', 30.00, 'Adjustable air suspension for improved ride quality.', 25, 11),
('Brake Rotors', 'BR-RT-062', 'StopTech', '126.40057SR', 7.20, 'Slotted brake rotors for improved braking.', 7, 12),
('Carbon Fiber Rear Wing', 'CF-WING-030', 'APR Performance', 'AS-107940', 4.00, 'Reduces lift and increases downforce.', 6, 13),
('Strut Tower Brace', 'STB-077', 'Cusco', '196 540 A', 3.00, 'Increases chassis stiffness for better handling.', 25, 14),
('Oil Cooler Kit', 'OIL-CL-083', 'Setrab', '50-113-7612', 4.50, 'Reduces engine oil temperatures under high performance.', 12, 15),
('Aluminum Driveshaft', 'ALU-DS-095', 'Driveshaft Shop', 'GMCA10-A', 12.00, 'Lightweight driveshaft for improved response.', 27, 16),
('Heavy-Duty Clutch Master Cylinder', 'HD-CMC-066', 'Wilwood', '260-13375', 1.50, 'Improved clutch feel and durability.', 8, 17),
('Lightweight Flywheel', 'LW-FW-018', 'ACT', '600240', 7.00, 'Reduces rotational mass for quicker engine response.', 8, 18),
('Stainless Steel Brake Lines', 'SS-BL-005', 'Goodridge', '12217', 0.30, 'Improved brake pedal feel and response.', 7, 19),
('Front Lip Spoiler', 'FL-SP-011', 'Stillen', 'KB11224', 2.00, 'Enhances aerodynamics and provides aggressive styling.', 6, 20),
('High-Flow Fuel Injectors', 'HF-FI-075', 'DeatschWerks', '18U-02-1000-4', 0.25, 'Increases fuel flow for high-performance applications.', 16, 21),
('Adjustable Control Arms', 'ADJ-CA-059', 'SPC', '72150', 6.00, 'Allows for precise suspension alignment adjustments.', 25, 22),
('Big Brake Kit', 'BBK-037', 'AP Racing', 'CP8520-1001', 25.00, 'Larger brake rotors and calipers for improved braking.', 7, 23),
('Performance Oil Filter', 'POF-072', 'K&N', 'HP-1004', 0.50, 'High-flow oil filter for enhanced engine protection.', 14, 24),
('Cat-Back Exhaust System', 'CB-EX-046', 'MagnaFlow', '19201', 20.00, 'Improves exhaust flow for better performance and sound.', 13, 25),
('High-Performance Radiator Hoses', 'HPR-HS-067', 'Samco', 'TCS-372', 1.20, 'Reduces the risk of overheating by improving coolant flow.', 10, 26),
('Performance Camshafts', 'PER-CAM-029', 'HKS', '22002-AN014', 8.00, 'Upgraded camshafts for more aggressive valve timing.', 11, 27),
('Rear Diffuser', 'RD-091', 'Voltex', 'VOLT-RD-001', 3.50, 'Improves aerodynamics and reduces drag.', 6, 28),
('Supercharger Kit', 'SC-KIT-058', 'Edelbrock', '1599', 50.00, 'Boosts engine power by forcing more air into the combustion chamber.', 12, 29),
('Carbon Ceramic Brake Discs', 'CC-BD-094', 'Brembo', 'PVT04', 5.50, 'Lightweight, high-performance brake discs for reduced stopping distance.', 7, 30),
('Sequential Gearbox', 'SEQ-GB-071', 'Xtrac', 'P1153', 35.00, 'Race-spec gearbox for rapid gear changes.', 26, 31),
('Adjustable Sway Bar', 'ADJ-SB-064', 'Whiteline', 'BKR29Z', 8.00, 'Reduces body roll for improved cornering.', 25, 32),
('Sports Seats', 'SP-STS-093', 'Recaro', 'RECARO-SPIEL', 15.00, 'Lightweight and supportive seats for improved driver feedback.', 19, 33),
('Short Shifter Kit', 'SS-KIT-010', 'B&M', '45088', 2.00, 'Reduces shift throw for faster gear changes.', 26, 34),
('Performance Brake Fluid', 'PBF-025', 'Motul', 'RBF 600', 1.00, 'High boiling point brake fluid for performance braking.', 7, 35),
('Aftermarket Steering Wheel', 'ASW-043', 'Momo', 'MO-STW-111', 2.50, 'Improves steering feel and reduces weight.', 24, 36);