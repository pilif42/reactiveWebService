# POC on Reactive APIs
Accessing data with R2DBC was put together using:
    - https://spring.io/guides/gs/accessing-data-r2dbc/ 
    - https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
    - https://projectreactor.io/docs/core/release/reference/index.html#which-operator


# Reactive H2
- Liquibase does NOT work yet with R2DBC. See open ticket https://liquibase.jira.com/browse/CORE-3419
    - however, https://docs.spring.io/spring-boot/docs/2.3.0.M3/reference/htmlsingle/#boot-features-r2dbc mentions it?
- At startup:
    - the table customer is created thanks to initializer in DbConfig.
    - the table customer is then populated in SampleDataInitializer.


# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
- curl -k -v -H "Content-Type: application/json" -d '{"email":"zz@email.com", "password":"pwd1", "role":"Developer"}' -X POST 'http://localhost:8080/customers'
    - HTTP/1.1 201 Created
    - Location: /customers/5
- curl -k -v -H "Accept:application/hal+json" -H "Accept-Language:en-US" -H "Cache-Control:no-store" -X GET 'http://localhost:8080/customers' 
    - 200 [{"id":1,"email":"A@email.com","password":"tester123","role":"Tester"},{"id":2,"email":"B@email.com","password":"tester123","role":"Tester"},{"id":3,"email":"C@email.com","password":"tester123","role":"Tester"},{"id":4,"email":"D@email.com","password":"tester123","role":"Tester"},{"id":5,"email":"zz@email.com","password":"pwd1","role":"Developer"}]

 
# TODO
- test the 2 endpoints (POST & GET) with a WebClient (in a different project to separate concerns: reactiveWebClient?)
        - maybe use https://www.baeldung.com/spring-5-webclient as a pointer?
        - how do we access the 5 in Location: /customers/5 when a POST is made?
        - use subscribe on the GET results: examples at https://projectreactor.io/docs/core/release/reference/index.html#_subscribe_method_examples
- play with the backpressure: https://projectreactor.io/docs/core/release/reference/index.html#_on_backpressure_and_ways_to_reshape_requests
- do we need classic endpoints as with ProfileRestController at https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
        - test endpoints: see AbstractBaseProfileEndpoints, etc. at https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux
- Retry & asynch endpoint: how to implement. For instance if the createCustomer in CustomerHandler fails, how to handle it?
- Read:
        - https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
        - https://netflixtechblog.com/reactive-programming-in-the-netflix-api-with-rxjava-7811c3a1496a
        - https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
        - Apply back pressure and test it.    
- Asynch endpoint to stream data from Azure or similar (Blob: see email). 
