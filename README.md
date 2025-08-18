# ForoHub API

API REST para gestionar tópicos de un foro (crear, listar, ver detalle, actualizar y eliminar), con **autenticación JWT**, **migraciones con Flyway**, **JPA/Hibernate**, y **documentación con Swagger**.

- **Stack:** Java 21+, Spring Boot 3, Spring Web, Spring Data JPA, Spring Security, Flyway, MySQL version 9.2, springdoc-openapi (Swagger UI), Auth0 Java JWT.
- **Dominio principal:** `Topico` (título, mensaje, fecha de creación, estado, autor y curso).
- **Autenticación:** login con email/contraseña y uso de token **Bearer JWT** para proteger los endpoints.

---

## Tabla de contenidos

- [Arquitectura y dominio](#arquitectura-y-dominio)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Migraciones (Flyway)](#migraciones-flyway)
- [Ejecución](#ejecución)
- [Documentación (Swagger)](#documentación-swagger)
- [Autenticación (JWT)](#autenticación-jwt)
- [Endpoints principales](#endpoints-principales)
- [Manejo de errores](#manejo-de-errores)
- [Estructura de paquetes](#estructura-de-paquetes)
- [Notas y decisiones](#notas-y-decisiones)
- [Roadmap](#roadmap)
- [Licencia](#licencia)

---

## Arquitectura y dominio

Entidades principales:

- **Usuario**: `id`, `nombre`, `email`, `contrasena`
- **Curso**: `id`, `nombre`, `categoria`
- **Topico**: `id`, `titulo`, `mensaje`, `fechaCreacion`, `status` (`ABIERTO|CERRADO|ARCHIVADO`), `autor(Usuario)`, `curso(Curso)`

Relaciones:

- `Topico` **muchos-a-uno** con `Usuario` (autor)
- `Topico` **muchos-a-uno** con `Curso`

Reglas de negocio clave:

- Para **crear**/actualizar un tópico, **todos** los campos relevantes deben ser válidos.
- **No** se permiten duplicados con el mismo **par (título + mensaje)**.
- En actualización (PUT), también valido duplicados (excluyendo el mismo id).

---

## Requisitos

- **Java 17** o superior
- **Maven**
- **MySQL** (usé MySQL 9.x en local; 8.x también funciona)
- Un editor / IDE (IntelliJ recomendado)

---

## Configuración

Variables/propiedades principales (ver `src/main/resources/application.properties`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT
api.security.secret=mi-super-secreto-ultra-largo-para-dev-1234567890

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

CAMINO DE RUTA ("Trello")

CRUD de tópicos con validaciones y duplicados

Autenticación JWT + filtro

Swagger UI con esquema Bearer

Migraciones y semillas mínimas

Endpoints completos de usuarios y respuestas

Tests (unit/integration)

Deploy (Docker + perfiles prod)

server.error.include-stacktrace=never
