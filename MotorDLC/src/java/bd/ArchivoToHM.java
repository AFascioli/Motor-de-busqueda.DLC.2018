package bd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.swing.JTable;
//import de Termino y la otra clase.

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
    
    public Map[] fileToHM()
    {   //Genera un mapa con todas las palabras de un archivo seleccionado.
        Map lhm =new LinkedHashMap();
        Map pxa =new LinkedHashMap();
        Map v[]= new Map[2];  
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
                    //Reemplazar filaPalabra por la clase java por Termino, si no me equivoco.
                    {
                        //Guardar las palabras para procesarlas.
                        String palabra =tokenizer.nextToken();
                        if(!lhm.containsKey(palabra)) //Primera vez que se encuentra la palabra.
                        {
                        FilaPalabra nuevo = new FilaPalabra(palabra,1,1);
                        lhm.put(nuevo.getPalabra(), nuevo);
                        FilaPalabraXArchivo nnuevo = new FilaPalabraXArchivo(nuevo.getPalabra(), fa.getName());
                        pxa.put(nnuevo.getId_pxa(),nnuevo);
                        }
                        else //Se encuentra una palabra ya existente en el vocabulario.
                        {
                        FilaPalabra existente = (FilaPalabra) lhm.get(palabra);
                        lhm.remove(palabra); //Se saca la palabra del hashmap para aumentarle la frecuencia y volverla a ingresar.
                        existente.aumentarFrecuencia();
                        lhm.put(existente.getPalabra(),existente);
                            if(!pxa.containsKey(palabra+fa.getName()))
                            {
                            FilaPalabraXArchivo nnuevo = new FilaPalabraXArchivo(palabra, fa.getName());
                            pxa.put(nnuevo.getId_pxa(),nnuevo);
                            FilaPalabra existenteDoc = (FilaPalabra) lhm.get(palabra);
                            lhm.remove(palabra);
                            existente.aumentarDocumentos();
                            lhm.put(existente.getPalabra(),existenteDoc);
                            }                    
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
            v[0]=lhm;
            v[1]=pxa;
            return v;
        }
    }
    
    public Map cargarHashDesdeBD() throws ClassNotFoundException 
    {   
        //Reemplazar tablaPalabra por la clase que utilicemos para guardar el vocabulario en la BD.
        Map v;
        TablaPalabra tp = new TablaPalabra(ruta);
        v = tp.obtenerTabla();

        return v; 
    }
    
    
}
