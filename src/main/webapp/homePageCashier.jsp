<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="locale" />

<html lang = ${lang}>
<head>
<meta charset="ISO-8859-1">
<title>HomePageCashier</title>
<link rel="stylesheet" href = "css/homePage.css">
<link rel="stylesheet" href = "css/header.css">
<link rel="stylesheet" href = "css/paging.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>

<header>
  <form class = "left_header">
    <a href="#default" class="welcome"><fmt:message key="cash.welcome" />, ${user.login}</a>
    <a href="#default" class="welcome"><fmt:message key="cash.yourRole" /> ${user.role.name} </a>
  </form>
    <div class = "errorLabel" >
    <p>${error}</p>
    </div>
    <div class="header-right">
    <form action="/createReceipt" method="post">
      <button type="submit" class="createReceipt"><fmt:message key="cash.createReceipt" /></button>
    </form>
    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="en">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.EN" /></button>
    </form>
    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="ua">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.UA" /></button>
    </form>
    <form action="/logOut" method="get">
      <button type="submit" class="logOut" ><fmt:message key="cash.logOut" /></button>
    </form>
    </div>
  </header>


  <div id = "prods" class="container" style = ${visibilityProds}>
      <div id="listForm">
          <form action="/homePageCashier" method="get">
            <div class="container-search">
                </label for="searchReq" hidden="true">
                <input hidden="true" name="page" value="0">
                <input hidden="true" name="size" value=${pageSize}>
                <input id="search" placeholder=<fmt:message key="cash.search" /> type="search" name="searchReq">
                <button type= "submit" class="icon"><i class="fa fa-search"></i></button>
            </div>
          </form>

          <table class = "productList">
              <tr id = "titles">
                  <th><fmt:message key="cash.code" /></th>
                  <th><fmt:message key="cash.name" /></th>
                  <th><fmt:message key="cash.capacity" /></th>
                  <th><fmt:message key="cash.capType" /></th>
                  <th><fmt:message key="cash.price" /></th>
              </tr>

              <c:forEach var="product" items="${productList}">
                  <tr id = "rows" prId=${product.id}>
                      <td tdId="code">${product.code}</td>
                      <td tdId="name">${product.name}</td>
                      <td tdId="capacity">${product.capacity}</td>
                      <td tdId="capacityType">${product.capacityType}</td>
                      <td tdId="price">${product.price}</td>
                  </tr>
              </c:forEach>
          </table>
          <div class="paging-nav">
              <c:choose>
                  <c:when test="${empty searchReq}">
                      <c:set var = "search" value = ""/>
                  </c:when>
                  <c:otherwise>
                      <c:set var = "search" value = "&searchReq=${searchReq}"/>
                  </c:otherwise>
              </c:choose>
              <a href="/homePageCashier?page=0&size=${pageSize}${search}">1</a>
              <c:forEach begin = "1" end="${pageCount-1}" varStatus="loop">
                  <a href="/homePageCashier?page=${loop.index}&size=${pageSize}${search}">${loop.index+1}</a>
              </c:forEach>
          </div>
      </div>
  </div>


<div id = "receiptDiv" class="container" style=${visibilityReceipt}>
  <form id="contact" action="/addProductToReceipt" method="post">
    <h4><fmt:message key="cash.receiptId" /> ${receiptId}</h4>
    <h4><fmt:message key="cash.date" /> ${receipt.creationDateTime}</h4>
    <h4><fmt:message key="cash.owner" /> ${user.login}</h4>

    <table id="table" class = "receiptProducts">
        <tr id = "titlesReceipt">
            <th><fmt:message key="cash.code" /></th>
            <th><fmt:message key="cash.name" /></th>
            <th><fmt:message key="cash.capacity" /></th>
            <th><fmt:message key="cash.capType" /></th>
            <th><fmt:message key="cash.price" /></th>
        </tr>

      <c:forEach var="product" items="${receiptProds}">
        <tr id = "rowsReceipt">
            <td tdId="code">${product.code}</td>
            <td tdId="name">${product.name}</td>
            <td><input id="capacity" type="number" value=${product.capacity} min="0" max="1000" step="0.1"></td>
            <td tdId="capacityType">${product.capacityType}</td>
            <td tdId="price">${product.price}</td>
        </tr>
      </c:forEach>

    </table>
    <span id="val"></span>



    <input class = "receiptProd" type="text" value=${receiptId}  name="receiptId" style="display:none"/>
    <input class = "receiptProd" type="text" placeholder=<fmt:message key="cash.productNameOrCode" />  name="codeOrName"/>
    <input class = "receiptProd" type="text" placeholder=<fmt:message key="cash.capacity" /> name="capacity"/>
    <button type="submit" id="contact-submit" data-submit="...Sending"><fmt:message key="cash.add" /></button>

  </form>
  <form class = "closeReceipt" action="/closeReceipt" method="post">
          <input class = "receiptProd" type="text" value=${receiptId}  name="receiptId" style="display:none"/>
          <button type="submit" id="contact-submit" data-submit="...Sending"><fmt:message key="cash.closeReceipt" /></button>
      </form>
</div>

<script>

  var table = document.getElementById("table"), sumVal = 0;

  for(var i = 1; i < table.rows.length; i++)
  {
      sumVal = sumVal + parseFloat(table.rows[i].cells[4].innerHTML);
  }

  document.getElementById("val").innerHTML = <fmt:message key="senior.sum" /> + sumVal;
            console.log(sumVal);

$("input#capacity").change(function(){
 var elem = this.parentNode.parentNode;
  $.get("/updateProduct", {productCode: elem.children[0].innerText, capacity: this.value});
  setTimeout(function(){
      document.location.reload(true);
  },1000);
});

function showCreateForm() {
 document.getElementById("prods").style.display="none";
 document.getElementById("receiptDiv").style.display="block";
}
</script>

</body>
</html>