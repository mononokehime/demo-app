Usage

# https://start.spring.io/
# https://spring.io/guides/tutorials/bookmarks/
# https://www.baeldung.com/spring-boot-app-as-a-service
# https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html
# https://www.packer.io/intro/getting-started/build-image.html
# https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
# mvn clean spring-boot:run
# http://localhost:8000/swagger-ui.html


curl -v localhost:8000/actuator/health
curl -v localhost:8000/actuator/info
curl -v localhost:8000/actuator/metrics
curl -v localhost:8080/employees | jq
curl -v localhost:8080/headers
curl -H "Accept-Charset: utf-8" -v localhost:8080/headers
curl -v http://localhost:8080/employees/1 | jq
curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
curl -H "custom: utf-8" localhost:8000/v2/api-docs | jq

