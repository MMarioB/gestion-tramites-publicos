# Usuarios Service

Microservicio para la gestión de usuarios del sistema (ciudadanos y funcionarios).

## 🚀 Ejecución Local

### Con Maven:
```bash
./mvnw spring-boot:run
```

### Con Docker:
```bash
# Construir imagen
docker build -t usuarios-service:latest .

# Ejecutar contenedor
docker run -d \
  --name usuarios-service \
  --network gestion-tramites-publicos_tramites-network \
  -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-usuarios:5432/usuarios_db \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  usuarios-service:latest
```

## 📝 Configuración

Variables de entorno disponibles:

| Variable                     | Descripción            | Default                                      |
|------------------------------|------------------------|----------------------------------------------|
| `SPRING_DATASOURCE_URL`      | URL de PostgreSQL      | jdbc:postgresql://localhost:5432/usuarios_db |
| `SPRING_DATASOURCE_USERNAME` | Usuario de PostgreSQL  | postgres                                     |
| `SPRING_DATASOURCE_PASSWORD` | Password de PostgreSQL | postgres                                     |
| `SERVER_PORT`                | Puerto del servicio    | 8082                                         |

## 🌐 Endpoints

Ver documentación completa en: http://localhost:8082/swagger-ui.html

### Principales endpoints:

- **POST** `/api/usuarios` - Crear usuario
- **GET** `/api/usuarios` - Listar todos
- **GET** `/api/usuarios/{id}` - Obtener por ID
- **GET** `/api/usuarios/dni/{dni}` - Obtener por DNI
- **GET** `/api/usuarios/buscar?q=texto` - Buscar
- **PUT** `/api/usuarios/{id}` - Actualizar
- **PATCH** `/api/usuarios/{id}/activar` - Activar
- **PATCH** `/api/usuarios/{id}/desactivar` - Desactivar
- **DELETE** `/api/usuarios/{id}` - Eliminar

## 🏗️ Arquitectura
```
Controller → Service → Repository → PostgreSQL
```

### Modelo de datos:

- **Usuario** (tabla principal)
    - ID (Long, auto-increment)
    - DNI (String, único)
    - Nombre, Apellidos
    - Email (único)
    - Teléfono
    - Tipo (CIUDADANO | FUNCIONARIO)
    - Activo (Boolean)
    - Fechas de auditoría

- **Dirección** (relación OneToOne)
    - ID (Long, auto-increment)
    - Calle, Número, Piso, Puerta
    - Código Postal, Ciudad, Provincia, País

## 📦 Dependencias

- Spring Boot 3.4.0
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- Swagger/OpenAPI
- Spring Boot Actuator

## 🧪 Ejemplo de uso

### Crear usuario:
```bash
curl -X POST http://localhost:8082/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "12345678A",
    "nombre": "Mario",
    "apellidos": "García López",
    "email": "mario@example.com",
    "telefono": "612345678",
    "tipo": "CIUDADANO",
    "direccion": {
      "calle": "Gran Vía",
      "numero": "28",
      "codigoPostal": "28013",
      "ciudad": "Madrid",
      "provincia": "Madrid",
      "pais": "España"
    }
  }'
```

### Listar usuarios activos:
```bash
curl http://localhost:8082/api/usuarios?activo=true
```

### Buscar usuario:
```bash
curl http://localhost:8082/api/usuarios/buscar?q=Mario
```