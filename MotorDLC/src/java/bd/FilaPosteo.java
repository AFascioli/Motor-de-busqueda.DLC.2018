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
public class FilaPosteo implements Comparable {

    private String id_termino;
    private String documento;
    private int frecuencia;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public FilaPosteo() {
    }

    public FilaPosteo(String id_termino, String documento, int frecuencia, String titulo) {
        this.id_termino = id_termino;
        this.documento = documento;
        this.frecuencia = frecuencia;
        this.titulo=titulo;
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

    @Override
    public int compareTo(Object o) {

        FilaPosteo fp = (FilaPosteo) o;

        if (this.frecuencia > fp.getFrecuencia()) {
            return 1;
        } else {
            if (this.frecuencia == fp.getFrecuencia()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public void aumentarFrecuencia() {
        this.frecuencia++;
    }
}
