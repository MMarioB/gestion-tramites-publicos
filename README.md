# Sistema de Gestión de Trámites Públicos

Proyecto de microservicios para gestión de trámites en administración pública.

## 🏗️ Arquitectura

- **tramites-service**: Gestión de trámites (Spring Boot + MongoDB)
- **usuarios-service**: Gestión de usuarios (Spring Boot + PostgreSQL)
- **notificaciones-service**: Sistema de notificaciones (Spring Boot + Kafka)
- **documentos-service**: Gestión de documentos (Spring Boot + MongoDB GridFS)
- **api-gateway**: Gateway de API (Spring Cloud Gateway)

## 🛠️ Stack Tecnológico

- Java 17
- Spring Boot 3.x
- MongoDB & PostgreSQL
- Apache Kafka
- Keycloak (OAuth2/JWT)
- Docker & Kubernetes
- Swagger/OpenAPI

## 🚀 Estado del Proyecto

- [x] Setup inicial
- [ ] tramites-service
- [ ] usuarios-service
- [ ] Kafka + notificaciones-service
- [ ] Keycloak + Seguridad
- [ ] API Gateway
- [ ] Kubernetes deployment

## 📝 Documentación

Cada microservicio tiene su propia documentación en su carpeta respectiva.