<%-- 
    Document   : guestService
    Created on : 24 Jun 2024, 20:33:05
    Author     : T440
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.reservations.model.Reservation"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guest Check In and Check Out</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/guestService.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <section>
        <% 
            User user = (User) request.getSession(false).getAttribute("user");
            String serviceMessage = (String) request.getAttribute("serviceMessage");
            List<Reservation> rightReservations = (List<Reservation>) request.getAttribute("rightReservations");
            List<Reservation> wrongReservations = (List<Reservation>) request.getAttribute("wrongReservations");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM YYYY");
            if(user != null && user.isAdmin()){
            %>
            
                <div>
                    <h1>Guest Check in and Check out</h1>
                </div>
                <form action="ReservationController" method="post">
                    <div class="email-container">
                        <input class="email-input" placeholder="Email address" name="email" type="email"> 
                        <input name="submit" type="hidden" value="service">
                        <button class="email-button" type="submit">Check user ticket</button>
                    </div>
                </form>
            <div class="content">
                <div class="right">
                    <% if(rightReservations != null && !rightReservations.isEmpty()){%>
                <h3>Confirmed and Checked In Reservations</h3>
                <table>
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
                        <% for(Reservation reserv : rightReservations){%><tr>
                            <td><%= reserv.getUser().getName() %></td>
                            <td><%= reserv.getRoom().getRoomNumber() %></td>
                            <td><%= reserv.getRoom().getRoomType().getRoom_type() %></td>
                            <td><%= reserv.getCheck_in().format(dateFormatter) %></td>
                            <td><%= reserv.getCheck_out().format(dateFormatter) %></td>
                            <td><%= reserv.getCreated_on().format(dateFormatter) %></td>
                            <td><%= reserv.getStatus().name() %></td>
                            <td>
                                <form action="ReservationController" method="post">
                                    <input name="reservationId" 
                                           value="<%= reserv.getBookingId()%>" 
                                           type="hidden">
                                    <input type="hidden" name="submit" value="checkIn">
                                    <button class="email-button" type="submit">Check In</button>

                                    <input type="hidden" name="submit" value="checkOut">
                                    <button class="email-button" type="submit">Check Out</button>
                                </form>
                            </td>
                    </tbody>
                </table>
                
                                           <%}} %>
                </div>
                
                <!---  WRONG RESERVATIONS  ---->
                <div>
                    <% if(wrongReservations != null && !wrongReservations.isEmpty()){%>
                <h3>Confirmed and Checked In Reservations</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Room Number</th>
                            <th>Room Type</th>
                            <th>Check in Date</th>
                            <th>Check out Date</th>
                            <th>Reserved on</th>
                            <th>Status</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        <% for(Reservation reserv : wrongReservations){%><tr>
                            <td><%= reserv.getUser().getName() %></td>
                            <td><%= reserv.getRoom().getRoomNumber() %></td>
                            <td><%= reserv.getRoom().getRoomType().getRoom_type() %></td>
                            <td><%= reserv.getCheck_in().format(dateFormatter) %></td>
                            <td><%= reserv.getCheck_out().format(dateFormatter) %></td>
                            <td><%= reserv.getCreated_on().format(dateFormatter) %></td>
                            <td><%= reserv.getStatus().name() %></td>
                           
                    </tbody>
                </table>
                
                <%}}%>
                </div>
            </div>
                <%if(serviceMessage != null){%><h5><%= serviceMessage %></h5><%}%>
        <%} else {%> <jsp:include page="unauthorized.jsp" /> <%}%>
        </section>
    </body>
</html>
