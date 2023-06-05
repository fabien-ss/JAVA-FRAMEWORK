/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu2004.framework.servlet;

import etu2004.framework.Mapping;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import utilitaire.FileUpload;
import utilitaire.ModelView;
import utilitaire.Utile;

/**
 *
 * @author KM
 */
@WebServlet
@MultipartConfig(location = "./")
public class FrontServlet extends HttpServlet {

    HashMap<String, etu2004.framework.Mapping> MappingUrls = new HashMap<>();
   
    @Override
    public void init() throws ServletException {
        try {      
            String packageName = getInitParameter("package_name");
            setMappingUrls(Utile.getAllHashMap(packageName));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SAXException | ParserConfigurationException | ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        String contentType = request.getContentType();
        
        PrintWriter out = response.getWriter();
        out.println(contentType);
        
        
       
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String nomMethode = uri.substring(context.length()+1);
        String packageName = getInitParameter("package_name");       
        String nomDeClasse = packageName+"."+(String) MappingUrls.get(nomMethode).getClassName();
        java.lang.Class cl = java.lang.Class.forName(nomDeClasse);

        Object objet = cl.newInstance();           

        String method = (String) MappingUrls.get(nomMethode).getMethod();

        Method methode = null;

        Method[] methodes = objet.getClass().getDeclaredMethods();
        for(Method m : methodes){
            if(m.getName().contains(method)){
                methode = m;
            }
        }
        
        
        Object retour = new Object();
          
        if(contentType != null){
            
            Collection<Part> parts = request.getParts();
            if(!parts.isEmpty()){
                out.println("manana");
                java.lang.Class[] paramtypesclasses = methode.getParameterTypes();

                HashMap<String, Object[]> parametres = new HashMap<>();

                for (Part part : parts) {
                    if (part.getContentType() == null) {
                        String fieldName = part.getName();
                        Object[] paramValues = request.getParameterValues(fieldName);
                        parametres.put(fieldName, paramValues);
                    } else {
                        // Faites quelque chose avec le champ de formulaire...
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

                objet = utilitaire.Utile.buildObject(cl, parametres);

                if(paramtypesclasses.length == 0){
                    retour = (ModelView) methode.invoke(objet);
                }

                if(paramtypesclasses.length > 0){ 
                   retour = Utile.setMethodsParameters(parametres, methode, objet);   
                }
            }
            else {
                out.println("tsy manana");
                retour = (ModelView) methode.invoke(objet);
            }
        }
        else if(contentType == null){
            Enumeration<String> paramNames = request.getParameterNames();
            
            if(paramNames.hasMoreElements()){
                java.lang.Class[] paramtypesclasses = methode.getParameterTypes();

                HashMap<String, Object[]> parametres = new HashMap<>();

                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String[] paramValues = request.getParameterValues(paramName);
                    parametres.put(paramName, paramValues);
                }

                objet = utilitaire.Utile.buildObject(cl, parametres);
                out.println("tena misy exe^tion");

                // fonction mandray HashMap fonction ane fonction

                if(paramtypesclasses.length == 0){
                    out.println("non eh");
                    retour = (ModelView) methode.invoke(objet);
                }

                if(paramtypesclasses.length > 0){ 

                    retour = Utile.setMethodsParameters(parametres, methode, objet);
                }
            }
            else{
                out.println("tsy manana");
                retour = (ModelView) methode.invoke(objet);
            }
            
        }
            
        try{
            ModelView m = (ModelView) retour;
            String key = null;
            for (Map.Entry<String, Object> entry : m.getData().entrySet()) {
                key = (String) entry.getKey();
                request.setAttribute((String) key, m.getData().get(key));
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+((ModelView) retour).getView());
            requestDispatcher.forward(request,response);
        }
        catch(Exception e){
            out.println(e.fillInStackTrace());
            out.print(e.getClass());
        }    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SAXException | ParserConfigurationException ex) {
               out.println(ex);
        } catch (Exception ex) {
            out.println(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
           out.println(ex);
        } catch (SAXException ex) {
            out.println(ex);
        } catch (ParserConfigurationException ex) {
            out.println(ex);
        } catch (Exception ex) {
            out.println(ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> MappingUrls) {
        this.MappingUrls = MappingUrls;
    }
}