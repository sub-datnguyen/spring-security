# Project Backend code base

## Technology versions summary
### JDK
AdoptOpenJDK 11 (latest zipped file downloaded from ELCA server)

### Spring boot and its predefined dependencies
Spring boot version: 2.3.2.RELEASE <br>
Hibernate version: 5.4.18.Final <br>
Hibernate validator: 6.1.5.Final <br>
Log4j2: 2.13.3 / Logback: 1.2.3 <br>
JUnit: 4.13 <br>
Jackson: 2.11.1 <br>
Lombok: 1.18.12 <br>
H2 DB: 1.4.200 <br>
commons-lang3: 3.10

### Database
H2 DB for unit test and web app.

### Database initializing tool
N/A

### Server container
Embebed tomcat in Spring Boot: 9.0.37

### Other libraries
MapStruct: 1.3.1.Final
commons-io: 2.6


## Note when using the code base
1. You need to rename the packages/classes where possible because the naming may not be suitable for your project anymore. Some clues: 
- `vn.elca.codebase` package should be renamed to your project specific package name.
- `*Elca*` classes should be renamed with Project name.
- `*CodeBase*` classes should be renamed with Project name.
2. Some classes are for the demo purpose only to show you how to use the features in the code base such as `Project`, `Task`,... You need to remove these classes when you mastered the code base and want to use it for the project.


## What is supported already in this code base
### Coding style
1. We are using the new DI style: wiring beans in the constructor instead of fields. Since we are using Lombok to reduce boilerplate code so we 
- declare the autowired field as `private final ...`
- add `@RequiredArgsConstructor` in class header of the bean so that the Spring support can bind the beans correctly.  
2. We use Lombok to reduce boilerplate getters/setters and constructors which are supported by Lombok. Less code > less bug.
3. We use MapStruct with support from IDE so that the mapping can be refactored also => reduce mistakes on refactoring.  
4. We configure Spring with JAVA annotation instead of XML files so that refactoring can be supported well.
5. The model was configured in the way that only service can see it. Other modules can't see it => the 

### Common context params for logging
We support the `MDCUtil` class to set and clear thread local scope log context. <br>
We support two common context params: `CorrelationId` and  `Username`. They are defined in `MDCContext`

### Common constants for the whole project
All the common constants which are used by all modules should be put in `CommonConstans` class. <br>
All the Spring profiles used in the project should be put in `SpringProfile` class.
 
### Exception handling
1. We defined already two project specific root exception: `ElcaRTException` for all runtime exception in the project and `ElcaBusinessException` for all Business exceptions in the project.
2. The class `ExceptionHandlingController` is included as global GUI exception handler to handle all the exceptions from web controllers. There we handled some necessary exceptions for you.
3. Package `vn.elca.codebase.common.dto` contains all the DTOs for exception handling and other common DTOs (e.g. `ApplicationContextDto` for application context info to be used by front-end).  

### Generated Qxxx classes for QueryDSL
Currently we set the generated dir of the Qxxx classes to `target/generated-sources/java` so that it won't conflict with the annotation processors of the IntelliJ internal builder. This leads to the problem that each time you change/add an entity you need to run maven again to update the Qxxx classes. This won't happen to MapStruct or Lombok classes because they are supported by IntelliJ (IntelliJ can recoginize them in the classpath scanner configured in `Build, execution, Deployment > Compiler > Annotation processors`)

### Mapping (both web and service) with MapStruct
#### Service
1. Added class `LocalDateAndDateTimeMapper` as a base mapper between JAVA `Date` and `LocalDate`, `LocalDateTime`
2. Added example mapping for `Project` entity, refer `ProjectDecorator`, `ProjectMapper`, `ProjectServiceDto` for information.

#### Web
1. Added class `CommonWebMapperDecorator` for common mapping on web.
2. Added interface `CommonWebMapper` for all the mappers on web.
 
### Serialize/deserialize mechanism with JSON
1. All the customizer classes are defined in package `vn.elca.codebase.common.jackson`, used in `WebCustomObjectMapper` and configured in `ElcaWebConfig#objectMapper()`.
2. Added support for `LocalDate` / `LocalDateTime` of JAVA 8. Also some constants and methods are pre-defined in `ElcaDateUtil` already. Please adapt to match your project expectation.
3. Added support to bind enum (in our customized way).

### REST Endpoint
1. Added class `AbstractElcaServiceEndpoint` for all the endpoints in projects. In this class we define
- Constants for REST service paths
- Root Accessible REST service path for all web endpoints
2. Added `HelloWorldServiceEndpoint` as a demo endpoint for the project. 
3. Added `CommonBusinessEndpoint` as a simple standard endpoint to provide project version info (base href link, version, login user,...). You can add more information to match your need here.
4. To run the application at local to test <br>
![](../../_static/configuration-to-run-locally.jpg)
(1): create a new configuration under Spring Boot node <br>
(2): make sure the main class is `vn.elca.codebase.CodeBaseDevModeWebApplication` <br>
(3): make sure the classpath module is pointed to web module <br>
(4): make sure JRE is correct <br>
(5): check to enable debug output in case you have issue but don't have any clear info to solve.  <br>
(6): make sure the active profile is `vn-dev` <br>
5. Some examples to run with HTTPEditor in IntelliJ to test the endpoint:
Load the application info
```
GET http://localhost:8080/service/common/application-context
Authorization: Basic Y29kZS1iYXNlLW1vY2stdXNlcjo=
```
Result should be like this
```
{
  "loginUser": "code-base-mock-user",
  "version": "<development-local>",
  "baseHref": "/front/"
}
```
Test endpoint HelloWorld
```
GET http://localhost:8080/service/common/sayHello/VLP
Authorization: Basic Y29kZS1iYXNlLW1vY2stdXNlcjo=
```
Result should be like this
```
Hello VLP
```

### DB
To take it easy we use H2 DB for the base code. You can merge the Oracle/MySQL/PostGres/... if you want to start with those DBs.
*NOTE: when you merge one of those branches, the application will run with the corresponding DB system but the unit test will still run with H2 DB.*

### Health check default support (Spring Actuator) 
The dependency to Spring Actuator is added by default so that your project can have the health check support and you can view the mapping tab of the Spring configuration windows in IntelliJ.
![](../../_static/health-check-support-by-IDE.jpg)
In the picture above you can see that the endpoints are clickable. Clicking on it you can open the simple HTTP editor in IntelliJ to test for the REST API.
*NOTE: this editor can only be used to test all the GET requests and POST requests which are not protected with XSRF. In case you want to test XSRF POST requests you need to send a GET first and then use the received token to add in the header of the POST so that the POST can be sent successfully.*  

### Basic Authentication with Spring security
To make it easy I configured an on memory basic authentication for this project. You can change it to match your expectation or merge the feature branches to get the authentication with IAM or JWT...
Default username to authenticate: `code-base-mock-user`. Any password (even blank) is acceptable. Default role: `DUMMY_ROLE`.
The test REST API to make sure the application work: `http://localhost:8080/service/common/sayHello/<any text here>`, e.g. `http://localhost:8080/service/common/sayHello/VLP`
#### Security configuration
1. By default all the requests must be authenticated.
2. CSRF is enabled for all the REST requests which are not from `/interface/*`. This is to separate the security checking between the GUI requests and webservice requests.
   *NOTE: CSRF is disabled for requests to load static resources of the web: css, images, JS*. This is configured in `CodeBaseStaticWebSecurityConfig`
3. Security Session is set to `STATELESS` mode. It means that Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext. *PAY ATTENTION TO THIS POINT in case you want to create a session scope in your project*. This configuration is defined in `CodeBaseApiSecurityConfig#configureAuthorization()` 
#### DESIGN NOTE 
The project is designed in the way that requests to acquire static resources (e.g. images, JS, CSS) go in another way less secure than the REST requests from project controller
Please check the class `CodeBaseStaticWebSecurityConfig`, `CodeBaseApiSecurityConfig` and `CodeBaseDummyBasicAuthenticationFilter` for more information.