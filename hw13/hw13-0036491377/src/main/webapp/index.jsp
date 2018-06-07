<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
   <link rel="stylesheet" type="text/css" href="style.css">
   <style> body {	background-color: ${bgColor};}</style>
   <body>
      <p><a href="colors.jsp">Background color chooser</a></p>
      <br>
      <p><a href="trigonometric?a=0&b=90">Trigonometric table</a><br>
      <form action="trigonometric" method="GET">
         Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
         Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
         <input type="submit" value="Table"><input type="reset" value="Reset">
      </form>
      </p><br>
      <p><a href="story">Funny_story.exe</a></p>
      <br>
      <p><a href="report.jsp">OS Usage report</a></p>
      <br>
      <p><a href="powers?a=1&b=100&n=3">Calculate powers</a><br>
      <form action="powers" method="GET">
         Početni broj:<br><input type="number" name="a" min="-100" max="100" step="1" value="0"><br>
         Završni broj:<br><input type="number" name="b" min="-100" max="100" step="1" value="10"><br>
         Broj stranica:<br><input type="number" name="n" min="1" max="8" step="1" value="1"><br>
         <input type="submit" value="Excel"><input type="reset" value="Reset">
      </form>
      </p><br>
      <p><a href="appinfo.jsp">Server stats</a></p>
      <br>
      <p><a href="glasanje">Glasanje</a></p>
   </body>
</html>