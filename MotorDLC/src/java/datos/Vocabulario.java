/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author dlcusr
 */
public class Vocabulario {
    private Map vocabulario;

    public Vocabulario() {
    }

    public Vocabulario(Map vocabulario) {
        this.vocabulario = vocabulario;
    }

    public Map getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(Map vocabulario) {
        this.vocabulario = vocabulario;
    }
    
    public ArrayList obtenerTerminosConsulta(String consulta){ //Toma la consulta y devuelve un Arraylist de terminos ordenado segun cantidad de documento (ascendente)
        
        ArrayList <Termino>res=new ArrayList<>();
        String aux2=consulta.toUpperCase();
        
        String []aux=aux2.split(" ");
        
        for (int i = 0; i < aux.length; i++) {
            res.add((Termino) this.vocabulario.get(aux[i]));
            
        }
        
        Collections.sort(res);
        
        return res;
    } 
}
