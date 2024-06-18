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
            if(user == null || user.isAdmin()){
        %>
        <h1>You are not Authorized to access this page</h1>
        <%} else {%>
        <div class="history-container">
            <h3>Upcoming stays</h3>
            <div class="reservation-ticket">
                <% 
                List<Reservation> reservations = (List<Reservation>) request.getSession(false).getAttribute("reservations");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM YYYY");
                if(reservations != null || reservations.size() > 0){
                    for(Reservation reservation : reservations){
                       String startDate = reservation.getCheck_in().format(dateFormatter);
                       String endDate = reservation.getCheck_out().format(dateFormatter);
                       String status = reservation.getStatus().name();
            %>
            <p>Reservation for <%=startDate%> - <%= endDate %></p>
            <p><%= reservation.getRoom().getLocation() %></p>
            <p><%=reservation.getRoom().getRoomType().name()%></p>
            
            <p>Room offer   ZAR <%=reservation.getRoom().getRates()%></p>
            <p><%=status%></p>
            <% if("PENDING".equals(status)){%>
            <p>Please proceed to <a href="PaymentController?submit=getPaymentPage">payment</a></p>
            <form action="UserController" method="post">
                <input name="submit" value="cancel" type="submit">
            </form>
            <%}%> 
            </div>
            <%    }
            } else {%>
            <p>No reservation history you have</p>
            <p>If you wish to make reservation <a href="home?submit=home">click here</a></p>
            <%}
}%>
        </div>
    </body>
</html>
