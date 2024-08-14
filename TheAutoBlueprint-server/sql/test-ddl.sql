DROP DATABASE IF EXISTS auto_blueprint_test;

CREATE DATABASE auto_blueprint_test;

USE auto_blueprint_test;

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

CREATE TABLE modification_plan (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    app_user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
    car_id INT REFERENCES car(car_id) ON DELETE SET NULL,
    plan_description TEXT,
    plan_hours_of_completion INT,
    budget DECIMAL(10, 2),
    total_cost DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE part_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE part (
    part_id INT PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255),
    manufacturer VARCHAR(255),
    OEM_number VARCHAR(255),
    weight DECIMAL(10, 2),
    details TEXT,
    category_id INT REFERENCES part_categories(category_id) ON DELETE SET NULL
);

CREATE TABLE supplier (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(255) NOT NULL,
    website VARCHAR(255)
);

CREATE TABLE plan_part (
    plan_id INT REFERENCES modification_plan(plan_id) ON DELETE CASCADE,
    part_id INT REFERENCES part(part_id) ON DELETE CASCADE,
    supplier_id INT REFERENCES supplier(supplier_id) ON DELETE SET NULL,
    price DECIMAL(10, 2),
    PRIMARY KEY (plan_id, part_id, supplier_id)
);

CREATE TABLE tutorial (
    tutorial_id INT PRIMARY KEY AUTO_INCREMENT,
    plan_id INT REFERENCES plan_part(plan_id) ON DELETE CASCADE,
    part_id INT REFERENCES plan_part(part_id) ON DELETE CASCADE,
    video_link VARCHAR(255),
    description TEXT
);

CREATE TABLE post (
    post_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
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
    comment_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //

CREATE PROCEDURE set_known_good_state()
BEGIN
    DELETE FROM app_user_role;
    DELETE FROM app_user;
    DELETE FROM plan_part;
    DELETE FROM part;
    DELETE FROM supplier;
    DELETE FROM modification_plan;
    DELETE FROM car;
    DELETE FROM `comment`;
    DELETE FROM post;
    DELETE FROM tutorial;
    DELETE FROM part_categories;
    DELETE FROM app_role;

    ALTER TABLE app_user AUTO_INCREMENT = 1;
    ALTER TABLE app_role AUTO_INCREMENT = 1;
    ALTER TABLE app_user_role AUTO_INCREMENT = 1;
    ALTER TABLE car AUTO_INCREMENT = 1;
    ALTER TABLE modification_plan AUTO_INCREMENT = 1;
    ALTER TABLE part_categories AUTO_INCREMENT = 1;
    ALTER TABLE part AUTO_INCREMENT = 1;
    ALTER TABLE supplier AUTO_INCREMENT = 1;
    ALTER TABLE plan_part AUTO_INCREMENT = 1;
    ALTER TABLE tutorial AUTO_INCREMENT = 1;
    ALTER TABLE post AUTO_INCREMENT = 1;
    ALTER TABLE `comment` AUTO_INCREMENT = 1;

    INSERT INTO app_role (`name`) VALUES
        ('USER'),
        ('ADMIN');

    -- passwords are set to "P@ssw0rd!"
    INSERT INTO app_user (username, password_hash, enabled)
        VALUES
        ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
        ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

    INSERT INTO app_user_role
        VALUES
        (1, 2),
        (2, 1);

    INSERT INTO car (make, model, year, engine, power, drive_type, transmission_type) VALUES
        ('Toyota', 'Corolla', 2020, '1.8L', 139, 'FWD', 'Automatic'),
        ('Ford', 'Mustang', 2021, '5.0L', 450, 'RWD', 'Manual');

    INSERT INTO modification_plan (app_user_id, car_id, plan_description, plan_hours_of_completion, budget, total_cost) VALUES
        (1, 1, 'Basic Upgrades', 10, 5000.00, 1200.00),
        (2, 2, 'Performance Mods', 20, 10000.00, 8000.00);

    INSERT INTO part_categories (category_name) VALUES
        ('Engine'),
        ('Suspension'),
        ('Body');

    INSERT INTO part (part_name, part_number, manufacturer, OEM_number, weight, details, category_id) VALUES
        ('Air Filter', 'AF123', 'K&N', 'OEM456', 1.5, 'High-flow air filter', 1),
        ('Shock Absorber', 'SA789', 'Bilstein', 'OEM123', 4.2, 'Heavy-duty shock absorber', 2);

    INSERT INTO supplier (supplier_name, website) VALUES
        ('AutoZone', 'https://www.autozone.com'),
        ('PepBoys', 'https://www.pepboys.com');

    INSERT INTO plan_part (plan_id, part_id, supplier_id, price) VALUES
        (1, 1, 1, 50.00),
        (2, 2, 2, 150.00);

    INSERT INTO tutorial (plan_id, part_id, video_link, description) VALUES
        (1, 1, 'https://www.youtube.com/watch?v=example', 'How to install an air filter'),
        (2, 2, 'https://www.youtube.com/watch?v=example2', 'How to replace a shock absorber');

    INSERT INTO post (user_id, title, post_description, image_url) VALUES
        (1, 'My Car Mods', 'Check out the mods I did on my car', 'http://example.com/car1.jpg'),
        (2, 'Upgraded Mustang', 'Performance upgrades for my Mustang', 'http://example.com/car2.jpg');

    INSERT INTO `comment` (post_id, user_id, comment_text) VALUES
        (1, 2, 'Nice work!'),
        (2, 1, 'Great upgrades!');
END //

DELIMITER ;

