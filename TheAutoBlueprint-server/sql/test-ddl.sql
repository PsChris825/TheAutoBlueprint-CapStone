DROP DATABASE IF EXISTS auto_blueprint_test;

CREATE DATABASE auto_blueprint_test;

USE auto_blueprint_test;

CREATE TABLE Users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE app_role (
    app_role_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user_role (
    app_user_id INT NOT NULL,
    app_role_id INT NOT NULL,
    CONSTRAINT pk_app_user_role PRIMARY KEY (app_user_id, app_role_id),
    CONSTRAINT fk_app_user_role_user_id FOREIGN KEY (app_user_id) REFERENCES Users(user_id),
    CONSTRAINT fk_app_user_role_role_id FOREIGN KEY (app_role_id) REFERENCES app_role(app_role_id)
);

CREATE TABLE Cars (
    car_id INT PRIMARY KEY AUTO_INCREMENT,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    engine VARCHAR(255),
    power DECIMAL(10, 2),
    drive_type VARCHAR(255),
    transmission_type VARCHAR(255)
);

CREATE TABLE ModificationPlans (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    car_id INT REFERENCES Cars(car_id) ON DELETE SET NULL,
    description TEXT,
    timeline JSON,
    budget DECIMAL(10, 2),
    total_cost DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE PartCategories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE Parts (
    part_id INT PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255),
    manufacturer VARCHAR(255),
    OEM_number VARCHAR(255),
    weight DECIMAL(10, 2),
    details TEXT,
    category_id INT REFERENCES PartCategories(category_id) ON DELETE SET NULL
);

CREATE TABLE Suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    website VARCHAR(255)
);

CREATE TABLE PlanParts (
    plan_id INT REFERENCES ModificationPlans(plan_id) ON DELETE CASCADE,
    part_id INT REFERENCES Parts(part_id) ON DELETE CASCADE,
    supplier_id INT REFERENCES Suppliers(supplier_id) ON DELETE SET NULL,
    price DECIMAL(10, 2),
    PRIMARY KEY (plan_id, part_id, supplier_id)
);

CREATE TABLE Tutorials (
    tutorial_id INT PRIMARY KEY AUTO_INCREMENT,
    plan_id INT REFERENCES PlanParts(plan_id) ON DELETE CASCADE,
    part_id INT REFERENCES PlanParts(part_id) ON DELETE CASCADE,
    video_link VARCHAR(255),
    description TEXT
);

CREATE TABLE Posts (
    post_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    title VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Comments (
    comment_id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT REFERENCES Posts(post_id) ON DELETE CASCADE,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE,
    comment_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

delimiter //

CREATE PROCEDURE set_known_good_state()
BEGIN

DELETE FROM app_user_role;
DELETE FROM Users;
DELETE FROM PlanParts;
DELETE FROM Parts;
DELETE FROM Suppliers;
DELETE FROM ModificationPlans;
DELETE FROM Cars;
DELETE FROM Comments;
DELETE FROM Posts;
DELETE FROM Tutorials;
DELETE FROM PartCategories;
DELETE FROM app_role;

ALTER TABLE Users AUTO_INCREMENT = 1;
ALTER TABLE app_role AUTO_INCREMENT = 1;
ALTER TABLE app_user_role AUTO_INCREMENT = 1;
ALTER TABLE Cars AUTO_INCREMENT = 1;
ALTER TABLE ModificationPlans AUTO_INCREMENT = 1;
ALTER TABLE PartCategories AUTO_INCREMENT = 1;
ALTER TABLE Parts AUTO_INCREMENT = 1;
ALTER TABLE Suppliers AUTO_INCREMENT = 1;
ALTER TABLE PlanParts AUTO_INCREMENT = 1;
ALTER TABLE Tutorials AUTO_INCREMENT = 1;
ALTER TABLE Posts AUTO_INCREMENT = 1;
ALTER TABLE Comments AUTO_INCREMENT = 1;

INSERT INTO Users (email, password_hash) VALUES 
('user1@example.com', 'hashed_password_1'),
('user2@example.com', 'hashed_password_2');

INSERT INTO app_role (name) VALUES 
('Admin'),
('User');

INSERT INTO app_user_role (app_user_id, app_role_id) VALUES 
(1, 1),
(2, 2);

INSERT INTO Cars (make, model, year, engine, power, drive_type, transmission_type) VALUES 
('Toyota', 'Corolla', 2020, '1.8L', 139.00, 'FWD', 'Automatic'),
('Ford', 'Mustang', 2021, '5.0L', 450.00, 'RWD', 'Manual');

INSERT INTO ModificationPlans (user_id, car_id, description, timeline, budget, total_cost) VALUES 
(1, 1, 'Basic Upgrades', '{"phases":["Phase 1", "Phase 2"]}', 5000.00, 1200.00),
(2, 2, 'Performance Mods', '{"phases":["Stage 1", "Stage 2"]}', 10000.00, 8000.00);

INSERT INTO PartCategories (category_name) VALUES 
('Engine'),
('Suspension'),
('Body');

INSERT INTO Parts (part_name, part_number, manufacturer, OEM_number, weight, details, category_id) VALUES 
('Air Filter', 'AF123', 'K&N', 'OEM456', 1.5, 'High-flow air filter', 1),
('Shock Absorber', 'SA789', 'Bilstein', 'OEM123', 4.2, 'Heavy-duty shock absorber', 2);

INSERT INTO Suppliers (name, website) VALUES 
('AutoZone', 'https://www.autozone.com'),
('PepBoys', 'https://www.pepboys.com');

INSERT INTO PlanParts (plan_id, part_id, supplier_id, price) VALUES 
(1, 1, 1, 50.00),
(2, 2, 2, 150.00);

INSERT INTO Tutorials (plan_id, part_id, video_link, description) VALUES 
(1, 1, 'https://www.youtube.com/watch?v=example', 'How to install an air filter'),
(2, 2, 'https://www.youtube.com/watch?v=example2', 'How to replace a shock absorber');

INSERT INTO Posts (user_id, title, description, image_url) VALUES 
(1, 'My Car Mods', 'Check out the mods I did on my car', 'http://example.com/car1.jpg'),
(2, 'Upgraded Mustang', 'Performance upgrades for my Mustang', 'http://example.com/car2.jpg');

INSERT INTO Comments (post_id, user_id, comment_text) VALUES 
(1, 2, 'Nice work!'),
(2, 1, 'Great upgrades!');

END //

delimiter ;

