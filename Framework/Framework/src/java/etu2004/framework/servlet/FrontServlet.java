/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu2004.framework.servlet;

import javax.servlet.RequestDispatcher;
import etu2004.framework.Mapping;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.ModelView;
import utils.Utile;

/**
 *
 * @author KM
 */
public class FrontServlet extends HttpServlet {

    HashMap<String, etu2004.framework.Mapping> MappingUrls = new HashMap<>();
   
    @Override
    public void init() throws ServletException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String path = getServletContext().getRealPath("/WEB-INF/config.xml");
            File configFile = new File(path);
            Document document = builder.parse(configFile);
            NodeList nodeList = document.getElementsByTagName("packe");
            Element element = (Element) nodeList.item(0);
            String packageName = element.getElementsByTagName("package-listening").item(0).getTextContent();
            
            setMappingUrls(Utile.getAllHashMap(packageName));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SAXException | ParserConfigurationException | ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, SAXException, ParserConfigurationException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(request.getRequestURI());
            out.println("mety oa");
            String[] ms = request.getRequestURI().split("/");
            String nomMethode = ms[3];
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String path = getServletContext().getRealPath("/WEB-INF/config.xml");
            File configFile = new File(path);
            Document document = builder.parse(configFile);
            NodeList nodeList = document.getElementsByTagName("packe");
            Element element = (Element) nodeList.item(0);
            String packageName = element.getElementsByTagName("package-listening").item(0).getTextContent();
            String nomDeClasse = packageName+"."+(String) MappingUrls.get(nomMethode).getClassName();
            java.lang.Class cl = java.lang.Class.forName(nomDeClasse);//Class.forName(MappingUrls.get(request.getRequestURI()).getClassName());
            Object objet = cl.newInstance();           
            String method = (String) MappingUrls.get(nomMethode).getMethod();
            Method methode = objet.getClass().getDeclaredMethod(method);
            Object retour = (ModelView) methode.invoke(objet);
            out.println(((ModelView) retour).getView());
            try{
                out.println(((ModelView) retour).getView());
//                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+((ModelView) retour).getView());
//                requestDispatcher.forward(request,response);
            }
            catch(Exception e){
                throw new Exception("Your servlet doesn't match any function");
            }
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
