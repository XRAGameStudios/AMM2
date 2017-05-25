package amm.nerdbook.classes;

import amm.nerdbook.Actions;
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
import javax.servlet.http.HttpServletRequest;

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
    public Post makeGroupPostByRequest(HttpServletRequest request)
    {
        if (request.getParameter("action")!=null && request.getParameter("action").equals("send"))
        {
            Post post = new Post();
            //L'utente ha premuto il tasto "Invia".
            //Ora ricavo i parametri:
            String teamID = request.getParameter("team");
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
                post.setGroup(GroupFactory.getInstance().getGroupByID(Integer.parseInt(teamID)));
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
    
    public Post makeUserPostByRequest(HttpServletRequest request)
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
    public boolean addNewUserPostOnDatabase (String content, int type, int authorID, int receiverID, String attachment)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "INSERT INTO " + Tables.user_posts + "(postID,content,type,author,toUser,attachment)" +
                    " VALUES (default,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setInt(2, type);
            stmt.setInt(3,authorID);
            stmt.setInt(4,receiverID);
            stmt.setString(5, attachment);
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return false;
    }
    public boolean addNewGroupPostOnDatabase (String content, int type, int authorID, int receiverID, String attachment)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "INSERT INTO " + Tables.group_posts + "(postID,content,type,author,toGroup,attachment)" +
                    " VALUES (default,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setInt(2, type);
            stmt.setInt(3,authorID);
            stmt.setInt(4,receiverID);
            stmt.setString(5, attachment);
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    //crea un oggetto userPost da un risultato di una query
    public Post makeUserPost(ResultSet res) throws SQLException
    {
        Post post = new Post();
        post.setID(res.getInt(Columns.userPosts_id));
        post.setAuthor(UserFactory.getInstance().getUserByID(res.getInt(Columns.userPosts_author)));
        post.setPostTypeByString(res.getString(Columns.postType_name));
        post.setURL(res.getString(Columns.userPosts_attachment));
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
        post.setPostTypeByString(res.getString(Columns.postType_name));
        post.setURL(res.getString(Columns.groupPosts_attachment));
        post.setContent(res.getString(Columns.groupPosts_content));
        return post;
    }
    
    public boolean addNewUserPost (Post post)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "INSERT INTO " + Tables.user_posts + "(postID,content,type,author,toUser,attachment)" +
                    " VALUES (default,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, post.getContent());
            stmt.setInt(2, (post.getPostType().ordinal() + 1));
            stmt.setInt(3,post.getAuthor().getID());
            stmt.setInt(4,post.getUser().getID());
            stmt.setString(5, post.getURL());
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public boolean addNewGroupPost (Post post)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "INSERT INTO " + Tables.user_posts + "(postID,content,type,author,toGroup,attachment)" +
                    " VALUES (default,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, post.getContent());
            stmt.setInt(2, post.getPostType().ordinal());
            stmt.setInt(3,post.getAuthor().getID());
            stmt.setInt(4,post.getGroup().getID());
            stmt.setString(5, post.getURL());
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
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
            stmt.setInt(1, ID);
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
        
    }
    public boolean deleteGroupPost(int ID)
    {
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String update =
                    "DELETE FROM " + Tables.group_posts +
                    " WHERE "+ Columns.groupPosts_id + " = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setInt(1, ID);
            int result = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return (result==1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return false;
        
    }
    
    
    public List<Post> getPostsByGroupID (int ID)
    {
          List<Post> posts = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.group_posts +
                    " JOIN " + Tables.postType + " ON " + Columns.userPosts_type + " = " + Columns.postType_id +
                    " JOIN " + Tables.groups + " ON " + Columns.groupPosts_destination + " = " + Columns.groups_id +        
                    " WHERE " + Columns.groupPosts_destination + " = ?";
               PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            while (res.next())
            {
                Post post = makeGroupPost(res);
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
    
    public List<Post> getUserPostsByUser(int ID)
    {
        List<Post> posts = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection(connectionString,Admin.username,Admin.password);
            String query =
                    "SELECT * FROM " + Tables.user_posts +
                    " JOIN " + Tables.postType + " ON " + Columns.userPosts_type + " = " + Columns.postType_id +
                    " WHERE " + Columns.userPosts_destination + " = ?";
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
        List<Post> userPosts = this.getUserPostsByUser(ID);
        List<Post> groupPosts = this.getGroupPostsByUser(ID);
        List<Post> result = new ArrayList<>();
        result.addAll(userPosts);
        result.addAll(groupPosts);
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
                    " WHERE "+ Columns.groupPosts_author +" = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            while (res.next())
            {
                Post post = makeGroupPost(res);
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
