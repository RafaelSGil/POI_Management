package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.person.*;
import pt.isec.pa.apoio_poe.model.data.proposals.AutoProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.data.proposals.Project;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;

import java.util.*;

public class Data {
    Map<String, ArrayList<Proposal>> proposals;
    Map<String, ArrayList<Person>> persons;

    public Data() {

        this.persons = new HashMap<String, ArrayList<Person>>();
        this.proposals = new HashMap<String, ArrayList<Proposal>>();
        this.persons.put("students", null);
        this.persons.put("professors", null);
        this.proposals.put("internships", null);
        this.proposals.put("projects", null);
        this.proposals.put("autoproposals", null);
    }

    public void addStudentFile(List<List<String>> attributes){
        Iterator<List<String>> attributesIterator = attributes.iterator();

        String name;
        String email;
        String course;
        String courseBranch;
        long id;
        double classification;
        boolean internship;

        while (attributesIterator.hasNext()) {

            id = Long.parseLong(attributesIterator.next().get(0));
            name = attributesIterator.next().get(1);
            email = attributesIterator.next().get(2);
            course = attributesIterator.next().get(3);
            courseBranch = attributesIterator.next().get(4);
            classification = Double.parseDouble(attributesIterator.next().get(5));
            internship = Boolean.parseBoolean(attributesIterator.next().get(6));

            addStudent(name, email, id, course, courseBranch, classification, internship);
        }
    }

    public void addStudent(String name, String email, long id, String course, String courseBranch,
                           double classification, boolean internship) {

        if (name == null || email == null || course == null || courseBranch == null)
            return;

        if (persons.get("students").contains(Student.createDummyStudent(id)))
            return;

        persons.get("students")
                .add((Student.createStudent(name, email, id, course, courseBranch, classification, internship)));
    }

    public void addProfessorFile(List<List<String>> attributes){
        Iterator<List<String>> attributesIterator = attributes.iterator();

        String name;
        String email;
        boolean advisor;

        while (attributesIterator.hasNext()) {
            name = attributesIterator.next().get(0);
            email = attributesIterator.next().get(1);
            advisor = Boolean.parseBoolean(attributesIterator.next().get(2));

            addProfessor(name, email, advisor);
        }
    }

    public void addProfessor(String name, String email, boolean advisor) {

        if (name == null || email == null)
            return;

        if (persons.get("professors").contains(Professor.createDummyProfessor(email)))
            return;

        persons.get("professors").add(Professor.createProfessor(name, email, advisor));
    }

    public void addProposalFile(List<List<String>> attributes){
        Iterator<List<String>> attributesIterator = attributes.iterator();

        String idOfProposal;
        String title;
        Student student;
        String branch;
        Professor professor;
        String nameOfCompany;
        String first;

        while (attributesIterator.hasNext()) {
            first = attributesIterator.next().get(0);

            if (first.equals("T1")) { // Internship
                idOfProposal = attributesIterator.next().get(1);
                branch = attributesIterator.next().get(2);
                title = attributesIterator.next().get(3);
                nameOfCompany = attributesIterator.next().get(4);

                if (attributesIterator.next().size() > 5) {
                    student = (Student) Student.createDummyStudent(Long.parseLong(attributesIterator.next().get(5)));

                    addInternship(idOfProposal, title, student, branch, nameOfCompany);
                } else {
                    addInternshipWithoutStudent(idOfProposal, title, branch, nameOfCompany);
                }
            }

            else if (first.equals("T2")) { // Project
                idOfProposal = attributesIterator.next().get(1);
                branch = attributesIterator.next().get(2);
                title = attributesIterator.next().get(3);
                professor = (Professor) Professor.createDummyProfessor(attributesIterator.next().get(4));

                if (attributesIterator.next().size() > 5) {
                    student = (Student) Student.createDummyStudent(Long.parseLong(attributesIterator.next().get(5)));

                    addProject(idOfProposal, title, student, branch, professor);
                } else {
                    addProjectWithoutStudent(idOfProposal, title, branch, professor);
                }
            }

            else if (first.equals("T3")) { // Auto proposal
                idOfProposal = attributesIterator.next().get(1);
                title = attributesIterator.next().get(2);
                student = (Student) Student.createDummyStudent(Long.parseLong(attributesIterator.next().get(3)));

                addAutoProposal(idOfProposal, title, student);
            }
        }
    }

    public void addInternship(String idOfProposal, String title, Student student, String branch,
                              String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (proposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        if (!(persons.get("students").contains(Student.createDummyStudent(student.getId()))))
            return;

        proposals.get("internships")
                .add(Internship.createInternship(idOfProposal, title, student, branch, nameOfCompany));
    }

    public void addInternshipWithoutStudent(String idOfProposal, String title, String branch, String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (proposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        proposals.get("internships")
                .add(Internship.createInternship(idOfProposal, title, null, branch, nameOfCompany));
    }

    public void addProject(String idOfProposal, String title, Student student, String branch, Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (!(persons.get("professors").contains(Professor.createDummyProfessor(professor.getEmail()))))
            return;

        if (!(persons.get("students").contains(Student.createDummyStudent(student.getId()))))
            return;

        if (proposals.get("projects").contains(Project.createDummyProject(idOfProposal)))
            return;

        proposals.get("projects").add(Project.createProject(idOfProposal, title, student, branch, professor));
    }

    public void addProjectWithoutStudent(String idOfProposal, String title, String branch, Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (proposals.get("projects").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        proposals.get("projects")
                .add(Project.createProject(idOfProposal, title, null, branch, professor));
    }

    public void addAutoProposal(String idOfProposal, String title, Student student) {
        if (idOfProposal == null || title == null)
            return;

        if (!(persons.get("students").contains(Student.createDummyStudent(student.getId()))))
            return;

        if (proposals.get("autoproposals").contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return;

        proposals.get("autoproposals").add(AutoProposal.createAutoProposal(idOfProposal, title, student));
    }

    public String getStudentGivenItsID(long idOfStudent) {
        if (persons.get("students").contains(Student.createDummyStudent(idOfStudent)))
            return "not found";

        return persons.get("students").get(persons.get("students").indexOf(Student.createDummyStudent(idOfStudent)))
                .toString();
    }

    public String getProfessorGivenItsEmail(String email) {
        if (persons.get("professors").contains(Professor.createDummyProfessor(email)))
            return "not found";

        return persons.get("professors").get(persons.get("professors").indexOf(Professor.createDummyProfessor(email)))
                .toString();
    }

    public boolean removeStudentGivenItsId(long idOfStudent) {
        if (persons.get("students").contains(Student.createDummyStudent(idOfStudent)))
            return false;

        return persons.get("students").remove(Student.createDummyStudent(idOfStudent));
    }

    public boolean removeProfessorGivenItsEmail(String email) {
        if (email == null) {
            return false;
        }

        if (!persons.get("professors").contains(Professor.createDummyProfessor(email)))
            return false;

        return persons.get("professors").remove(Professor.createDummyProfessor(email));
    }

    public boolean removeInternshipsGivenItsID(String idOfProposal) {
        if (idOfProposal == null) {
            return false;
        }

        if (!proposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return false;

        return proposals.get("internships").remove(Internship.createDummyInternship(idOfProposal));
    }

    public boolean removeAutoProposalGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!proposals.get("autoproposals").contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return false;

        return proposals.get("autoproposals").remove(AutoProposal.createDummyAutoProposal(idOfProposal));
    }

    public boolean removeProjectsGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!proposals.get("projects").contains(Project.createDummyProject(idOfProposal)))
            return false;

        return proposals.get("projects").remove(Project.createDummyProject(idOfProposal));
    }

    public String getAllStudents() {
        StringBuilder sb = new StringBuilder();

        if (persons.get("studens").size() == 0) {
            sb.append("No students registered");
            return sb.toString();
        }

        for (Person person : persons.get("students")) {
            sb.append(person.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getAllProfessors() {
        StringBuilder sb = new StringBuilder();

        if (persons.get("professors").size() == 0) {
            sb.append("No professors registered");
            return sb.toString();
        }

        for (Person person : persons.get("professors")) {
            sb.append(person.toString()).append("\n");
        }
        return sb.toString();
    }
}