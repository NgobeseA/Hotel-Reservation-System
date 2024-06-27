<%-- 
    Document   : register
    Created on : 13 Jun 2024, 14:44:39
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/signup.css">
    </head>
    <body>
        <%
                User user = (User) request.getSession(false).getAttribute("user");
            %>
        <jsp:include page="navbar.jsp" />
        <section id="container" class="container">
            
            <div class="form-container sign-up-container">
                <h3>Sign Up</h3>

                <form action="UserController" method="post">
                    <div class="infield">
                        <input placeholder="name" name="name" type="text" required=""/>
                    </div>
                    <div class="infield"><input placeholder="surname" name="surname" type="text" required> </div>
                    <div class="infield"> <input placeholder="email" name="email" type="email" required> </div>
                    <div class="infield"> <input placeholder="contact number" name="contact" type="number" required=""> </div>
                    <div class="infield"> <input placeholder="password" name="password" type="password" required=""></div>
                    <% if(user != null && user.isAdmin()){%>
                    <div class="infield">admin: <input name="admin" type="checkbox">  </div>
                    <%}%>
                    <input name="submit" type="hidden" value="register">
                    <button  type="sumit">Register</button>
                </form>
            </div>
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h3>Wola, Good to see you!</h3>
                        <p>Keep connected with us where good times are in our place</p>
                        <form action="UserController" method="get">
                            <input name="submit" value="getLoginPage" type="hidden">
                        <button  type="submit">Sign In</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
