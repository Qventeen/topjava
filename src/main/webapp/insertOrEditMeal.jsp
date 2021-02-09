<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:out value="${action}"/> meal
    </title>
</head>
<body>

<%--Вывод результата валидации заполнения формы--%>
<c:set var="valErr" scope="request" value="${requestScope.get('validationR')}"/>
<c:if test='${valErr != null && !valErr.isEmpty()}'>
    <div align="center"><h3>Form parameters is not correct</h3>
    <c:forEach items="${valErr}" var="item">
        <c:out value="${item}"/><br/>
    </c:forEach>
    </div>
</c:if>

<table align="center">
<form method="post" action="meals">
    <tr>
        <td>ID:</td>
        <td><input type="text" name="id" value="${meal.id}" readonly  aria-hidden="true" /><br/></td>
    </tr>
    <tr>
        <td>Date/time:</td>
        <td><input type="datetime-local"   name="dateTime" value="${meal.dateTime}" /><br/></td>
    </tr>
    <tr>
        <td>Description:</td>
        <td><input type="text" name="description" value="${meal.description}"/><br/></td>
    </tr>
    <tr>
        <td>Calories:</td>
        <td><input type="text" name="calories" value="${meal.calories}" min="0"  max="5000"/><br/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit"/></td>
    </tr>
</form>
</table>
</body>
</html>
