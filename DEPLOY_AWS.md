# Desplegar la API en AWS (paso a paso)

Esta guía usa **AWS Elastic Beanstalk** (para la API Java) y **Amazon RDS (PostgreSQL)** (para la base de datos).

---

## Requisitos

- Cuenta de AWS
- AWS CLI instalado y configurado (`aws configure`)
- Repositorio en GitHub (este proyecto)

---

## Parte 1: Base de datos PostgreSQL en RDS

### 1.1 Crear la base de datos

1. Entra en la consola de AWS: **RDS** → **Create database**.
2. **Engine**: PostgreSQL (versión 15 o 16).
3. **Templates**: Free tier (o Dev/Test si prefieres).
4. **Configuración**:
   - **DB instance identifier**: `gym-db`
   - **Master username**: `postgresadmin` (o el que quieras)
   - **Master password**: una contraseña segura (guárdala)
5. **Instance configuration**: por ejemplo `db.t3.micro` (free tier).
6. **Storage**: dejar por defecto.
7. **Connectivity**:
   - **VPC**: default (o la tuya).
   - **Public access**: Yes (para poder conectar desde Beanstalk o tu PC).
   - **VPC security group**: crear nuevo (ej. `rds-gym-sg`).
8. **Database name**: `gymdb`.
9. Clic en **Create database** y espera a que esté **Available**.

### 1.2 Permitir tráfico a PostgreSQL

1. Ve a **EC2** → **Security Groups**.
2. Abre el security group que usa tu instancia RDS (ej. `rds-gym-sg`).
3. **Inbound rules** → **Edit inbound rules** → **Add rule**:
   - Type: **PostgreSQL**
   - Port: **5432**
   - Source: **Anywhere-IPv4** (0.0.0.0/0) para pruebas, o el security group de Elastic Beanstalk en producción.
4. Guarda.

### 1.3 Anotar el endpoint de RDS

En **RDS** → **Databases** → tu base de datos:

- **Endpoint**: algo como `gym-db.xxxxxx.us-east-1.rds.amazonaws.com`
- **Port**: 5432  
- **Master username** y **password** (los que definiste).

---

## Parte 2: Aplicación en Elastic Beanstalk

### 2.1 Crear la aplicación

1. **Elastic Beanstalk** → **Create Application**.
2. **Application name**: `gym-api`.
3. **Platform**: **Java**.
4. **Platform branch**: **Corretto 17** (o Java 17).
5. **Application code**: **Upload your code**.
6. **Version label**: `v1`.
7. Sube un **ZIP** del proyecto (solo código fuente + `pom.xml`, sin `target/`).  
   O en **Source**: elige **Public sample application** para probar; luego cambiarás a GitHub o ZIP.

Si prefieres **conectar GitHub** (recomendado para despliegues automáticos):

- En **Source** elige **GitHub** y autoriza AWS.
- Repositorio: `estebanbonilla22/gym`, rama `main`.
- AWS construirá el JAR con Maven y desplegará.

### 2.2 Configurar variables de entorno

1. En tu **Environment** de Elastic Beanstalk: **Configuration** → **Software** → **Edit**.
2. En **Environment properties** añade:

| Nombre             | Valor                                                                 |
|--------------------|-----------------------------------------------------------------------|
| `PORT`             | `5000`                                                                |
| `POSTGRES_HOST`    | endpoint de RDS (ej. `gym-db.xxxxxx.us-east-1.rds.amazonaws.com`)    |
| `POSTGRES_PORT`    | `5432`                                                                |
| `POSTGRES_DB`      | `gymdb`                                                               |
| `POSTGRES_USER`    | usuario maestro de RDS (ej. `postgresadmin`)                          |
| `POSTGRES_PASSWORD`| contraseña maestro de RDS                                             |
| `POSTGRES_SSL_MODE`| `require` (RDS soporta SSL)                                           |
| `JWT_SECRET`       | una cadena larga y segura                                             |

3. **Apply**.

### 2.3 Puerto en Elastic Beanstalk

En entornos Java, Elastic Beanstalk suele usar el puerto **5000** para el proxy. Por eso se pone `PORT=5000` en las variables. La app ya usa `server.port=${PORT:8080}`, así que escuchará en 5000.

---

## Parte 3: Crear tablas en la base de datos

1. Conéctate a RDS desde tu PC (o desde un bastion) con un cliente PostgreSQL (DBeaver, psql, etc.):
   - Host: endpoint de RDS
   - Port: 5432
   - Database: gymdb
   - User / Password: los de RDS
   - SSL: required (según tu cliente)

2. Ejecuta el contenido de `src/main/resources/schema-postgres.sql` para crear las tablas `sedes`, `users` y `maquinas`.

---

## Parte 4: Probar la API

1. En Elastic Beanstalk, abre la **URL** del environment (ej. `gym-api.us-east-1.elasticbeanstalk.com`).
2. Prueba:
   - `https://tu-url.elasticbeanstalk.com/` o `.../health` → debe devolver JSON con `"status":"ok"`.
   - `POST .../auth/register` y `POST .../auth/login` con Postman o similar.

---

## Resumen de variables de entorno (AWS)

La API espera estas variables (todas opcionales si usas valores por defecto en local):

- `PORT` – puerto HTTP (en EB suele ser 5000).
- `POSTGRES_HOST` – endpoint RDS.
- `POSTGRES_PORT` – 5432.
- `POSTGRES_DB` – gymdb.
- `POSTGRES_USER` – usuario RDS.
- `POSTGRES_PASSWORD` – contraseña RDS.
- `POSTGRES_SSL_MODE` – `require` en RDS.
- `JWT_SECRET` – clave para los tokens JWT.

---

## Despliegue desde GitHub (opcional)

1. En **Elastic Beanstalk** → **Application** → **Environments** → tu environment.
2. **Upload and deploy** o configura **CodePipeline** con origen GitHub.
3. Cada push a `main` puede disparar un build (Maven) y despliegue automático si lo enlazas en Pipeline.

Si usas **ZIP** manual: genera el JAR con `./mvnw clean package -DskipTests` y sube el JAR o el ZIP del proyecto; EB puede construir desde el ZIP con Maven.
