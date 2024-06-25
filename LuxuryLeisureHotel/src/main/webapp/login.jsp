<%-- 
    Document   : login
    Created on : Jun 14, 2024, 10:32:17 AM
    Author     : Train 01
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login.css">
    </head>
    <body>
        <%
           String message = (String) request.getAttribute("message");
         %>
         <jsp:include page="navbar.jsp" />
         <section class="container">
             <h1>Sign In</h1>
        
        <form action="UserController" method="post">
            email: <input name="email" type="email"> </br>
            password: <input name="password" type="password"></br>
            <% if(message != null){%><p><%=message%></p><%}%>
            <input name="submit" type="submit" value="login">
        </form>
         </section>
    </body>
</html>
