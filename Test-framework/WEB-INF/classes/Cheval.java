/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tChevallate file, choose Tools | TChevallates
 * and open the tChevallate in the editor.
 */
package objet;

import java.util.ArrayList;
import utilitaire.ModelView;
import utilitaire.MyAnnotation;

public final class Cheval {
    
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
    
    public Cheval(){
    }

    public Cheval(int id, String nom, String prenom, int numero){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setNumero(numero);
    }
    
    @MyAnnotation(url="get-Cheval", ParametersNames = {})
    public ModelView getAll(){
        ArrayList<Cheval> Chevals = new ArrayList<>();
        Chevals.add(new Cheval(1, "Koto", "Jean", 032));
        Chevals.add(new Cheval(2, "Jean", "Koto balida", 002));
        
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        m.addItem("liste",Chevals);
        return m;
    }
    
    @MyAnnotation(url="add-Cheval", ParametersNames = {})
    public ModelView insert(){
        ModelView m = new ModelView();
        m.setView("Cheval.jsp");
        m.addItem("Cheval", this);
        return m;
    }
    
    @MyAnnotation(url="find-Cheval" , ParametersNames = { "id" })
    public ModelView findById(int id){
        
        ModelView m = new ModelView();
        m.setView("Chevalsdetails.jsp");
        
        ArrayList<Cheval> Chevals = new ArrayList<>();
        Chevals.add(new Cheval(1, "Koto", "Jean", 032));
        Chevals.add(new Cheval(2, "Jean", "Koto balida", 002));
        
        for(Cheval e : Chevals){
            if(e.getId() == id) {
                m.addItem("Cheval", e);
                return m;
            }
        }
        return m;
    }
}
