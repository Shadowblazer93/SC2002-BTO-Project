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
 * Interface for managing the registration logic between officers and BTO projects.
 * Includes methods for creating, approving, rejecting, and retrieving registrations.
 */
public interface IRegistrationService {

    /**
     * Retrieves all officer registrations grouped by project name.
     *
     * @return A map where the key is the project name and the value is a list of registrations.
     */
    Map<String, List<Registration>> getAllRegistrations();
    
    /**
     * Retrieves all officer registrations associated with a specific project.
     *
     * @param projectName The name of the BTO project.
     * @return A list of {@code Registration} objects for the given project.
     */
    List<Registration> getRegistrationsByProject(String projectName);
    
    /**
     * Adds a new registration entry under the specified project.
     *
     * @param project      The name of the project.
     * @param registration The registration object to add.
     */
    void addRegistration(String project, Registration registration);

    /**
     * Creates a new {@code Registration} object with the specified details.
     *
     * @param id               The ID of the registration.
     * @param officer          The officer being registered.
     * @param projectName      The name of the project.
     * @param registrationDate The date of registration.
     * @param status           The current registration status.
     * @return A new {@code Registration} object.
     */
    Registration createRegistration(int id, Officer officer, String projectName, 
                                    LocalDate registrationDate, RegistrationStatus status);
               
    /**
     * Registers an officer to a specific BTO project.
     *
     * @param officer The officer attempting to register.
     * @param project The BTO project to register for.
     * @return A {@code Registration} object representing the registration.
     */
    Registration registerProject(Officer officer, BTOProject project);

    /**
     * Approves a registration by a manager.
     *
     * @param manager     The manager approving the registration.
     * @param registration The registration to approve.
     * @return A message indicating success or failure of approval.
     */
    String approveRegistration(Manager manager, Registration registration);
    
    /**
     * Rejects a registration by a manager.
     *
     * @param manager     The manager rejecting the registration.
     * @param registration The registration to reject.
     * @return A message indicating success or failure of rejection.
     */
    String rejectRegistration(Manager manager, Registration registration);

}
