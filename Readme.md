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
La repos son unas interfaces que lo que hacen es generar metodos genericos para el manejo de los CRUD de los objetos creados anteriormente 

### **RepoHorario** ###

Jpa implementa los metodos predefinidos con los que se trabajan en SpringBoot 

```JpaRepository<Horario, Long>```

a)
```
Page<Horario> findByInstalacion(Instalacion instalacion,Pageable pageable);
```

```Instalacion instalacion```

Filtra los horarios que pertenecen a una instalación específica.

```Pageable pageable```

Permite especificar detalles de paginación (número de página, tamaño de la página, orden)

b)

    List<Horario> findByInstalacion(Instalacion instalacion);

Devuelve una lista con los horarios que coinciden con la instalacion

### **RepoInstalacion** ###

Jpa implementa los metodos predefinidos con los que se trabajan en SpringBoot 

```JpaRepository<Instalacion, Long>```

### **RepoReserva** ###

Jpa implementa los metodos predefinidos con los que se trabajan en SpringBoot 

```JpaRepository<Reserva, Long>```
### **RepoUsuario** ###


Jpa implementa los metodos predefinidos con los que se trabajan en SpringBoot 

```JpaRepository<Usuario, Long>```

## Controladores ##
El controlador es responsable de manejar las solicitudes HTTP entrantes y enviar respuestas. Por lo general, recibe datos del usuario a través de la solicitud, delega en un servicio para realizar la lógica de negocio y luego envía una respuesta al usuario.

### **ControDatos** ###
```@Controller``` Indica que es un controlador de Spring

```@RequestMapping ``` Define la URL base para las rutas gestionadas por este controlador

```
@Autowired
RepoUsuario repoUsuario;
```
Es un repositorio que permite realizar operaciones de base de datos relacionadas con la entidad Usuario

```
private Usuario getLoggedUser() {
    Authentication authentication = 
        SecurityContextHolder.getContext().getAuthentication();
    return repoUsuario.findByUsername(
        authentication.getName()).get(0);
}
```
Obtiene el usuario actualmente autenticado mediante el contexto de seguridad de Spring Security.

Busca al usuario en la base de datos utilizando el nombre de usuario.

Devuelve el primer usuario encontrado.

```
@GetMapping("")
public String misDatos(Model modelo) {
    modelo.addAttribute("usuario", getLoggedUser());
    return "mis-datos/mis-datos";
}
```
Añade el usuario autrntificado en la lista
```
@GetMapping("/edit")
public String getMisDatos(Model modelo) {
    modelo.addAttribute("usuario", getLoggedUser());
    return "mis-datos/mis-datos";
}
```
Carga los datos enviados del usuario autenticado y los envia al modelo
```
@PostMapping("/edit")
public String postMisDatos(
    @ModelAttribute("usuario") Usuario u,
    Model modelo) {
    Usuario loggedUser = getLoggedUser();
    if (loggedUser.getId() == u.getId()) {
        u.setTipo(loggedUser.getTipo());
        u.setPassword(loggedUser.getPassword());
        u.setEnabled(loggedUser.isEnabled());
        repoUsuario.save(u);
        return "redirect:/mis-datos";
    } else {
        modelo.addAttribute("mensaje", "Error editando datos de usuario");
        modelo.addAttribute("titulo", "No ha sido posible editar sus datos.");
        return "/error";
    }
}
```
Esto evita que un usuario pueda modificar indebidamente atributos sensibles o de otros usuarios.
### **ControHorario** ###
```@Controller``` Indica que es un controlador de Spring

```@RequestMapping ``` Define la URL base para las rutas gestionadas por este controlador
```
@Autowired 
RepoHorario repoHorario;

@Autowired
RepoInstalacion repoInstalacion;
```
RepoHorario: Interactúa con la base de datos para realizar operaciones sobre la entidad Horario.

RepoInstalacion: Gestiona datos de la entidad Instalacion.

```
@GetMapping("")
public String getHorarios(
    Model model,
    @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    
    Page<Horario> page = repoHorario.findAll(pageable);
    model.addAttribute("page", page);
    model.addAttribute("horarios", page.getContent());
    model.addAttribute("instalaciones", repoInstalacion.findAll());
    return "horarios/horarios";
}

```
Recupera todos los horarios en forma paginada mediante repoHorario.findAll(pageable) y devuelve la vista horarios/horarios

```
@GetMapping("/instalacion/{id}")
public String getHorariosByInstalacion(
    @PathVariable @NonNull Long id,
    Model model,
    @PageableDefault(size = 10, sort = "id") Pageable pageable) {

    Optional<Instalacion> insOptional = repoInstalacion.findById(id);

    if (insOptional.isPresent()) {
        Page<Horario> page = repoHorario.findByInstalacion(insOptional.get(), pageable);
        model.addAttribute("page", page);
        model.addAttribute("horarios", page.getContent());
        model.addAttribute("instalaciones", repoInstalacion.findAll());
        model.addAttribute("instalacion", insOptional.get());
        return "horarios/horarios";
    } else {
        model.addAttribute("mensaje", "La instalación no existe");
        model.addAttribute("titulo", "Error filtrando por instalación.");
        return "/error";
    }
}
```
Busca la instalación por ID
```
@GetMapping("/add")
public String addHorario(Model modelo) {
    modelo.addAttribute("horario", new Horario());
    modelo.addAttribute("operacion", "ADD");
    modelo.addAttribute("instalaciones", repoInstalacion.findAll());
    return "/horarios/add";
}
```
Prepara un formulario vacío para crear un nuevo horario.
```
@PostMapping("/add")
public String addHorario(
    @ModelAttribute("horario") Horario horario)  {
    repoHorario.save(horario);
    return "redirect:/horario";
}
```
Procesa el formularrio
```
@GetMapping("/edit/{id}")
public String editHorario( 
    @PathVariable @NonNull Long id,
    Model modelo) {

    Optional<Horario> oHorario = repoHorario.findById(id);
    if (oHorario.isPresent()) {
        modelo.addAttribute("horario", oHorario.get());
        modelo.addAttribute("operacion", "EDIT");
        modelo.addAttribute("instalaciones", repoInstalacion.findAll());
        return "/horarios/add";
    } else {
        modelo.addAttribute("mensaje", "La instalación no exsiste");
        modelo.addAttribute("titulo", "Error editando instalación.");
        return "/error";
    }
}
```
Busca el horario por ID.
```
@PostMapping("/edit/{id}")
public String editHorario(
    @ModelAttribute("horario") Horario horario)  {
    repoHorario.save(horario);
    return "redirect:/horario";
}
```
Actualiza la base de datos
```
@GetMapping("/del/{id}")
public String delHorario( 
    @PathVariable @NonNull Long id,
    Model modelo) {

    Optional<Horario> oHorario = repoHorario.findById(id);
    if (oHorario.isPresent()) {
        modelo.addAttribute("operacion", "DEL");
        modelo.addAttribute("instalaciones", repoInstalacion.findAll());
        modelo.addAttribute("horario", oHorario.get());
        return "/horarios/add";
    } else {
        modelo.addAttribute("mensaje", "La instalación no exsiste");
        modelo.addAttribute("titulo", "Error borrando instalación.");
        return "/error";
    }
}
```
Elimina un horario 
```
@PostMapping("/del/{id}")
public String delHorario(
    @ModelAttribute("horario") Horario horario)  {
    repoHorario.delete(horario);
    return "redirect:/horario";
}
```
Procesa la eliminacion de un horario
### **ControInstalacion** ###

```
@Autowired 
    RepoInstalacion repoInstalacion;
```
Repo instalacion interactua con la base de datos de Instalacion 
```
@GetMapping("")
    public String getInstalaciones(Model model) {
        List<Instalacion> instalaciones = repoInstalacion.findAll();
        model.addAttribute("instalaciones", instalaciones);
        return "instalaciones/instalaciones";
    }
```
Mapea las instalaciones 
```
@GetMapping("/add")
    public String addInstalacion(Model modelo) {
        modelo.addAttribute("instalacion", new Instalacion());
        return "/instalaciones/add";
    }

    @PostMapping("/add")
    public String addInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.save(instalacion);
        return "redirect:/instalacion";
    }
```
Hace una peticion para ver los atributos de una nueva instalacion y hace una peticion post para añadirla
```
@GetMapping("/edit/{id}")
    public String editInstalacion( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            modelo.addAttribute("instalacion", oInstalacion.get());
            return "/instalaciones/add";
        } else {
            modelo.addAttribute("mensaje", "La instalación no exsiste");
            modelo.addAttribute("titulo", "Error editando instalación.");
            return "/error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.save(instalacion);
        return "redirect:/instalacion";
    }
```
Mapea los datos de una instalacion y lo que a continuacion hara es un post de los cambios que hayas realizado en la instalacion
```
@GetMapping("/del/{id}")
    public String delInstalacion( 
        @PathVariable @NonNull Long id,
        Model modelo) {

        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            modelo.addAttribute("borrando", "verdadero");
            modelo.addAttribute("instalacion", oInstalacion.get());
            return "/instalaciones/add";
        } else {
            modelo.addAttribute("mensaje", "La instalación no exsiste");
            modelo.addAttribute("titulo", "Error borrando instalación.");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String delInstalacion(
        @ModelAttribute("instalacion") Instalacion instalacion)  {
        repoInstalacion.delete(instalacion);
        return "redirect:/instalacion";
    }
```
Muestras los datos de una instalacion por su id y lo que hara a continuacion es una peticion post que eliminara la instalacion
### **ControMain** ###
```
@GetMapping("/")
    public String getIndex() {
        return "index";
    }
```
Buscara el archivo **index.html**
```
@GetMapping("/error")
    public String getError() {
        return "error";
    }
```
Buscara el archivo **error.html** cuando surja un error
```
@GetMapping("/acerca")
    public String acercade() {
        return "acercade";
    }
```
Buscara el archivo **acercade.html**
```
@GetMapping("/login")
    public String loginForm() {
        return "login";
    }
```
Buscara el archivo **login.html**
```
@GetMapping("/denegado")
    public String accesoDenegado() {
        return "denegado";
    }
```
Buscara el archivo **denegado.html**
## Configuracion ##

El archivo **ConfiSec.java** es el que aporta la seguridad y configuracion de nuestra de aplicaicon 