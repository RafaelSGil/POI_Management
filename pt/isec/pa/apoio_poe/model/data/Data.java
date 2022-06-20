package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.person.*;
import pt.isec.pa.apoio_poe.model.data.proposals.AutoProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.data.proposals.MidProposal;
import pt.isec.pa.apoio_poe.model.data.proposals.Project;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Class that holds all the data anda data manipulation of the program
 *
 * @author RafaelGil and HugoFerreira
 * @version 1.0.0
 */

public class Data implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Collections to store the values
     */

    /**
     * stores the self proposals the students submit'
     */
    private Set<Proposal> autoproposals;
    /**
     * stores all the students
     */
    private Set<Person> students;
    /**
     * stores all the professors
     */
    private Set<Person> professors;
    /**
     * stores all the internships available
     */
    private Set<MidProposal> internships;
    /**
     * stores all the projects available
     */
    private Set<MidProposal> projects;
    /**
     * stores all the candidatures made
     */
    private Map<Long, List<String>> candidatures;
    /**
     * stores all the phases that have been locked
     * true if locked, false if unlocked
     */
    private Map<ApplicationPhases, Boolean> lockedPhases;
    /**
     * <p></p>stores all the proposals that been attributed to students
     * key = id of proposals, value = id of student
     */
    private Map<String, Long> proposalAttributions;
    /**
     * <p>stores all the attributed to professors
     * key = id of professor, value = List of proposals' id</p>
     */
    private Map<String, List<String>> advisorAttribution;
    /**
     * <p>stores the logger messages</p>>
     */
    private ArrayList<String> log;
    /**
     * <p>stores all the ties that have occurred when attributing a proposal</p>
     *
     */
    private ArrayList<Person> ties;


    /**
     * default and only constructor of this class
     */
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
        this.log = new ArrayList<>();
        this.ties = new ArrayList<>();
        startMap();
    }

    private void startMap() {
        for (ApplicationPhases phase : ApplicationPhases.values()) {
            lockedPhases.put(phase, false);
        }
    }

    /**
     *
     * @param phase enum that indicates the phase of the program
     * @return boolean - whether the phase is locked or not
     */
    public boolean isLocked(ApplicationPhases phase) {
        // checks if given state is already locked

        return lockedPhases.get(phase);
    }

    /**
     *
     * @param phase the phase of the program it wishes to lock
     */
    public void lockPhase(ApplicationPhases phase) {
        // locks the given state by attributing it a true value

        lockedPhases.put(phase, true);
    }

    /**
     *
     * @return ArrayList- array with all the messages of the log, cleans the log
     */
    public ArrayList<String> getLog() {
        ArrayList<String> arr = new ArrayList<>(log);
        if(arr.size() == 0){
            arr.add("No errors");
        }
        log.clear();
        return arr;
    }

    /**
     *
     * @param attributes List of Lists with the line of information read from a file, regarding a student
     * @return boolean - whether the data insertion was successful or not
     */
    public boolean addStudentFile(List<List<String>> attributes) {

        try{
            if(attributes == null){
                log.add("Path incorrect!");
                return false;
            }

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

            int lineCSV = 1;

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

                    if (!students.contains(Student.createDummyStudent(id))){
                        if (courses.contains(course)){
                            if (coursesBranches.contains(courseBranch)){
                                if (classification % 1 != 0){
                                    if (internship == true || !internship){
                                        addStudent(name, email, id, course, courseBranch, classification, internship);
                                    }else{
                                        log.add("Internship eligibility has to be a boolean [line " + lineCSV+ "]");
                                    }
                                }else{
                                    log.add("Classification value is wrong [line " + lineCSV+ "]");
                                }
                            }else{
                                log.add("Branch does not exist [line " + lineCSV+ "]");
                            }
                        }else{
                            log.add("Course does not exist [line " + lineCSV+ "]");
                        }
                    }else{
                        log.add("Student already exists [line " + lineCSV+ "]");
                    }

                }
                ++lineCSV;
            }
        }catch (Exception e){
            log.add("Could not load student data file");
            return false;
        }
        return true;
    }

    /**
     *
     * @param identifier id of the student to change
     * @param change identifies the field of the data to change
     * @param whatToChange new value
     * @return whether the edit was successful or not
     */
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

    public boolean exportData(boolean professor) throws FileNotFoundException {
        try{
            String delimiter = ",";
            String separator = "\n";
            String header = "Student,Candidatures,Proposal,Order";
            String headerProfessor = "Student,Candidatures,Proposal,Order,Professor";
            FileWriter file = null;
            int order = 0;
            String proposalAttributed = "";
            if (professor == false) {
                try {
                    file = new FileWriter(
                            "/home/rafa/dev/GitHub/POI_Management/pt/isec/pa/apoio_poe/files/proposals.csv");
                    file.append(header);
                    file.append(separator);

                    Iterator<Map.Entry<Long, List<String>>> itr = candidatures.entrySet().iterator();
                    Iterator<Map.Entry<Long, List<String>>> auxItr = candidatures.entrySet().iterator();
                    Iterator<Map.Entry<String, Long>> itrAttribuition = proposalAttributions.entrySet().iterator();
                    while (itr.hasNext()) {
                        order = 1;
                        Map.Entry<Long, List<String>> entry = itr.next();
                        file.append(Long.toString(entry.getKey()));
                        file.append(delimiter);
                        Iterator<String> proposals = entry.getValue().iterator();
                        while (proposals.hasNext()) {
                            file.append(proposals.next());
                            file.append(delimiter);
                        }
                        while (itrAttribuition.hasNext()) {
                            Map.Entry<String, Long> entryAttribuition = itrAttribuition.next();
                            if (entry.getKey() == entryAttribuition.getValue()) {
                                file.append(entryAttribuition.getKey());
                                file.append(delimiter);
                                proposalAttributed = entryAttribuition.getKey();
                                break;
                            }
                        }

                        while (auxItr.hasNext()) {
                            Map.Entry<Long, List<String>> entryAux = auxItr.next();
                            proposals = entryAux.getValue().iterator();
                            while (proposals.hasNext()) {
                                if (proposals.next().equals(proposalAttributed)) {
                                    file.append(Integer.toString(order));
                                    break;
                                } else {
                                    order++;
                                }
                            }
                            break;
                        }
                        file.append(separator);
                    }

                    for (Proposal auto : autoproposals) {
                        if (auto.getStudent() != -1 && !candidatures.containsKey(auto.getStudent())) {
                            file.append(Long.toString(auto.getStudent()));
                            file.append(delimiter);
                            file.append(auto.getIdOfProposal());
                            file.append(delimiter);
                            file.append(auto.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));
                            file.append(separator);
                        }
                    }

                    for (MidProposal project : projects) {
                        if (project.getStudent() != -1 && !candidatures.containsKey(project.getStudent())) {
                            file.append(Long.toString(project.getStudent()));
                            file.append(delimiter);
                            file.append(project.getIdOfProposal());
                            file.append(delimiter);
                            file.append(project.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));
                            file.append(separator);
                        }
                    }

                    for (MidProposal internship : internships) {
                        if (internship.getStudent() != -1 && !candidatures.containsKey(internship.getStudent())) {
                            file.append(Long.toString(internship.getStudent()));
                            file.append(delimiter);
                            file.append(internship.getIdOfProposal());
                            file.append(delimiter);
                            file.append(internship.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));
                            file.append(separator);
                        }
                    }
                    file.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    file = new FileWriter(
                            "/home/rafa/dev/GitHub/POI_Management/pt/isec/pa/apoio_poe/files/proposals.csv");
                    file.append(headerProfessor);
                    file.append(separator);

                    Iterator<Map.Entry<Long, List<String>>> itr = candidatures.entrySet().iterator();
                    Iterator<Map.Entry<Long, List<String>>> auxItr = candidatures.entrySet().iterator();
                    Iterator<Map.Entry<String, Long>> itrAttribuition = proposalAttributions.entrySet().iterator();
                    Iterator<Map.Entry<String, List<String>>> advisorItr = advisorAttribution.entrySet().iterator();
                    while (itr.hasNext()) {
                        order = 1;
                        Map.Entry<Long, List<String>> entry = itr.next();
                        file.append(Long.toString(entry.getKey()));
                        file.append(delimiter);
                        Iterator<String> proposals = entry.getValue().iterator();
                        while (proposals.hasNext()) {
                            file.append(proposals.next());
                            file.append(delimiter);
                        }
                        while (itrAttribuition.hasNext()) {
                            Map.Entry<String, Long> entryAttribuition = itrAttribuition.next();
                            if (entry.getKey() == entryAttribuition.getValue()) {
                                file.append(entryAttribuition.getKey());
                                file.append(delimiter);
                                proposalAttributed = entryAttribuition.getKey();
                                break;
                            }
                        }

                        while (auxItr.hasNext()) {
                            Map.Entry<Long, List<String>> entryAux = auxItr.next();
                            proposals = entryAux.getValue().iterator();
                            while (proposals.hasNext()) {
                                if (proposals.next().equals(proposalAttributed)) {
                                    file.append(Integer.toString(order));
                                    break;
                                } else {
                                    order++;
                                }
                            }
                            break;
                        }

                        while (advisorItr.hasNext()) {
                            Map.Entry<String, List<String>> entryAdvisor = advisorItr.next();
                            if (entryAdvisor.getValue().contains(proposalAttributed)) {
                                file.append(delimiter);
                                file.append(entryAdvisor.getKey());
                                break;
                            }
                        }
                        file.append(separator);
                    }
                    advisorItr = advisorAttribution.entrySet().iterator();

                    for (Proposal auto : autoproposals) {
                        if (auto.getStudent() != -1 && !candidatures.containsKey(auto.getStudent())) {
                            file.append(Long.toString(auto.getStudent()));
                            file.append(delimiter);
                            file.append(auto.getIdOfProposal());
                            file.append(delimiter);
                            file.append(auto.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));

                            while (advisorItr.hasNext()) {
                                Map.Entry<String, List<String>> entryAdvisor = advisorItr.next();
                                if (entryAdvisor.getValue().contains(auto.getIdOfProposal())) {
                                    file.append(delimiter);
                                    file.append(entryAdvisor.getKey());
                                    break;
                                }
                            }
                            file.append(separator);
                        }
                    }

                    for (MidProposal project : projects) {
                        if (project.getStudent() != -1 && !candidatures.containsKey(project.getStudent())) {
                            file.append(Long.toString(project.getStudent()));
                            file.append(delimiter);
                            file.append(project.getIdOfProposal());
                            file.append(delimiter);
                            file.append(project.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));

                            while (advisorItr.hasNext()) {
                                Map.Entry<String, List<String>> entryAdvisor = advisorItr.next();
                                if (entryAdvisor.getValue().contains(project.getIdOfProposal())) {
                                    file.append(delimiter);
                                    file.append(entryAdvisor.getKey());
                                    break;
                                }
                            }
                            file.append(separator);
                        }
                    }

                    for (MidProposal internship : internships) {
                        if (internship.getStudent() != -1 && !candidatures.containsKey(internship.getStudent())) {
                            file.append(Long.toString(internship.getStudent()));
                            file.append(delimiter);
                            file.append(internship.getIdOfProposal());
                            file.append(delimiter);
                            file.append(internship.getIdOfProposal());
                            file.append(delimiter);
                            file.append(Integer.toString(1));

                            while (advisorItr.hasNext()) {
                                Map.Entry<String, List<String>> entryAdvisor = advisorItr.next();
                                if (entryAdvisor.getValue().contains(internship.getIdOfProposal())) {
                                    file.append(delimiter);
                                    file.append(entryAdvisor.getKey());
                                    break;
                                }
                            }
                            file.append(separator);
                        }
                    }
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            log.add("Could not export data");
            return false;
        }
        return true;
    }

    /**
     * <p>Receives all the data, creates and inserts a new student in the collection</p>
     * @param name name of the student
     * @param email email of the student
     * @param id id of the student
     * @param course course of the student
     * @param courseBranch branch of the student
     * @param classification classification of the student
     * @param internship internship accessibility
     */
    public void addStudent(String name, String email, long id, String course, String courseBranch,
            double classification, boolean internship) {

        if (name == null || email == null || course == null || courseBranch == null)
            return;

        if (students.contains(Student.createDummyStudent(id)))
            return;

        students.add((Student.createStudent(name, email, id, course, courseBranch, classification, internship)));
    }

    /**
     *
     * @param attributes List of Lists with the line of information read from a file, regarding a professor
     * @return whether the data insertion was successful or not
     */
    public boolean addProfessorFile(List<List<String>> attributes) {
        try{
            if(attributes == null){
                log.add("Path incorrect!");
                return false;
            }

            Iterator<List<String>> listsOfListsIterator = attributes.iterator();

            String name;
            String email;
            int lineCSV = 1;

            while (listsOfListsIterator.hasNext()) {
                List<String> list = new ArrayList<String>();
                list = (List<String>) listsOfListsIterator.next();

                Iterator<String> eachListIterator = list.iterator();

                while (eachListIterator.hasNext()) {
                    name = (String) eachListIterator.next();
                    email = (String) eachListIterator.next();

                    if (!professors.contains(Professor.createDummyProfessor(email))){
                        addProfessor(name, email);
                    }else{
                        log.add("Professor already exists [line " + lineCSV + "]");
                    }
                }
                ++lineCSV;
            }
        }catch (Exception e){
            log.add("Could not load professor data file");
            return false;
        }

        return true;
    }

    /**
     *
     * @param email email of the professor to change
     * @param name new name
     * @return whether the edit was successful or not
     */
    public boolean editProfessor(String email, String name  ) {
        for (Person professor : professors) {
            if (professor.equals(Professor.createDummyProfessor(email))) {
                professor.setName(name);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Receives the data, creates and inserts a new professor in the collection</p>
     * @param name name of the professor to insert
     * @param email email of the professor to insert
     */

    public void addProfessor(String name, String email) {

        if (name == null || email == null)
            return;

        if (professors.contains(Professor.createDummyProfessor(email)))
            return;

        professors.add(Professor.createProfessor(name, email));
    }

    /**
     *
     * @param attributes List of Lists with the line of information read from a file, regarding a proposal
     * @return whether the data insertion was successful or not
     */

    public boolean addProposalFile(List<List<String>> attributes) {
        try{
            if(attributes == null){
                log.add("Path incorrect!");
                return false;
            }

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
            int lineCSV = 1;

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
                                }else{
                                    log.add("Student doesn't exist [line " + lineCSV + "]");
                                }
                            } else {
                                addInternshipWithoutStudent(idOfProposal, title, branch, nameOfCompany);
                            }
                        }else{
                            log.add("Course branch does not exist [line " + lineCSV + "]");
                        }
                    }else{
                        log.add("Proposal already exists [line " + lineCSV + "]");
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
                                    }else{
                                        log.add("Student does not exist [line " + lineCSV + "]");
                                    }
                                } else {
                                    addProjectWithoutStudent(idOfProposal, title, branch, (Professor) professor);
                                }
                            }else{
                                log.add("Professor does not exist [line " + lineCSV + "]");
                            }
                        }else{
                            log.add("Course branch does not exist [line " + lineCSV + "]");
                        }
                    }else{
                        log.add("Proposal already exists [line " + lineCSV + "]");
                    }
                }

                else if (list.get(0).equals("T3")) { // Auto proposal
                    idOfProposal = (String) eachListIterator.next();
                    title = (String) eachListIterator.next();
                    student = (Student) Student.createDummyStudent(Long.parseLong((String) eachListIterator.next()));
                    if (!projects.contains(Project.createDummyProject(idOfProposal))
                            && !internships.contains(Internship.createDummyInternship(idOfProposal))
                            && !autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal))){
                        if (!studentsAdded.contains(student)) {
                            addAutoProposal(idOfProposal, title, student);
                            studentsAdded.add(student);
                        }else{
                            log.add("Student does not exist [line " + lineCSV + "]");
                        }
                    }else{
                        log.add("Proposal already exists [line " + lineCSV + "]");
                    }
                }
                ++lineCSV;
            }
        }catch (Exception e){
            log.add("Could not load proposals data file");
            return false;
        }

        return true;
    }

    /**
     * <p>Receives the data, creates an adds an internship to the collection</p>
     * @param idOfProposal id of the internship
     * @param title title of the internship
     * @param student id of the student it is attributed to
     * @param branch List with the branches that may take the internship
     * @param nameOfCompany name of the company where the internship will take place
     */
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

    /**
     * <p>Receives the data, creates an adds an internship to the collection</p>
     * @param idOfProposal id of the internship
     * @param title title of the internship
     * @param branch List with the branches that may take the internship
     * @param nameOfCompany name of the company where the internship will take place
     */
    public void addInternshipWithoutStudent(String idOfProposal, String title, List<String> branch,
            String nameOfCompany) {
        if (idOfProposal == null || title == null || nameOfCompany == null)
            return;

        if (internships.contains(Internship.createDummyInternship(idOfProposal)))
            return;

        internships.add(Internship.createInternship(idOfProposal, title, null, branch,
                nameOfCompany));

    }

    /**
     * <p>Receives the data, creates an adds a project to the collection</p>
     * @param idOfProposal id o the project
     * @param title title of the project
     * @param student id of the student it is attributed to
     * @param branch List with the branches that may take this project
     * @param professor professor that submitted this project
     */
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

    /**
     * <p>Receives the data, creates an adds a project to the collection</p>
     * @param idOfProposal id o the project
     * @param title title of the project
     * @param branch List with the branches that may take this project
     * @param professor professor that submitted this project
     */
    public void addProjectWithoutStudent(String idOfProposal, String title, List<String> branch,
            Professor professor) {
        if (idOfProposal == null || title == null || branch == null)
            return;

        if (projects.contains(Internship.createDummyInternship(idOfProposal)))
            return;

        projects.add(Project.createProject(idOfProposal, title, null, branch, professor));
    }

    /**
     * <p>Receives the data, creates an adds a self-proposal to the collection</p>
     * @param idOfProposal id of the proposal
     * @param title title of the proposal
     * @param student id of the student that submitted the self-proposal
     */
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

    /**
     *
     * @param email email of the professor
     * @return string - information of the professor requested
     */
    public String getProfessorGivenItsEmail(String email) {
        for (Person pf : professors) {
            if (pf.equals(Professor.createDummyProfessor(email))) {
                return pf.toString();
            }
        }
        return "Could not find the given professor";

    }

    /**
     * <p>Removing a student implies removing all of his self-proposals</p>
     * @param id id of the student to remove
     * @return whether the removal was successful or not
     */
    public boolean removeStudentGivenItsId(String id) {
        long idOfStudent;
        try{
            idOfStudent =  Long.parseLong(id);
        }catch (NumberFormatException e){
            return false;
        }
        for(Proposal auto : autoproposals){
            if(auto.getStudent() == idOfStudent){
                removeAutoProposalGivenItsID(auto.getIdOfProposal());
            }
        }
        
        return students.remove(Student.createDummyStudent(idOfStudent));
    }

    /**
     *
     * @param email email of the professor to remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeProfessorGivenItsEmail(String email) {
        return professors.remove(Professor.createDummyProfessor(email));
    }

    /**
     *
     * @param idOfProposal if of the proposal to remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeProposals(String idOfProposal) {
        if (removeInternshipsGivenItsID(idOfProposal))
            return true;

        if (removeProjectsGivenItsID(idOfProposal))
            return true;

        return removeAutoProposalGivenItsID(idOfProposal);
    }

    /**
     *
     * @param idOfProposal if of the internship to remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeInternshipsGivenItsID(String idOfProposal) {
        if (idOfProposal == null) {
            return false;
        }

        if (!internships.contains(Internship.createDummyInternship(idOfProposal)))
            return false;

        return internships.remove(Internship.createDummyInternship(idOfProposal));
    }

    /**
     *
     * @param idOfProposal if of the self-proposal to remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeAutoProposalGivenItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!autoproposals.contains(AutoProposal.createDummyAutoProposal(idOfProposal)))
            return false;

        return autoproposals.remove(AutoProposal.createDummyAutoProposal(idOfProposal));
    }

    /**
     *
     * @param idOfProposal if of the project to remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeProjectsGivenItsID(String idOfProposal) {
        if (idOfProposal == null)
            return false;

        if (!projects.contains(Project.createDummyProject(idOfProposal)))
            return false;

        return projects.remove(Project.createDummyProject(idOfProposal));
    }

    /**
     * <p>Indicates what is the type of the given proposal</p>
     * @param identifier id of the proposal
     * @return -1 -> not found; 0 -> internship; 1 -> project; 3 -> self-proposal
     */
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

    /**
     *
     * @param identifier id of the proposal to edit
     * @param attribute parameter to alter
     * @param newValue List with the new values
     * @return boolean - whether the edit was successful or not
     */
    public boolean editProposals(String identifier, String attribute, List<String> newValue) {
        if (internships.contains(Internship.createDummyInternship(identifier))) {
            return editInternships(identifier, attribute, newValue);
        }
        if (projects.contains(Project.createDummyProject(identifier))) {
            return editProjects(identifier, attribute, newValue);
        }
        if (autoproposals.contains(AutoProposal.createDummyAutoProposal(identifier))) {
            return editAutoProposals(identifier, attribute, newValue);
        }
        return true;
    }

    /**
     *
     * @param identifier id of the self-proposal to alter
     * @param attribute attribute to alter
     * @param newValue List of new values
     * @return whether the edit was successful or not
     */
    public boolean editAutoProposals(String identifier, String attribute, List<String> newValue) {
        try {
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
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *
     * @param identifier id of the project to alter
     * @param attribute attribute to alter
     * @param newValue List of new values
     * @return whether the edit was successful or not
     */
    public boolean editProjects(String identifier, String attribute, List<String> newValue) {
        try {
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
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *
     * @param identifier id of the internship to alter
     * @param attribute attribute to alter
     * @param newValue List of new values
     * @return whether the edit was successful or not
     */
    public boolean editInternships(String identifier, String attribute, List<String> newValue) {
        try{
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
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *
     * @return ArrayList- list with the data of every student registered
     */
    public ArrayList<String> getAllStudents() {
        ArrayList<String> arr = new ArrayList<>();

        if (students.size() == 0) {
            arr.add("No students registered");
            return arr;
        }

        for (Person student : students) {
            arr.add(student.toString());
        }

        return arr;
    }

    /**
     *
     * @return ArrayList - list with the data of every professor registered
     */
    public ArrayList<String> getAllProfessors() {
        ArrayList<String> arr = new ArrayList<>();

        if (professors.size() == 0) {
            arr.add("No professors registered");
            return arr;
        }

        for (Person professor : professors) {
            arr.add(professor.toString());
        }
        return arr;
    }

    /**
     *
     * @return ArrayList - list with the data of every proposal registered
     */
    public ArrayList<String> getAllProjects() {
        ArrayList<String> arr = new ArrayList<>();

        if (autoproposals.size() == 0 && internships.size() == 0 && projects.size() == 0) {
            arr.add("No projects registered");
            return arr;
        }

        arr.add(("\nAutoProposals: \n"));
        for (Proposal auto : autoproposals) {
            arr.add(auto.toString());
        }

        arr.add("\nProjects: \n");
        for (MidProposal mid : projects) {
            arr.add(mid.toString());
        }

        arr.add("\nInternShips: \n");
        for (MidProposal mid : internships) {
            arr.add(mid.toString());
        }
        return arr;
    }

    /**
     *
     * @return whether the Phase1 was locked successfully or not
     */
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

    /**
     *
     * @param attributes List of Lists with the line of information read from a file, regarding a candidature
     * @return whether the data insertion was successful or not
     */
    public boolean addCandidatureFile(List<List<String>> attributes) {
        if(attributes == null){
            log.add("Path incorrect!");
            return false;
        }

        Iterator<List<String>> listOfListsIterator = attributes.iterator();

        long id = 0;
        List<String> proposals = new ArrayList<String>();
        Set<Student> studentsAdded = new HashSet<>();

        int lineCSV = 1;

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
                                        }else{
                                            log.add("Internship " + aux + " already has a student attributed [line " + lineCSV + "]");
                                        }
                                    }else{
                                        log.add("Internship " + aux + " does not exist [line " + lineCSV + "]");
                                    }
                                }
                            } else if (projects.contains(Project.createDummyProject(aux))) {
                                for (Proposal project : projects) {
                                    if (project.getIdOfProposal().equals(aux)) {
                                        if (project.getStudent() == -1) {
                                            proposals.add(aux);
                                        }else{
                                            log.add("Project " + aux + " already has a student attributed [line " + lineCSV + "]");
                                        }
                                    }else{
                                        log.add("Project " + aux + " does not exist [line " + lineCSV + "]");
                                    }
                                }
                            }
                        }else{
                            log.add("Student can't fill a candidature for a autoproposal [line " + lineCSV + "]");
                        }
                    }
                    if (proposals.size() > 0)
                        if (students.contains(Student.createDummyStudent(id)))
                            candidatures.put(id, new ArrayList<>(proposals));
                } else {
                    log.add("Student is doubled [line " + lineCSV + "]");
                    if (eachListIterator.hasNext())
                        eachListIterator.next();
                }
            }

            ++lineCSV;
        }
        return true;
    }

    /**
     *
     * @return whether the Phase1 was locked successfully or not
     */
    public ArrayList<String> getCandidatures() {
        ArrayList<String> arr = new ArrayList<>();

        for (Long idStudent : candidatures.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Student with id = ").append(idStudent).append(" has filled a candidature for proposal ");
            for (String idProposal : candidatures.get(idStudent)) {
                sb.append(idProposal).append("; ");
            }
            arr.add(sb.toString());
        }

        return arr;
    }

    /**
     *
     * @param id id of the candidature to remove from
     * @param proposal  id of the proposal to remove from candidature
     * @return whether the removal was successful or not
     */
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

    /**
     *
     * @param id id of the candidature to edit
     * @param proposal if of the proposal to add
     * @return boolean - whether the edit was successful or not
     */
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

    /**
     *
     * @param id id of the proposal to completely remove
     * @return boolean - whether the removal was successful or not
     */
    public boolean removeCompleteCandidatureGivenItsID(String id) {
        Long identifier = Long.parseLong(id);
        if (candidatures.containsKey(identifier)) {
            candidatures.remove(identifier);
            return true;
        }
        return false;
    }

    /**
     *
     * @return ArrayList - List with all the proposals attributed
     */
    public ArrayList<String> getProposalAttributions() {
        ArrayList<String> arr = new ArrayList<>();

        if (autoproposals.size() == 0 && internships.size() == 0 && projects.size() == 0) {
            arr.add("No projects registered");
            return arr;
        }

        arr.add(("AutoProposals:"));
        for (Proposal auto : autoproposals) {
            if (auto.getStudent() != -1) {
                arr.add(auto.toString());
            }
        }

        arr.add("Projects:");
        for (MidProposal mid : projects) {
            if (mid.getStudent() != -1) {
                arr.add(mid.toString());
            }
        }

        arr.add("InternShips:");
        for (MidProposal mid : internships) {
            if (mid.getStudent() != -1) {
                arr.add(mid.toString());
            }
        }
        return arr;
    }

    /**
     *
     * @return ArrayList - List with all the students that submitted a candidature
     */
    public ArrayList<String> listStudentsWithCandidatures() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (candidatures.containsKey(((Student) student).getId())) {
                arr.add(student.toString());
            }
        }
        return arr;
    }


    /**
     *
     * @return ArrayList - List with all the students that didn't submitted a candidature
     */
    public ArrayList<String> listStudentsWithoutCandidatures() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (!candidatures.containsKey(((Student) student).getId())) {
                arr.add(student.toString());
            }
        }

        return arr;
    }

    /**
     *
     * @return ArrayList - List with all the students that  submitted a self-proposal
     */
    public ArrayList<String> listStudentsWithAutoProposals() {
        ArrayList<String> arr = new ArrayList<>();

        for (Proposal auto : autoproposals) {
            if (auto.getStudent() != -1) {
                for (Person student : students) {
                    if (auto.getStudent() == ((Student) student).getId()) {
                        arr.add(student.toString());
                    }
                }
            }
        }

        return arr;
    }

    /**
     *
     * @param strs list with all the filters selected
     * @param state enum the indicates the state in which the application is in
     * @return ArrayList - List with all the information solicited by the filters
     */
    public ArrayList<String> listProposalsFilters(List<String> strs, ApplicationState state) {
        ArrayList<String> arr = new ArrayList<>();

        ArrayList<Integer> filters = new ArrayList<>();

        for (String s : strs){
            try{
                filters.add(Integer.parseInt(s));
            }catch (NumberFormatException e){
                return arr;
            }
        }

        for (int filter : filters) {
            if (filter == 1) {
                arr.add("AutoProposals from students:\n");
                for (Proposal auto : autoproposals) {
                    if (auto.getStudent() != -1) {
                        arr.add(auto.toString());
                    }
                }
            }
            if (filter == 2) {
                arr.add("Proposals from professors:\n");
                for (MidProposal project : projects) {
                    if (((Project) project).getProfessor() != null) {
                        for (Person professor : professors) {
                            if (professor.getEmail().equals(((Project) project).getProfessor())) {
                                arr.add(professor.toString());
                            }
                        }
                    }
                }
            }

            if (state == ApplicationState.CANDIDATURE) {
                if (filter == 3) {
                    arr.add("Proposals with candidatures:\n");
                    for (Long ids : candidatures.keySet()) {
                        Iterator<String> proposals = candidatures.get(ids).iterator();
                        while (proposals.hasNext()) {
                            String aux = proposals.next();
                            for (MidProposal project : projects) {
                                if (project.getIdOfProposal().equals(aux)) {
                                    arr.add(project.toString());
                                }
                            }
                            for (MidProposal internship : internships) {
                                if (internship.getIdOfProposal().equals(aux)) {
                                    arr.add(internship.toString());
                                }
                            }
                        }
                    }
                }
                if (filter == 4) {
                    arr.add("Proposals without candidatures:\n");
                    for (Long ids : candidatures.keySet()) {
                        Iterator<String> proposals = candidatures.get(ids).iterator();
                        while (proposals.hasNext()) {
                            String aux = proposals.next();
                            for (MidProposal project : projects) {
                                if (!project.getIdOfProposal().equals(aux)) {
                                    arr.add(project.toString());
                                }
                            }
                            for (MidProposal internship : internships) {
                                if (!internship.getIdOfProposal().equals(aux)) {
                                    arr.add(internship.toString());
                                }
                            }
                        }
                    }
                }
            }

            if (state == ApplicationState.PROPOSAL_ATTRIBUTION) {
                if (filter == 3) {
                    arr.addAll(listAvailableProposals());
                }
                if (filter == 4) {
                    arr.addAll(listAttributedProposals());

                }
            }
        }

        return arr;
    }

    /**
     *
     * @param identifier
     * @return
     */
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

    /**
     *
     * @return boolean - whether it managed to associate all the students that were pre-associated
     */
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

    /**
     *
     * @return boolean - whether it managed to automatically associate the students left with no proposal association
     */
    public boolean nonAssociateAttribution() {
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
                    ties.addAll(studentsProposals);
                    return false;
                }
            }
            studentsProposals.clear();
            i++;
        }
        fixAttributions();
        return true;
    }

    /**
     *
     */
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

    /**
     * <p>When there is a tie between two students, chooses which one to attribute first</p>
     * @param indexStudent index of the desired student in the collection that stores the ties
     * @return boolean - whether it managed to associate the student or not
     */
    public boolean chooseStudentToAssociate(String indexStudent) {
        int index;

        try{
            index = Integer.parseInt(indexStudent);
        }catch (NumberFormatException e){
            return false;
        }

        if (ties != null) {
            for (Map.Entry<Long, List<String>> entry : candidatures.entrySet()) {
                if (entry.getKey() == ties.get(index).getId()) {
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

        assert ties != null;
        ties.clear();
        fixAttributions();
        return true;
    }

    /**
     *
     * @return ArrayList - List with all the ties;
     */
    public ArrayList<Person> getTies(){
        return new ArrayList<>(ties);
    }

    /**
     *
     * @return ArrayList- List with all the students with proposals attributed
     */
    public ArrayList<String> listStudentWithProposalAttributed() {
        ArrayList<String> arr = new ArrayList<>();

        for (String idProposal : proposalAttributions.keySet()) {
            arr.add("Proposal " + idProposal + " is attributed to student" + proposalAttributions.get(idProposal));
        }

        return arr;
    }

    /**
     *
     * @return ArrayList - List with all the students without proposals attributed
     */
    public ArrayList<String> listStudentWithoutProposalAttributed() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (!proposalAttributions.containsValue(((Student) student).getId())) {
                arr.add(student.toString());
            }
        }

        return arr;
    }

    /**
     *
     * @param idOdProposal id of the proposal to associate
     * @param idOfStudent id of the student to associate
     * @return boolean - whether it managed to attribute or not
     */
    public boolean manualAttribution(String idOdProposal, String idOfStudent) {
        long idOdStudent;
        try{
            idOdStudent = Long.parseLong(idOfStudent);
        }catch (NumberFormatException e){
            return false;
        }

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

    /**
     *
     * @param idOdProposal id of the proposal to remove the attribution
     * @return boolean - whether it managed to remove or not
     */
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

    /**
     * <p>Automatically associate professors that were pre-associated</p>
     */
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

    /**
     *
     * @param idOfProposal id of the proposal to associate
     * @param emailProfessor email of the professor to associate
     * @return boolean - whether it managed to associate or not
     */
    public boolean manualProfessorAttribution(String idOfProposal, String emailProfessor) {
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

    /**
     *
     * @param emailProfessor email of the professor to remove from
     * @param idOfProposal id of the proposal to remove
     * @return boolean - whether it managed to remove the proposal from the professor
     */
    public boolean manualProfessorRemoval(String emailProfessor, String idOfProposal) {
        if(advisorAttribution.containsKey(emailProfessor)){
            if(advisorAttribution.get(emailProfessor).size() > 1){
                for(Map.Entry<String, List<String>> attributions : advisorAttribution.entrySet()){
                    attributions.getValue().removeIf(a -> a.equals(idOfProposal));
                }
                return true;
            }

            return advisorAttribution.remove(emailProfessor) != null;
        }

        return false;
    }


    /**
     *
     * @return Arraylist - List with all the professors attributions
     */
    public ArrayList<String> listProfessorAttributions() {
        ArrayList<String> arr = new ArrayList<>();

        for (String email : advisorAttribution.keySet()) {
            arr.add("Professor(" + email + ") is the advisor for proposal: ");
            for (String id : advisorAttribution.get(email)) {
                if (proposalAttributions.containsKey(id)) {
                    for (String idOfProposal : proposalAttributions.keySet()) {
                        if (idOfProposal.equals(id)) {
                            if (proposalAttributions.get(idOfProposal) != null) {
                                arr.add(idOfProposal + " which is attributted to student " + proposalAttributions.get(idOfProposal));
                            }
                        }
                    }
                } else {
                    arr.add(id + "with no student attributed");
                }

            }
        }

        return arr;
    }

    /**
     *
     * @return Arraylist - List with all the with proposals and professors attributed
     */
    public ArrayList<String> listStudentsWithProposalAndProfessorAttributed() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (proposalAttributions.containsValue(student.getId())) {
                for (String idProposal : proposalAttributions.keySet()) {
                    if (proposalAttributions.get(idProposal).equals(student.getId())
                            && advisorAttribution.containsValue(List.of(idProposal))) {
                        for (String email : advisorAttribution.keySet()) {
                            arr.add("Student " + student.getId() + " is attributed to proposal " + idProposal + " with " + email + " has its advisor");
                        }
                    }
                }
            }
        }

        return arr;
    }

    /**
     *
     * @return Arraylist - List with all the with proposals attributed but without professors attributed
     */
    public ArrayList<String> listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (proposalAttributions.containsValue(student.getId())) {
                for (String idProposal : proposalAttributions.keySet()) {
                    if (proposalAttributions.get(idProposal).equals(student.getId())
                            && !advisorAttribution.containsValue(List.of(idProposal))) {
                        arr.add("Student " + student.getId() + " is attributed to proposal " + idProposal + " without advisor");
                    }
                }
            }
        }

        return arr;
    }


    /**
     *
     * @return String - average number of professor attributions
     */
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

        try{
            int averageValue = count / advisorAttribution.size();

            sb.append("Average professor attribution ").append(averageValue);
        }catch (Exception e){
            sb.append("No professor attribution");
        }

        return sb.toString();
    }

    /**
     *
     * @return String - minimum attributions from all professors
     */
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


    /**
     *
     * @return String - maximum attributions from all professors
     */
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

    /**
     *
     * @param email email of the desired professor
     * @return String - attributions from the desired professor
     */
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

    /**
     *
     * @return ArrayList - List with all the students without proposals but with candidatures
     */
    public ArrayList<String> listStudentWithoutProposalAttributedAndWithCandidature() {
        ArrayList<String> arr = new ArrayList<>();

        for (Person student : students) {
            if (!proposalAttributions.containsValue(student.getId()) && candidatures.containsKey(student.getId())) {
                arr.add(student.toString());
            }
        }

        return arr;
    }

    /**
     *
     * @return ArrayList - List with all available proposals
     */
    public ArrayList<String> listAvailableProposals() {
        ArrayList<String> arr = new ArrayList<>();

        arr.add("Available proposals:");
        for (Proposal internship : internships) {
            if (!proposalAttributions.containsKey(internship.getIdOfProposal())) {
                arr.add(internship.toString());
            }
        }
        for (Proposal project : projects) {
            if (!proposalAttributions.containsKey(project.getIdOfProposal())) {
                arr.add(project.toString());
            }
        }
        for (Proposal auto : autoproposals) {
            if (!proposalAttributions.containsKey(auto.getIdOfProposal())) {
                arr.add(auto.toString());
            }
        }

        return arr;
    }

    /**
     *
     * @return List with all attributed proposals
     */
    public ArrayList<String> listAttributedProposals() {
        ArrayList<String> arr = new ArrayList<>();

        arr.add("Proposals attributed:");
        for (Proposal internship : internships) {
            if (proposalAttributions.containsKey(internship.getIdOfProposal())) {
                arr.add(internship.toString());
            }
        }
        for (Proposal project : projects) {
            if (proposalAttributions.containsKey(project.getIdOfProposal())) {
                arr.add(project.toString());
            }
        }
        for (Proposal auto : autoproposals) {
            if (proposalAttributions.containsKey(auto.getIdOfProposal())) {
                arr.add(auto.toString());
            }
        }

        return arr;
    }

    /**
     *
      * @return ArrayList - List with all the branches that have students
     */
    public ArrayList<String> getBranches(){
        ArrayList<String> branches = new ArrayList<>();

        for(Person student : students){
            if(!branches.contains(student.getCourseBranch())){
                branches.add(student.getCourseBranch());
            }
        }

        return new ArrayList<>(branches);
    }

    /**
     *
     * @param map map to order
     * @return HashMap - ordered map from highest to smallest value
     */
    public HashMap<String, Integer> getOrderedHashMap(HashMap<String, Integer> map){
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

        map.clear();

        list.sort(Map.Entry.comparingByValue());

        for(Map.Entry<String, Integer> m : list){
            map.put(m.getKey(), m.getValue());
        }

        return map;
    }

    /**
     *
     * @return HashMap - ordered map from highest to smallest value
     */
    public HashMap<String, Integer> getTopCompanies(){
        HashMap<String, Integer> map = new HashMap<>();

        for(MidProposal internship : internships){
            if(map.containsKey(((Internship)internship).getNameOfCompany())){
                int i = map.get(((Internship)internship).getNameOfCompany());
                map.put(((Internship)internship).getNameOfCompany(), ++i);
                continue;
            }

            map.put(((Internship)internship).getNameOfCompany(), 1);
        }

        return getOrderedHashMap(map);
    }

    /**
     *
     * @return HashMap - ordered map from highest to smallest value
     */
    public HashMap<String, Integer> getTopAdvisors(){
        HashMap<String, Integer> map = new HashMap<>();

        for(Map.Entry<String, List<String>> m : advisorAttribution.entrySet()){
            map.put(m.getKey(), m.getValue().size());
        }

        return getOrderedHashMap(map);
    }

    /**
     *
     * @return HashMap - ordered map from highest to smallest value
     */
    public HashMap<String, Integer> getNumberProposalsPerBranches(){
        HashMap<String, Integer> map = new HashMap<>();
        Set<MidProposal> midProposals = new HashSet<>();

        midProposals.addAll(internships);
        midProposals.addAll(projects);


        for(MidProposal mid : midProposals){
            String[] str = mid.getBranches().split(" ");
            List<String> list = new ArrayList<>(List.of(str));
            for(String s : list){
                if(map.containsKey(s)){
                    int i = map.get(s);
                    map.put(s, ++i);
                    continue;
                }

                map.put(s, 1);
            }
        }

        return getOrderedHashMap(map);
    }

    /**
     *
     * @return HashMap - ordered map from highest to smallest value
     */
    public HashMap<String, Integer> getNumberOfProposalsAttrAndNotAttrib(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Attributed", 0);
        map.put("Not Attributed", 0);

        for(Proposal internship : internships){
            if(proposalAttributions.containsKey(internship.getIdOfProposal())){
                int i = map.get("Attributed");
                map.put("Attributed", ++i);
                continue;
            }
            int i = map.get("Not Attributed");
            map.put("Not Attributed", ++i);
        }

        for(Proposal project : projects){
            if(proposalAttributions.containsKey(project.getIdOfProposal())){
                int i = map.get("Attributed");
                map.put("Attributed", ++i);
                continue;
            }
            int i = map.get("Not Attributed");
            map.put("Not Attributed", ++i);
        }

        for(Proposal auto : autoproposals){
            if(proposalAttributions.containsKey(auto.getIdOfProposal())){
                int i = map.get("Attributed");
                map.put("Attributed", ++i);
                continue;
            }
            int i = map.get("Not Attributed");
            map.put("Not Attributed", ++i);
        }

        return getOrderedHashMap(map);
    }
}