-- Create database if not exists
CREATE DATABASE IF NOT EXISTS pms;
USE pms;

-- Table for Users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Table for Roles
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Table for User Roles (Many-to-Many relationship)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Table for Promotions
CREATE TABLE IF NOT EXISTS promotions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    image_path VARCHAR(500)
);

-- Insert roles into the roles table
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

-- Insert user 'john' into the users table
INSERT INTO users (username, password) VALUES ('john', '$2a$12$O1nNxGJT0Gq8i1hVBkcpjuMkbQqyqgsa.e.9KMMSG6P8a8qWJ5H8a');

-- Assign the 'ADMIN' role to user 'john'
INSERT INTO user_roles (user_id, role_id) 
SELECT u.id, r.id 
FROM users u, roles r 
WHERE u.username = 'john' AND r.name = 'ADMIN';
