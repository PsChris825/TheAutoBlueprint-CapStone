DROP DATABASE IF EXISTS auto_blueprint;
CREATE DATABASE auto_blueprint;
USE auto_blueprint;

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



