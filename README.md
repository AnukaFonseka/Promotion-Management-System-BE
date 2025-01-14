# Promotion Management System (Backend)

This is the backend implementation of the **Promotion Management System**, developed using **Spring Boot** and **MySQL**. It provides a RESTful API for managing promotions, including features such as creating, updating, deleting promotions, and handling image uploads.

---

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Database Script](#database-script)
- [Credentials](#credentials)
- [Project Structure](#project-structure)
- [License](#license)

---

## Features
- User authentication with JWT.
- Role-based access control (Admin and User).
- Create, update, and delete promotions.
- Image upload and storage for promotions.
- RESTful APIs following industry best practices.

---

## Technologies Used
- **Java 11**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **MySQL**
- **JWT (JSON Web Token)**
- **Maven**

---

## Setup Instructions

### Prerequisites
- Java 21 or later
- MySQL installed
- Maven installed

### Steps
1. Clone the repository:
   ```
   git clone https://github.com/AnukaFonseka/Promotion-Management-System-BE.git
   cd Promotion-Management-System-BE
   ```
2. Update `application.properties` with your MySQL configuration:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/pms
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

---

## API Endpoints

### Authentication
| Method | Endpoint   | Description      |
|--------|------------|-----------------|
| POST   | `/register`| Register a user |
| POST   | `/login`   | Login a user    |

### Users
| Method | Endpoint     | Description           |
|--------|--------------|-----------------------|
| GET    | `/users`     | Get all users         |
| PUT    | `/users/{id}`| Update a user by ID   |
| DELETE | `/users/{id}`| Delete a user by ID   |

### Promotions
| Method | Endpoint                | Description                         |
|--------|-------------------------|-------------------------------------|
| POST   | `/promotions2`          | Create a new promotion with an image|
| GET    | `/promotions`           | Get all promotions                  |
| GET    | `/promotions/{id}`      | Get a promotion by ID               |
| PUT    | `/promotions2/{id}`     | Update promotion with an image      |
| DELETE | `/promotions/{id}`      | Delete a promotion                  |

---

## Database Configuration
Create a MySQL database named `pms`. Use the provided credentials in `application.properties` to connect.

---

## Database Script
```sql
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
```

---

## Credentials

### Admin Credentials:
- Username: `john`
- Password: `j@12345`


---

## Project Structure
```
src
├── main
│   ├── java
│   │   └── com.tyonics.PromotionManagementSystem
│   │       ├── config
│   │       ├── controller
│   │       ├── entity
│   │       ├── repository
│   │       ├── service
│   │       └── filter
│   └── resources
│       ├── static
│       │   └── public
│       └── db
│           └── pms.sql
│       └── application.properties
└── test
```

---

## License
This project is for educational purposes and part of an assignment submission. Do not use it for commercial purposes.