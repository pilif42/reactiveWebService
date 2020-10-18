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
    
    
# Endpoints
- We started building functional endpoints as described at https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web-reactive.html#webflux-fn
    - See CustomerRouter.
- We then built annotated endpoints as described at https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web-reactive.html#webflux-controller
    - See package controller.


# To build:
mvn clean install


# To run:
- Run config in IntelliJ:
       - pointing to Application
       - VM Options = -Dspring.profiles.active=dev
- Or: mvn clean package spring-boot:run -DskipTests=true -Dspring-boot.run.profiles=dev


# To test:
- with curl:
        - functional endpoints:
                - curl -k -v -H "Content-Type: application/json" -d '{"email":"zz@email.com", "password":"pwd1", "role":"Developer"}' -X POST 'http://localhost:8080/customers'
                        - HTTP/1.1 201 Created
                        - Location: /customers/5
                - curl -k -v -H "Accept:application/hal+json" -H "Accept-Language:en-US" -H "Cache-Control:no-store" -X GET 'http://localhost:8080/customers' 
                        - 200 [{"id":1,"email":"A@email.com","password":"tester123","role":"Tester"},{"id":2,"email":"B@email.com","password":"tester123","role":"Tester"},{"id":3,"email":"C@email.com","password":"tester123","role":"Tester"},{"id":4,"email":"D@email.com","password":"tester123","role":"Tester"},{"id":5,"email":"zz@email.com","password":"pwd1","role":"Developer"}]
        - annotated endpoints:
                - curl -k -v -H "Content-Type: application/json" -d '{"email":"zz@email.com", "password":"pwd1", "role":"Developer"}' -X POST 'http://localhost:8080/annotatedCustomers'
                         - HTTP/1.1 201 Created
                - curl -k -v -H "Accept:application/hal+json" -H "Accept-Language:en-US" -H "Cache-Control:no-store" -X GET 'http://localhost:8080/annotatedCustomers' 
                         - 200 [{"id":1,"email":"A@email.com","password":"tester123","role":"Tester"},{"id":2,"email":"B@email.com","password":"tester123","role":"Tester"},{"id":3,"email":"C@email.com","password":"tester123","role":"Tester"},{"id":4,"email":"D@email.com","password":"tester123","role":"Tester"},{"id":5,"email":"zz@email.com","password":"pwd1","role":"Developer"}]         
                - curl -k -v -H "Accept:application/hal+json" -H "Accept-Language:en-US" -H "Cache-Control:no-store" -X GET 'http://localhost:8080/annotatedCustomers/1'
                         - 200 {"id":1,"email":"A@email.com","password":"tester123","role":"Tester"}
- with project reactiveWebClient
