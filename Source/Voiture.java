/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objet;

import utils.ModelView;
import utils.MyAnnotation;

/**
 *
 * @author fabien
 */
public class Voiture {
    @MyAnnotation(url="demarrer")
    public ModelView demarrer(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }
    @MyAnnotation(url="get-voiture")
    public ModelView getVoite(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }
}
