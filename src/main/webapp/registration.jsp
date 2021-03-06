<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="locale" />

<html lang = ${lang}>
<head>
    <title>Login-Registration</title>
    <link rel="stylesheet" href = "css/registration.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
      <div class = "errorLabel" >
        <p>${error}</p>
      </div>

    <div class="login-page">
      <div class="form">
        <form class="register-form" action="/registration" method="post">
          <input type="text" placeholder=<fmt:message key="login.username" />  name="login"/>
          <input type="password" placeholder=<fmt:message key="login.password" /> name="pass"/>
          <select name="roleId" required>
            <c:forEach var="role" items="${roleList}">
                <option value=${role.id} >${role.name}</option>
            </c:forEach>
             </select>
          <button><fmt:message key="reg.create" /></button>
          <p class="message"><fmt:message key="reg.mess" /> <a href="#"><fmt:message key="reg.href" /></a></p>
        </form>
        <form class="login-form" action="/login" method="post">
          <input type="text" placeholder=<fmt:message key="login.username" /> name="login"/>
          <input type="password" placeholder=<fmt:message key="login.password" /> name="pass"/>
          <button><fmt:message key="login.login" /></button>
          <p class="message"><fmt:message key="login.mess" /><a href="#"><fmt:message key="login.href" /></a></p>
        </form>
      </div>
    </div>
    <form class="lang" action="/changeLanguage" method="get">
          <input hidden="true" name="lang" value="en">
          <button type="submit" class="changeLanguage" ><fmt:message key="cash.EN" /></button>
        </form>
        <form class="lang" action="/changeLanguage" method="get">
          <input hidden="true" name="lang" value="ua">
          <button type="submit" class="changeLanguage" ><fmt:message key="cash.UA" /></button>
        </form>
    <script src="js/registration.js"></script>
</body>
</html>