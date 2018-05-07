package bd;

import datos.NodoDocumento;
import datos.Posteo;
import datos.Termino;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class ArchivoToHM {

    private File file[];

    public ArchivoToHM(File file[]) {
        this.file = file;
    }

    public String removeAcentos(String str) {
        //Reemplaza las vocales con acentos y dieresis por las vocales sin estos.
        String acentos = "áÁäÄéÉëËíÍïÏóÓöÖúÚüÜ";
        String correxion = "aAaAeEeEiIiIoOoOuUuU";
        String corregido = str;
        for (int i = 0; i < acentos.length(); i++) {
            corregido = corregido.replace(acentos.charAt(i), correxion.charAt(i));
        }
        return corregido;
    }

    public Map[] fileToHM() {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map terminoHM = new LinkedHashMap();
        Map posteoHM = new LinkedHashMap();
        Map resp[] = new LinkedHashMap[2];
//        int contador=0;
        try {
            for (Object o : this.file) {
//                contador++;
//                System.out.println("Documento "+contador);

                File fa = (File) o; //Lectura del archivo
                FileReader fr = new FileReader(fa);
                BufferedReader br = new BufferedReader(fr);
                //Inicializacion
                String s = br.readLine();
                StringTokenizer tokenizer;
                char comilla = '"';

                while (s != null) {
                    s = removeAcentos(s);
                    s = s.toUpperCase();

                    tokenizer = new StringTokenizer(s, comilla + " $/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}`´&|%°<>~©ª¬'±+");

                    while (tokenizer.hasMoreTokens()) {
                        //Guardar las palabras para procesarlas.
                        String palabra = tokenizer.nextToken();
                        if (!terminoHM.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {
                            
                            //============================NUEVA IMPLEMENTACION=================================
                            
                            Termino termino = new Termino(palabra, 1, 1);
                            terminoHM.put(palabra, termino);
                            FilaPosteo fp = new FilaPosteo(palabra, fa.getName(), 1);
                            posteoHM.put(palabra + fa.getName(), fp);
                            
                            //============================NUEVA IMPLEMENTACION=================================
                        } else //Se encuentra una palabra ya existente en el vocabulario.
                        {
                            
                                //============================NUEVA IMPLEMENTACION=================================
                                if (posteoHM.containsKey(palabra + fa.getName())) { //Documento esta en el hash de posteo
                                    
                                    FilaPosteo aux = (FilaPosteo) posteoHM.remove(palabra + fa.getName());//Saca el documento y le aumenta la frecuencia para ese termino
                                    aux.aumentarFrecuencia();
                                    posteoHM.put(palabra + fa.getName(), aux);
                                    
                                    Termino aux1= (Termino) terminoHM.remove(palabra);
                                    
                                    if (aux1.getFrecuenciaMax()<aux.getFrecuencia()) { //Chequear si hace falta actualizar la frecuencia maxima del termino
                                        
                                        aux1.setFrecuenciaMax(aux.getFrecuencia());
                                        
                                    }
                                    terminoHM.put(palabra,aux1);
                                    
                                } else {    //Documento no esta en el hash de posteo
                                    
                                    FilaPosteo fp = new FilaPosteo(palabra, fa.getName(), 1);
                                    posteoHM.put(palabra + fa.getName(), fp);
                                    
                                    Termino termAux=(Termino) terminoHM.remove(palabra);//Aumentar la cantidad de documentos del termino
                                    termAux.setCantDocumentos(termAux.getCantDocumentos()+1);
                                    terminoHM.put(palabra, termAux);
                                }
                                //============================NUEVA IMPLEMENTACION=================================
                            
                        }
                    }
                    s = br.readLine();
                }
                br.close();
                fr.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(ex.getLocalizedMessage());
        } finally {
            resp[0]=terminoHM;
            resp[1]=posteoHM;
            return resp;
        }
    }

}
