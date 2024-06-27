<%-- 
    Document   : userDashboard
    Created on : 17 Jun 2024, 14:57:23
    Author     : T440
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="co.za.andile.luxuryleisurehotel.reservations.model.Reservation"%>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/UserDashboard.css">
        <title>My Dashboard</title>
        
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <%
           
            User user = (User) request.getSession(false).getAttribute("user");
            List<Reservation> historyReservation = (List<Reservation>) request.getAttribute("UserReservationHistory");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd mm yyyy");
            if(user == null && user.isAdmin()){
        %>
        <h1>You are not Authorized to access this page</h1>
        <%} else {%>
        <div class="container">
            <div>
                <div class="button">
                    <a href="ReservationController?submit=getUserReservationHistory">View history</a>
                </div>
            </div>
            <div class="row">
                 <h3>Upcoming stays</h3>
                 <% 
                     // need to pudh a reservations for upcoming stays not all reservations
                        List<Reservation> reservations = (List<Reservation>) request.getSession(false).getAttribute("UpComing");
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM YYYY");
                        if(reservations != null || reservations.size() > 0 ){
                            for(Reservation reservation : reservations){
                               String startDate = reservation.getCheck_in().format(dateFormatter);
                               String endDate = reservation.getCheck_out().format(dateFormatter);
                               String status = reservation.getStatus().name();
                    %>
            <div class="column">
                <div class="card">
                    
                    <p><strong>Reservation for <%=startDate%> - <%= endDate %></strong></p>
                    <hr>
                    <p><%= reservation.getRoom().getLocation() %></p>
                    <p><p>

                    <p>Room offer   ZAR <%=reservation.getRoom().getRates()%></p>
                    <p><%=status%></p>
                    <div class="progress-bar">
                        <div class="circle <%= "PENDING".equals(status) || "CONFIRMED".equals(status) || "CHECKED_IN".equals(status) || "CHECKED_OUT".equals(status) ? "active" : "" %>">1</div>
                        <div class="circle <%= "CONFIRMED".equals(status) || "CHECKED_IN".equals(status) || "CHECKED_OUT".equals(status) ? "active" : "" %>">2</div>
                        <div class="circle <%= "CHECKED_IN".equals(status) || "CHECKED_OUT".equals(status) ? "active" : "" %>">3</div>
                        <div class="circle <%= "CHECKED_OUT".equals(status) ? "active" : "" %>">4</div>
                        <div class="circle <%= "CANCELLED".equals(status) ? "active" : "" %>">5</div>
                        <div class="line"></div>
                    </div>
                    <% if("PENDING".equals(status)){%>
                    <p>Please proceed to <a href="PaymentController?submit=getPaymentPage&reservationId=<%=reservation.getBookingId()%>">payment</a></p>
                    <%}%>
                    <form action="UserController" method="post" class="header">
                        <input name="submit" value="viewDetails" type="hidden">
                        <button type="submit">View Details</button>
                        <input name="submit" value="cancel" type="hidden">
                        <button type="submit">Cancel</button>
                    </form>
                    
                </div>
                 
            </div>
            <%    } %>
            
            <%}
}%>
            </div>
            <% if(historyReservation != null && !historyReservation.isEmpty()){%>
            <div>
                <h3>Reservation History</h3>
                <div class="table">
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Room Number</th>
                                <th>Room Type</th>
                                <th>Reserved from - To</th>
                                <th>Reserved date</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Reservation reservation : historyReservation){%>
                            <tr>
                                <td><%= reservation.getUser().getName()%></td>
                                <td><%= reservation.getUser().getEmail()%></td>
                                <td><%= reservation.getRoom().getRoomType().getRoom_type()%></td>
                                <td><%= reservation.getRoom().getRoomNumber()%></td>
                                <td><%= reservation.getCheck_in().format(formatter)%> - <%= reservation.getCheck_out()%></td>
                                <td><%= reservation.getCreated_on().format(formatter)%></td>
                                <td>Eish</td>
                                <td><%= reservation.getStatus().name()%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
            <%}%>
    </body>
</html>
