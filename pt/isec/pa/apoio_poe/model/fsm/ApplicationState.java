package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.*;
import pt.isec.pa.apoio_poe.model.data.Data;

public enum ApplicationState {
    STUDENT, STUDENT_LOCKED, PROFESSOR, PROFESSOR_LOCKED, PROPOSAL, PROPOSAL_LOCKED, // PHASE1
    CANDIDATURE, CANDIDATURE_LOCKED, // PHASE2
    PROPOSAL_ATTRIBUTION, PROPOSAL_ATTRIBUTION_LOCKED, TIE, // PHASE3
    PROFESSOR_ATTRIBUTION, // PHASE4
    SEARCH; // PHASE5

    IApplicationState createState(ApplicationContext context, Data data) {
        // State factory

        return switch (this) {
            case STUDENT -> new StudentPhase(context, data);
            case PROFESSOR -> new ProfessorPhase(context, data);
            case PROPOSAL -> new ProposalPhase(context, data);
            case STUDENT_LOCKED -> new StudentPhaseLocked(context, data);
            case PROFESSOR_LOCKED -> new ProfessorPhaseLocked(context, data);
            case PROPOSAL_LOCKED -> new ProposalPhaseLocked(context, data);
            case CANDIDATURE -> new CandidaturePhase(context, data);
            case CANDIDATURE_LOCKED -> new CandidaturePhaseLocked(context, data);
            case PROPOSAL_ATTRIBUTION -> new ProposalAttributionPhase(context, data);
            case PROPOSAL_ATTRIBUTION_LOCKED -> new ProposalAttributionPhaseLocked(context, data);
            case TIE -> new Tie(context, data);
            case PROFESSOR_ATTRIBUTION -> new ProfessorAttributionPhase(context, data);
            case SEARCH -> new SearchPhase(context, data);
            default -> null;
        };
    }
}
