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
    category_id INT REFERENCES part_category(category_id) ON DELETE SET NULL
);

CREATE TABLE plan_part (
    plan_part_id INT PRIMARY KEY AUTO_INCREMENT,
    part_id INT REFERENCES part(part_id) ON DELETE CASCADE,
    price DECIMAL(10, 2),
    tutorial_url VARCHAR(255),
    supplier_url VARCHAR(255)
);

CREATE TABLE modification_plan (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    app_user_id INT REFERENCES app_user(app_user_id) ON DELETE CASCADE,
    car_id INT REFERENCES car(car_id) ON DELETE SET NULL,
    plan_part_id INT REFERENCES plan_part(plan_part_id) ON DELETE SET NULL,
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

SET SQL_SAFE_UPDATES = 0;
DELETE FROM app_user_role;
DELETE FROM app_user;
DELETE FROM plan_part;
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