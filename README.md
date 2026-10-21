# 🛍️ ShopSphere - Complete Full-Stack Java E-Commerce Platform

[![Java Version](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10%20(Servlet%206.0)-orange.svg)](https://jakarta.ee/)
[![Server](https://img.shields.io/badge/Server-Apache%20Tomcat%2011-yellow.svg)](https://tomcat.apache.org/)
[![Database](https://img.shields.io/badge/Database-MySQL%208.x-blue.svg)](https://www.mysql.com/)
[![Build](https://img.shields.io/badge/Build-Maven%203.9%2B-brightgreen.svg)](https://maven.apache.org/)

ShopSphere is an enterprise-grade, secure, responsive, full-stack e-commerce web application built from scratch using a pure **Jakarta Servlets 6.0 + JSP 3.1 + JSTL 3.0 + JDBC** layered architecture. It features a modern customer storefront and a complete administrative control panel.

## 🚀 Quick Start

1. **Import Database:** Run `database/shopsphere.sql` in MySQL Workbench.
2. **Configure DB:** Edit `web/src/main/resources/db.properties` with your MySQL root password.
3. **Build:** `mvn clean package`
4. **Deploy:** Copy `web/target/shopsphere.war` to Tomcat 11 `webapps/`.
5. **Run:** Open `http://localhost:8080/shopsphere/`

## 🔑 Demo Credentials
| Role | Email | Password |
|---|---|---|
| **Admin** | `admin@shopsphere.com` | `Admin@123` |
| **Customer** | `john@example.com` | `Customer@123` |

## 🏛️ Architecture
- **Filters:** `EncodingFilter`, `AuthFilter`, `AdminFilter`
- **Controllers:** 20+ Jakarta Servlets
- **Services:** 10 Business Logic classes
- **DAOs:** 10 JDBC PreparedStatement classes
- **Models:** 12 POJO Entities
- **Views:** JSP + JSTL + Fragments

## 🔒 Security
- BCrypt password hashing (`jBCrypt`)
- SQL Injection immunity (100% PreparedStatement)
- Session fixation prevention (`changeSessionId()`)
- Server-side pricing and inventory atomic transactions
- XSS escaping with `<c:out>`

*Created for educational purposes, software engineering demonstrations, and university project submissions.*
