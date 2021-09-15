<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HomePageCashier</title>
<link rel="stylesheet" href = "css/homePage.css">
<link rel="stylesheet" href = "css/header.css">

</head>
<body>

<header>
    <a href="#default" class="welcome">Welcome, ${user.login}</a>
    <div class="header-right">
    <form action="/createReceipt" method="post">
      <button type="submit" class="createReceipt" onclick="showForm()" >Create Receipt</button>
    </form>
    <form action="/changeLanguage" method="post">
      <button type="submit" class="changeLanguage" >Change Language</button>
    </form>
    <form action="/logOut" method="post">
      <button type="submit" class="logOut" >Log out</button>
    </form>
    </div>
  </header>


<div class="container" style=${visibility}>
  <form id="contact" action="/homePageCashier" method="post">
    <h4>Receipt id: ${receiptId}</h4>
    <h4>Date of creation: ${receipt.creationDateTime}</h4>
    <h4>Owner: ${user.login}</h4>
    <fieldset>
      <input placeholder="Add product" type="email" tabindex="2" required>
    </fieldset>
    <fieldset>
      <button name="submit" type="submit" id="contact-submit" data-submit="...Sending">Submit</button>
    </fieldset>
  </form>
</div>
</body>
</html>