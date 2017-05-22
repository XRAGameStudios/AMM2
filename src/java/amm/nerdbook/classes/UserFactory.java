package amm.nerdbook.classes;

import amm.nerdbook.database.Admin;
import amm.nerdbook.database.Columns;
import amm.nerdbook.database.Tables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabio
 */
public class UserFactory
{
    //----------------------------------------------------------------------------------------
    //tutti i metodi sql vanno scritti SOLO qui. tutti i metodi vanno rinominati nel campo ID
    private String connectionString;
    private static UserFactory singleton;
    public static UserFactory getInstance()
    {
        //se non è già istanziato mi creo una copia statica, altrimenti me lo restituisce
        if (singleton==null)
        {
            singleton = new UserFactory();
        }
        return singleton;
    }
    // devo richiamare il factory con un UtenteFactory uf = UtenteFactory.getInstance();
    //-----------------------------------------------------------------------------------------
    
    public UserFactory()
    {
      
    }
    public User makeUser(ResultSet res) throws SQLException
    {
        User user = new User();
        user.setID(res.getInt(Columns.user_id));
        user.setName(res.getString(Columns.user_name));
        user.setSurname(res.getString(Columns.user_surname));
        user.setStatus(res.getString(Columns.user_status));
        user.setImageURL(res.getString(Columns.user_imageURL));
        user.setAge(res.getString(Columns.user_birth));
        return user;
    }
    
    public int getUserIDByUserPassword(String username, String password)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT " + Columns.user_id + " FROM " + Tables.users +
                    " WHERE " + Columns.user_email + " = ? AND "
                    + Columns.user_password + " = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();
            if (res.next())
            {
                int ID = res.getInt(Columns.user_id);
                stmt.close();
                conn.close();
                return ID;
            }
            stmt.close();
            conn.close();
            return -1;
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        //l'utente non è valido
        return -1;
    }
    
    public User getUserByID(int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.users +
                    " WHERE " + Columns.user_id + " = ?";
            //prevengo la SQL Injection con il ?
            PreparedStatement stmt = conn.prepareStatement(query);
            //processa query ed identifica i punti di domanda dove inserire gli attributi.
            stmt.setInt(1, ID);
            //ricordarsi che l'indice parte da 1 e non da 0 per lo statement.
            ResultSet res = stmt.executeQuery();
            //restituisce un set di risultati della query, che non sa cosa è.
            //mi aspettto solo al più un risultato.
            if (res.next())
            {
                User user = makeUser(res);
                stmt.close();
                conn.close();
                return user;
                
            }
            
            stmt.close();
            conn.close();
            return null;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
        
    }
    
    public boolean checkAdminByID(int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.admins +
                    " WHERE " + Columns.admins_admin + " = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            //processa query ed identifica i punti di domanda dove inserire gli attributi.
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            if(res.next())
            {
                stmt.close();
                conn.close();
                return true;
            }
            stmt.close();
            conn.close();
            return false;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.users;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            while(res.next())
            {
                users.add(makeUser(res));
            }
            stmt.close();
            conn.close();
            return users;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public void setConnectionString(String s){
        this.connectionString = s;
    }
    
    public String getConnectionString(){
        return this.connectionString;
    }
}
