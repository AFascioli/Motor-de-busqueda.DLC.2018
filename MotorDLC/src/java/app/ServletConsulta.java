package app;

import bd.RankeoDocumentosConsulta;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletConsulta extends HttpServlet {

     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            RankeoDocumentosConsulta gestor= new RankeoDocumentosConsulta();
         
            //HttpSession session = request.getSession();
            //Implementado con context
            Map vocabulario = (Map) this.getServletConfig().getServletContext().getAttribute("vocabulario");
            
            String consulta=request.getParameter("txt_busqueda");
            
            List resultado=gestor.rankeo(vocabulario, consulta);
            
            request.setAttribute("busqueda",consulta);
            
            if (resultado==null) {
                request.setAttribute("resultado",null);
            }
            else
            {  request.setAttribute("resultado",resultado); 
            }
            
        }
        catch (ClassNotFoundException e) {
           request.setAttribute("errorMsg", e.getMessage());
        }
        ServletContext app = this.getServletContext();
        RequestDispatcher disp = app.getRequestDispatcher("/buscador.jsp");
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
