package controller;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import java.util.HashMap;
import java.util.Map;

public class EnquiryController {
    private static Map<Integer, Enquiry> allEnquiries = new HashMap<>();  // Store all enquiries
    private static int enquiryCount = 0;                            // Track enquiry ID

    public static int getEnquiryCount() {
        return enquiryCount;
    }
    public static void incrementEnquiryCount() {
        enquiryCount++;
    }

    public Enquiry createEnquiry(int id, String applicantNRIC, String projectName, String message, String response, String status) {
        enquiryCount = Math.max(enquiryCount,id)+1;
        Enquiry enquiry = new Enquiry(id,applicantNRIC,projectName,message,response,status);
        allEnquiries.put(enquiry.getID(), enquiry);  // Add the enquiry to the list
        return enquiry;
    }

    public static void addEnquiry(Enquiry enquiry) {
        allEnquiries.put(enquiry.getID(), enquiry);  // Add the enquiry to the list
    }

    public static void removeEnquiry(Enquiry enquiry) {
        allEnquiries.remove(enquiry.getID());  // Remove the enquiry from the list
    }
    
    public static Map<Integer, Enquiry> getAllEnquiries() {
        return allEnquiries;
    }

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
