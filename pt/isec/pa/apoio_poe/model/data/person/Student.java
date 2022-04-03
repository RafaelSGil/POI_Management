package pt.isec.pa.apoio_poe.model.data.person;

public class Student extends Person{
    private final long id;
    private String course;
    private String courseBranch;
    private double classification;
    private boolean internship;

    public Student(String name, String email, long id, String course, String courseBranch, double classification, boolean internship){
        super(name, email);
        this.id = id;
        this.course = course;
        this.courseBranch = courseBranch;
        this.classification = classification;
        this.internship = internship;
    }

    public long getId(){return id;}

    public String getCourse(){return course;}

    public void setCourse(String course){this.course = course;}

    public String getCourseBranch(){return courseBranch;}

    public void setCourseBranch(String courseBranch){this.courseBranch = courseBranch;}

    public double getClassification(){return classification;}  

    public void setClassification(double classification){this.classification = classification;}

    public boolean getInternship(){return internship;}

    public void setInternship(boolean internship){this.internship = internship;}
}
