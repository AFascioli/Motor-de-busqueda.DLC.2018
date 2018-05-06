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
    private Posteo posteo;

    public Termino(String id_termino, int frecuenciaMax, int cantDocumentos, Posteo posteo) {
        this.id_termino = id_termino;
        this.frecuenciaMax = frecuenciaMax;
        this.cantDocumentos = cantDocumentos;
        this.posteo = posteo;
    }

    public Termino() {
    }

    public Posteo getPosteo() {
        return posteo;
    }

    public void setPosteo(Posteo posteo) {
        this.posteo = posteo;
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
    //Corregir
    //Seguramente vamos a buscar el posteo del termino preguntar el tamaño y actualizar
    public void actualizarCantidadDoc()
    {
        this.cantDocumentos = this.posteo.getLista().size();
    }
    
}
