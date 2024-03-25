<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Framework Documentation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1, h2, h3 {
            color: #333;
        }
        code {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            padding: 2px 4px;
            border-radius: 4px;
        }
        pre {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Framework Documentation</h1>

    <h2>Configuration</h2>

    <h3>Requirements</h3>
    <ul>
        <li>Tomcat 9 or lower</li>
    </ul>

    <h3>web.xml</h3>
    <p>First, create your objects in a well-distinct package.</p>
    <p>Copy the name of this package into the configuration file <code>web.xml</code> of the application in the value of the <code>init-param</code> tag with the name <code>package_name</code>.</p>
    <p>In <code>session_name</code>, put the session name you want to use.</p>
    <p>And in <code>profil_name</code>, put the profile name, for example "profile", which will serve as your profile identification.</p>

    <h3>External Dependent Library</h3>
    <ul>
        <li><code>gson.jar</code> to be placed in your application's library.</li>
    </ul>

    <h3>Execution</h3>
    <p>Copy the <code>etu2004Framework.jar</code> into the <code>lib</code> folder of your project.</p>
    <p>In the <code>do.sh</code> file, replace <code>chemin_webapps</code> with the path where the Java Web Applications (Tomcat server) are located on your computer.</p>
    <p>In <code>chemin_bin</code>, the path to the <code>bin</code> folder of your Tomcat application.</p>
    <p>In <code>package_name</code>, the <code>package_name</code> containing your objects.</p>
    <p>In <code>application_dir</code>, the path to your Java application.</p>

    <h2>ModelView</h2>
    <p>Contains the following attributes (all encapsulated) :</p>
    <ul>
        <li><code>String View = "index.jsp";</code> // here the redirection page of your data</li>
        <li><code>HashMap&lt;String, Object&gt; data = new HashMap&lt;&gt;;</code> // here to put your data to display via <code>addItem(name, value)</code></li>
        <li><code>HashMap&lt;String, Object&gt; sessions = new HashMap&lt;&gt;;</code> // here we will contain the session information to be stored in the sessions, you can use <code>addSession(name, value)</code></li>
        <li><code>ArrayList&lt;String&gt; sessionName = new ArrayList&lt;&gt;;</code> // here the sessions to be deleted, to be declared if you want to delete</li>
        <li><code>boolean invalidateSession = false;</code> // here to delete all sessions, <code>true</code> if you want to delete all</li>
        <li><code>boolean isJSON = false;</code> // here to specify if the return is a JSON and not the ModelView</li>
    </ul>

    <h3>Example</h3>
    <pre>
ModelView m = new ModelView(); // instantiation of the ModelView class
m.addSession("nom", this.nom); // registering the name in the sessions
m.addItem("prenom", this.prenom); // prenom will be sent to the view
m.setView("index.jsp"); // The view
ArrayList&lt;String&gt; ses = new ArrayList&lt;&gt;; // list of sessions to delete
ses.add("profil"); // we will delete "profil"
m.setSessionName(ses); // add the list to ModelView
m.setInvalidateSession(false); // do not delete the sessions
m.setIsJSON(false); // no JSON
return m;
    </pre>

    <h2>Annotations</h2>
    <h3>@MyAnnotation</h3>
    <p>Contains the following attributes :</p>
    <ul>
        <li><code>public boolean restApi() default false;</code> // here to declare on the functions to say if it returns a ModelView or a JSON</li>
        <li><code>public boolean isSingleton() default false;</code> // here to define if you want to use 1 single instance of the object for this type in all the application, memory aid</li>
        <li><code>public String url() default"";</code> // here to match which URL goes to which function</li>
        <li><code>public String auth() default "";</code> // here to define the necessary permission for the function to work</li>
        <li><code>public String[] ParametersNames() default {};</code> // here if you make a form, you have to specify the names of the fields, for array fields make sure to put exactly as in the form (follow with [])</li>
    </ul>

    <h3>@Session</h3>
    <p>To be declared on a function if it needs session data to be stored in an attribute that you must create and encapsulate called <code>HashMap&lt;String, Object&gt; session</code> of the class <code>java.util.HashMap</code>.</p>

    <h3>Example</h3>
    <pre>
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
    </pre>

    <h3>@restApi</h3>
    <p>Here to be declared on a function in case it returns an object to be cast to JSON.</p>

    <h3>Example</h3>
    <pre>
@restApi
@MyAnnotation(url="testApi", ParametersNames = {})
public Emp numeroByAnnotation(){
    return new Emp(2, "Jean", "Koto Balida", 002);
}
    </pre>

    <h2>Your Classes</h2>
    <p>Make sure to properly adjust the annotations.</p>
    <p>In case of file upload, if you want to have a photo attribute in your object, you can use the <code>FileUpload</code> class in the framework, located at <code>import utilitaire.FileUpload;</code>.</p>
    <p>To specify that if you want to instantiate an object from a form or another method, the names of the fields and the names of the attributes must correspond, you are not forced to initialize all the attributes.</p>
</body>
</html>
