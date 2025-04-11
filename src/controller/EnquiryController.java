package controller;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Officer;
import java.util.ArrayList;
import java.util.List;

public class EnquiryController {
    private static List<Enquiry> allEnquiries = new ArrayList<>();  // Store all enquiries
    private static int enquiryCount = 0;                            // Track enquiry ID

    public int getEnquiryCount() {
        return enquiryCount;
    }

    public void replyEnquiry(Officer officer, String enquiryID, String reply) {
        BTOProject assignedProject = officer.getAssignedProject();
        // Find the enquiry based on ID
        Enquiry enquiry = assignedProject.getEnquiries().get(enquiryID);
        // If the enquiry is found, reply to it
        if (enquiry != null) {
            enquiry.setReply(reply);  // Set the response and update the status to CLOSED
            System.out.println("Response sent successfully.");
        } else {
            System.out.println("Enquiry with ID " + enquiryID + " not found.");
        }
    }
}
