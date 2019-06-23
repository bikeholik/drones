# drones

Prerequisites:
 - docker 18.09+
 - docker-compose 1.23+
 - java 9+
 
 Build and start the environment with:
 ```
 docker-compose up --force-recreate -d  --build
 ```
 
 When application is built and container is started see the logs with:
 ```
 docker logs -f drones-app
 ```
