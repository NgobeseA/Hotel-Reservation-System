<%-- 
    Document   : datePicker
    Created on : 22 Jun 2024, 18:04:39
    Author     : T440
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        <div class="calendar">
            <form id="reservationForm" action="ReservationController" method="get">
                Check-in: <input name="checkIn" id="checkIn" type="date">
                Check-in Time: <input name="time-in" type="time" value="14:00" readonly>
                Check-Out: <input name="checkOut" id="checkOut" type="date">
                Check-Out Time: <input name="time-out" type="time" value="12:00" readonly>
                <input type="hidden" name="submit" value="getBookingPage">
                <button type="submit">Book now</button>
            </form>
        </div>
    </body>
</html>
