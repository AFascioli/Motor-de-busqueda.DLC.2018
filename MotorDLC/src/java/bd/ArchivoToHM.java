package bd;

import datos.Termino;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ArchivoToHM {

    private File file[];

    public ArchivoToHM(File file[]) {
        this.file = file;
    }

    public String removeAcentos(String str) {
        //Reemplaza las vocales con acentos y dieresis por las vocales sin estos.
        String acentos =   "áÁäÄéÉëËíÍïÏóÓöÖúÚüÜàèìòùÀÈÌÒÙ";
        String correxion = "aAaAeEeEiIiIoOoOuUuUaeiouAEIOU";
        String corregido = str;
        for (int i = 0; i < acentos.length(); i++) {
            corregido = corregido.replace(acentos.charAt(i), correxion.charAt(i));
        }
        return corregido;
    }
    
    public Map[] fileToHM() {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map terminoHM = new LinkedHashMap();
        Map posteoHM = new LinkedHashMap();

        String titulo = "";
        int contador = 0;
        int auxcontador = 0;
        try {
            for (Object o : this.file) {
                contador = 0;
                titulo="";
                
                auxcontador++;
                File fa = (File) o; //Lectura del archivo
                FileReader fr = new FileReader(fa);
                BufferedReader br = new BufferedReader(fr);
                //Inicializacion
                String slinea = br.readLine();
                   
                StringTokenizer tokenizer;
                char comilla = '"';

                while (slinea != null) {
                    contador++;
                     
                    slinea = removeAcentos(slinea);
                    slinea = slinea.toUpperCase();

                    tokenizer = new StringTokenizer(slinea, comilla + " Ø$/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}’`´¨&|%°<>~©ª¬'±+«»");

                    while (tokenizer.hasMoreTokens()) {
                        //Guardar las palabras para procesarlas.
                        String palabra = tokenizer.nextToken();

                        //Carga del titulo, en las primeras dos lineas del texto
                        if (contador < 3) { //Para agregarle el titulo a el documento (las dos primeras lineas del libro)
                        titulo += palabra+" ";
                        }

                        
                        if (!terminoHM.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {

                            Termino termino = new Termino(palabra, 1, 1);
                            terminoHM.put(palabra, termino);
                            FilaPosteo fp = new FilaPosteo(palabra, fa.getAbsolutePath(), 1, titulo);
                            posteoHM.put(palabra + fa.getAbsolutePath(), fp);
//                            

                        } else //Se encuentra una palabra ya existente en el vocabulario.
                        {
                                 if (posteoHM.containsKey(palabra + fa.getAbsolutePath())) { //Documento esta en el hash de posteo

                                FilaPosteo aux = (FilaPosteo) posteoHM.remove(palabra + fa.getAbsolutePath());//Saca el documento y le aumenta la frecuencia para ese termino
                                aux.aumentarFrecuencia();
                                posteoHM.put(palabra + fa.getAbsolutePath(), aux);

                                Termino aux1 = (Termino) terminoHM.remove(palabra);

                                if (aux1.getFrecuenciaMax() < aux.getFrecuencia()) { //Chequear si hace falta actualizar la frecuencia maxima del termino

                                    aux1.setFrecuenciaMax(aux.getFrecuencia());

                                }
                                terminoHM.put(palabra, aux1);

                            } else {    //Documento no esta en el hash de posteo

                                FilaPosteo fp = new FilaPosteo(palabra, fa.getAbsolutePath(), 1, titulo);
                                posteoHM.put(palabra + fa.getAbsolutePath(), fp);
                            

                                Termino termAux = (Termino) terminoHM.remove(palabra);//Aumentar la cantidad de documentos del termino
                                termAux.setCantDocumentos(termAux.getCantDocumentos() + 1);
                                terminoHM.put(palabra, termAux);
                            }

                        }
                    }
                    slinea = br.readLine();
                }
                br.close();
                fr.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(ex.getLocalizedMessage());
        } finally {

            Map resp[] = new LinkedHashMap[2];
            resp[0] = terminoHM;
            resp[1] = posteoHM;
            terminoHM = null;
            posteoHM = null;
            System.out.println("Archivos leidos: " + auxcontador);
            return resp;
        }
    }

    public Map[] fileToHM2() {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map terminoHM = new LinkedHashMap();
        Map posteoHM = new LinkedHashMap();

        String titulo = "";
        int contador = 0;
        try {
            for (File fa : this.file) {
                contador = 0;
                titulo = "";
                 //Lectura del archivo
                List <String>fileList = Files.lines(Paths.get(fa.getPath()), Charset.forName("ISO-8859-1")).collect(Collectors.toList());
                
                //Inicializacion
                for (String stringFile : fileList) {
                    
                    String s = stringFile.toString();
                   
                    contador++;

                   
                    StringTokenizer tokenizer;
                    char comilla = '"';

                     
                    if (contador < 3) { //Para agregarle el titulo a el documento (las dos primeras lineas del libro)
                        
                        String stringaux = s.replaceAll(comilla + " Ø$/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}’`´¨&|%°<>~©ª¬'±+«»","");
                        titulo += stringaux;
                        
                        //System.out.println(fa.getPath()+": "+titulo);
                    }
                    s = removeAcentos(s);
                    s = s.toUpperCase();

                    tokenizer = new StringTokenizer(s, comilla + " Ø$/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}’`´¨&|%°<>~©ª¬'±+«»");

                    while (tokenizer.hasMoreTokens()) {
                        //Guardar las palabras para procesarlas.
                        String palabra = tokenizer.nextToken();
                        
                        if (!terminoHM.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {

                            Termino termino = new Termino(palabra, 1, 1);
                            terminoHM.put(palabra, termino);
                            FilaPosteo fp = new FilaPosteo(palabra, fa.getAbsolutePath(), 1, titulo); //Cambio de getName a getPath
                            posteoHM.put(palabra + fa.getAbsolutePath(), fp);                           

                        } else //Se encuentra una palabra ya existente en el vocabulario.
                        {

                            if (posteoHM.containsKey(palabra + fa.getAbsolutePath())) { //Documento esta en el hash de posteo

                                FilaPosteo aux = (FilaPosteo) posteoHM.remove(palabra + fa.getAbsolutePath());//Saca el documento y le aumenta la frecuencia para ese termino
                                aux.aumentarFrecuencia();
                                posteoHM.put(palabra + fa.getAbsolutePath(), aux);

                                Termino aux1 = (Termino) terminoHM.remove(palabra);

                                if (aux1.getFrecuenciaMax() < aux.getFrecuencia()) { //Chequear si hace falta actualizar la frecuencia maxima del termino

                                    aux1.setFrecuenciaMax(aux.getFrecuencia());

                                }
                                terminoHM.put(palabra, aux1);

                            } else {    //Documento no esta en el hash de posteo

                                FilaPosteo fp = new FilaPosteo(palabra, fa.getAbsolutePath(), 1, titulo);
                                posteoHM.put(palabra + fa.getAbsolutePath(), fp);

                                Termino termAux = (Termino) terminoHM.remove(palabra);//Aumentar la cantidad de documentos del termino
                                termAux.setCantDocumentos(termAux.getCantDocumentos() + 1);
                                terminoHM.put(palabra, termAux);
                            }
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ex.getLocalizedMessage());
        } finally {
            Map resp[] = new LinkedHashMap[2];
            resp[0] = terminoHM;
            resp[1] = posteoHM;
            terminoHM = null;
            posteoHM = null;
            return resp;
        }
    }
    
     public Map actualizarTerminoHM(Map nuevo, Map vocab)
    {
        for (Object term : nuevo.values()) {
            Termino termino= (Termino)term;
                
                if (vocab.get(termino.getId_termino())!=null) {//El termino de nuevo documento esta en el vocabulario
                    
                    Termino terminoVoc= (Termino) vocab.remove(termino.getId_termino());
                    
                    termino.setCantDocumentos(terminoVoc.getCantDocumentos()+termino.getCantDocumentos());
                    
                    if (terminoVoc.getFrecuenciaMax()>termino.getFrecuenciaMax()) {
                        termino.setFrecuenciaMax(terminoVoc.getFrecuenciaMax());
                    }
                }
                 vocab.put(termino.getId_termino(), termino);
                 termino=null;
            
        }
        return vocab;
    }
    
}
