<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <body>
      <h1>${poll.title}</h1>
      <p>${poll.message}</p>
      <c:forEach var="option" items="${pollOptions}">
         ${option.id}. <a href="odabir?pollID=${option.pollId}&optionID=${option.id}">${option.title}</a><br>
      </c:forEach>
      <br>

      <a href="index.html">Click here to go back to the main page.</a>
   </body>
</html>
