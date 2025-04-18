package entity.enquiry;

import enums.EnquiryStatus;

public class Enquiry {
    private int id;
    private final String applicantNRIC;
    private final String projectName;
    private String message;
    private String response;
    private EnquiryStatus status;

    public Enquiry(int id, String applicantNRIC, String projectName, String message, String response, EnquiryStatus status) {
        this.id = id;
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.message = message;
        this.response = response;
        this.status = status;
    }

    public Enquiry(int id, String applicantNRIC, String projectName, String message, String response, String status) {
        this.id = id;
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.message = message;
        this.response = response;

        if (status.equalsIgnoreCase("OPEN")) {this.status = EnquiryStatus.OPEN;}
        else {this.status = EnquiryStatus.CLOSED;}
    }

    public int getID() {
        return id;
    }
    public String getApplicantNRIC() {
        return applicantNRIC;
    }
    public String getMessage() {
        return message;
    }
    public String getProjectName() {
        return projectName;
    }
    public EnquiryStatus getStatus() {
        return status;
    }
    public String getResponse() {
        if (response == null) {
            return "No response yet.";
        }
        return response;
    }

    public void setReply(String resp) {
        this.response = resp;
        this.status = EnquiryStatus.CLOSED;
        System.out.println("Successfully responded to the enquiry.");
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    @Override
    public String toString() {
        String str = "";
        str+= "Enquiry details: \n";
        str+= "ID: "+this.id+"\n";
        str+= "Applicant NRIC:" +this.applicantNRIC+"\n";
        str+= "BTO Project Details:"+this.projectName+"\n";
        str+= "Message: "+this.message+"\n";
        str+= "Status: "+this.status+"\n";
        return str;
        // System.out.println("BTO Project Details");
    }
}