package app;

import bd.ArchivoToHM;
import bd.TablaPosteo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {

        File dir = new File("/home/dlcusr/NetBeansProjects/Motor.DLC/Documentos1/");
//        File dir = new File("/home/dlcusr/NetBeansProjects/Motor.DLC/Documentos2/");
//        File dir = new File("/home/dlcusr/NetBeansProjects/Motor.DLC/Documentos3/");
        
        
       
        File[] archivos = dir.listFiles();
        System.out.println("Cantidad de documentos:" + archivos.length);
        
        
//       
//        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
//        Map aux[] = arcToHM.fileToHM2();
//        System.out.println("Tamaño TerminoHM: " + aux[0].size());
//        System.out.println("Tamaño PosteoHM: " + aux[1].size());
//        aux[0]=null;
//        archivos=null;
//   
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
        
        for (File archivo : archivos) {
            if(!tp.estaIDDocumento(archivo.getName()))
            {
                System.out.println(archivo.getName());
                
            }
        }

//        try {
            //Comentar despues de la primera vez
         //tp.deleteTable("VOCABULARIO");
//         tp.deleteTable("POSTEO");
         
         //Segunda y tercera vez
//         Map vocabulario = tp.loadVocabulario();
//         tp.deleteTable("VOCABULARIO");
//         tp.insertarTerminoHM(arcToHM.actualizarTerminoHM(aux[0], vocabulario));
         
         
            //tp.insertarTerminoHM(aux[0]);
            //aux[0]=null;
//            tp.insertarPosteoHM(aux[1]);
//            aux[1]=null;
            
//        } catch (ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        
    }
}
