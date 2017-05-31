<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<json:array>
    <c:forEach var="u" items="${foundUsers}">
        <json:object>
            <json:property name="ID" value="${u.ID}"/>
            <json:property name="name" value="${u.name}"/>
            <json:property name="surname" value="${u.surname}"/>
            <json:property name="fullname" value="${u.fullname}"/>
            <json:property name="imageURL" value="${u.imageURL}"/>
        </json:object>
    </c:forEach>
</json:array>