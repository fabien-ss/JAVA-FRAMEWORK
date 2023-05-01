/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import java.util.ArrayList;
import utilitaire.ModelView;
import utilitaire.MyAnnotation;

/**
 *
 * @author KM
 */

public final class Emp {
    
    int id;
    String nom;
    String prenom;
    int numero;
    

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
    
    @MyAnnotation(url="get-emp")
    public ModelView getAll(){
        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp(1, "Koto", "Jean", 032));
        emps.add(new Emp(2, "Koto2", "Jean2", 002));
        
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        m.addItem("liste",emps);
        return m;
    }
    
    @MyAnnotation(url="add-emp")
    public ModelView insert(){
        ModelView m = new ModelView();
        m.setView("emp.jsp");
        m.addItem("emp", this);
        return m;
    }
    
    @MyAnnotation(url="find-emp")
    public ModelView findById(int id){
        
        ModelView m = new ModelView();
        m.setView("empsdetails.jsp");
        
        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp(1, "Koto", "Jean", 032));
        emps.add(new Emp(2, "Koto2", "Jean2", 002));
        
        for(Emp e : emps){
            if(e.getId() == id) {
                m.addItem("emp", e);
                return m;
            }
        }
        return m;
    }
}
