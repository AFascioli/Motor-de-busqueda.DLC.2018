package app;

import bd.ArchivoToHM;
import bd.TablaPosteo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Indexar {

    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {
      
        File dir = new File("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\Documentos");    
 
        File[] archivos = dir.listFiles();
        System.out.println("Cantidad de documentos:" + archivos.length);
        
        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
        Map aux[] = arcToHM.fileToHM2();
        System.out.println("Tamaño TerminoHM: " + aux[0].size());
        System.out.println("Tamaño PosteoHM: " + aux[1].size());
       
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        try {

         tp.deleteTable("VOCABULARIO");
         tp.deleteTable("POSTEO");
  
            tp.insertarTerminoHM(aux[0]);
            tp.insertarPosteoHM(aux[1]);
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Indexar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Indexar.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
