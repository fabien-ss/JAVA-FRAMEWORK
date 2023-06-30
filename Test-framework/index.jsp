<%-- 
    Document   : index
    Created on : 3 mars 2023, 08:42:02
    Author     : fabien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Bonjour</h1>
        <form action="add-emp" method="post" enctype="multipart/form-data">
            Entrer votre nom: <input type="text" name="nom" value="u">
            <br>
            Prenom <input type="text" name="prenom" value="u">
            <br><!-- comment -->
            Numero <input type="number" name="numero" value=111>
            <input type="file" name="photo">
            <input type="submit" value="Valider">
        </form>
        <a href="get-emp">Afficher emp</a>
        <a href="login.jsp">S'authentifier</a>
        <a href="get-connected">Obtenir depuis session</a>
        <a href="etudiant">Test json modelview</a>
        <a href="testApi">Test json object</a>
    </body>
</html>
