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
    private String connectionString;
    private static UserFactory singleton;
    public static UserFactory getInstance()
    {
        if (singleton==null)
        {
            singleton = new UserFactory();
        }
        return singleton;
    }
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
        user.setPassword(res.getString(Columns.user_password));
        user.setImageURL(res.getString(Columns.user_imageURL));
        user.setAge(res.getString(Columns.user_birth));
        return user;
    }
    
    public boolean editUser(int ID, String name, String surname, String password, String date, String imageURL, String status)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "UPDATE " + Tables.users + " SET " +
                    Columns.user_name + " = ?, " +
                    Columns.user_surname + " = ?, " +
                    Columns.user_password + " = ?, " +
                    Columns.user_imageURL + " = ?, " +
                    Columns.user_status + " = ?, " +
                    Columns.user_birth + " = ? " +
                    "WHERE " + Columns.user_id + " = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, password);
            stmt.setString(4, imageURL);
            stmt.setString(5, status);
            stmt.setString(6, date);
            stmt.setInt(7, ID);
            int res = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (res==1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        //l'utente non è valido
        return false;
    }
    
    public boolean deleteUser(int ID)
    {
        String deleteUserPosts =
                "DELETE FROM " + Tables.user_posts +
                " WHERE " + Columns.userPosts_author + " = ? " +
                " OR " + Columns.userPosts_destination + " = ?";
        String deleteGroupPosts =
                "DELETE FROM " + Tables.group_posts +
                " WHERE " + Columns.groupPosts_author + " = ? ";
        String deleteFriendships =
                "DELETE FROM " + Tables.friends +
                " WHERE " + Columns.friends_followed + " = ? " +
                " OR " + Columns.friends_follower + " = ?";
        String deleteUserFromTeam=
                "DELETE FROM " + Tables.teams +
                " WHERE " + Columns.teams_joiner + " = ? ";
        String deleteUser =
                "DELETE FROM " + Tables.users +
                " WHERE " + Columns.user_id + " = ? ";
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            conn.setAutoCommit(false);
            PreparedStatement stmt1 = conn.prepareStatement(deleteUserPosts);
            PreparedStatement stmt2 = conn.prepareStatement(deleteGroupPosts);
            PreparedStatement stmt3 = conn.prepareStatement(deleteUserFromTeam);
            PreparedStatement stmt4 = conn.prepareStatement(deleteFriendships);
            PreparedStatement stmt5= conn.prepareStatement(deleteUser);
            //imposto le variabili per i vari statement.
            stmt1.setInt(1, ID);
            stmt1.setInt(2, ID);
            stmt2.setInt(1, ID);
            stmt3.setInt(1, ID);
            stmt4.setInt(1, ID);
            stmt4.setInt(2, ID);
            stmt5.setInt(1, ID);
            boolean result = false;
            //eseguo in ordine gli update.
            try
            {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
                stmt3.executeUpdate();
                stmt4.executeUpdate();
                stmt5.executeUpdate();
                conn.commit();
                result = true;
                
            }
            catch (Exception e)
            {
                //se ci sono eccezioni vengono scritte qui.
                e.printStackTrace();
                //in tal caso il database torna allo stato precedente.
                conn.rollback();
                result = false;
            }
            finally
            {
                conn.setAutoCommit(true);
                stmt1.close();
                stmt2.close();
                stmt3.close();
                stmt4.close();
                conn.close();
                return result;
                
            }
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        //l'utente non è valido
        return false;
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
