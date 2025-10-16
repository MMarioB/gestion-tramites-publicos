# 🏛️ Sistema de Gestión de Trámites Públicos

Sistema de microservicios para la gestión de trámites en administración pública, construido con Spring Boot, MongoDB, Kafka y Kubernetes.

## 📋 Tabla de Contenidos

- [Arquitectura](#arquitectura)
- [Microservicios](#microservicios)
- [Stack Tecnológico](#stack-tecnológico)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Uso](#uso)
- [Endpoints API](#endpoints-api)
- [Testing](#testing)
- [Deployment](#deployment)

## 🏗️ Arquitectura
```
┌─────────────┐
│   Cliente   │
└──────┬──────┘
       │
┌──────▼──────────────────────────────────────┐
│           API Gateway (TODO)                │
└──────┬──────────────────────────────────────┘
       │
       ├──────► tramites-service (MongoDB)
       ├──────► usuarios-service (PostgreSQL) [TODO]
       ├──────► notificaciones-service (Kafka) [TODO]
       └──────► documentos-service (MongoDB GridFS) [TODO]
```

## 🔧 Microservicios

### ✅ tramites-service
**Puerto:** 8081  
**Base de datos:** MongoDB  
**Estado:** ✅ Completado

Gestión completa de trámites con estados, validaciones y auditoría.

**Funcionalidades:**
- CRUD completo de trámites
- Gestión de estados (BORRADOR → ENVIADO → EN_REVISION → APROBADO/RECHAZADO)
- Filtrado por estado y solicitante
- Auditoría de cambios (fechas, usuarios)
- Validaciones de entrada
- Manejo de excepciones centralizado

### 🚧 usuarios-service [TODO]
**Puerto:** 8082  
**Base de datos:** PostgreSQL

Gestión de usuarios (ciudadanos y funcionarios).

### 🚧 notificaciones-service [TODO]
**Puerto:** 8083  
**Base de datos:** MongoDB

Sistema de notificaciones basado en eventos Kafka.

### 🚧 documentos-service [TODO]
**Puerto:** 8084  
**Base de datos:** MongoDB GridFS

Gestión y almacenamiento de documentos adjuntos.

### 🚧 api-gateway [TODO]
**Puerto:** 8080

Gateway de entrada al sistema con enrutamiento y seguridad.

## 🛠️ Stack Tecnológico

### Backend
- **Java 17**
- **Spring Boot 3.4.0**
- **Spring Data MongoDB**
- **Spring Data JPA** (para PostgreSQL)
- **Lombok**
- **Validation API**

### Bases de Datos
- **MongoDB 7.0** - Base de datos NoSQL
- **PostgreSQL 16** [TODO] - Base de datos relacional

### Mensajería
- **Apache Kafka** [TODO] - Sistema de eventos

### Seguridad
- **Keycloak** [TODO] - OAuth2/JWT
- **Spring Security** [TODO]

### Documentación
- **Swagger/OpenAPI 3** - Documentación interactiva de APIs

### DevOps
- **Docker & Docker Compose** - Contenedorización
- **Kubernetes** [TODO] - Orquestación
- **Helm** [TODO] - Gestión de despliegues K8s
- **GitHub Actions** [TODO] - CI/CD

### Monitorización
- **Spring Boot Actuator** - Health checks
- **Prometheus** [TODO] - Métricas
- **Grafana** [TODO] - Dashboards

## 📋 Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **Docker & Docker Compose**
- **Git**

### Verificar instalación:
```bash
java -version    # Debe mostrar Java 17+
mvn -v          # Maven 3.8+
docker -v       # Docker instalado
docker-compose -v
```

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU-USUARIO/gestion-tramites-publicos.git
cd gestion-tramites-publicos
```

### 2. Levantar infraestructura (MongoDB)
```bash
docker-compose up -d
```

Verificar que MongoDB está corriendo:
```bash
docker ps
# Deberías ver: mongodb-tramites
```

### 3. Compilar y ejecutar tramites-service
```bash
cd tramites-service
./mvnw clean install
./mvnw spring-boot:run
```

El servicio estará disponible en: **http://localhost:8081**

## 📖 Uso

### Swagger UI (Documentación Interactiva)

Accede a la documentación interactiva de la API:

**http://localhost:8081/swagger-ui.html**

Desde aquí puedes:
- Ver todos los endpoints disponibles
- Probar las operaciones directamente
- Ver los modelos de datos
- Consultar respuestas de ejemplo

### Health Check

Verifica que el servicio está funcionando:
```bash
curl http://localhost:8081/actuator/health
```

Respuesta esperada:
```json
{
  "status": "UP"
}
```

## 🌐 Endpoints API

### tramites-service (http://localhost:8081)

| Método | Endpoint                                        | Descripción                |
|--------|-------------------------------------------------|----------------------------|
| POST   | `/api/tramites`                                 | Crear nuevo trámite        |
| GET    | `/api/tramites`                                 | Listar todos los trámites  |
| GET    | `/api/tramites?estado=ENVIADO`                  | Filtrar por estado         |
| GET    | `/api/tramites/{id}`                            | Obtener trámite por ID     |
| GET    | `/api/tramites/solicitante/{id}`                | Trámites de un solicitante |
| PUT    | `/api/tramites/{id}`                            | Actualizar trámite         |
| PATCH  | `/api/tramites/{id}/estado?nuevoEstado=ENVIADO` | Cambiar estado             |
| DELETE | `/api/tramites/{id}`                            | Eliminar trámite           |

### Ejemplos de uso con curl

**Crear trámite:**
```bash
curl -X POST http://localhost:8081/api/tramites \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Solicitud de licencia de obras",
    "descripcion": "Construcción de terraza",
    "solicitanteId": "usuario-001"
  }'
```

**Listar trámites:**
```bash
curl http://localhost:8081/api/tramites
```

**Cambiar estado:**
```bash
curl -X PATCH "http://localhost:8081/api/tramites/{ID}/estado?nuevoEstado=ENVIADO"
```

## 🧪 Testing
```bash
cd tramites-service
./mvnw test
```

## 🐳 Docker

### Levantar solo la infraestructura:
```bash
docker-compose up -d
```

### Ver logs:
```bash
docker-compose logs -f mongodb-tramites
```

### Parar servicios:
```bash
docker-compose down
```

### Eliminar volúmenes (¡cuidado, borra los datos!):
```bash
docker-compose down -v
```

## 📦 Estructura del Proyecto
```
gestion-tramites-publicos/
├── tramites-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/gestiontramites/tramites/
│   │   │   │   ├── controller/     # Controllers REST
│   │   │   │   ├── service/        # Lógica de negocio
│   │   │   │   ├── repository/     # Acceso a datos
│   │   │   │   ├── model/          # Entidades
│   │   │   │   ├── dto/            # DTOs
│   │   │   │   └── exception/      # Manejo de excepciones
│   │   │   └── resources/
│   │   │       └── application.yml # Configuración
│   │   └── test/
│   └── pom.xml
├── usuarios-service/ [TODO]
├── notificaciones-service/ [TODO]
├── documentos-service/ [TODO]
├── api-gateway/ [TODO]
├── infrastructure/
│   ├── docker/
│   └── kubernetes/ [TODO]
├── docker-compose.yml
└── README.md
```

## 🎯 Roadmap

### Fase 1: Microservicios Base ✅
- [x] tramites-service con MongoDB
- [x] CRUD completo
- [x] Validaciones y excepciones
- [x] Swagger/OpenAPI
- [ ] usuarios-service con PostgreSQL
- [ ] documentos-service

### Fase 2: Integración 🚧
- [ ] Apache Kafka para eventos
- [ ] notificaciones-service
- [ ] Comunicación entre microservicios

### Fase 3: Seguridad 🔜
- [ ] Keycloak (OAuth2/JWT)
- [ ] Spring Security
- [ ] Autorización basada en roles

### Fase 4: Gateway y Orquestación 🔜
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Kubernetes deployment
- [ ] Helm charts
- [ ] Auto-escalado (HPA)

### Fase 5: CI/CD y Monitorización 🔜
- [ ] GitHub Actions
- [ ] Prometheus + Grafana
- [ ] Logging centralizado
- [ ] Tests de integración

## 👨‍💻 Desarrollo

### Crear nueva feature:
```bash
git checkout develop
git pull origin develop
git checkout -b feature/nombre-feature
# ... desarrollar ...
git add .
git commit -m "feat: descripción"
git push origin feature/nombre-feature
# Crear Pull Request a develop
```

### Workflow de ramas:
```
main (producción)
  ↑
develop (integración)
  ↑
feature/* (desarrollo)
```

## 📄 Licencia

MIT

## 👤 Autor

Mario - [GitHub](https://github.com/MMarioB)

---

⭐ Si te ha gustado el proyecto, dale una estrella en GitHub