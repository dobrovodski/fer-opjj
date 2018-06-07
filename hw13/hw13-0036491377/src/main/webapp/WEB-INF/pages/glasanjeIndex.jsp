<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="style.css">
      <style> body {	background-color: ${bgColor};}</style>
   </head>
   <body>
      <h1>Glasanje za omiljeni bend:</h1>
      <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
         glasali!
      </p>
      <c:forEach var="entry" items="${bandMap}">
         <a href="glasanje-glasaj?id=${entry.value.id}">${entry.value.name}</a><br>
      </c:forEach>
      <br>
      <a href="index.jsp">Click here to go back to the main page.</a>
   </body>
</html>