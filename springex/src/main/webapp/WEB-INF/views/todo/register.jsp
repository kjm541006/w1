<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 30.
  Time: 오후 4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="/todo/register" method="post">
    <div>Title: <input type="text" name="title"></div>
    <div>DueDate: <input type="date" name="dueDate" value="2024-08-30"> </div>
    <div>Writer: <input type="text" name="writer"></div>
    <div>Completed: <input type="checkbox" name="completed"></div>
    <div><button type="submit">Register</button></div>
</form>
</body>
</html>
