/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bd.ArchivoToHM;
import bd.ConexionBD;
import bd.Documento;
import bd.FilaPosteo;
import bd.FilaRankeo;
import bd.TablaPosteo;
import datos.Termino;
import datos.Vocabulario;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dlcusr
 */
public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        
        //========================test==============================
        File dir= new File("./Documentos a leer");
        File []archivos=dir.listFiles();
        System.out.println("test: "+ archivos.length);
//        
//        File f[] = new File[3];
//
////        
////          f[0]=new File("00ws110.txt");
//        f[0] = new File("0ddc809a.txt");
//        f[1] = new File("0ddcc10.txt");
//        f[2] = new File("0ddcl10.txt");
//        f[3]=new File("1argn10.txt");
//        f[4]=new File("1cahe10.txt");
//        f[5]=new File("1dfre10.txt");
//        f[7]=new File("1donql10.txt");
//        f[8]=new File("1drvb10.txt");
//        f[9]=new File("1hofh10.txt");

        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
        Map aux[] = arcToHM.fileToHM();
        System.out.println("Tamaño hm: "+aux[0].size());
        
//        System.out.println(aux.size()); //Tamaño del hm
//        Termino term = (Termino) aux.get("GUTENBERG");
//        System.out.println("Frecuencia maxima: " + term.getId_termino()+", "+term.getPosteo().getLista().get(0).getFrecuencia());

        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
//        FilaPosteo fp=(FilaPosteo) tp.obtenerTabla("WE").get(0);
//        System.out.println("Frecuencia maxima de WE: "+fp.getFrecuencia());
        
        try {
//            tp.deleteTable();
//            System.out.println("TerminoHM size: "+aux[0].size());
            tp.insertarTerminoHM(aux[0]);
//            tp.insertarPosteoHM(aux[1]);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void rankeo() throws ClassNotFoundException{
    
        String consulta="all our book";
        
        TablaPosteo tp=new TablaPosteo("//localhost:1527/MotorDLC");
        
        Vocabulario voc=new Vocabulario();
        
        ArrayList terminosConsulta=voc.obtenerTerminosConsulta(consulta);
        ArrayList documentosConsulta=new ArrayList();
        
        for (Object o : terminosConsulta) {
            
            Termino term=(Termino)o;
            ArrayList arrayfr= tp.loadRankeo(term.getId_termino()); //array filarankeo de cada termino
            //)===================Test===============================
            Map hmDocs=new LinkedHashMap();//HM para los documentos
            
            for (Object object : arrayfr) {
                
                FilaRankeo fr=(FilaRankeo)object;
                Documento doc=new Documento();
                
                doc.setNombre(fr.getDocumento());
                doc.setPeso(fr.getPeso()+fr.calcularPeso(arrayfr.size(), 500));
                
                if (hmDocs.containsKey(doc.getNombre())) {//Si el documento ya esta en el HM, lo saca y le suma el peso que se calculo antes
                    double aux=(double) hmDocs.remove(doc.getNombre())+doc.getPeso();
                    hmDocs.put(doc.getNombre(), aux);
                }
                else{
                    hmDocs.put(doc.getNombre(),doc.getPeso());//agregas el documentos al HM
                }    
                
            }
            
            
            //)===================Test===============================
            for (Object object : arrayfr) {
                
                FilaRankeo fr=(FilaRankeo)object;
                fr.setPeso(fr.getPeso()+fr.calcularPeso(arrayfr.size(), 500));
            }
            documentosConsulta.addAll(arrayfr);
            //loadRankeo devuelve array de los documentos por cada termino y los cargamos en un array list
            //documentosConsulta es un array de FilaRankeo
            
        }
        
        for (Object object : terminosConsulta) {
            
           Termino termino=(Termino) object;
           
           
        }
        
        
    }
}
