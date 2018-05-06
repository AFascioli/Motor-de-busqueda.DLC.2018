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
public class Termino {
    
    private String id_termino;
    private int frecuenciaMax;
    private int cantDocumentos;

    public Termino(String id_termino, int frecuenciaMax, int cantDocumentos) {
        this.id_termino = id_termino;
        this.frecuenciaMax = frecuenciaMax;
        this.cantDocumentos = cantDocumentos;
    }

    public Termino() {
    }

            
    public String getId_termino() {
        return id_termino;
    }

    public void setId_termino(String id_termino) {
        this.id_termino = id_termino;
    }

    public int getFrecuenciaMax() {
        return frecuenciaMax;
    }

    public void setFrecuenciaMax(int frecuenciaMax) {
        this.frecuenciaMax = frecuenciaMax;
    }

    public int getCantDocumentos() {
        return cantDocumentos;
    }

    public void setCantDocumentos(int cantDocumentos) {
        this.cantDocumentos = cantDocumentos;
    }
        
}
