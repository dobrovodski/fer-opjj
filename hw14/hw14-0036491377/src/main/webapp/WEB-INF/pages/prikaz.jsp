<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <body>
      <h2> List of all active polls: </h2>
      <c:forEach var="poll" items="${polls}">
         <a href="glasanje?pollID=${poll.id}">${poll.title}</a> ${poll.message}<br><br>
      </c:forEach>
      <br>
   </body>
</html>