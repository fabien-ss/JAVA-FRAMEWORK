Ceci est un framework ecrit en java
df
- configuration
    requis
        tomcat 9 ou inferieur
    web.xml
        Premierement créer vos objet dans un package bien distinct
        copier le nom de ce package dans le fichier de configuration web.xml de l'application dans la valeur de la balise init-param ayant le nom package_name
        dans session_name mettre le nom de session que vous voulez utiliser 
        et dans profil_name mettre le nom de profil ex: "profil" qui servira d'identification de votre profil 
    librairie externe dependante
        gson.jar à mettre dans la libraire de votre application
    execution
        copier le etu2004Framework.jar dans le fichier lib de votre Project
        dans le fichier do.sh remplacer le chemin_webapps par le chemin ou se situe les applications java web (serveur tomcat) sur votre ordinateur
        dans chemin_bin le chemin vers le dossier bin de votr applciation tomcat
        dans package_name le package_name contenant vos objets
        dans application_dir le chemin de votra applications java

- ModelView
    contient les attributs suivants (tous encapsulées):
        String View = "index.jsp"; // ici la page de redirection de vos donnes
        HashMap<String, Object> data = new HashMap<>(); // ici mettre vos donnees a afficher via addItem(nom, valeur)
        HashMap<String, Object> sessions = new HashMap<>(); // ici on va contenir les informations de session à enregistrer dans les sessions, vous pouvez utiliser addSession(nom, valeur)
        ArrayList<String> sessionName = new ArrayList<>(); // ici les session à supprimer, A declerer si vous voulez supprimer
        boolean invalidateSession = false; // ici pour supprimer toutes les sessions, true si vous voulez tous supprimer
        boolean isJSON = false; // ici pour specifier si le retour est un json et non le modelView
    exemple:
            ModelView m = new ModelView(); // instanciation de la classe modelview
            m.addSession("nom", this.nom); // enregistrement du nom dans les sessions
            m.addItem("prenom", this.prenom); // prenom va etre envoyé dans la view
            m.setView("index.jsp"); // La view
            ArrayList<String> ses = new ArrayList<>(); //liste des session à supprimer
            ses.add("profil"); //on va supprimer "profil"
            m.setSessionName(ses); // ajoute de la liste dans modelview
            m.setInvalidateSession(false); //ne pas supprimer les session
            m.setIsJSON(false); // pas de json
        return m;
    exemple methode de login:
        @MyAnnotation(url="login", ParametersNames = {})
        public ModelView login(){
            ModelView m = new ModelView();
            m.addSession("isConnected", this);
            m.addSession("", m);
            m.addSession("profil", this.getPrenom());
            m.addSession("nom", this.nom);
            m.addItem("prenom", this.prenom);
            m.setView("index.jsp");
            m.addItem("profi", this);
            return m;
        }
    exemple de methode de deconnexion:
        @MyAnnotation(url="disconnect")
        public ModelView disconnect(){
            ModelView m = new ModelView();
            m.setInvalidateSession(true);
            m.setView("index.jsp");
            return m;
        }
- Les annotations
    @MyAnnotation
        il contient les attributs suivant 
            public boolean restApi() default false; // ici déclarer sur les foncitons pour dire si elle retourne un modelView ou un JSON
            public boolean isSegleton() default false; // ici pour definir si vous voulez utiliser 1 seule instance d'objet pour ce type dans toutes l'application, aide memoire
            public String url() default""; // ici pour matcher quel url va vers quel fonction 
            public String aunth() default ""; // ici pour definir la permission necessaire pour que la fonction marche
            public String[] ParametersNames() default {}; // ici si vous faite un formulaire, il faut preciser le nom des champs, pour les champs tableau veillez mettre exactement comme dans le formulaire (faire suivre de [])
    @Session
        à declarer sur une fonction si elle a besoin des donnees de session qui vont etre stocker dans un attribut que vous devez creer et encapsilee appellé HashMap<String, Object> session de ka ckasse java.util.HashMap
        exemple:
            @Session
            @MyAnnotation(url="get-connected", ParametersNames = {})
            public ModelView getConnectedUser() {
                ModelView m = new ModelView();
                m.addItem("profil", this.getSession().get("profil"));
                m.addItem("emp", this.getSession().get("isConnected"));
                m.addItem("nom", this.getSession().get("nom"));
                m.setView("empsdetails.jsp");
                return m;
            }


    @restApi
        ici à declarer sur une fonciton au cas ou elle retourne un objet a caster en json
        exemple:
            @restApi
            @MyAnnotation(url="testApi", ParametersNames = {})
            public Emp numeroByAnnoation(){
                return new Emp(2, "Jean", "Koto balida", 002);
            }

- Vos classes
    Verifier à bien ajuster les annotations
    en cas d'upload de fichier si vous voulez avoir un attribut photo dans votre objet
        vous pourvez utiliser la classe FileUpload dans le framework situe import utilitaire.FileUpload;
    à preciser que si vous voulez instancier un objet à partir d'un formulaire ou autre methide les noms des champs et les noms des attribytes doivent correspondre, on est pas forcé d'initializer toutes les attributs
