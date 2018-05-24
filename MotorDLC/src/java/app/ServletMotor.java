package app;

import bd.TablaPosteo;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletMotor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String dest = "";
        try {
            
            TablaPosteo tp = new TablaPosteo("//localhost:1527/MotorDLC");
            
            this.getServletConfig().getServletContext().setAttribute("vocabulario",tp.loadVocabulario());
            
            //session.setAttribute("vocabulario",tp.loadVocabulario());
            //HttpSession session= request.getSession();
            //Map vocaux = (Map)session.getAttribute("vocabulario");
            
            Map vocaux = (Map)this.getServletConfig().getServletContext().getAttribute("vocabulario");
            System.out.println("Map vocabulario en servlet motor, isEmpty:" + vocaux.isEmpty());
            
            dest = "/buscador.html";
        }
        catch (Exception e) {                
            System.out.println("Error, servletmotor:" + e.getMessage());
        }
        ServletContext app = this.getServletContext();
        RequestDispatcher disp = app.getRequestDispatcher(dest);
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
