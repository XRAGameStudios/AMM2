<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="title" value="Accedi a Nerdbook" scope="request"/>
    <jsp:include page="head.jsp"/>
    
    <body>
        <div class="page">
            <jsp:include page="header.jsp"/>
            <c:set var="page" value="login" scope="request"/>
            <jsp:include page="navbar.jsp"/>
            <div class="content" id="login_content">
                <jsp:include page="error.jsp"/>
                <div class="logo">
                    <p class="logo">NerdBook</p>
                </div>
                <div id="login" class="forms">
                    <form action="login.html" method="post">
                        <label for="username">Nome utente:</label>
                        <input type="text" id="username" name="username" value="">
                        <label for="password">Password:</label>
                        <input type="password" name="password" id="password" value="">
                        <button type="submit">Accedi</button>
                    </form>
                </div>
            </div>
                
        </div>
    </body>
</html>