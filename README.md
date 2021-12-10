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

