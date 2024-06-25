<%-- 
    Document   : payment
    Created on : 18 Jun 2024, 14:28:47
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.reservations.model.Reservation"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/payment.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <div class="container">
            <% 
            User user = (User) request.getSession(false).getAttribute("user");
            Reservation reservation = (Reservation) request.getSession(false).getAttribute("reservation");
            Double amount = (Double) request.getSession(false).getAttribute("total_amount");
            if(user != null){
                %>
                <div class="reservation-summary">
                    <h3>Reservation Summary</h3>
                    <div>
                        <h5>Reserving for <%= reservation.getCheck_in()%> - <%= reservation.getCheck_out()%></h5>
                            <hr>
                        <p><%= reservation.getRoom().getRoomType().getRoom_type() %></p>
                        <p><%= reservation.getRoom().getLocation()%></p>
                        <p><%= reservation.getRoom().getRoomNumber() %></p>
                        <p><%= reservation.getMeal_type() %></p>
                        <p>Reservation was made on the: <%= reservation.getCreated_on() %></p>
                        <p>room per night:  ZAR <%= reservation.getRoom().getRates() %></p>
                        <hr>
                        <p> Total amount:   ZAR <%= amount %></p>
                        <p>including VAT    ZAR <%= amount  %></p>
                    </div>
                </div>
                <div class="payment">
                    <h3>Payment</h3>
                    <div>
                        <form action="PaymentController" method="post">
                            Account holder: <input name="accHolder" type="text"></br>
                            <div>
                                <label>Account number</label>
                                <input name="accNumber" type="number">
                            </div>
                            <div>
                                <label>CVV</label>
                                <input name="cvv" type="number">
                            </div></br>
                            <label>Valid thru</label>
                            <input name="validthru" type="date"></br>
                            <input name="amount" value="<%= amount%>" type="hidden">
                            <input name="reservationId" value="<%= reservation.getBookingId() %>" type="hidden"> 
                            <input name="submit" value="pay now" type="submit">
                        </form>
                    </div>
                </div>
                        <%} else {%> <jsp:include page="unauthorized.jsp" /><%}%>
        </div>
        
       
        
    </body>
</html>
