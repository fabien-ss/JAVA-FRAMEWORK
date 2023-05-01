
<%@page import="java.util.ArrayList, objet.Emp" contentType="text/html" pageEncoding="UTF-8"%>

<%
    ArrayList<Emp> emps = (ArrayList<Emp>) request.getAttribute("liste");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
    </head>
    <body>
        <table style="margin: auto; border: 1px solid">
            <tr>
                <th>Nom</th>
                <th>Prenom</th>
                <th>Numero</th>
            </tr>
            <% for(Emp e : emps) { %>
                <tr>
                    <td><%=e.getNom()%></td>
                    <td><%=e.getPrenom()%></td>
                    <td><%=e.getNumero()%></td>
                    <td><a href="find-emp?id=<%=e.getId()%>">voir</a></td>
                </tr>
            <% } %>
        </table>
        <style>
            th, td{
                width: 20%;
                border: 1px solid;
            }
        </style>
    </body>
</html>
