# Abeflow Project Context & Structure

## Project Overview
Abeflow is an open-source service for generating dynamic pipelines in programmatic or visual ways, licensed under Apache 2.0 + Commons Clause (no commercial use allowed).

---

## Directory Structure

```
service--abeflow/
├── .github/                              # GitHub configuration and templates
│   ├── copilot-instructions.md           # AI context and guidelines
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md                 # Bug report template
│   │   └── feature_request.md            # Feature request template
│   └── PULL_REQUEST_TEMPLATE.md          # Pull request template
├── .mvn/                                 # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── kotlin/br/com/abegg/abeflow/service/
│   │   │   ├── AbeflowApplication.kt     # Spring Boot main application class
│   │   │   ├── config/                   # Configuration classes (TO BE CREATED)
│   │   │   │   └── ApplicationConfig.kt
│   │   │   ├── controller/               # REST API controllers (TO BE CREATED)
│   │   │   │   └── PipelineController.kt
│   │   │   ├── service/                  # Business logic services (TO BE CREATED)
│   │   │   │   ├── PipelineService.kt
│   │   │   │   └── ValidationService.kt
│   │   │   ├── model/                    # Data models and DTOs (TO BE CREATED)
│   │   │   │   ├── Pipeline.kt
│   │   │   │   ├── PipelineNode.kt
│   │   │   │   └── PipelineDTO.kt
│   │   │   ├── repository/               # Data access layer (TO BE CREATED)
│   │   │   │   └── PipelineRepository.kt
│   │   │   ├── exception/                # Custom exceptions (TO BE CREATED)
│   │   │   │   ├── PipelineException.kt
│   │   │   │   └── ValidationException.kt
│   │   │   └── util/                     # Utility classes (TO BE CREATED)
│   │   │       └── PipelineValidator.kt
│   │   └── resources/
│   │       ├── application.yaml          # Application properties
│   │       ├── static/                   # Static files (CSS, JS, images)
│   │       └── templates/                # HTML templates
│   └── test/
│       └── kotlin/br/com/abegg/abeflow/service/
│           ├── AbeflowApplicationTests.kt
│           ├── controller/               # Controller tests
│           ├── service/                  # Service tests
│           └── integration/              # Integration tests
├── mvnw                                  # Maven wrapper script
├── mvnw.cmd                              # Maven wrapper script (Windows)
├── pom.xml                               # Maven configuration
├── .cursorrules                          # Cursor IDE rules
├── .gitignore                            # Git ignore rules
├── LICENSE                               # Apache 2.0 + Commons Clause
├── README.md                             # Project documentation
└── PROJECT_CONTEXT.md                    # This file
```

---

## Key Packages & Their Responsibilities

| Package | Purpose | Status | Key Files |
|---------|---------|--------|-----------|
| `controller` | REST API endpoints | ❌ TO CREATE | Pipeline operations, health checks |
| `service` | Business logic | ❌ TO CREATE | Pipeline generation, validation |
| `model` | Data structures | ❌ TO CREATE | Pipeline, Node, DTO classes |
| `repository` | Data persistence | ❌ TO CREATE | Database/storage access |
| `config` | Spring configuration | ❌ TO CREATE | Beans, application setup |
| `exception` | Custom exceptions | ❌ TO CREATE | Error handling |
| `util` | Helper utilities | ❌ TO CREATE | Validators, converters |

---

## Core Concepts & Entities

### Pipeline
- **Purpose**: Represents a workflow/DAG of executable steps
- **Location**: `model/Pipeline.kt`
- **Properties**: 
  - `id`: Unique identifier
  - `name`: Pipeline name
  - `description`: Pipeline description
  - `nodes`: List of PipelineNode
  - `edges`: List of connections
  - `status`: ACTIVE, INACTIVE, ARCHIVED
  - `createdAt`, `updatedAt`: Timestamps

### PipelineNode
- **Purpose**: Individual step/task in a pipeline
- **Location**: `model/PipelineNode.kt`
- **Properties**:
  - `id`: Node identifier
  - `type`: NODE_TYPE (processor, input, output, conditional, etc.)
  - `name`: Node name
  - `configuration`: Key-value config map
  - `position`: X, Y coordinates for visual representation

### Pipeline Edges
- **Purpose**: Connections between nodes
- **Properties**:
  - `source`: Source node ID
  - `target`: Target node ID
  - `condition`: Optional condition expression

---

## API Endpoints (To Be Implemented)

### Pipeline Management
```
POST   /api/v1/pipelines              → Create new pipeline
GET    /api/v1/pipelines              → List all pipelines
GET    /api/v1/pipelines/{id}         → Get pipeline details
PUT    /api/v1/pipelines/{id}         → Update pipeline
DELETE /api/v1/pipelines/{id}         → Delete pipeline
```

### Pipeline Execution
```
POST   /api/v1/pipelines/{id}/execute → Execute pipeline
GET    /api/v1/pipelines/{id}/status  → Get execution status
GET    /api/v1/pipelines/{id}/history → Get execution history
```

### Health & Monitoring (via Spring Actuator)
```
GET    /actuator                       → List all actuator endpoints
GET    /actuator/health                → Health check
GET    /actuator/health/live           → Liveness probe
GET    /actuator/health/ready          → Readiness probe
GET    /actuator/metrics               → Application metrics
GET    /actuator/info                  → Application info
GET    /actuator/prometheus            → Prometheus metrics
```

---

## Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Kotlin | Latest  |
| Framework | Spring Boot | 3.x     |
| Build | Maven | 3.6+    |
| JVM | Java | 25+     |
| Testing | JUnit 5, Mockito | Latest  |
| Database | TBD | -       |
| API Documentation | SpringDoc OpenAPI | -       |

---

## Configuration (application.yaml)

```yaml
spring:
  application:
    name: abeflow-service
  jpa:
    hibernate:
      ddl-auto: validate
  datasource:
    url: jdbc:h2:mem:testdb  # TBD: Change for production
    driverClassName: org.h2.Driver

server:
  port: 8080
  servlet:
    context-path: /

logging:
  level:
    root: INFO
    br.com.abegg.abeflow: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: health, metrics, info, prometheus
      base-path: /actuator
  endpoint:
    health:
      show-details: when-authorized
      probes:
        enabled: true
  health:
    livenessState:
      enabled: true
    readinessState:
      enabled: true

abeflow:
  api:
    version: v1
    base-path: /api/v1
```

---

## Spring Boot Actuator

The project uses **Spring Boot Actuator** for monitoring and health checks. This provides production-ready endpoints without the need for custom implementations.

### Exposed Endpoints
- **`/actuator/health`**: General health status of the application
- **`/actuator/health/live`**: Liveness probe (is the app running?)
- **`/actuator/health/ready`**: Readiness probe (is the app ready to handle requests?)
- **`/actuator/metrics`**: Application metrics (request counts, response times, etc.)
- **`/actuator/info`**: Application information and version
- **`/actuator/prometheus`**: Metrics in Prometheus format

### Configuration
The Actuator is configured in `application.yaml`:
- Base path: `/actuator`
- Health details shown only to authorized users (for security)
- Liveness and readiness probes enabled for Kubernetes deployments
- Prometheus metrics exposed for monitoring integration

### Usage
No additional code is needed—Actuator automatically provides health checks and metrics. Simply access the endpoints above.

---

## Development Guidelines When Creating Files

### 1. Creating a New Controller
- **Location**: `src/main/kotlin/br/com/abegg/abeflow/service/controller/`
- **Naming**: `{EntityName}Controller.kt`
- **Pattern**: Extend `RestController`, use `@RequestMapping`
- **Example Path**: `/api/v1/{entity}`

### 2. Creating a New Service
- **Location**: `src/main/kotlin/br/com/abegg/abeflow/service/service/`
- **Naming**: `{EntityName}Service.kt` (interface) + `{EntityName}ServiceImpl.kt`
- **Pattern**: `@Service`, dependency injection
- **Responsibility**: Business logic, validation

### 3. Creating a New Model
- **Location**: `src/main/kotlin/br/com/abegg/abeflow/service/model/`
- **Naming**: `{EntityName}.kt`
- **Pattern**: Data classes, `@Entity`, `@Table` if JPA

### 4. Creating a New DTO
- **Location**: `src/main/kotlin/br/com/abegg/abeflow/service/model/`
- **Naming**: `{EntityName}DTO.kt` or `{EntityName}Request.kt`, `{EntityName}Response.kt`
- **Pattern**: Data classes for API communication

### 5. Creating Tests
- **Location**: `src/test/kotlin/br/com/abegg/abeflow/service/`
- **Naming**: `{Class}Test.kt`
- **Pattern**: Mirror source structure, use JUnit 5 + Mockito
- **Coverage**: Aim for >80% code coverage

---

## Build & Run Commands

```bash
# Build
./mvnw clean compile

# Run tests
./mvnw test

# Run application
./mvnw spring-boot:run

# Package
./mvnw clean package

# Run packaged jar
java -jar target/abeflow-service-1.0.0.jar
```

---

## Kotlin Coding Conventions for This Project

1. **Use data classes** for simple models
2. **Use sealed classes** for restricted class hierarchies
3. **Prefer immutability** where possible
4. **Use meaningful names** (no abbreviations)
5. **Add kdoc comments** for public functions
6. **Use extension functions** where appropriate
7. **Avoid null references** - use `Optional` or `?` sparingly
8. **Use `when` expressions** instead of `if-else chains`

---

## Integration Points

### Completed
- ✅ Health & Monitoring (Spring Boot Actuator)

### To Be Integrated
- [ ] Database (H2, PostgreSQL, or MongoDB)
- [ ] Message Queue (RabbitMQ or Kafka for async pipeline execution)
- [ ] Cache (Redis for performance)
- [ ] Logging (SLF4J + Logback)
- [ ] API Documentation (Swagger/OpenAPI)
- [ ] Authentication/Authorization (Spring Security)

---

## Notes for AI Assistants

- **Do NOT** suggest paid or proprietary solutions
- **Keep everything open-source friendly** (Apache, MIT, GPL compatible)
- **Follow Spring Boot best practices** for configuration and annotations
- **Write defensive code** with proper error handling
- **Add logging** at appropriate levels (DEBUG, INFO, WARN, ERROR)
- **Structure code** for testability and modularity
- **Document complex logic** with comments
- **Use DTOs** for API responses to decouple from entities
- **Always include unit tests** for new features

