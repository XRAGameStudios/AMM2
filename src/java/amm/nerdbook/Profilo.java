package amm.nerdbook;

import amm.nerdbook.classes.User;
import amm.nerdbook.classes.UserFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Profilo extends HttpServlet
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
            //se non ci sono errori, ottengo l'user loggato
            if (me!=null)
            {
                request.setAttribute("me",me);
                request.setAttribute("user",me);
                //se l'utente è amministratore...
                request.setAttribute("isAdmin", UserFactory.getInstance().checkAdminByID(me.getID()));
                //carico la sidebar
                Actions.loadAside(me, request);
                String readAction = request.getParameter("action");
                //carico il messaggio, se ho ricevuto il parametro
                if (readAction!=null && readAction.equals("save"))
                {
                    String name =request.getParameter("user_name");
                    String surname = request.getParameter("user_surname");
                    String date = request.getParameter("user_bd");
                    String status = request.getParameter("user_phrase");
                    String password = request.getParameter("password");
                    String imageURL = request.getParameter("img_profile");
                    //questi servono per aggiornare temporaneamente il profilo subito dopo il click del save.
                    me.setImageURL(imageURL);
                    me.setAge(date);
                    me.setName(name);
                    me.setSurname(surname);
                    me.setPassword(password);
                    //la funzione racchiusa nell'if lo aggiorna in modo permanente
                    if (UserFactory.getInstance().editUser(me.getID(), name, surname, password, date, imageURL, status))
                        Actions.setMessage("done", request);
                }
                else if (readAction!=null && readAction.equals("delete"))
                {
                    if(UserFactory.getInstance().deleteUser(me.getID()))
                    {
                        //invalido la sessione
                        request.getSession().invalidate();
                        request.getRequestDispatcher("login.html").forward(request, response);
                    }
                    
                }
                request.getRequestDispatcher("profilo.jsp").forward(request, response);
            }
            else
            {
                request.setAttribute("logged",false);
                request.getRequestDispatcher("login.html?error=403P").forward(request, response);
            }
            
        }
        else
            //codice 403: forbidden (non hai le autorizzazioni per vedere la pagina).
            request.getRequestDispatcher("login.html?error=403P").forward(request, response);
        
        
        
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
