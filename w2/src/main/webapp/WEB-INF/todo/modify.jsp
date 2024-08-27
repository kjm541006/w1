<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 27.
  Time: 오후 3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Modify/Remove </title>
</head>
<body>
<form action="/todo/modify" method="post">
    <div><input type="text" name="tno" value="${dto.tno}" readonly></div>
    <div><input type="text" name="title" value="${dto.title}"></div>
    <div><input type="date" name="dueDate" value="${dto.dueDate}"></div>
    <div>completed: <input type="checkbox" name="completed" ${dto.completed ? "checked" : ""} ></div>
    <div><button type="submit">Modify</button></div>
</form>

<form action="/todo/remove" method="post">
    <input type="hidden" name="tno" value="${dto.tno}" readonly>
    <div>
        <button type="submit">Remove</button>
    </div>
</form>
</body>
</html>
