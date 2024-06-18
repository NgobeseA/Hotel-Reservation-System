<%-- 
    Document   : adminDashboard
    Created on : 18 Jun 2024, 15:56:00
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <% User user = (User) request.getSession(false).getAttribute("user");
        if(user.isAdmin()){
        %>
        <div class="container">
            <div>
                <h2>Admin Dashboard</h2>
            </div>
            <div>
                <div>
                    <h3>Reservations</h3>
                    
                </div>
                <div>
                    <h3>Users</h3>
                </div>
            </div>
        </div>
        <% } else {%> <jsp:include page="unauthorized.jsp" /><%}%>
    </body>
</html>
