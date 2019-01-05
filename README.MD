
INFO] Generating "Change Log" report    --- maven-changelog-plugin:2.3:changelog
[INFO] Generating changed sets xml to: /Users/fergus.macdermot/Documents/git-repo/personal/demo-app/target/changelog.xml
[INFO] Executing: /bin/sh -c cd /Users/fergus.macdermot/Documents/git-repo/personal/demo-app && git whatchanged '--since=2018-12-06 07:00:16 +0000' '--until=2019-01-06 07:00:16 +0000' --date=iso -- /Users/fergus.macdermot/Documents/git-repo/personal/demo-app
[INFO] Working directory: /Users/fergus.macdermot/Documents/git-repo/personal/demo-app
[INFO] Generating "File Activity" report --- maven-changelog-plugin:2.3:file-activity
[INFO] Using existing changelog.xml...
[INFO] Generating "Developer Activity" report --- maven-changelog-plugin:2.3:dev-activity
[INF
Usage

# https://start.spring.io/
# https://spring.io/guides/tutorials/bookmarks/
# https://www.baeldung.com/spring-boot-app-as-a-service
# https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html
# https://www.packer.io/intro/getting-started/build-image.html
# https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
# mvn clean spring-boot:run
# http://localhost:8000/swagger-ui.html

Employee

mvn clean resources:resources site install -P reporting

mvn license:remove-file-header


curl -v localhost:8000/actuator/health
curl -v localhost:8000/actuator/info
curl -v localhost:8000/actuator/metrics
curl -v localhost:8080/employees | jq
curl -v localhost:8080/headers
curl -H "Accept-Charset: utf-8" -v localhost:8080/headers
curl -v http://localhost:8080/employees/1 | jq
curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
curl -H "custom: utf-8" localhost:8000/v2/api-docs | jq
