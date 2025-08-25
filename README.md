# AgriBid Server

## Description
AgriBid Server is the backend application for the AgriBid platform. It provides REST APIs to handle farmers' crop listings, buyer bids, user management, and other core business logic. Built with **Spring Boot** and **MongoDB**, it ensures secure, scalable, and efficient data handling for the AgriBid marketplace.

## Technology Stack
- **Backend Framework:** Spring Boot
- **Database:** MongoDB
- **Build Tool:** Gradle
- **API Communication:** RESTful APIs (JSON)
- **File Storage:** Cloudinary (for images )

## Features
- CRUD operations for crops, bids, and users
- Farmer and buyer dashboards
- API endpoints for frontend consumption
- Integration with Cloudinary for media storage

## Prerequisites
- Java 17+
- MongoDB instance running locally or in the cloud
- Gradle installed

## Running the Application
1. Clone the repository:
```bash
https://github.com/Kmasthan/agribid-server.git
```
2. Navigate to the project directory
```bash
cd agribid-server
```
3. Build and run the application
```bash
./gradlew bootRun
```
- The server will start on http://localhost:8081/
