<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${not empty message}"> 
    <div class="message post">
        <h2>Informazione:</h2>
        <p>${message}</p>
    </div>
</c:if>
