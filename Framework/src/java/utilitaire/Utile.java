package utilitaire;
import etu2004.framework.Mapping;

import java.lang.reflect.*;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fabien
 */
public class Utile {
    public static void setUserDataSession(Object objet, Method methode, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(methode.isAnnotationPresent(Session.class)){
            String setterName = "setSession";
            Enumeration<String> sessionsNames = request.getSession().getAttributeNames();
            HashMap<String, Object> allSession = new HashMap<>();
            while (sessionsNames.hasMoreElements()) {
                String nextElement = sessionsNames.nextElement();
                Object sessionObject = request.getSession().getAttribute(nextElement);
                allSession.put(nextElement, sessionObject);
            }
            Method set = objet.getClass().getDeclaredMethod(setterName, HashMap.class);
           // set.invoke(objet, new HashMap<>()); tsy azoko intsony ito anh
            set.invoke(objet, allSession);
        }
    }
//fonction checkAutorisation
    public static void checkAuthorisation(Method methode, HttpSession session, String profileName)throws Exception{
        if(methode.getDeclaredAnnotation(MyAnnotation.class) != null){
            if(!"".equals(methode.getDeclaredAnnotation(MyAnnotation.class).aunth())){
                MyAnnotation authn = methode.getDeclaredAnnotation(MyAnnotation.class);
                String profile = authn.aunth();// session.getAttribute(uri)

                if(profile.equals("admin")){
                    if(!profile.equals(session.getAttribute(profileName)) ){
                       throw new Exception("Can't acces "+methode.getName());
                    }
                }
            }
        }
    }
//fonction default setter
    public static Object getDefaultValues(Field objet){
        if(objet.getType().isPrimitive()){
            if (objet.getType() == int.class) {
                return 0;
            } else if (objet.getType() == short.class) {
                return (short) 0;
            } else if (objet.getType() == long.class) {
                return 0L;
            } else if (objet.getType() == float.class) {
                return 0.0f;
            } else if (objet.getType() == double.class) {
                return 0.0;
            } else if (objet.getType() == byte.class) {
                return (byte) 0;
            } else if (objet.getType() == char.class) {
                return '\u0000';
            } else if (objet.getType() == boolean.class) {
                return false;
            } else if (objet.getType() == float.class) {
                return 0.0f;
            } else if (objet.getType() == double.class) {
                return 0.0;
            } else if (objet.getType() == byte.class) {
                return (byte) 0;
            } else if (objet.getType() == char.class) {
                return '\u0000';
            } else if (objet.getType() == boolean.class) {
                return false;
            }
            else if (objet.getType() == String.class) {
                return "";
            }
        }
        return null;
    }
//fonction pour reset attribut
    public static void resetAttributeToDefault(Object objet) throws Exception{
        Field[] fields = objet.getClass().getDeclaredFields();
        for(Field f : fields){
            if(!f.getName().equals("nombredappel")){
                String setter = Utile.setters(f.getName());
                Method method = objet.getClass().getDeclaredMethod(setter, f.getType());
                method.invoke(objet, Utile.getDefaultValues(f));
            }
            if(f.getName().equals("nombredappel")){
                Method getterMethode = objet.getClass().getDeclaredMethod("getNombredappel");
                Method method = objet.getClass().getDeclaredMethod("setNombredappel", int.class);
                method.invoke(objet, ((int)getterMethode.invoke(objet)) + 1);
            }
        }
    }
//fonction pour traiter les requête du type normal
    public static Object request_traitor(Object objet, Object retour, HttpServletRequest request, Method methode) throws Exception{
        Enumeration<String> paramNames = request.getParameterNames();
        if(paramNames.hasMoreElements()){
            java.lang.Class[] paramtypesclasses = methode.getParameterTypes();
            HashMap<String, Object[]> parametres = new HashMap<>();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                Object[] paramValues = request.getParameterValues(paramName);
                parametres.put(paramName, paramValues);
            }
            objet = utilitaire.Utile.buildObject(objet, parametres);
            if(paramtypesclasses.length == 0){
                retour = (ModelView) methode.invoke(objet);
            }
            if(paramtypesclasses.length > 0){ 
                retour = Utile.setMethodsParameters(parametres, methode, objet);
            }
        }
        else{
            retour = methode.invoke(objet);
        }
        return retour;
    }
//fonction pour traiter ceux avec multipart/form-data
    public static Object request_multipart_traitor(Object objet, Object retour, HttpServletRequest request, Method methode) throws Exception{
        Collection<Part> parts = request.getParts();
        if(!parts.isEmpty()){
            java.lang.Class[] paramtypesclasses = methode.getParameterTypes();
            HashMap<String, Object[]> parametres = new HashMap<>();
            for (Part part : parts) {
                if (part.getContentType() == null) {
                    String fieldName = part.getName();
                    Object[] paramValues = request.getParameterValues(fieldName);
                    parametres.put(fieldName, paramValues);
                } else {
                    String fileName = part.getName();
                    Part filePart = request.getPart(fileName);
                    InputStream inputStream = filePart.getInputStream();
                    byte[] fileBytes = inputStream.readAllBytes();
                    String imageDirectory = "./";
                    
                    FileUpload file = new FileUpload();
                    file.setBytes(fileBytes);
                    file.setName(part.getSubmittedFileName());
                    file.setPath(imageDirectory+fileName);
                    FileUpload[] fichiers = new FileUpload[1];
                    fichiers[0] = file;
                    parametres.put(fileName, fichiers);
                }
            }
            objet = utilitaire.Utile.buildObject(objet, parametres);
            if(paramtypesclasses.length == 0) retour = (ModelView) methode.invoke(objet);
            if(paramtypesclasses.length > 0) retour = Utile.setMethodsParameters(parametres, methode, objet);   
        }
        else {
            retour = methode.invoke(objet);
        }
        return retour;
    }
//fonction pour attribuer les paramàetre à la fonction et directement prendre l'objet modelview
    public static Object setMethodsParameters(HashMap<String, Object[]> parametres, Method methode, Object objet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object retour;
        Parameter[] parametresfonction = methode.getParameters();
        Object[] values = new Object[parametresfonction.length];
        MyAnnotation annotation = methode.getDeclaredAnnotation(MyAnnotation.class);
        String[] parametersclasses = annotation.ParametersNames(); //maka anaran'ireo parametre anle fonction
        for (Map.Entry<String, Object[]> entry : parametres.entrySet()) { //pour chaque clé / clé 
            String key = entry.getKey();
            for(int i = 0; i < parametersclasses.length; i++){
                if(parametersclasses[i].equals(key)){
                    Object[] params = parametres.get(key);
                    if(params.length == 1){
                        values[i] = Utile.convertToPrimitive(params[0], parametresfonction[i].getType());
                        i += 1;
                    }
                    else if(params.length > 1){
                        int[] array_object_to_set = new int[params.length];
                        int j = 0;
                        for(Object p : params){
                            System.out.println("p: "+p);
                            array_object_to_set[j] = Integer.parseInt((String) p);//Utile.convertToPrimitive(p, int.class);
                            j += 1;
                        }
                        values[i] = array_object_to_set;
                        i += 1;
                    }

                }
            }
        }
        retour = methode.invoke(objet, values);
        return retour;
    }
//fonction cast
    public static Object convertToPrimitive(Object value, Class<?> type) {
        if(type.equals(int[].class)){
            int[] new_int = new int[1];
            new_int[0] = Integer.parseInt((String) value);
            return new_int;
        }
        else if (type.equals(byte.class)) {
            return Byte.valueOf(value.toString());
        } else if (type.equals(short.class)) {
            return Short.valueOf(value.toString());
        } else if (type.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (type.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (type.equals(float.class)) {
            return Float.valueOf(value.toString());
        } else if (type.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (type.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        } else if (type.equals(char.class)) {
            return value.toString().charAt(0);
        } else {
            throw new IllegalArgumentException("Type non supporté : " + type.getName());
        }
    }
//fonction pour obtenir le nom du setter ex: en entrée nom, sortie: setNom
    public static String setters(String variable_name){
        String premierelettre = variable_name.charAt(0) + "";
        String majuscule = premierelettre.toUpperCase();
        return "set"+variable_name.replaceFirst(premierelettre, majuscule);
    }
//fonction pour savoir si un string est dans un tableau de string
    public static boolean contains(String word, String[] words){
        for(String s : words){
            if(s.equals(word)){
                return true;
            }
        }
        return false;
    }
//ici instanciation de l'objet avec attribution des attributs ces derniers correspondent à ceux du formulaire
    public static Object buildObject(Object nouvelle_instance, HashMap<String, Object[]> parametres) throws Exception{
       // Object nouvelle_instance = objet;
        Field[] fields = nouvelle_instance.getClass().getDeclaredFields();
        String[] field_name = new String[fields.length];
        int i = 0;
        for(Field f : fields){
            field_name[i] = f.getName();
            i += 1;
        }
        Utile.resetAttributeToDefault(nouvelle_instance);
        
        for (Map.Entry<String, Object[]> entry : parametres.entrySet()) {
            String key = entry.getKey();
            if(contains(key, field_name)){
                Object[] values = entry.getValue();
                for (Object value : values) {
                    String setter = setters(key);
                    var field = nouvelle_instance.getClass().getDeclaredField(key);
                    Class field_type = field.getType();
                    Method m = nouvelle_instance.getClass().getDeclaredMethod(setter, field_type);
                    Object converted_value;
                    if(field_type.isPrimitive()) {
                        converted_value = Utile.convertToPrimitive((String) value, field_type);
                    } 
                    else if(field_type.equals(FileUpload.class)){
                        converted_value = value;
                    }
                    else if(field_type.equals(String.class)) {
                        converted_value = value;
                    } 
                    else if (field_type.equals(Date.class)) { //Si c'est une date mieux vaut le formatter
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date parsedDate = dateFormat.parse((String) value);
                            converted_value = new java.sql.Date(parsedDate.getTime());
                        } catch (ParseException e) {
                            throw new IllegalArgumentException("Impossible de convertir la valeur en Date : " + value);
                        }
                    } else if (field_type.equals(LocalDate.class)) {
                        converted_value = LocalDate.parse((String)value);
                    } 
                    else {
                        converted_value = field_type.cast((String)value);
                    }
                    m.invoke(nouvelle_instance, converted_value);
                }
            }
        }
        return nouvelle_instance;
    }
//ici permet de completer la variable mappingurls (prend les objets d'un package par méthode et les mets dans une hashmap
    public static HashMap<String, Mapping> getAllHashMap(String packageName) throws ClassNotFoundException, UnsupportedEncodingException, IOException, SAXException, ParserConfigurationException {
        HashMap<String, Mapping> hash = new HashMap<>();
        List<Class<?>> classes = obtenirClasses(packageName);
        for (Class cls : classes) {
            System.out.println("Class: " + cls.getName());
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
               if(method.getDeclaredAnnotation(MyAnnotation.class)!=null){
                    MyAnnotation annotation = method.getDeclaredAnnotation(MyAnnotation.class);
                    if(!"".equals(annotation.url())){
                        String url = annotation.url();
                        String classname = cls.getSimpleName();
                        String nommethod = method.getName();
                        Mapping map = new Mapping(classname, nommethod);
                        hash.put(url, map);
                    }
                }
            }
        }
        return hash;
    }
//fonction ahafahana mahazo ireo classe segleton rehetra anaty HashMap<String, Object>
    public static HashMap<String, Object> getAllSengletonClasses(String packageName) throws ClassNotFoundException, UnsupportedEncodingException, IOException, SAXException, ParserConfigurationException, InstantiationException, IllegalAccessException {
        HashMap<String, Object> hash = new HashMap<>();
        List<Class<?>> classes = obtenirClasses(packageName);
        for (Class cls : classes) {
            MyAnnotation singleton_annotation = (MyAnnotation) cls.getDeclaredAnnotation(MyAnnotation.class);
            if(singleton_annotation.isSegleton()){
                //Object objet = cls.newInstance();
                hash.put(cls.getCanonicalName(), null);
            }
        }
        return hash;
    }
    
//fonction pour obtenir toutes les classes d'un package donné
    public static List<Class<?>> obtenirClasses(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        Enumeration<java.net.URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        while (resources.hasMoreElements()) {
            java.net.URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        if (clazz.getPackage().getName().equals(packageName)) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        }
        return classes;
    }  
}

