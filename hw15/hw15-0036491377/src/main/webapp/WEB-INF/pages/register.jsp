<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

  form:before {
    content: 'Register';
    margin: 16px 0;
    width: 100%;
  }

  textarea,
  input[type="text"],
  input[type="password"] {
    font-size: 1rem;
    margin: 0;
    margin-top: 8px;
    margin-bottom: 16px;
    border-radius: 8px;
    border: none;
    padding: 16px;
    background-color: #f4f4f4;
    position: relative;
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
</style>
<body>
  <p color="red">${error}</p>
  <p color="green">${message}</p>

  <form action="register" method="POST">
    <input type="text" name="user" placeholder="Username">
    <br>
    <input type="text" name="firstname" placeholder="First name">
    <br>
    <input type="text" name="lastname" placeholder="Last name">
    <br>
    <input type="text" name="email" placeholder="Email">
    <br>
    <input type="password" name="password" placeholder="Password">
    <br>
    <input type="submit" value="Register">
  </form>

  <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
</body>

</html>