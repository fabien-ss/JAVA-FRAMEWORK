/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import java.util.ArrayList;
import java.util.HashMap;
import utilitaire.ModelView;
import utilitaire.MyAnnotation;

/**
 *
 * @author KM
 */

public class Emp {
    ArrayList<String> listes = new ArrayList<>();
    
    public Emp(){
        listes.add("Juiliem");
        listes.add("Joseph");
    }
    @MyAnnotation(url="get-emp")
    public ModelView getAll(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        m.addItem("liste",listes);
        return m;
    }
    
    @MyAnnotation(url="add-emp")
    public ModelView insert(HashMap<String, String> donnees){
        listes.add(donnees.get("nom"));
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        m.addItem("liste", listes);
        return m;
    }
}
