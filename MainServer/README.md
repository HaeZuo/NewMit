# MainServer

## 01. 환경

* Language : Java
* Type : Gradle - Groovy
* JDK : Oracle OpenJDK VERSION 17.0.9
* Java : 17
* Package Create : War
* Spring Boot 3.2.4

### 종속성

* Developer Tools
  - Spring Boot DevTools
  - Lombok
  - Spring Modulith

* Web
  - Spring Web
  - Spring Session
  - Spring Web Services

* Template Engines
  - ~~Thymeleaf~~

* Template
  - ~~Tiles or~~ SiteMesh

* Security
  - Spring Security
  - OAuth2 Client

* SQL
  - MyBatis Framework
  - MySQL Driver

## 02. 설정

* JSP 사용
  - Spring Boot는 기본적으로 jsp를 지원하지 않으며, 대체로 Spring Boot에서 제공하는 Template Engines 사용을 권장한다. 하지만 사용했다.
  - 템플릿 엔진은 SiteMesh를 사용했다.
