/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import datos.Termino;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author dlcusr
 */
public class TablaPosteo {

    private String ruta;

    public TablaPosteo(String ruta) {
        this.ruta = ruta;
    }

    public void insertarTerminoHM(Map termino) throws ClassNotFoundException, SQLException //Toma el hashmap y mete en la base de datos el posteo
    {
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stm = c.createStatement();
        c.setAutoCommit(false);

        String aux = "";

        for (Object t : termino.values()) { //Recorre el hash de terminos

            Termino term = (Termino) t;
            aux+=" ( '"+term.getId_termino()+"', "+term.getFrecuenciaMax()+", "+term.getCantDocumentos()+"),";
            
        }
        System.out.println("aux length:"+aux.length());
        stm.executeUpdate("INSERT INTO VOCABULARIO (ID_TERMINO, FRECUENCIAMAX, CANTIDADDOCS) VALUES "+aux.substring(0,aux.length()-1));
        
        stm.close();
        c.commit();
        c.close();
    }
    
    public void insertarPosteoHM(Map posteo) throws ClassNotFoundException, SQLException //Toma el hashmap y mete en la base de datos el posteo
    {
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stm = c.createStatement();
        c.setAutoCommit(false);

        String aux = "";

        for (Object t : posteo.values()) { //Recorre el hash de terminos

            FilaPosteo fp = (FilaPosteo) t;
            aux+=" ( '"+fp.getId_termino()+"', '"+fp.getDocumento()+"', "+fp.getFrecuencia()+"),";
            
        }
        stm.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA) VALUES "+aux.substring(0,aux.length()-1));
        
        stm.close();
        c.commit();
        c.close();
    }

    public ArrayList obtenerTabla(String terminoBuscado) throws ClassNotFoundException {//Parametro termino buscado deberia ser lo que el usuario ingresa en el buscador
        //Recupera de la base de datos un mapa con todos los elementos.
        ArrayList<FilaPosteo> array = new ArrayList<>();
        try {
            ConexionBD conn = new ConexionBD(ruta);
            Connection c = conn.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POSTEO WHERE ID_TERMINO LIKE '" + terminoBuscado + "'");

            FilaPosteo aux; //Clase auxiliar para guardar en el hashmap termino, documento y su frecuencia

            while (rs.next()) {
                aux = new FilaPosteo();
                aux.setId_termino(rs.getString("ID_TERMINO"));
                aux.setDocumento(rs.getString("ID_DOCUMENTO"));
                aux.setFrecuencia(rs.getInt("FRECUENCIA"));
                array.add(aux);
            }

            rs.close();
            stmt.close();
            c.close();

            Collections.sort(array);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public void deleteTable(String tabla) throws ClassNotFoundException, SQLException {
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stmt = c.createStatement();
        stmt.executeUpdate("delete from "+tabla);
        c.commit();
        c.close();
    }
}
