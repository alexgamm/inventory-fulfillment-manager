## Inventory fulfillment manager

### Test task for PropVue LLC

**Prerequisites include:**
- Docker 
- Git
- Java 17

### Installation

Run the command to clone the repository locally

`git clone https://github.com/alexgamm/inventory-fulfillment-manager.git`

#### Containers run

The project root contains docker-compose.yml, which describes the configuration of the service components. To run it, go to the root directory of the project:

`cd project-name`

Execute the gradle task to build the application image:

`./gradlew bootBuildImage`

Run the command to start the required containers:

`docker compose up -d`

The application provides 3 API endpoints, the documentation for which can be found in swagger ui. Run the application locally and follow the link:

http://localhost:8080/swagger-ui/index.html#/

