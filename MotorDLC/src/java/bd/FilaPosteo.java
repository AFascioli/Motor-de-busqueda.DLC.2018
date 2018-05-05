/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author dlcusr
 */
public class FilaPosteo {
    
    private String id_termino;
    private String documento;
    private int frecuencia;

    public FilaPosteo() {
    }

    public FilaPosteo(String id_termino, String documento, int frecuencia) {
        this.id_termino = id_termino;
        this.documento = documento;
        this.frecuencia = frecuencia;
    }

    public String getId_termino() {
        return id_termino;
    }

    public void setId_termino(String id_termino) {
        this.id_termino = id_termino;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
    
}
