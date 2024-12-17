# Pistas deportivas #

## Creacion de Stack con Docker Compose .env y init.db ##

Creamos una carpeta que se llame stack cullo esquema es 
    stack
    |__scripts
    |   |__ init.db
    |__.env
    |__docker-compose.yml

**Docker compose**
```
version: '3.1'

services:

  adminer:
    image: adminer
    restart: "no"
    ports:
      - ${ADMINER_PORT}:8080

  db:
    image: mysql:latest
    restart: "no"
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    ports:
      - ${MYSQL_PORT}:3306
    volumes:
      - ./scripts:/docker-entrypoint-initdb.d
  ```
**.env**
```
MYSQL_ROOT_PASSWORD=vivaPealDeBecerro
MYSQL_USERNAME=root
MYSQL_PORT=33306
MYSQL_HOST=localhost
MYSQL_DATABASE=deporte
ADMINER_PORT=8181
SERVICE_PORT=8080
  ```
**init.db**

```CREATE DATABASE IF NOT EXISTS deporte;```
