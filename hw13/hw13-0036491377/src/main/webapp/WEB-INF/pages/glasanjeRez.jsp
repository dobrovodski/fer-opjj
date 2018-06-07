<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="style.css">
      <style type="text/css">
         body {	background-color: ${bgColor};}
         table.rez td {text-align: center;}
      </style>
   </head>
   <body>
      <table border="1" cellspacing="0" class="rez">
         <thead>
            <td> Naziv </td>
            <td> Glasova </td>
         </thead>
         <tbody>
            <c:forEach var="band" items="${resultList}">
               <tr>
                  <td>${band.name}</td>
                  <td>${band.voteCount}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>
      <h2>Grafički prikaz rezultata</h2>
      <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
      <h2>Rezultati u XLS formatu</h2>
      <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
      <h2>Razno</h2>
      <p>Primjeri pjesama pobjedničkih bendova:</p>
      <ul>
         <c:forEach var="band" items="${bestList}">
            <li><a href="${band.link}" target="_blank">${band.name}</a></li>
         </c:forEach>
      </ul>
      <a href="index.jsp">Click here to go back to the main page.</a>
   </body>
</html>