package bd;

import entidad.Termino;
import entidad.Vocabulario;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RankeoDocumentosConsulta {

    public RankeoDocumentosConsulta() {
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
        
         //Buscamos la cantidad de documentos totales
                int cantidadDocumentos=0;
                 File dir = new File("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\Documentos");
                 cantidadDocumentos = dir.list().length;
                 
                 dir = new File("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\DocumentosAIndexar");
                 cantidadDocumentos += dir.list().length;
                 

        for (Object o : terminosConsulta) {

            Termino term = (Termino) o;
            ArrayList arrayFR = tp.loadRankeo(term); //Array filaPosteoRankeada de cada termino
            //HM para los documentos

            for (Object object : arrayFR) {

                FilaPosteoRankeada fr = (FilaPosteoRankeada) object;
                Documento docAInsertar = new Documento();
                
                docAInsertar.setNombre(fr.getDocumento());
                double valorAInsertar= Math.floor(fr.calcularPeso(arrayFR.size(), cantidadDocumentos) * 100) / 100;
                docAInsertar.setPeso(valorAInsertar);
                docAInsertar.setTitulo(fr.getTitulo());

                if (hmDocs.containsKey(docAInsertar.getNombre())) {//Si el documento ya esta en el HM, lo saca y le suma el peso que se calculo antes

                    Documento docAux = (Documento) hmDocs.remove(docAInsertar.getNombre());
                    double valorAInsertar1= Math.floor((docAux.getPeso() + docAInsertar.getPeso()) * 100) / 100;
                    docAInsertar.setPeso(valorAInsertar1);

                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);
                } else {
                    hmDocs.put(docAInsertar.getNombre(), docAInsertar);//Agregamos el documentos al HM
                }
            }
        }
        ArrayList<Documento> resultadoConsulta = new ArrayList<>(hmDocs.values());//Mete lo del hash map en un array list para ordenarlo por peso
        Collections.sort(resultadoConsulta);
        
        
        return resultadoConsulta.subList(0, resultadoConsulta.size());//El segundo parametro nos indica cuantos documentos vamos a mostrar 
    }
}
