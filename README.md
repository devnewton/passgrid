# passgrid

passgrid is a KISS password grid generator and manager powered by java, spring boot and mongodb.

## Build and run demo using [docker](https://www.docker.com/)

    docker build --tag=passgrid https://github.com/devnewton/passgrid.git
    docker run -p 8080:8080 passgrid

## Build, run and deploy manually

### Skill check

Please note that a thorough knowledge of Java web application development and hosting is required.

### Requirements

- JDK 7+
- Maven 3+
- mongodb 2+

### Build

    mvn package

### Run locally

Ensure that mongodb is running and listening on 127.0.0.1 then run:

    java -jar target/passgrid-*.jar

The frontend is now accessible on [locahost:8080](http://localhost:8080).

### Deploy and hosting on a production server

There is several options to deploy and host jb3. Here is one that requires:

- a domain name (example: mydomain.me).
- a web server with http proxy capabilities (example [Cherokee](http://cherokee-project.com/)).

#### Application configuration

In production environnement, please change the following parameters

- security.user.name
- security.user.password

to your application.properties configuration.

#### Deployment

1. Build and launch passgrid on the server.
2. Configure your web server to act as reverse proxy on http://localhost:8080
