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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/navbar.css">
</head>
<body>
    <header>
        <a class="brand" href="home?submit=home">Luxury Leisure Hotel</a>
        <nav class="navbar">
            <ul class="navbar-list">
                <li><a href="home?submit=home">Overview</a></li>
                <li><a href="RoomController?submit=getRoomType">Rooms</a></li>
                <li><a href="">Services</a></li>
                <li><a href="">About Luxury Leisure</a></li>
                <%
                    User user = (User) request.getSession(false).getAttribute("user");
                    if (user == null) {
                %>
                <li><a href="UserController?submit=getLoginPage">Sign In</a></li>
                <li><a href="UserController?submit=getSignUpPage">Sign Up</a></li>
                <% } else { StringBuilder initials = new StringBuilder(user.getName().charAt(0));
                    initials.append(user.getSurname().charAt(0));%>
                    <li><a href="ReservationController?submit=getBookingPage">Booking</a></li>
                    <% if(!user.isAdmin()){%>
                    <li><a href="UserController?submit=getDashboard">My Dashboard</a></li>
                    <%} else {%>
                    <li><a href="AdminController?submit=getDashboard">Admin Dashboard</a></li>
                    <%}%>
                    <li class="dropdown">
                        <span class="user-circle"><%= initials.toString().toUpperCase() %></span>
                        <div class="dropdown-content">
                            <a href="#">Edit Profile</a>
                            <a href="UserController?submit=logout">Log out</a>
                        </div>
                    </li>
                <%}%>
            </ul>
    
    </nav>
    </header>
    
</body>
</html>

