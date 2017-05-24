<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav>
    <ul>
        <!--Condizione posta per chi non è loggato: non può visualizzare i due link-->
        <c:if test="${logged=='true'}"> 
            <li <c:if test="${page=='bacheca'}">class="active"</c:if> ><a href="bacheca.html">Bacheca</a></li> 
            <li <c:if test="${page=='profilo'}">class="active"</c:if> ><a href="profilo.html">Profilo</a></li> 
            </c:if>
        <li <c:if test="${page=='descrizione'}">class="active"</c:if> ><a href="descrizione.html">FAQ</a></li>
            <c:if test="${logged=='false'}">
            <li <c:if test="${page=='login'}">class="active"</c:if> ><a href="login.html">Accedi</a></li>    
            </c:if>
    </ul>
    <c:if test="${logged=='true'}">
        <div class="logout">
            <a href="login.html?action=logout" class="a_logout">
                <c:if test="${not empty me.imageURL}">
                    <img class="profile mini" alt="${me.fullname}" src="${me.imageURL}"/>
                </c:if>
                <p class="user logoutItem">${me.fullname}</p>
                <p class="logoutItem">Logout</p>
            </a> 
        </div>
    </c:if>
</nav>
