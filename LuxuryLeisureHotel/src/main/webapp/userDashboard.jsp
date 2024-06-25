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
            if(user == null && user.isAdmin()){
        %>
        <h1>You are not Authorized to access this page</h1>
        <%} else {%>
        <div class="container">
            <div class="row">
                 <h3>Upcoming stays</h3>
                 <% 
                        List<Reservation> reservations = (List<Reservation>) request.getSession(false).getAttribute("reservations");
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM YYYY");
                        if(reservations != null || reservations.size() > 0){
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
                    <% if("PENDING".equals(status)){%>
                    <p>Please proceed to <a href="PaymentController?submit=getPaymentPage&reservationId=<%=reservation.getBookingId()%>">payment</a></p>
                    <form action="UserController" method="post">
                        <input name="submit" value="cancel" type="hidden">
                        <button type="submit">Cancel</button>
                    </form>
                    <%}%>
                </div>
                 
            </div>
            <%    }
            } else {%>
            <p>No reservation history you have</p>
            <p>If you wish to make reservation <a href="home?submit=home">click here</a></p>
            <%}
}%>
            </div>
        </div>
    </body>
</html>
