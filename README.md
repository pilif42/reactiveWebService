# Accessing data with R2DBC
POC put together using https://spring.io/guides/gs/accessing-data-r2dbc/


# Reactive H2
Liquibase does NOT work yet with R2DBC:
    - open ticket = https://liquibase.jira.com/browse/CORE-3419
    - The alternative is:
            - schema.sql in src/main/resources. Spring Boot’s auto-configuration picks it up during application startup to initialize the database schema.
            - And, we populate the table customer:
                    - in demo of Application
                    - or in hello of GreetingHandler.
    

# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev
 
   
# TODO
- add endpoints. See https://spring.io/guides/gs/reactive-rest-service/
- Retry & asynch endpoint: how to implement. For instance if the findAll in AccountHandler fails, how to handle it?
- Read:
        - https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
        - https://netflixtechblog.com/reactive-programming-in-the-netflix-api-with-rxjava-7811c3a1496a
        - https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
        - Apply back pressure and test it.    
- Asynch endpoint to stream data from Azure or similar (Blob: see email). 


# TODO - To test:
# - curl -k -v -X GET http://localhost:8080/hello -H "Accept:text/plain"
#     -> 200 and body = Accounts have now been stored. Ready to rumble!
# - curl -k -v -X GET http://localhost:8080/customer -H "Accept:application/json"
#     -> 200 and body = {"glossary":{"title":"example glossary","GlossDiv":{"title":"S","GlossList":{"GlossEntry":{"ID":"SGML","SortAs":"SGML","GlossTerm":"Standard Generalized Markup Language","Acronym":"SGML","Abbrev":"ISO 8879:1986","GlossDef":{"para":"A meta-markup language, used to create markup languages such as DocBook.","GlossSeeAlso":["GML","XML"]},"GlossSee":"markup"}}}}}
   
