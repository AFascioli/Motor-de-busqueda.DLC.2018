/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import datos.Termino;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Statement stm= c.createStatement();
        c.setAutoCommit(false);
        
        String aux = "";
        int contador=0;
        
        for (Object t : termino.values()) { //Recorre el hash de terminos

            Termino term = (Termino) t;
            aux+=" ('"+term.getId_termino()+"', "+term.getFrecuenciaMax()+", "+term.getCantDocumentos()+"),";
            
            contador++;
            if (contador==100) { //Contador que indica cada cuanto ejecutar el insert
                Statement stmaux= c.createStatement();
                stmaux.executeUpdate("INSERT INTO VOCABULARIO (ID_TERMINO, FRECUENCIAMAX, CANTIDADDOCS) VALUES "+aux.substring(0,aux.length()-1));
                
                stmaux.close();
//                c.commit();
                
                aux="";
                contador=0;
            }
        }
        
        System.out.println("aux length:"+aux.length());//Como el contador no llega siempre a 100, los datos que quedan en el aux se los mando a esta ultima executeUpdate
        
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
        int contador=0;
        
        for (Object t : posteo.values()) { //Recorre el hash de terminos

            FilaPosteo fp = (FilaPosteo) t;
            aux+=" ( '"+fp.getId_termino()+"', '"+fp.getDocumento()+"', "+fp.getFrecuencia()+"),";
            
            contador++;
            if (contador==100) { //Contador que indica cada cuanto ejecutar el insert
                Statement stmaux= c.createStatement();
                stmaux.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA) VALUES "+aux.substring(0,aux.length()-1));
                
                stmaux.close();
//                c.commit();//Fijarse que esto se puede sacar para disminuir tiempo ejecucion
                
                aux="";
                contador=0;
            }
        }
        
        stm.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA) VALUES "+aux.substring(0,aux.length()-1));
        stm.close();
        c.commit();
        c.close();
    }

    public ArrayList loadRankeo(Termino termino) throws ClassNotFoundException {//Parametro termino buscado deberia ser lo que el usuario ingresa en el buscador
        //Recupera de la base de datos un mapa con todos los elementos.
        ArrayList<FilaRankeo> array = new ArrayList<>();
        
//        int r=10, auxR=10;
//        if (termino.getCantDocumentos()<r) {
//            auxR=termino.getCantDocumentos();
//        }
        
        try {
            ConexionBD conn = new ConexionBD(ruta);
            Connection c = conn.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POSTEO WHERE ID_TERMINO LIKE '" + termino.getId_termino() + "' FETCH FIRST 10 ROWS ONLY");

            FilaRankeo aux; //Clase auxiliar para guardar en el hashmap termino, documento y su frecuencia

            while (rs.next()) {
                aux = new FilaRankeo(rs.getString("ID_TERMINO"),rs.getString("ID_DOCUMENTO"),rs.getInt("FRECUENCIA"),0,rs.getString("TITULO"));
                
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
    
    public Map loadVocabulario() throws ClassNotFoundException, SQLException
    {
        Map vocabulario=new LinkedHashMap();
        
        ConexionBD conn = new ConexionBD(ruta);
            Connection c = conn.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM VOCABULARIO");

            Termino aux; //Clase auxiliar para guardar en el hashmap termino, documento y su frecuencia

            while (rs.next()) {
                aux = new Termino();
                aux.setId_termino(rs.getString("ID_TERMINO"));
                aux.setFrecuenciaMax(rs.getInt("FRECUENCIAMAX"));
                aux.setCantDocumentos(rs.getInt("CANTIDADDOCS"));
                vocabulario.put(aux.getId_termino(), aux);
            }

            rs.close();
            stmt.close();
            c.close();
            return vocabulario;
    }
}
