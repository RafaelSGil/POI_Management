package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import java.util.List;

import java.util.Iterator;

import pt.isec.pa.apoio_poe.csv_files.Files;

public class StudentPhase extends StateAdapter {

    public StudentPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
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

            data.addStudent(name, email, id, course, courseBranch, classification, internship);
        }
        return true;
    }

    @Override
    public String checkData() {
        return data.getAllStudents();
    }

    @Override
    public boolean deleteData(String identifier) {
        long id = Long.parseLong(identifier);
        return data.removeStudentGivenItsId(id);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT;
    }

}
