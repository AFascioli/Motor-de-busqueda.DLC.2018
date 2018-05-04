package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private final String ruta;

    public ConexionBD(String rutaConexion)
    {
        this.ruta=rutaConexion;
    }
    
    public Connection conectar() throws ClassNotFoundException, SQLException 
    {        
        Connection conexion;
        Class.forName("org.sqlite.JDBC");
        
        conexion= DriverManager.getConnection("jdbc:sqlite:"+ruta);
        
        return conexion;
    }        
}
