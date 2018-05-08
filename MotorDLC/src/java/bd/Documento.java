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
public class Documento implements Comparable{
    
    private String nombre;
    private double peso;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Documento() {
    }

    public Documento(String nombre, double peso,String titulo) {
        this.nombre = nombre;
        this.peso = peso;
        this.titulo=titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(Object o) {
        Documento doc = (Documento) o;

        if (this.peso > doc.getPeso()) {
            return -1;
        } else {
            if (this.peso == doc.getPeso()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    
    
}
