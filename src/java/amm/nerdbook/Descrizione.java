/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package amm.nerdbook;

import amm.nerdbook.classes.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//tale classe è necessaria per determinare se l'utente è o meno loggato, e in caso nasconde determinati pulsanti nella navbar
public class Descrizione extends HttpServlet
{
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        boolean logged = Actions.hasSession(request);
        request.setAttribute("logged",logged);
        if (logged)
        {
            User me = Actions.getLoggedUser(request);
            //se è presente una sessione valida, ottengo l'user loggato per la navbar
            if (me!=null)
            {
                request.setAttribute("me",me);
                request.setAttribute("user",me);
                //carico la sidebar
                Actions.loadAside(me, request);
            }
            else
            {
                //sessione invalida
                request.setAttribute("logged",false);
            }
        }
        
        //a prescindere da tutto, carico la pagina
        request.getRequestDispatcher("descrizione.jsp").forward(request, response);
        
        
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
    
}
