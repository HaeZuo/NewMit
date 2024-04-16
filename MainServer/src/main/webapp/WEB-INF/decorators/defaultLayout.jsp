<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <sitemesh:write property='head'/>
    </head>
    <body>
        <header>
            <jsp:include page="./include/header.jsp"/>
        </header>
        <div>
            <sitemesh:write property='body'/>
        </div>
        <footer>
            <jsp:include page="./include/footer.jsp"/>
        </footer>
    </body>
</html>