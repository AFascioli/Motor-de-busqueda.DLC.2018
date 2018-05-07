/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.logging.Logger;

/**
 *
 * @author dlcusr
 */
public class FilaRankeo implements Comparable{

    private String id_termino;
    private String documento;
    private int frecuencia;
    private double peso;

    public FilaRankeo() {
    }

    public FilaRankeo(String id_termino, String documento, int frecuencia, double peso) {
        this.id_termino = id_termino;
        this.documento = documento;
        this.frecuencia = frecuencia;
        this.peso=peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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

    public double calcularPeso(int cantDocsTerm, int cantDocsTotal)
    {
        double pesoCalc=this.frecuencia*(Math.log((cantDocsTotal/cantDocsTerm)));
        return pesoCalc;
    }
    
    
    @Override
    public int compareTo(Object o) {

        FilaRankeo fr = (FilaRankeo) o;

        if (this.peso > fr.getPeso()) {
            return 1;
        } else {
            if (this.peso == fr.getPeso()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


}
