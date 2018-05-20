package bd;

import entidad.Termino;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
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
        String acentos = "áÁäÄéÉëËíÍïÏóÓöÖúÚüÜàèìòùÀÈÌÒÙ";
        String correxion = "aAaAeEeEiIiIoOoOuUuUaeiouAEIOU";
        String corregido = str;
        for (int i = 0; i < acentos.length(); i++) {
            corregido = corregido.replace(acentos.charAt(i), correxion.charAt(i));
        }
        return corregido;
    }

    public Map[] fileToHM2() {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map terminoHM = new LinkedHashMap();
        Map posteoHM = new LinkedHashMap();

        System.out.println("Largo de file:"+ file.length);
        
        String titulo = "";

        String nombreArc="";

        int contadorDoc =0;
        
        try {
              for (int i = 0; i < file.length; i++) {
                File fa = file[i];
                titulo="";
                
                contadorDoc++;
                
                nombreArc=fa.getAbsolutePath() ;
                char comilla = '"';
               
                //Lectura del archivo
                List<String> fileList = Files.lines(Paths.get(fa.getPath()), Charset.forName("ISO-8859-1")).collect(Collectors.toList());
                //Lista para crear el titulo
                List<String> listaTitulo = fileList.subList(0, 3);
                //Acumulacion y asignacion del titulo del archivo
                if (listaTitulo.get(0).isEmpty()) {
                    titulo=listaTitulo.get(1).replaceAll("[:\\]\\[,\\[.\\#Ø$*'ºª@&\\\\|%°<>~©ª¬'±+«»!¡_?¿;=^÷\\\\{\\\\}’`´¨]","")+" "+listaTitulo.get(2).replaceAll("[:\\]\\[,\\[.\\#Ø$*'ºª@&\\\\|%°<>~©ª¬'±+«»!¡_?¿;=^÷\\\\{\\\\}’`´¨]","");
                    
                } else {
                    titulo=listaTitulo.get(0).replaceAll("[:\\]\\[,\\[.\\#Ø$*'ºª@&\\\\|%°<>~©ª¬'±+«»!¡_?¿;=^÷\\\\{\\\\}’`´¨]","")+" "+listaTitulo.get(1).replaceAll("[:\\]\\[,\\[.\\#Ø$*'ºª@&\\\\|%°<>~©ª¬'±+«»!¡_?¿;=^÷\\\\{\\\\}’`´¨]","");                  
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
                            FilaPosteo fp = new FilaPosteo(palabra,nombreArc, 1, titulo); //Cambio de getName a getPath
                            posteoHM.put(palabra + nombreArc, fp);

                        } else //Se encuentra una palabra ya existente en el vocabulario.
                        {

                            if (posteoHM.containsKey(palabra + nombreArc)) { //Documento esta en el hash de posteo

                                FilaPosteo aux = (FilaPosteo) posteoHM.remove(palabra + nombreArc);//Saca el documento y le aumenta la frecuencia para ese termino
                                aux.aumentarFrecuencia();
                                posteoHM.put(palabra + nombreArc, aux);

                                Termino aux1 = (Termino) terminoHM.remove(palabra);

                                if (aux1.getFrecuenciaMax() < aux.getFrecuencia()) { //Chequear si hace falta actualizar la frecuencia maxima del termino
                                    aux1.setFrecuenciaMax(aux.getFrecuencia());
                                }
                                terminoHM.put(palabra, aux1);

                            } else {    //Documento no esta en el hash de posteo

                                FilaPosteo fp = new FilaPosteo(palabra, nombreArc, 1, titulo);
                                posteoHM.put(palabra + nombreArc, fp);

                                Termino termAux = (Termino) terminoHM.remove(palabra);//Aumentar la cantidad de documentos del termino
                                termAux.setCantDocumentos(termAux.getCantDocumentos() + 1);
                                terminoHM.put(palabra, termAux);
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ex.getLocalizedMessage());
        } finally {
            Map resp[] = new LinkedHashMap[2];
            resp[0] = terminoHM;
            resp[1] = posteoHM;
            terminoHM = null;
            posteoHM = null;
            System.out.println("La cantidad de documentos indexados es: "+contadorDoc);
            
            return resp;
        }
    }

    public Map actualizarTerminoHM(Map nuevo, Map vocab) throws ClassNotFoundException, SQLException {
        
        for (Object term : nuevo.values()) {
            Termino termino = (Termino) term;
            int caso;
            
            if (vocab.get(termino.getId_termino()) != null) {//El termino de nuevo documento esta en el vocabulario

                Termino terminoVoc = (Termino) vocab.remove(termino.getId_termino());

                termino.setCantDocumentos(terminoVoc.getCantDocumentos() + termino.getCantDocumentos());

                if (terminoVoc.getFrecuenciaMax() > termino.getFrecuenciaMax()) {
                    termino.setFrecuenciaMax(terminoVoc.getFrecuenciaMax());
                    //Caso 1 Actualiza cant. doc. y frec. max.
                    caso=1;
                }
                else
                {
                    //Caso 2 Actualiza cant. doc.
                    caso=2;
                }
            }
            else
            {
            //Caso 3 Termino nuevo
                caso=3;
            }
            
            TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
            //Actualizacion de la base de datos con el termino
            tp.actualizarTermino(termino, caso);
            
            vocab.put(termino.getId_termino(), termino);
            termino = null;

        }
        return vocab;
    }

}
