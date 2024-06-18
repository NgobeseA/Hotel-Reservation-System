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
    <script>
        function setDefaultDates() {
            // Get today's date
            const today = new Date();
            // Get tomorrow's date
            const tomorrow = new Date();
            tomorrow.setDate(today.getDate() + 1);

            // Format dates to yyyy-mm-dd
            const formattedToday = today.toISOString().split('T')[0];
            const formattedTomorrow = tomorrow.toISOString().split('T')[0];

            // Set the values of the date input fields
            document.getElementById('checkIn').value = formattedToday;
            document.getElementById('checkOut').value = formattedTomorrow;
        }

        // Call the function when the window loads
        window.onload = setDefaultDates;
    </script>
</head>
<body>

    <div class="navbar">
        <% User user = (User) request.getSession(false).getAttribute("user");%>
        <jsp:include page="navbar.jsp"></jsp:include>
    </div>

    <div class="container">
        <h1>Live soft life ntanga!</h1>
  

        <h3>Book now Ntanga! <%= user != null && user.isAdmin() %></h3>
        <form action="ReservationController" method="post">
            Check-in: <input name="checkIn" id="checkIn" type="date">
            Check-in Time: <input name="time-in" type="time" value="14:00" readonly><br>
            Check-Out: <input name="checkOut" id="checkOut" type="date">
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
