<%--
  Created by IntelliJ IDEA.
  User: jumin
  Date: 2024. 8. 13.
  Time: 오후 4:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Todo List</h1>
    <a href="/todo/register">등록하러 가기</a>
    ${name}
    <ul>
        <c:forEach items="${todos}" var="todo">
            <li>${todo}</li>
        </c:forEach>
    </ul>
</body>
</html>
