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
public class PostFactory
{
    private String connectionString;
    private static PostFactory singleton;
    
    public static PostFactory getInstance()
    {
        //se non è già istanziato mi creo una copia statica, altrimenti me lo restituisce
        if (singleton == null)
        {
            singleton = new PostFactory();
        }
        return singleton;
    }
    
    
    public PostFactory()
    {
        
    }
    //crea un oggetto userPost da un risultato di una query
    public Post makeUserPost(ResultSet res) throws SQLException
    {
        Post post = new Post();
        post.setID(res.getInt(Columns.userPosts_id));
        post.setAuthor(UserFactory.getInstance().getUserByID(res.getInt(Columns.userPosts_author)));
        post.setUser(UserFactory.getInstance().getUserByID(res.getInt(Columns.userPosts_destination)));
        post.setContent(res.getString(Columns.userPosts_content));
        return post;
    }
    //crea un oggetto groupPost da un risultato di una query
    public Post makeGroupPost (ResultSet res) throws SQLException
    {
        Post post = new Post();
        post.setID(res.getInt(Columns.groupPosts_id));
        post.setAuthor(UserFactory.getInstance().getUserByID(res.getInt(Columns.groupPosts_author)));
        post.setGroup(GroupFactory.getInstance().getGroupByID(res.getInt(Columns.groupPosts_destination)));
        post.setContent(res.getString(Columns.groupPosts_content));
        return post;
    }
    
    
    public Post sendPostToUser (Post post, User user)
    {
        post.setUser(user);
        return post;
    }
    
    public boolean addUserPost (Post post)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "INSERT INTO " + Tables.user_posts + "(postID,content,type,author,toUser,attachment)" +
                    " VALUES (?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, 1000);
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getPostType().ordinal());
            stmt.setInt(4,post.getAuthor().getID());
            stmt.setInt(5,post.getUser().getID());
            stmt.setString(6, post.getUrl());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return false;
    }
    //elimina un post del database dato un ID
    public boolean deleteUserPost(int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String update =
                    "DELETE FROM " + Tables.user_posts +
                    " WHERE "+ Columns.userPosts_id + " = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            conn.setAutoCommit(false);
            stmt.setInt(1, ID);
            int res = stmt.executeUpdate();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
        
    }
    
    
    public List<Post> getUserPostsByUser(int ID)
    {
        List<Post> posts = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.user_posts +
                    " JOIN " + Tables.postType + " ON " + Columns.userPosts_type + " = " + Columns.postType_id +
                    " WHERE " + Columns.user_id + " = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            while (res.next())
            {
                Post post = makeUserPost(res);
                posts.add(post);
            }
            
            stmt.close();
            conn.close();
            return posts;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
        
    }
    
    public List<Post> getAllPostsByUser(int ID)
    {
        List<Post> userpost = this.getUserPostsByUser(ID);
        List<Post> grouppost = this.getGroupPostsByUser(ID);
        List<Post> result = new ArrayList<>();
        result.addAll(userpost);
        result.addAll(grouppost);
        return result;
    }
    
    public List<Post> getGroupPostsByUser(int ID)
    {
        List<Post> posts = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.group_posts +
                    " JOIN " + Tables.postType + " ON " + Columns.groupPosts_type + " = " + Columns.postType_id +
                    " WHERE "+ Columns.user_id +" = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            while (res.next())
            {
                Post post = makeUserPost(res);
                posts.add(post);
            }
            
            stmt.close();
            conn.close();
            return posts;
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
