package amm.nerdbook.classes;

/**
 *
 * @author fabio
 */
public class Post
{
    public enum Type {TEXT, URL, IMAGE};
    //author è chi ha scritto il post
    //user è chi ha ricevuto il post. Possono combaciare.
    private User author, user;
    private Group group;
    private boolean userPost = true;
    private String url, content;
    private Type postType;
    private int ID;
    
    public Post()
    {
        setID(-1);
        url=content="";
        postType= Type.TEXT;
    }
    
    public User getAuthor()
    {
        return author;
    }
    
    public void setAuthor(User author)
    {
        this.author = author;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public int getID()
    {
        return ID;
    }
    
    public void setID(int ID)
    {
        this.ID = ID;
    }
    
    public Type getPostType()
    {
        return postType;
    }
    
    public void setPostType(Type postType)
    {
        this.postType = postType;
    }
    
    public void setAsImage()
    {
        setPostType(Post.Type.IMAGE);
    }
    
    public void setAsURL()
    {
        setPostType(Post.Type.URL);
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
        setAsUserPost();
        
    }

    /**
     * @return the group
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(Group group)
    {
        this.group = group;
        setAsGroupPost();
    }

    /**
     * @return the isUserPost
     */
    public boolean isUserPost()
    {
        return userPost;
    }
    
    public boolean isGroupPost()
    {
        return !userPost;
    }
    
    public void setAsUserPost()
    {
        this.userPost = true;
    }
    public void setAsGroupPost()
    {
        this.userPost = false;
    }
    
}
