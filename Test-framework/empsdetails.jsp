<%-- 
    Document   : empsdetails
    Created on : Apr 28, 2023, 5:28:39 AM
    Author     : fabien
--%>

<%@page import="objet.Emp" contentType="text/html" pageEncoding="UTF-8"%>

<%
    Emp emp = (Emp) request.getAttribute("emp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p>Nom <%=emp.getNom()%></p>
        <p>Prenom <%=emp.getPrenom()%></p>
        <p>Numero</p>
    </body>
</html>
