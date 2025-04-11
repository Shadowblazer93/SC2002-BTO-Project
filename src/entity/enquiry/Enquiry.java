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

    public Enquiry(int id, String applicantNRIC, BTOProject project, String message) {
        this.id = id;
        this.applicantNRIC = applicantNRIC;
        this.project = project;
        this.message = message;
        this.response = "";
        this.status = EnquiryStatus.OPEN;
    }
    
    public void editMessage(String msg) {
        this.message = msg;
        System.out.println("Successfully edited the enquiry message!");
    }

    // public void view() {
    //     System.out.println("Enquiry details: ");
    //     System.out.println("ID: "+this.id);
    //     System.out.println("Applicant NRIC:" +this.applicantNRIC);
    //     // System.out.println("BTO Project Details");
    //     System.out.println("Message: "+this.message);
    //     System.out.println("Status: "+this.status);
    //     if (status==EnquiryStatus.CLOSED) System.out.println("Response:"+this.response);
    // }

    public void setReply(String resp) {
        this.response = resp;
        this.status = EnquiryStatus.CLOSED;
        System.out.println("Successfully responded to the enquiry.");
    }

    public String toString() {
        String str = "";
        str+= "Enquiry details: \n";
        str+= "ID: "+this.id+"\n";
        str+= "Applicant NRIC:" +this.applicantNRIC+"\n";
        str+= "BTO Project Details:"+this.project.getProjectName()+"\n";
        str+= "Message: "+this.message+"\n";
        str+= "Status: "+this.status+"\n";
        return str;
        // System.out.println("BTO Project Details");
    }

    public int getID() {
        return id;
    }
    public String getMessage() {
        return message;
    }
}