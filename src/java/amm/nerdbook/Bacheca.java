package amm.nerdbook;

import amm.nerdbook.classes.Post;
import amm.nerdbook.classes.PostFactory;
import amm.nerdbook.classes.User;
import amm.nerdbook.classes.UserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Bacheca extends HttpServlet
{
    
    List<Post> posts = new ArrayList<>();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        if (Actions.hasSession(request))
        {
            User me = Actions.getLoggedUser(request);
            //se non ci sono errori, ottengo l'user loggato
            if (me!=null)
            {
                request.setAttribute("logged",true);
                request.setAttribute("me",me);
                request.setAttribute("isAdmin", UserFactory.getInstance().checkAdminByID(me.getID()));
                //carico la sidebar
                Actions.loadAside(me, request);
                //leggo la bacheca dell'utente a seconda della richiesta.
                if (canLoadBacheca(request))
                {
                    //aggiunge un post se è stato inviato
                    Post newPost = PostFactory.getInstance().makeUserPostByRequest(request);
                    if(newPost!=null)
                        //invio il messaggio di conferma se il post è stato correttamente creato.
                        request.setAttribute("newPost",newPost);
                    //se esiste già una conferma, in questo modo appare il messaggio di avvenuto invio.
                    sendConfirmMessage(request);
                    
                    //carico la jsp
                    request.getRequestDispatcher("bacheca.jsp").forward(request, response);
                }
                else
                {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            else
                request.getRequestDispatcher("login.html?error=403B").forward(request, response);
        }
        else
            request.getRequestDispatcher("login.html?error=403B").forward(request, response);
    }
    
    private void sendConfirmMessage(HttpServletRequest request)
    {
        String action = request.getParameter("action");
        String destinatario = request.getParameter("for");
        if (action!=null && action.equals("confirm"))
        {
            if (destinatario!=null)
            {
                String content = request.getParameter("content");
                String attachment = request.getParameter("attachment");
                int type = Actions.convertPostType(request.getParameter("type"));
                int authorID = Integer.parseInt(request.getParameter("from"));
                int receiverID =Integer.parseInt(request.getParameter("user"));
                //per visualizzarlo immediatamente dopo
                Post post = new Post();
                post.setContent(content);
                post.setAuthor(UserFactory.getInstance().getUserByID(authorID));
                post.setUser(UserFactory.getInstance().getUserByID(receiverID));
                post.setPostTypeByString(request.getParameter("type"));
                post.setURL(attachment);
                posts.add(post);
                //questo invece lo salverà permanentemente nel database.
                if (PostFactory.getInstance().addNewUserPostOnDatabase(content, type, authorID, receiverID, attachment))
                    Actions.setMessage(destinatario, request);
            }
            
            
        }
    }
    
    
    private boolean canLoadBacheca(HttpServletRequest request)
    {
        //ho bisogno dell'user id dell'utente che voglio mostrare
        String ID = request.getParameter("user");
        // voglio pescare il parametro "user" (?user=x oppure ?...&user=x).
        if (ID!=null) //ovvero, se presente il parametro
        {
            User user = Actions.findUser(ID);
            if (user!=null)
            {
                //la servlet comunica il suo attributo al gestore del servlet
                request.setAttribute("user",user);
                //leggo la lista di post dell'utente che mi è stato richiesto
                posts = PostFactory.getInstance().getAllPostsByUser(user.getID());
                request.setAttribute("posts", posts);
                //chiamo il mio gestore delle richieste (bacheca.jsp) e gli mando la mia richiesta.
                return true;
            }
            else
                //l'utente con quel codice non esiste. C'è un errore e mando in false il metodo.
                return false;
        }
        else
        {
            //non è presente il parametro.
            //pesco l'ID dell'utente loggato.
            User me = Actions.getLoggedUser(request);
            //l'utente "user" corrisponde con il proprietario.
            request.setAttribute("user",me);
            //carico i suoi post
            posts = PostFactory.getInstance().getAllPostsByUser(me.getID());
            request.setAttribute("posts", posts);
            return true;
        }
        
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
