<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HomePageCommodityExpert</title>
<link rel="stylesheet" href = "css/homePage.css">
<link rel="stylesheet" href = "css/header.css">

</head>
<body>

<header>
    <a href="#default" class="welcome">Welcome, ${user.login}</a>
    <div class="header-right">

      <button class="newProduct" onclick="showCreateForm()">New product</button>

    <form action="/changeLanguage" method="post">
      <button type="submit" class="changeLanguage" >Change Language</button>
    </form>
    <form action="/logOut" method="post">
      <button type="submit" class="logOut" >Log out</button>
    </form>
    </div>
  </header>


<div id = "prods" class="container" style = "display: block;">
  <form id="listForm" action="/homePageCommodityExpert" method="post">
    <table class = "productList">
        <tr id = "titles">
            <th>Code</th>
            <th>Name</th>
            <th>Capacity</th>
            <th>CapacityType</th>
            <th>Price</th>
        </tr>

        <c:forEach var="product" items="${productList}">
            <tr id = "rows">
                <td>${product.code}</td>
                <td>${product.name}</td>
                <td>${product.capacity}</td>
                <td>${product.capacityType}</td>
                <td>${product.price}</td>
            </tr>
        </c:forEach>
    </table>
  </form>
</div>

<div id = "addProd" class="container" style = "display: none;">
  <form id="contact" action="/createProduct" method="post">
      <input type="text" placeholder="Code"  name="code"/>
      <input type="text" placeholder="Name" name="name"/>
      <input type="text" placeholder="CapacityType"  name="capacityType"/>
      <input type="text" placeholder="Capacity" name="capacity"/>
      <input type="text" placeholder="Price" name="price"/>
      <button>Create Product</button>
  </form>
</div>

<script>
function showCreateForm() {
 document.getElementById("prods").style.display="none";
 document.getElementById("addProd").style.display="block";
}
</script>
</body>
</html>