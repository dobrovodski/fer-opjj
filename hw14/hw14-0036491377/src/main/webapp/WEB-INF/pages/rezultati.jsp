<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
   <body>
      <table border="1" cellspacing="0" class="rez">
         <thead>
            <td> Naslov </td>
            <td> Broj glasova </td>
         </thead>
         <tbody>
            <c:forEach var="option" items="${resultList}">
               <tr>
                  <td>${option.title}</td>
                  <td>${option.voteCount}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>
      <h2>Grafički prikaz rezultata</h2>
      <img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="500" height="500" />
      <h2>Rezultati u XLS formatu</h2>
      <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a></p>
      <h2>Razno</h2>
      <p>Linkovi na pobjednike glasanja:</p>
      <ul>
         <c:forEach var="option" items="${bestList}">
            <li><a href="${option.link}" target="_blank">${option.title}</a></li>
         </c:forEach>
      </ul>
      <a href="index.html">Click here to go back to the main page.</a>
   </body>
</html>