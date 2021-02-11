<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <table>
            <tr>
                <td>DateTime:</td>
                <td><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></td><br/>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" value="${meal.description}" size=40 name="description" required></td><br/>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="number" value="${meal.calories}" name="calories" required></td><br/>
            </tr>
            <button type="submit">Save</button>
            <button onclick="window.history.back()">Cancel</button>
        </table>
    </form>
</section>
</body>
</html>
