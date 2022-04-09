package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.person.*;
import pt.isec.pa.apoio_poe.model.data.proposals.AutoProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.data.proposals.MidProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Project;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;
import java.util.*;

public class Data {
    Map<String, ArrayList<Proposal>> autoproposals;
    Set<Student> students;
    Set<Professor> professors;
    Map<String, ArrayList<MidProposal>> midProposals;

    public Data() {

        this.students = new HashSet<>();
        this.professors = new HashSet<>();
        this.autoproposals = new HashMap<String, ArrayList<Proposal>>();
        this.midProposals = new HashMap<String, ArrayList<MidProposal>>();
        this.midProposals.put("internships", null);
        this.midProposals.put("projects", null);
        this.autoproposals.put("autoproposals", null);
    }

    public void addStudentFile(List<List<String>> attributes) {
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

        if (students.contains(Student.createDummyStudent(id)))
            return;

        students.add((Student.createStudent(name, email, id, course, courseBranch, classification, internship)));
    }

    public void addProfessorFile(List<List<String>> attributes) {
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

        if (professors.contains(Professor.createDummyProfessor(email)))
            return;

        professors.add(Professor.createProfessor(name, email, advisor));
    }

    public void addProposalFile(List<List<String>> attributes) {
        Iterator<List<String>> attributesIterator = attributes.iterator();

        String idOfProposal;
        String title;
        Student student;
        List<String> branch;
        Professor professor;
        String nameOfCompany;
        String first;

        while (attributesIterator.hasNext()) {
            first = attributesIterator.next().get(0);

            if (first.equals("T1")) { // Internship
                idOfProposal = attributesIterator.next().get(1);
                branch = List.of(attributesIterator.next().get(2).split("[|]+"));
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
                branch = List.of(attributesIterator.next().get(2).split("[|]+"));
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

    public void addInternship(String idOfProposal, String title, Student student, List<String> branch,
            String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (midProposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        if (!(students.contains(Student.createDummyStudent(student.getId()))))
            return;

        midProposals.get("internships")
                .add(Internship.createInternship(idOfProposal, title, student, branch, nameOfCompany));
    }

    public void addInternshipWithoutStudent(String idOfProposal, String title, List<String> branch,
            String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (midProposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        midProposals.get("internships")
                .add(Internship.createInternship(idOfProposal, title, null, branch, nameOfCompany));
    }

    public void addProject(String idOfProposal, String title, Student student, List<String> branch,
            Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (!(professors.contains(Professor.createDummyProfessor(professor.getEmail()))))
            return;

        if (!(professors.contains(Student.createDummyStudent(student.getId()))))
            return;

        if (midProposals.get("projects").contains(Project.createDummyProject(idOfProposal)))
            return;

        midProposals.get("projects").add(Project.createProject(idOfProposal, title, student, branch, professor));
    }

    public void addProjectWithoutStudent(String idOfProposal, String title, List<String> branch,
            Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (midProposals.get("projects").contains(Internship.createDummyInternship(idOfProposal)))
            return;

        midProposals.get("projects")
                .add(Project.createProject(idOfProposal, title, null, branch, professor));
    }

    public void addAutoProposal(String idOfProposal, String title, Student student) {
        if (idOfProposal == null || title == null)
            return;

        if (!(students.contains(Student.createDummyStudent(student.getId()))))
            return;

        if (autoproposals.get("autoproposals").contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return;

        autoproposals.get("autoproposals").add(AutoProposal.createAutoProposal(idOfProposal, title, student));
    }

    public String getStudentGivenItsID(long idOfStudent) {
        for (Student st : students) {
            if (st.equals(Student.createDummyStudent(idOfStudent))) {
                return st.toString();
            }
        }

        return "Could not find the given student";
    }

    public String getProfessorGivenItsEmail(String email) {
        for (Professor pf : professors) {
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
        if (email == null) {
            return false;
        }

        return professors.remove(Professor.createDummyProfessor(email));
    }

    public boolean removeInternshipsGivenItsID(String idOfProposal) {
        if (idOfProposal == null) {
            return false;
        }

        if (!midProposals.get("internships").contains(Internship.createDummyInternship(idOfProposal)))
            return false;

        return midProposals.get("internships").remove(Internship.createDummyInternship(idOfProposal));
    }

    public boolean removeAutoProposalGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!autoproposals.get("autoproposals").contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return false;

        return autoproposals.get("autoproposals").remove(AutoProposal.createDummyAutoProposal(idOfProposal));
    }

    public boolean removeProjectsGiveItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!midProposals.get("projects").contains(Project.createDummyProject(idOfProposal)))
            return false;

        return midProposals.get("projects").remove(Project.createDummyProject(idOfProposal));
    }

    public String getAllStudents() {
        StringBuilder sb = new StringBuilder();

        if (students.size() == 0) {
            sb.append("No students registered");
            return sb.toString();
        }

        for (Student student : students) {
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

        for (Professor professor : professors) {
            sb.append(professor.toString()).append("\n");
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

        for (MidProposal mid : midProposals.get("internships")) {
            if (mid.compareBranch("DA"))
                counterDaProposals++;
            if (mid.compareBranch("SI"))
                counterSiProposals++;
            if (mid.compareBranch("RAS"))
                counterRasStudents++;
        }

        for (MidProposal mid : midProposals.get("projects")) {
            if (mid.compareBranch(("DA")))
                counterDaProposals++;
            if (mid.compareBranch("SI"))
                counterSiProposals++;
            if (mid.compareBranch("RAS"))
                counterRasProposals++;
        }

        for (Student student : students) {
            if (student.getCourseBranch().equals("DA"))
                counterDaStudents++;

            if (student.getCourseBranch().equals("SI"))
                counterSiStudents++;

            if (student.getCourseBranch().equals("RAS"))
                counterRasStudents++;
        }

        if ((counterDaStudents <= counterDaProposals) && (counterRasStudents <= counterRasProposals)
                && (counterSiStudents <= counterSiProposals))
            return true;
        return false;
    }

}