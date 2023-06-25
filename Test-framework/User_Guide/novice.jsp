<h1>Contrainte</h1>
    <h2>Classe</h2>
        Chaque fonction doit-etre annotée avec MyAnnotation de la classe Utilitaire.MyAnnotaion de framework.jar
        <br>
            l'annotaion doit-etre initialiser comme suit:
        <br> 
        <img src=".assets/images/exemple.png" width="100%" height="400px">
        <br>
        avec les deux parametres url, parametresnames
        <ul>
            <li>dans url on donne l'url qui doit correspondre a la fonction</li>
            <li>dans parametresnames contient les noms donnés à chaque parametre de chaque fonction</li>
        </ul>
    <h2>Configuration Servlet</h2>
    <img src=".assets/images/servlet.png" width="100%" height="400px">
    <ul>
        <li>dans la balise init-param > param-value, mettre la classe commune de vos objets</li>
        <li>la servlet Frontservlet est configuree pour que tous les url y tombent</li>
    </ul>
    <h2>Liens</h2>
    le lien contenu dans la balise doit correspondre a l'une des fonctions <br>
    dans la classe
    <img src=".assets/images/reffon.png" width="100%" height="400px">
    le Lien
    <img src=".assets/images/ref.png" width="100%" height="400px">
    Pour faire passer une id 
    <img src=".assets/images/lien.png" width="100%" height="400px">
    eventuellement id serra un des parametres de la fonction à appellée, on precise le nom du/des parametre(s)
    <img src=".assets/images/para.png" width="100%" height="400px">