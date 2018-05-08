/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bd.ArchivoToHM;
import bd.Documento;
import bd.FilaRankeo;
import bd.TablaPosteo;
import datos.Termino;
import datos.Vocabulario;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author dlcusr
 */
public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {

        //========================test==============================
//        File dir = new File("./Documentos a leer");
//        File[] archivos = dir.listFiles();
//        System.out.println("Cantidad de documentos: " + archivos.length);
//
//
//        ArchivoToHM arcToHM = new ArchivoToHM(archivos);
//        Map aux[] = arcToHM.fileToHM();
//        System.out.println("Tamaño TerminoHM: " + aux[0].size());
//        System.out.println("Tamaño PosteoHM: " + aux[1].size());
//        
//        archivos=null;
        
//        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

//        try {
////            tp.deleteTable("VOCABULARIO");
//            tp.insertarTerminoHM(aux[0]);
//            
//            aux[0]=null;;//PARA LIBERAR MEMORIA????, LE MANDE PARA VER SI AYUDA
//            
//            tp.insertarPosteoHM(aux[1]);
//            
//            aux[1]=null;;//PARA LIBERAR MEMORIA????, LE MANDE PARA VER SI AYUDA
//
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        
    }

    public static ArrayList rankeo() throws ClassNotFoundException, SQLException {

        String consulta = "the and";//Esto deberia ser un parametro, que es la consulta que hace el usuario
        
        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        Vocabulario voc =new Vocabulario(tp.loadVocabulario());
        
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
        return resultadoConsulta;
    }
}
