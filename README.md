# Weather App Backing Service

## Setup

To run the SWA Backing Service, follow these steps:

1. Build the docker image for the service by executing the following commands:
   ```bash
   mvn clean install -DskipTests=true
   docker build -t=YOUR_CONT_NAME .
    ```
2. Launch a PostgreSQL container to serve as the database for the Backing Service:
    ```bash
    docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=main -e POSTGRES_DB=measurements --name=postgres_cont postgres
    ```
4. Start the SWA Backing Service container:
   ```bash
   docker run YOUR_CONT_NAME
    ```
## About 
The SWA Backing Service is responsible for storing data related to actual weather 
measurements of cities. It uses PostgreSQL as its database backend to manage the 
recorded measurements.
Once the service is up and running, you can access the API documentation to explore the available endpoints and their 
functionalities. The Open API Swagger documentation can be accessed at http://localhost:8082/swagger-ui/index.html)
## Authors

- Vadym Ostapovych - ostapva@fel.cvut.cz
- Tomáš Hauser - hausetom@fel.cvut.cz

### ToDo list

- [x] Basis functionality
- [x] Docker integration
- [ ] Resolve volume store for postgres

