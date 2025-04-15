package controller;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.EnquiryStatus;
import java.util.HashMap;
import java.util.Map;

public class EnquiryController {
    private static Map<Integer, Enquiry> allEnquiries = new HashMap<>();  // Store all enquiries
    private static int enquiryCount = 1;                            // Track enquiry ID

    public static int getEnquiryCount() {
        return enquiryCount;
    }

    public static Enquiry createEnquiry(int id, String applicantNRIC, String projectName, String message, String response, EnquiryStatus status) {
        enquiryCount = Math.max(enquiryCount,id);
        Enquiry enquiry = new Enquiry(enquiryCount,applicantNRIC,projectName,message,response,status);
        enquiryCount++;
        addEnquiry(enquiry);
        return enquiry;
    }

    public static void addEnquiry(Enquiry enquiry) {
        allEnquiries.put(enquiry.getID(), enquiry);  // Add the enquiry to the map
    }

    public static void removeEnquiry(Enquiry enquiry) {
        allEnquiries.remove(enquiry.getID());
    }
    
    public static Map<Integer, Enquiry> getAllEnquiries() {
        return allEnquiries;
    }

    public static Enquiry getEnquiryByID(int id) {
        return allEnquiries.get(id);
    }

    public static void submitEnquiry(Applicant applicant, BTOProject project, String message) {
        Enquiry enquiry = createEnquiry(0, applicant.getNRIC(), project.getProjectName(), message, null, EnquiryStatus.OPEN);
        applicant.addEnquiry(enquiry);  // Add enquiry to applicant
        project.addEnquiry(enquiry);    // Add enquiry to project
    }

    public static void deleteEnquiry(Applicant applicant, Enquiry enquiry) {
        removeEnquiry(enquiry);             // Remove from all enquiries
        applicant.removeEnquiry(enquiry);   // Remove from applicant's enquiries
    }

    public static void editEnquiry(Enquiry enquiry, String newMessage) {
        enquiry.setMessage(newMessage);
    }

    public static void replyEnquiry(BTOProject project, int enquiryId, String reply) {
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
