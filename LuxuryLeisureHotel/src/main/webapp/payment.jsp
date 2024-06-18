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
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <div class="container">
            <% 
            User user = (User) request.getSession(false).getAttribute("user");
            Reservation reservation = (Reservation) request.getSession(false).getAttribute("reservation");
            if(user != null){
                %>
                <div class="reservation-summary">
                    <h3>Reservation Summary</h3>
                    <div>
                        <p>Reserving for date to be added</p>
                        <p><%= reservation.getRoom().getRoomType().name()%></p>
                        <p><%= reservation.getRoom().getLocation()%></p>
                        <p>room per night:  ZAR <%= reservation.getRoom().getRates() %></p>
                        <hr>
                        <p> Total amount:   ZAR</p>
                        <p>including VAT</p>
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
                            <input name="submit" value="checkout" type="submit">
                        </form>
                    </div>
                </div>
        </div>
        
       
        
    </body>
</html>
