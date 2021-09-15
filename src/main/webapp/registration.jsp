<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login-Registration</title>
<link rel="stylesheet" href = "css/registration.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <div class="login-page">
      <div class="form">
        <form class="register-form" action="/registration" method="post">
          <input type="text" placeholder="login"  name="login"/>
          <input type="password" placeholder="password" name="pass"/>
          <select name="role" required>
              <option value="CASHIER">CASHIER</option>
              <option value="SENIOR_CASHIER">SENIOR_CASHIER</option>
              <option value="COMMODITY_EXPERT">COMMODITY_EXPERT</option>
             </select>
          <button>create</button>
          <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" action="/login" method="post">
          <input type="text" placeholder="username" name="login"/>
          <input type="password" placeholder="password" name="pass"/>
          <button>login</button>
          <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
      </div>
    </div>
    <script src="js/registration.js"></script>
</body>
</html>