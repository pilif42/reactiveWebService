# Original documentation
https://spring.io/guides/gs/reactive-rest-service/


# To build:
mvn clean install


# To run:
Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
http://localhost:8080/hello
    -> Hello, Spring!
    
    
# H2
The console is available at http://localhost:8082/ with sa / password thanks to the class com.sample.db.H2.
    
   
# TODO
Find a way to insert a lot of data into account
    - current issue with H2 console: Database "/home/philippe/test" not found, either pre-create it or allow remote database 
    creation (not recommended in secure environments) [90149-200] 90149/90149 
Asynch endpoint to stream data from DB using https://www.baeldung.com/spring-data-java-8#completablefuture?
