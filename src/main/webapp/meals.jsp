<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr bgcolor="#6495ed">
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

    <c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>

    <c:choose>
    <c:when test="${meal.excess}">
    <tr bgcolor="#cd5c5c">
        </c:when>
        <c:otherwise>
    <tr bgcolor="#8fbc8f">
        </c:otherwise>
    </c:choose>

        <td><c:out value="${formatter.format(meal.dateTime)}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        </c:forEach>
    </tr>

</table>
</body>
</html>
