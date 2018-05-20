package app;

import bd.ArchivoToHM;
import bd.TablaPosteo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
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
        try {

            File dir = new File("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\MotorWin.DLC\\DocumentosAIndexar");
            
            //Armamos el vector filtrando los archivos que no tengan extension .txt 
            LinkedList listaArchivos;
            listaArchivos = new LinkedList();
            
//            for (File archivo : dir.listFiles()) {
//                if(archivo.getName().endsWith(".txt")){
//                    listaArchivos.add(archivo);
//                    
//                }
//            }
//             
//            File[] archivos = Arrays.copyOf(listaArchivos.toArray(), listaArchivos.size() , File[].class );
//            
            File[] archivos = dir.listFiles();

            ArchivoToHM arcToHM = new ArchivoToHM(archivos);
            Map aux[] = arcToHM.fileToHM2();

            archivos = null;

            TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
//            System.out.println("Test de estaIDDocumento: "+tp.estaIDDocumento("C:\\Users\\Usuario\\Google Drive\\Facultad\\Quinto año\\DLC\\MotorWin.DLC\\Documentos\\00weees110.txt"));
                    
            HttpSession session = request.getSession();
            Map vocabulario = (Map) arcToHM.actualizarTerminoHM(aux[0], (Map)session.getAttribute("vocabulario"));
            
            session.setAttribute("vocabulario",vocabulario);
            aux[0] = null;
            tp.actualizarPosteo(aux[1]);
            aux[1] = null;
            
        } catch (ClassNotFoundException | SQLException ex ) {
                request.setAttribute("indexado",false);
                Logger.getLogger(Indexar.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMsg", ex.getMessage());
        }

        ServletContext app = this.getServletContext();
        RequestDispatcher disp = app.getRequestDispatcher("/index.html");
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
