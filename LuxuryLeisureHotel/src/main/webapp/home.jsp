<%-- 
    Document   : home
    Created on : 13 Jun 2024, 15:30:10
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.room.model.Room"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Luxury Leisure Hotel</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/home.css">
        <script>
            const btns = document.querySelectorAll(".nav-btn");
            const slides = document.querySelectorAll(".img-slide");
            
            var sliderNav = function(manual){
                btns.forEach((btn) => {
                    btn.classList.remove("active");
                });
                
                slides.forEach((slide) => {
                    slide.classList.remove("active");
                });
                
                btns[manual].classList.add("active");
                slides[manual].classList.add("active");
                
               
            };
             btns.forEach((btn, i) => {
                    btn.addEventListener("click", () => {
                        sliderNav(i)
                    });
                });
        </script>
    </head>
    
<body>

   
        <% User user = (User) request.getSession(false).getAttribute("user");%>
        <jsp:include page="navbar.jsp" />


    <div class="container">
        
       <section class="wall"> 
                <img class="img-slide active" src="./images/background.jpg">
                <img class="img-slide " src="./images/swimming-pool.jpg">
                <img class="img-slide" src="./images/waiting.jpg">
                
                <div class="content active">
                    <h3><span>Luxury Leisure Hotel</span></h3>
                    <p>Live soft life ntanga</p>
                    <a href="ReservationController?submit=getBookingPage">Book now</a>
                </div>

                <div class="slider-navigation">
                    <div class="nav-btn active"></div>
                    <div class="nav-btn"></div>
                    <div class="nav-btn"></div>
                </div>

               
        </section>
    </div>

</body>


</html>
