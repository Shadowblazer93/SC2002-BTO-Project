package controller.user;

import entity.user.Applicant;
import interfaces.IApplicantService;
import java.util.HashMap;
import java.util.Map;

/**
 * The ApplicantController class manages the creation and retrieval of applicants in the system.
 * It implements the IApplicantService interface to provide functionality for managing applicants.
 */
public class ApplicantController implements IApplicantService {
    private static final Map<String, Applicant> allApplicants = new HashMap<>(); // NRIC + Applicant

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
    @Override
    public Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password) {
        Applicant applicant = new Applicant(nric, name, age, maritalStatus, password);
        allApplicants.put(nric, applicant);
        return applicant;
    }

    /**
     * Retrieves an applicant from the system by their NRIC.
     *
     * @param nric NRIC of the applicant to retrieve.
     * @return Applicant object associated with the given NRIC, or null if no such applicant exists.
     */
    @Override
    public Applicant getApplicant(String nric) {
        return allApplicants.get(nric);
    }

    /**
     * Retrieves all applicants in the system.
     *
     * @return Map of all applicants, with keys as NRIC and values as Applicant objects.
     */
    @Override
    public Map<String, Applicant> getAllApplicants() {
        return allApplicants;
    }
}
