# Original documentation
https://spring.io/guides/gs/reactive-rest-service/


# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
- http://localhost:8080/hello
    -> Hello, Spring!
- http://localhost:8080/account
    
    
# H2
The console is available at http://localhost:8082/ thanks to the class com.sample.db.H2.
Log into it with sa / password, specifying URL JDBC = jdbc:h2:mem:testdb
At startup, to verify the preloaded data: select * from account
    
   
# TODO
- Asynch endpoint to stream data from DB: maybe using https://www.baeldung.com/spring-data-java-8#completablefuture?
