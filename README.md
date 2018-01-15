
 [![Build Status](https://travis-ci.org/Nazariii/party-planner.svg?branch=master)](https://travis-ci.org/Nazariii/party-planner) [ ![Codeship Status for Nazariii/party-planner](https://codeship.com/projects/4e2bbd20-d887-0133-f6c8-5e1dcb628de7/status?branch=master)](https://codeship.com/projects/143232) [![Dependency Status](https://www.versioneye.com/user/projects/56fe9754fcd19a00415afdf4/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56fe9754fcd19a00415afdf4) [![codecov.io](https://codecov.io/github/Nazariii/party-planner/coverage.svg?branch=master)](https://codecov.io/github/Nazariii/party-planner?branch=master)  [![Stories in Ready](https://badge.waffle.io/Nazariii/party-planner.png?label=ready&title=Ready)](https://waffle.io/Nazariii/party-planner) [![Join the chat at https://gitter.im/Nazariii/party-planner](https://badges.gitter.im/Nazariii/party-planner.svg)](https://gitter.im/Nazariii/party-planner?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)



#Party planner


##Rest platform for easy organisation of parties

_This is test project_

###Technologies:

- Java 8
- Spring Framework 4.2
- Spring Boot 1.3.2
- Spring Data JPA/ Specifications/ Hibernate 4
- Spring Security 4.0.3 / ( CORS, CSRF) / LDAP Auth
- MySQL/ H2
- Spring MVC
- Spring + Quartz 2.2 / EhCache
- Spring Testing/ JUnit 4/ Hamcrest/ JSON path
- YAML/ Lombok
- SLF4J

###Pre Reqs:

- install MySQL and create _party_planer_ db using docker:
  1. docker run -it -v C:/tempData/mysql:/var/lib/mysql --name  mysql -e MYSQL_ROOT_PASSWORD=root --rm -p 3306:3306  -d mysql:latest
  2. docker exec -it mysql sh
  3. mysql -u root -p
  4. create database party_planner;

