# Original documentation
https://spring.io/guides/gs/reactive-rest-service/


# To switch from H2 to Reactive H2
- When using H2:
    - Search for all 'H2_ITEM' and ensure that they are uncommented out.
    - Search for all 'Reactive_H2_ITEM' and ensure that they are commented out.
    - The schema will be created at startup. Some data will be loaded. All items are defined in src/main/resources/db and picked up by Liquibase.
    - The console is available at http://localhost:8082/ thanks to the class com.sample.db.H2.
            - Log into it with sa / password, specifying URL JDBC = jdbc:h2:mem:testdb
            - To verify data has been loaded at startup: select * from account

- When using Reactive H2:
    - Search for all 'Reactive_H2_ITEM' and ensure that they are uncommented out.
    - Search for all 'H2_ITEM' and ensure that they are commented out.
    - Note that Liquibase is commented out because it does NOT work yet with R2DBC:
            - open ticket = https://liquibase.jira.com/browse/CORE-3419
            - The alternative is:
                    - schema.sql in src/main/resources. Spring Boot’s auto-configuration picks it up during application startup to initialize the database schema.
    

# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
- curl -k -v -X GET http://localhost:8080/hello -H "Accept:text/plain"
    -> 200 and body = Hello, Spring!
- curl -k -v -X GET http://localhost:8080/account -H "Accept:application/json"
    -> 200 and body = {"glossary":{"title":"example glossary","GlossDiv":{"title":"S","GlossList":{"GlossEntry":{"ID":"SGML","SortAs":"SGML","GlossTerm":"Standard Generalized Markup Language","Acronym":"SGML","Abbrev":"ISO 8879:1986","GlossDef":{"para":"A meta-markup language, used to create markup languages such as DocBook.","GlossSeeAlso":["GML","XML"]},"GlossSee":"markup"}}}}}
    
   
# TODO
- In AccountHandler, verify that accounts are retrieved.
        - Maybe use the below as a reference:
                - https://spring.io/guides/gs/accessing-data-r2dbc/
                - code is at https://github.com/spring-guides/gs-accessing-data-r2dbc/blob/master/complete/src/main/java/com/example/accessingdatar2dbc/AccessingDataR2dbcApplication.java
- Retry & asynch endpoint: how to implement. For instance if the findAll in AccountHandler fails, how to handle it?
- Read:
        - https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
        - https://netflixtechblog.com/reactive-programming-in-the-netflix-api-with-rxjava-7811c3a1496a
        - https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
        - Apply back pressure and test it.    
- Asynch endpoint to stream data from Azure or similar (Blob: see email). 
