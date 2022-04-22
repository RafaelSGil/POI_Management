package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.person.*;
import pt.isec.pa.apoio_poe.model.data.proposals.AutoProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.data.proposals.MidProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Project;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

import java.util.*;

public class Data {
    Set<Proposal> autoproposals;
    Set<Person> students;
    Set<Person> professors;
    Set<MidProposal> internships;
    Set<MidProposal> projects;
    Map<Long, List<String>> candidatures;
    Map<ApplicationState, Boolean> lockedPhases;
    Map<String, Long> proposalAttributions;
    Map<String, List<String>> advisorAttribution;

    public Data() {

        this.students = new HashSet<>();
        this.professors = new HashSet<>();
        this.autoproposals = new HashSet<>();
        this.internships = new HashSet<>();
        this.projects = new HashSet<>();
        this.candidatures = new HashMap<>();
        this.lockedPhases = new HashMap<>();
        this.proposalAttributions = new HashMap<>();
        this.advisorAttribution = new HashMap<>();
        startMap();
    }

    private void startMap() {
        // initializes the map with all enum states and gives them the value false,
        // which means the state has not yet been locked

        for (ApplicationState state : ApplicationState.values()) {
            lockedPhases.put(state, false);
        }
    }

    public boolean isLocked(ApplicationState state) {
        // checks if given state is already locked

        return lockedPhases.get(state);
    }

    public void lockPhase(ApplicationState state) {
        // locks the given state by attributing it a true value

        lockedPhases.put(state, true);
    }

    public void addStudentFile(List<List<String>> attributes) {

        Iterator<List<String>> listsOfListsIterator = attributes.iterator();

        String name;
        String email;
        String course;
        String courseBranch;
        long id;
        double classification;
        boolean internship;
        Set<String> courses = new HashSet<>();
        Set<String> coursesBranches = new HashSet<>();
        courses.add("LEI");
        courses.add("LEI-PL");
        coursesBranches.add("DA");
        coursesBranches.add("SI");
        coursesBranches.add("RAS");

        while (listsOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listsOfListsIterator.next();

            Iterator<String> eachListIterator = list.iterator();

            while (eachListIterator.hasNext()) {
                id = Long.parseLong((String) eachListIterator.next());
                name = (String) eachListIterator.next();
                email = (String) eachListIterator.next();
                course = (String) eachListIterator.next();
                courseBranch = (String) eachListIterator.next();
                classification = Double.parseDouble(eachListIterator.next());
                internship = Boolean.parseBoolean(eachListIterator.next());

                if (!students.contains(Student.createDummyStudent(id)))
                    if (courses.contains(course))
                        if (coursesBranches.contains(courseBranch))
                            if (classification % 1 != 0)
                                if (internship == true || internship == false)
                                    addStudent(name, email, id, course, courseBranch, classification, internship);

            }
        }
    }

    public boolean editStudent(String identifier, String change, String whatToChange) {
        Student aux = (Student) Student.createDummyStudent((Long.parseLong(identifier)));

        if (students.contains(aux)) {

            if (change.equals("name")) {
                for (Person student : students) {
                    if (student.equals(aux)) {
                        student.setName(whatToChange);
                        return true;
                    }
                }
            } else if (change.equals("course")) {
                for (Person student : students) {
                    if (student.equals(aux)) {
                        student.setCourse(whatToChange);
                        return true;
                    }
                }
            } else if (change.equals("course branch")) {
                for (Person student : students) {
                    if (student.equals(aux)) {
                        student.setCourse(whatToChange);
                    }
                }
            } else if (change.equals("classification")) {
                for (Person student : students) {
                    if (student.equals(aux)) {
                        student.setClassification(Double.parseDouble(whatToChange));
                        return true;
                    }
                }
            } else if (change.equals("internship")) {
                for (Person student : students) {
                    if (student.equals(aux)) {
                        student.setInternship(Boolean.parseBoolean(whatToChange));
                        return true;
                    }
                }
            }
        }
        return false;
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
        Iterator<List<String>> listsOfListsIterator = attributes.iterator();

        String name;
        String email;
        boolean advisor;

        while (listsOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listsOfListsIterator.next();

            Iterator<String> eachListIterator = list.iterator();

            while (eachListIterator.hasNext()) {
                name = (String) eachListIterator.next();
                email = (String) eachListIterator.next();
                advisor = Boolean.parseBoolean((String) eachListIterator.next());

                if (!professors.contains(Professor.createDummyProfessor(email)))
                    addProfessor(name, email, advisor);
            }
        }
    }

    public boolean editProfessor(String email, boolean advisor) {
        for (Person professor : professors) {
            if (professor.equals(Professor.createDummyProfessor(email))) {
                professor.setAdvisor(advisor);
                return true;
            }
        }
        return false;
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

        String idOfProposal;
        String title;
        Student student;
        List<String> branch;
        Person professor;
        String nameOfCompany;
        Set<String> coursesBranches = new HashSet<>();
        coursesBranches.add("DA");
        coursesBranches.add("SI");
        coursesBranches.add("RAS");
        Set<Student> studentsAdded = new HashSet<>();

        while (listOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            list = (List<String>) listOfListsIterator.next();

            Iterator<String> eachListIterator = list.iterator();
            eachListIterator.next();

            if (list.get(0).equals("T1")) { // Internship
                idOfProposal = (String) eachListIterator.next();
                branch = List.of(((String) eachListIterator.next()).split("[|]+"));
                title = (String) eachListIterator.next();
                nameOfCompany = (String) eachListIterator.next();

                if (!projects.contains(Project.createDummyProject(idOfProposal))
                        && !internships.contains(Internship.createDummyInternship(idOfProposal))
                        && !autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal))) {
                    if (coursesBranches.containsAll(branch)) {
                        if (list.size() > 5) {
                            student = (Student) Student
                                    .createDummyStudent(Long.parseLong((String) eachListIterator.next()));
                            if (!studentsAdded.contains(student)) {
                                addInternship(idOfProposal, title, student, branch, nameOfCompany);
                                studentsAdded.add(student);
                            }
                        } else {
                            addInternshipWithoutStudent(idOfProposal, title, branch, nameOfCompany);
                        }
                    }
                }
            }

            else if (list.get(0).equals("T2")) { // Project
                idOfProposal = (String) eachListIterator.next();
                branch = List.of(((String) eachListIterator.next()).split("[|]+"));
                title = (String) eachListIterator.next();
                professor = (Professor) Professor.createDummyProfessor((String) eachListIterator.next());

                if (!projects.contains(Project.createDummyProject(idOfProposal))
                        && !internships.contains(Internship.createDummyInternship(idOfProposal))
                        && !autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal))) {
                    if (coursesBranches.containsAll(branch)) {
                        if (professors.contains(professor)) {
                            if (list.size() > 5) {
                                student = (Student) Student
                                        .createDummyStudent(Long.parseLong((String) eachListIterator.next()));
                                if (!studentsAdded.contains(student)) {
                                    addProject(idOfProposal, title, student, branch, (Professor) professor);
                                    studentsAdded.add(student);
                                }
                            } else {
                                addProjectWithoutStudent(idOfProposal, title, branch, (Professor) professor);
                            }
                        }
                    }
                }
            }

            else if (list.get(0).equals("T3")) { // Auto proposal
                idOfProposal = (String) eachListIterator.next();
                title = (String) eachListIterator.next();
                student = (Student) Student.createDummyStudent(Long.parseLong((String) eachListIterator.next()));
                if (!projects.contains(Project.createDummyProject(idOfProposal))
                        && !internships.contains(Internship.createDummyInternship(idOfProposal))
                        && !autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
                    if (!studentsAdded.contains(student)) {
                        addAutoProposal(idOfProposal, title, student);
                        studentsAdded.add(student);
                    }
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

    public boolean removeProposals(String idOfProposal) {
        if (removeInternshipsGivenItsID(idOfProposal))
            return true;

        if (removeProjectsGivenItsID(idOfProposal))
            return true;

        return removeAutoProposalGivenItsID(idOfProposal);
    }

    public boolean removeInternshipsGivenItsID(String idOfProposal) {
        if (idOfProposal == null) {
            return false;
        }

        if (!internships.contains(Internship.createDummyInternship(idOfProposal)))
            return false;

        return internships.remove(Internship.createDummyInternship(idOfProposal));
    }

    public boolean removeAutoProposalGivenItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return false;

        return autoproposals.remove(AutoProposal.createDummyAutoProposal(idOfProposal));
    }

    public boolean removeProjectsGivenItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!projects.contains(Project.createDummyProject(idOfProposal)))
            return false;

        return projects.remove(Project.createDummyProject(idOfProposal));
    }

    public int checkTypeProposal(String identifier) {
        if (internships.contains(Internship.createDummyInternship(identifier))) {
            return 0;
        }
        if (projects.contains(Project.createDummyProject(identifier))) {
            return 1;
        }
        if (autoproposals.contains(AutoProposal.createDummyAutoProposal(identifier))) {
            return 2;
        }

        return -1;
    }

    public boolean editProposals(String identifier, String attribute, List<String> newValue) {
        if (internships.contains(Internship.createDummyInternship(identifier))) {
            editInternships(identifier, attribute, newValue);
        }
        if (projects.contains(Project.createDummyProject(identifier))) {
            editProjects(identifier, attribute, newValue);
        }
        if (autoproposals.contains(AutoProposal.createDummyAutoProposal(identifier))) {
            editAutoProposals(identifier, attribute, newValue);
        }
        return true;
    }

    public void editAutoProposals(String identifier, String attribute, List<String> newValue) {
        for (Proposal auto : autoproposals) {
            if (auto.getIdOfProposal().equals(identifier)) {
                if (attribute.equals("title")) {
                    auto.setTitle(newValue.get(0));
                }
                if (attribute.equals("student")) {
                    if (students.contains(Student.createDummyStudent(Long.parseLong(newValue.get(0))))) {
                        auto.setStudent((Student) Student.createDummyStudent(Long.parseLong(newValue.get(0))));
                    }
                }
            }
        }
    }

    public void editProjects(String identifier, String attribute, List<String> newValue) {
        for (MidProposal mid : projects) {
            if (mid.getIdOfProposal().equals(identifier)) {
                if (attribute.equals("title")) {
                    mid.setTitle(newValue.get(0));
                }
                if (attribute.equals("branch")) {
                    mid.setBranch(newValue);
                }
                if (attribute.equals("professor")) {
                    if (professors.contains(Professor.createDummyProfessor(newValue.get(0)))) {
                        ((Project) mid).setProfessor((Professor) Professor.createDummyProfessor(newValue.get(0)));
                    }
                }
                if (attribute.equals("student")) {
                    if (students.contains(Student.createDummyStudent(Long.parseLong(newValue.get(0))))) {
                        mid.setStudent((Student) Student.createDummyStudent(Long.parseLong(newValue.get(0))));
                    }

                }
            }
        }
    }

    public void editInternships(String identifier, String attribute, List<String> newValue) {
        for (MidProposal mid : internships) {
            if (mid.getIdOfProposal().equals(identifier)) {
                if (attribute.equals("title")) {
                    mid.setTitle(newValue.get(0));
                }
                if (attribute.equals("branch")) {
                    mid.setBranch(newValue);
                }
                if (attribute.equals("company")) {
                    ((Internship) mid).setNameOfCompany(newValue.get(0));
                }
                if (attribute.equals("student")) {
                    if (students.contains(Student.createDummyStudent(Long.parseLong(newValue.get(0))))) {
                        mid.setStudent((Student) Student.createDummyStudent(Long.parseLong(newValue.get(0))));
                    }
                }
            }
        }
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

        sb.append(("\nAutoProposals: \n"));
        for (Proposal auto : autoproposals) {
            sb.append(auto.toString()).append("\n");
        }

        sb.append("\nProjects: \n");
        for (MidProposal mid : projects) {
            sb.append(mid.toString()).append("\n");
        }

        sb.append("\nInternShips: \n");
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
            if (((Student) student).getCourseBranch().equals("DA"))
                counterDaStudents++;

            if (((Student) student).getCourseBranch().equals("SI"))
                counterSiStudents++;

            if (((Student) student).getCourseBranch().equals("RAS"))
                counterRasStudents++;
        }

        return (counterDaStudents <= counterDaProposals) && (counterRasStudents <= counterRasProposals)
                && (counterSiStudents <= counterSiProposals);
    }

    public boolean addCandidatureFile(List<List<String>> attributes) {
        Iterator<List<String>> listOfListsIterator = attributes.iterator();

        long id = 0;
        List<String> proposals = new ArrayList<String>();
        Set<Student> studentsAdded = new HashSet<>();

        while (listOfListsIterator.hasNext()) {
            List<String> list = new ArrayList<String>();
            proposals.clear();
            list = (List<String>) listOfListsIterator.next();

            Iterator<String> eachListIterator = list.iterator();

            while (eachListIterator.hasNext()) {
                int flag = 0;
                id = Long.parseLong((String) eachListIterator.next());
                for (Proposal auto : autoproposals) {
                    if (auto.getStudent() == id)
                        flag = 1;
                }
                if (flag == 1) {
                    break;
                }
                System.out.println(id);
                if (!studentsAdded.contains((Student) Student.createDummyStudent(id))) {
                    studentsAdded.add((Student) Student.createDummyStudent(id));
                    while (eachListIterator.hasNext()) {
                        String aux = (String) eachListIterator.next();
                        if (!autoproposals.contains(AutoProposal.createDummyAutoProposal(aux))) {
                            if (internships.contains(Internship.createDummyInternship(aux))) {
                                for (Proposal internship : internships) {
                                    if (internship.getIdOfProposal().equals(aux)) {
                                        if (internship.getStudent() == -1) {
                                            proposals.add(aux);
                                        }
                                    }
                                }
                            } else if (projects.contains(Project.createDummyProject(aux))) {
                                for (Proposal project : projects) {
                                    if (project.getIdOfProposal().equals(aux)) {
                                        if (project.getStudent() == -1) {
                                            proposals.add(aux);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (proposals.size() > 0)
                        if (students.contains(Student.createDummyStudent(id)))
                            candidatures.put(id, new ArrayList<>(proposals));
                } else {
                    if (eachListIterator.hasNext())
                        eachListIterator.next();
                }
            }
        }
        return true;
    }

    public String getCandidatures() {
        StringBuilder sb = new StringBuilder();

        for (Long idStudent : candidatures.keySet()) {
            sb.append("Student with id = ").append(idStudent).append(" has filled a candidature for proposal ");
            for (String idProposal : candidatures.get(idStudent)) {
                sb.append(idProposal).append("; ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean removeCandidatureGivenStudentID(String id, String proposal) {
        if (id == null || proposal == null)
            return false;

        for (Long ids : candidatures.keySet()) {
            if (ids.equals(Long.parseLong(id))) {
                Iterator<String> proposals = candidatures.get(ids).iterator();
                while (proposals.hasNext()) {
                    if (proposals.next().equals(proposal)) {
                        proposals.remove();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean editCandidatures(String id, String proposal) {
        if (id == null || proposal == null)
            return false;

        for (Long ids : candidatures.keySet()) {
            if (ids.equals(Long.parseLong(id))) {
                Iterator<String> proposals = candidatures.get(ids).iterator();
                while (proposals.hasNext()) {
                    if (proposals.next().equals(proposal)) {
                        return false;
                    }
                }
                if (autoproposals.contains(AutoProposal.createDummyAutoProposal(proposal))) {
                    candidatures.get(Long.parseLong(id)).add(proposal);
                    return true;
                } else if (internships.contains(Internship.createDummyInternship(proposal))) {
                    candidatures.get(Long.parseLong(id)).add(proposal);
                    return true;
                } else if (projects.contains(Project.createDummyProject(proposal))) {
                    candidatures.get(Long.parseLong(id)).add(proposal);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeCompleteCandidatureGivenItsID(String id) {
        Long identifier = Long.parseLong(id);
        if (candidatures.containsKey(identifier)) {
            candidatures.remove(identifier);
            return true;
        }
        return false;
    }

    public String getProposalAttributions() {
        StringBuilder sb = new StringBuilder();

        if (autoproposals.size() == 0 && internships.size() == 0 && projects.size() == 0) {
            sb.append("No projects registered");
            return sb.toString();
        }

        sb.append(("AutoProposals: \n\n"));
        for (Proposal auto : autoproposals) {
            if (auto.getStudent() != -1) {
                sb.append(auto).append("\n");
            }
        }

        sb.append("Projects: \n\n");
        for (MidProposal mid : projects) {
            if (mid.getStudent() != -1) {
                sb.append(mid).append("\n");
            }
        }

        sb.append("InternShips: \n\n");
        for (MidProposal mid : internships) {
            if (mid.getStudent() != -1) {
                sb.append(mid.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String listStudentsWithCandidatures() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (candidatures.containsKey(((Student) student).getId())) {
                sb.append(student).append("\n");
            }
        }

        return sb.toString();
    }

    public String listStudentsWithoutCandidatures() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (!candidatures.containsKey(((Student) student).getId())) {
                sb.append(student).append("\n");
            }
        }

        return sb.toString();
    }

    public String listStudentsWithAutoProposals() {
        StringBuilder sb = new StringBuilder();

        for (Proposal auto : autoproposals) {
            if (auto.getStudent() != -1) {
                for (Person student : students) {
                    if (auto.getStudent() == ((Student) student).getId()) {
                        sb.append(student).append("\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    public String listProposalsFilters(List<Integer> filters, ApplicationState state) {
        StringBuilder sb = new StringBuilder();

        for (int filter : filters) {
            if (filter == 1) {
                sb.append("AutoProposals from students:\n");
                for (Proposal auto : autoproposals) {
                    if (auto.getStudent() != -1) {
                        sb.append(auto).append("\n");
                    }
                }
            }
            if (filter == 2) {
                sb.append("Proposals from professors:\n");
                for (MidProposal project : projects) {
                    if (((Project) project).getProfessor() != null) {
                        for (Person professor : professors) {
                            if (professor.getEmail().equals(((Project) project).getProfessor())) {
                                sb.append(project).append("\n");
                            }
                        }
                    }
                }
            }

            if (state == ApplicationState.CANDIDATURE) {
                if (filter == 3) {
                    sb.append("Proposals with candidatures:\n");
                    for (Long ids : candidatures.keySet()) {
                        Iterator<String> proposals = candidatures.get(ids).iterator();
                        while (proposals.hasNext()) {
                            String aux = proposals.next();
                            for (MidProposal project : projects) {
                                if (project.getIdOfProposal().equals(aux)) {
                                    sb.append(project).append("\n");
                                }
                            }
                            for (MidProposal internship : internships) {
                                if (internship.getIdOfProposal().equals(aux)) {
                                    sb.append(internship).append("\n");
                                }
                            }
                        }
                    }
                }
                if (filter == 4) {
                    sb.append("Proposals without candidatures:\n");
                    for (Long ids : candidatures.keySet()) {
                        Iterator<String> proposals = candidatures.get(ids).iterator();
                        while (proposals.hasNext()) {
                            String aux = proposals.next();
                            for (MidProposal project : projects) {
                                if (!project.getIdOfProposal().equals(aux)) {
                                    sb.append(project).append("\n");
                                }
                            }
                            for (MidProposal internship : internships) {
                                if (!internship.getIdOfProposal().equals(aux)) {
                                    sb.append(internship).append("\n");
                                }
                            }
                        }
                    }
                }
            }

            if (state == ApplicationState.PROPOSAL_ATTRIBUTION) {
                if (filter == 3) {
                    sb.append(listAvailableProposals());
                }
                if (filter == 4) {
                    sb.append(listAttributedProposals());
                }
            }
        }

        return sb.toString();
    }

    public Proposal checkAssociationProposals(String identifier) {
        // checks if a given proposal is already associated with a student

        if (internships.contains(Internship.createDummyInternship(identifier))) {
            for (MidProposal internship : internships) {
                if (internship.getIdOfProposal().equals(identifier)) {
                    if (internship.getStudent() == -1) {
                        return internship;
                    }
                }
            }
        }
        if (projects.contains(Project.createDummyProject(identifier))) {
            for (MidProposal project : projects) {
                if (project.getIdOfProposal().equals(identifier)) {
                    if (project.getStudent() == -1) {
                        return project;
                    }
                }
            }
        }
        if (autoproposals.contains(AutoProposal.createDummyAutoProposal(identifier))) {
            for (Proposal autoproposal : autoproposals) {
                if (autoproposal.getIdOfProposal().equals(identifier)) {
                    if (autoproposal.getStudent() == -1) {
                        return autoproposal;
                    }
                }
            }
        }

        return null;
    }

    public boolean associatedAttribution() {
        for (Proposal auto : autoproposals) {
            if (auto.getStudent() != -1) {
                if (!proposalAttributions.containsKey(auto.getIdOfProposal())
                        && !proposalAttributions.containsValue(auto.getStudent())) {
                    proposalAttributions.put(auto.getIdOfProposal(), auto.getStudent());
                }
            }
        }
        for (MidProposal project : projects) {
            if (project.getStudent() != -1) {
                if (!proposalAttributions.containsKey(project.getIdOfProposal())
                        && !proposalAttributions.containsValue(project.getStudent())) {
                    proposalAttributions.put(project.getIdOfProposal(), project.getStudent());
                }
            }
        }
        for (MidProposal internship : internships) {
            if (internship.getStudent() != -1) {
                if (!proposalAttributions.containsKey(internship.getIdOfProposal())
                        && !proposalAttributions.containsValue(internship.getStudent())) {
                    proposalAttributions.put(internship.getIdOfProposal(), internship.getStudent());
                }
            }
        }
        return true;
    }

    public ArrayList<Person> nonAssociateAttribution() {
        ArrayList<Person> studentsProposals = new ArrayList<>();
        double highestGrade = 0;
        int i = 0;

        while (i != candidatures.size()) {
            for (Long id : candidatures.keySet()) {
                for (Person student : students) {
                    if (student.equals(Student.createDummyStudent(id))) {
                        if (studentsProposals.size() == 0 && !proposalAttributions.containsValue(student.getId())) {
                            studentsProposals.add(student);
                            highestGrade = student.getClassification();
                        } else if (student.getClassification() > highestGrade
                                && !proposalAttributions.containsValue(student.getId())) {
                            studentsProposals.clear();
                            studentsProposals.add(student);
                            highestGrade = student.getClassification();
                        } else if (student.getClassification() == highestGrade && !proposalAttributions
                                .containsValue(student.getId())) {
                            studentsProposals.add(student);
                        }

                    }
                }
            }

            if (studentsProposals.size() == 1) {
                for (Map.Entry<Long, List<String>> entry : candidatures.entrySet()) {
                    if (entry.getKey() == studentsProposals.get(0).getId()) {
                        Iterator<String> itr = entry.getValue().iterator();
                        while (itr.hasNext()) {
                            String proposal = itr.next();
                            for (MidProposal internship : internships) {
                                String branches[] = internship.getBranches().split(" ");
                                for (String branch : branches)
                                    for (Person student : students) {
                                        if (student.equals(Student.createDummyStudent(entry.getKey()))) {
                                            if (student.getInternship())
                                                if (branch.equals(student.getCourseBranch()))
                                                    if (!proposalAttributions.containsKey(proposal)
                                                            && !proposalAttributions.containsValue(entry.getKey())) {
                                                        proposalAttributions.put(proposal, entry.getKey());
                                                    }
                                        }
                                    }
                            }
                            for (MidProposal project : projects) {
                                String branches[] = project.getBranches().split(" ");
                                for (String branch : branches)
                                    for (Person student : students) {
                                        if (student.equals(Student.createDummyStudent(entry.getKey()))) {
                                            if (branch.equals(student.getCourseBranch()))
                                                if (!proposalAttributions.containsKey(proposal)
                                                        && !proposalAttributions.containsValue(entry.getKey())) {
                                                    proposalAttributions.put(proposal, entry.getKey());
                                                }
                                        }
                                    }
                            }
                        }
                    }
                }
            } else {
                if (studentsProposals.size() != 0) {
                    return new ArrayList<>(studentsProposals);
                }
            }
            studentsProposals.clear();
            i++;
        }

        fixAttributions();
        return null;
    }

    public void fixAttributions() {
        for (String idProposal : proposalAttributions.keySet()) {
            if (internships.contains(Internship.createDummyInternship(idProposal))) {
                for (Proposal internship : internships) {
                    if (internship.getIdOfProposal().equals(idProposal)) {
                        internship
                                .setStudent((Student) Student.createDummyStudent(proposalAttributions.get(idProposal)));
                    }
                }
            }
            if (projects.contains(Project.createDummyProject(idProposal))) {
                for (Proposal project : projects) {
                    if (project.getIdOfProposal().equals(idProposal)) {
                        project.setStudent((Student) Student.createDummyStudent(proposalAttributions.get(idProposal)));
                    }
                }
            }
            if (autoproposals.contains(AutoProposal.createDummyAutoProposal(idProposal))) {
                for (Proposal auto : autoproposals) {
                    if (auto.getIdOfProposal().equals(idProposal)) {
                        auto.setStudent((Student) Student.createDummyStudent(proposalAttributions.get(idProposal)));
                    }
                }
            }
        }
    }

    public ArrayList<Person> chooseStudentToAssociate(ArrayList<Person> studentsProposals, int index) {
        ArrayList<Person> aux = new ArrayList<>();

        if (studentsProposals != null) {
            for (Map.Entry<Long, List<String>> entry : candidatures.entrySet()) {
                if (entry.getKey() == studentsProposals.get(index).getId()) {
                    Iterator<String> itr = entry.getValue().iterator();
                    while (itr.hasNext()) {
                        String proposal = itr.next();
                        for (MidProposal internship : internships) {
                            String branches[] = internship.getBranches().split(" ");
                            for (String branch : branches)
                                for (Person student : students) {
                                    if (student.equals(Student.createDummyStudent(entry.getKey()))) {
                                        if (student.getInternship())
                                            if (branch.equals(student.getCourseBranch()))
                                                if (!proposalAttributions.containsKey(proposal)
                                                        && !proposalAttributions.containsValue(entry.getKey())) {
                                                    proposalAttributions.put(proposal, entry.getKey());
                                                }
                                    }
                                }
                        }
                        for (MidProposal project : projects) {
                            String branches[] = project.getBranches().split(" ");
                            for (String branch : branches)
                                for (Person student : students) {
                                    if (student.equals(Student.createDummyStudent(entry.getKey()))) {
                                        if (branch.equals(student.getCourseBranch()))
                                            if (!proposalAttributions.containsKey(proposal)
                                                    && !proposalAttributions.containsValue(entry.getKey())) {
                                                proposalAttributions.put(proposal, entry.getKey());
                                            }
                                    }
                                }
                        }
                    }
                }
            }
        }
        if ((aux = nonAssociateAttribution()) != null) {
            fixAttributions();
            return aux;
        }

        fixAttributions();
        return null;
    }

    public String listStudentWithProposalAttributed() {
        StringBuilder sb = new StringBuilder();

        for (String idProposal : proposalAttributions.keySet()) {
            sb.append("Proposal ").append(idProposal).append(" is attributed to student ")
                    .append(proposalAttributions.get(idProposal));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String listStudentWithoutProposalAttributed() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (!proposalAttributions.containsValue(((Student) student).getId())) {
                sb.append(student).append("\n");
            }
        }

        return sb.toString();
    }

    public boolean manualAttribution(String idOdProposal, long idOdStudent) {
        if (proposalAttributions.containsKey(idOdProposal) || proposalAttributions.containsValue(idOdStudent)) {
            return false;
        }

        if (!students.contains(Student.createDummyStudent(idOdStudent))) {
            return false;
        }

        if (projects.contains(Project.createDummyProject(idOdProposal))
                || internships.contains(Internship.createDummyInternship(idOdProposal))
                || autoproposals.contains(AutoProposal.createDummyAutoProposal(idOdProposal))) {
            proposalAttributions.put(idOdProposal, idOdStudent);
            return true;
        }

        return false;
    }

    public boolean manualRemoval(String idOdProposal) {
        if (proposalAttributions.containsKey(idOdProposal)) {
            proposalAttributions.remove(idOdProposal);
            return true;
        }

        return false;
    }

    public boolean isEveryStudentAttributed() {
        for (Long idStudent : candidatures.keySet()) {
            if (!proposalAttributions.containsValue(idStudent)) {
                return false;
            }
        }

        return true;
    }

    public void associatedAdvisor() {
        for (MidProposal project : projects) {
            if (!advisorAttribution.containsValue(List.of(project.getIdOfProposal()))) {
                if (advisorAttribution.containsKey(project.getProfessor())) {
                    advisorAttribution.get(project.getProfessor()).add(project.getIdOfProposal());
                } else {
                    advisorAttribution.put(project.getProfessor(), new ArrayList<>(List.of(project.getIdOfProposal())));
                }
            }
        }
    }

    public boolean manualProfessorAttribution(String idOfProposal, String emailProfessor) {
        if (advisorAttribution.containsKey(emailProfessor) || advisorAttribution.containsValue(List.of(idOfProposal))) {
            return false;
        }

        if (!professors.contains(Professor.createDummyProfessor(emailProfessor))) {
            return false;
        }
        if (projects.contains(Project.createDummyProject(idOfProposal))
                || internships.contains(Internship.createDummyInternship(idOfProposal))
                || autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal))) {
            if (advisorAttribution.containsKey(emailProfessor)) {
                advisorAttribution.get(emailProfessor).add(idOfProposal);
            } else {
                advisorAttribution.put(emailProfessor, List.of(idOfProposal));
            }
            return true;
        }

        return false;
    }

    public boolean manualProfessorRemoval(String emailProfessor) {
        return advisorAttribution.remove(emailProfessor) != null;
    }

    public String listProfessorAttributions() {
        StringBuilder sb = new StringBuilder();

        for (String email : advisorAttribution.keySet()) {
            sb.append("\nProfessor(").append(email).append(") is the advisor for proposal: \n");
            for (String id : advisorAttribution.get(email)) {
                if (proposalAttributions.containsKey(id)) {
                    for (String idOfProposal : proposalAttributions.keySet()) {
                        if (idOfProposal.equals(id)) {
                            if (proposalAttributions.get(idOfProposal) != null) {
                                sb.append(idOfProposal).append(" which is attributted to student ")
                                        .append(proposalAttributions.get(idOfProposal)).append("\n");
                            }
                        }
                    }
                } else {
                    sb.append(id).append(" with no student attributed").append("\n");
                }

            }
        }

        return sb.toString();
    }

    public String listStudentsWithProposalAndProfessorAttributed() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (proposalAttributions.containsValue(student.getId())) {
                for (String idProposal : proposalAttributions.keySet()) {
                    if (proposalAttributions.get(idProposal).equals(student.getId())
                            && advisorAttribution.containsValue(List.of(idProposal))) {
                        for (String email : advisorAttribution.keySet()) {
                            sb.append("Student ").append(student.getId()).append(" is attributed to proposal")
                                    .append(idProposal).append(" with ").append(email).append(" has its advisor\n");
                        }
                    }
                }
            }
        }

        return sb.toString();
    }

    public String listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (proposalAttributions.containsValue(student.getId())) {
                for (String idProposal : proposalAttributions.keySet()) {
                    if (proposalAttributions.get(idProposal).equals(student.getId())
                            && !advisorAttribution.containsValue(List.of(idProposal))) {
                        sb.append("Student ").append(student.getId()).append(" is attributed to proposal").append(" ")
                                .append(idProposal).append(" without an advisor\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    public String getNumberOfAttributionsPerProfessor() {
        StringBuilder sb = new StringBuilder();

        int count = 0;

        for (Person prof : professors) {
            for (String emailOfProf : advisorAttribution.keySet()) {
                if (emailOfProf.equals(prof.getEmail())) {
                    ++count;
                }
            }

            sb.append("Professor ").append(prof.getEmail()).append(" has ").append(count).append(" attributions\n");
            count = 0;
        }

        return sb.toString();
    }

    public String getAverageNumberOfAttributionsForProfessors() {
        StringBuilder sb = new StringBuilder();

        int count = 0;

        for (Person prof : professors) {
            for (String emailOfProf : advisorAttribution.keySet()) {
                if (emailOfProf.equals(prof.getEmail())) {
                    ++count;
                }
            }
        }

        int averageValue = count / advisorAttribution.size();

        sb.append("Average professor attribution ").append(averageValue);

        return sb.toString();
    }

    public String getMinProfessorsAttributions() {
        StringBuilder sb = new StringBuilder();

        String minEmail = null;
        int min = Integer.MAX_VALUE;

        for (String email : advisorAttribution.keySet()) {
            if (advisorAttribution.get(email).size() < min) {
                min = advisorAttribution.get(email).size();
                minEmail = email;
            }
        }

        if (minEmail != null) {
            sb.append("Professor ").append(minEmail).append(" has the lowest attribution = ").append(min);
        } else {
            sb.append("No minimum attributions");
        }

        return sb.toString();
    }

    public String getMaxProfessorsAttributions() {
        StringBuilder sb = new StringBuilder();

        String maxEmail = null;
        int max = Integer.MIN_VALUE;

        for (String email : advisorAttribution.keySet()) {
            if (advisorAttribution.get(email).size() > max) {
                max = advisorAttribution.get(email).size();
                maxEmail = email;
            }
        }

        if (maxEmail != null) {
            sb.append("Professor ").append(maxEmail).append(" has the highest attribution = ").append(max);
        } else {
            sb.append("No minimum attributions");
        }

        return sb.toString();
    }

    public String getSpecificProfessorAttributions(String email) {
        StringBuilder sb = new StringBuilder();

        for (String emailOfProf : advisorAttribution.keySet()) {
            if (emailOfProf.equals(email)) {
                sb.append("Professor ").append(emailOfProf).append(" has ")
                        .append(advisorAttribution.get(emailOfProf).size()).append(" attributions");
                break;
            }
        }

        return sb.toString();
    }

    public String listStudentWithoutProposalAttributedAndWithCandidature() {
        StringBuilder sb = new StringBuilder();

        for (Person student : students) {
            if (!proposalAttributions.containsValue(student.getId()) && candidatures.containsKey(student.getId())) {
                sb.append(student).append("\n");
            }
        }

        return sb.toString();
    }

    public String listAvailableProposals() {
        StringBuilder sb = new StringBuilder();

        sb.append("Available proposals:\n");
        for (Proposal internship : internships) {
            if (!proposalAttributions.containsKey(internship.getIdOfProposal())) {
                sb.append(internship).append("\n");
            }
        }
        for (Proposal project : projects) {
            if (!proposalAttributions.containsKey(project.getIdOfProposal())) {
                sb.append(project).append("\n");
            }
        }
        for (Proposal auto : autoproposals) {
            if (!proposalAttributions.containsKey(auto.getIdOfProposal())) {
                sb.append(auto).append("\n");
            }
        }

        return sb.toString();
    }

    public String listAttributedProposals() {
        StringBuilder sb = new StringBuilder();

        sb.append("Proposals attributed:\n");
        for (Proposal internship : internships) {
            if (proposalAttributions.containsKey(internship.getIdOfProposal())) {
                sb.append(internship).append("\n");
            }
        }
        for (Proposal project : projects) {
            if (proposalAttributions.containsKey(project.getIdOfProposal())) {
                sb.append(project).append("\n");
            }
        }
        for (Proposal auto : autoproposals) {
            if (proposalAttributions.containsKey(auto.getIdOfProposal())) {
                sb.append(auto).append("\n");
            }
        }

        return sb.toString();
    }
}