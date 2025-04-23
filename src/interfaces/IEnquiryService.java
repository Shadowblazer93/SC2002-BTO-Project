package interfaces;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.EnquiryStatus;
import java.util.List;
import java.util.Map;

/**
 * IEnquiryService interface defines operations for managing enquiries in the BTO system.
 * Provides methods to create, retrieve, update, and delete enquiries.
 */
public interface IEnquiryService {

    /**
     * Adds an enquiry to the system.
     *
     * @param enquiry Enquiry to be added.
     */
    void addEnquiry(Enquiry enquiry);

    /**
     * Removes an enquiry from the system.
     *
     * @param enquiry Enquiry to be removed.
     */
    void removeEnquiry(Enquiry enquiry);

    /**
     * Retrieves all enquiries in the system.
     *
     * @return Map of all enquiries, keyed by their IDs.
     */
    Map<Integer, Enquiry> getAllEnquiries();

    /**
     * Retrieves an enquiry by its ID.
     *
     * @param id ID of the enquiry to retrieve.
     * @return Enquiry with the specified ID, or null if not found.
     */
    Enquiry getEnquiryByID(int id);

    /**
     * Retrieves all enquiries submitted by an applicant with the specified NRIC.
     *
     * @param nric NRIC of the applicant.
     * @return List of enquiries submitted by the applicant.
     */
    List<Enquiry> getEnquiriesByNRIC(String nric);

    /**
     * Creates a new enquiry and adds it to the system.
     *
     * @param id            ID of the enquiry (0 for auto-generated ID).
     * @param applicantNRIC NRIC of the applicant submitting the enquiry.
     * @param projectName   Name of the project the enquiry is about.
     * @param message       Message of the enquiry.
     * @param response      Response to the enquiry (if any).
     * @param status        Status of the enquiry (OPEN or CLOSED).
     * @return Newly created Enquiry object.
     */
    Enquiry createEnquiry(int id, String applicantNRIC, String projectName, String message, 
                          String response, EnquiryStatus status);

    /**
     * Submits a new enquiry on behalf of an applicant for a specific project.
     *
     * @param applicant Applicant submitting the enquiry.
     * @param project   Project the enquiry is about.
     * @param message   Message of the enquiry.
     */
    void submitEnquiry(Applicant applicant, BTOProject project, String message);

    /**
     * Deletes an enquiry from the system and removes it from the applicant's and project's records.
     *
     * @param applicant Applicant who submitted the enquiry.
     * @param enquiry   Enquiry to be deleted.
     */
    void deleteEnquiry(Applicant applicant, Enquiry enquiry);

    /**
     * Edits the message of an existing enquiry.
     *
     * @param enquiry    Enquiry to be edited.
     * @param newMessage New message for the enquiry.
     */
    void editEnquiry(Enquiry enquiry, String newMessage);

    /**
     * Replies to an enquiry for a specific project.
     *
     * @param project   Project the enquiry is associated with.
     * @param enquiryId ID of the enquiry to reply to.
     * @param reply     Reply message for the enquiry.
     */
    void replyEnquiry(BTOProject project, int enquiryId, String reply);

}
