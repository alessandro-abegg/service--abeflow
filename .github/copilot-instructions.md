# GitHub Copilot Instructions for Abeflow Service

## Project Overview
Abeflow is an open-source service designed to generate dynamic pipelines in both programmatic and visual ways. This project allows users to create flexible pipelines without financial earnings restrictions, promoting accessibility and collaboration in the open-source community. The main idea is to permit the generation of dynamic pipelines programmatically or visually.

## Tech Stack
- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: MongoDB
- **Cache**: Redis
- **Message Queue**: RabbitMQ
- **Authentication Database**: MySQL (for Keycloak)
- **Authentication**: OAuth2 with Keycloak (RHSSO)
- **Configuration**: YAML-based application properties
- **Package Structure**: br.com.abegg.abeflow.service

## Key Directories

### Main Source Code (`src/main/kotlin/br/com/abegg/abeflow/service/`)
- `Application.kt`: Spring Boot application entry point
- `config/`: Configuration classes
  - `RabbitMQConfig.kt`: RabbitMQ messaging configuration
  - `RedisConfig.kt`: Redis caching configuration
  - `oauth2/SecurityConfig.kt`: Security configuration for OAuth2 resource server
- `datasources/`: Data source and repository layer
  - `dynamicobject/`: Dynamic object data access
    - `DynamicObjectRepositoryImpl.kt`: MongoDB aggregation queries and repository implementation
    - `DynamicObjectRepositoryMongo.kt`: MongoDB repository interface
    - `mappers/`: Mapping functions between entities and models
      - `DynamicObjectMapper.kt`: Entity-model mapping utilities
    - `model/`: MongoDB document models
- `entities/`: Domain entities and models
  - `DynamicObject.kt`: Main domain entity
  - `DynamicObjectStatus.kt`: Status enumeration
  - `DynamicObjectType.kt`: Type enumeration
  - `pojos/`: Plain old Java objects
    - `IPojo.kt`: Base interface for POJOs
    - `PipelinePojo.kt`: Pipeline data structure
    - `ScriptPojo.kt`: Script data structure
- `iteractors/`: Business logic and interactor implementations
  - `DynamicObjectService.kt`: Business logic for dynamic objects
  - `components/`: Reusable components
    - `DynamicObjectValidatorComponent.kt`: Validation logic component
- `repositories/`: Repository interfaces
  - `DynamicObjectRepository.kt`: Repository interface for dynamic objects
- `transportlayers/`: HTTP transport layer, controllers, and request/response DTOs
  - `DynamicObjectApi.kt`: API interface
  - `impl/`: API implementations
    - `DynamicObjectApiImpl.kt`: REST API implementation

### Resources (`src/main/resources/`)
- `application.yaml`: Application configuration and properties
- `static/`: Static assets (CSS, JavaScript, images)
- `templates/`: Template files for dynamic content

### Test Code (`src/test/kotlin/br/com/abegg/abeflow/service/`)
- `AbeflowApplicationTests.kt`: Main application integration tests
- `core/usecase/CreatePipelineUseCaseTest.kt`: Use case unit tests

## Coding Standards
- Follow Kotlin coding conventions.
- Use Spring Boot best practices for REST APIs, configuration, and dependency injection.
- Write unit tests for all new features using JUnit and Mockito.
- Use meaningful variable and function names.
- Add comments for complex logic.
- Prefer functional programming styles where appropriate in Kotlin.

## Development Workflow
- Start Docker services: `docker-compose up -d` (includes MongoDB, Redis, RabbitMQ, MySQL, Keycloak)
- Use Maven for building: `./mvnw clean compile`
- Run tests: `./mvnw test`
- Run application: `./mvnw spring-boot:run`
- Ensure all code is committed with descriptive messages.
- Use GitHub Issues for tracking bugs and features.

## AI Assistant Guidelines
- When suggesting code, ensure it aligns with the project's open-source nature and dynamic pipeline focus.
- Prefer using Spring Boot annotations and configurations correctly.
- For pipeline-related features, think in terms of modularity, extensibility, and reusability.
- Suggest using Kotlin's features like data classes, sealed classes, and coroutines where beneficial.
- Avoid suggesting proprietary or paid solutions; keep everything open-source friendly.
- When adding dependencies, check for compatibility with the Apache 2.0 + Commons Clause license.

## License
The project is licensed under Apache License 2.0 modified by the Commons Clause, which allows use and modification but prohibits selling the software or services based on it.

## Monitoring & Health Checks
- The project uses **Spring Boot Actuator** for health checks and monitoring
- No custom health endpoint controllers needed
- Actuator endpoints available at `/actuator` (health, metrics, info, prometheus)
- Health probes (liveness, readiness) enabled for Kubernetes deployments

## Authentication
- The project uses **Keycloak (RHSSO)** for identity and access management
- Keycloak is configured with MySQL database for persistent storage of realms and configurations
- Configured as OAuth2 resource server with JWT validation
- Pre-configured realm "abeflow" with client "abeflow-service" in Docker setup
- Keycloak admin console available at http://localhost:8180 (admin/admin)
- Service account authentication for API access

## Docker Compose Standards & Best Practices

### Service Startup Order & Health Checks
- **Pattern**: Services with dependencies use `depends_on` with `condition: service_healthy`
- **Implementation**: Each service that provides external dependencies must include a `healthcheck` block
- **Order**: Critical order for startup:
  1. `mysql` - Database layer (verified with `mysqladmin ping`)
  2. `keycloak` - Auth service (depends on mysql, verified with HTTP health endpoint `/health/ready`)
  3. `keycloak-init` - Post-startup configuration (depends on keycloak being healthy)
  4. Other services (`mongodb`, `redis`, `rabbitmq`) - Can start independently

### Keycloak Configuration in Docker
- **Environment Variables**: Use `KC_` prefix for Keycloak configuration
- **Important**: `KC_HEALTH_ENABLED: "true"` is required for healthcheck to function
- **Admin Setup**: Use `KC_BOOTSTRAP_ADMIN_USERNAME` and `KC_BOOTSTRAP_ADMIN_PASSWORD` for automatic admin creation
- **Mode**: Use `start-dev` command for development with MySQL backend to allow realm import
- **Realm Import**: Place realm JSON files in `scripts/keycloak/` directory and mount as `/opt/keycloak/data/import`

### Health Check Best Practices
- **MySQL**: `test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]`
- **Keycloak**: Custom CMD-SHELL to verify HTTP 200 response from `/health/ready`
- **Parameters**:
  - `interval: 10s` - Check every 10 seconds
  - `timeout: 5s` - Wait 5 seconds for response
  - `retries: 5` - Retry 5 times before marking unhealthy
  - `start_period: 20-30s` - Grace period before first check (database needs more time)

### Recommended Docker Compose Cleanup
```bash
# Complete cleanup (removes volumes)
docker-compose down -v

# Then restart
docker-compose up -d

# Monitor services
docker-compose logs -f keycloak
```
