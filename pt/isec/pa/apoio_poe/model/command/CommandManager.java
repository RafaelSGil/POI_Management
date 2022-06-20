package pt.isec.pa.apoio_poe.model.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>Facade for the command pattern</p>
 *<p>Enables the undo and redo functionality</p>
 * @author RafelGil and HugoFerreira
 */
public class CommandManager {
    private Deque<ICommand> undoProp;
    private Deque<ICommand> redoProp;
    private Deque<ICommand> undoProf;
    private Deque<ICommand> redoProf;

    /**
     *
     */
    public CommandManager(){
        this.undoProp = new ArrayDeque<>();
        this.redoProp = new ArrayDeque<>();
        this.undoProf = new ArrayDeque<>();
        this.redoProf = new ArrayDeque<>();
    }

    /**
     *
     * @param cmd command
     * @return bool
     */
    public boolean invokeProp(ICommand cmd){
        redoProp.clear();
        if(cmd.execute()){
            undoProp.push(cmd);
            return true;
        }
        undoProp.clear();
        return false;
    }

    /**
     *
     * @return bool
     */
    public boolean undoProp() {
        if (undoProp.isEmpty())
            return false;
        ICommand cmd = undoProp.pop();
        cmd.undo();
        redoProp.push(cmd);
        return true;
    }

    /**
     *
     * @return bool
     */
    public boolean redoProp() {
        if (redoProp.isEmpty())
            return false;
        ICommand cmd = redoProp.pop();
        cmd.execute();
        undoProp.push(cmd);
        return true;
    }

    /**
     *
     * @param cmd command
     * @return bool
     */
    public boolean invokeProf(ICommand cmd){
        redoProf.clear();
        if(cmd.execute()){
            undoProf.push(cmd);
            return true;
        }
        undoProf.clear();
        return false;
    }

    /**
     *
     * @return bool
     */
    public boolean undoProf(){
        if(undoProf.isEmpty()){
            return false;
        }
        ICommand cmd = undoProf.pop();
        cmd.undo();
        redoProf.push(cmd);
        return true;
    }

    /**
     *
     * @return bool
     */
    public boolean redoProf(){
        if(redoProf.isEmpty()){
            return false;
        }
        ICommand cmd = redoProf.pop();
        cmd.execute();
        undoProf.push(cmd);
        return true;
    }
}
