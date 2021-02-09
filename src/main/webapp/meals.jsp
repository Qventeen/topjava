<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: limegreen;
        }

        .excess {
            color: indianred;
        }
        .caption {
            color: cornflowerblue;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table align="center" border="2" cellpadding="7" cellspacing="0">
    <thead>
    <tr class="caption">
        <th>ID</th>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Deleting</th>
        <th>Editing</th>
    </tr>
    </thead>
    <jsp:useBean id="formatter" scope="application" type="java.time.format.DateTimeFormatter"/>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess? "excess":"normal" }">
            <td>${meal.id}</td>
            <td>${formatter.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&id=${meal.id}"/>Delete</td>
            <td><a href="meals?action=edit&id=${meal.id}"/>Edit</td>
        </tr>
    </c:forEach>
</table>
<br/>

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
                <input type="submit" name="action" value="create"/>
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
