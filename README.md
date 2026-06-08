<div align="center">

# 🏋️ FitnessTracker

**A self-built fitness backend — because every existing app was either too expensive or too bloated.**

Track your workouts, nutrition, body measurements and training plans in one place.

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.4-6DB33F?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![CI](https://img.shields.io/badge/CI/CD-GitHub_Actions-2088FF?style=flat&logo=github-actions&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active_Development-brightgreen?style=flat)

</div>

---

## Features

- 🔐 **Auth** — Register, login, JWT authentication, role-based access (GUEST / USER / ADMIN)
- 🏃 **Training Sessions** — Log workouts, filter by date/difficulty, track streaks
- 🥗 **Nutrition** — Log meals by type, get daily summaries
- 📋 **Workout Plans** — Build plans with exercises, sets and reps
- 📏 **Body Measurements** — Track weight, height, body fat, BMI
- 📊 **Daily Dashboard** — Full overview of training and nutrition for any day

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4 |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Security | Spring Security + JWT (JJWT 0.12) |
| Build | Maven (Multi-Module) |
| Testing | JUnit, Mockito, Spring Tests |
| Quality | JaCoCo, SonarQube, Checkstyle |
| CI/CD | GitHub Actions |
| Container | Docker |

---

## Installation

### Prerequisites

- Java 21
- Maven
- PostgreSQL
- Docker (optional)

### Option 1 — Docker (recommended)

```bash
git clone https://github.com/mehdirahimi05/FitnessTracker.git
cd FitnessTracker
docker compose up
```

### Option 2 — Manual

```bash
git clone https://github.com/mehdirahimi05/FitnessTracker.git
cd FitnessTracker
```

Configure `api/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fitness_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your-secret-key-minimum-256-bit
jwt.expiration=86400000
```

```bash
mvn clean install -DskipTests
cd api
mvn spring-boot:run
```

App runs on `http://localhost:8080`

---

## Author

**Mehdi Rahimi**  
Angewandte Informatik, FH Erfurt — 4th Semester  
[github.com/mehdirahimi05](https://github.com/mehdirahimi05)
