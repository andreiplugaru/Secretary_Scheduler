# Faculty Secretary Appointment Microservices

This project provides the backend for a faculty secretary appointment application

## Architecture

This project follows a microservices architecture, with each service responsible for a specific domain. The key components include:

- **User Service**: Manages user registration, authentication, roles.
- **Appointment Service**: Handles appointment creation, cancellation.
- **Schedule service**: Allows each secratary to provide their own available schedule.
  
Microservices are developed using Quarkus framework.

Each service communicates with others using RESTful APIs and RabbitMQ. Each microservice has its own database.

A docker compose is proivded, so all the services, included the databses server can be started with `docker compose up`. For testing, the swagger interface can be explored at `/q/swagger-ui` for each service.

Current to dos:
- ** deploy application
