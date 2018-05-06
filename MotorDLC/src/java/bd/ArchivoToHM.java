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
        this.file=file;
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
    
    public Map fileToHM()
    {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map terminoHM =new LinkedHashMap();
        
        
        try {       
                for(Object o:this.file){
                    
                    System.out.println("Cambio de documento");
                    
                File fa = (File)o; //Lectura del archivo
                FileReader fr= new FileReader(fa);
                BufferedReader br= new BufferedReader(fr);
                //Inicializacion
                String s=br.readLine(); 
                StringTokenizer tokenizer;
                char comilla = '"';
                
                while(s!=null)
                {
                s=removeAcentos(s);
                s=s.toUpperCase();

                tokenizer = new StringTokenizer(s,comilla + " $/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}`´&|%°<>~©ª¬'±+");

                while (tokenizer.hasMoreTokens())
                    {
                        //Guardar las palabras para procesarlas.
                        String palabra =tokenizer.nextToken();
                        if(!terminoHM.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {
                            NodoDocumento nodoDoc = new NodoDocumento(fa.getName(), 1); //Primero se crea el nodo documento para poder ahcer el posteo
                            ArrayList <NodoDocumento>nodoDocs= new ArrayList<>(); //Creamos el array list y le mandamos el documento.
                            nodoDocs.add(nodoDoc);
                            
                            Posteo posteo=new Posteo (palabra,nodoDocs); //creamos el posteo y le mandas el arraylist
                           
                            
                            Termino term = new Termino(palabra,1,1,posteo); //con todo lo anterior creado podemos crear el termino
                            
                            terminoHM.put(term.getId_termino(), term);
                        
                        }
                        else //Se encuentra una palabra ya existente en el vocabulario.
                        {
                            Termino existente= (Termino)terminoHM.get(palabra);
                            terminoHM.remove(palabra); //Se saca la palabra del hashmap para aumentarle la frecuencia y volverla a ingresar.
                            NodoDocumento auxxx = new NodoDocumento (fa.getName(),1);
                            
                            if (existente.getPosteo().getNodoIndex(fa.getName())==-1) { //pregunta si esta el documento, si esta entra al else y se le aumenta la frecuencia, sino va al if
                                existente.getPosteo().getLista().add(auxxx);
                                existente.getPosteo().ordenarLista();
                            }
                            else
                            {
                                existente.getPosteo().getLista().get(existente.getPosteo().getNodoIndex(fa.getName())).aumentarFrecuencia();
                                
                            }
                            //recorremos el arraylist de documentos y nos fijamos si el termino encontrado ya fue encontrado en ese documento y se le aumenta la frecuencia, 
                            //sino se agrega un nuevo nodo
                            
//                            for (int i = 0; i < existente.getPosteo().getLista().size(); i++) {   
//                             
//                                if (fa.getName().compareToIgnoreCase(existente.getPosteo().getLista().get(i).getId_documento())==0) { //entra en este if si ya aparecio el termino en el mismo documento
//                                    existente.getPosteo().getLista().get(i).aumentarFrecuencia();
//                                    
////                                    System.out.println("Aumento frecuencia");
//                                    
//                                    existente.getPosteo().ordenarLista();   //ordenamos la lista de documentos por frecuencia maxima (PUEDE QUE SEA INECESARIO, CUANDO SE METE EN LA BD EL HM NO INFLUYE EL ORDEN, CUANDO LO SACAMOS DE LA BD USAMOS EL ORDER BY)
//                                    existente.setFrecuenciaMax(existente.getPosteo().getLista().get(0).getFrecuencia()); //actualizamos la frecuencia maxima del termino
//                                    
//                                }
//                                else{       //Si la palabra encontrada ya esta en vocabulario pero es un documento nuevo
//                                    NodoDocumento nuevoDoc = new NodoDocumento (fa.getName(),1);
//                                    existente.getPosteo().getLista().add(nuevoDoc);
//                                    existente.getPosteo().ordenarLista(); //ordenamos la lista de documentos por frecuencia maxima
//                                    //No actualizamos la frecuencia maxima del termino porque deberia ser 1 o mas y si se mete un nuevo documento no cambia la frecuencia maxima
//                                    
////                                    System.out.println("Nuevo documento");
//                                }
//                            }

                            terminoHM.put(existente.getId_termino(), existente); //Se vuelve a agregar el termino al hash map
                                           
                        }
                    }
                s=br.readLine();
                }
             br.close();
             fr.close();
            } 
        }catch (Exception ex) {
            Logger.getLogger(ex.getLocalizedMessage());
        }
        finally
        {
            
            return terminoHM;
        }
    }
    
        
    
}
