<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="title" value="Il tuo profilo" scope="request"/>
    <jsp:include page="head.jsp"/>
        
    <body>
        <div class="page">
            <jsp:include page="header.jsp"/>
            <c:set var="page" value="profilo" scope="request"/>
            <jsp:include page="navbar.jsp"/>
            <jsp:include page="aside.jsp"/>    
                
            <div class="content" id="profile">
                <jsp:include page="message.jsp"/>
                <div class="inlineblock">
                    <image class="profile big" alt="La tua foto" src="${user.imageURL}">
                </div>
                <div id="infos" class="forms">
                    <h2>Inserisci i tuoi dati:</h2>
                    <form action="profilo.html" method="post">
                        <input type="hidden" name="action" value="save">
                        <label for="user_name">Nome:</label>
                        <input type="text" id="user_name" name="user_name" value="${user.name}">
                        <label for="user_surname">Cognome:</label>
                        <input type="text" id="user_surname" name="user_surname" value="${user.surname}">
                        <label for="img_profile">URL foto:</label>
                        <input type="url" id="img_profile" name="img_profile" value="${user.imageURL}">
                        <label for="user_phrase">Motto:</label>
                        <input type="text" id="user_phrase" name="user_phrase" value="${user.status}">
                        <label for="user_bd">Nato il:</label>
                        <input type="text" id="user_bd" name="user_bd" value="${user.age}">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" value="${user.password}">
                        <label for="password_conf">Conferma:</label>
                        <input type="password" id="password_conf" name="password_conf" value="${user.password}">
                        <button type="submit">Salva</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
    
</html>