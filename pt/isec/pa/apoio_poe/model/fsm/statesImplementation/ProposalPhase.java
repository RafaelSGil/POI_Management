package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import java.util.List;
import java.util.Iterator;
import pt.isec.pa.apoio_poe.csv_files.Files;
import pt.isec.pa.apoio_poe.model.data.person.*;

public class ProposalPhase extends StateAdapter {

    public ProposalPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
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

                    data.addInternship(idOfProposal, title, student, branch, nameOfCompany);
                } else {
                    data.addInternshipWithoutStudent(idOfProposal, title, branch, nameOfCompany);
                }
            }

            else if (first.equals("T2")) { // Project
                idOfProposal = attributesIterator.next().get(1);
                branch = attributesIterator.next().get(2);
                title = attributesIterator.next().get(3);
                professor = (Professor) Professor.createDummyProfessor(attributesIterator.next().get(4));

                if (attributesIterator.next().size() > 5) {
                    student = (Student) Student.createDummyStudent(Long.parseLong(attributesIterator.next().get(5)));

                    data.addProject(idOfProposal, title, student, branch, professor);
                } else {
                    data.addProjectWithoutStudent(idOfProposal, title, branch, professor);
                }
            }

            else if (first.equals("T3")) { // Auto proposal
                idOfProposal = attributesIterator.next().get(1);
                title = attributesIterator.next().get(2);
                student = (Student) Student.createDummyStudent(Long.parseLong(attributesIterator.next().get(3)));

                data.addAutoProposal(idOfProposal, title, student);
            }
        }
        return true;
    }

    @Override
    public boolean deleteData(String identifier) {
        return false;
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL;
    }
}
