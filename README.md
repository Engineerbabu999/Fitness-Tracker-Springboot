# 🏃 Fitness Tracker Backend REST API

![Java 25](https://img.shields.io/badge/Java-25-orange.svg?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen.svg?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6%2B-6DB33F.svg?logo=springsecurity&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-7.4-59666C.svg?logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-9.7-4479A1.svg?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Multi--Stage-2496ED.svg?logo=docker&logoColor=white)
![Render](https://img.shields.io/badge/Deploy-Render-46E3B7.svg?logo=render&logoColor=white)
![Swagger UI](https://img.shields.io/badge/OpenAPI-3.0.3-85EA2D.svg?logo=swagger&logoColor=white)

---

## 📖 Table of Contents
1. [Project Overview](#-project-overview)
2. [Key Features](#-key-features)
3. [Technology Stack](#-technology-stack)
4. [Architecture & System Design](#-architecture--system-design)
5. [Database Schema & Data Model](#-database-schema--data-model)
6. [API Endpoints Reference](#-api-endpoints-reference)
   - [Authentication Endpoints](#1-authentication-endpoints-apiauth)
   - [Activity Tracking Endpoints](#2-activity-tracking-endpoints-apiactivities)
   - [Recommendation Endpoints](#3-recommendation-endpoints-apirecommendation)
   - [Swagger & OpenAPI Endpoints](#4-swagger--openapi-endpoints)
7. [Security & JWT Authentication](#-security--jwt-authentication)
8. [Prerequisites & Local Setup](#-prerequisites--local-setup)
9. [Configuration & Environment Variables](#-configuration--environment-variables)
10. [Building & Testing with Maven](#-building--testing-with-maven)
11. [Docker Guide (Build & Run)](#-docker-guide-build--run)
12. [Complete Render Deployment Guide](#-complete-render-deployment-guide)
13. [Troubleshooting & FAQs](#-troubleshooting--faqs)

---

## 🌟 Project Overview

The **Fitness Tracker Backend** is a high-performance, cloud-ready RESTful service architected on modern **Java 25** and **Spring Boot 4.1.2**. It delivers an enterprise-grade platform for tracking personal workouts, analyzing performance statistics with flexible JSON attributes, and generating personalized recovery, safety, and training recommendations.

Designed from the ground up for modern containerized cloud environments, the application features stateless JWT security, flexible MySQL/H2 dual database support, self-documenting OpenAPI/Swagger UI specifications, container-aware JVM memory tuning, and zero-downtime deployment compatibility on platforms like **Render**, AWS, or GCP.

---

## ⚡ Key Features

- **🔐 Stateless Security & RBAC**: Stateless JWT (JSON Web Token) authentication with HMAC-SHA256 digital signing, custom Spring Security filters, and Role-Based Access Control (`USER`, `ADMIN`).
- **🏋️ Comprehensive Workout Tracking**: Log various exercise disciplines (`RUNNING`, `CYCLING`, `SWIMMING`, `WEIGHT_TRAINING`, `YOGA`, `HIIT`, `WALKING`, `OTHER`) with duration, calories burned, start timestamps, and extensible metrics.
- **📊 Native JSON Metrics**: Store unstructured sensor, GPS, and biometric data (e.g. pace, heart rate, cadence, elevation, splits) utilizing native MySQL `json` columns mapped with Hibernate `@JdbcTypeCode(SqlTypes.JSON)`.
- **💡 Smart AI/Rule Recommendation Engine**: Generate context-aware coaching advice, improvement suggestions, and injury prevention/safety guidelines tied directly to specific workouts and users.
- **📄 Interactive Swagger/OpenAPI Documentation**: Fully interactive Swagger UI 3 with built-in JWT Bearer authentication authorization support.
- **🐳 Multi-Stage Docker Build**: Production-optimized two-stage Dockerfile utilizing Eclipse Temurin OpenJDK 25, layer caching, and slim JRE runtime image.
- **☁️ Cloud & Render Native**: Automatic dynamic `$PORT` binding, container memory limit awareness (`-XX:MaxRAMPercentage=75.0`), and environment variable overrides for database URLs and secrets.
- **🧪 Zero-Dependency Test Suite**: Dedicated test configuration using in-memory H2 database in MySQL compatibility mode, allowing builds and tests to succeed offline without requiring an active MySQL instance.

---

## 🛠️ Technology Stack

| Layer | Component | Version | Description |
|---|---|---|---|
| **Language** | Java | **OpenJDK 25** | Latest modern Java features, virtual threads, improved JIT compiler |
| **Framework** | Spring Boot | **4.1.2** | Core enterprise application framework |
| **Web MVC** | Spring WebMVC | 4.1.2 | RESTful controller architecture & HTTP handling |
| **Security** | Spring Security | 4.1.2 | Security filter chains, BCrypt password hashing |
| **Tokens** | JJWT (io.jsonwebtoken) | 0.13.0 | JWT generation, signing, parsing, and claim validation |
| **Data Access** | Spring Data JPA / Hibernate | 7.4.x | ORM persistence layer with native JSON column mapping |
| **Production DB**| MySQL Connector/J | 9.7.0 | High-performance MySQL relational driver |
| **Test DB** | H2 Database | 2.3.232 | In-memory database with MySQL syntax compatibility |
| **API Docs** | SpringDoc OpenAPI | 3.0.3 | Automated Swagger UI and OpenAPI 3.0 specification |
| **Boilerplate** | Project Lombok | 1.18.48 | Automatic getters, setters, builders, and constructors |
| **Build Tool** | Apache Maven Wrapper | 3.10.0 | Reproducible builds with bundled wrapper script |
| **Container** | Docker / Eclipse Temurin | 25-jre | Multi-stage containerization with minimal attack surface |

---

## 🏗️ Architecture & System Design

```
+-----------------------------------------------------------------------------------+
|                                  Client Layer                                     |
|           (Mobile App / React Frontend / Postman / Swagger UI / Render)           |
+-----------------------------------------------------------------------------------+
                                          |
                                HTTP / JSON (Bearer Token)
                                          v
+-----------------------------------------------------------------------------------+
|                               Spring Security                                     |
|  - JwtAuthFilter (Extracts 'Bearer <token>', verifies signature, sets context)    |
|  - SecurityConfig (Defines public routes vs authenticated routes)                 |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                             REST Controllers Layer                                |
|  - AuthController (/api/auth)         - User registration & login                 |
|  - ActivityController (/api/activities) - Log and retrieve fitness activities     |
|  - RecommendationController (/api/recommendation) - Workout recommendations       |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                                 Service Layer                                     |
|  - UserService (Business logic, password encoding, JWT issuance)                  |
|  - ActivityService (Activity tracking, user association, DTO mappings)            |
|  - RecommendationService (Recommendation generation & retrieval)                  |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                            Repository Layer (JPA)                                 |
|  - UserRepository, ActivityRepository, RecommendationRepository                   |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                               Database Layer                                      |
|    MySQL 8.0/9.0+ (Production / Render)    <--->    H2 in-memory (Unit/CI Tests)   |
+-----------------------------------------------------------------------------------+
```

---

## 🗄️ Database Schema & Data Model

The schema consists of three interconnected relational entities with native JSON column mappings:

### 1. `User` Entity (`users`)
- **`id`** (`VARCHAR(36)`, Primary Key, UUID generated)
- **`email`** (`VARCHAR(255)`, Unique, Not Null)
- **`password`** (`VARCHAR(255)`, Not Null, BCrypt hashed)
- **`firstName`** (`VARCHAR(255)`)
- **`lastName`** (`VARCHAR(255)`)
- **`role`** (`ENUM('USER', 'ADMIN')`, Default: `USER`)
- **`createdAt`** (`DATETIME`, Auto-generated on insert)
- **`updatedAt`** (`DATETIME`, Auto-generated on update)

### 2. `Activity` Entity (`activity`)
- **`id`** (`VARCHAR(36)`, Primary Key, UUID generated)
- **`user_id`** (`VARCHAR(36)`, Foreign Key -> `users.id`, Cascading, Not Null)
- **`activityType`** (`ENUM`: `RUNNING`, `CYCLING`, `SWIMMING`, `WEIGHT_TRAINING`, `YOGA`, `HIIT`, `WALKING`, `OTHER`)
- **`duration`** (`INT`, Duration in minutes)
- **`caloriesBurned`** (`INT`, Estimated calories burned)
- **`startTime`** (`DATETIME`, Timestamp when activity started)
- **`additionalMetrics`** (`JSON`, Native MySQL JSON column storing arbitrary key-value telemetry such as heart rate, pace, elevation, reps)
- **`createdAt`** (`DATETIME`)
- **`updatedAt`** (`DATETIME`)

### 3. `Recommendation` Entity (`recommendation`)
- **`id`** (`VARCHAR(36)`, Primary Key, UUID generated)
- **`user_id`** (`VARCHAR(36)`, Foreign Key -> `users.id`, Not Null)
- **`activity_id`** (`VARCHAR(36)`, Foreign Key -> `activity.id`, Not Null)
- **`type`** (`VARCHAR(255)`, e.g., `RECOVERY`, `NUTRITION`, `CARDIO_TARGET`)
- **`recommendation`** (`VARCHAR(2000)`, Narrative recommendation text)
- **`improvements`** (`JSON`, Array of strings detailing areas to improve)
- **`suggestions`** (`JSON`, Array of strings offering actionable coaching advice)
- **`safety`** (`JSON`, Array of strings with injury prevention guidelines)
- **`createdAt`** (`DATETIME`)
- **`updatedAt`** (`DATETIME`)

---

## 📡 API Endpoints Reference

Base URL (Local): `http://localhost:8080`  
Base URL (Production): `https://<your-render-service>.onrender.com`

### 1. Authentication Endpoints (`/api/auth`)

#### 🔹 Register a New User
- **Method**: `POST`
- **URL**: `/api/auth/register`
- **Access**: Public
- **Request Body**:
```json
{
  "email": "alex.smith@example.com",
  "password": "SecurePassword123!",
  "firstName": "Alex",
  "lastName": "Smith",
  "role": "USER"
}
```
- **Response** (`201 Created`):
```json
{
  "id": "e4b2d13b-821f-45a8-9d33-4df42d20ad77",
  "email": "alex.smith@example.com",
  "firstName": "Alex",
  "lastName": "Smith",
  "role": "USER",
  "createdAt": "2026-11-01T18:00:00",
  "updatedAt": "2026-11-01T18:00:00"
}
```

#### 🔹 Login & Obtain JWT Token
- **Method**: `POST`
- **URL**: `/api/auth/login`
- **Access**: Public
- **Request Body**:
```json
{
  "email": "alex.smith@example.com",
  "password": "SecurePassword123!"
}
```
- **Response** (`200 OK`):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlNGIyZDEzYi04MjFmLTQ1YTgtOWQzMy00ZGY0MmQyMGFkNzciLCJyb2xlIjpbIlVTRVIiXSwiaWF0IjoxNzYyMDAwMDAwLCJleHAiOjE3NjIxNzI4MDB9.xyz...",
  "user": {
    "id": "e4b2d13b-821f-45a8-9d33-4df42d20ad77",
    "email": "alex.smith@example.com",
    "firstName": "Alex",
    "lastName": "Smith",
    "role": "USER",
    "createdAt": "2026-11-01T18:00:00",
    "updatedAt": "2026-11-01T18:00:00"
  }
}
```

---

### 2. Activity Tracking Endpoints (`/api/activities`)

All endpoints in this group require an active Bearer JWT token in the `Authorization` header.

#### 🔹 Log a New Workout Activity
- **Method**: `POST`
- **URL**: `/api/activities`
- **Headers**:
  - `Authorization: Bearer <your-jwt-token>`
  - `X-user-id: <user-uuid>`
  - `Content-Type: application/json`
- **Request Body**:
```json
{
  "activityType": "RUNNING",
  "duration": 45,
  "caloriesBurned": 480,
  "startTime": "2026-11-01T07:30:00",
  "additionalMetrics": {
    "distanceKm": 7.5,
    "avgHeartRate": 152,
    "maxHeartRate": 174,
    "avgPaceMinPerKm": "6:00",
    "elevationGainMeters": 42
  }
}
```
- **Response** (`201 Created`):
```json
{
  "id": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "userId": "e4b2d13b-821f-45a8-9d33-4df42d20ad77",
  "activityType": "RUNNING",
  "duration": 45,
  "caloriesBurned": 480,
  "startTime": "2026-11-01T07:30:00",
  "additionalMetrics": {
    "distanceKm": 7.5,
    "avgHeartRate": 152,
    "maxHeartRate": 174,
    "avgPaceMinPerKm": "6:00",
    "elevationGainMeters": 42
  },
  "createdAt": "2026-11-01T08:15:00",
  "updatedAt": "2026-11-01T08:15:00"
}
```

#### 🔹 Retrieve All User Activities
- **Method**: `GET`
- **URL**: `/api/activities`
- **Headers**:
  - `Authorization: Bearer <your-jwt-token>`
  - `X-user-id: <user-uuid>`
- **Response** (`200 OK`):
```json
[
  {
    "id": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
    "userId": "e4b2d13b-821f-45a8-9d33-4df42d20ad77",
    "activityType": "RUNNING",
    "duration": 45,
    "caloriesBurned": 480,
    "startTime": "2026-11-01T07:30:00",
    "additionalMetrics": {
      "distanceKm": 7.5,
      "avgHeartRate": 152
    },
    "createdAt": "2026-11-01T08:15:00",
    "updatedAt": "2026-11-01T08:15:00"
  }
]
```

---

### 3. Recommendation Endpoints (`/api/recommendation`)

All recommendation endpoints require an active Bearer JWT token in the `Authorization` header.

#### 🔹 Generate a Workout Recommendation
- **Method**: `POST`
- **URL**: `/api/recommendation/generate`
- **Headers**:
  - `Authorization: Bearer <your-jwt-token>`
  - `Content-Type: application/json`
- **Request Body**:
```json
{
  "userId": "e4b2d13b-821f-45a8-9d33-4df42d20ad77",
  "activityId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "type": "POST_RUN_RECOVERY",
  "improvements": [
    "Increase cadence to 170-180 spm to decrease heel impact",
    "Hydrate with electrolyte beverage within 30 minutes"
  ],
  "suggestions": [
    "Perform 15 minutes of lower limb foam rolling",
    "Schedule a low-impact recovery walk tomorrow"
  ],
  "safety": [
    "Do not exceed max aerobic heart rate for more than 10 minutes",
    "Ensure proper warm-up before next high-tempo session"
  ]
}
```
- **Response** (`200 OK`):
```json
{
  "id": "7f8b9c1d-1122-3344-5566-778899aabbcc",
  "type": "POST_RUN_RECOVERY",
  "recommendation": null,
  "improvements": [
    "Increase cadence to 170-180 spm to decrease heel impact",
    "Hydrate with electrolyte beverage within 30 minutes"
  ],
  "suggestions": [
    "Perform 15 minutes of lower limb foam rolling",
    "Schedule a low-impact recovery walk tomorrow"
  ],
  "safety": [
    "Do not exceed max aerobic heart rate for more than 10 minutes",
    "Ensure proper warm-up before next high-tempo session"
  ],
  "createdAt": "2026-11-01T08:20:00",
  "updatedAt": "2026-11-01T08:20:00"
}
```

#### 🔹 Get All Recommendations for a User
- **Method**: `GET`
- **URL**: `/api/recommendation/user/{userId}`
- **Headers**: `Authorization: Bearer <your-jwt-token>`
- **Response** (`200 OK`): Array of recommendations for that user.

#### 🔹 Get Recommendations for a Specific Activity
- **Method**: `GET`
- **URL**: `/api/recommendation/activity/{activityId}`
- **Headers**: `Authorization: Bearer <your-jwt-token>`
- **Response** (`200 OK`): Array of recommendations generated for that workout.

---

### 4. Swagger & OpenAPI Endpoints

- **Interactive Swagger UI**: `http://localhost:8080/swagger-ui.html` (or `/swagger-ui/index.html`)
- **OpenAPI 3 JSON Specification**: `http://localhost:8080/v3/api-docs`

> 💡 **Using Swagger UI with JWT**:
> 1. Register or login via `/api/auth/login` to obtain your token string.
> 2. Click the green **Authorize 🔓** button at the top right of the Swagger UI.
> 3. Paste your token (raw token string or `Bearer <token>`).
> 4. Click **Authorize** and then **Close**. Now all secured requests sent through Swagger UI will automatically include the `Authorization` header.

---

## 🔒 Security & JWT Authentication

- **Algorithm**: HMAC-SHA256 (`Keys.hmacShaKeyFor`)
- **Default Expiration**: 48 hours (172,800,000 ms), customizable via `JWT_EXPIRATION_MS`.
- **Payload Subject**: Contains the User's unique UUID (`userId`).
- **Claims**: Contains `role` and `roles` arrays for authorization.
- **Filter**: `JwtAuthFilter` intercepts incoming requests, validates tokens without throwing unhandled exceptions, and populates the `SecurityContext` with `SimpleGrantedAuthority("ROLE_" + role)`.
- **CSRF**: Disabled for stateless REST API endpoints.

---

## 💻 Prerequisites & Local Setup

### System Requirements
- **Java**: OpenJDK 25 (or OpenJDK 21+)
- **Maven**: 3.9+ (or use the included `./mvnw` wrapper)
- **Database**: MySQL 8.0+ / 9.0+ or Docker Desktop

### 1. Clone the Repository
```bash
git clone https://github.com/Engineerbabu777/Fitness-Tracker.git
cd Fitness-Tracker
```

### 2. Configure Local Database
Make sure MySQL is running locally on port `3306`:
```sql
CREATE DATABASE IF NOT EXISTS fitness CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Run Locally with Maven
```bash
./mvnw spring-boot:run
```

The application will start on port `8080`. Access Swagger UI at `http://localhost:8080/swagger-ui.html`.

---

## ⚙️ Configuration & Environment Variables

All settings can be configured via environment variables or `src/main/resources/application.properties`:

| Environment Variable | Default Value | Description |
|---|---|---|
| `PORT` | `8080` | HTTP port the server binds to (Render sets this dynamically) |
| `DB_URL` / `DATABASE_URL` | `jdbc:mysql://localhost:3306/fitness` | JDBC Connection URL |
| `DB_USERNAME` / `USER` | `root` | Database username |
| `DB_PASSWORD` / `PASS` | `root` | Database password |
| `DIALECT` | `org.hibernate.dialect.MySQLDialect` | Hibernate SQL dialect |
| `HIBERNATE_DDL_AUTO` | `update` | DDL generation policy (`update`, `validate`, `create-drop`) |
| `SHOW_SQL` | `false` | Log SQL queries to stdout (`true` / `false`) |
| `JWT_SECRET` | *(64-byte default key)* | Base64-encoded secret key for signing JWT tokens |
| `JWT_EXPIRATION_MS` | `172800000` | JWT token validity in milliseconds (48h) |

---

## 📦 Building & Testing with Maven

The project uses the Maven Wrapper (`mvnw`), eliminating the need to install Maven globally.

### 1. Run Clean Compile
```bash
./mvnw clean compile
```

### 2. Run All Tests
The test suite utilizes an in-memory H2 database, so tests run reliably in any environment without needing an external MySQL connection:
```bash
./mvnw test
```

### 3. Create Executable JAR File
```bash
./mvnw clean package
```
Upon completion, the repackaged Spring Boot executable JAR will be generated at:
```
target/fitness-0.0.1-SNAPSHOT.jar
```

### 4. Run the Generated JAR
```bash
java -jar target/fitness-0.0.1-SNAPSHOT.jar
```

---

## 🐳 Docker Guide (Build & Run)

The application includes an optimized multi-stage `Dockerfile`:
- **Stage 1 (Builder)**: Uses `maven:3.9-eclipse-temurin-25` to compile source code and package the Spring Boot JAR with layer caching.
- **Stage 2 (Runtime)**: Uses lightweight `eclipse-temurin:25-jre` containing only the Java Runtime Environment to minimize image footprint and security vulnerabilities.

### 1. Build the Docker Image
```bash
docker build -t fitness-backend:latest .
```

### 2. Run the Container Standalone
```bash
docker run -d \
  -p 8080:8080 \
  -e PORT=8080 \
  -e DB_URL="jdbc:mysql://host.docker.internal:3306/fitness?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" \
  -e DB_USERNAME="root" \
  -e DB_PASSWORD="your_password" \
  -e JWT_SECRET="your_custom_64_byte_base64_secret_key" \
  --name fitness-app \
  fitness-backend:latest
```

### 3. Run with Docker Compose (App + MySQL)
Create a `docker-compose.yml` in your project root:
```yaml
version: '3.8'

services:
  mysqldb:
    image: mysql:9.0
    container_name: fitness-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: fitness
      MYSQL_USER: fitnessuser
      MYSQL_PASSWORD: fitnesspassword
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql

  fitness-backend:
    build: .
    container_name: fitness-backend
    restart: always
    ports:
      - "8080:8080"
    environment:
      PORT: 8080
      DB_URL: jdbc:mysql://mysqldb:3306/fitness?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      DB_USERNAME: fitnessuser
      DB_PASSWORD: fitnesspassword
      JWT_SECRET: nbag7ya9uwesjjjjj=skdjosidusd89y87whjsdkbsdjhbds78e7e77e=ekeke
    depends_on:
      - mysqldb

volumes:
  db_data:
```
Start both services:
```bash
docker compose up -d --build
```

---

## 🚀 Complete Render Deployment Guide

Deploying this Spring Boot application on [Render](https://render.com) is streamlined and fully supported via Docker. Follow this complete step-by-step guide:

### Step 1: Push Code to GitHub
Ensure all your project files, including `Dockerfile`, `pom.xml`, and `.dockerignore`, are pushed to your GitHub repository:
```bash
git add .
git commit -m "Configure production-ready Dockerfile, OpenAPI, and Render support"
git push origin main
```

---

### Step 2: Set Up a MySQL Database
Render offers PostgreSQL out-of-the-box. For MySQL, you have two simple options:

#### Option A: Managed Cloud MySQL (Recommended)
Use a free/starter MySQL cloud database provider such as:
- **Aiven for MySQL** (Free tier available, generates standard JDBC URL)
- **PlanetScale** or **Railway MySQL**
- Note your database credentials:
  - Host (e.g. `mysql-xxxxx.aivencloud.com`)
  - Port (e.g. `23456`)
  - Database Name (e.g. `fitness`)
  - User and Password

#### Option B: Deploy MySQL on Render as a Private Service
1. In Render Dashboard, click **New +** -> **Web Service** or **Private Service**.
2. Name it `fitness-mysql`.
3. Image: `mysql:8.0`.
4. Add environment variables:
   - `MYSQL_ROOT_PASSWORD`: `<strong_password>`
   - `MYSQL_DATABASE`: `fitness`
5. Attach a Persistent Disk mounted at `/var/lib/mysql`.

---

### Step 3: Deploy the Backend Web Service on Render

1. Log in to your [Render Dashboard](https://dashboard.render.com/).
2. Click **New +** and select **Web Service**.
3. Select **Build and deploy from a Git repository** and connect your GitHub repository (`Fitness-Tracker`).
4. Configure service details:
   - **Name**: `fitness-backend`
   - **Region**: Choose the region closest to your users / database (e.g. `Oregon (US West)` or `Frankfurt (EU Central)`).
   - **Branch**: `main`
   - **Runtime**: **Docker** (Render will automatically detect your `Dockerfile`).
   - **Instance Type**: **Free** or **Starter**.

---

### Step 4: Configure Environment Variables on Render

Under the **Environment Variables** tab of your Render service, add the following key-value pairs:

| Key | Value | Notes |
|---|---|---|
| `PORT` | `10000` | Render defaults to port 10000. Our `application.properties` reads `${PORT:8080}` automatically! |
| `DB_URL` | `jdbc:mysql://<db-host>:<port>/fitness?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` | Replace with your cloud database host and port |
| `DB_USERNAME` | `<your-db-username>` | Database user |
| `DB_PASSWORD` | `<your-db-password>` | Database password |
| `JWT_SECRET` | `nbag7ya9uwesjjjjj=skdjosidusd89y87whjsdkbsdjhbds78e7e77e=ekeke` | Or your custom 64+ char base64 secret |
| `JAVA_TOOL_OPTIONS` | `-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0` | **Crucial for Render Free Tier** (prevents JVM Out-Of-Memory crashes) |

---

### Step 5: Trigger Build & Verify Deployment

1. Click **Create Web Service**.
2. Render will automatically pull the repo, execute the multi-stage Docker build, cache Maven layers, and start the container.
3. Watch the **Logs** tab. You should see:
   ```
   Started FitnessApplication in X.XXX seconds (process running for X.XXX)
   ```
4. Render will assign you a live HTTPS URL:
   ```
   https://fitness-backend-xxxx.onrender.com
   ```
5. Test your live application by navigating to Swagger UI:
   ```
   https://fitness-backend-xxxx.onrender.com/swagger-ui.html
   ```

---

## ❓ Troubleshooting & FAQs

### 1. `java.lang.IllegalArgumentException: 'url' must start with "jdbc"`
- **Cause**: The `DB_URL` environment variable was empty or not provided.
- **Solution**: The application now includes default fallback values in `application.properties` and an in-memory H2 database for tests in `src/test/resources/application.properties`.

### 2. Render Deployment fails with "Out of Memory (OOM) Killed"
- **Cause**: By default, OpenJDK may attempt to reserve 25-50% of the host machine's total physical memory (which may be 32GB+ on Render's shared worker nodes), exceeding the 512MB container limit.
- **Solution**: Set the environment variable:
  `JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Xss256k"`
  This forces the JVM to calculate its heap size relative to the container's 512MB quota instead of the host machine.

### 3. Render Free Tier Spin-Down / Cold Starts
- **Behavior**: On Render's Free tier, web services spin down after 15 minutes of inactivity. The first request after sleep may take ~30-50 seconds to initialize.
- **Solution**: For production zero-latency requirements, upgrade the instance to Render's **Starter** tier ($7/month) or use an automated uptime monitor (like UptimeRobot) to ping `/api/auth` health every 10 minutes.

### 4. JWT Authorization returns 403 Forbidden on secured routes
- **Cause**: Spring Security `.hasRole("ADMIN")` checks for the authority `ROLE_ADMIN`.
- **Solution**: Our `JwtAuthFilter` automatically maps both `ADMIN` and `ROLE_ADMIN` into the `SecurityContext`, ensuring all role checks succeed smoothly.

---

## 📄 License
This project is licensed under the Apache 2.0 License.
