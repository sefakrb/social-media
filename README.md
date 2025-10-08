# 🚀 Scorp Social Media API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF.svg)](https://github.com/features/actions)

A professional-grade **Spring Boot REST API** for social media functionality, built with modern Java practices and production-ready architecture.

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Technology Stack](#-technology-stack)
- [Quick Start](#-quick-start)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Testing](#-testing)
- [Docker Deployment](#-docker-deployment)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Configuration](#-configuration)
- [Performance Optimizations](#-performance-optimizations)
- [Security Considerations](#-security-considerations)
- [Contact](#-contact)

## ✨ Features

### Core Functionality
- 👤 **User Management** - Registration, profiles, and user information
- 📝 **Post Management** - Create, retrieve, and manage social media posts
- 👥 **Follow System** - Follow/unfollow users with relationship tracking
- ❤️ **Like System** - Like/unlike posts with engagement tracking
- 🔄 **Post Mixing Algorithm** - Intelligent post distribution for balanced feeds

### Technical Features
- 🏗️ **Clean Architecture** - Layered design with separation of concerns
- 🛡️ **Input Validation** - Comprehensive validation using Bean Validation
- 📊 **Pagination** - Efficient data retrieval with pagination support
- 🔍 **Global Exception Handling** - Centralized error handling with custom exceptions and meaningful responses
- 📚 **API Documentation** - Interactive Swagger/OpenAPI documentation
- 🧪 **Comprehensive Testing** - Unit, integration, and controller tests with high coverage
- 📝 **Structured Logging** - SLF4J logging with proper log levels
- 🐳 **Docker Support** - Containerized deployment with Docker Compose
- 🔄 **CI/CD Pipeline** - Automated builds and tests with GitHub Actions

## 🏗️ Architecture

The application follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │────│    Services     │────│  Repositories   │
│   (REST API)    │    │ (Business Logic)│    │  (Data Access)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      DTOs       │    │    Entities     │    │    Database     │
│ (Data Transfer) │    │   (Domain)      │    │   (PostgreSQL)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Design Patterns Used
- **Repository Pattern** - Data access abstraction
- **Service Layer Pattern** - Business logic separation
- **DTO Pattern** - Data transfer objects for API
- **Builder Pattern** - Entity construction
- **Strategy Pattern** - Post mixing algorithm

## 🛠️ Technology Stack

### Backend
- **Java 17** - Modern Java with latest features
- **Spring Boot 3.2.4** - Main application framework
- **Spring Data JPA** - Data persistence layer
- **Spring Validation** - Input validation
- **Spring Web** - REST API development

### Database
- **PostgreSQL 14** - Primary database
- **H2** - In-memory database for testing
- **Hibernate** - ORM framework

### Development & Testing
- **Lombok** - Code generation and boilerplate reduction
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Testcontainers** - Integration testing
- **Spring Boot Test** - Testing utilities

### Documentation & Monitoring
- **OpenAPI 3** - API documentation
- **Swagger UI** - Interactive API explorer
- **Spring Actuator** - Application monitoring
- **SLF4J + Logback** - Structured logging

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **GitHub Actions** - CI/CD automation
- **Maven** - Build and dependency management

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (optional)
- PostgreSQL 14 (if not using Docker)

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/sefakrb/social-media.git
   cd social-media
   ```

2. **Start PostgreSQL database**
   ```bash
   # Using Docker Compose
   docker-compose up -d postgres
   
   # Or start your local PostgreSQL instance
   # Make sure it's running on localhost:5432
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/api/v1/health`

### Docker Deployment

1. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

2. **Access services**
   - API: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - PgAdmin: `http://localhost:8888` (admin@example.com / admin)

## 📚 API Documentation

### Interactive Documentation
Visit `http://localhost:8080/swagger-ui.html` for interactive API documentation.

### Core Endpoints

#### Users
- `POST /api/v1/users` - Create a new user
- `GET /api/v1/users/{id}` - Get user by ID
- `GET /api/v1/users` - Get all users (paginated)

#### Posts
- `POST /api/v1/posts` - Create a new post
- `GET /api/v1/posts` - Get all posts (paginated)
- `GET /api/v1/posts/user/{userId}` - Get posts by user
- `POST /api/v1/posts/context` - Get posts with user context
- `POST /api/v1/posts/mix` - Mix posts by owners

#### Follows
- `POST /api/v1/follows` - Create follow relationship
- `GET /api/v1/follows` - Get all follows (paginated)
- `GET /api/v1/follows/following/{userId}` - Get users being followed
- `GET /api/v1/follows/followers/{userId}` - Get followers
- `DELETE /api/v1/follows/{followerId}/{followingId}` - Unfollow user

#### Likes
- `POST /api/v1/likes` - Like a post
- `GET /api/v1/likes` - Get all likes (paginated)
- `GET /api/v1/likes/post/{postId}` - Get likes for a post
- `GET /api/v1/likes/user/{userId}` - Get likes by user
- `DELETE /api/v1/likes/{userId}/{postId}` - Unlike a post
- `GET /api/v1/likes/check/{userId}/{postId}` - Check if user liked post

### Example API Calls

#### Create User
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "full_name": "John Doe",
    "profile_picture": "https://example.com/profile.jpg",
    "bio": "Software developer passionate about technology"
  }'
```

#### Create Post
```bash
curl -X POST http://localhost:8080/api/v1/posts \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Just had an amazing day at the beach! 🌊",
    "user_id": 1,
    "image_url": "https://example.com/beach-photo.jpg"
  }'
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/scorp/socialmedia/
│   │   ├── controller/          # REST API controllers
│   │   ├── service/             # Business logic services
│   │   ├── user/                # User domain module
│   │   │   ├── model/           # User entities, DTOs, repositories
│   │   │   ├── service/         # User business logic
│   │   │   └── mapper/          # User mappers
│   │   ├── post/                # Post domain module
│   │   ├── follow/              # Follow domain module
│   │   ├── like/                # Like domain module
│   │   ├── exception/           # Custom exceptions and handlers
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── BusinessException.java
│   │   │   └── ErrorResponse.java
│   │   ├── common/              # Shared components and base classes
│   │   └── SocialmediaApplication.java
│   └── resources/
│       ├── application.yml      # Application configuration
│       └── static/              # Static resources
└── test/
    ├── java/scorp/socialmedia/
    │   ├── controller/          # Controller tests (MockMvc)
    │   ├── service/             # Service tests (Mockito)
    │   └── integration/         # Integration tests (Testcontainers)
    └── resources/
        └── application-test.yml # Test configuration
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserControllerTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Types

1. **Unit Tests** - Test individual components in isolation
2. **Integration Tests** - Test component interactions
3. **Controller Tests** - Test REST API endpoints
4. **Service Tests** - Test business logic
5. **Repository Tests** - Test data access layer

### Test Coverage
The project maintains high test coverage with comprehensive test suites for all major components.

## 🐳 Docker Deployment

### Development Environment
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f api_service

# Stop services
docker-compose down
```

### Production Deployment
```bash
# Build production image
docker build -t social-media-api:latest .

# Run with production configuration
docker run -d \
  --name social-media-api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  social-media-api:latest
```

## 🔄 CI/CD Pipeline

The project uses **GitHub Actions** for continuous integration and deployment:

### Automated Workflow
- **Trigger**: Runs on every push to the repository
- **Build**: Builds application with Docker Compose
- **Test**: Runs all tests in containerized environment
- **Deploy**: Ready for deployment to production

### Workflow Configuration
See [`.github/workflows/build.yml`](.github/workflows/build.yml) for the complete CI/CD configuration.

```bash
# Manually trigger workflow
git push origin main
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/scorp
    username: root
    password: root
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

server:
  port: 8080
  servlet:
    context-path: /api/v1

app:
  pagination:
    default-page-size: 20
    max-page-size: 100
```

### Environment Variables
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_PROFILES_ACTIVE` - Active Spring profile

## 🚀 Performance Optimizations

- **Connection Pooling** - HikariCP for efficient database connections
- **Lazy Loading** - JPA lazy loading for relationships
- **Pagination** - Efficient data retrieval with pagination
- **Batch Operations** - Optimized batch inserts and updates
- **Caching** - Strategic caching for frequently accessed data

## 🔒 Security Considerations

- **Input Validation** - Comprehensive validation on all inputs using Bean Validation
- **SQL Injection Prevention** - JPA/Hibernate parameterized queries
- **Error Handling** - Secure error messages without sensitive data exposure
- **Exception Management** - Custom exceptions with proper HTTP status codes
- **CORS Configuration** - Configurable cross-origin resource sharing

## 📈 Monitoring & Observability

- **Health Checks** - Spring Actuator health endpoints
- **Metrics** - Application metrics via Actuator
- **Logging** - Structured logging with SLF4J
- **Profiling** - Spring Boot DevTools for development

### Development Guidelines
- Follow Java coding conventions
- Write comprehensive tests
- Update documentation
- Use meaningful commit messages
- Ensure all tests pass

## 👨‍💻 Contact

**Sefa Karabaş**
- Email: sefaa.karabas@gmail.com
- Website: [sefakarabas.com](https://sefakarabas.com/)
- LinkedIn: [Sefa Karabaş](https://linkedin.com/in/sefakarabas)

Project Link: [https://github.com/sefakrb/social-media](https://github.com/sefakrb/social-media)
