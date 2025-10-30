# TUrtle
This is the official [TUrtle](https://exmaple.com) repository

## Development
[Docker](https://www.docker.com/) has to be installed in order to run the full application stack.  
All commands listed here are run in `/`  
* Start the Docker Development stack
    * `docker compose -f docker-compose.dev.yml up --build`

___

# Backend (Spring Boot, Kotlin)
This is the backend, base on Spring Boot in Kotlin
Name: **TUrtleAPI**

## Structure
```
TUrtle/backend/src/
    main/
        kotlin/com/csw/turtleapi/
            api/
                v1/                     |   1st version of the api
                    config/             |   configs, security, other things
                    controller/         |   endpoint declarations
                    entity/             |   database tables in JPA format
                    exception/          |
                    repository/         |   CRUD operations for Entities
                    service/            |   services, for example a JWTService for auth persistance
            Application.kt              |   main entry point
        resources/
            application.yml                 |   Spring Boot main configuration, keep it secret
```

___

# Frontend (Svelte, JavaScript)
Name: **TUrtleView**

## Structure
```
TUrtle/frontend/
    routes/     |   the different pages
    lib/        |   reusable components
```

## Development
All commands listed here are run in `/frontend`
* Initialize the frontend
    * `npm install`
* Start the Server (i have not tested this yet, may be wrong)
    * `npm start`
