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


  body>a:last-child {
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

  body {
    justify-content: flex-start;
    padding-top: 150px;
  }

  p {
    max-width: 900px;
    width: 100%;
    margin: 0;
  }

  p:nth-of-type(1) {
    /* username */
    padding: 8px 32px;
    background-color: white;
  }

  p:nth-of-type(2) {
    /* title */
    padding: 32px 32px;
    background-color: #ee5253;
    font-size: 2rem;
    color: white;
    font-weight: bold;
  }

  p:nth-of-type(3) {
    /* created */
    border-bottom: 1px solid #eee;
  }

  p:nth-of-type(3),
  p:nth-of-type(4) {
    /* created & modified */
    padding: 4px 32px;
    background-color: white;
    color: #aaa;
    font-size: 0.8rem;
  }

  p:nth-of-type(3):before {
    /* created */
    content: 'Created: '
  }

  p:nth-of-type(4):before {
    /* modified */
    content: 'Last Modified: '
  }

  p:nth-of-type(5) {
    /* text */
    padding: 64px;
    background-color: white;
    margin-top: 32px;
    box-shadow: 0 10px 40px -15px rgba(0, 0, 0, 0.5);
  }

  a {
    margin-top: 8px;
    padding: 0 8px;
    background-color: transparent;
    text-align: right;
    color: #aaa;
    font-size: 0.8rem;
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

  ul::before {
    display: block;
    content: 'Comments';
    padding: 32px;
    background-color: #444;
    color: white;
  }

  ul {
    max-width: 800px;
    width: 100%;
    background-color: white;
    margin: 0;
    padding: 0;
    margin-top: 128px;
    list-style-type: none;
  }

  li {
    margin: 32px;
    padding: 16px 0;
    border-bottom: 1px solid #ccc;
  }

  li > span {
    display: block;
  }

  span {
      display:block;
      width:100%;
      word-wrap:break-word;
  }

  li > span:nth-child(1) {
    color: #ee5253;
    float: left;
  }

  li > span:nth-child(2) {
    color: #ccc;
    font-style: italic;
    float: left;
    margin-left: 8px;
  }

  li >span:nth-child(3) {
    clear: both;
    margin: 32px 0;
    padding-left: 16px;
  }

  form {
    margin-top:30px;
    max-width:700px;
    width:100%;
    padding: 32px;
    font-size: 2em;
  }

  textarea,
    input[type="text"], {
      font-size: 1rem;
      margin: 0;
      margin-top: 8px;
      margin-bottom: 16px;
      border-radius: 8px;
      border: none;
      padding: 16px;
      background-color: #f4f4f4;
      position: relative;
      resize: none;
      width:100%;
    }

   input[type="submit"] {
       background-color: #A3CB38;
       color: white;
       font-weight: bold;
       padding: 8px;
       border-radius: 4px;
       border: none;
       outline: none;
       font-size: 0.8rem;
       text-transform: uppercase;
       max-width: 100px;
       width: 100%;
       margin-top:8px;
       align-self: flex-end;
     }

     input[type="submit"]:hover {
       background-color: #C4E538;
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
  <p><span>${entry.creator.nick}</span></p>
  <p><span>${entry.title}</span></p>
  <p>${entry.createdAt}</p>
  <p>${entry.lastModifiedAt}</p>
  <p><span>${entry.text}</span></p>
  <c:if test = "${sessionScope['current.user.nick'] == entry.creator.nick}">
  <a href="edit?id=${entry.id}">Edit</a>
  </c:if>
  <br>
  <ul>
  <c:forEach var="e" items="${entry.comments}">
  <li>
  <span>${e.usersEMail}</span><span>${e.postedOn}</span><span>${e.message}</span>
  </li>
  </c:forEach>
  </ul>

  <form action="" method="POST">
  <c:choose>
    <c:when test="${empty sessionScope['current.user.id']}">
      <div><input type="text" name="email" placeholder="email"></div>
    </c:when>
    <c:otherwise>
      <input type="hidden" name="nick" value="${sessionScope['current.user.nick']}"/>
    </c:otherwise>
  </c:choose>
      ​<textarea name="comment" rows="8" cols="90" placeholder="comment"></textarea>
      <input type="hidden" name="blogId" value="${entry.id}"/>
      <div><input type="submit" value="Comment"></div>
  </form>

  <a class="home" href="${pageContext.request.contextPath}/index.jsp">Home</a>
  <c:if test="${not empty sessionScope['current.user.id']}">
    <div class="logout">
    <span>${sessionScope['current.user.nick']}</span>
    <a href='${pageContext.request.contextPath}/servleti/logout'>Logout</a>
    </div>
  </c:if>
</body>

</html>