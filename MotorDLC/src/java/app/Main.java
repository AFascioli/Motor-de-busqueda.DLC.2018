/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bd.ArchivoToHM;
import bd.ConexionBD;
import bd.TablaPosteo;
import datos.Termino;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dlcusr
 */
public class Main {
    public static void main(String args[]) throws ClassNotFoundException, SQLException{
        File f[]= new File[1];
        
          f[0]=new File("00ws110.txt");
//        f[1]=new File("0ddc809a.txt");
//        f[2]=new File("0ddcc10.txt");
//        f[3]=new File("0ddcl10.txt");
//        f[4]=new File("1argn10.txt");
//        f[5]=new File("1cahe10.txt");
//        f[6]=new File("1dfre10.txt");
//        f[7]=new File("1donql10.txt");
//        f[8]=new File("1drvb10.txt");
//        f[9]=new File("1hofh10.txt");
        
        ArchivoToHM arcToHM= new ArchivoToHM(f,"//localhost:1527/MotorDLC");
        Map aux =arcToHM.fileToHM();
        System.out.println(aux.size()); //Tamaño del hm
        Termino term=(Termino)aux.get("A");
        System.out.println("Frecuencia maxima: "+term.getFrecuenciaMax());
        
        
        
        TablaPosteo tp= new TablaPosteo("//localhost:1527/MotorDLC",aux);
        
        try {
            tp.insertarHM();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
