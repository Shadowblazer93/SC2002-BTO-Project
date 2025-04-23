package controller;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.EnquiryStatus;
import interfaces.IEnquiryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EnquiryController class manages all operations related to enquiries in the BTO system.
 * Provides functionality to create, retrieve, update, and delete enquiries.
 */
public class EnquiryController implements IEnquiryService {
    private static Map<Integer, Enquiry> allEnquiries = new HashMap<>();  // Store all enquiries
    private static int enquiryCount = 1;                                  // Track enquiry ID

    /**
     * Adds an enquiry to the system.
     *
     * @param enquiry Enquiry to be added.
     */
    @Override
    public void addEnquiry(Enquiry enquiry) {
        allEnquiries.put(enquiry.getID(), enquiry);  // Add the enquiry to the map
    }

    /**
     * Removes an enquiry from the system.
     *
     * @param enquiry Enquiry to be removed.
     */
    @Override
    public void removeEnquiry(Enquiry enquiry) {
        allEnquiries.remove(enquiry.getID());
    }

    /**
     * Retrieves all enquiries in the system.
     *
     * @return Map of all enquiries, keyed by their IDs.
     */
    @Override
    public Map<Integer, Enquiry> getAllEnquiries() {
        return allEnquiries;
    }

    /**
     * Retrieves an enquiry by its ID.
     *
     * @param id ID of the enquiry to retrieve.
     * @return Enquiry with the specified ID, or null if not found.
     */
    @Override
    public Enquiry getEnquiryByID(int id) {
        return allEnquiries.get(id);
    }

    /**
     * Retrieves all enquiries submitted by an applicant with the specified NRIC.
     *
     * @param nric NRIC of the applicant.
     * @return List of enquiries submitted by the applicant.
     */
    @Override
    public List<Enquiry> getEnquiriesByNRIC(String nric) {
        List<Enquiry> enquiries = new ArrayList<>();
        for (Enquiry enquiry : allEnquiries.values()) {
            if (enquiry.getApplicantNRIC().equalsIgnoreCase(nric)) {
                enquiries.add(enquiry);
            }
        }
        return enquiries;
    }

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
    @Override
    public Enquiry createEnquiry(int id, String applicantNRIC, String projectName, String message, String response, EnquiryStatus status) {
        enquiryCount = Math.max(enquiryCount, id);
        Enquiry enquiry = new Enquiry(enquiryCount, applicantNRIC, projectName, message, response, status);
        enquiryCount++;
        addEnquiry(enquiry);
        return enquiry;
    }

    /**
     * Submits a new enquiry on behalf of an applicant for a specific project.
     *
     * @param applicant Applicant submitting the enquiry.
     * @param project   Project the enquiry is about.
     * @param message   Message of the enquiry.
     */
    @Override
    public void submitEnquiry(Applicant applicant, BTOProject project, String message) {
        Enquiry enquiry = createEnquiry(0, applicant.getNRIC(), project.getProjectName(), message, null, EnquiryStatus.OPEN);
        applicant.addEnquiry(enquiry);  // Add enquiry to applicant
        project.addEnquiry(enquiry);    // Add enquiry to project
    }

    /**
     * Deletes an enquiry from the system and removes it from the applicant's and project's records.
     *
     * @param applicant Applicant who submitted the enquiry.
     * @param enquiry   Enquiry to be deleted.
     */
    @Override
    public void deleteEnquiry(Applicant applicant, Enquiry enquiry) {
        removeEnquiry(enquiry);             // Remove from all enquiries
        applicant.removeEnquiry(enquiry);   // Remove from applicant's enquiries
    }

    /**
     * Edits the message of an existing enquiry.
     *
     * @param enquiry    Enquiry to be edited.
     * @param newMessage New message for the enquiry.
     */
    @Override
    public void editEnquiry(Enquiry enquiry, String newMessage) {
        enquiry.setMessage(newMessage);
    }

    /**
     * Replies to an enquiry for a specific project.
     *
     * @param project   Project the enquiry is associated with.
     * @param enquiryId ID of the enquiry to reply to.
     * @param reply     Reply message for the enquiry.
     */
    @Override
    public void replyEnquiry(BTOProject project, int enquiryId, String reply) {
        // Find the enquiry based on ID
        Enquiry enquiry = project.getEnquiries().get(enquiryId);
        if (enquiry != null) {
            enquiry.setReply(reply);
            System.out.println("Response sent successfully.");
        } else {
            System.out.println("Enquiry with ID " + enquiryId + " not found.");
        }
    }
}
