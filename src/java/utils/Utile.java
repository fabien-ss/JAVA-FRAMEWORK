package utils;
import etu2004.framework.Mapping;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fabien
 */
public class Utile {
    
    public static HashMap<String, Mapping> getAllHashMap(String packageName) throws ClassNotFoundException, UnsupportedEncodingException, IOException {
        System.out.println("io");
        HashMap<String, Mapping> hash = new HashMap<>();
     
        List<Class<?>> classes = obtenirClasses(packageName);
        for (Class cls : classes) {
            System.out.println("Class: " + cls.getName());
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
               //System.out.println("Method: " + method.getName());
               if(method.getDeclaredAnnotation(MyAnnotation.class)!=null){
                    MyAnnotation annotation = method.getDeclaredAnnotation(MyAnnotation.class);
                    if(annotation.url() != ""){
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
    public static void main(String[] args) throws Exception {
        System.out.println("on");
        String n = "objet";
        HashMap<String, Mapping> m = Utile.getAllHashMap(n);
        for(String key : m.keySet()){
            System.out.println(key+ " k "+m.get(key).getMethod() +" key " + m.get(key).getClassName());
        }
    }
}

