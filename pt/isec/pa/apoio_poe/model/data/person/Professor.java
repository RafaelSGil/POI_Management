package pt.isec.pa.apoio_poe.model.data.person;

public class Professor extends Person{
    boolean advisor;

    public Professor(String name, String email, boolean advisor){
        super(name, email);
        this.advisor = advisor;        
    }

    public boolean getAdvisor(){return advisor;}

    public void setAdvisor(boolean advisor){this.advisor = advisor;}
}
