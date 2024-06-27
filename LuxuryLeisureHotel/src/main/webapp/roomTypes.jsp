<%-- 
    Document   : roomTypes
    Created on : 20 Jun 2024, 19:05:20
    Author     : T440
--%>

<%@page import="co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room Types</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/roomType.css">
    </head>
    <body>
        <jsp:include page="navbar.jsp" />
        <%
            List<RoomType> roomtypes = (List<RoomType>) request.getSession(false).getAttribute("RoomTypes");
            %>
            
            <div class="container">
               <% if(roomtypes != null && !roomtypes.isEmpty()){
                     for(RoomType rt : roomtypes){ %>
                <div class="row">
                    <div class="img-pic">
                        
                        <img src="<%= request.getContextPath() %>/images/<%= rt.getPicture_url() %>" >
                    </div>
                    <div class="right">
                    <h4><%=rt.getRoom_type() %></h4>
                    <p><%=rt.getPrice_per_night()%></p>
                    </div>
                </div>
                    <%}%>
               <%}%>
            </div>
    </body>
</html>
