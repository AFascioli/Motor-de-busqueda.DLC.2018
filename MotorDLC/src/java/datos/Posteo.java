/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author dlcusr
 */

public class Posteo {

    private String id_termino;
    private ArrayList<NodoDocumento> lista;

    public Posteo(String id_termino, ArrayList<NodoDocumento> lista) {
        this.id_termino = id_termino;
        this.lista = lista;
    }
    
    public Posteo(){}

    public String getId_termino() {
        return id_termino;
    }

    public void setId_termino(String id_termino) {
        this.id_termino = id_termino;
    }

    public ArrayList<NodoDocumento> getLista() {
        return lista;
    }

    public void setLista(ArrayList<NodoDocumento> lista) {
        this.lista = lista;
    }
    
    public void addDoc(NodoDocumento nodo)
    {
        this.lista.add(nodo);

    }
    
    public void deleteDoc(String id_docp)
    {
        for (int i = 0; i < this.lista.size(); i++) {
     if (this.lista.get(i).getId_documento() == null ? id_docp == null : this.lista.get(i).getId_documento().equals(id_docp))
        {
            this.lista.remove(i);
            break;
        }
        }
    }
    
    //Hay metodo getNodo() por si tenemos el nombre del doc y por posicion porque seguramente vamos a querer
    //obtener los primeros dos o tres, etc.
    public NodoDocumento getNodo(String id_docp)
    {        
        for (int i = 0; i < this.lista.size(); i++) {
        if (this.lista.get(i).getId_documento() == null ? id_docp == null : this.lista.get(i).getId_documento().equals(id_docp))
        {
            return  this.lista.get(i);
        }
        }
     return null;
    }
    
    public int getNodoIndex(String id_docp) //Devuelve el index dado el nombre del documento, si no esta devuelve -1
    {        
        for (int i = 0; i < this.lista.size(); i++) {
        if (this.lista.get(i).getId_documento() == null ? id_docp == null : this.lista.get(i).getId_documento().equals(id_docp))
        {
            return  i;
            
        }
        }
     return -1;
    }
    
    public NodoDocumento getNodo(int posicion)
    {
        return this.lista.get(posicion);
    }

    public void ordenarLista()
    {
    Collections.sort(this.lista);
    }
    
    
    
    
}
