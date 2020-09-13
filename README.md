# POC on Reactive APIs
    - Accessing data with R2DBC was put together using https://spring.io/guides/gs/accessing-data-r2dbc/
    - Full solution inspired from https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux


# Reactive H2
- Liquibase does NOT work yet with R2DBC. See open ticket https://liquibase.jira.com/browse/CORE-3419
- The database is primed at startup thanks to SampleDataInitializer.


# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
- curl -k -v -H "Content-Type: application/json" -d '{"email":"zz@email.com", "password":"pwd1", "role":"Developer"}' -X POST http://localhost:8080/profiles
    - 201

   
# TODO
- write a test for CustomerService similar to ProfileServiceTest at https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
- add classic endpoints as with ProfileRestController at https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
- test endpoints: see AbstractBaseProfileEndpoints, etc. at https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
- Retry & asynch endpoint: how to implement. For instance if the findAll in AccountHandler fails, how to handle it?
- Read:
        - https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
        - https://netflixtechblog.com/reactive-programming-in-the-netflix-api-with-rxjava-7811c3a1496a
        - https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
        - Apply back pressure and test it.    
- Asynch endpoint to stream data from Azure or similar (Blob: see email). 
