<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <body>
    <div class="wrap">
        <jsp:include page="./include/header.jsp"/>
        <div>
            <head>
                <sitemesh:write property='head'/>
            </head>
            <sitemesh:write property='body'/>
        </div>
        <jsp:include page="./include/footer.jsp"/>
    </div>
    </body>
</html>