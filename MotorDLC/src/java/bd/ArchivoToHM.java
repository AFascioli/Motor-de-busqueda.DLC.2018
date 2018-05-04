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
import javax.swing.JTable;

public class ArchivoToHM {

    private File file[];
    private JTable tabla;
    private String ruta;
    
    public ArchivoToHM(File file[],JTable tabla, String ruta) {
        this.file=file;
        this.tabla=tabla;
        this.ruta=ruta;
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

                tokenizer = new StringTokenizer(s,comilla + " $/:,.*-#[]ºª@[0123456789]()!¡_?¿;=^÷{}`´&|%°<>~©ª¬'±");

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
                            
                            //recorremos el arraylist de documentos y nos fijamos si el termino encontrado ya fue encontrado en ese documento y se le aumenta la frecuencia, 
                            //sino se agrega un nuevo nodo
                            for (int i = 0; i < existente.getPosteo().getLista().size()-1; i++) {   
                             
                                if (fa.getName().compareToIgnoreCase(existente.getPosteo().getLista().get(i).getId_documento())==0) { //entra en este if si ya aparecio el termino en el mismo documento
                                    existente.getPosteo().getLista().get(i).aumentarFrecuencia();
                                    
                                    existente.getPosteo().ordenarLista();   //ordenamos la lista de documentos por frecuencia maxima
                                    existente.setFrecuenciaMax(existente.getPosteo().getLista().get(0).getFrecuencia());//actualizamos la frecuencia maxima del termino
                                    
                                }
                                else{       //Si la palabra encontrada ya esta en vocabulario pero es un documento nuevo
                                    NodoDocumento nuevoDoc = new NodoDocumento (fa.getName(),1);
                                    existente.getPosteo().getLista().add(nuevoDoc);
                                    existente.getPosteo().ordenarLista(); //ordenamos la lista de documentos por frecuencia maxima
                                    //No actualizamos la frecuencia maxima del termino porque deberia ser 1 o mas y si se mete un nuevo documento no cambia la frecuencia maxima
                                }
                            }
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
    
    public Map obtenerHashPosteoDesdeBD() throws ClassNotFoundException 
    {   
        //Reemplazar tablaPalabra por la clase que utilicemos para guardar el vocabulario en la BD.
       
        Map v=null;  //PROVISORIO!!!!!!!!!!!!!!!!!!!
//        TablaPalabra tp = new TablaPalabra(ruta);
//            
//        v = tp.obtenerTabla();

        return v; 
    }
    
    
}