<%-- 
    Document   : login
    Created on : Jun 14, 2024, 10:32:17 AM
    Author     : Train 01
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/signup.css">
    </head>
    <body>
        <%
           String message = (String) request.getAttribute("message");
         %>
         <jsp:include page="navbar.jsp" />
         <section id="container" class="container">
             <div class="form-container sign-up-container">
                 <h3>Sign In</h3>
        
                <form action="UserController" method="post">
                    <div class="infield"><input placeholder="Email" name="email" type="email"></div>
                    <div class="infield"> <input placeholder="password" name="password" type="password"></div>
                    <% if(message != null){%><p><%=message%></p><%}%>
                    <input name="submit" type="hidden" value="login">
                    <button type="submit">Login</button>
                </form>
                 
             </div>
                    <div class="overlay-container">
                        <div class="overlay">
                            <div class="overlay-panel overlay-left">
                                <h3>Welcome to Luxury Leisure</h3>
                                <p>If you do not have account feel free to sign up</p>
                                <form action="UserController" method="get">
                                    <input name="submit" value="getSignUpPage" type="hidden">
                                <button  type="submit">Sign Up</button>
                        </form>
                            </div>
                        </div>
                    </div>
         </section>
    </body>
</html>
