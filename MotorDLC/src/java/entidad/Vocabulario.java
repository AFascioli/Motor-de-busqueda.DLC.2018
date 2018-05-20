package entidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;

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
        StringTokenizer token=new StringTokenizer(aux2 , " $/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}`´&|%°<>~©ª¬'±+");
        
        while(token.hasMoreTokens())
        {
            Termino termAux=(Termino) this.vocabulario.get(token.nextToken());
            
            if (termAux!=null) {
                res.add(termAux);
            }
            
        }
             
        Collections.sort(res);
        
        return res;
    } 
}
