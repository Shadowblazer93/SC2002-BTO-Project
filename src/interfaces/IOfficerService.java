package interfaces;

import entity.project.BTOProject;
import entity.user.Officer;
import java.util.Map;

/**
 * Interface for services related to Officer operations.
 */
public interface IOfficerService {
    
    /**
     * Creates a new Officer with the given details.
     *
     * @param nric NRIC of the officer
     * @param name Name of the officer
     * @param password Officer's password
     * @param age Age of the officer
     * @param maritalStatus Marital status of the officer
     * @return The newly created Officer object
     */
    Officer createOfficer(String nric, String name, String password, int age, String maritalStatus);
    
    /**
     * Retrieves an officer by their NRIC.
     *
     * @param nric The NRIC of the officer
     * @return The Officer object if found, otherwise null
     */
    Officer getOfficer(String nric);
    
    /**
     * Retrieves a map of all registered officers.
     *
     * @return A map with NRICs as keys and Officer objects as values
     */
    Map<String, Officer> getAllOfficers();
    
    /**
     * Registers an officer for a BTO project, subject to validation rules.
     *
     * @param officer The officer attempting to register
     * @param project The BTO project to register for
     * @return A message indicating the result of the registration attempt
     */
    String registerProject(Officer officer, BTOProject project);
}
