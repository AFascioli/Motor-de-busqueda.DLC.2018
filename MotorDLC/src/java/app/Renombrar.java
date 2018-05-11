/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bd.Documento;
import bd.FilaRankeo;
import bd.TablaPosteo;
import datos.Termino;
import datos.Vocabulario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dlcusr
 */
public class Renombrar {

    public Renombrar() {
    }
    
    
    public List rankeo(Map vocab, String consulta) throws ClassNotFoundException {

        TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");

        Vocabulario vocabulario = new Vocabulario(vocab);

        ArrayList terminosConsulta = vocabulario.obtenerTerminosConsulta(consulta);
        
        if (terminosConsulta.isEmpty()) {
            List error=null;
            return error;
        }
        Map hmDocs = new LinkedHashMap();

        for (Object o : terminosConsulta) {

            Termino term = (Termino) o;
            ArrayList arrayFR = tp.loadRankeo(term); //array filarankeo de cada termino
            //HM para los documentos

            for (Object object : arrayFR) {

                FilaRankeo fr = (FilaRankeo) object;
                Documento docAInsertar = new Documento();

                docAInsertar.setNombre(fr.getDocumento());
                double valorAInsertar= Math.floor(fr.calcularPeso(arrayFR.size(), 500) * 100) / 100;
                docAInsertar.setPeso(valorAInsertar);//500 es la cantidad total de documentos
                docAInsertar.setTitulo(fr.getTitulo());

                if (hmDocs.containsKey(docAInsertar.getNombre())) {//Si el documento ya esta en el HM, lo saca y le suma el peso que se calculo antes

                    Documento docAux = (Documento) hmDocs.remove(docAInsertar.getNombre());
                    double valorAInsertar1= Math.floor((docAux.getPeso() + docAInsertar.getPeso()) * 100) / 100;
                    docAInsertar.setPeso(valorAInsertar1);

                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);
                } else {
                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);//agregas el documentos al HM
                }
            }
        }
        ArrayList<Documento> resultadoConsulta = new ArrayList<>(hmDocs.values());//Mete lo del hash map en un array list para ordenarlo por peso
        Collections.sort(resultadoConsulta);
        
        
        return resultadoConsulta.subList(0, resultadoConsulta.size());//El segundo parametro nos indica cuantos documentos vamos a mostrar 
    }
}
