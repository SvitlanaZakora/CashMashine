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
  <div id="listForm">
    <table class = "productList">
        <tr id = "titles">
            <th>Code</th>
            <th>Name</th>
            <th>Capacity</th>
            <th>CapacityType</th>
            <th>Price</th>
        </tr>

        <c:forEach var="product" items="${productList}">
            <tr id = "rows" prId=${product.id}>
                <td tdId="code">${product.code}</td>
                <td tdId="name">${product.name}</td>
                <td tdId="capacity">${product.capacity}</td>
                <td tdId="capacityType">${product.capacityType}</td>
                <td tdId="price">${product.price}</td>
                <td><a id="${product.id}" onClick="getById(this)">Update</a></td>
            </tr>
        </c:forEach>
    </table>
  </div>
</div>

<div id = "addProd" class="container" style = "display: none;">
  <form formId = "formId" id="contact" action="/createProduct" method="post">
      <input type="text" placeholder="Code"  name="code"/>
      <input type="text" placeholder="Name" name="name"/>
      <input type="text" placeholder="CapacityType"  name="capacityType"/>
      <input type="text" placeholder="Capacity" name="capacity"/>
      <input type="text" placeholder="Price" name="price"/>
      <button  btnUpdate = "btnUpdate"  name="submit" type="submit" id="contact-submit" data-submit="...Sending">Create Product</button>
  </form>
  <button class="button" onClick="goBack()">Go back</button>

</div>

<script>
function goBack() {
  document.getElementById("prods").style.display="block";
   document.getElementById("addProd").style.display="none";
}

function showCreateForm() {
 document.getElementById("prods").style.display="none";
 document.getElementById("addProd").style.display="block";
}

function getById(elem) {
 document.getElementById("prods").style.display="none";
 document.getElementById("addProd").style.display="block";
 var productRow = elem.parentNode.parentNode;
 document.querySelector("[name=code]").value=productRow.children[0].innerText;
 document.querySelector("[name=name]").value=productRow.children[1].innerText;
 document.querySelector("[name=capacity]").value=productRow.children[2].innerText;
 document.querySelector("[name=capacityType]").value=productRow.children[3].innerText;
 document.querySelector("[name=price]").value=productRow.children[4].innerText;
 document.querySelector("[btnUpdate=btnUpdate]").innerText = "Update Product";
 document.querySelector("[formId=formId]").action = "/updateProduct?id="+elem.id;
}
</script>
</body>
</html>