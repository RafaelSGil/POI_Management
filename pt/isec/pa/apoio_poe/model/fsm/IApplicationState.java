package pt.isec.pa.apoio_poe.model.fsm;

public interface IApplicationState {
    boolean insertData();

    boolean checkData();

    boolean editData();

    boolean deleteData();

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

    ApplicationState getState();
}