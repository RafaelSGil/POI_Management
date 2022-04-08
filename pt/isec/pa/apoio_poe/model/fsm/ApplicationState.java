package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.ProfessorPhase;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.ProposalPhase;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;

public enum ApplicationState {
    STUDENT, STUDENT_LOCKED, PROFESSOR, PROFESSOR_LOCKED, PROPOSAL, PROPOSAL_LOCKED, // PHASE1
    CANDIDATURE, CANDIDATURE_LOCKED, // PHASE2
    PROPOSAL_ATRIBUTTION, PROPOSAL_ATRIBUTTION_LOCKED, // PHASE3
    PROFESSOR_ATTRIBUTION, // PHASE4
    SEARCH; // PHASE5

    IApplicationState createState(ApplicationContext context, Application application) {
        return switch (this) {
            case STUDENT -> new StudentPhase(context, application);
            case PROFESSOR -> new ProfessorPhase(context, application);
            case PROPOSAL -> new ProposalPhase(context, application);

        };
    }
}
