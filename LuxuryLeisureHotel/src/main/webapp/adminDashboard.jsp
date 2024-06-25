<%-- 
    Document   : adminDashboard
    Created on : 18 Jun 2024, 15:56:00
    Author     : T440
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="co.za.andile.luxuryleisurehotel.reservations.model.Reservation"%>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.room.model.Room"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <% User user = (User) request.getSession(false).getAttribute("user");
        List<Room> rooms = (List<Room>) request.getSession(false).getAttribute("Allrooms");
        List<Reservation> reservedRooms = (List<Reservation>) request.getSession(false).getAttribute("reservedRooms");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM YYYY");
        if(user != null && user.isAdmin()){
        %>
        <div class="container">
            <div class="header">
                <h2>Admin Dashboard</h2>
                
                <button class="button" type="submit">Guest Service</button>
            </div>
            <div>
                <div class="table">
                    <table>
                    <% if(reservedRooms != null && !reservedRooms.isEmpty()){%>

                    <h3>Reservations</h3>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Room Number</th>
                            <th>Room Type</th>
                            <th>Check in Date</th>
                            <th>Check out Date</th>
                            <th>Reserved on</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                            <% for(Reservation reserv : reservedRooms){%><tr>
                            <td><%= reserv.getUser().getName() %></td>
                            <td><%= reserv.getRoom().getRoomNumber() %></td>
                            <td><%= reserv.getRoom().getRoomType().getRoom_type() %></td>
                            <td><%= reserv.getCheck_in().format(dateFormatter) %></td>
                            <td><%= reserv.getCheck_out().format(dateFormatter) %></td>
                            <td><%= reserv.getCreated_on().format(dateFormatter) %></td>
                            <td><%= reserv.getStatus().name() %></td>
                            <td>
                                <form>
                                    <input hidden name="reservationId" value="<%= reserv.getBookingId()%>" >
                                    <button class="button" type="submit">Edit</button>
                                </form>
                                <form action="ReservationController" method="post">
                                    <input hidden name="reservationId" value="<%= reserv.getBookingId()%>" >
                                    <input type="hidden" name="submit" value="cancelReservation">
                                    <button class="button" type="submit">Cancel</button>
                                </form>
                            </td>
                            </tr>
                            <%} }%>
                        
                    </tbody>
                    
                    </table>
                </div>
                <div>
                    <h3>Rooms</h3>
                    <div>
                        <a href="RoomController?submit=getAddRoomPage">Add Room</a>
                    </div>
                    <div>
                        <table>
                            <% 
                                if(rooms != null && rooms.size() > 0){%>
                            <tr>
                                <th>Room Number</th>
                                <th>Room Type</th>
                                <th>Room Location</th>
                                <th>Ratings</th>
                                <th>Available</th>
                                <th>Price per night</th>
                                <th>Action</th>
                            </tr>
                          
                            <% for(Room room : rooms){%>
                            <tr>
                                <td><%=room.getRoomNumber()%></td>
                                <td><%=room.getRoomType()%></td>
                                <td><%=room.getLocation()%></td>
                                <td><%=room.isAvailable()%></td>
                                <td><%=room.getRates()%></td>
                                <td>
                                    <form action="RoomController" method="get" style="display:inline;">
                                        <input type="hidden" name="roomId" value="<%= room.getId() %>">
                                        <input type="submit" name="submit" value="getAddRoomPage">
                                    </form>
                                    <form action="RoomController" method="post" style="display:inline;">
                                        <input type="hidden" name="roomId" value="<%= room.getId() %>">
                                        <input type="hidden" name="submit" value="Delete">
                                        <button class="button" type="submt">Delete</button>
                                    </form>
                                </td>
                            </tr>
                            <% } }else {%> <h4>No Rooms</h4> <%}%>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <% } else {%> <jsp:include page="unauthorized.jsp" /><%}%>
    </body>
</html>
