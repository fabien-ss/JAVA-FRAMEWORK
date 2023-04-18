
<%@page import="java.util.ArrayList" contentType="text/html" pageEncoding="UTF-8"%>

<%
    ArrayList<String> message = (ArrayList<String>) request.getAttribute("liste");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
    </head>
    <body>
        <% for(String m : message) { %>
            <em><%=m%></em>
        <% } %>
        tert
    </body>
</html>
