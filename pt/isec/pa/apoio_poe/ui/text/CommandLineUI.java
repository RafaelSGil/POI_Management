package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.util.InputProtection;

public class CommandLineUI {
    private ApplicationContext context;

    public CommandLineUI(ApplicationContext context) {
        this.context = context;
    }

    public boolean studentPhase() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Insert student data", "Delete student data",
                "Edit student data", "Consult student data", "Professor Management", "Proposal Management",
                "Close state");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteStudentData();
            case 3 -> editStudentData();
            case 4 -> consultData();
            case 5 -> professorManagement();
            case 6 -> proposalManagement();
            case 7 -> closePhase();
        }

        return true;
    }

    public void insertData() {
        String file = InputProtection.readString("Specify the complete path to the csv file pls: ", false);
        context.insertData(file);
    }

    public void deleteStudentData() {
        String identifier = InputProtection.readString("Specify the id of the student you want to delete: ", false);
        context.deleteData(identifier);
    }

    public void editStudentData() {
        String identifier = InputProtection.readString("Specify the id of the student you want to edit: ", false);
        context.editData(identifier);
    }

    public void consultData() {
        System.out.println(context.checkData());
    }

    public void professorManagement() {
        context.professorManager();
    }

    public void proposalManagement() {
        context.proposalManager();
    }

    public void closePhase() {
        context.closeState();
    }

    public boolean studentPhaseLocked() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Consult student data", "Professor Management",
                "Proposal Management");

        switch (input) {
            case 1 -> consultData();
            case 2 -> professorManagement();
            case 3 -> proposalManagement();
        }

        return true;
    }

    public boolean professorPhase() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Insert professor data", "Delete professor data",
                "Edit professor data", "Consult professor data", "Student Management", "Proposal Management",
                "Close state");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteProfessorData();
            case 3 -> editProfessorData();
            case 4 -> consultData();
            case 5 -> studentManagement();
            case 6 -> proposalManagement();
            case 7 -> closePhase();
        }

        return true;
    }

    public void deleteProfessorData() {
        String identifier = InputProtection.readString("Specify the email of the professor you want to delete: ",
                false);
        context.deleteData(identifier);
    }

    public void editProfessorData() {
        String identifier = InputProtection.readString("Specify the email of the professor you want to edit: ", false);
        context.editData(identifier);
    }

    public void studentManagement() {
        context.studentManager();
    }

    public boolean professorPhaseLocked() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Consult professor data", "Student Management",
                "Proposal Management");

        switch (input) {
            case 1 -> consultData();
            case 2 -> studentManagement();
            case 3 -> proposalManagement();
        }

        return true;
    }

    public boolean proposalPhase() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Insert professor data", "Delete professor data",
                "Edit professor data", "Consult professor data", "Student Management", "Professor Management",
                "Close state");

        switch (input) {
            case 1 -> insertData();
            case 2 -> deleteProposalData();
            case 3 -> editProposalData();
            case 4 -> consultData();
            case 5 -> studentManagement();
            case 6 -> professorManagement();
            case 7 -> closePhase();
        }

        return true;
    }

    public void deleteProposalData() {
        String identifier = InputProtection.readString("Specify the id of the proposal you want to delete: ", true);
        context.deleteData(identifier);
    }

    public void editProposalData() {
        String identifier = InputProtection.readString("Specify the id of the proposal you want to edit: ", true);
        context.editData(identifier);
    }

    public boolean proposalPhaseLocked() {
        System.out.println("Current state: " + context.getState());

        int input = InputProtection.chooseOption(null, "Consult proposal data", "Student Management",
                "Professor Management");

        switch (input) {
            case 1 -> consultData();
            case 2 -> studentManagement();
            case 3 -> professorManagement();
        }

        return true;
    }

    public void start() {
        while (switch (context.getState()) {
            case STUDENT -> studentPhase();
            case STUDENT_LOCKED -> studentPhaseLocked();
            case PROFESSOR -> professorPhase();
            case PROFESSOR_LOCKED -> professorPhaseLocked();
            case PROPOSAL -> proposalPhase();
            case PROPOSAL_LOCKED -> proposalPhaseLocked();
            /*
             * case CANDIDATURE -> candidaturePhase();
             * case CANDIDATURE_LOCKED -> candidaturePhaseLocked();
             * case PROPOSAL_ATTRIBUTION -> proposalAttributionPhase();
             * case PROPOSAL_ATTRIBUTION_LOCKED -> proposalAttributionPhaseLocked();
             * case PROFESSOR_ATTRIBUTION -> professorAttribution();
             * case SEARCH -> searchPhase();
             */
            default -> throw new IllegalArgumentException("Unexpected value: " + context.getState());
        })
            ;
    }

}
