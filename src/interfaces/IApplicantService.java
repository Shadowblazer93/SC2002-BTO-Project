package interfaces;

import entity.user.Applicant;
import java.util.Map;

/**
 * Defines the operations related to managing applicant data in the BTOMS
 */
public interface IApplicantService {
    
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
     * Retrieve an applicant using their NRIC
     * @param nric Applicant NRIC
     * @return Applicant object if found, null otherwise
     */
    Applicant getApplicant(String nric);
    
    /**
     * Retrieve a map of all Applicants
     * @return Map of NRICs to Applicant objects
     */
    Map<String, Applicant> getAllApplicants();
    
}
