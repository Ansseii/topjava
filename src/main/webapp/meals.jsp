<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<jsp:useBean id="meals" scope="request" type="java.util.List"/>
<c:set var="mealList" value="${meals}"/>

<table border="1" cellpadding="8" cellspacing="0">
    <a href="meals?action=add"><button type="button" class="btn btn-info">Add</button></a>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style="${meal.exceed ? "color: red;":"color: green;"}">
            <td><a><javatime:format value="${meal.dateTime}"/></a></td>
            <td><a>${meal.description}</a></td>
            <td><a>${meal.calories}</a></td>
            <td><a href="meals?id=${meal.id}&action=delete"><button type="button" class="btn btn-info">Delete</button></a></td>
            <td><a href="meals?id=${meal.id}&action=update"><button type="button" class="btn btn-info">Update</button></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
