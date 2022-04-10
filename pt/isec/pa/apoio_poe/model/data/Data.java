package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.person.*;
import pt.isec.pa.apoio_poe.model.data.proposals.AutoProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.data.proposals.MidProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Project;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;
import java.util.*;

public class Data {
    Set<Proposal> autoproposals;
    Set<Person> students;
    Set<Person> professors;
    Set<MidProposal> internships;
    Set<MidProposal> projects;

    public Data() {

        this.students = new HashSet<>();
        this.professors = new HashSet<>();
        this.autoproposals = new HashSet<>();
        this.internships = new HashSet<>();
        this.projects = new HashSet<>();
    }

    public void addStudentFile(List<List<String>> attributes) {

        Iterator listsOfListsIterator = attributes.iterator();

        String name;
        String email;
        String course;
        String courseBranch;
        long id;
        double classification;
        boolean internship;

        while (listsOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listsOfListsIterator.next();

            Iterator eachListIterator = list.iterator();

            while (eachListIterator.hasNext()) {
                id = Long.parseLong((String) eachListIterator.next());
                name = (String) eachListIterator.next();
                email = (String) eachListIterator.next();
                course = (String) eachListIterator.next();
                courseBranch = (String) eachListIterator.next();
                classification = Double.parseDouble((String) eachListIterator.next());
                internship = Boolean.parseBoolean((String) eachListIterator.next());

                addStudent(name, email, id, course, courseBranch, classification, internship);

            }
        }
    }

    public void addStudent(String name, String email, long id, String course, String courseBranch,
            double classification, boolean internship) {

        if (name == null || email == null || course == null || courseBranch == null)
            return;

        if (students.contains(Student.createDummyStudent(id)))
            return;

        students.add((Student.createStudent(name, email, id, course, courseBranch, classification, internship)));
    }

    public void addProfessorFile(List<List<String>> attributes) {
        Iterator listsOfListsIterator = attributes.iterator();

        String name;
        String email;
        boolean advisor;

        while (listsOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listsOfListsIterator.next();

            Iterator eachListIterator = list.iterator();

            while (eachListIterator.hasNext()) {
                name = (String) eachListIterator.next();
                email = (String) eachListIterator.next();
                advisor = Boolean.parseBoolean((String) eachListIterator.next());

                addProfessor(name, email, advisor);
            }
        }
    }

    public void addProfessor(String name, String email, boolean advisor) {

        if (name == null || email == null)
            return;

        if (professors.contains(Professor.createDummyProfessor(email)))
            return;

        professors.add(Professor.createProfessor(name, email, advisor));
    }

    public void addProposalFile(List<List<String>> attributes) {
        Iterator<List<String>> listOfListsIterator = attributes.iterator();

        String auxFirstElement;
        String idOfProposal;
        String title;
        Student student;
        List<String> branch;
        Professor professor;
        String nameOfCompany;
        String first;

        while (listOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listOfListsIterator.next();

            Iterator eachListIterator = list.iterator();

            if (list.get(0).equals("T1")) { // Internship
                auxFirstElement = (String) eachListIterator.next();
                idOfProposal = (String) eachListIterator.next();
                branch = List.of(((String) eachListIterator.next()).split("[|]+"));
                title = (String) eachListIterator.next();
                nameOfCompany = (String) eachListIterator.next();

                if (list.size() > 5) {
                    student = (Student) Student.createDummyStudent(Long.parseLong((String) eachListIterator.next()));
                    addInternship(idOfProposal, title, student, branch, nameOfCompany);
                } else {
                    addInternshipWithoutStudent(idOfProposal, title, branch, nameOfCompany);
                }
            }

            else if (list.get(0).equals("T2")) { // Project
                auxFirstElement = (String) eachListIterator.next();
                idOfProposal = (String) eachListIterator.next();
                branch = List.of(((String) eachListIterator.next()).split("[|]+"));
                title = (String) eachListIterator.next();
                professor = (Professor) Professor.createDummyProfessor((String) eachListIterator.next());

                if (list.size() > 5) {
                    student = (Student) Student.createDummyStudent(Long.parseLong((String) eachListIterator.next()));

                    addProject(idOfProposal, title, student, branch, professor);
                } else {
                    addProjectWithoutStudent(idOfProposal, title, branch, professor);
                }
            }

            else if (list.get(0).equals("T3")) { // Auto proposal
                auxFirstElement = (String) eachListIterator.next();
                idOfProposal = (String) eachListIterator.next();
                title = (String) eachListIterator.next();
                student = (Student) Student.createDummyStudent(Long.parseLong((String) eachListIterator.next()));

                addAutoProposal(idOfProposal, title, student);
            }
        }
    }

    public void addInternship(String idOfProposal, String title, Student student, List<String> branch,
            String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (internships.contains(Internship.createDummyInternship(idOfProposal)))
            return;

        if (!(students.contains(Student.createDummyStudent(student.getId()))))
            return;

        internships.add(Internship.createInternship(idOfProposal, title, student, branch, nameOfCompany));
    }

    public void addInternshipWithoutStudent(String idOfProposal, String title, List<String> branch,
            String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;


        if (internships.contains(Internship.createDummyInternship(idOfProposal)))
            return;

        internships.add(Internship.createInternship(idOfProposal, title, null, branch,
                nameOfCompany));

    }

    public void addProject(String idOfProposal, String title, Student student, List<String> branch,
            Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (!(professors.contains(Professor.createDummyProfessor(professor.getEmail()))))
            return;

        if (!(students.contains(Student.createDummyStudent(student.getId()))))
            return;

        if (projects.contains(Project.createDummyProject(idOfProposal)))
            return;

        projects.add(Project.createProject(idOfProposal, title, student, branch, professor));
    }

    public void addProjectWithoutStudent(String idOfProposal, String title, List<String> branch,
            Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (projects.contains(Internship.createDummyInternship(idOfProposal)))
            return;

        projects.add(Project.createProject(idOfProposal, title, null, branch, professor));
    }

    public void addAutoProposal(String idOfProposal, String title, Student student) {
        if (idOfProposal == null || title == null)
            return;

        if (!(students.contains(Student.createDummyStudent(student.getId()))))
            return;


        if (autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return;

        autoproposals.add(AutoProposal.createAutoProposal(idOfProposal, title, student));
    }

    public String getStudentGivenItsID(long idOfStudent) {
        for (Person st : students) {
            if (st.equals(Student.createDummyStudent(idOfStudent))) {
                return st.toString();
            }
        }

        return "Could not find the given student";
    }

    public String getProfessorGivenItsEmail(String email) {
        for (Person pf : professors) {
            if (pf.equals(Professor.createDummyProfessor(email))) {
                return pf.toString();
            }
        }
        return "Could not find the given professor";

    }

    public boolean removeStudentGivenItsId(long idOfStudent) {
        return students.remove(Student.createDummyStudent(idOfStudent));
    }

    public boolean removeProfessorGivenItsEmail(String email) {
        return professors.remove(Professor.createDummyProfessor(email));
    }

    public boolean removeProposals(String idOfProposal){
        if(removeInternshipsGivenItsID(idOfProposal))
            return true;

        if(removeProjectsGiveItsID(idOfProposal))
            return true;

        if(removeAutoProposalGiveItsID(idOfProposal))
            return true;

        return false;
    }

    public boolean removeInternshipsGivenItsID(String idOfProposal) {
        if (idOfProposal == null) {
            return false;
        }

        if (!internships.contains(Internship.createDummyInternship(idOfProposal)))
            return false;

        return internships.remove(Internship.createDummyInternship(idOfProposal));
    }

    public boolean removeAutoProposalGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return false;

        return autoproposals.remove(AutoProposal.createDummyAutoProposal(idOfProposal));
    }

    public boolean removeProjectsGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!projects.contains(Project.createDummyProject(idOfProposal)))
            return false;

        return projects.remove(Project.createDummyProject(idOfProposal));
    }

    public String getAllStudents() {
        StringBuilder sb = new StringBuilder();

        if (students.size() == 0) {
            sb.append("No students registered");
            return sb.toString();
        }

        for (Person student : students) {
            sb.append(student.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getAllProfessors() {
        StringBuilder sb = new StringBuilder();

        if (professors.size() == 0) {
            sb.append("No professors registered");
            return sb.toString();
        }

        for (Person professor : professors) {
            sb.append(professor.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getAllProjects() {
        StringBuilder sb = new StringBuilder();

        if (autoproposals.size() == 0 && internships.size() == 0 && projects.size() == 0) {
            sb.append("No projects registered");
            return sb.toString();
        }

        sb.append(("AutoProposals: \n"));
        for (Proposal auto : autoproposals) {
            sb.append(auto.toString()).append("\n");
        }

        sb.append("Projects: \n");
        for (MidProposal mid : projects) {
            sb.append(mid.toString()).append("\n");
        }

        sb.append("InternShips: \n");
        for (MidProposal mid : internships) {
            sb.append(mid.toString()).append("\n");
        }
        return sb.toString();
    }

    public boolean lockPhase1() {

        int counterDaProposals = 0;
        int counterSiProposals = 0;
        int counterRasProposals = 0;
        int counterDaStudents = 0;
        int counterSiStudents = 0;
        int counterRasStudents = 0;

        for (MidProposal mid : internships) {
            if (mid.compareBranch("DA"))
                counterDaProposals++;
            if (mid.compareBranch("SI"))
                counterSiProposals++;
            if (mid.compareBranch("RAS"))
                counterRasStudents++;
        }

        for (MidProposal mid : projects) {
            if (mid.compareBranch(("DA")))
                counterDaProposals++;
            if (mid.compareBranch("SI"))
                counterSiProposals++;
            if (mid.compareBranch("RAS"))
                counterRasProposals++;
        }

        for (Person student : students) {
            if (((Student)student).getCourseBranch().equals("DA"))
                counterDaStudents++;

            if (((Student)student).getCourseBranch().equals("SI"))
                counterSiStudents++;

            if (((Student)student).getCourseBranch().equals("RAS"))
                counterRasStudents++;
        }

        return (counterDaStudents <= counterDaProposals) && (counterRasStudents <= counterRasProposals)
                && (counterSiStudents <= counterSiProposals);
    }

}