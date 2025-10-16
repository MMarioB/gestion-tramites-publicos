# Tramites Service

Microservicio para la gestión de trámites públicos.

## 🚀 Ejecución Local

### Con Maven:
```bash
./mvnw spring-boot:run
```

### Con Docker:
```bash
# Construir imagen
docker build -t tramites-service:latest .

# Ejecutar contenedor
docker run -d \
  --name tramites-service \
  --network gestion-tramites-publicos_tramites-network \
  -p 8081:8081 \
  -e SPRING_DATA_MONGODB_HOST=mongodb-tramites \
  tramites-service:latest
```

## 📝 Configuración

Variables de entorno disponibles:

| Variable                       | Descripción         | Default     |
|--------------------------------|---------------------|-------------|
| `SPRING_DATA_MONGODB_HOST`     | Host de MongoDB     | localhost   |
| `SPRING_DATA_MONGODB_PORT`     | Puerto de MongoDB   | 27017       |
| `SPRING_DATA_MONGODB_DATABASE` | Nombre de la BD     | tramites_db |
| `SERVER_PORT`                  | Puerto del servicio | 8081        |

## 🌐 Endpoints

Ver documentación completa en: http://localhost:8081/swagger-ui.html

## 🏗️ Arquitectura
```
Controller → Service → Repository → MongoDB
```

## 📦 Dependencias

- Spring Boot 3.4.0
- Spring Data MongoDB
- Lombok
- Swagger/OpenAPI
- Spring Boot Actuator