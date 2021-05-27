<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <h1>Custom Login Page</h1>
    <h2><c:out value="${error}"/></h2>
    <h2><c:out value="${logout}"/></h2>

    <form method='post' action="/customLogout">
        <input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button>로그아웃</button>
    </form>
</body>
</html>