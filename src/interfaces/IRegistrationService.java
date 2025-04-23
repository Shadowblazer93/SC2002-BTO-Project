package interfaces;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface defining services for Registration entity operations.
 * Provides methods for creating, retrieving, and managing registrations
 * of officers to BTO projects.
 */
public interface IRegistrationService {

    /**
     * Retrieves all registrations in the system, organized by project.
     * 
     * @return Map where key is project name and value is list of registrations
     */
    Map<String, List<Registration>> getAllRegistrations();
    
    /**
     * Retrieves all registrations for a specific project.
     * 
     * @param projectName Name of the project
     * @return List of registrations for the specified project
     */
    List<Registration> getRegistrationsByProject(String projectName);
    
    /**
     * Adds a registration to a specific project.
     * 
     * @param project Name of project
     * @param registration Registration to add
     */
    void addRegistration(String project, Registration registration);

    /**
     * Creates a new registration with specified details.
     * 
     * @param id Registration ID (use 0 for auto-generation)
     * @param officer Officer making the registration
     * @param projectName Project being registered for
     * @param registrationDate Date of registration
     * @param status Initial status of registration
     * @return The newly created Registration object
     */
    Registration createRegistration(int id, Officer officer, String projectName, 
                                    LocalDate registrationDate, RegistrationStatus status);
    
    /**
     * Registers an officer for a project, creating a registration with PENDING status.
     * 
     * @param officer Officer to register
     * @param project Project to register for
     * @return The created Registration object
     */
    Registration registerProject(Officer officer, BTOProject project);

    /**
     * Approves a registration, assigning the officer to the project.
     * 
     * @param manager Manager approving the registration
     * @param registration Registration to approve
     * @return Result message indicating success or reason for failure
     */
    String approveRegistration(Manager manager, Registration registration);
    
    /**
     * Rejects a registration, preventing the officer from being assigned.
     * 
     * @param manager Manager rejecting the registration
     * @param registration Registration to reject
     * @return Result message indicating success or reason for failure
     */
    String rejectRegistration(Manager manager, Registration registration);
}
