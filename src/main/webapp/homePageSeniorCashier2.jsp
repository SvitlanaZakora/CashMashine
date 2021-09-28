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

</head>
<body>

<header>
  <form class = "left_header">
    <a href="#default" class="welcome"><fmt:message key="senior.welcome" />, ${user.login}</a>
    <a href="#default" class="welcome"><fmt:message key="senior.yourRole" /> ${user.role.name} </a>
  </form>

   <div class = "errorLabel" >
      <p>${error}</p>
   </div>

    <div class="header-right">
    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="en">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.EN" /></button>
    </form>
    <form action="/changeLanguage" method="get">
      <input hidden="true" name="lang" value="ua">
      <button type="submit" class="changeLanguage" ><fmt:message key="cash.UA" /></button>
    </form>
    <form action="/logOut" method="get">
      <button type="submit" class="logOut" ><fmt:message key="senior.logOut" /></button>
    </form>
    </div>
  </header>


  <div id = "receipts" class="container" style=${visibilityReceipts}>
      <div id="listForm">
          <table class = "receiptList">
              <tr id = "titles">
                  <th><fmt:message key="senior.num" /></th>
                  <th><fmt:message key="senior.date" /></th>
                  <th><fmt:message key="senior.owner" /></th>
                  <th><fmt:message key="senior.status" /></th>
              </tr>

              <c:forEach var="receipt" items="${receiptList}">
                  <tr id = "rows" recId=${receipt.id}>
                      <td tdId="id">${receipt.id}</td>
                      <td tdId="DateOfCreation">${receipt.creationDateTime}</td>
                      <td tdId="Owner">${receipt.ownerName}</td>
                      <td tdId="Status">${receipt.active ? "active" : "close"}</td>
                      <td><a href= "/updateReceipt?recId=${receipt.id}" ><fmt:message key="senior.update" /></a></td>
                      <td><a href= "/deleteReceipt?recId=${receipt.id}" ><fmt:message key="senior.delete" /></a></td>
                  </tr>
              </c:forEach>
          </table>
          <div class="paging-nav">
              <a href="/homePageSeniorCashier?page=0&size=${pageSize}">1</a>
              <c:forEach begin = "1" end="${pageCount-1}" varStatus="loop">
                  <a href="/homePageSeniorCashier?page=${loop.index}&size=${pageSize}">${loop.index+1}</a>
              </c:forEach>
          </div>
      </div>
  </div>

  <div id = "updReceipt" class="container" style=${visibilityUpdateReceipt}>
    <form formId = "formId" id="contact" action="/createProduct" method="post">
        <h4><fmt:message key="cash.receiptId" /> ${receipt.id}</h4>
        <h4><fmt:message key="cash.date" /> ${receipt.creationDateTime}</h4>
        <h4><fmt:message key="cash.owner" /> ${receipt.ownerName}</h4>

        <table id="table" class = "receiptProducts">
            <tr id = "titlesReceipt">
                <th><fmt:message key="cash.code" /></th>
                <th><fmt:message key="cash.name" /></th>
                <th><fmt:message key="cash.capacity" /></th>
                <th><fmt:message key="cash.name" /></th>
                <th><fmt:message key="cash.price" /></th>
            </tr>

          <c:forEach var="product" items="${receiptProducts}">
            <tr id = "rowsReceipt">
                <td tdId="code">${product.code}</td>
                <td tdId="name">${product.name}</td>
                <td tdId="capacity">${product.capacity}</td>
                <td tdId="capacityType">${product.capacityType}</td>
                <td tdId="price">${product.price}</td>
                <td><a href= "/deleteProductFromReceipt?recId=${receipt.id}&prodId=${product.id}" id="${product.id}"><fmt:message key="senior.delete" /></a></td>
            </tr>
          </c:forEach>

        </table>
        <span id="val"></span>
    </form>
    <button class="button" onClick="goBack()"><fmt:message key="senior.back" /></button>

  </div>

 <script>

 var table = document.getElementById("table"), sumVal = 0;

   for(var i = 1; i < table.rows.length; i++)
   {
       sumVal = sumVal + parseFloat(table.rows[i].cells[4].innerHTML);
   }

   document.getElementById("val").innerHTML = <fmt:message key="senior.sum" /> + sumVal;
   console.log(sumVal);

  function goBack() {
    ${receipt=null}
    document.getElementById("receipts").style.display="block";
     document.getElementById("updReceipt").style.display="none";
  }
 </script>
</body>
</html>