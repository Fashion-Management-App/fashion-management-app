# Fashion Management Application

A Spring Boot web application for managing fashion atelier fitting records.  
The application allows users to create, view, and delete customer fitting entries using a simple and responsive interface.

---

# Features

- View all fashion fitting records
- Add new records
- Delete records
- MongoDB database integration
- Thymeleaf-based frontend
- Docker support
- Playwright end-to-end testing
- GitHub Actions CI workflow

---

# Technologies Used

- Java 17
- Spring Boot
- Spring MVC
- Spring Data MongoDB
- Thymeleaf
- Maven
- Docker
- Playwright
- JUnit 5

---

# Requirements

Before running the project locally, make sure you have installed:

- Java 17+
- Maven 3.9+
- MongoDB
- Git

Optional:
- Docker Desktop

---

# Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/fashion-management-app.git
cd fashion-management-app/fashion-app-mongodb
```

---

# Configure MongoDB

Make sure MongoDB is running locally.

Default MongoDB connection:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/fashion_db
spring.data.mongodb.database=fashion_db
server.port=8080
```

File location:

```txt
src/main/resources/application.properties
```

---

# Run Application Locally

## Using Maven

```bash
mvn spring-boot:run
```

Application will be available at:

```txt
http://localhost:8080
```

---

# Build Project

```bash
mvn clean package
```

Generated JAR file:

```txt
target/fashion-management-app-0.3.0-SNAPSHOT.jar
```

Run JAR manually:

```bash
java -jar target/fashion-management-app-0.3.0-SNAPSHOT.jar
```

---

# Run Tests

## Unit and Integration Tests

```bash
mvn test
```

---

# Run Playwright E2E Tests

Set environment variable:

## Windows PowerShell

```powershell
$env:E2E_BASE_URL="http://localhost:8080"
```

## Linux / macOS

```bash
export E2E_BASE_URL=http://localhost:8080
```

Run tests:

```bash
mvn test
```

---

# Run with Docker

## Build Docker Image

```bash
docker build -f deploy/Dockerfile -t fashion-management-app .
```

## Run Container

```bash
docker run -p 8080:8080 fashion-management-app
```

Application:

```txt
http://localhost:8080
```

---

# Project Structure

```txt
fashion-app-mongodb/
│
├── src/
│   ├── main/
│   │   ├── java/com/fashion/
│   │   ├── resources/
│   │   └── templates/
│   │
│   └── test/
│       └── java/com/fashion/
│
├── deploy/
│   └── Dockerfile
│
├── .github/workflows/
│
├── pom.xml
└── README.md
```

---

# CI/CD

The project includes GitHub Actions workflows for:

- Maven build validation
- Checkstyle validation
- Automated Playwright E2E testing
- Docker image build

---

# License

This project is created for educational purposes.










