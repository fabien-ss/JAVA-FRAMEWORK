package utilitaire;
import etu2004.framework.Mapping;

import java.lang.reflect.*;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author KM
 */
public class Utile {
     // fonction mandray HashMap fonction anle fonction
    
    public static Object setMethodsParameters(HashMap<String, String> parametre, Method methode, Object objet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object retour;
        
        Parameter[] parametresfonction = methode.getParameters();
        Object[] values = new Object[parametresfonction.length];
        
        MyAnnotation annotation = methode.getDeclaredAnnotation(MyAnnotation.class);
        String[] parametersclasses = annotation.ParametersNames();
        
        for (Map.Entry<String, String> entry : parametre.entrySet()) {
            String key = entry.getKey();
            for(int i = 0; i < parametersclasses.length; i++){
                if(parametersclasses[i].equals(key)){
                    values[i] = Utile.convertToPrimitive(parametre.get(key), parametresfonction[i].getType());
                    i += 1;
                }
            }
        }
        
        //retour = methode.invoke(objet, values)
        
        retour = methode.invoke(objet, values);
        
        return retour;
    }    
    public static Object convertToPrimitive(Object value, Class<?> type) {
        if (type.equals(byte.class)) {
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

    
    public static String setters(String variable_name){
        String premierelettre = variable_name.charAt(0) + "";
        String majuscule = premierelettre.toUpperCase();
        return "set"+variable_name.replaceFirst(premierelettre, majuscule);
    }
    
    public static Object buildObject(java.lang.Class classe, HashMap<String, String> parametres) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, ParseException{
        Object nouvelle_instance = classe.newInstance();
        for (Map.Entry<String, String> entry : parametres.entrySet()) {
            
                String key = entry.getKey();
                String value = entry.getValue();
                String setter = setters(key);
                
                var field = nouvelle_instance.getClass().getDeclaredField(key);
                
                Class field_type = field.getType();
                
                Method m = nouvelle_instance.getClass().getDeclaredMethod(setter, field_type);
                
                value = value.trim();
                
                Object converted_value;
                if(field_type.isPrimitive()) {
                    converted_value = Utile.convertToPrimitive(value, field_type);
                } 
                else if(field_type.equals(String.class)) {
                    converted_value = value;
                } 
                else if (field_type.equals(Date.class)) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(value);
                    converted_value = new java.sql.Date(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Impossible de convertir la valeur en Date : " + value);
                }
                } else if (field_type.equals(LocalDate.class)) {
                    converted_value = LocalDate.parse(value);
                } 
                else {
                    converted_value = field_type.cast(value);
                }
                m.invoke(nouvelle_instance, converted_value);
        }
        return nouvelle_instance;
    }
    
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
        //}
        return hash;
    }

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
    public static void main(String argv[]) throws Exception {
        
    }
}

