<%-- 
    Document   : test
    Created on : 14 Jun 2024, 22:45:23
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
        <h1>Hello World!</h1>
         <form action="sendEmail" method="post">
            <label for="to">To:</label>
            <input type="email" id="to" name="to" required><br><br>

            

            <input type="submit" value="Send Email">
        </form>
    </body>
</html>
