# Flag Picker
This is a simple microservice developed using spring boot. It provides various REST end points to support Flag pick requirements

Available REST end points can be viewed using open API link - http://localhost:8080/swagger-ui.html

##REST End Points

Fetch specific Continent details - http://localhost:8080/v1/continents/{continent}

Fetch Count Metrics of a specific continent - http://localhost:8080/actuator/metrics/count.continent.{continent} - Replace {continent} with actual continent value

Fetch specific Country flag - http://localhost:8080/v1/countries/{country}/flag

Fetch Count Metrics of a specific country - http://localhost:8080/actuator/metrics/count.flag.country.{country}} - Replace {country} with actual continent value
