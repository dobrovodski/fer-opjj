<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <style type="text/css">
            body {	background-color: ${bgColor};}
            table.rez td {text-align: center;}
        </style>
    </head>
    <body>
        <table border="1" cellspacing="0" class="rez">
            <thead>
                <td> Naziv </td>
                <td> Glasova </td>
            </thead>
            <tbody>
                <c:forEach var="u" items="${resultList}">
                    <tr>
                        <td>${u.name}</td>
                        <td>${u.voteCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h2>Grafički prikaz rezultata</h2>
        <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
        <h2>Rezultati u XLS formatu</h2>
        <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
        <h2>Razno</h2>
        <p>Primjeri pjesama pobjedničkih bendova:</p>
        <ul>
            <li><a href="https://www.youtube.com/watch?v=z9ypq6_5bsg" target="_blank">The Beatles</a>
            </li>
            <li><a href="https://www.youtube.com/watch?v=H2di83WAOhU" target="_blank">The
                Platters</a>
            </li>
        </ul>
    </body>
</html>