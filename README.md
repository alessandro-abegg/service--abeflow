# Abeflow Service

Abeflow is an open-source service designed to generate dynamic pipelines in both programmatic and visual ways. This project allows users to create flexible pipelines without any financial earnings restrictions, promoting accessibility and collaboration in the open-source community.

## Features
- **Dynamic Pipeline Generation**: Create pipelines programmatically or through a visual interface.
- **Open-Source License**: Released under an open-source license that permits use without financial constraints.
- **Extensible Architecture**: Built with modularity in mind to allow easy extensions and customizations.

## Tech Stack
- **Language**: Kotlin
- **Framework**: Spring Boot 4.1.0-M4
- **Build Tool**: Maven
- **Configuration**: YAML-based application properties
- **Database**: MySQL
- **Cache**: Redis
- **Message Queue**: RabbitMQ

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose (for running infrastructure services)

### Installation

#### Option 1: Using Docker Compose (Recommended)
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd service--abeflow
   ```

2. Start the infrastructure services:
   ```bash
   docker-compose up -d
   ```

3. Build and run the application:
   ```bash
   ./mvnw clean compile
   ./mvnw spring-boot:run
   ```

#### Option 2: Manual Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd service--abeflow
   ```

2. Set up MySQL, Redis, and RabbitMQ manually

3. Build the project:
   ```bash
   ./mvnw clean compile
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080` by default.

### Infrastructure Services
The application requires the following services:
- **MySQL**: Database (Port 3306)
- **Redis**: Caching and session storage (Port 6379)
- **RabbitMQ**: Message queuing (Ports 5672, 15672 for management UI)

To start these services using Docker Compose:
```bash
docker-compose up -d
```

To stop the services:
```bash
docker-compose down
```

### Running Tests
```bash
./mvnw test
```

## Project Structure
- `src/main/kotlin/br/com/abegg/abeflow/service/`: Main application code
- `src/main/resources/application.yaml`: Application configuration
- `src/test/kotlin/br/com/abegg/abeflow/service/`: Unit tests

## Contributing
We welcome contributions! Please follow these steps:
1. Fork the repository.
2. Create a feature branch.
3. Make your changes and add tests.
4. Submit a pull request.

Ensure all code follows Kotlin and Spring Boot best practices.

## License
This project is released under an open-source license that allows use without financial earnings. See the LICENSE file for details.

## Contact
For questions or support, please open an issue in the repository.
