/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bd.ArchivoToHM;
import bd.TablaPosteo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dlcusr
 */
public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {

        File dir = new File("/home/dlcusr/NetBeansProjects/Motor.DLC/Documentos100");
        File[] archivos = dir.listFiles();
        System.out.println("Cantidad de documentos:" + archivos.length);


        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
        Map aux[] = arcToHM.fileToHM();
        System.out.println("Tamaño TerminoHM: " + aux[0].size());
        System.out.println("Tamaño PosteoHM: " + aux[1].size());
        
        archivos=null;
   
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        try {
         tp.deleteTable("VOCABULARIO");
            tp.insertarTerminoHM(aux[0]);
            
            aux[0]=null;//PARA LIBERAR MEMORIA????, LE MANDE PARA VER SI AYUDA
            
            tp.insertarPosteoHM(aux[1]);
            
            aux[1]=null;//PARA LIBERAR MEMORIA????, LE MANDE PARA VER SI AYUDA

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

               
    }

    
}
