/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
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
public class GroupFactory
{
    private static GroupFactory singleton;
    private String connectionString;
    
    public static GroupFactory getInstance()
    {
        //principio identico agli altri factory\singleton
        if (singleton == null)
        {
            singleton = new GroupFactory();
        }
        return singleton;
    }
    
    private List<Group> groups = new ArrayList<>();
    
    public GroupFactory()
    {
        
    }
    
    //creo un gruppo
    public Group makeGroup (ResultSet res) throws SQLException
    {
        Group group = new Group();
        group.setID(res.getInt(Columns.groups_id));
        group.setIcon(res.getString(Columns.groups_icon));
        group.setName(res.getString(Columns.groups_name));
        return group;
    }
    
    public boolean checkGroupFounder (int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.groups +
                    " WHERE " + Columns.groups_founder + " = ?";
            //prevengo la SQL Injection con il ?
            PreparedStatement stmt = conn.prepareStatement(query);
            //processa query ed identifica i punti di domanda dove inserire gli attributi.
            stmt.setInt(1, ID);
            //ricordarsi che l'indice parte da 1 e non da 0 per lo statement.
            ResultSet res = stmt.executeQuery();
            //restituisce un set di risultati della query, che non sa cosa è.
            //mi aspettto solo al più un risultato.
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
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    
    
    public Group getGroupByID(int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.groups +
                    " WHERE " + Columns.groups_id + " = ?";
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
                Group group = makeGroup(res);
                stmt.close();
                conn.close();
                return group;
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
    
    // trova tutti i gruppi in cui un utente si è iscritto
    public List<Group> getGroupsByUserID(int ID)
    {
        List<Group> groups = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.teams +
                    " JOIN " + Tables.groups + " ON " + Columns.teams_team + " = " + Columns.groups_id +
                    " WHERE " + Columns.teams_joiner + " = ?";
            //prevengo la SQL Injection con il ?
            PreparedStatement stmt = conn.prepareStatement(query);
            //processa query ed identifica i punti di domanda dove inserire gli attributi.
            stmt.setInt(1, ID);
            //ricordarsi che l'indice parte da 1 e non da 0 per lo statement.
            ResultSet res = stmt.executeQuery();
            //restituisce un set di risultati della query, che non sa cosa è.
            //mi aspettto solo al più un risultato.
            while(res.next())
            {
                Group group = makeGroup(res);
                groups.add(group);
                
            }
            
            stmt.close();
            conn.close();
            return groups;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    // trova tutti i gruppi in cui un utente si è iscritto
    public List<Group> getAllGroups()
    {
        List<Group> groups = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.groups;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            while(res.next())
            {
                //non funziona perchè è in un'altra tabella
                Group group = makeGroup(res);
                groups.add(group);
                
            }
            
            stmt.close();
            conn.close();
            return groups;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteGroup(int ID)
    {
        String deleteTeamMates =
                "DELETE FROM " + Tables.teams +
                " WHERE " + Columns.teams_team + " = ? ";
        
        String deleteGroupPosts =
                "DELETE FROM " + Tables.group_posts +
                " WHERE " + Columns.groupPosts_destination + " = ? ";
        String deleteGroup =
                "DELETE FROM " + Tables.groups +
                " WHERE " + Columns.groups_id + " = ? ";
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            conn.setAutoCommit(false);
            PreparedStatement stmt1 = conn.prepareStatement(deleteTeamMates);
            PreparedStatement stmt2 = conn.prepareStatement(deleteGroupPosts);
            PreparedStatement stmt3 = conn.prepareStatement(deleteGroup);
            //imposto le variabili per i vari statement.
            stmt1.setInt(1, ID);
            stmt2.setInt(1, ID);
            stmt3.setInt(1, ID);
            boolean result = false;
            //eseguo in ordine gli update.
            try
            {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
                stmt3.executeUpdate();
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
                conn.close();
                return result;
                
            }
            
        }
         catch (SQLException ex)
        {
         ex.printStackTrace();
        }
       return false;
    }
    
    
    
    public void setConnectionString(String s){
        this.connectionString = s;
    }
    
    public String getConnectionString(){
        return this.connectionString;
    }
    
}
