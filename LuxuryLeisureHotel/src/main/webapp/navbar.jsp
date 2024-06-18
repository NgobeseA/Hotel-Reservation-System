<%-- 
    Document   : navbar
    Created on : 18 Jun 2024, 15:06:15
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Navigation Bar</title>
    <link rel="stylesheet" type="text/css" href="styles.css"> <!-- Include your CSS file here -->
</head>
<body>
<nav>
    <ul>
        <li><a href="home?submit=home">Overview</a></li>
        <li><a href="">Rooms</a></li>
        <li><a href="">Services</a></li>
        <li><a href="">About Luxury Leisure</a></li>
        <%
            User user = (User) request.getSession(false).getAttribute("user");
            if (user == null) {
        %>
        <li><a href="signin.jsp">Sign In</a></li>
        <li><a href="signup.jsp">Sign Up</a></li>
        <% } else { %>
        <li><a href=""><%= user.getName().toUpperCase() %></a></li>
        <% if(!user.isAdmin()){%>
        <li><a href="UserController?submit=getDashboard">My Dashboard</a></li>
        <%} else {%>
        <li><a href="">Admin Dashboard</a></li>
        <% } }%>
    </ul>
</nav>
</body>
</html>

