/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objet;

import java.util.Vector;
import utils.MyAnnotation;


/**
 *
 * @author KM
 */

public class Emp {
    
    @MyAnnotation(url="/Framework/jsp/parler")
    public void parler(){
    }
    
    @MyAnnotation(url="/Framework/jsp/get-emp")
    public Vector getAll(){
        Vector<String> vs = new Vector<>();
        vs.add("ok");
        return vs;
    }
    
    @MyAnnotation(url="/Framework/jsp/add-emp")
    public void insert(){
        
    }
}
