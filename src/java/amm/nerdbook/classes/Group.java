/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package amm.nerdbook.classes;


/**
 *
 * @author fabio
 */
public class Group
{
    private String name;
    private int ID;
    private String icon;
    
    // costruttore di un gruppo non valido
    public Group()
    {
        setID(-1);
        setName(null);
    }
    
    //costruttore pronto
    public Group(String groupName,int groupID)
    {
        setID(groupID);
        setName(groupName);
        
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getID()
    {
        return ID;
    }
    
    public void setID(int ID)
    {
        this.ID=ID;
    }
    
    //in modo da sapere quanti membri fanno parte di un gruppo
    
    // due gruppi sono uguali sse il loro ID è univocamente identico.
    @Override
    public boolean equals(Object group)
    {
        if (group instanceof Group)
        {
            if (((Group)group).getID() ==getID())
                return true;
        }
        return false;
    }



    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    
    
    
}
