
INFO] Generating "Change Log" report    --- maven-changelog-plugin:2.3:changelog
[INFO] Generating changed sets xml to: /Users/fergus.macdermot/Documents/git-repo/personal/demo-app/target/changelog.xml
[INFO] Executing: /bin/sh -c cd /Users/fergus.macdermot/Documents/git-repo/personal/demo-app && git whatchanged '--since=2018-12-06 07:00:16 +0000' '--until=2019-01-06 07:00:16 +0000' --date=iso -- /Users/fergus.macdermot/Documents/git-repo/personal/demo-app
[INFO] Working directory: /Users/fergus.macdermot/Documents/git-repo/personal/demo-app
[INFO] Generating "File Activity" report --- maven-changelog-plugin:2.3:file-activity
[INFO] Using existing changelog.xml...
[INFO] Generating "Developer Activity" report --- maven-changelog-plugin:2.3:dev-activity
[INF
Usage



Employee

mvn clean site install -P reporting

mvn license:remove-file-header


curl -v localhost:8000/actuator/health
curl -v localhost:8000/actuator/info
curl -v localhost:8000/actuator/metrics
curl -v localhost:8000/employees | jq
curl -v localhost:8000/headers
curl -H "Accept-Charset: utf-8" -v localhost:8080/headers
curl -v http://localhost:8000/employees/1 | jq
new employee
curl -v -X POST localhost:8000/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
replace
curl -v -X PUT localhost:8000/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bear"}'
delete
curl -v -X DELETE localhost:8000/employees/3
curl -H "custom: utf-8" localhost:8000/v2/api-docs | jq

# 
logback
https://logback.qos.ch/manual/configuration.html
# https://spring.io/guides/tutorials/rest/ - original code
# https://start.spring.io/
# https://spring.io/guides/tutorials/bookmarks/
# https://www.baeldung.com/spring-boot-app-as-a-service
# https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html
# https://www.packer.io/intro/getting-started/build-image.html
# https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
# mvn clean spring-boot:run
# http://localhost:8000/swagger-ui.html