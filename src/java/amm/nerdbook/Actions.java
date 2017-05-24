package amm.nerdbook;

import amm.nerdbook.classes.Aside;
import amm.nerdbook.classes.Post;
import amm.nerdbook.classes.User;
import amm.nerdbook.classes.UserFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Actions
{
    
    //data una richiesta di sessione, mi dice se già esistente.
    public static boolean hasSession (HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        return session != null;
    }
    
    // data una sessione esistente, ne verifico la validità. In caso contrario la invalido.
    public static void checkCurrentSession (HttpServletRequest request)
    {
        if (hasSession(request))
        {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("ID")==null)
                //la sessione non è valida. La distruggo.
                session.invalidate();
        }
    }
    
    public static User getLoggedUser (HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        Object ID = session.getAttribute("ID");
        if (ID!=null)
        {
            int element = (int) ID;
            //se l'utente è un utente valido
            if (element>-1)
                return UserFactory.getInstance().getUserByID(element);
            else
                return null;
        }
        else
        {
            //la sessione ha problemi e dev'essere invalidata
            session.invalidate();
            return null;
        }
    }
    
    public static boolean isUserCompleted(int ID)
    {
        User u = UserFactory.getInstance().getUserByID(ID);
        if (u.getName().isEmpty() || u.getSurname().isEmpty() ||
                u.getImageURL().isEmpty() || u.getStatus().isEmpty())
            return false;
        return true;
    }
    
    public static User findUser(String ID)
    {
        int userID = Integer.parseInt(ID);
        return UserFactory.getInstance().getUserByID(userID);
    }
    
    public static void loadAside(User user, HttpServletRequest request)
    {
        request.setAttribute("friends", Aside.getUsers(user));
        request.setAttribute("groups",Aside.getGroups(user));
        
    }
    
    public static void setMessage(String message, HttpServletRequest request)
    {
        switch (message)
        {
            //messaggi predefiniti
            case "done":
                request.setAttribute("message", "I tuoi dati sono stati aggiornati correttamente.");
                break;
            //messaggi personalizzati per bacheca.  
            default:
                request.setAttribute("message", "Hai appena scritto sulla bacheca di " + message);
        }
    }
    public static Post addPost(HttpServletRequest request)
    {
        if (request.getParameter("action")!=null && request.getParameter("action").equals("send"))
        {
            Post post = new Post();
            //L'utente ha premuto il tasto "Invia".
            //Ora ricavo i parametri:
            String userID = request.getParameter("user");
            String content = request.getParameter("content");
            String attachment = request.getParameter("attachment");
            String type = request.getParameter("type");
            // se il post contiene del contenuto allora è un post valido.
            if (!content.isEmpty())
            {
                post.setContent(content);
                //l'autore sarà per forza chi ha eseguito il login
                post.setAuthor(Actions.getLoggedUser(request));
                //il destinatario l'utente della bacheca.
                post.setUser(UserFactory.getInstance().getUserByID(Integer.parseInt(userID)));
              //L'utente ha scritto qualcosa. Verifico la presenza dell'attachment
              if (!attachment.isEmpty())
              {
                  post.setURL(attachment);
                  if (type.equals("url"))
                      post.setAsURL();
                  else
                      post.setAsImage();
              }
              return post;
            }
            return null;
        }
        return null;
    }
    
    
    
}
