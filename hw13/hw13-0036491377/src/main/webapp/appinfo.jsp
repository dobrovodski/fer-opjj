<%@ page import="java.util.Date" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style> body {	background-color: ${bgColor};}</style>
	<%
            long uptime = new Date().getTime() - (long)getServletContext().getAttribute("serverStartTime");
            long secs = (long) (uptime / 1000) % 60;
            long minutes = (long) (secs / 60) % 60;
            long hours = (long) (minutes / 60) % 24;
            long days = (long) (hours / 24);
            %>
   <body>
        <h1> Server uptime </h1>
        <p> <%=days%> days, <%=hours%> hours, <%=minutes%> minutes, <%=secs%> seconds </p><br>
        <a href="index.jsp">MAIN PAGE</a>
   </body>
</html>
