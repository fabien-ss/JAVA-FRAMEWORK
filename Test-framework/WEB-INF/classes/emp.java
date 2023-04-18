/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import java.util.ArrayList;
import utils.ModelView;
import utils.MyAnnotation;


/**
 *
 * @author KM
 */

public class emp {
    String nom;
    
    public emp(){}
    public emp(String nom){
        this.nom = nom;
    }
    @MyAnnotation(url="parler")
    public ModelView parler(){
        ModelView m = new ModelView();
        
        m.setView("liste.jsp");
        return m;
    }
    
    @MyAnnotation(url="get-emp")
    public ModelView getAll(){
        ArrayList<emp> emps = new ArrayList<>();
        emps.add(new emp("Koto"));
        emps.add(new emp("Julien"));
        ModelView m = new ModelView();
        m.setView("listeemp.jsp");
        m.addItem("listeEmp", emps);
        return m;
    }
    
    @MyAnnotation(url="add-emp")
    public ModelView insert(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
