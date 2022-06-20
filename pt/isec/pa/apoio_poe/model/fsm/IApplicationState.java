package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.person.Person;
import java.util.ArrayList;
import java.util.List;

public interface IApplicationState {
    /**
     *
     * @param file path
     * @return bool
     */
    boolean insertData(String file);

    /**
     *
     * @return ArrayList
     */
    ArrayList<String> checkData();

    /**
     *
     * @param identifier id of proposals
     * @param attribute attribute
     * @param newValue value
     * @return bool
     */
    boolean editDataProposal(String identifier, String attribute, List<String> newValue);

    /**
     *
     * @param email email of professor
     * @param advisor new value
     * @return
     */
    boolean editDataProfessor(String email, String advisor);

    /**
     *
     * @param identifier id of proposal
     * @param change parameter
     * @param whatToChange new value
     * @return
     */
    boolean editDataStudent(String identifier, String change, String whatToChange);

    /**
     *
     * @param identifier id
     * @return bool
     */
    boolean deleteData(String identifier);

    /**
     *
     * @param type type
     * @return bool
     */
    boolean chooseType(String type);

    /**
     *
     * @return bool
     */
    boolean closeState();

    /**
     *
     * @return whether it successfully transitioned to student state
     */
    boolean studentTransitioning();

    /**
     *
     * @return whether it successfully transitioned to professor state
     */
    boolean professorTransitioning();

    /**
     *
     * @return whether it successfully transitioned to proposal state
     */
    boolean proposalTransitioning();

    /**
     *
     * @return whether it successfully transitioned to candidature state
     */
    boolean candidatureTransitioning();

    /**
     *
     * @return ArrayList - List with all students who have a self-proposal
     */
    ArrayList<String> listStudentsWithAutoProposal();

    /**
     *
     * @return ArrayList - List with all students who have a candidature
     */
    ArrayList<String> listStudentsWithCandidature();

    /**
     *
     * @return ArrayList - List with all students who don't have a candidature
     */
    ArrayList<String> listStudentsWithoutCandidature();

    /**
     *
     * @return string
     */
    String listAutoProposalWithoutCandidatures();

    /**
     *
     * @return string
     */
    String listAutoProposalWithCandidatures();

    /**
     *
     * @return bool
     */
    boolean proposalAttributionTransitioning();

    /**
     *
     * @return bool
     */
    boolean associateAttribution();

    /**
     *
     * @return bool
     */
    boolean nonAssociateAttribution();

    /**
     *
     * @param index index of student
     * @return bool
     */
    boolean chooseStudentToAssociate(String index);

    /**
     *
     * @return ArrayList
     */
    ArrayList<Person> getTies();

    /**
     *
     * @param idOfProposal id of proposal
     * @param idOfStudent id of student
     * @return bool
     */
    boolean manualAttribution(String idOfProposal, String idOfStudent);

    /**
     *
     * @param idOfProposal id of proposal
     * @return bool
     */
    boolean manualRemoval(String idOfProposal);

    /**
     *
     * @return bool
     */
    boolean professorAttributionTransitioning();

    /**
     *
     * @return ArrayList
     */
    ArrayList<String> listStudentsWithProposalWithoutProfessor();

    /**
     *
     * @return ArrayList
     */
    ArrayList<String> listStudentsWithProposalAndProfessor();

    /**
     *
     * @return string
     */
    String averageProfessorAttributions();

    /**
     *
     * @return string
     */
    String minimumProfessorAttributions();

    /**
     *
     * @return string
     */
    String maximumProfessorAttributions();

    /**
     *
     * @return string
     */
    String specificProfessorAttributions(String email);

    /**
     *
     * @return bool
     */
    boolean listProposalsWithAttributions();

    /**
     *
     * @return bool
     */
    boolean listProposalsWithoutAttributions();

    /**
     *
     * @return bool
     */
    boolean listStudentsWithProposals();

    /**
     *
     * @return enum
     */
    ApplicationState getState();

    /**
     *
     * @param id candidature to remove from
     * @param proposal proposal to remove
     * @return whether the removal was successful or not
     */
    boolean removeProposalFromCandidature(String id, String proposal);

    /**
     *
     * @param id candidature to remove
     * @return whether the removal was successful or not
     */
    boolean removeCandidature(String id);

    /**
     *
     * @param id id of candidature
     * @param proposal id of proposals
     * @return bool
     */
    boolean editCandidatures(String id, String proposal);

    /**
     *
     * @param filters list of filters
     * @return ArrayList
     */
    ArrayList<String> listProposalsFilters(List<String> filters);

    /**
     *
     *
     * @return ArrayList
     */
    ArrayList<String> listStudentWithProposalAttributed();

    /**
     *
     *
     * @return ArrayList
     */
    ArrayList<String> listStudentWithoutProposalAttributed();

    /**
     *
     * @param idOfProposal id of proposal
     * @param emailProfessor email of professor
     * @return bool
     */
    boolean manualProfessorAttribution(String idOfProposal, String emailProfessor);

    /**
     *
     * @param email of professro
     * @param idOfProposal id of proposal
     * @return bool
     */
    boolean manualProfessorRemoval(String email, String idOfProposal);

    /**
     *
     * @param email id of professro
     * @return string
     */
    String getProfessorByEmail(String email);

    /**
     *
     *
     * @return ArrayList
     */
    ArrayList<String> listProfessorAttributions();

    /**
     *
     */
    void exportData();
}