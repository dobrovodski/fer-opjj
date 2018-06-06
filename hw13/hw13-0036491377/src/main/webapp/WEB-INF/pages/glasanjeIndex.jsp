<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <h1>Glasanje za omiljeni bend:</h1>
    <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
    glasali!</p>
    <c:forEach var="band" items="${bandList}">
    <a href="glasanje-glasaj?id=${band.id}">${band.name}</a><br>
    </c:forEach>
    <br>
    <a href="index.jsp">MAIN PAGE</a>
</body>
</html>