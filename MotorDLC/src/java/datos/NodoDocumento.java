/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author dlcusr
 */
public class NodoDocumento extends NodoAbstract {
    private String id_documento;
    private int frecuencia;

    public NodoDocumento(String id_documento, int frecuencia) {
        this.id_documento = id_documento;
        this.frecuencia = frecuencia;
        
    }
    
    public NodoDocumento()
    {
        this.id_documento = "";
        this.frecuencia = 0;
    }

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
    
    public void aumentarFrecuencia()
    {
        this.frecuencia= this.frecuencia +1;
    }
    
    @Override
    public int compareTo(NodoAbstract nodo) {
        
         NodoDocumento nodoaux= (NodoDocumento) nodo;
       
        if(this.frecuencia > nodoaux.getFrecuencia()) 
        {
            return 1;
        }
        else
        {
            if(this.frecuencia == nodoaux.getFrecuencia()){ return 0; }
            else { return -1; }
        }
        
    }
    
}
