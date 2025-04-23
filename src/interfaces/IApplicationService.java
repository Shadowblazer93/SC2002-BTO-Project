package interfaces;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import entity.user.Officer;
import enums.ApplicationStatus;
import enums.FlatType;
import java.util.Map;

/**
 * IApplicationService interface defines operations for managing BTO applications in the system.
 * Provides methods to create, retrieve, update, and manage applications and withdrawals.
 */
public interface IApplicationService {

    /**
     * Adds an application to the system.
     *
     * @param application Application to be added.
     */
    void addApplication(BTOApplication application);

    /**
     * Removes an application from the system.
     *
     * @param application Application to be removed.
     */
    void removeApplication(BTOApplication application);

    /**
     * Retrieves all applications in the system.
     *
     * @return Map of all applications, keyed by applicant NRIC.
     */
    Map<String, BTOApplication> getAllApplications();

    /**
     * Retrieves an application by applicant NRIC.
     *
     * @param nric NRIC of the applicant.
     * @return Application associated with the given NRIC, or null if not found.
     */
    BTOApplication getApplicationByNRIC(String nric);

    /**
     * Creates a new application and adds it to the system.
     *
     * @param id            ID of the application (0 for auto-generated ID).
     * @param applicant     Applicant submitting the application.
     * @param projectName   Name of the project the application is for.
     * @param flatType      Flat type being applied for.
     * @param status        Status of the application (e.g., PENDING, APPROVED).
     * @param withdrawal    Indicates if a withdrawal request is associated with the application.
     * @return Newly created BTOApplication object.
     */
    BTOApplication createApplication(int id, Applicant applicant, String projectName, 
                                      FlatType flatType, ApplicationStatus status, boolean withdrawal);

    /**
     * Checks if an applicant is eligible to apply for a specific flat type.
     *
     * @param applicant Applicant to check eligibility for.
     * @param flatType  Flat type being checked.
     * @return True if the applicant is eligible, false otherwise.
     */
    boolean isEligible(Applicant applicant, FlatType flatType);

    /**
     * Submits an application for a project on behalf of an applicant.
     *
     * @param applicant Applicant submitting the application.
     * @param project   Project the application is for.
     * @param flatType  Flat type being applied for.
     * @return Newly created BTOApplication object.
     */
    BTOApplication applyProject(Applicant applicant, BTOProject project, FlatType flatType);

    /**
     * Submits a withdrawal request for an applicant's application.
     *
     * @param applicant Applicant requesting the withdrawal.
     */
    void requestWithdrawal(Applicant applicant);

    /**
     * Approves an application.
     *
     * @param application Application to be approved.
     */
    void approveApplication(BTOApplication application);

    /**
     * Rejects an application.
     *
     * @param application Application to be rejected.
     */
    void rejectApplication(BTOApplication application);

    /**
     * Approves a withdrawal request for an application.
     *
     * @param application Application with the withdrawal request to be approved.
     */
    void approveWithdrawal(BTOApplication application);

    /**
     * Rejects a withdrawal request for an application.
     *
     * @param application Application with the withdrawal request to be rejected.
     */
    void rejectWithdrawal(BTOApplication application);

    /**
     * Checks if an officer has access to a specific application.
     *
     * @param officer     Officer attempting to access the application.
     * @param application Application being accessed.
     * @return True if the officer has access, false otherwise.
     */
    boolean hasAccessToApplication(Officer officer, BTOApplication application);
}
