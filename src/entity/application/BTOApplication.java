package entity.application;

import entity.user.Applicant;
import enums.ApplicationStatus;
import enums.FlatType;

/**
 * Represents a BTO (Build-To-Order) application submitted by an applicant.
 * Stores details such as the project name, flat type, application status,
 * and withdrawal request status.
 */
public class BTOApplication {

    /** Unique identifier for the application. */
    private final int ID;

    /** The applicant who submitted this application. */
    private Applicant applicant;

    /** The name of the BTO project this application is for. */
    private final String projectName;

    /** The flat type the applicant has applied for (e.g., 2-room, 3-room). */
    private FlatType flatType;

    /** The current status of the application. */
    private ApplicationStatus status;

    /** Indicates whether the applicant has requested a withdrawal. */
    private boolean withdrawal;

    /**
     * Constructs a new {@code BTOApplication} with the given parameters.
     *
     * @param ID          Unique ID of the application
     * @param applicant   The applicant submitting this application
     * @param projectName The name of the BTO project
     * @param flatType    The flat type selected
     * @param status      The application status
     * @param withdrawal  Whether withdrawal was requested initially
     */
    public BTOApplication(int ID, Applicant applicant, String projectName, FlatType flatType, ApplicationStatus status, boolean withdrawal) {
        this.ID = ID;
        this.applicant = applicant;
        this.projectName = projectName;
        this.flatType = flatType;
        this.status = status;
        this.withdrawal = withdrawal;
    }

    /**
     * Returns the application ID.
     *
     * @return Unique integer ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the applicant associated with this application.
     *
     * @return The applicant object
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Returns the name of the BTO project applied for.
     *
     * @return Project name as a string
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the flat type selected in this application.
     *
     * @return The selected {@code FlatType}
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Returns the current withdrawal status of the application.
     *
     * @return {@code true} if withdrawal is requested, otherwise {@code false}
     */
    public boolean getWithdrawal() {
        return withdrawal;
    }

    /**
     * Updates the flat type associated with this application.
     *
     * @param flatType The new flat type
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Updates the initial applicant object reference.
     * Typically used during setup or reconstruction.
     *
     * @param applicant The applicant to set
     */
    public void setApplicantInitial(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * Returns the current status of the application.
     *
     * @return The application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the application status (e.g., PENDING, SUCCESSFUL).
     *
     * @param status The new status
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Updates the withdrawal request flag.
     *
     * @param withdrawal {@code true} if withdrawal is requested, otherwise {@code false}
     */
    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    /**
     * Returns a formatted string representation of this application,
     * including project, status, and applicant details.
     *
     * @return Human-readable string
     */
    @Override
    public String toString() {
        return String.format("""
                ID: %d
                Applicant: %s
                Project Name: %s
                Flat Type: %s
                Status: %s
                Requested Withdrawal: %b""",
                ID, applicant.getName(), projectName, flatType, status, withdrawal);
    }
} 
