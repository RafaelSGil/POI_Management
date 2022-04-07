package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseFive;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseFour;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseOne;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseThree;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseTwo;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.ProfessorPhase;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.ProposalPhase;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;

public enum ApplicationState 
{
    PHASE1, PHASE2, PHASE3, PHASE4, PHASE5, STUDENT_PHASE, PROFESSOR_PHASE, PROPOSAL_PHASE;

    IApplicationState createState(ApplicationContext context, Application application){
        return switch (this) {
            case PHASE1 -> new PhaseOne(context, application);
            case PHASE2 -> new PhaseTwo(context, application);
            case PHASE3 -> new PhaseThree(context, application);
            case PHASE4 -> new PhaseFour(context, application);
            case PHASE5 -> new PhaseFive(context, application);
            case STUDENT_PHASE -> new StudentPhase(context, application);
            case PROFESSOR_PHASE -> new ProfessorPhase(context, application);
            case PROPOSAL_PHASE -> new ProposalPhase(context, application);
            default -> null;
        };
    }
}
