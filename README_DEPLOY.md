# Despliegue en AWS Elastic Beanstalk + PostgreSQL RDS

Este proyecto es una API Spring Boot (WebFlux) preparada para desplegarse en **AWS Elastic Beanstalk** usando una base de datos **PostgreSQL en Amazon RDS**.

## Variables de entorno requeridas

Configúralas en Elastic Beanstalk en:
**Environment → Configuration → Software → Environment properties**

- `JWT_SECRET`: secreto para firmar JWT.
- `POSTGRES_HOST`: endpoint/host de RDS (ej. `gym-db.xxxxxx.us-east-1.rds.amazonaws.com`).
- `POSTGRES_PORT`: puerto (normalmente `5432`).
- `POSTGRES_DB`: nombre de la base de datos (ej. `gymdb`).
- `POSTGRES_USER`: usuario de la base de datos.
- `POSTGRES_PASSWORD`: contraseña del usuario.

> El puerto HTTP lo toma de `PORT` (por defecto `8080`). En Elastic Beanstalk se fuerza a `5000` vía `.ebextensions/java.config`.

## Crear PostgreSQL en Amazon RDS (paso a paso)

1. En AWS Console ve a **RDS → Create database**.
2. **Engine**: PostgreSQL (15/16).
3. **Templates**: Free tier (si aplica) o Dev/Test.
4. Define:
   - **DB instance identifier**: (ej. `gym-db`)
   - **Master username / password**
   - **Initial database name**: (ej. `gymdb`)
5. Crea la instancia y espera estado **Available**.
6. Anota el **Endpoint** (host) y el **Port** (5432).

### Seguridad de red (RDS)

- Asegura que el **Security Group** de RDS permita **entrada en 5432** desde el origen que corresponda.
  - Para pruebas: tu IP.
  - Para producción: el Security Group de tu entorno de Elastic Beanstalk (recomendado).

## Crear tablas

Conéctate a tu RDS y ejecuta el SQL de:
`src/main/resources/schema-postgres.sql`

Puedes usar `psql`, DBeaver u otra herramienta.

## Desplegar en Elastic Beanstalk

### Opción A: Subir ZIP desde consola (simple)

1. Ve a **Elastic Beanstalk → Create application**.
2. Plataforma: **Java (Corretto 17 / Java 17)**.
3. Sube un **ZIP** del proyecto.
4. En el entorno, configura las variables de entorno (tabla anterior).

### Opción B: Construir con Maven y desplegar (recomendado)

En tu máquina local:

```bash
./mvnw clean package -DskipTests
```

Luego despliega el artefacto generado en `target/*.jar` con tu estrategia preferida (ZIP/JAR, CodePipeline, etc.).

## Puerto 5000 en Elastic Beanstalk

Este repo incluye `.ebextensions/java.config` con:

- JVM Options: `-Dserver.port=5000`

Así Elastic Beanstalk enruta correctamente las peticiones al puerto esperado.

## Dockerfile (opcional)

Este repo incluye un `Dockerfile` listo para producción (Java 17 Alpine) exponiendo el puerto **5000**.

