# Faculty Secretary Appointment Microservices

This project provides the backend for a faculty secretary appointment application

## Architecture

This project follows a microservices architecture, with each service responsible for a specific domain. The key components include:

- **User Service**: Manages user registration, authentication, roles.
- **Appointment Service**: Handles appointment creation, cancellation.
- **Schedule service**: Allows each secratary to provide their own available schedule.

Each service communicates with others using RESTful APIs and RabbitMQ. Each microservice has its own database.

Current to dos:
- ** add an endpoint in Appointment Service to provide the current availabnle time slots
- ** deploy application
