<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${not empty newPost}"> 
    <div class="message post">
        <div>
            <h2>Riassunto del post:</h2>
            <ul>
                <li>Contenuto del post: ${newPost.content}</li>
                <li>Autore: ${newPost.author.fullname}</li>
                    <c:if test="${empty team}">
                    <li>Destinatario: ${newPost.user.fullname}</li>
                    </c:if>
                    <c:if test="${not empty team}">
                    <li>Destinatario: ${newPost.group.name}</li>
                    </c:if>
                <li>Tipo: ${newPost.postType}</li>
                <li>Link in allegato: ${newPost.URL}</li>
            </ul>
        </div>
        <div>
            <form method="post" action="bacheca.html">
                <input type="hidden" name="action" value="confirm">
                <input type="hidden" name="content" value="${newPost.content}">
                <input type="hidden" name="attachment" value="${newPost.URL}">
                <input type="hidden" name="type" value="${newPost.postType}">
                <c:if test="${empty team}"><input type="hidden" name="user" value="${newPost.user.ID}"></c:if>
                <c:if test="${not empty team}"><input type="hidden" name="team" value="${newPost.group.ID}"></c:if>
                <input type="hidden" name="from" value="${newPost.author.ID}">
                
                <input type="hidden" name="for" 
                       <c:if test="${empty team}"> value="${newPost.user.fullname}"</c:if>
                       <c:if test="${not empty team}"> value="${newPost.group.name}"</c:if>>
                <button type="submit">Conferma</button>
            </form>
        </div>
    </div>
</c:if>
