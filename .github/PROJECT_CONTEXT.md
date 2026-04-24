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
│   │   │   ├── Application.kt            # Spring Boot main application class
│   │   │   ├── config/                   # Configuration classes
│   │   │   │   ├── RabbitMQConfig.kt     # RabbitMQ configuration
│   │   │   │   └── RedisConfig.kt        # Redis configuration
│   │   │   ├── datasources/              # Data access layer
│   │   │   │   └── dynamicobject/        # Dynamic object data access
│   │   │   │       ├── DynamicObjectRepositoryImpl.kt  # Repository implementation
│   │   │   │       ├── DynamicObjectRepositoryMongo.kt  # MongoDB repository
│   │   │   │       ├── mappers/          # Mapping utilities
│   │   │   │       │   └── DynamicObjectMapper.kt
│   │   │   │       └── model/            # MongoDB models
│   │   │   ├── entities/                 # Domain entities
│   │   │   │   ├── DynamicObject.kt      # Main entity
│   │   │   │   ├── DynamicObjectStatus.kt # Status enum
│   │   │   │   ├── DynamicObjectType.kt  # Type enum
│   │   │   │   └── pojos/                # POJOs
│   │   │   │       ├── IPojo.kt          # Base interface
│   │   │   │       ├── PipelinePojo.kt   # Pipeline POJO
│   │   │   │       └── ScriptPojo.kt     # Script POJO
│   │   │   ├── iteractors/               # Business logic layer
│   │   │   │   ├── DynamicObjectService.kt # Service class
│   │   │   │   └── components/           # Components
│   │   │   │       └── DynamicObjectValidatorComponent.kt
│   │   │   ├── repositories/             # Repository interfaces
│   │   │   │   └── DynamicObjectRepository.kt
│   │   │   └── transportlayers/          # Transport layer
│   │   │       ├── DynamicObjectApi.kt   # API interface
│   │   │       └── impl/                 # API implementations
│   │   │           └── DynamicObjectApiImpl.kt
│   │   └── resources/
│   │       ├── application.yaml          # Application properties
│   │       ├── static/                   # Static files
│   │       └── templates/                # HTML templates
│   └── test/
│       └── kotlin/br/com/abegg/abeflow/service/
│           └── ApplicationTests.kt       # Integration tests
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
| `config` | Spring configuration | ✅ IMPLEMENTED | RabbitMQConfig.kt, RedisConfig.kt |
| `datasources` | Data access layer | ✅ IMPLEMENTED | DynamicObjectRepositoryImpl.kt, DynamicObjectRepositoryMongo.kt |
| `entities` | Domain entities | ✅ IMPLEMENTED | DynamicObject.kt, DynamicObjectStatus.kt, DynamicObjectType.kt |
| `iteractors` | Business logic | ✅ IMPLEMENTED | DynamicObjectService.kt, DynamicObjectValidatorComponent.kt |
| `repositories` | Repository interfaces | ✅ IMPLEMENTED | DynamicObjectRepository.kt |
| `transportlayers` | Transport layer | ✅ IMPLEMENTED | DynamicObjectApi.kt, DynamicObjectApiImpl.kt |

---

## Core Concepts & Entities

### DynamicObject
- **Purpose**: Represents a dynamic object that can be a pipeline or script
- **Location**: `entities/DynamicObject.kt`
- **Properties**: 
  - `id`: Unique identifier (scriptId + version)
  - `scriptId`: Script identifier
  - `version`: Version number
  - `type`: Type (PIPELINE or SCRIPT)
  - `status`: Status (ACTIVE, INACTIVE, etc.)
  - `isMain`: Whether it's the main version
  - `content`: The content (pipeline or script data)
  - `createdAt`, `updatedAt`: Timestamps

### PipelinePojo
- **Purpose**: Plain old Java object for pipeline data
- **Location**: `entities/pojos/PipelinePojo.kt`
- **Properties**:
  - Pipeline-specific data structure

### ScriptPojo
- **Purpose**: Plain old Java object for script data
- **Location**: `entities/pojos/ScriptPojo.kt`
- **Properties**:
  - Script-specific data structure
---

## API Endpoints (Implemented)

### Dynamic Object Management
```
GET    /api/v1/dynamic-object/query              → Query all dynamic objects
GET    /api/v1/dynamic-object/{id}/version/{version} → Get dynamic object by ID and version
POST   /api/v1/dynamic-object                    → Save a dynamic object
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
| Framework | Spring Boot | 4.1.0-M4 |
| Build | Maven | 3.6+    |
| JVM | Java | 21       |
| Testing | JUnit 5, Mockito | Latest  |
| Database | MongoDB | Latest  |
| Cache | Redis | Latest  |
| Message Queue | RabbitMQ | Latest  |
| WebSocket | Spring WebSocket | Latest  |
| API Documentation | SpringDoc OpenAPI | 3.0.2   |

---

## Configuration (application.yaml)

```yaml
spring:
  application:
    name: abeflow-service
  data:
    mongodb:
      uri: mongodb://localhost:27017/abeflow
      auto-index-creation: true
    redis:
      host: localhost
      port: 6379
  rabbitmq:
    host: localhost
    port: 5672
    username: abeflow_user
    password: abeflow_pass

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
- ✅ Database (MongoDB)
- ✅ Message Queue (RabbitMQ)
- ✅ Cache (Redis)
- ✅ Health & Monitoring (Spring Boot Actuator)
- ✅ API Documentation (SpringDoc OpenAPI)

### To Be Integrated
- [ ] Logging (SLF4J + Logback)
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
