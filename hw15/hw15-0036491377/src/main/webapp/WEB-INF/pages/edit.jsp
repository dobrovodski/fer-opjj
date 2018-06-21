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
    padding: 0 32px;
    background-color: white;
    box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 3em;
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
    padding: 32px;
    background-color: white;
    display: flex;
    flex-direction: column;
    font-size: 2em;
    box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
  }

  textarea, input[type="text"] {
    font-size: 1rem;
    margin: 8px 0;
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

  input[name="title"] {
    margin-bottom: 32px;
  }

  textarea {
    margin-bottom: 16px;
  }


  a {
    color: #ee5253;
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
  <c:if test = "${not empty sessionScope['current.user.id']}">
  <form action="edit" method="POST">
    <input type="text" name="title" placeholder="title" value=${entry.title}></input>
    <br>
    ​<textarea name="text" rows="10" cols="70" placeholder="message content">${entry.text}</textarea>
    <br>
    <input type="hidden" value="${sessionScope['current.user.nick']}" name="creator" />
    <input type="hidden" value="${entry.id}" name="id" />
    <input type="submit" value="Submit">
  </form>
  </c:if>
  <a class="home" href="${pageContext.request.contextPath}/index.jsp">Home</a>
  <c:if test="${not empty sessionScope['current.user.id']}">
    <div class="logout">
    <span>${sessionScope['current.user.nick']}</span>
    <a href='${pageContext.request.contextPath}/servleti/logout'>Logout</a>
    </div>
  </c:if>
</body>

</html>