<%-- 
    Document   : home
    Created on : 13 Jun 2024, 15:30:10
    Author     : T440
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Luxury Leisure Hotel</title>
    </head>
    <body>
        <h1>Live soft life ntanga!</h1>
        
        <hr>
        <h3>Book now Ntanga!</h3>
        <form action="/BookingController" method="post">
            Check-in: <input name="checkIn" type="date">
            Check-in Time: <input name="time-in" type="time"><br>
            Check-Out: <input name="checkOut" type="date">
            Check-Out Time: <input name="time-out" type="time"> <br>
            <input name="submit" type="submit" value="booking">
        </form>
    </body>
</html>
