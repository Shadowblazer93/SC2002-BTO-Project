package entity.enquiry;

import enums.EnquiryStatus;

/**
 * Enquiry class submitted by an applicant regarding a BTO project.
 * An Enquiry contains details such as the applicant's NRIC, project name, message, response, and status.
 */
public class Enquiry {
    private int id;
    private final String applicantNRIC;
    private final String projectName;
    private String message;
    private String response;
    private EnquiryStatus status;

    /**
     * Constructs an Enquiry object with the specified details.
     *
     * @param id            unique ID of the enquiry.
     * @param applicantNRIC NRIC of the applicant who submitted the enquiry.
     * @param projectName   name of the BTO project the enquiry is about.
     * @param message       message of the enquiry.
     * @param response      response to the enquiry (if any).
     * @param status        status of the enquiry (OPEN or CLOSED).
     */
    public Enquiry(int id, String applicantNRIC, String projectName, String message, String response, EnquiryStatus status) {
        this.id = id;
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.message = message;
        this.response = response;
        this.status = status;
    }

    /**
     * Constructs an Enquiry object with the specified details, using a string to determine the status.
     *
     * @param id            unique ID of the enquiry.
     * @param applicantNRIC NRIC of the applicant who submitted the enquiry.
     * @param projectName   name of the BTO project the enquiry is about.
     * @param message       message of the enquiry.
     * @param response      response to the enquiry (if any).
     * @param status        status of the enquiry as a string ("OPEN" or "CLOSED").
     */
    public Enquiry(int id, String applicantNRIC, String projectName, String message, String response, String status) {
        this.id = id;
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.message = message;
        this.response = response;

        if (status.equalsIgnoreCase("OPEN")) {
            this.status = EnquiryStatus.OPEN;
        } else {
            this.status = EnquiryStatus.CLOSED;
        }
    }

    /**
     * Gets the unique ID of the enquiry.
     *
     * @return The ID of the enquiry.
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the NRIC of the applicant who submitted the enquiry.
     *
     * @return The NRIC of the applicant.
     */
    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    /**
     * Gets the message of the enquiry.
     *
     * @return The message of the enquiry.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the name of the BTO project the enquiry is about.
     *
     * @return The name of the BTO project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the status of the enquiry.
     *
     * @return The status of the enquiry (OPEN or CLOSED).
     */
    public EnquiryStatus getStatus() {
        return status;
    }

    /**
     * Gets the response to the enquiry.
     *
     * @return The response to the enquiry, or "No response yet." if no response exists.
     */
    public String getResponse() {
        if (response == null) {
            return "No response yet.";
        }
        return response;
    }

    /**
     * Sets the response to the enquiry and updates the status to CLOSED.
     *
     * @param resp The response to the enquiry.
     */
    public void setReply(String resp) {
        this.response = resp;
        this.status = EnquiryStatus.CLOSED;
        System.out.println("Successfully responded to the enquiry.");
    }

    /**
     * Updates the message of the enquiry.
     *
     * @param msg The new message for the enquiry.
     */
    public void setMessage(String msg) {
        this.message = msg;
    }

    /**
     * Returns a string representation of the enquiry, including its details.
     *
     * @return A string representation of the enquiry.
     */
    @Override
    public String toString() {
        String str = "";
        str += "Enquiry details: \n";
        str += "ID: " + this.id + "\n";
        str += "Applicant NRIC: " + this.applicantNRIC + "\n";
        str += "BTO Project Details: " + this.projectName + "\n";
        str += "Message: " + this.message + "\n";
        str += "Status: " + this.status + "\n";
        return str;
    }
}