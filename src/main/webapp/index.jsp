<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Інтернет-магазин журналів</title>
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>

    <h1>Ласкаво просимо в інтернет-магазин журналів!</h1>

    <form>
        <button type="button" onClick="location.href='register.jsp'">Зареєструватися</button>
        <button type="button" onClick="location.href='login.jsp'">Ввійти</button><br>
    </form>

    <jsp:include page="footer.jsp"></jsp:include>
</body>

</html>