package bd;

import entidad.Termino;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
        int contador = 0;

        for (Object t : termino.values()) { //Recorre el hash de terminos

            Termino term = (Termino) t;
            aux += " ('" + term.getId_termino() + "', " + term.getFrecuenciaMax() + ", " + term.getCantDocumentos() + "),";

            contador++;
            if (contador == 250) { //Contador que indica cada cuanto ejecutar el insert
                Statement stmaux = c.createStatement();
                stmaux.executeUpdate("INSERT INTO VOCABULARIO (ID_TERMINO, FRECUENCIAMAX, CANTIDADDOCS) VALUES " + aux.substring(0, aux.length() - 1));

                stmaux.close();

                aux = "";
                contador = 0;
            }
        }

        stm.executeUpdate("INSERT INTO VOCABULARIO (ID_TERMINO, FRECUENCIAMAX, CANTIDADDOCS) VALUES " + aux.substring(0, aux.length() - 1));

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
        int contador = 0;

        for (Object t : posteo.values()) { //Recorre el hash de terminos

            FilaPosteo fp = (FilaPosteo) t;
            aux += " ( '" + fp.getId_termino() + "', '" + fp.getDocumento() + "', " + fp.getFrecuencia() + ",'" + fp.getTitulo() + "'),";
            
            contador++;
            if (contador == 250) { //Contador que indica cada cuanto ejecutar el insert
                Statement stmaux = c.createStatement();

                stmaux.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA,TITULO) VALUES " + aux.substring(0, aux.length() - 1));

                stmaux.close();
                aux = "";
                contador = 0;
            }
        }

        stm.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA, TITULO) VALUES " + aux.substring(0, aux.length() - 1));
        stm.close();
        c.commit();
        c.close();
    }

    public ArrayList loadRankeo(Termino termino) throws ClassNotFoundException {//Parametro termino buscado deberia ser lo que el usuario ingresa en el buscador
        //Recupera de la base de datos un mapa con todos los elementos.
        ArrayList<FilaPosteoRankeada> array = new ArrayList<>();

        try {
            ConexionBD conn = new ConexionBD(ruta);
            Connection c = conn.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POSTEO WHERE ID_TERMINO LIKE '" + termino.getId_termino() + "' FETCH FIRST 50 ROWS ONLY");

            FilaPosteoRankeada aux; //Clase auxiliar para guardar en el hashmap termino, documento y su frecuencia

            while (rs.next()) {
                aux = new FilaPosteoRankeada();
                aux.setId_termino(rs.getString("ID_TERMINO"));
                aux.setDocumento(rs.getString("ID_DOCUMENTO"));
                aux.setFrecuencia(rs.getInt("FRECUENCIA"));
                aux.setPeso(0);
                aux.setTitulo(rs.getString("TITULO"));
                
                array.add(aux);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return array;
    }

    public void deleteTable(String tabla) throws ClassNotFoundException, SQLException {
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stmt = c.createStatement();
        stmt.executeUpdate("truncate table " + tabla);
        c.commit();
        c.close();
    }

    public Map loadVocabulario() throws ClassNotFoundException, SQLException {
        Map vocabulario = new LinkedHashMap();

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

    public boolean estaDocumento(FilaPosteo fp) throws ClassNotFoundException, SQLException {
        boolean bandera = false;
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        //Prueba de ruta absoluta para el nombre
        String iddoc =fp.getDocumento();
        iddoc = iddoc.substring(86, iddoc.length());
        
        String check = "SELECT DISTINCT * FROM POSTEO WHERE ID_DOCUMENTO LIKE '%" + iddoc + "' FETCH FIRST 1 ROWS ONLY";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(check);

        if (rs.next()) {
            bandera = true;
        }

        st.close();
        c.commit();
        c.close();
        return bandera;
    }
    
     public boolean estaIDDocumento(String iddoc) throws ClassNotFoundException, SQLException {
        boolean bandera = false;
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        //Prueba de cortar la ruta absoluta para la busqueda del documento
        iddoc = iddoc.substring(86, iddoc.length());
               
        String check = "SELECT DISTINCT * FROM POSTEO WHERE ID_DOCUMENTO LIKE '%" + iddoc + "' FETCH FIRST 1 ROWS ONLY";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(check);

        if (rs.next()) {
            bandera = true;
        }

        st.close();
        c.commit();
        c.close();
        return bandera;
    }

    public void actualizarPosteo(Map posteoNuevo) throws ClassNotFoundException, SQLException {
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stm = c.createStatement();
        c.setAutoCommit(false);

        String aux = "";
        int contador = 0;

        //Prueba, despues borrar
        System.out.println("Prueba si HMposteo isEmpty:" + posteoNuevo.isEmpty());
        
        for (Object t : posteoNuevo.values()) { //Recorre el hash de terminos

            FilaPosteo fp = (FilaPosteo) t;

            if (this.estaIDDocumento(fp.getDocumento())) {  //Para checkear que el documento no este en la base de datos
                continue;
            }
          
            aux += " ( '" + fp.getId_termino() + "', '" + fp.getDocumento() + "', " + fp.getFrecuencia() + ",'" + fp.getTitulo() + "'),";

            contador++;
            if (contador == 250) { //Contador que indica cada cuanto ejecutar el insert
                Statement stmaux = c.createStatement();
                stmaux.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA,TITULO) VALUES " + aux.substring(0, aux.length() - 1));

                stmaux.close();
                aux = "";
                contador = 0;
            }
        }
        System.out.println("INSERT de posteo, aux: "+aux);
        stm.executeUpdate("INSERT INTO POSTEO (ID_TERMINO, ID_DOCUMENTO, FRECUENCIA, TITULO) VALUES " + aux.substring(0, aux.length() - 1));
        stm.close();
        c.commit();
        c.close();
    }
    
    public void actualizarTermino(Termino termino, int caso) throws ClassNotFoundException, SQLException
    {
    //Caso depende del termino
        //1. Actualiza cant. doc. y frec. max.
        //2. Actualiza cant. doc.
        //3. Termino nuevo
        
        ConexionBD conn = new ConexionBD(ruta);
        Connection c = conn.conectar();
        Statement stm = c.createStatement();
        c.setAutoCommit(false);
        String consulta ="";
        
        switch (caso) {
            case 1:
                    consulta ="UPDATE VOCABULARIO SET FRECUENCIAMAX = "+ termino.getFrecuenciaMax() 
                            +", CANTIDADDOCS = "+ termino.getCantDocumentos() 
                            +" WHERE ID_TERMINO LIKE '"+ termino.getId_termino() +"'"; 
                break;
            case 2:
                    consulta ="UPDATE VOCABULARIO SET CANTIDADDOCS = "+ termino.getCantDocumentos() 
                            +" WHERE ID_TERMINO LIKE '"+ termino.getId_termino() +"'";
                break;
            case 3:
                    consulta = "INSERT INTO VOCABULARIO (ID_TERMINO, FRECUENCIAMAX, CANTIDADDOCS) VALUES "+
                            " ('" + termino.getId_termino() + "', " + termino.getFrecuenciaMax() + ", " + termino.getCantDocumentos() + ")";
                break;
            default:
                throw new AssertionError();
                    }
        
        stm.executeUpdate(consulta);              
        stm.close();
        c.commit();
        c.close();  
    }
    
}
