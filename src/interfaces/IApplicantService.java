package interfaces;

import entity.user.Applicant;
import java.util.Map;

/**
 * IApplicantService interface defines methods for managing applicants in the system.
 * It provides methods for creating, retrieving, and managing applicants.
 */
/**
 * Defines the operations related to managing applicant data in the BTOMS
 */
public interface IApplicantService {

    /**
     * Creates a new applicant and adds them to the system.
     *
     * @param nric          The NRIC of the applicant.
     * @param name          The name of the applicant.
     * @param age           The age of the applicant.
     * @param maritalStatus The marital status of the applicant (e.g., "single", "married").
     * @param password      The password for the applicant's account.
     * @return The newly created Applicant object.
     */
    
    /**
     * Create a new applicant with the specified details
     * @param nric Applicant NRIC
     * @param name Applicant name
     * @param age Applicant age
     * @param maritalStatus Applicant marital status
     * @param password Applicant password
     * @return Newly created applicant object
     */
    Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password);

    /**
     * Retrieves an applicant from the system by their NRIC.
     *
     * @param nric The NRIC of the applicant to retrieve.
     * @return The Applicant object associated with the given NRIC, or null if no such applicant exists.
     */
    
    /**
     * Retrieve an applicant using their NRIC
     * @param nric Applicant NRIC
     * @return Applicant object if found, null otherwise
     */
    Applicant getApplicant(String nric);

    /**
     * Retrieves all applicants in the system.
     *
     * @return A map of all applicants, keyed by their NRIC.
     */
    
    /**
     * Retrieve a map of all Applicants
     * @return Map of NRICs to Applicant objects
     */
    Map<String, Applicant> getAllApplicants();
}
