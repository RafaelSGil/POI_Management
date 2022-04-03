package pt.isec.pa.apoio_poe.model.data.person;

public abstract class Person {
    private final String name;
    private final String email;
    
    public String getName(){return name;}

    public String getEmail(){return email;}

    public Person(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
}
