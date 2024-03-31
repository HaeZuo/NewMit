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
```
    // -- JSP Begin --
    // implementation 'javax.servlet:jstl' // 스프링 3.0 미만
    // ref : https://velog.io/@jjungyu12/JSP-JSTL-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EB%B2%84%EC%A0%84%EA%B3%BC-%EC%82%AC%EC%9A%A9%EB%B2%95
    implementation 'jakarta.servlet:jakarta.servlet-api'
    implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api'
    implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl'
    implementation "org.apache.tomcat.embed:tomcat-embed-jasper"
    // -- JSP End --
```
 - 템플릿 엔진은 SiteMesh를 사용했다.
```
    // -- sitemesh Begin --
    // Template
    // 설정파일 : webapp/WEB-INF/decorators
    // application.java에 bean 추가
    implementation 'org.sitemesh:sitemesh:3.2.1'
    // -- sitemesh End
```
