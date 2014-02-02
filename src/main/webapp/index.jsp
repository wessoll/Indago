<%-- 
    Document   : index
    Created on : Feb 2, 2014, 1:30:04 PM
    Author     : wesley
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:if test="${loggedInUser != null}">
    <%
        // redirect user back to the homepage if he is already logged in
        response.sendRedirect("homepage.html");
    %>
</c:if>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Made in Europe -->
        <!-- Message Handling -->
        <c:if test="${loginSuccess != null && !loginSuccess}">
            Username or password incorrect.
        </c:if>
            
        <c:choose>
            <c:when test="${!registerSuccess}">
                <c:if test="${usernameTaken}">
                    The Username you provided is already taken. Please choose another one.
                </c:if>
                <c:if test="${emailAddressTaken}">
                    There is already a User registered with the email address you provided.
                </c:if>
            </c:when>
            <c:otherwise>
                You are now a registered member of Indago. Thank you!
            </c:otherwise>
        </c:choose>
        <!-- eof Message Handling -->

        <div id="welcome">
            <h2>Welcome to Indago Alpha 0.1</h2>
            
            Hello and Welcome! Thank you for visiting Indago. You should know that Indago is still under
            development. You are more than welcome to test it for yourself! Please submit any bugs, improvements, or 
            questions to <a href="https://github.com/wscheper/Indago">Indago on Github</a>.
            
            <p><b>Please note that any data you save will be erased by March 1, 2014.</b></p>
        </div>
        
        <h2>Login</h2>
        <form id="frmLogin" action="login" method="post">
            <input type="text" name="username" id="username" placeholder="Enter Username" required>
            <input type="password" name="password" id="password" placeholder="Enter Password" required>
            <button type="submit" id="btnSubmit">Login</button>            
        </form>
        <h2>Sign Up</h2>
        <p>
            <i>Where your information will be used for</i></br>
            
            Your first and last name will be used for identification. Your email address will be used to send 
            notifications to. Your password is saved using md5.
        </p>
        
        <form id="frmRegister" action="register" method="post">
            <input type="text" name="firstname" id="firstname" placeholder="First Name" required>
            <input type="text" name="lastname" id="lastname" placeholder="Last Name" required>
            <input type="email" name="emailAddress" id="emailAddress" placeholder="Email Address" required>
            <input type="text" name="username" id="username" placeholder="Username" required>
            <input type="password" name="password" id="password" placeholder="Password" required>
            <button type="submit" id="btnSubmit">Sign Up</button>            
        </form>
    </body>
</html>