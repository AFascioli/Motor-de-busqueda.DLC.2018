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
//La creamos para poder implementar compareTo y el .sort() para la lista de NodoDocumento.
public abstract class NodoAbstract implements Comparable<NodoAbstract> {
    private int frecuencia;
}
