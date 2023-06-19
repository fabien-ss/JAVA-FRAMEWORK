/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import java.util.ArrayList;
import java.util.HashMap;
import utilitaire.FileUpload;
import utilitaire.ModelView;
import utilitaire.MyAnnotation;
import utilitaire.Session;

@MyAnnotation(isSegleton = true)
public final class Emp {
    
    int nombredappel = 0;
    public void setNombredappel(int i){
        this.nombredappel = i;
    }
    public int getNombredappel(){
        return this.nombredappel;
    }
    int id;
    String nom;
    String prenom;
    int numero;
    FileUpload photo;
    
    HashMap<String, Object> session = new HashMap<>();

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }


    public FileUpload getPhoto() {
        return photo;
    }

    public void setPhoto(FileUpload photo) {
        this.photo = photo;
    }
    
//e
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumero() {
        return numero;
    }
    
    public Emp(){
    }
    public Emp(int id, String nom, String prenom, int numero){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setNumero(numero);
    }
    @MyAnnotation(url="login", ParametersNames = {})
    public ModelView login(){
        ModelView m = new ModelView();
        m.addSession("isConnected", this);
        m.addSession("profil", this.getPrenom());
        m.setView("index.jsp");
        m.addItem("profi", this);
        return m;
    }
    @MyAnnotation(url="get-emp", ParametersNames = {})
    public ModelView getAll(){
        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp(1, "Koto", "Jean", 032));
        emps.add(new Emp(2, "Jean", "Koto balida", 002));
        
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        m.addItem("liste",emps);
        return m;
    }
    
    @MyAnnotation(url="add-emp", ParametersNames = {})
    public ModelView insert(){
        ModelView m = new ModelView();
        m.setView("emp.jsp");
        m.addItem("emp", this);
        return m;
    }
    
    @Session
    @MyAnnotation(url="get-connected", ParametersNames = {})
    public ModelView getConnectedUser() {
        ModelView m = new ModelView();
        m.addItem("emp", this);
        m.setView("empsdetails.jsp");
        return m;
    }
    
    @MyAnnotation(url="find-emp" , ParametersNames = { "id" }, aunth = "admin")
    public ModelView findById(int id){
        ModelView m = new ModelView();
        m.setView("empsdetails.jsp");
        
        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp(1, "Koto", "Jean", 032));
        emps.add(new Emp(2, "Jean", "Koto balida", 002));
        
        for(Emp e : emps){
            if(e.getId() == id) {
                m.addItem("emp", e);
                return m;
            }
        }
        return m;
    }
}
