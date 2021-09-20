#Prerequisites
+ Docker

#About
This project is based on Spring-Boot and requires a local postgres DB running on the standard port. 
For development and testing, a docker-compose file is provided in src/main/docker which will start a
postgres db and then the application. The fastest way to get started is to run the `buildAndRun` gradle
task, which will build a docker image and then execute docker compose up. There is also an IDEA http-client
file(rest-test.http) which allows sending calls to the local server. 