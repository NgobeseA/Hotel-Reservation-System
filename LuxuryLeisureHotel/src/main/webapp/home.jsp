<%-- 
    Document   : home
    Created on : 13 Jun 2024, 15:30:10
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.room.model.Room"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Luxury Leisure Hotel</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .container {
            padding: 20px;
        }
        .room-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
        }
        .room-box {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 15px;
            margin: 10px;
            width: 300px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .room-box img {
            max-width: 100%;
            border-radius: 5px;
        }
        .room-box h4 {
            margin: 10px 0;
        }
        .room-box p {
            margin: 5px 0;
        }
    </style>
</head>
<body>

    <div class="navbar">
        <a href="home?submit=home">Home</a>
        <% 
           
            User user = (User) request.getSession(false).getAttribute("user");
            String message = (String) request.getAttribute("message");
            if(user != null && user.isAdmin()){
        %>
        <a href="RoomController?submit=getAddRoomPage">Add Room</a>
        <a href="AdminController?submit=addUser">Add User</a>
        <% } else {%>
        <a href="UserController?submit=getDashboard">My Dashboard</a>
        <%}%>

    </div>

    <div class="container">
        <% if(message != null) { %>
        <h3><%=message%></h3>
        <% } else { %>
        <h1>Live soft life ntanga!</h1>
        <% } %>

        <h3>Book now Ntanga! <%= user != null && user.isAdmin() %></h3>
        <form action="ReservationController" method="post">
            Check-in: <input name="checkIn" type="date">
            Check-in Time: <input name="time-in" type="time" value="14:00" readonly><br>
            Check-Out: <input name="checkOut" type="date">
            Check-Out Time: <input name="time-out" type="time" value="12:00" readonly> <br>

            <% 
                List<Room> rooms = (List<Room>) request.getSession(false).getAttribute("rooms");
                if(rooms != null && !rooms.isEmpty()) {
                    for(Room room : rooms){
            %>
            <div class="room-box">
                <img src="<%= room.getPicture() %>" alt="Room Picture">
                <h4>Room Type: <%= room.getRoomType().name() %></h4>
                <p>Description: <%= room.getDescription() %></p>
                <p>Rating: <%= room.getRating() %></p>
                <p>Price per night: <%= room.getRates() %></p>
                <label>
                    <input type="checkbox" name="roomIds" value="<%= room.getId() %>"> Select Room
                </label>
                    <input name="userId" value="<%= user.getId()%>" hidden>
            </div>
            <% 
                    }
                } else { 
            %>
            <h3>No rooms are available at the moment</h3>
            <% } %>

            <input name="submit" type="submit" value="reserveRoom">
        </form>
    </div>

</body>


</html>
