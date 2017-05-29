<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<aside>
    <div>
        <input class="search" type="text" value="" id="search" name="search" placeholder="Cerca persone...">
        <button id="sendSearch" onclick="search()">Cerca</button>
    </div>
    <div class="people">
        <h2 class="title">Persone</h2>
        <div class="friends" >
            <ul id="friendz">
                <!-- ciclo for dove vengono inseriti gli amici di ogni persona nella sidebar-->
                <c:forEach var="friend" items="${friends}">
                    <li>
                        <c:if test="${not empty friend.imageURL}">
                            <img class="profile sidebar" alt ="Foto di ${friend.fullname}" src="${friend.imageURL}">
                        </c:if>
                        <a href="bacheca.html?user=${friend.ID}">
                            <p class="user">${friend.fullname}</p>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="groups">
        <h2 class="title">Gruppi</h2>
        <div class="grouptitle">
            <a id="add" href="about:blank"><img alt="Crea nuovo gruppo" title="Crea nuovo gruppo" src="images/add.png"></a>
        </div>
        <div class="section_content">
            <ul>
                <!--L'icona è un pseudoelemento-->
                <c:forEach var="group" items="${groups}">
                    <li>
                        <p id="${group.icon}" class="group"><a href="bacheca.html?team=${group.ID}">${group.name}</a></p>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</aside>
