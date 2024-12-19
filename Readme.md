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
**init.sql**

```CREATE DATABASE IF NOT EXISTS deporte;```


## Modelos ##

A continuacion crearemos los modelos de la base de datos de nuestras instalaciones deportivas 

### **Horario** ###

id que seria la clave primaria 

``` 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

una relacion de muchos a uno con instalaciones ya que hay varias instalaciones donde se puede reservar como puede ser un pista de futbol o una cancha de baloncesto 

```
    @ManyToOne
    private Instalacion instalacion;
 ```

y dos campos que son LocalTime que seria la hora de inicio y la hora de fin

```
    private LocalTime horaInicio;    
    private LocalTime horaFin;
 ```
### **Instalacion** ###

id que seria la clave primaria 

``` 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

y el nombre de la instalacion que no podria ser nulo 

``` 
    @Column(nullable = false, length = 80)
    private String nombre;
```

### **Reserva** ###

id que seria la clave primaria 

``` 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

una relacion de muchos a uno de usuarios ya que varios usuarios pueden hacer reservas

```
    @ManyToOne
    private Usuario usuario;
```

una relacion de muchos horarios 

```
    @ManyToOne
    private Horario horario;
```
y un parametro que es la fecha de la reserva 

```
    private LocalDate fecha;
```

### **Rol** ###

Que es una clase enum que diferencia a los Usuarios de los Administradores y de los Operarios

```
    public enum Rol {
      ADMIN, OPERARIO, USUARIO
    }
```

### **Usuario** ###

id que seria la clave primaria 

``` 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

Un nomrbe que no puede ser nulo que debe ser unico y que su maximo de caracteres es de 20

```
    @Column(nullable = false, unique = true,length = 20)
    private String username;
```

Una contraseña que no puede ser nula y su maximo de caracteres es de 80

```
    @Column(nullable = false, length = 80)
    private String password; 
```

Un email que no puede ser nulo, es unico y su maximo de caracteres es de 80

```
    @Column(nullable = false, unique = true, length = 80)
    private String email; 
```

Y el llamamiento a el enum **Rol** que nos da el tipo de Usuario ques es osea:

- Administrador
- Operario
- Usuario

```
    @Enumerated(EnumType.STRING)
    private Rol tipo;
```

## Repositorios ##
