# drones

Prerequisites:
 - docker 18.09+
 - docker-compose 1.23+
 - java 9+
 
 Build and start the environment with:
 ```
 docker-compose up --force-recreate -d  --build
 ```
 
 When application is built and containers are started see the logs with:
 ```
 docker logs -f drones-app
 ```
 Note that starting kafka and mysql might take some time.
 
 Sample output:
 ```
 [thread:main] Starting drone with id 5937
 [thread:drone-1] [6043] Started with memory 10 and speed 12.0
 [thread:drone-2] [5937] Started with memory 9 and speed 14.0
 [thread:http-nio-8080-exec-10] 10 moves sent to drone 6043
 [thread:http-nio-8080-exec-2] 9 moves sent to drone 5937
 [thread:drone-1] [6043] Flying to [51.501087, -0.124971]
 [thread:drone-2] [5937] Flying to [51.495117, -0.101261]
 [thread:drone-2] [5937] Flying to [51.495182, -0.101261]
 [thread:drone-1] [6043] Flying to [51.501095, -0.125065]
 [thread:org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] [traffic-report] 2019-06-23T15:23:55.156615Z 5937 14.0m/s; LIGHT traffic in Elephant & Castle
 [thread:org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] [traffic-report] 2019-06-23T15:23:55.156618Z 6043 12.0m/s; MODERATE traffic in Westminster
 [thread:drone-2] [5937] Flying to [51.495247, -0.101228]
 ```

Assuming *kafka-console-consumer.sh* script is available locally traffic messages can also be seen using:
```
kafka-console-consumer.sh --bootstrap-server localhost:29092 --topic traffic-reports
```
