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

        File dir = new File("/home/dlcusr/NetBeansProjects/Motor.DLC/DocumentosTP1/");
       
        File[] archivos = dir.listFiles();
        System.out.println("Cantidad de documentos:" + archivos.length);
int cc =0;
        for (File archivo : archivos) {
            cc++;
            System.out.println("Archivo "+cc+ ": "+archivo.getName());
            
        }
        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
        Map aux[] = arcToHM.fileToHM2();
        System.out.println("Tamaño TerminoHM: " + aux[0].size());
        System.out.println("Tamaño PosteoHM: " + aux[1].size());
        
        archivos=null;
   
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        try {
         tp.deleteTable("VOCABULARIO");
         tp.deleteTable("POSTEO");
         
            tp.insertarTerminoHM(aux[0]);
            
            aux[0]=null;
            
            tp.insertarPosteoHM(aux[1]);
            
            aux[1]=null;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
