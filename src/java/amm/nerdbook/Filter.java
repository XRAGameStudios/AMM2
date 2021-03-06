/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package amm.nerdbook;
import amm.nerdbook.classes.User;
import amm.nerdbook.classes.UserFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fabio
 */
public class Filter extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        /* per come ho realizzato il progetto, se l'utente non è loggato non è
           possibile eseguire una ricerca in quanto non è accessibile la sidebar.
           Se dovessi però andare a verificare che l'utente sia loggato mi basterebbe 
           eseguire un if (Actions.hasSession()) per verificare la presenza di una 
           sessione e, in caso di assenza, rimanderei alla servlet di login.
        */
        String command = request.getParameter("cmd");
        if (command!=null)
        {
            if (command.equals("search"))
            {
                List<User> users = UserFactory.getInstance().searchUsersByName(request.getParameter("q"));
                //l'ultimo parametro è dato da js
                request.setAttribute("foundUsers", users);
                response.setContentType("application/json");
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                
                request.getRequestDispatcher("json_builder.jsp").forward(request, response);
                
                
            }
        }
        //preso dal file ajax.
        
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
