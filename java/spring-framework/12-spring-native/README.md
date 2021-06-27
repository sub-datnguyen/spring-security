# Spring Native exercise
## Common commands in the project
### Build project
`mvn clean install -DskipTests`
### Build image (after adding spring-native)
`mvn clean install -DskipTests -Pnative`
## Exercise
1. Integrate Spring native into project to build a docker image for the application.
- Configurate building a docker image when the spring profile `native` is active.