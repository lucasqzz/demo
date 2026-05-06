CREATE DATABASE IF NOT EXISTS ecommerce_user;
CREATE DATABASE IF NOT EXISTS ecommerce_product;
CREATE DATABASE IF NOT EXISTS ecommerce_order;

-- User Service Database
USE ecommerce_user;

CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO t_user (username, password, email, phone) VALUES
('admin', '$2a$10$EIXe4G/LHCwMMRLBnxJuseP/gTbHeTdN9qDkGHzSPnMm6GV56mCJa', 'admin@example.com', '13800000000'),
('testuser', '$2a$10$EIXe4G/LHCwMMRLBnxJuseP/gTbHeTdN9qDkGHzSPnMm6GV56mCJa', 'test@example.com', '13800000001');

-- Product Service Database
USE ecommerce_product;

CREATE TABLE IF NOT EXISTS t_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO t_product (name, description, price, stock) VALUES
('iPhone 15', 'Apple iPhone 15 128GB', 999.00, 100),
('MacBook Pro', 'Apple MacBook Pro M3 14-inch', 1999.00, 50),
('AirPods Pro', 'Apple AirPods Pro 2nd Gen', 249.00, 200);

-- Order Service Database
USE ecommerce_order;

CREATE TABLE IF NOT EXISTS t_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    items_json TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
);
