package entity.registration;

import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;

/**
 * Represents a registration request from an Officer to handle a BTO project.
 * Stores information about the officer making the request, the target project,
 * registration date, and the current status (PENDING, APPROVED, or REJECTED).
 * Registration objects are created when officers express interest in managing
 * a project and must be approved by a manager before the officer is assigned.
 */
public class Registration {
    private final int id;
    private final Officer officer;
    private final String projectName;
    private final LocalDate registrationDate;
    private RegistrationStatus status;

    /**
     * Constructs a new Registration with the specified details.
     *
     * @param id The unique identifier for this registration
     * @param officer The officer registering for the project
     * @param projectName The name of the project being registered for
     * @param registrationDate The date when the registration was submitted
     * @param status The initial status of the registration (PENDING, APPROVED, REJECTED)
     */
    public Registration(int id, Officer officer, String projectName, LocalDate registrationDate, RegistrationStatus status) {
        this.id = id;
        this.officer = officer;
        this.projectName = projectName;
        this.registrationDate = registrationDate;
        this.status = status; 
    }

    /**
     * Retrieves the registration status.
     * 
     * @return The current status of this registration
     */
    public RegistrationStatus getRegistrationStatus() {
        return this.status;
    }

    /**
     * Retrieves the unique identifier for this registration.
     * 
     * @return The registration ID
     */
    public int getID() {
        return id;
    }

    /**
     * Retrieves the officer who submitted this registration.
     * 
     * @return The officer entity
     */
    public Officer getOfficer() {
        return this.officer;
    }

    /**
     * Retrieves the name of the project this registration is for.
     * 
     * @return The project name
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Retrieves the date when this registration was submitted.
     * 
     * @return The registration date
     */
    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * Retrieves the current status of this registration.
     * 
     * @return The registration status (PENDING, APPROVED, or REJECTED)
     */
    public RegistrationStatus getStatus() {
        return this.status;
    }

    /**
     * Updates the registration status to APPROVED.
     * Called when a manager approves this registration.
     */
    public void approveRegistration() {
        this.status = RegistrationStatus.APPROVED;
    }

    /**
     * Updates the registration status to REJECTED.
     * Called when a manager rejects this registration.
     */
    public void rejectRegistration() {
        this.status = RegistrationStatus.REJECTED;
    }
}
