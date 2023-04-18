package utilitaire;
import etu2004.framework.Mapping;

import java.lang.reflect.*;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public static void main(String argv[]) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
  
        //   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       //Utile.getAllHashMap();
//            
//            // Affichage de l'URL
//            System.out.println("URL de la base de données : " + url);
//        System.out.println(Utile.getAllHashMap());
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        
//        File f = new File("config.xml");
//        Document document = builder.parse(f);
//        System.out.println(f.getAbsoluteFile());
//        NodeList nodeList = document.getElementsByTagName("packe");
//        Element element = (Element) nodeList.item(0);
//        String packageName = element.getElementsByTagName("package-listening").item(0).getTextContent();
//           System.out.println(packageName);
    }
}

