# Student Management System - Secure Core Platform 🛡️

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)
![JWT](https://img.shields.io/badge/Security-JWT-red?style=for-the-badge)

## 📖 Introduction

**Student Management System (SMS)** is a scalable web application designed to manage academic records, course enrollments, and user identities. 

This repository currently hosts the **Secure Core Module (Phase 1)**, which provides a robust foundation for the system, focusing on Centralized Authentication, Role-Based Access Control (RBAC), and High-level Database Architecture.

> **Current Status:** Phase 1 (Identity & Security Core) Completed. Phase 2 (Academic Logic) Database Designed & Under Development.

## 🚀 Key Features

### 🔐 Phase 1: Identity & Security Core (Completed)
* **Stateless Authentication:** Implemented using **Spring Security** and **JWT** (Access Token & Refresh Token).
* **Role-Based Access Control (RBAC):** Dynamic permission management for **ADMIN**, **TEACHER**, and **STUDENT**.
* **User Management:** Secure APIs for Registration, Profile Updates, and Password Management.
* **Global Exception Handling:** Standardized API error responses (e.g., `AppException`, `ErrorCode`) for smooth Frontend integration.
* **DTO Mapping:** Efficient data transfer using **MapStruct**.

### 🎓 Phase 2: Academic Management (Designed & In Progress)
* **Student Profile:** Linked with User Identity (1-to-1).
* **Course/Classroom Management:** Admin can create classes; Students can view available classes.
* **Enrollment Logic:** Many-to-Many relationship handling between Students and Classes.
* **Score/Grading:** Management of student performance.

## 🏗️ System Architecture

The project follows the standard **Multi-layer Architecture** to ensure separation of concerns and maintainability:

```mermaid
graph TD;
    Client-->Controller_Layer;
    Controller_Layer-->Service_Layer;
    Service_Layer-->Repository_Layer;
    Repository_Layer-->MySQL_Database;
    Service_Layer-->Mapper_MapStruct;
