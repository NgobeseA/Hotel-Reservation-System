<%-- 
    Document   : addRoom
    Created on : 16 Jun 2024, 00:31:59
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.room.model.RoomType"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Room</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <% 
            User user = (User) request.getSession(false).getAttribute("user");
            RoomType[] roomtype = (RoomType[]) request.getAttribute("enum");
            if(!user.isAdmin()){
            %>
            <jsp:include page="unauthorized.jsp" />
            <%} else {%>
            <h1>add Room</h1>
        <form action="RoomController" method="post">
            Room type: <select id="roomType" name="roomType">
                <option>select</option>
                <% for (RoomType rt : roomtype) {%>
                <option value="<%= rt %>"><%= rt %></option>
                    
                <%}%>
            </select><br>
            Picture: <input name="picture" type="text"><br>
            description: <input name="description" type="text"><br>
            Location: <input name="location" type="text"><br>
            Rates: <input name="rates" type="number"><br>
            Rating: <input name="rating" type="number"><br>
            Available: <input name="available" type="checkbox"><br>
            Room Number: <input name="room_number" type="text"></br>
            <input name="submit" type="submit" value="createRoom">
            
        </form>
            <%}%>
    </body>
</html>
