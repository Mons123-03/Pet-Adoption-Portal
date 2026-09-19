-- Pet Adoption Portal Database Schema (reference only)
--
-- You do NOT need to run this manually - Spring Data JPA automatically
-- creates/updates these tables on startup (spring.jpa.hibernate.ddl-auto=update
-- in application.properties), based on the @Entity classes.
--
-- This file is provided only as a readable reference of the resulting
-- table structure, and as an option if you prefer to create the schema
-- yourself and switch ddl-auto to "validate" or "none" instead.

CREATE DATABASE IF NOT EXISTS pet_adoption_portal;
USE pet_adoption_portal;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'ADOPTER',   -- ADOPTER, OWNER, ADMIN
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    pet_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    breed VARCHAR(100),
    age INT,
    gender VARCHAR(10) DEFAULT 'Male',
    description TEXT,
    image_url VARCHAR(255),
    status VARCHAR(20) DEFAULT 'AVAILABLE',        -- AVAILABLE, PENDING, ADOPTED
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS adoption_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    adopter_id INT NOT NULL,
    message TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',          -- PENDING, APPROVED, REJECTED
    requested_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE,
    FOREIGN KEY (adopter_id) REFERENCES users(id) ON DELETE CASCADE
);

-- A default admin account (admin@petadoption.com / admin123) is created
-- automatically on first startup by CommandLineRunner in
-- PetAdoptionPortalApplication.java - no manual INSERT needed.
