# 📘 ShopSphere E-Commerce System - Comprehensive Project Report

**Architecture:** Model-View-Controller (MVC) using Jakarta EE 10, JDBC, and MySQL 8.x
**Deployment Target:** Apache Tomcat 11

## 1. Executive Summary
ShopSphere is a complete, full-stack, enterprise-grade e-commerce application designed to provide customers with an intuitive shopping experience while equipping store administrators with management tools to oversee inventory, fulfill orders, moderate reviews, and evaluate store performance. Developed from first principles using Java 17, Jakarta Servlets 6.0, JSP 3.1, JSTL 3.0, and raw JDBC.

## 2. Problem Statement & Proposed Solution
Many educational tutorial applications suffer from architectural flaws: SQL injection vulnerabilities, plain text passwords, and client-side price manipulation. ShopSphere addresses every deficiency:
- **Zero-Trust Client Pricing**: The server recalculates all prices and discounts against live database records during checkout.
- **Atomic Transactions**: Inventory updates execute inside an atomic JDBC transaction to prevent overselling.
- **Enterprise-Grade Security**: BCrypt password hashing, centralized AuthFilter and AdminFilter, session regeneration.
- **Modern Standards**: Fully compliant with Jakarta EE 10 / Servlet 6.0.

## 3. Database Schema & ER Design
The database schema is normalized to Third Normal Form (3NF) to eliminate redundancy and maintain referential integrity. It features 11 core tables including `users`, `products`, `orders`, `cart_items`, and `coupons`.

## 4. Security Analysis
- **Password Hashing**: Utilizes jBCrypt with an adaptive work factor.
- **SQL Injection Immunity**: Every query uses PreparedStatement with typed parameter binding.
- **Anti-Duplicate Submission**: Checkout forms generate a cryptographically random UUID token in the session.

## 5. Testing
Automated unit tests were authored using JUnit 5 (Jupiter) covering key business logic: `DBConnectionTest`, `PasswordUtilTest`, `ProductTest`, `CartTest`, and `CouponServiceTest`.
