<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="locale" />

<html lang = ${lang}>
<head>
<meta charset="ISO-8859-1">
<title>HomePageCommodityExpert</title>
<link rel="stylesheet" href = "css/homePage.css">
<link rel="stylesheet" href = "css/header.css">
<link rel="stylesheet" href = "css/paging.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>

<header>
  <form class = "left_header">
    <a href="#default" class="welcome"><fmt:message key="comm.welcome" />, ${user.login}</a>
    <a href="#default" class="welcome"><fmt:message key="comm.yourRole" /> ${user.role.name} </a>
  </form>


    <div class = "errorLabel" >
        <p>${error}</p>
     </div>

    <div class="header-right">

      <button class="newProduct" onclick="showCreateForm()"><fmt:message key="comm.newProd" /></button>

    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="en">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.EN" /></button>
    </form>
    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="ua">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.UA" /></button>
    </form>
    <form action="/logOut" method="get">
      <button type="submit" class="logOut" ><fmt:message key="comm.logOut" /></button>
    </form>
    </div>
  </header>


<div id = "prods" class="container" style = "display: block;">
    <div id="listForm">
        <form action="/homePageCommodityExpert" method="get">
          <div class="container-search">
              </label for="searchReq" hidden="true">
              <input hidden="true" name="page" value="0">
              <input hidden="true" name="size" value=${pageSize}>
              <input id="search" placeholder=<fmt:message key="comm.search" /> type="search" name="searchReq">
              <button type= "submit" class="icon"><i class="fa fa-search"></i></button>
          </div>
        </form>

        <table class = "productList">
            <tr id = "titles">
                <th><fmt:message key="comm.code" /></th>
                <th><fmt:message key="comm.name" /></th>
                <th><fmt:message key="comm.capacity" /></th>
                <th><fmt:message key="comm.capType" /></th>
                <th><fmt:message key="comm.price" /></th>
            </tr>

            <c:forEach var="product" items="${productList}">
                <tr id = "rows" prId=${product.id}>
                    <td tdId="code">${product.code}</td>
                    <td tdId="name">${product.name}</td>
                    <td tdId="capacity">${product.capacity}</td>
                    <td tdId="capacityType">${product.capacityType}</td>
                    <td tdId="price">${product.price}</td>
                    <td><a id="${product.id}" onClick="getById(this)"><fmt:message key="comm.update" /></a></td>
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
            <a href="/homePageCommodityExpert?page=0&size=${pageSize}${search}">1</a>
            <c:forEach begin = "1" end="${pageCount-1}" varStatus="loop">
                <a href="/homePageCommodityExpert?page=${loop.index}&size=${pageSize}${search}">${loop.index+1}</a>
            </c:forEach>
        </div>
    </div>
</div>

<div id = "addProd" class="container" style = "display: none;">
  <form formId = "formId" id="contact" action="/createProduct" method="post">
      <input type="text" placeholder=<fmt:message key="comm.code" />  name="code"/>
      <input type="text" placeholder=<fmt:message key="comm.name" /> name="name"/>
      <input type="text" placeholder="<fmt:message key="comm.capType" />"  name="capacityType"/>
      <input type="text" placeholder=<fmt:message key="comm.capacity" /> name="capacity"/>
      <input type="text" placeholder=<fmt:message key="comm.price" /> name="price"/>
      <button  btnUpdate = "btnUpdate"  name="submit" type="submit" id="contact-submit" data-submit="...Sending"><fmt:message key="comm.create" /></button>
  </form>
  <button class="button" onClick="goBack()"><fmt:message key="comm.back" /></button>

</div>

<script>
function goBack() {
  document.getElementById("prods").style.display="block";
   document.getElementById("addProd").style.display="none";
}

function showCreateForm() {
 document.getElementById("prods").style.display="none";
 document.getElementById("addProd").style.display="block";
 document.querySelector("[name=code]").value="";
 document.querySelector("[name=name]").value="";
 document.querySelector("[name=capacity]").value="";
 document.querySelector("[name=capacityType]").value="";
 document.querySelector("[name=price]").value="";
 document.querySelector("[btnUpdate=btnUpdate]").innerText = "<fmt:message key="comm.create" />";
 document.querySelector("[formId=formId]").action = "/createProduct";
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
 document.querySelector("[btnUpdate=btnUpdate]").innerText = "<fmt:message key="comm.update" />";
 document.querySelector("[formId=formId]").action = "/updateProduct?id="+elem.id;
}
</script>
</body>
</html>