<%-- 
    Document   : sendEmail
    Created on : 17 Jun 2024, 03:03:33
    Author     : T440
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <h2>Send Email</h2>
    <form action="EmailController" method="post">
        To: <input type="email" name="to" required><br><br>
        Subject: <input type="text" name="subject" required><br><br>
        Message: <br>
        <textarea name="message" rows="10" cols="30" required></textarea><br><br>
        <input type="submit" value="Send Email">
    </form>
    </body>
</html>
