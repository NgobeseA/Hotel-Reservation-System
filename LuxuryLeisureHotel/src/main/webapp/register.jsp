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
    </head>
    <body>
        <%
           User user = (User) request.getSession(false).getAttribute("user");
         %>
        <h3>Sign Up</h3>
        
        <form action="UserController" method="post">
            name:<input name="name" type="text"/> <br>
            surname: <input name="surname" type="text"> <br>
            email: <input name="email" type="email"> <br>
            contact number: <input name="contact" type="number"> <br/>
            address: <input name="address" type="text"/> <br>
            password: <input name="password" type="password"/><br>
            admin: <input name="admin" type="checkbox"></br>
            <input name="submit" type="submit" value="register">
        </form>
    </body>
</html>
