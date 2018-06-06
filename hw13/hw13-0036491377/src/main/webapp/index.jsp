<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
	<style> body {	background-color: ${bgColor};}</style>
<body>
	<a href="colors.jsp">Background color chooser</a><br>
	<a href="trigonometric?a=0&b=90">Trigonometric table</a><br>
	<form action="trigonometric" method="GET">
        Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
        Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
        <input type="submit" value="Table"><input type="reset" value="Reset">
    </form><br>
    <a href="story">Funny_story.exe</a>
	<a href="report.jsp">OS Usage report</a><br>
</body>
</html>
