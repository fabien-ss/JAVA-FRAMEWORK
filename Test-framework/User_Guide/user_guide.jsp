<h1>Contrainte</h1>
    <h2>Classe</h2>
        <li>
            Les attributs doivent etre encapsulées sous le format getNomAttribut et setNomAttribut <br>
            remplacer NomAttribut par le nom de votre attribut.<br>
        </li>
        <li>
            Chaque fonction doit-etre annotée avec MyAnnotation de la classe Utilitaire.MyAnnotaion de framework.jar<br>
            l'annotation doit-etre initialisée comme suit:<br>
        </li>
        <div style="text-align: left; margin: auto;">
        ex:<br>
            @MyAnnotation(url = "login", ParametersNames = {})<br>
            public ModelView login(){<br>
                ModelView m = new ModelView();<br>
                m.setView("userspace.jsp");<br>
                m.addItem("user", this);<br>
                return m;<br>
            }<br>
        </div>
        avec les deux parametres url, parametresnames<br>
            dans url on donne l'url qui doit matcher avec la fonction<br>
            dans parametresnames contient les noms donnés à chaque parametre de la fonction<br>
    
    <h2>Configuration Servlet</h2><br>
        <li>Dans la balise init-param > param-value, mettre la classe commune de vos objets<br> </li>
        </li>la servlet Frontservlet est configuree pour que tous les url y tombent exceptées les fichier jsp<br></li>
    
    <h2>Liens/Formulaires</h2>
        <ul>
            <li>Si on utilise une fonction parametrée</li>
                Si on fait passer une id dans une url et que cette id est un parametre de la fonction à appellée<br>
                l'id doit correspondre au parametre demandé dans la fonction, on doit préciser le nom du/des parametre(s) dans ParametersNames <br>
                <div style="text-align: left; margin: auto;">
                    ex: html a href="detais-emp?idEmp=1">Voir tous les employés</a><br>
                        Java 
                            @MyAnnotaion (url = detais-emp, ParametersNames = { idEmp })<br>
                            fonction obtenir les employés (idEmp)<br>
                    Meme cas pour les formulaires les noms des input doivent correspondre aux noms des parametres<br>

            </div>
            <li>Si on utilise une fonction propre à la classe qui n'a pas besoin de parametre</li><br>
                Pour un formulaire, les noms des champs doivent correspondre aux attributs de la classe utilisée<br>
                ex: 
                <div style="text-align: left; margin: auto;">
                    html <br>
                           input type='text' name='nom'<br>
                    Java<br>
                        public class emp{<br>
                            String nom;<br>
                            public void setNom(String nom) { this.nom = nom; }<br>
                            public String getNom() { return this.nom; }<br>
                            @MyAnnotaion (url = inserer-emp, ParametersNames = {})<br>
                            public ModelView insert(){
                                fonction d'insertion vers une base de donnée ou autre<br>
                                this.insert();<br>
                            }<br>
                        }<br>
                </div>
        </ul><br>
        NB: 