package pt.isec.pa.apoio_poe.ui.text;

import java.util.ArrayList;
import java.util.List;

import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.ui.util.InputProtection;
import pt.isec.pa.apoio_poe.model.FSManager;

public class CommandLineUI {
    private FSManager manager;

    public CommandLineUI(FSManager manager) {
        this.manager = manager;
    }

    public boolean studentPhase() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Insert student data", "Delete student data",
                "Edit student data", "Consult student data", "Professor Management", "Proposal Management",
                "Candidature Management",
                "Close state", "Save & quit", "Load save");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteStudentData();
            case 3 -> editStudentData();
            case 4 -> consultData();
            case 5 -> professorManagement();
            case 6 -> proposalManagement();
            case 7 -> candidatureManagement();
            case 8 -> closePhase();
            case 9 -> {
                if (!save()) {
                    return false;
                }
            }
            case 10 -> loadSave();
        }

        return true;
    }

    public boolean save() {
        String path = InputProtection.readString("Specify the path to the folder you want to save: ", true);

        if (manager.serializationOfProgram(path)) {
            System.out.println("Successful serialization");
            return false;
        }

        System.out.println("Error occurred");
        return true;
    }

    public void loadSave() {
        String path = InputProtection.readString("Specify the path to the file you want to load: ",
                true);
        try {
            if (manager.loadSave(path)) {
                System.out.println("Successful load");
            } else {
                System.out.println("Could not load the file");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertData() {
        String file = InputProtection.readString("Specify the complete path to the csv file pls: ", false);
        System.out.println(manager.insertData(file));
    }

    public void deleteStudentData() {
        String identifier = InputProtection.readString("Specify the id of the student you want to delete: ", false);
        manager.deleteData(identifier);
    }

    public void editStudentData() {
        String identifier = InputProtection.readString("Specify the id of the student you want to edit: ", true);

        String change = InputProtection.readString(
                "Specify the field you want to change [name, course, course branch, classification, internships]: ",
                true);

        String whatToChange = InputProtection.readString("Specify the new value you want to change to: ", false);

        manager.editDataStudent(identifier, change, whatToChange);
    }

    public void consultData() {
        System.out.println(manager.checkData());
    }

    public void professorManagement() {
        manager.professorManager();
    }

    public void proposalManagement() {
        manager.proposalManager();
    }

    public void closePhase() {
        manager.closeState();
    }

    public boolean studentPhaseLocked() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Consult student data", "Professor Management",
                "Proposal Management", "Candidature Management", "Save & quit");

        switch (input) {
            case 1 -> consultData();
            case 2 -> professorManagement();
            case 3 -> proposalManagement();
            case 4 -> candidatureManagement();
            case 5 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean professorPhase() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Insert professor data", "Delete professor data",
                "Edit professor data", "Consult professor data", "Student Management", "Proposal Management",
                "Candidature Management", "Close state", "Save & quit");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteProfessorData();
            case 3 -> editProfessorData();
            case 4 -> consultData();
            case 5 -> studentManagement();
            case 6 -> proposalManagement();
            case 7 -> candidatureManagement();
            case 8 -> closePhase();
            case 9 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void deleteProfessorData() {
        String identifier = InputProtection.readString("Specify the email of the professor you want to delete: ",
                false);
        manager.deleteData(identifier);
    }

    public void editProfessorData() {
        String identifier = InputProtection.readString("Specify the email of the professor you want to edit: ", false);
        String advisor;

        do {
            advisor = InputProtection
                    .readString("Specify the value you want to give to the field \"advisor\" [true/false]:", false);

        } while (!advisor.equals("true") && !advisor.equals("false"));

        manager.editDataProfessor(identifier, Boolean.parseBoolean(advisor));
    }

    public void studentManagement() {
        manager.studentManager();
    }

    public boolean professorPhaseLocked() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Consult professor data", "Student Management",
                "Proposal Management", "Candidature Management", "Save & quit");

        switch (input) {
            case 1 -> consultData();
            case 2 -> studentManagement();
            case 3 -> proposalManagement();
            case 4 -> candidatureManagement();
            case 5 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean proposalPhase() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Insert proposal data", "Delete proposal data",
                "Edit proposal data", "Consult proposal data", "Student Management", "Professor Management",
                "Candidature Management", "Close state", "Save & quit");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteProposalData();
            case 3 -> editProposalData();
            case 4 -> consultData();
            case 5 -> studentManagement();
            case 6 -> professorManagement();
            case 7 -> candidatureManagement();
            case 8 -> closePhase();
            case 9 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void deleteProposalData() {
        String identifier = InputProtection.readString("Specify the id of the proposal you want to delete: ", true);
        manager.deleteData(identifier);
    }

    public void deleteCandidatureData() {
        switch (InputProtection.chooseOption(null, "Remove single proposal from candidature",
                "Remove an entire candidature")) {
            case 1 -> {
                String id = InputProtection.readString("Specify the id of the student of the candidature: ", true);
                String proposal = InputProtection.readString("Specify the proposal you want to remove: ", true);
                manager.removeProposalFromCandidature(id, proposal);
            }
            case 2 -> {
                String id = InputProtection.readString("Specify the id of the student of the candidature: ", true);
                manager.removeCandidature(id);
            }
        }
    }

    public void editProposalData() {
        String identifier = InputProtection.readString("Specify the id of the proposal you want to edit: ", true);
        int input = manager.checkTypeProposal(identifier);

        switch (input) {
            case 0 -> editInternshipData(identifier);
            case 1 -> editProjectData(identifier);
            case 2 -> editAutoProposalData(identifier);
        }
    }

    public void editInternshipData(String identifier) {
        switch (InputProtection.chooseOption("What attribute do you want to alter?", "title", "branch", "company",
                "student")) {
            case 1 -> {
                String attribute = "title";
                String newValue = InputProtection.readString("New title: ", false);
                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
            case 2 -> {
                String attribute = "branch";
                List<String> newValues = new ArrayList<>();
                ArrayList<String> branchesAvailable = manager.getBranches();

                while (true) {
                    String newValue = InputProtection.readString("New branch " + branchesAvailable + " (write end to stop): ", true);
                    if (newValue.equals("end")) {
                        break;
                    }

                    if (!branchesAvailable.contains(newValue)) {
                        System.out.println("That's not a valid branch!");
                        continue;
                    }

                    newValues.add(newValue);
                }

                manager.editDataProposals(identifier, attribute, newValues);
            }
            case 3 -> {
                String attribute = "company";
                String newValue = InputProtection.readString("Specify the new name for the company: ", false);
                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
            case 4 -> {
                String attribute = "student";
                String newValue;

                while (true) {
                    newValue = InputProtection.readString("Specify the id of the new student: ",
                            false);
                    try {
                        Long.parseLong(newValue);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("That's not a numeric value");
                    }
                }

                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
        }
    }

    public void editProjectData(String identifier) {
        switch (InputProtection.chooseOption("What attribute do you want to alter?", "title", "branch", "company",
                "student")) {
            case 1 -> {
                String attribute = "title";
                String newValue = InputProtection.readString("New title: ", false);
                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
            case 2 -> {
                String attribute = "branch";
                List<String> newValues = new ArrayList<>();
                ArrayList<String> branchesAvailable = manager.getBranches();

                while (true) {
                    String newValue = InputProtection.readString("New branch " + branchesAvailable + " (write end to stop): ", true);
                    if (newValue.equals("end")) {
                        break;
                    }

                    if (!branchesAvailable.contains(newValue)) {
                        System.out.println("That's not a valid branch!");
                        continue;
                    }

                    newValues.add(newValue);
                }

                manager.editDataProposals(identifier, attribute, newValues);
            }
            case 3 -> {
                String attribute = "professor";
                String newValue = InputProtection.readString("Specify the email of the new professor: ", true);
                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
            case 4 -> {
                String attribute = "student";
                String newValue;

                while (true) {
                    newValue = InputProtection.readString("Specify the id of the new student: ",
                            false);
                    try {
                        Long.parseLong(newValue);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("That's not a number");
                    }
                }

                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
        }
    }

    public void editAutoProposalData(String identifier) {
        switch (InputProtection.chooseOption("what attribute do you want to alter?", "title", "student")) {
            case 1 -> {
                String attribute = "title";
                String newValue = InputProtection.readString("Specify the new title: ", false);
                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
            case 2 -> {
                String attribute = "student";
                String newValue;

                while (true) {
                    newValue = InputProtection.readString("Specify the id of the new student: ",
                            false);
                    try {
                        Long.parseLong(newValue);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("That's not a number");
                    }
                }

                manager.editDataProposals(identifier, attribute, List.of(newValue));
            }
        }
    }

    public boolean proposalPhaseLocked() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Consult proposal data", "Student Management",
                "Professor Management", "Candidature Management", "Save & quit");

        switch (input) {
            case 1 -> consultData();
            case 2 -> studentManagement();
            case 3 -> professorManagement();
            case 4 -> candidatureManagement();
            case 5 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void editCandidatureData() {
        String id = InputProtection.readString("Insert the id of the student that applied to the candidature: ", true);
        String proposal = InputProtection.readString("Insert the proposal you want to add to the student: ", true);
        manager.editCandidatures(id, proposal);
    }

    public boolean candidaturePhase() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Insert candidature data", "Delete candidature data",
                "Edit candidature data", "Consult candidature data", "List students", "List proposals", "Close state",
                "Go to previous state", "Proposal Attribution Management", "Save & quit");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteCandidatureData();
            case 3 -> editCandidatureData();
            case 4 -> consultData();
            case 5 -> listStudents();
            case 6 -> listProposals();
            case 7 -> closePhase();
            case 8 -> studentManagement();
            case 9 -> proposalAttributionManagement();
            case 10 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void proposalAttributionManagement() {
        manager.proposalAttributionManager();
    }

    public boolean candidaturePhaseLocked() {
        System.out.println("Current state: " + manager.getState());

        int input = InputProtection.chooseOption(null, "Consult candidature data", "Go to previous state",
                "Proposal Attribution Management", "Save & quit");

        switch (input) {
            case 1 -> consultData();
            case 2 -> studentManagement();
            case 3 -> proposalAttributionManagement();
            case 4 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void candidatureManagement() {
        manager.candidatureManager();
    }

    public void listStudents() {
        if (manager.getState() == ApplicationState.CANDIDATURE
                || manager.getState() == ApplicationState.CANDIDATURE_LOCKED) {
            switch (InputProtection.chooseOption("Pick a listing option: ", "List students with candidatures",
                    "List students without candidatures", "List students with self proposals")) {
                case 1 -> listStudentsWithCandidature();
                case 2 -> listStudentsWithoutCandidature();
                case 3 -> listStudentsWithAutoProposals();
            }

            return;
        }

        if (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION
                || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED) {
            switch (InputProtection.chooseOption("Pick a listing option: ", "List students with candidatures",
                    "List students with self proposals", "List students with proposal attributed",
                    "List students without attributions")) {
                case 1 -> listStudentsWithCandidature();
                case 2 -> listStudentsWithAutoProposals();
                case 3 -> listStudentWithProposalAttributed();
                case 4 -> listStudentWithoutProposalAttributed();
            }

        }

        if (manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION) {
            switch (InputProtection.chooseOption(null, "List students with proposal and professor attributed",
                    "List students with proposal attributed and without professor attributed")) {
                case 1 -> listStudentsWithProposalAndProfessorAttributed();
                case 2 -> listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
            }
        }
    }

    public void listStudentWithoutProposalAttributed() {
        System.out.println(manager.listStudentWithoutProposalAttributed());
    }

    public void listStudentWithProposalAttributed() {
        System.out.println(manager.listStudentWithProposalAttributed());
    }

    public void listStudentsWithCandidature() {
        System.out.println(manager.listStudentsWithCandidatures());
    }

    public void listStudentsWithoutCandidature() {
        System.out.println(manager.listStudentsWithoutCandidatures());
    }

    public void listStudentsWithAutoProposals() {
        System.out.println(manager.listStudentsWithAutoProposals());
    }

    public void listProposals() {
        List<Integer> filters = new ArrayList<>();

        if (manager.getState() == ApplicationState.CANDIDATURE
                || manager.getState() == ApplicationState.CANDIDATURE_LOCKED) {
            System.out.println(
                    "Filters available: \n\t1 - AutoProposals from students \n\t2 - Proposals from professors \n\t3 - Proposals with candidatures \n\t4 - Proposals without candidatures");

            while (true) {
                int filter = InputProtection.readInt("Specify the filters you want [type 5 to stop]: ");

                if (filter == 5 || filters.size() >= 4) {
                    break;
                }

                filters.add(filter);
            }
        }

        if (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION
                || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED) {
            System.out.println(
                    "Filters available: \n\t1 - AutoProposals from students \n\t2 - Proposals from professors \n\t3 - Available proposals \n\t4 - Proposals attributed");

            while (true) {
                int filter = InputProtection.readInt("Specify the filters you want [type 5 to stop]: ");

                if (filter == 5 || filters.size() >= 4) {
                    break;
                }

                filters.add(filter);
            }
        }

        System.out.println(manager.listProposalsFilters(filters));
    }

    public boolean proposalAttributionPhase() {
        System.out.println("Current state: " + manager.getState());

        if (!manager.isLocked(ApplicationPhases.PHASE2)) {
            switch (InputProtection.chooseOption(null, "Associated students attribution", "List students",
                    "List proposals", "Go to previous state", "Save & quit")) {
                case 1 -> associateAttribution();
                case 2 -> listStudents();
                case 3 -> listProposals();
                case 4 -> candidatureManagement();
                case 5 -> {
                    if (!save()) {
                        return false;
                    }
                }
            }
        } else {
            switch (InputProtection.chooseOption(null, "Associated students attribution",
                    "Non associated students attribution", "Manual attribution", "Manual removal", "List students",
                    "List proposals", "Go to previous state", "Close state", "Professor attribution phase",
                    "Save & quit")) {
                case 1 -> associateAttribution();
                case 2 -> nonAssociatedAttribution();
                case 3 -> manualAttributionMenu();
                case 4 -> manualRemovalMenu();
                case 5 -> listStudents();
                case 6 -> listProposals();
                case 7 -> candidatureManagement();
                case 8 -> closePhase();
                case 9 -> professorAttributionManagement();
                case 10 -> {
                    if (!save()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean proposalAttributionPhaseLocked() {
        System.out.println("Current state: " + manager.getState());

        switch (InputProtection.chooseOption(null, "List students",
                "List proposals", "Go to previous state", "Professor attribution phase", "Save & quit")) {
            case 1 -> listStudents();
            case 2 -> listProposals();
            case 3 -> candidatureManagement();
            case 4 -> professorAttributionManagement();
            case 5 -> {
                if (!save()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void associateAttribution() {
        manager.associateAttribution();
    }

    public void chooseStudentToAssociate(int index) {
        manager.chooseStudentToAssociate(index);
    }

    public void nonAssociatedAttribution() {
        manager.nonAssociatedAttribution();
    }

    public void manualAttributionMenu(){
        switch (InputProtection.chooseOption(null, "Insert attribution manually", "Undo", "Redo")){
            case 1 -> manualAttribution();
            case 2 -> {manager.undo();}
            case 3 -> {manager.redo();}
        }
    }

    public void manualAttribution() {
        if (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION) {
            String idOfProposal = InputProtection.readString("Specify the id of the proposal: ", true);
            String idOfStudent;

            while (true) {
                idOfStudent = InputProtection.readString("Specify the id of the student: ", true);

                try {
                    Long.parseLong(idOfStudent);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("That's not a number");
                }

            }

            if (!manager.manualAttribution(idOfProposal, Long.parseLong(idOfStudent))) {
                System.out.println("Could not attribute manually");
            }
        }

        if (manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION) {
            String email = InputProtection.readString("Specify the email of the professor: ", true);
            String idOfProposal = InputProtection.readString("Specify the id of the proposal: ", true);

            if (!manager.manualProfessorAttribution(idOfProposal, email)) {
                System.out.println("Could not attribute");
            }
        }
    }

    public void manualRemovalMenu(){
        switch (InputProtection.chooseOption(null, "Remove attribution manually", "Undo", "Redo")){
            case 1 -> manualRemoval();
            case 2 -> {manager.undo();}
            case 3 -> {manager.redo();}
        }
    }

    public void manualRemoval() {
        if (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION) {
            String idOfProposal = InputProtection.readString("Specify the id of the proposal: ", true);

            if (!manager.manualRemoval(idOfProposal)) {
                System.out.println("Could not remove manually");
            }
        }

        if (manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION) {
            String email = InputProtection.readString("Specify the email of the professor: ", true);

            if (!manager.manualProfessorRemoval(email)) {
                System.out.println("Could not remove");
            }
        }
    }

    public void professorAttributionManagement() {
        manager.professorAttributionManager();
    }

    public boolean professorAttributionPhase() {
        System.out.println("Current state: " + manager.getState());

        switch (InputProtection.chooseOption(null, "Automatic attribution", "Manual attribution",
                "Manual removal", "Edit professor data", "Consult professor data", "List attributions", "List students",
                "List professor data", "Close state", "Go to previous state", "Save & quit")) {
            case 1 -> associateAttribution();
            case 2 -> manualAttributionMenu();
            case 3 -> manualRemovalMenu();
            case 4 -> editProfessorData();
            case 5 -> consultProfessorData();
            case 6 -> listProfessorAttributions();
            case 7 -> listStudents();
            case 8 -> listProfessorData();
            case 9 -> closePhase();
            case 10 -> proposalAttributionManagement();
            case 11 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void listProfessorAttributions() {
        System.out.println(manager.listProfessorAttributions());
    }

    public void consultProfessorData() {
        String email = InputProtection.readString("Specify the email of the professor you want to consult: ", true);
        System.out.println(manager.getProfessorByEmail(email));
    }

    public void listStudentsWithProposalAndProfessorAttributed() {
        System.out.println(manager.listStudentsWithProposalAndProfessorAttributed());
    }

    public void listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        System.out.println(manager.listStudentsWithProposalAttributedAndWithoutProfessorAttributed());
    }

    public void listProfessorData() {
        switch (InputProtection.chooseOption(null, "List average attributions", "List minimum attributions",
                "List maximum attributions", "List specific professor attributions")) {
            case 1 -> listAverageAttributions();
            case 2 -> listMinimumAttributions();
            case 3 -> listMaximumAttribution();
            case 4 -> listSpecificProfessorAttribution();
        }
    }

    public void listAverageAttributions() {
        System.out.println(manager.listAverageAttributions());
    }

    public void listMinimumAttributions() {
        System.out.println(manager.listMinimumAttributions());
    }

    public void listMaximumAttribution() {
        System.out.println(manager.listMaximumAttribution());
    }

    public void listSpecificProfessorAttribution() {
        String email = InputProtection.readString("Specify the email of the professor: ", true);
        System.out.println(manager.listSpecificProfessorAttribution(email));
    }

    public boolean searchPhase() {
        System.out.println("Current state: " + manager.getState());

        switch (InputProtection.chooseOption(null, "List students with attributed proposals",
                "List students without proposals attributed and with candidatures",
                "Available proposals", "Attributed proposals", "List professor data", "Save & quit")) {
            case 1 -> listStudentWithProposalAttributed();
            case 2 -> listStudentWithoutProposalAttributedAndWithCandidature();
            case 3 -> listAvailableProposals();
            case 4 -> listAttributedProposals();
            case 5 -> listProfessorData();
            case 6 -> {
                if (!save()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void listStudentWithoutProposalAttributedAndWithCandidature() {
        System.out.println(manager.listStudentWithoutProposalAttributedAndWithCandidature());
    }

    public void listAvailableProposals() {
        System.out.println(manager.listAvailableProposals());
    }

    public void listAttributedProposals() {
        System.out.println(manager.listAttributedProposals());
    }


    private boolean tiePhase() {
        System.out.println("Current State: " + manager.getState());

        ArrayList<Person> studentsProposals = manager.getTies();
        int option = -1;
        String opt;
        int i = 0;

        System.out.println("\nYou have to choose between this students to get the priority: \n");
        for (Person student : studentsProposals) {
            System.out.println(
                    "\nIndex " + i + " - Name: " + student.getName() + " ID: " + student.getId() + " E-mail: "
                            + student.getEmail()
                            + " Course Branch: " + student.getCourseBranch());
            i++;
        }
        while (option < 0 || option > studentsProposals.size()) {
            opt = InputProtection.readString(
                    "\nPlease introduce the index of the student you want to have priority: ",
                    true);
            option = Integer.parseInt(opt);
        }

        chooseStudentToAssociate(option);

        return true;
    }

    public void start() {
        while (switch (manager.getState()) {
            case STUDENT -> studentPhase();
            case STUDENT_LOCKED -> studentPhaseLocked();
            case PROFESSOR -> professorPhase();
            case PROFESSOR_LOCKED -> professorPhaseLocked();
            case PROPOSAL -> proposalPhase();
            case PROPOSAL_LOCKED -> proposalPhaseLocked();
            case CANDIDATURE -> candidaturePhase();
            case CANDIDATURE_LOCKED -> candidaturePhaseLocked();
            case PROPOSAL_ATTRIBUTION -> proposalAttributionPhase();
            case PROPOSAL_ATTRIBUTION_LOCKED -> proposalAttributionPhaseLocked();
            case TIE -> tiePhase();
            case PROFESSOR_ATTRIBUTION -> professorAttributionPhase();
            case SEARCH -> searchPhase();
        })
            ;
    }
}