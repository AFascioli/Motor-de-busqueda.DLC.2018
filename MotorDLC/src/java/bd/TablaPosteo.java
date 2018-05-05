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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author dlcusr
 */
public class TablaPosteo {
    
    
    private String ruta;
    private Map termino;
    
    public void insertarHM() throws ClassNotFoundException, SQLException //Toma el hashmap y mete en la base de datos el posteo
    {
        ConexionBD conn=new ConexionBD(ruta);
        Connection c=conn.conectar();
        Statement stm=c.createStatement();
        c.setAutoCommit(false);
        for (Object t:this.termino.values()) { //Recorre el hash de terminos
            
            Termino term=(Termino) t;
            
            for (int i = 0; i < term.getPosteo().getLista().size()-1; i++) { //Recorre la lista de posteo de cada termino
                
                stm.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA) VALUES ("+term.getPosteo().getId_termino()+", "+term.getPosteo().getLista().get(i).getId_documento()
                                    +", "+term.getPosteo().getLista().get(i).getFrecuencia()+");");
            }
            
        }
        stm.close();
        c.commit();
        c.close();        
    }
    
    public Map obtenerTabla(String terminoBuscado) throws ClassNotFoundException {//Parametro termino buscado deberia ser lo que el usuario ingresa en el buscador
        //Recupera de la base de datos un mapa con todos los elementos.
        Map posteoHM = new LinkedHashMap();
        try {
            ConexionBD conn = new ConexionBD(ruta);
            Connection c = conn.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POSTEO WHERE ID_TERMINO='"+terminoBuscado+"'");
            
            FilaPosteo aux; //Clase auxiliar para guardar en el hashmap termino, documento y su frecuencia

            while (rs.next()) {
                aux = new FilaPosteo();
                aux.setId_termino(rs.getString("ID_TERMINO"));
                aux.setDocumento(rs.getString("ID_DOCUMENTO"));
                aux.setFrecuencia(rs.getInt("FRECUENCIA"));
                posteoHM.put(aux.getId_termino()+aux.getDocumento(),aux);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posteoHM;
    }
}
