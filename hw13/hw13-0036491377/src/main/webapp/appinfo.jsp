<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <link rel="stylesheet" type="text/css" href="style.css">
   <style> body {	background-color: ${bgColor};}</style>
   <%
      long uptime = System.currentTimeMillis() - (long)getServletContext().getAttribute("serverStartTime");
      long secs = (long) (uptime / 1000.0);
      long minutes = (long) (secs / 60.0);
      long hours = (long) (minutes / 60.0) ;
      long days = (long) (hours / 24.0);
      %>
   <body>
      <h1> Server uptime </h1>
      <p> <%=days%> days, <%=hours % 24%> hours, <%=minutes % 60%> minutes, <%=secs % 60%> seconds </p>
      <br>
      <a href="index.jsp">Click here to go back to the main page.</a>
   </body>
</html>