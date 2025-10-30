# TUrtle
This is the official [TUrtle](https://exmaple.com) repository

## Development
[Docker](https://www.docker.com/) has to be installed in order to run the full application stack.  
All commands listed here are run in `/`  
* Start the Docker Development stack
    * `docker compose -f docker-compose.dev.yml up --build`

___

# Backend (FastAPI, Python)
This is the backend, base on FastAPI in Python  
Name: **TUrtleAPI**

## Structure
```
backend/
    api/
        v1/                     
            models/             |   database tables in python format
            repositories/       |   database inteface for models
            routers/            |   endpoint declarations
            schemas/            |   database model templates
            services/           |   services, for example a JWTService for auth persistance
    core/                       |   configs, security, other things
    database/                   |   ?
    main.py                     |   main entrypoint
    requirements.txt            |   python dependencies for the backend
```

## Development
All commands listed here are run in `/backend`
* Install the Dependencies
  * `pip install -r requirements.txt`
* Save currently installed dependencies into [requirements.txt](backend/requirements.txt)
  * `pip freeze > requirements.txt`

## Documentation
* The TUrtleAPI documentation can be found when the server is running under
  * `http://localhost:8000/docs`

___

# Frontend (Svelte, JavaScript)
Name: **TUrtleView**

## Structure
```
frontend/
    routes/     |   the different pages
    lib/        |   reusable components
```

## Development
All commands listed here are run in `/frontend`
* Initialize the frontend
    * `npm install`
* Start the Server (i have not tested this yet, may be wrong)
    * `npm start`
