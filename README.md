# Flag Picker
This is a simple microservice developed using spring boot. It provides various REST end points to support Flag pick requirements

##REST End Points
Available REST end points can be viewed using Swagger UI - http://localhost:8080/swagger-ui.html

##Database Scripts
DDL scripts were available in schema.sql file under resources

DML scripts were available in data.sql file under resources

#Metrics End Points
Metrics end points can be accessed using - http://localhost:8080/actuator/metrics

Metrics to check on number of times a continent was searched for - http://localhost:8080/actuator/metrics/count.continents.{continent} 
Ex: http://localhost:8080/actuator/metrics/count.continents.Africa, http://localhost:8080/actuator/metrics/count.continents.Asia

Metrics to check on number of times a country was searched for - http://localhost:8080/actuator/metrics/count.countries.{country}. 
Ex: http://localhost:8080/actuator/metrics/count.countries.USA, http://localhost:8080/actuator/metrics/count.countries.India

##Execute the Micro service using Gradle
Run the command on root folder - gradle bootRun

##Execute the Micro service using Java Class
Run the main method in FlagPickerApplication.java