package amm.nerdbook;

import amm.nerdbook.classes.GroupFactory;
import amm.nerdbook.classes.PostFactory;
import amm.nerdbook.classes.UserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(loadOnStartup=0)
public class Login extends HttpServlet
{
    //il driver
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //database pulito
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/ammdb";
    //database di build
    private static final String DB_BUILD_PATH = "WEB-INF/db/ammdb";
    //resto della classe...
    public static final String id = "ID";
    public static final String logged = "logged";
    public int loggedID=-1;
    public List<String> errors = new ArrayList<>();
    
    @Override
    public void init(){
        String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
        try {
            
            Class.forName(JDBC_DRIVER);
            
        } catch (ClassNotFoundException ex) {
            
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        GroupFactory.getInstance().setConnectionString(dbConnection);
        UserFactory.getInstance().setConnectionString(dbConnection);
        PostFactory.getInstance().setConnectionString(dbConnection);
        
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("logged", false);
        errors = new ArrayList<>();
        //aggiungo eventuali errori se presenti.
        getErrors(request);
        //Se è presente la richiesta di logout, eseguo il logout.
        doLogout(request);
        //Se è presente una sessione non valida, la distruggo
        Actions.checkCurrentSession(request);
        //verifico che i dati siano corretti per eseguire il login.
        if (doLogin(request))
            //se son corretti procedo al reinvio della corretta servlet
            doAfterLogin(request,response);
        else
        {
            //in caso contrario invio gli errori e carico la pagina jsp.
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    
    
    private void doLogout (HttpServletRequest request)
    {
        //il logout avviene solo in presenza dei parametri di logout e se presente una sessione precedente.
        boolean sessionExists= Actions.hasSession(request);
        if (sessionExists)
        {
            String logout_intention = request.getParameter("action");
            if (logout_intention!=null && logout_intention.equals("logout"))
            {
                HttpSession session = request.getSession(false);
                session.invalidate();
            }
        }
    }
    
    private void getErrors(HttpServletRequest request)
    {
        String ErrorCode = request.getParameter("error");
        if (ErrorCode!=null)
        {
            switch (ErrorCode)
            {
                case "403B":
                    errors.add("Impossibile accedere alla bacheca: accesso negato. Eseguire prima il login.");
                    break;
                case "403P":
                    errors.add("Impossibile accedere alla sezione profilo: accesso negato. Eseguire prima il login.");
                    break;
                default:
                    errors.add("Errore sconosciuto. Eseguire prima il login.");
                    break;
            }
        }
    }
    
    private boolean doLogin (HttpServletRequest request)
    {
        //leggo gli eventuali username e password, se sono stati inviati.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //verifico innanzitutto se esiste già una sessione. In tal caso vado direttamente al passo successivo
        boolean sessionExists= Actions.hasSession(request);
        if (sessionExists)
            return true;
        else
        {
            //valuto i dati che mi son arrivati.
            if (username==null && password==null)
                //Nessun dato ricevuto. Rimando al login.jsp in attesa dei dati di login.
                return false;
            // In caso contrario ho ricevuto dei dati, per cui ne verifico la correttezza.
            if (username!=null && password!=null)
            {
                //verifico se l'ID dell'utente loggato ha un valore diverso da -1 (utente non valido)
                int userID = UserFactory.getInstance().getUserIDByUserPassword(username, password);
                if (userID>-1)
                {
                    //l'utente è valido. Creo pertanto una sessione.
                    HttpSession session = request.getSession();
                    session.setAttribute(logged, true);
                    loggedID = userID;
                    session.setAttribute(id, userID);
                    return true;
                }
                else
                {
                    //login eseguito con nome utente e/o password errati.
                    errors.add("Il nome utente o la password sono errati.");
                    return false;
                }
            }
            else
            {
                //Tentativo di login solo con nome utente o solo con password
                errors.add("È necessario inserire entrambi i campi per continuare.");
                return false;
            }
        }
    }
    
    //come la servlet deve comportarsi dopo aver eseguito correttamente il login
    private void doAfterLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        /*Poichè posso ottenere true dal metodo precedente se esiste una sessione
        o se i dati di login son corretti, ho due casi. */
        HttpSession session = request.getSession(false);
        int userID = -1;
        if (session!=null)
            //Caso 1: leggo lo user ID direttamente dalla sessione.
            userID= (int) session.getAttribute(id);
        else
            //Caso 2: leggo lo user ID dalla variabile precedentemente impostata (loggedID).
            userID = loggedID;
        //in base al fatto che il profilo sia completo carico la bacheca o la pagina del profilo.
        if (Actions.isUserCompleted(userID))
            request.getRequestDispatcher("bacheca.html").forward(request, response);
        else
            request.getRequestDispatcher("profilo.html").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
    
}
