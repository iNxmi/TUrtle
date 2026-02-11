# TUrtle
This is the official [TUrtle](https://exmaple.com) repository

## Development
[Docker](https://www.docker.com/) has to be installed in order to run the full application stack.  
All commands listed here are run in `/`  
* Start the Docker Development stack
    * `docker compose up --build`

___

# Backend (Spring Boot, Kotlin, REST)
This is the backend, based on Spring Boot in Kotlin. 
Port: 8080

**TODO:**
* make ip not spoofable
* email sending; noreply@mail.de (verification email, confirmation email(booking, returning, deadline reminder), support confirmation email, password/pin reset email)
* statistics
* distinguish prof/student role
* document this: rsql, system settings, templates with thymeleaf, regex in some system settings
* `*@stud.tu-darmstadt.de` -> ok
* `*@tu-darmstadt.de` -> ok
* `*@andere.email` -> request
* local dns via PI Hole
* make all srvice create and patch transactional
* invert all request overrides to make them more safe, in controllers
* implement user auth token tracking for revoking
* 
* /api/auth/reset-password
* create /api/token endpoints (add /validate to check if it is valid for frontend)
* make CREATE:/api/support-tickets verify by email like user registration
* user registration has no email check if valid or in trusted emails check

## Docker Environment
```
DATASOURCE_SERVER: ${IP:PORT}
DATASOURCE_DATABASE: ${DATABASE}
DATASOURCE_USERNAME: ${USERNAME}
DATASOURCE_PASSWORD: ${PASSWORD}

SMTP_HOST: ${SMTP_HOST}
SMTP_PORT: ${SMTP_PORT}
SMTP_USERNAME: ${SMTP_USERNAME}
SMTP_PASSWORD: ${SMTP_PASSWORD}
```

## Structure
```
TUrtle/backend/src/
    main/
        kotlin/de/csw/turtle/
            api/
                components/             |   general uncategorized components
                configuration/          |   configs, security, other things
                controller/             |   endpoint declarations
                dto/                    |   data transfer objects (exposed to the internet)
                entity/                 |   database tables in JPA format
                exception/              |   custom exceptions and global exception handler
                filter/                 |   filters for e.g. auth and rate limiting
                mapper/                 |   mapping to/from dto and database entities
                repository/             |   CRUD operations for Entities
                service/                |   services, for example a JWTService for auth
            Application.kt              |   main entry point
        resources/
            application.yml             |   Spring Boot main configuration
    test/kotlin/de/csw/turtle/
        api/
            service/
            controller/
```

## Swagger UI / OpenAPI
When the TUrtleAPI is running, API endpoint documentation can be accessed and tested via [http://localhost:8080/docs](http://localhost:8080/docs).

___

# Frontend (Svelte, JavaScript)
Port: 3000

## Structure
```
TUrtle/frontend/
    messages/               |   I18n translations
    src/        
        routes/             |   the different pages
        paraglide/          |   autogenerate translation files absed on /messages/
        lib/                |   reusable components
        app.css             |   root css document
        app.html            |   root html document
    static/                 |   public exposed files (used for fonts, images and other visible stuff)
    tests/                  |   various tests
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
