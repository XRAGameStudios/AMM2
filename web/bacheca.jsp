<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <c:set var="title" value="Bacheca di Nerdbook" scope="request"/>
    <jsp:include page="head.jsp"/>
    <body>
        <div class="page">
            <jsp:include page="header.jsp"/>
            <c:set var="page" value="bacheca" scope="request"/>
            <jsp:include page="navbar.jsp"/>
            <jsp:include page="aside.jsp"/>    
                
            <div id="bacheca" class="content">
                <jsp:include page="message.jsp"/>
                <jsp:include page="summary.jsp"/>
                <c:if test="${empty team && not empty user}">
                    <div class="post state">
                        <img alt="Foto di ${user.fullname}" class="profile" title="Foto di ${user.name}" src="${user.imageURL}"/>
                        <p class="myself user">${user.fullname}</p>
                        <p class="mystate">${user.status}</p>
                    </div>
                </c:if>
                <c:if test="${not empty team}">
                    <div class="post state">
                        <p class="user">${team.name}</p>
                    </div>
                </c:if>
                <div class="forms post" id="newPost">
                    <form method="POST" action="bacheca.html">
                        <input type="hidden" name="action" value="send">
                            
                        <c:if test="${empty team}">
                        <input type="hidden" name="user" value="${user.ID}">
                        </c:if>
                        
                        <c:if test="${not empty team}">
                        <input type="hidden" name="team" value="${team.ID}">
                        </c:if>
                            
                        <input name="content" id="newStatus" type="text" placeholder="Scrivi nuovo post..."/>
                        <input type="url" name="attachment" id="addAttachment" placeholder="Aggiungi URL (opzionale)..."/>    
                        <label for="image">Immagine</label>
                        <input type="radio" name="type" value="image">
                        <label for="url">Link</label>
                        <input type="radio" name="type" value="url">
                        <button type="submit" >Invia</button>
                            
                    </form>
                </div>
                <div class="posts">
                    <c:forEach var="post" items="${posts}">
                        <div class="post">
                            <a href="bacheca.html?user=${post.author.ID}">
                                <img class="profile" alt="Foto di ${post.author.name}" title="Foto di ${post.author.name}" src="${post.author.imageURL}"/>
                            </a>
                            <a href="bacheca.html?user=${post.author.ID}">
                                <p class="user">${post.author.fullname}</p>
                            </a>
                            <p class="message">${post.content}</p>
                            <c:if test="${post.postType=='IMAGE'}">
                                <a href="${post.URL}" target="_blank">
                                    <img class="attachment" title ="Allegato di ${post.author.name}" alt="Post con foto" src="${post.URL}">
                                </a>
                            </c:if>
                            <c:if test="${post.postType=='URL'}">
                                <p class="userLink"> <a href="${post.URL}" target="_blank">${post.URL}</a></p>
                                </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
        
</html>