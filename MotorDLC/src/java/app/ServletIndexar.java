package app;

import bd.ArchivoToHM;
import bd.TablaPosteo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletIndexar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        response.setContentType("text/html;charset=UTF-8");
        String destino="/index.html";
        
        try {

            File dir = new File("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\DocumentosAIndexar");
            TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
            
            //Armamos el vector filtrando los archivos que no tengan extension .txt 
            LinkedList <File>listaArchivos = new LinkedList<>();
            //Obtenemos el hashmap de los documentos ya indexados
            Map mapaDoc = tp.documentosIndexados();
            
            for (File archivo : dir.listFiles()) {
                if(archivo.getName().endsWith(".txt") && !mapaDoc.containsKey(archivo.getName()) ){
                    listaArchivos.add(archivo);
                }
            }

            if (listaArchivos.isEmpty()) {
                destino="/errorIndexacion.html";
            }
            else
            {
                destino ="/documentosIndexados.jsp";
                
            File[] archivotest = new File[listaArchivos.size()]; 
            
            for (int i = 0; i < archivotest.length; i++) {
                archivotest[i]=listaArchivos.get(i);
            }
            
            ArchivoToHM arcToHM = new ArchivoToHM(archivotest);
            Map aux[] = arcToHM.fileToHM2();
            
            //Obtenemos el vocabulario de la sesion
            HttpSession session = request.getSession();
            //Agregamos atributo para mostrar los archivos indexados
            session.setAttribute("listaArchivos",archivotest);
            
            Map vocabulario = (Map) arcToHM.actualizarTerminoHM(aux[0], (Map)session.getAttribute("vocabulario"));
            session.setAttribute("vocabulario",vocabulario);
            
            tp.actualizarPosteo(aux[1]);
            }
            
        } catch (ClassNotFoundException | SQLException ex ) {
                request.setAttribute("indexado",false);
                Logger.getLogger(Indexar.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMsg", ex.getMessage());
        }

        ServletContext app = this.getServletContext();
        RequestDispatcher disp = app.getRequestDispatcher(destino);
        disp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        processRequest(request, response);
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
