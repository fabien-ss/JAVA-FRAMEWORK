/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import utils.ModelView;
import utils.MyAnnotation;


/**
 *
 * @author KM
 */

public class Emp {
    
    @MyAnnotation(url="/Framework/parler")
    public ModelView parler(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }
    
    @MyAnnotation(url="get-emp")
    public ModelView getAll(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }
    
    @MyAnnotation(url="add-emp")
    public ModelView insert(){
        ModelView m = new ModelView();
        m.setView("liste.jsp");
        return m;
    }
}
