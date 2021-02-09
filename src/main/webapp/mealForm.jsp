<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:out value="${action}"/> meal
    </title>
</head>
<body>

<table align="center">
<form method="post" action="meals">
    <tr>
        <td>ID:</td>
        <td><input type="number" name="id" value="${meal.id}" readonly  aria-hidden="true" /><br/></td>
    </tr>
    <tr>
        <td>Date/time:</td>
        <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}" /><br/></td>
    </tr>
    <tr>
        <td>Description:</td>
        <td><input type="text" name="description" value="${meal.description}"/><br/></td>
    </tr>
    <tr>
        <td>Calories:</td>
        <td><input type="number" name="calories" value="${meal.calories}" min="0"  max="5000"/><br/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit"/></td>
    </tr>
</form>
</table>
</body>
</html>
