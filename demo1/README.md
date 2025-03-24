NUEVO SPA - API de Gestión de Tareas
API RESTful para la gestión de tareas de la empresa NUEVO SPA, desarrollada con Spring Boot 3.4.x, JWT para autenticación y documentada con OpenAPI/Swagger.
Características

Autenticación mediante JWT
Operaciones CRUD para tareas
Documentación de API con OpenAPI/Swagger
Base de datos H2 en memoria
Datos precargados (usuarios y estados de tareas)
Implementación con Java 17 y Spring Boot 3.4.x

Tecnologías utilizadas

Java 17
Spring Boot 3.4.x
Spring Security
Spring Data JPA
JWT (JSON Web Token)
H2 Database
OpenAPI 3.0 / Swagger UI
Maven

Estructura del proyecto
El proyecto sigue una arquitectura en capas y está organizado de la siguiente manera:

Entidades: Representan las tablas de la base de datos (User, Task, TaskStatus).
Repositorios: Interfaces para acceder a datos mediante Spring Data JPA.
DTOs: Objetos para transferencia de datos entre capas.
Servicios: Lógica de negocio de la aplicación.
Controladores: Endpoints REST para la API.
Seguridad: Configuración y componentes para la autenticación JWT.
Excepciones: Manejo centralizado de errores.

Instrucciones de ejecución
Prerrequisitos

JDK 17 o superior
Maven 3.6 o superior

Pasos para ejecutar la aplicación

Clonar el repositorio:
bashCopiargit clone <URL_DEL_REPOSITORIO>
cd taskmanagement

Compilar la aplicación:
bashCopiarmvn clean package

Ejecutar la aplicación:
bashCopiarjava -jar target/taskmanagement-0.0.1-SNAPSHOT.jar
O usar Maven directamente:
bashCopiarmvn spring-boot:run

La aplicación estará disponible en: http://localhost:8080

Acceder a la documentación de la API
Una vez la aplicación esté en ejecución, puedes acceder a:

Documentación Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs

Acceder a la consola H2

URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem
Usuario: sa
Contraseña: (dejar en blanco)

Pruebas con Postman
Se incluye una colección de Postman para probar todas las funcionalidades de la API:

Importar el archivo nuevo_spa_tasks_api.postman_collection.json en Postman.
La colección contiene carpetas organizadas por funcionalidad:

Autenticación: Para obtener el token JWT.
Tareas: Para realizar operaciones CRUD en tareas.



Usuarios precargados
La aplicación viene con tres usuarios precargados para realizar pruebas:
UsuarioContraseñaEmailusuario1password1usuario1@nuevospa.comusuario2password2usuario2@nuevospa.comadminadmin123admin@nuevospa.com
Flujo de trabajo para probar la API

Autenticarse con uno de los usuarios predefinidos para obtener un token JWT.
Utilizar el token JWT en las siguientes operaciones (se incluye automáticamente en la colección de Postman).
Realizar operaciones CRUD en las tareas.

Implementación API First
La API se ha implementado siguiendo el enfoque API First:

Se definió primero el contrato de la API en el archivo openapi.yml.
Se generaron las interfaces de los controladores usando el plugin de Maven de OpenAPI Generator.
Se implementaron los controladores basados en las interfaces generadas.

Principios SOLID aplicados

S (Responsabilidad única): Cada clase tiene una única responsabilidad.
O (Abierto/Cerrado): El diseño permite extensiones sin modificar código existente.
L (Sustitución de Liskov): Las implementaciones pueden sustituir a las interfaces sin afectar el comportamiento.
I (Segregación de interfaces): Interfaces específicas para clientes específicos.
D (Inversión de dependencias): Dependencia en abstracciones, no en implementaciones concretas.

Contribución
Para contribuir a este proyecto:

Hacer un fork del repositorio.
Crear una rama para la nueva funcionalidad o corrección: git checkout -b feature/nueva-funcionalidad
Realizar los cambios y hacer commit: git commit -m 'Agregar nueva funcionalidad'
Hacer push a la rama: git push origin feature/nueva-funcionalidad
Enviar un Pull Request con tus cambios.
