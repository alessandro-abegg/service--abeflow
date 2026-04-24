# GitHub Copilot Instructions for Abeflow Service

## Project Overview
Abeflow is an open-source service designed to generate dynamic pipelines in both programmatic and visual ways. This project allows users to create flexible pipelines without financial earnings restrictions, promoting accessibility and collaboration in the open-source community. The main idea is to permit the generation of dynamic pipelines programmatically or visually.

## Tech Stack
- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: MongoDB
- **Configuration**: YAML-based application properties
- **Package Structure**: br.com.abegg.abeflow.service

## Key Directories

### Main Source Code (`src/main/kotlin/br/com/abegg/abeflow/service/`)
- `Application.kt`: Spring Boot application entry point
- `config/`: Configuration classes
  - `RabbitMQConfig.kt`: RabbitMQ messaging configuration
  - `RedisConfig.kt`: Redis caching configuration
- `datasources/`: Data source and repository layer (placeholder for external data access)
- `entities/`: Domain entities and models (placeholder for entity definitions)
- `iteractors/`: Business logic and interactor implementations (placeholder for use case implementations)
- `transportlayers/`: HTTP transport layer, controllers, and request/response DTOs (placeholder for REST endpoints)

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
