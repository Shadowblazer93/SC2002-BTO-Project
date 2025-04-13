package controller;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import java.util.ArrayList;
import java.util.List;

public class EnquiryController {
    private static List<Enquiry> allEnquiries = new ArrayList<>();  // Store all enquiries
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
        allEnquiries.add(enquiry);  // Add the enquiry to the list
        return enquiry;
    }

    public static void addEnquiry(Enquiry enquiry) {
        allEnquiries.add(enquiry);  // Add the enquiry to the list
    }

    public static void removeEnquiry(Enquiry enquiry) {
        allEnquiries.remove(enquiry);  // Remove the enquiry from the list
    }
    
    public static List<Enquiry> getAllEnquiries() {
        return allEnquiries;
    }

<<<<<<< HEAD
    public void replyEnquiry(BTOProject project, int enquiryID, String reply) {
=======
    public void replyEnquiry(Officer officer, String enquiryID, String reply) {
        BTOProject assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("Officer has no assigned project.");
            return;
        }
        
>>>>>>> 3eb7033 (changed OfficerMain)
        // Find the enquiry based on ID
        Enquiry enquiry = project.getEnquiries().get(enquiryID);
        // If the enquiry is found, reply to it
        if (enquiry != null) {
            enquiry.setReply(reply);  // Set the response and update the status to CLOSED
            System.out.println("Response sent successfully.");
        } else {
            System.out.println("Enquiry with ID " + enquiryID + " not found.");
        }
    }
}
