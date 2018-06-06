<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style> body {	background-color: ${bgColor};}</style>
   <body>
        <table>
        <thead>
        <td> Value </td>
        <td> Cos </td>
        <td> Sin </td>
        </thead>
        <tbody>
        <c:forEach var="v" items="${values}">
            <tr>
            <td>"${v.value}"</td>
            <td>"${v.cos}"</td>
            <td>"${v.sin}"</td></tr>
        </c:forEach>
        </tbody>
        </table>
        <a href="index.jsp">MAIN PAGE</a>
   </body>
</html>
