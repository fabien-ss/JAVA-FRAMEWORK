/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fabien
 */
package utilitaire;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    public boolean isSegleton() default false;
    public String url() default"";
    public String aunth() default "";
    public String[] ParametersNames() default {}; 
}
