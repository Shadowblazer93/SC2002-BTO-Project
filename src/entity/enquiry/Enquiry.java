package entity.enquiry;

import entity.project.BTOProject;
import enums.EnquiryStatus;

public class Enquiry {
    public int id;
    private String applicantNRIC;
    private BTOProject project;
    private String message;
    private String response;
    private EnquiryStatus status;


    
    public void editMessage(String msg) {
        this.message = msg;
        System.out.println("Successfully edited the enquiry message!");
    }

    public void view() {
        System.out.println("Enquiry details: ");
        System.out.println("ID: "+this.id);
        System.out.println("Applicant NRIC:" +this.applicantNRIC);
        // System.out.println("BTO Project Details");
        System.out.println("Message: "+this.message);
        System.out.println("Status: "+this.status);
        if (status==EnquiryStatus.CLOSED) System.out.println("Response:"+this.response);
    }

    public void reply(String resp) {
        this.response = resp;
        this.status = EnquiryStatus.CLOSED;
        System.out.println("Successfully responded to the enquiry.");
    }
}