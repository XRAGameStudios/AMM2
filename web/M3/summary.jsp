<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${not empty newpost}"> 
    <div class="message post">
        <div>
            <h2>Riassunto del post:</h2>
            <ul>
                <li>Contenuto del post: ${newpost.content}</li>
                <li>Autore: ${newpost.author.fullname}</li>
                <li>Destinatario: ${newpost.user.fullname}</li>
                <li>Link in allegato: ${newpost.url}</li>
            </ul>
        </div>
        <div>
            <form method="post" action="bacheca.html">
                <input type="hidden" name="action" value="confirm">
                <input type="hidden" name="user" value="${newpost.user.ID}">
                <input type="hidden" name="for" value="${newpost.user.fullname}">
                <button type="submit">Conferma</button>
            </form>
        </div>
    </div>
</c:if>
