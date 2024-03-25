# Framework Documentation

This is a Java framework.

## Configuration

### Requirements

- Tomcat 9 or lower

### web.xml

First, create your objects in a well-distinct package.

Copy the name of this package into the configuration file `web.xml` of the application in the value of the `init-param` tag with the name `package_name`.

In `session_name`, put the session name you want to use.

And in `profil_name`, put the profile name, for example "profile", which will serve as your profile identification.

### External Dependent Library

- `gson.jar` to be placed in your application's library.

### Execution

Copy the `etu2004Framework.jar` into the `lib` folder of your project.

In the `do.sh` file, replace `chemin_webapps` with the path where the Java Web Applications (Tomcat server) are located on your computer.

In `chemin_bin`, the path to the `bin` folder of your Tomcat application.

In `package_name`, the `package_name` containing your objects.

In `application_dir`, the path to your Java application.

## ModelView

Contains the following attributes (all encapsulated):

- `String View = "index.jsp";` // here the redirection page of your data
- `HashMap<String, Object> data = new HashMap<>();` // here to put your data to display via `addItem(name, value)`
- `HashMap<String, Object> sessions = new HashMap<>();` // here we will contain the session information to be stored in the sessions, you can use `addSession(name, value)`
- `ArrayList<String> sessionName = new ArrayList<>();` // here the sessions to be deleted, to be declared if you want to delete
- `boolean invalidateSession = false;` // here to delete all sessions, `true` if you want to delete all
- `boolean isJSON = false;` // here to specify if the return is a JSON and not the ModelView

### Example
```
java ModelView m = new ModelView(); // instantiation of the ModelView class 
m.addSession("nom", this.nom); // registering the name in the sessions 
m.addItem("prenom", this.prenom); // prenom will be sent to the view 
m.setView("index.jsp"); // The view 
ArrayList<String> ses = new ArrayList<>(); // list of sessions to delete 
ses.add("profil"); // we will delete "profil" 
m.setSessionName(ses); // add the list to ModelView 
m.setInvalidateSession(false); // do not delete the sessions 
m.setIsJSON(false); // no JSON return m;
```
## Annotations

### @MyAnnotation

Contains the following attributes:

- `public boolean restApi() default false;` // here to declare on the functions to say if it returns a ModelView or a JSON
- `public boolean isSingleton() default false;` // here to define if you want to use 1 single instance of the object for this type in all the application, memory aid
- `public String url() default"";` // here to match which URL goes to which function
- `public String auth() default "";` // here to define the necessary permission for the function to work
- `public String[] ParametersNames() default {};` // here if you make a form, you have to specify the names of the fields, for array fields make sure to put exactly as in the form (follow with [])

### @Session

To be declared on a function if it needs session data to be stored in an attribute that you must create and encapsulate called `HashMap<String, Object> session` of the class `java.util.HashMap`.

### Example

```
java
@Session @MyAnnotation(url="get-connected", ParametersNames = {})
 public ModelView getConnectedUser() {
ModelView m = new ModelView();
m.addItem("profil", this.getSession().get("profil"));
m.addItem("emp", this.getSession().get("isConnected"));
m.addItem("nom", this.getSession().get("nom"));
m.setView("empsdetails.jsp"); return m; }
```


### @restApi

Here to be declared on a function in case it returns an object to be cast to JSON.

### Example

```
java
@restApi @MyAnnotation(url="testApi", ParametersNames = {})
public Emp numeroByAnnotation(){ return new Emp(2, "Jean", "Koto Balida", 002); }
```


## Your Classes

Make sure to properly adjust the annotations.

In case of file upload, if you want to have a photo attribute in your object, you can use the `FileUpload` class in the framework, located at `import utilitaire.FileUpload;`.

To specify that if you want to instantiate an object from a form or another method, the names of the fields and the names of the attributes must correspond, you are not forced to initialize all the attributes.

