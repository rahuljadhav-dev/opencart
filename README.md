# 🛒 OpenCart Test Automation Framework

A **Hybrid Test Automation Framework** built for the [OpenCart demo e-commerce application](https://tutorialsninja.com/demo/), combining **Selenium WebDriver**, **TestNG**, and **Cucumber (BDD)**, designed using the **Page Object Model (POM)** with **data-driven testing**, **cross-browser & remote execution**, and integrated **Allure / ExtentReports** reporting.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium-4.33.0-43B02A?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-blue)
![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-23D96C?logo=cucumber)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)
![Allure](https://img.shields.io/badge/Reports-Allure-FF6E42)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Framework Design](#-framework-design)
- [Test Coverage](#-test-coverage)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Running Tests](#-running-tests)
- [Running on Selenium Grid (Docker)](#-running-on-selenium-grid-docker)
- [Test Reports](#-test-reports)
- [Configuration](#-configuration)
- [Author](#-author)

---

## 🔎 Overview

This repository contains an end-to-end UI test automation suite for **OpenCart** — a demo online store — covering **account registration**, **login (positive & data-driven)**, and the same login flow re-implemented in **Cucumber/Gherkin** for BDD-style testing.

The framework is built to demonstrate real-world automation practices:

- ✅ Page Object Model (POM) for maintainable, reusable UI interactions
- ✅ Data-driven testing using Excel (Apache POI)
- ✅ Both **TestNG-style** and **BDD/Cucumber-style** test authoring in one project
- ✅ Local **and** remote/cross-browser execution via Selenium Grid
- ✅ Centralized logging (Log4j2) and dual reporting (Allure + ExtentReports)
- ✅ Dockerized environment for consistent, portable test runs
- ✅ Group-based execution (`sanity`, `regression`, `master`) for CI-friendly test selection

---

## 🧰 Tech Stack

| Category | Tool / Library | Version |
|---|---|---|
| Language | Java | 17 |
| Build Tool | Maven | 3.9.x |
| UI Automation | Selenium WebDriver | 4.33.0 |
| Test Runner | TestNG | 7.11.0 |
| BDD | Cucumber (Java + TestNG) | 7.14.0 |
| Test Data | Apache POI (Excel) | 5.4.1 |
| Logging | Log4j2 | 2.24.x / 2.25.x |
| Reporting | Allure | 2.24.0 |
| Reporting | ExtentReports | 5.1.2 |
| Containerization | Docker / Docker Compose | — |
| Remote Execution | Selenium Grid (Hub + Chrome/Firefox nodes) | — |
| Utilities | Apache Commons (IO, Lang3) | — |

---

## 📁 Project Structure

```
opencart/
├── src/test/java
│   ├── testCases/                 # TestNG test classes
│   │   ├── TC001_AccountRegistrationTest.java
│   │   ├── TC002_LoginTest.java
│   │   └── TC003_DataDrivenTest.java
│   │
│   ├── pageObjects/                # Page Object Model classes
│   │   ├── BasePage.java           # Common PageFactory init logic
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── AccountRegistrationPage.java
│   │   └── MyAccountPage.java
│   │
│   ├── testBase/                   # Base test setup/teardown, driver management
│   │
│   ├── features/                   # Cucumber Gherkin feature files
│   │   └── Login.feature
│   │
│   ├── stepdefinitions/            # Step definitions mapped to feature files
│   │   └── LoginSteps.java
│   │
│   ├── testrunners/                 # Cucumber-TestNG runner
│   │   └── TestRunner.java
│   │
│   └── utilities/                   # Reusable helper classes
│       ├── ExcelUtils.java          # Apache POI Excel reader/writer
│       ├── LoginDataDrivenTest.java # @DataProvider source for data-driven tests
│       ├── ExtentReportUtility.java # TestNG listener → ExtentReports
│       ├── AllureTestListener.java  # TestNG listener → Allure
│       ├── FileUpload.java
│       └── Functional.java
│
├── src/test/resources
│   ├── config.properties           # Environment/app config (URL, credentials, execution mode)
│   └── log4j2.properties           # Logging configuration
│
├── testData/
│   └── registeredAccounts.xlsx     # Data-driven test input (email/password/expected result)
│
├── testng.xml                      # Default suite (Registration, Login, Data-Driven tests)
├── grouping.xml                    # Group-based suite (sanity / regression / master)
├── cross_browser.xml               # Parallel cross-browser suite
├── docker-compose.yaml             # Selenium Grid (Hub + Chrome + Firefox nodes)
├── Dockerfile                      # Containerized Maven + JDK 17 test runner
├── run.bat                         # Windows one-click test runner
└── pom.xml                         # Maven dependencies & build configuration
```

---

## 🏗 Framework Design

This is a **Hybrid Framework**, combining multiple design approaches:

| Layer | Approach | Purpose |
|---|---|---|
| **Design Pattern** | Page Object Model + PageFactory | Separates locators/UI actions from test logic |
| **Test Style 1** | TestNG classes (`testCases/`) | Traditional Java-based test authoring |
| **Test Style 2** | Cucumber BDD (`features/` + `stepdefinitions/`) | Plain-English scenarios for BDD/stakeholder readability |
| **Test Data** | Excel via Apache POI (`ExcelUtils`) | Data-driven execution, no hardcoded test data |
| **Execution** | Local WebDriver or RemoteWebDriver (Selenium Grid) | Configurable via `config.properties` |
| **Reporting** | Allure + ExtentReports (via TestNG Listeners) | Dual reporting for detailed, visual test results |
| **Logging** | Log4j2 | Structured, level-based logs for debugging |

**Key design decisions:**
- `BasePage` centralizes `PageFactory.initElements()` so every page object stays DRY.
- `BaseClass` (in `testBase/`) handles browser setup/teardown, reads `os`/`browser` via TestNG `@Parameters`, and switches between local and remote (Grid) execution based on `config.properties`.
- Random test data (name, email, phone) is generated per run using Apache Commons `RandomStringUtils` to avoid duplicate-registration conflicts.
- Screenshots are automatically captured on failure and attached to the Allure report.

---

## ✅ Test Coverage

| Test Case | Type | Description |
|---|---|---|
| `TC001_AccountRegistrationTest` | TestNG | Registers a new user with randomly generated data |
| `TC002_LoginTest` | TestNG | Verifies login with valid credentials from `config.properties` |
| `TC003_DataDrivenTest` | TestNG + Excel | Runs login with multiple valid/invalid credential sets from `registeredAccounts.xlsx`, validating both positive and negative scenarios |
| `Login.feature` | Cucumber (BDD) | Same login flow written in Gherkin (`Given / When / Then`), executed via `TestRunner` |

---

## 🔧 Prerequisites

Make sure you have the following installed:

- **Java JDK 17+**
- **Maven 3.9+**
- **Google Chrome** / **Microsoft Edge** / **Mozilla Firefox** (for local runs)
- **Docker & Docker Compose** (optional — only needed for Grid-based remote execution)
- An IDE such as **Eclipse** or **IntelliJ IDEA** (optional, for development)

---

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/rahuljadhav-dev/opencart.git
   cd opencart
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure your environment**
   Update `src/test/resources/config.properties` with the app URL, credentials, and execution mode (see [Configuration](#-configuration) below).

---

## ▶️ Running Tests

### Run the default suite (Registration + Login + Data-Driven)
```bash
mvn clean test
```
This uses `testng.xml`, wired via the `maven-surefire-plugin` in `pom.xml`.

### Run a specific TestNG suite
```bash
mvn test -DsuiteXmlFile=grouping.xml       # group-based (sanity/regression/master)
mvn test -DsuiteXmlFile=cross_browser.xml  # parallel cross-browser run
```

### Run only a specific group
Edit `grouping.xml` and uncomment the desired group (`sanity` / `regression` / `master`) under `<groups><run>`, then:
```bash
mvn test -DsuiteXmlFile=grouping.xml
```

### Run Cucumber BDD tests
Executed automatically through `TestRunner.java` (Cucumber-TestNG) — include it in your suite XML or run directly from your IDE as a TestNG test.

### Windows quick-run
```bash
run.bat
```

---

## 🐳 Running on Selenium Grid (Docker)

This project ships with a ready-to-use Selenium Grid setup (1 Hub + Chrome node + Firefox node).

1. **Start the Grid**
   ```bash
   docker-compose up -d
   ```
   Hub console available at: `http://localhost:4444`

2. **Set execution mode to remote** in `config.properties`:
   ```properties
   execution_env=remote
   ```

3. **Run your tests** — they'll now execute against the Grid nodes instead of your local browser.

4. **Run everything (including the app) fully containerized:**
   ```bash
   docker build -t opencart-tests .
   docker run opencart-tests
   ```

---

## 📊 Test Reports

Two reporting tools are integrated via TestNG listeners (`utilities/ExtentReportUtility.java`, `utilities/AllureTestListener.java`):

### Allure Report
```bash
mvn allure:report
mvn allure:serve
```
Generates an interactive HTML report from `allure-results/` into `allure-report/`, including step logs and failure screenshots.

### ExtentReports
Generated automatically after each run — check the `reports/` and `test-output/` directories for the HTML report.

### TestNG Native Report
Default TestNG HTML/XML report available under `test-output/` after every run.

---

## ⚙️ Configuration

All environment-level settings live in **`src/test/resources/config.properties`**:

| Property | Description |
|---|---|
| `appUrl` | Base URL of the application under test |
| `email` / `password` | Default valid login credentials used by `TC002_LoginTest` |
| `execution_env` | `local` (default WebDriver) or `remote` (Selenium Grid via `RemoteWebDriver`) |

Logging behavior (log level, file output) is configured in **`src/test/resources/log4j2.properties`**.

---

## 👤 Author

**Rahul Jadhav**
Software Engineer

Feel free to explore, fork, or raise an issue if you spot something worth improving. 🚀
