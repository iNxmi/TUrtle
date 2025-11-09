# TUrtle
This is the official [TUrtle](https://exmaple.com) repository

## Development
[Docker](https://www.docker.com/) has to be installed in order to run the full application stack.  
All commands listed here are run in `/`  
* Start the Docker Development stack
    * `docker compose up --build`

___

# Backend (Spring Boot, Kotlin)
This is the backend, based on Spring Boot in Kotlin  
Name: **TUrtleAPI**  
Port: 8080

## application.yml
```properties
turtle.api.max_sessions=16
# 30 * 24 * 60 * 60 = 2_592_000 -> 30 days in seconds
turtle.api.session_duration_seconds=2_592_000
turtle.api.session_key={some_super_secret_key}
```

## Docker Environment
```
DATASOURCE_SERVER: {ip:port}
DATASOURCE_DATABASE: {database}
DATASOURCE_USERNAME: {username}
DATASOURCE_PASSWORD: {password}
```

Based on REST:

## Structure
```
TUrtle/backend/src/
    main/
        kotlin/de/csw/turtle/
            config/                 |   configs, security, other things
            controller/             |   endpoint declarations
            entity/                 |   database tables in JPA format
            exception/              |
            repository/             |   CRUD operations for Entities
            service/                |   services, for example a JWTService for auth persistance
            Application.kt          |   main entry point
        resources/
            application.yml         |   Spring Boot main configuration, keep it secret
```

## Swagger UI / OpenAPI
When the TUrtleAPI is running, API endpoint documentation can be accessed and tested via [http://localhost:8080/docs](http://localhost:8080/docs).

___

# Frontend (Svelte, JavaScript)
Name: **TUrtleView**  
Port: 3000

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
* Start the Server
    * `npm run dev`

---

# Adminbereich
- Managen von Support tickets
- Steuerung von Hardware (Tür, schränke) als override
- Managen von ausgeliehenen Gegenständen (von anderen Usern) und Database
- Managen von Veranstaltungskalender
- Managen von User database (erstellen/editieren/löschen)
- Anzeigen von Statistiken

# Userbereich
- Ausleihen von Gegenständen
- Anfordern von Veranstaltungsterminen
- Profil einsehen und bearbeiten
- Informations/Help Seite anzeigen/lesen (event. außerhalb von login)
- Emoji-Code zurücksetzen / Passwort zurücksetzen
- Support tickets ausstellen

# Door Terminal
- terminal page für Door terminal
- Variable resolution / auto scale
- Support ticket button mit basic Auswahl
