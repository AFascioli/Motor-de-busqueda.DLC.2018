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
import java.sql.Array;
import java.sql.Connection;
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
public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {

        //========================test==============================
        File dir = new File("./Documentos a leer");
        File[] archivos = dir.listFiles();
        System.out.println("Cantidad de documentos: " + archivos.length);
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
        System.out.println("Tamaño TerminoHM: " + aux[0].size());
        System.out.println("Tamaño PosteoHM: " + aux[1].size());

//        System.out.println(aux.size()); //Tamaño del hm
//        Termino term = (Termino) aux.get("GUTENBERG");
//        System.out.println("Frecuencia maxima: " + term.getId_termino()+", "+term.getPosteo().getLista().get(0).getFrecuencia());
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
//        FilaPosteo fp=(FilaPosteo) tp.obtenerTabla("WE").get(0);
//        System.out.println("Frecuencia maxima de WE: "+fp.getFrecuencia());

        try {
            tp.deleteTable("VOCABULARIO");
//            System.out.println("TerminoHM size: "+aux[0].size());
            tp.insertarTerminoHM(aux[0]);
            tp.insertarPosteoHM(aux[1]);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void rankeo() throws ClassNotFoundException {

        String consulta = "all our book";//Esto deberia ser un parametro, que es la consulta que hace el usuario

        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        Vocabulario voc = new Vocabulario();

        ArrayList terminosConsulta = voc.obtenerTerminosConsulta(consulta);

        Map hmDocs = new LinkedHashMap();

        for (Object o : terminosConsulta) {

            Termino term = (Termino) o;
            ArrayList arrayFR = tp.loadRankeo(term); //array filarankeo de cada termino
            //)===================Test===============================
            //HM para los documentos

            for (Object object : arrayFR) {

                FilaRankeo fr = (FilaRankeo) object;
                Documento docAInsertar = new Documento();

                docAInsertar.setNombre(fr.getDocumento());
                docAInsertar.setPeso(fr.calcularPeso(arrayFR.size(), 500));//500 es la cantidad total de documentos
                docAInsertar.setTitulo(fr.getTitulo());

                if (hmDocs.containsKey(docAInsertar.getNombre())) {//Si el documento ya esta en el HM, lo saca y le suma el peso que se calculo antes

                    Documento docAux = (Documento) hmDocs.remove(docAInsertar.getNombre());

                    docAInsertar.setPeso(docAux.getPeso() + docAInsertar.getPeso());

                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);
                } else {
                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);//agregas el documentos al HM
                }

            }

        }
        
        ArrayList<Documento> resultadoConsulta = new ArrayList<>(hmDocs.values());//Mete lo del hash map enun array list para ordenarlo por peso
        Collections.sort(resultadoConsulta);//Este es el array list de documento ordenado por peso
    }
}
