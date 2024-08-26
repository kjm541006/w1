<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 26.
  Time: 오후 5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="/todo/register" method="POST">
    <div><input type="text" name="title" placeholder="INSERT TITLE"></div>
    <div><input type="date" name="dueDate"></div>
    <div>
        <button type="reset">RESET</button>
        <button type="submit">REGISTER</button>
    </div>
</form>
</body>
</html>
