<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style>
    @import url('https://fonts.googleapis.com/css?family=Noto Sans:400,700');

    * {
      box-sizing: border-box;
      line-height: 1.1;
    }

    br {
      display: none;
    }

    body {
      margin: 0;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      height: 100vh;
      width: 100vw;
      background-color: #ebebeb;
      color: #444;
      font-family: 'Noto Sans', sans-serif;
    }

    body:before {
      box-sizing: border-box;
      content: '';
      height: 96px;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      padding: 0;
      background-color: white;
      box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.1);
      display: flex;
      flex-direction: row;
      align-items: center;
      font-size: 3em;
    }

    a {
      color: #ee5253;
    }

    /**/

    b {
      font-size: 2rem;
      padding: 16px;
      background-color: #444;
      color: white;
      max-width: 500px;
      width: 100%;
    }

    ul {
      max-width: 500px;
      width: 100%;
      list-style-type: none;
      margin: 0;
      padding: 0;
    }

    li {
      padding: 16px 32px;
      background-color: white;
      width: 100%;
      margin-bottom: 8px;
      color: #aaa;
      border-left: 10px solid #ee5253;
      box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
    }

    li:first-child {
      margin-top: 8px;
    }

    li a {
      max-width: 500px;
      width: 100%;
      padding: 16px 32px;
      background-color: white;
      border-left: 10px solid #bbbbbb;
      box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
    }

    li a {
      max-width: initial;
      width: initial;
      padding: 0;
      background-color: transparent;
      border: none;
    }

    .home {
    position: absolute;
    top: 0;
    left: 0;
    font-size: 2rem;
    height: 96px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 32px;
    }

    .logout {
    position: absolute;
    right: 0;
    top: 0;
    height: 96px;
    padding: 0 32px;

    display: flex;
    justify-content: center;
    align-items: center;
    }

    .logout a {
        margin-left: 16px;
    }


</style>

<body>

    <c:choose>
      <c:when test="${entries==null}">
        Ovaj korisnik nema blog zapisa.
      </c:when>
      <c:otherwise>
        <b>Popis blogova korisnika ${nick}:</b>
        <ul>
        <c:forEach var="e" items="${entries}">
          <li><a href="${e.creator.nick}/${e.id}">${e.title}</a> ${e.createdAt}
          </li>
        </c:forEach>
        </ul>
      </c:otherwise>
    </c:choose>

  <c:if test = "${sessionScope['current.user.nick'] == nick}">
  <a href='${nick}/new'>New</a>
  </c:if>

  <a href="${pageContext.request.contextPath}/index.jsp" class="home">Home</a>

  <c:if test="${not empty sessionScope['current.user.id']}">
    <div class="logout">
    <span>${sessionScope['current.user.nick']}</span>
    <a href='${pageContext.request.contextPath}/servleti/logout'>Logout</a>
    </div>
  </c:if>
</body>

</html>