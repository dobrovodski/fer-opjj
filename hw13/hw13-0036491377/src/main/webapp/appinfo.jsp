<%@ page import="java.util.Date" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<style> body {	background-color: ${bgColor};}</style>
	<%
            long uptime = new Date().getTime() - (long)getServletContext().getAttribute("serverStartTime");
            long sec = (long)(uptime / 1000) % 60;
            long minutes = (long)((uptime / (1000*60)) % 60);
            long hours = (long)((uptime / (1000*60*60)) % 24);
            %>
   <body>
        <p> <%=hours%> hours, <%=minutes%> minutes, <%=sec%> seconds </p>
        <a href="index.jsp">MAIN PAGE</a>
   </body>
</html>
