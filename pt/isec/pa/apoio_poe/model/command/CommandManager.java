package pt.isec.pa.apoio_poe.model.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {
    private Deque<ICommand> undo;
    private Deque<ICommand> redo;

    public CommandManager(){
        this.undo = new ArrayDeque<>();
        this.redo = new ArrayDeque<>();
    }

    public boolean invoke(ICommand cmd){
        redo.clear();
        if(cmd.execute()){
            undo.push(cmd);
            return true;
        }
        undo.clear();
        return false;
    }

    public boolean undo() {
        if (undo.isEmpty())
            return false;
        ICommand cmd = undo.pop();
        cmd.undo();
        redo.push(cmd);
        return true;
    }
    public boolean redo() {
        if (redo.isEmpty())
            return false;
        ICommand cmd = redo.pop();
        cmd.execute();
        undo.push(cmd);
        return true;
    }
}
