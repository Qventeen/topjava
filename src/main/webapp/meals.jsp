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
<table align="center">
    <tr bgcolor="#6495ed">
        <th>ID</th>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Deleting</th>
        <th>Editing</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <jsp:useBean id="formatter" scope="application" type="java.time.format.DateTimeFormatter"/>

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
        <td><c:out value="${meal.id}"/></td>
        <td><c:out value="${formatter.format(meal.dateTime)}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>"/>Delete</td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>"/>Edit</td>
    </c:forEach>
    </tr>
</table><br/>

<%--Вывод ошибок идентификатора--%>
<c:set var="valErr" value="${requestScope.get('validationR')}"/>
<c:if test="${valErr != null && !valErr.isEmpty()}">
    <h4 align="center">Incorrect find ID</h4>
    <c:forEach items="${valErr}" var="err">
            <c:out value="${err}"/>
    </c:forEach>
</c:if>

<table align="center">
    <tr>
        <td>
            <form method="get" action="meals">
                <input type="submit" name="action" value="create" />
            </form>
        </td>
        <td>
            <form method="get" action="meals">
                <input type="submit" name="action" value="find"/>
                <input type="number" required="required" name="id" min="0"/>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
