<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<section>
    <form method="post" action="meals" name="formAddUser">
        <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" name="description" size=50 value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Time:</dt>
            <dd><input type="datetime-local" name="time" size=50 value="${meal.dateTime}"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="text" name="calories" size=50 value="${meal.calories}"></dd>
        </dl>
        <input type="submit" value="Submit"/>
    </form>
</section>
</body>
</html>
