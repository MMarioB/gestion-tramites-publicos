# Etapa 1: Build - Compilar la aplicación
FROM maven:3.9-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven primero (mejor cache)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se cachea esta capa si pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar (salta tests para ir más rápido)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime - Imagen final ligera
FROM eclipse-temurin:17-jre

# Metadata
LABEL maintainer="mario@example.com"
LABEL description="Tramites Service - Sistema de Gestión de Trámites"

# Crear usuario no-root para seguridad
RUN groupadd -r spring && useradd -r -g spring spring

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar permisos
RUN chown spring:spring app.jar

# Cambiar a usuario no-root
USER spring:spring

# Puerto que expone la aplicación
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Opciones de JVM (puedes ajustarlas)
CMD ["-Xms256m", "-Xmx512m"]