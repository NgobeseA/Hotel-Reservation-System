<%-- 
    Document   : booking
    Created on : 20 Jun 2024, 10:13:33
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType"%>
<%@page import="java.util.Date"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.room.model.Room"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reserve a Room</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/booking.css">
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const today = new Date();
                const tomorrow = new Date(today);
                tomorrow.setDate(tomorrow.getDate() + 1);

                const todayStr = today.toISOString().split('T')[0];
                const tomorrowStr = tomorrow.toISOString().split('T')[0];

                const checkInInput = document.getElementById('checkIn');
                const checkOutInput = document.getElementById('checkOut');

                checkInInput.setAttribute('min', todayStr);
                checkOutInput.setAttribute('min', todayStr);

                checkInInput.value = todayStr;
                checkOutInput.value = tomorrowStr;

                checkInInput.addEventListener('change', function() {
                    const checkInDate = new Date(checkInInput.value);
                    checkInDate.setDate(checkInDate.getDate() + 1);
                    checkOutInput.setAttribute('min', checkInDate.toISOString().split('T')[0]);
                });

                document.getElementById('reservationForm').addEventListener('submit', function(event) {
                    if (new Date(checkInInput.value) >= new Date(checkOutInput.value)) {
                        alert('Check-out date must be after the check-in date.');
                        event.preventDefault();
                    }
                });
            });
        </script>
    </head>
    <body>
        <% User user = (User) request.getSession(false).getAttribute("user");
            Date checkIn = (Date) request.getSession(false).getAttribute("check_in");
            Date checkOut = (Date) request.getSession(false).getAttribute("check_out");
            String message = (String) request.getAttribute("message");
            %>
        <jsp:include page="navbar.jsp" />
        <section class="container">
             <h1>Reserve a room</h1>
             <% if(message != null){%><h3><%= message %></h3><%}%>
               
                <form action="ReservationController" method="post">
                    <div class="datepicker">
                    Check-in: <input name="checkIn" value="<%=checkIn %>" id="checkIn" type="date">
                    Check-in Time: <input name="time-in"  type="time" value="14:00" readonly>
                    Check-Out: <input name="checkOut" value="<%= checkOut %>" id="checkOut" type="date">
                    Check-Out Time: <input name="time-out" type="time" value="12:00" readonly> 
                    
                
                    </div>
                    <% 
                        List<RoomType> rooms = (List<RoomType>) request.getSession(false).getAttribute("roomtypes");
                        if(rooms != null && !rooms.isEmpty()) {
                            for(RoomType room : rooms){
                    %> 
                    <div class="room-box">
                        <img src="./images/<%= room.getPicture_url() %>" alt="Room Picture">
                        <h4>Room Type: <%= room.getRoom_type() %></h4>
                         <input type="radio" name="roomtypeId" value="<%= room.getId() %>"> Select
                    </div>
                    <% 
                            }
                        } else { 
                    %>
                    <h3>No rooms are available at the moment</h3>
                    <% } %>

                    <hr>
                    <div>
                        <h4>Dining Preference</h4>
                    </div>
                    <div>
                        <select name="mealtype">
                            <option value="breakfast">Breakfast</option>
                            <option value="lunch">Lunch</option>
                            <option value="both">Both</option>
                            <option value="none">None</option>
                        </select>
                    </div>
                    <div>
                        <input name="dietary" 
                               type="text" 
                               placeholder="Dietary Restriction"
                               />
                    </div>
                    <input type="hidden" name="submit" value="reserveRoom">
                <button type="submit">Reserve Now</button>
                </form>   
        </section>
    </body>
</html>
