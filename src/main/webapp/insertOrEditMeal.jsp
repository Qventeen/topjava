<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:out value="${requestScope.get('action')}"/> meal
    </title>
</head>
<body>

<%--Вывод результата валидации заполнения формы--%>
<c:set var="valErr" value="${requestScope.get('validationR')}"/>
<c:if test='${valErr != null && !valErr.isEmpty()}'>
    <h3>Form parameters is not correct</h3>
    <c:forEach items="${valErr}" var="item">
        <div>${item}<br/></div>
    </c:forEach>
</c:if>

<table align="center">
<form method="post" action="meals">
    <tr>
        <td>Date/time:</td>
        <td><input type="datetime-local"  name="dateTime" value="${meal.dateTime}" /><br/></td>
    </tr>
    <tr>
        <td>Description:</td>
        <td><input type="text" name="description" value="${meal.description}"/><br/></td>
    </tr>
    <tr>
        <td>Calories:</td>
        <td><input type="number" name="calories" value="${meal.calories}"  min="0" max="5000"/><br/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>
</form>
</table>
</body>
</html>
