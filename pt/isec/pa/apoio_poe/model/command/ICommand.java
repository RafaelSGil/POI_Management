package pt.isec.pa.apoio_poe.model.command;

public interface ICommand {
    /**
     *
     * @return bool
     */
    boolean execute();

    /**
     *
     * @return bool
     */
    boolean undo();
}
