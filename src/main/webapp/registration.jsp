<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
<div class="Registration">
    <div class="form">
        <form action="/registration" method="post">
            <input type="text" placeholder="login" name="login"/>
            <input type="password" placeholder="pass" name="pass"/>
            <input type="text" placeholder="role" name="role"/>
            <input type="submit" value="Submit" />
        </form>
    </div>
</div>
</body>
</html>