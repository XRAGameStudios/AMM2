package amm.nerdbook.database;

public class Columns
{
    //tutti i nomi delle colonne della tabella "users"
    public static final String user_name="name";
    public static final String user_id="userID";
    public static final String user_surname="surname";
    public static final String user_email="email";
    public static final String user_username="username";
    public static final String user_password="password";
    public static final String user_imageURL="url";
    public static final String user_birth="birthday";
    public static final String user_status="status";
    //tutti i nomi delle colonne della tabella "userPosts"
    public static final String posts_id="postID";
    public static final String posts_content="content";
    public static final String posts_type="type";
    public static final String posts_author="author";
    public static final String posts_destination_user="toUser";
    public static final String posts_destination_group="toGroup";
    public static final String posts_attachment="attachment";
    //tutti i nomi delle colonne della tabella "groups"
    public static final String groups_id="groupID";
    public static final String groups_name="name";
    public static final String groups_icon="icon";
    public static final String groups_founder="founder";
    //tutti i nomi delle colonne della tabella "postType"
    public static final String postType_id="postTypeID";
    public static final String postType_name="name";
    //tutti i nomi delle colonne della tabella "friends"
    public static final String friends_followed="followed";
    public static final String friends_follower="follower";
    //tutti i nomi delle colonne della tabella "teams"
    public static final String teams_joiner="joiner";
    public static final String teams_team="team";
    //tutti i nomi delle colonne della tabella "administrators"
    public static final String admins_id="adminID";
    public static final String admins_admin="adminUser";
    
    
}
