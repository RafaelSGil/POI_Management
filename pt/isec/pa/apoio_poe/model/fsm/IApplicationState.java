package pt.isec.pa.apoio_poe.model.fsm;

public interface IApplicationState
{
    boolean insertData();
    boolean checkData();
    boolean editData();
    boolean deleteData();

    ApplicationState getState();
}