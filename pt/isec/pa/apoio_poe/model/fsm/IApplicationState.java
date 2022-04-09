package pt.isec.pa.apoio_poe.model.fsm;

public interface IApplicationState {
    boolean insertData(String file);

    String checkData();

    boolean editData();

    boolean deleteData(String identifier);

    boolean chooseType(String type);

    boolean closeState(ApplicationState state);

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
}