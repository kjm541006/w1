<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 26.
  Time: 오후 5:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo Read</title>
</head>
<body>
<div><input type="text" name="tno" value="${dto.tno}" readonly></div>
<div><input type="text" name="title" value="${dto.title}" readonly></div>
<div><input type="date" name="dueDate" value="${dto.dueDate}"></div>
<div><input type="checkbox" name="completed" ${dto.completed ? "checked" : ""} readonly></div>
<div>
    <a href="/todo/modify?tno=${dto.tno}">Modify/Remove</a>
    <a href="/todo/list">List</a>
</div>
</body>
</html>
