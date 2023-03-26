<%-- 
    Document   : index
    Created on : 3 mars 2023, 08:42:02
    Author     : KM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="servlet//entrer">
            Entrer votre nom: <input type="text" name="nom">
            <input type="submit" value="Valider">
        </form>
        <a href="servlet/add-emp">Ajouter emp</a>
        <a href="servlet/get-emp">Afficher emp</a>
        <a href="servlet/demarrer">Demarrer voiture</a>
        <a href="servlet/get-voiture">Voiture</a
    </body>
</html>
