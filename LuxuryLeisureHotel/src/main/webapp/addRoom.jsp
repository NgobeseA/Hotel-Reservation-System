<%-- 
    Document   : addRoom
    Created on : 16 Jun 2024, 00:31:59
    Author     : T440
--%>


<%@page import="co.za.andile.luxuryleisurehotel.room.model.Room"%>
<%@page import="co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType"%>
<%@page import="java.util.List"%>
<%@page import="co.za.andile.luxuryleisurehotel.users.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Room</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/addRoom.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <div class="addRoom">
            <div class="contianer">
               
               <% 
            User user = (User) request.getSession(false).getAttribute("user");
            List<RoomType> roomtypes = (List<RoomType>) request.getSession(false).getAttribute("RoomTypes");
            Room roomToEdit = (Room) request.getAttribute("roomToEdit");
            String message = (String) request.getAttribute("AddTypeMessage");
            String roomMessage = (String) request.getAttribute("AddRoomMessage");
            if(user != null && user.isAdmin()){
            %>
            <div class="container">
                <div class="addRoom">
                    <h3>Add Room</h3> 
                    
                    <% if(roomToEdit != null){%>
                        <form action="RoomController" method="post">
                            <div class="room-type">
                                 <% if(roomMessage != null){%><h4><%= roomMessage %></h4><%}%>
                                Room type: <select id="roomType" name="roomType">
                                    <option value="<%= roomToEdit.getId()%>"><%= roomToEdit.getRoomType().getRoom_type()%></option>
                                    <% for (RoomType rt : roomtypes) {%>
                                    <option value="<%= rt.getId() %>"> <%= rt.getRoom_type() %> </option>
                                    <%}%>
                                    </select><br>
                            </div>


                                    <div class="name">Price per night: <input name="rates" value="<%=roomToEdit.getRates()%>" type="number"></div>
                                    <div class="name">Room Number: <input name="room_number" value="<%=roomToEdit.getRoomNumber()%>" type="text"></div>
                                    <div class="name">Available: <input name="available" value="<%= roomToEdit.isAvailable()%>" type="checkbox"></div>
                                    <div class="name">Location: <input name="location" value="<%= roomToEdit.getLocation()%>" type="text" readonly></div>
                            <div class="submit"><input id="form_button" name="submit" type="submit" value="updateRoom"></div>

                        </form>
                                    <%} else {%>
                                    <form action="RoomController" method="post">
                        <div class="room-type">
                             <% if(roomMessage != null){%><h4><%= roomMessage %></h4><%}%>
                            Room type: <select id="roomType" name="roomType">
                                <option>select</option>
                                <% for (RoomType rt : roomtypes) {%>
                                <option value="<%= rt.getId() %>"> <%= rt.getRoom_type() %> </option>
                                <%}%>
                                </select><br>
                        </div>

                                
                                <div class="name">Price per night: <input name="rates" type="number"></div>
                                <div class="name">Room Number: <input name="room_number" type="text"></div>
                                <div class="name">Available: <input name="available" type="checkbox"></div>
                                <div class="name">Location: <input name="location" type="text"></div>
                        <div class="submit"><input id="form_button" name="submit" type="submit" value="createRoom"></div>

                    </form>
                                <%}%>
                </div>
                <div class="addRoomType">
                    <h3>Add Room Type</h3>
                    <form action="RoomTypeController" method="post">
                        <input placeholder="Room Type" name="type" type="text">
                        <input placeholder="Picture URL" name="picture_url" type="text">
                        <% if(message != null){%>
                        <p><%= message %></p><%}%>
                        <input name="submit" type="submit" value="addRoomType">
                    </form>
                </div>
            </div>
                        
            <%} else {%>
            <jsp:include page="unauthorized.jsp" />
            <%} %> 
            </div>
            
        </div>
    </body>
</html>
