package pt.isec.pa.apoio_poe.model.fsm;

public interface IApplicationState {
    boolean chooseType(String type);

    boolean closeState();

    boolean studentManager();

    boolean professorManager();

    boolean proposalManager();

    boolean candidatureManager();

    boolean proposalAttributionManager();

    boolean professorAttributionManager();

    ApplicationState getState();
}