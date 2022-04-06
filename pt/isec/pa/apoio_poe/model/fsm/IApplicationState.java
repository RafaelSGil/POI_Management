package pt.isec.pa.apoio_poe.model.fsm;

public interface IApplicationState
{
    boolean InsertData();
    boolean checkData();
    boolean editData();
    boolean deleteData();

    ApplicationState getState();
}