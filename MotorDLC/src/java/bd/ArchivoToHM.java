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
                 
                //PROBAMOS EL TITULO
                
                //Acumulacion y asignacion del titulo del archivo
//                for (int i = 1; i < 3; i++) {
//                      
//                    String stringaux = fileList.get(i).replaceAll(comilla + " Ø$/,.*-#[]ºª@[]()!¡_?¿;=^÷{}’`´¨&|%°<>~©ª¬'±+«»","");
//                        titulo += stringaux;
//                        br.
//                    
//                }
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

                        if (!terminoHM.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {

                            Termino termino = new Termino(palabra, 1, 1);
                            terminoHM.put(palabra, termino);
                            FilaPosteo fp = new FilaPosteo(palabra, fa.getAbsolutePath(), 1, titulo);
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

        String titulo="";
        try {
            for (File fa : this.file) {

                char comilla = '"';
                //String titulo;
                 //Lectura del archivo
                List <String>fileList = Files.lines(Paths.get(fa.getPath()), Charset.forName("ISO-8859-1")).collect(Collectors.toList());
                //Lista para crear el titulo
                List <String>listaTitulo = fileList.subList(0, 3);
                //Acumulacion y asignacion del titulo del archivo
                
                String aReemplazar= " Ø$/,\\.*-#\\[\\]ºª@[0123456789]()!¡_?¿;=^÷\\{\\}’`´¨&\\|%°<>~©ª¬'±+«»\\:";
                StringTokenizer tok;
                StringTokenizer tok1;
                if (listaTitulo.get(0).isEmpty()) {
                    
                     tok = new StringTokenizer(listaTitulo.get(1), comilla + aReemplazar) ;
                     tok1= new StringTokenizer(listaTitulo.get(2), comilla + aReemplazar) ;
                     titulo+= tok+""+tok1;
                    //titulo =  listaTitulo.get(1).replaceAll(comilla + aReemplazar ,"") +" "+ listaTitulo.get(2).replaceAll(comilla + aReemplazar,"");
                } else 
                {
                     tok = new StringTokenizer(listaTitulo.get(0), comilla + aReemplazar) ;
                     tok1= new StringTokenizer(listaTitulo.get(1), comilla + aReemplazar) ;                    
                     titulo+= tok+""+tok1;
                   // titulo =  listaTitulo.get(0).replaceAll(comilla + aReemplazar,"") +" "+ listaTitulo.get(1).replaceAll(comilla + aReemplazar,"");
                }
                
                //Inicializacion
                for (String stringFile : fileList) {
                    
                    String s = stringFile.toString();
                    StringTokenizer tokenizer;
                    
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
