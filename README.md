# Technical-test
The spring technical test

## Setup

- Under /resource/application.yaml, please edit your database information (```username``` + ```password```)
  

- Connect to your Database Manager Tool (MySQL workbench), run below command to init your schema:
  
    `
        CREATE SCHEMA `testdb`;
    `
  

- Under root project (`{home}/technical-test`)
  
  build: `mvn clean install`

  run: `mvn spring-boot:run`


- Open your browser and access `http://localhost:9999/technical/v1/api/greeting` response `Hello world` mean that the application has been started

## Explain application

1. The application has been designed as multiple modules for each responsibility. There are 4 main packages includes:
    
+ `/domains:` take responsibility of define JPA entities
+ `/repositories:` take responsibility of database connection query
+ `/services:` ask to `/repositories` for getting `Entities`, and take responsibility of handle business logic
+ `/controller:` ask to `/service` for getting `Responses`, we have defined `Rest API endpoints` here.
+ Others: `/dtos, /enums, /errors, /exceptions, /mappers, /utils, /validators` to support for transfer data, utilize and validations

2. There are two main patterns using: `Singleton` and `Dependency Injection`

Two above things make this application  comply to SOLID principles.

3. Remainder:

    3.1. `Consider that the technology should support high volumes of read and write requests with small payloads and
   be highly available and performant`.
   The request DTO is still handle as normal way. I have researched about this thing but having no idea to implement.
   
    3.2. For `GET devices/{device-id}?from=<from>&to=<to>`. It only makes sure work well as manual test, no having integration tests.

    3.3 Because of wasted a lot of time to resolve stuck in the locally then some Unit Tests under `/services` are not covered, but these cases are working as well and covered by Integration tests.

4. Times spent:
   
   - Preparation: 30 minutes for setting project and Github
   
   - Coding: 12 hours (including tests)
   
   - Documentation: 30 minutes
   
   - Overall Check: 15 minutes
   
   #### Grand total: 13 hours

