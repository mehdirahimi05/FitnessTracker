<div align="center">

# 🏋️ FitnessTracker System

**Modulare Spring Boot 3 & PostgreSQL Backend-Architektur mit vollständiger DevOps-Automatisierung: CI/CD Pipeline via GitHub Actions, SonarQube-Qualitätsanalyse, Multi-Stage Docker-Builds und Continuous Deployment auf Kubernetes mittels Helm-Chart.**

[![CI/CD Pipeline](https://github.com/mehdirahimi05/FitnessTracker/actions/workflows/build.yml/badge.svg)](https://github.com/mehdirahimi05/FitnessTracker/actions/workflows/build.yml)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.0-6DB33F?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker_Hub-mehdi2005-2496ED?style=flat&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-KinD_Cluster-326CE5?style=flat&logo=kubernetes&logoColor=white)
![Helm](https://img.shields.io/badge/Helm-v3_Chart-0F1689?style=flat&logo=helm&logoColor=white)

</div>


## 🔄 CI/CD Pipeline & Automatisierung

Die Pipeline ist in `.github/workflows/build.yml` definiert und gliedert sich in vier strikt getrennte, sequentielle Jobs:

```text
┌────────────────┐     ┌───────────────────────┐     ┌────────────────────────┐     ┌────────────────────────┐
│  JOB 1: Build  │ ──► │ JOB 2: Test & Analyze │ ──► │  JOB 3: Dockerization  │ ──► │ JOB 4: Helm CD Release │
│  & Checkstyle  │     │ (JUnit, JaCoCo, Sonar)│     │ (Build, Version & Push)│     │ (KinD Cluster & Smoke) │
└────────────────┘     └───────────────────────┘     └────────────────────────┘     └────────────────────────┘
```

1. **Job 1 (`build`):**
    * Richtet JDK 21 (Temurin) ein und cacht Maven-Dependencies (`~/.m2`).
    * Führt statische Codeanalyse via **Checkstyle** aus.
    * Baut die JAR-Dateien beider Module und lädt sie als Artefakt hoch.

2. **Job 2 (`test-and-analyze`):**
    * Führt alle Unit- und Slice-Tests mit JUnit 5 und Mockito aus.
    * Erstellt den Code-Coverage-Report via **JaCoCo**.
    * Führt den Quality-Gate-Scan auf dem **SonarQube-Server** aus.

3. **Job 3 (`docker`):**
    * Multi-Stage-Docker-Build für ein minimales Runtime-Image.
    * Login auf Docker Hub.
    * Push mit Versionierung: `${{ github.sha }}` (exakter Commit-Hash) und `latest`.

4. **Job 4 (`deploy`):**
    * Startet ein **KinD (Kubernetes in Docker)** Test-Cluster direkt auf der CI-VM.
    * Führt `helm lint` und `helm upgrade --install` mit dem versionierten Image-Tag aus.
    * Führt Live-Verifikation via `kubectl rollout status` und HTTP-Health-Check durch.

---

## Installation

```bash
git clone https://github.com/mehdirahimi05/FitnessTracker.git
cd FitnessTracker
docker compose up --build -d
```

API läuft danach unter `http://localhost:8080`.