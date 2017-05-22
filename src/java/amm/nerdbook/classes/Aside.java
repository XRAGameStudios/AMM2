/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package amm.nerdbook.classes;

import java.util.ArrayList;
import java.util.List;

//Organizzo il tutto per classi per tenere il codice ordinato e "logico".
public class Aside
{
    //trovo tutti i gruppi dove un utente è iscritto.
    public static List<Group> getGroups(User user)
    {
        GroupFactory gf = GroupFactory.getInstance();
        return gf.getGroupsByUserID(user.getID());
    }
    
    public static List<User> getUsers(User user)
    {
        List<User> users = new ArrayList<>();
        UserFactory uf = UserFactory.getInstance();
        for (User u: uf.getAllUsers())
        {
            if (user!=u)
                users.add(u);
        }
        return users;
    }
}
