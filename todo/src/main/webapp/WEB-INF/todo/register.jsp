<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 13.
  Time: 오후 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register</h1>
    <form action="/todo/register" method="post">
        <input type="text" placeholder="제목" name="title">
        <button type="submit">등록</button>

    </form>
</body>
</html>
