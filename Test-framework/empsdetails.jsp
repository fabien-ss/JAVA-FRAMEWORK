<%-- 
    Document   : empsdetails
    Created on : Apr 28, 2023, 5:28:39 AM
    Author     : fabien
--%>

<%@page import="objet.Emp" contentType="text/html" pageEncoding="UTF-8"%>

<%
    String prof = (String) request.getAttribute("profil");
    String nom = (String) request.getAttribute("nom");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! <%=prof%>, ident = <%=nom%> </h1>
    </body>
</html>
