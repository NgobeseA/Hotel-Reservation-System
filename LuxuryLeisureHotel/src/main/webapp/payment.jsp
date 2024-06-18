<%-- 
    Document   : payment
    Created on : 18 Jun 2024, 14:28:47
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment</title>
    </head>
    <body>
        <% 
            User user = (User) request.getSession(false).getAttribute("user");
            if(user != null){
                %>
        <h1>Payment</h1>
        
    </body>
</html>
