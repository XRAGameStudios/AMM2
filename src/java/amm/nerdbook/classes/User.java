package amm.nerdbook.classes;

public class User
{
    //Stringhe statiche dei nomi tabelle DB

    
    
    private String name,surname,address, date, email, password, imageURL, fullname;
    private String status, age;
    private int ID;
    private final String defaultImageURL ="images/user/default.jpg";
    //al posto dell'username è possibile accedere con email e password.
    //per usare un username basta semplicemente aggiungere un campo
    //private String username;
    //infine aggiungendo i costruttori e sostituendo il metodo di verifica con l'username si accede al sito.
    
    public User()
    {
        ID=-1;
        age="";
        name=surname=address=date=email=password=imageURL=fullname="";
    }
    //inserisco un secondo costruttore per creare direttamente un utente.
    public User(String name, String surname, String email, String password, String age)
    {
        setName(name);
        setSurname(surname);
        setEmail(email);
        setPassword(password);
        setAge(age);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSurname()
    {
        return surname;
    }
    
    public void setSurname(String surname)
    {
        this.surname = surname;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getImageURL()
    {
        if (imageURL!=null)
            return imageURL;
        else
            return defaultImageURL;
    }
    
    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    @Override
    public boolean equals(Object user)
    {
        if (user instanceof User)
        {
            if (getID() == ((User) user).getID())
                return true;
            else
                return false;
        }
        return false;
        
    }
    
    public int getID()
    {
        return ID;
    }
    
    public void setID(int ID)
    {
        this.ID = ID;
    }
    
    public String getFullname()
    {
        fullname= name + " " + surname;
        return fullname;
    }
    

    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    /**
     * @return the defaultImageURL
     */
    public String getDefaultImageURL()
    {
        return defaultImageURL;
    }
    
}
