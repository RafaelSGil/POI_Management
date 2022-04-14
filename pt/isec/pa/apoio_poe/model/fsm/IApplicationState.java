package pt.isec.pa.apoio_poe.model.fsm;

import java.util.Map;

public interface IApplicationState {
    boolean insertData(String file);

    String checkData();

    boolean editDataProposal(Map<String, Map<String, String>> attributes);

    boolean editDataProfessor(String email, boolean advisor);

    boolean editDataStudent(String identifier, String change, String whatToChange);

    boolean deleteData(String identifier);

    boolean chooseType(String type);

    boolean closeState();

    boolean studentManager();

    boolean professorManager();

    boolean proposalManager();

    boolean candidatureManager();

    boolean listStudenstWithAutoProposal();

    boolean listStudentsWithCandidature();

    boolean listStudentsWithoutCandidature();

    boolean listAutoProposalWithouCandidatures();

    boolean listAutoProposalWithCandidatures();

    boolean proposalAttributionManager();

    boolean associateAttribution();

    boolean nonAssociateAttribution();

    boolean manualAttribution();

    boolean listStudents();

    boolean listProposals();

    boolean professorAttributionManager();

    boolean listStudentsWithProposalWihoutProfessor();

    boolean listStudentsWithProposalAndProfessor();

    boolean averageProfessorAtribuitions();

    boolean minimumProfessorAtribuitions();

    boolean maximumProfessorAtribuitions();

    boolean specificProfessorAtribuitions();

    boolean listProposalsWithAtribuitions();

    boolean listProposalsWihoutAtribuitions();

    boolean listStudentsWithProposals();

    boolean listStudentsWihoutProposalsWithCandidature();

    ApplicationState getState();

    boolean removeCandidature(String id, String proposal);
}