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
    content: 'Blog.me';
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


  body > a:last-child {
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

  /**/

  form {
    max-width: 500px;
    width: 100%;
    height:900px;
    padding: 32px;
    margin-top:90px;
    background-color: white;
    display: flex;
    flex-direction: column;
    font-size: 2em;
    box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
  }

   textarea, input[type="text"], input[type="password"]  {
    font-size: 1rem;
    margin: 0;
    margin-top: 8px;
    margin-bottom: 16px;
    border-radius: 8px;
    border: none;
    padding: 16px;
    background-color: #f4f4f4;
  }

  input[type="submit"] {
    background-color: #A3CB38;
    color: white;
    font-weight: bold;
    padding: 8px;
    border-radius: 8px;
    border: none;
    outline: none;
    font-size: 1.25rem;
    text-transform: uppercase;
    max-width: 200px;
    width: 100%;
    align-self: flex-end;
  }

  input[type="submit"]:hover {
    background-color: #C4E538;
  }

  textarea {
    margin-bottom: 16px;
  }

  a {
    width: 100%;
    max-width: 200px;
    padding: 12px 8px;
    border-radius: 8px;
    background-color: #ee5253;
    color: white;
    text-align: center;
    margin: 8px;
  }

  body > div:first-of-type {
    position: absolute;
    right: 0;
    top: 0;
    height: 96px;
    padding: 0 32px;

    display: flex;
    justify-content: center;
    align-items: center;
  }

  body > div:first-of-type a {
    margin: 0;
    background-color: transparent;
    color: #ee5253;
    margin-left: 16px;
  }

  ul {
    max-width: 500px;
    width: 100%;
    background-color: white;
    box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
    margin: 0;
    margin-top:10px;
    padding: 32px;
    list-style-type: none;
  }

  li a {
    width: initial;
    max-width: initial;
    padding: 0;
    border-radius: 0;
    background-color: transparent;
    color: #444;
    text-align: left;
    margin: 0;
  }

  li {
    margin: 8px 0;
    padding: 8px;
    background-color: #f0f0f0;
  }

  li:nth-child(even) {
    background-color: #f8f8f8;
  }

</style>

<body>
  <p> ${message} </p>
  <c:if test="${empty sessionScope['current.user.id']}">
    <form action="main" method="POST">
      <input type="text" name="user" placeholder="Username">
      <br>
      <input type="password" name="password" placeholder="Password">
      <br>
      <input type="submit" value="Login">
    </form>
  </c:if>

  <c:if test="${empty sessionScope['current.user.id']}">
    Dont have an account? <a href='register'>Click here!</a>
  </c:if>
  <br>
  <c:choose>
    <c:when test="${users==null}">
      No users registered!
    </c:when>
    <c:otherwise>
      <b>Registered users:</b>
      <ul>
        <c:forEach var="e" items="${users}">
          <li>
            <a href="author/${e.nick}">${e.nick}</a>
          </li>
        </c:forEach>
      </ul>
    </c:otherwise>
  </c:choose>

  <c:if test="${not empty sessionScope['current.user.id']}">
    <div>
    <span>${sessionScope['current.user.nick']}</span>
    <a href='${pageContext.request.contextPath}/servleti/logout'>Logout</a>
    </div>
  </c:if>
</body>

</html>