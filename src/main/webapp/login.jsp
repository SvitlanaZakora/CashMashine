<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<div class="Login">
    <div class="form">
        <form action="/login" method="post">
            <input type="text" placeholder="login" name="login"/>
            <input type="password" placeholder="pass" name="pass"/>
            <input type="submit" value="Submit" />
        </form>
    </div>
</div>
</body>
</html>