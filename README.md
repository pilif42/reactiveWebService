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
All H2 items have been commented out as we will instead use r2dbc-h2 because we are interested in a reactive solution.
Search for 'H2_ITEM' to see them all in case you want to reactivate them.

When activated:
    - The console is available at http://localhost:8082/ thanks to the class com.sample.db.H2.
    - Log into it with sa / password, specifying URL JDBC = jdbc:h2:mem:testdb
    - After startup, to verify the preloaded data: select * from account
    
   
# TODO
- org.springframework.dao.InvalidDataAccessApiUsageException: Reactive Repositories are not supported by JPA. 
Offending repository is com.sample.db.repository.ReactiveAccountRepository!

- Solution with reactive H2: https://spring.io/guides/gs/accessing-data-r2dbc/
    - also see https://spring.io/blog/2016/11/28/going-reactive-with-spring-data
    - and maybe https://www.baeldung.com/spring-data-java-8#completablefuture?
    
- Asynch endpoint to stream data from Azure or similar (Blob: see email). 
