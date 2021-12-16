How to start the application:
1. Open the application in IntelliJ
2. Use `docker-compose.yml` to deploy activemq and iam service (install docker for this step if you don't have it)
3. To build the application, use the following maven command: `mvn clean install -Dmaven.wagon.http.ssl.insecure=true -Dskip.npm=true -DskipTests`
    - For missing library, copy the content in `/etc/esb-client` into your maven repository
4. To run the application, create a Spring boot run configuration:
    - Main class: `vn.elca.training.Application`
    - Use classpath of module: `spring-boot-dependencies-training-web`
    - VM Options: `-Dspring.config.location=classpath:/,classpath:/serviceConfig/`

Problem:
- The application is implemented with ActiveMQ and Mysql database. Atomikos is configured to handle distributed transaction. 
- Send a GET request to `localhost:8080/front/service/basicdata/createEngins/case1.xml`
  to make the application read a list of `Engin` item from a xml file and sends the data to JMS queue.
- Queue listener `EnginQueueListener` will handle the message and call `IEnginService` to save all `Engin` to the database.
- Halfway through the data persisting, an exception occurs, but the transaction does not rollback. Half of the `Engin`list are actually 
  saved into the database. (You can access the database with username/password `dev/dev` )

Question:
1. Can you explain why the transaction rollback does not work ?
2. (Advance) How can you fix this problem ?