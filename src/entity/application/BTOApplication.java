package entity.application;

public class BTOApplication {
    private String applicantNRIC;
    private String projectID;
    private String flatType;
    private String status; // Pending, Successful, etc.
    private boolean hasRequestedWithdrawal;

    public BTOApplication(String nric, String projectID, String flatType) {
        this.applicantNRIC = nric;
        this.projectID = projectID;
        this.flatType = flatType;
        this.status = "Pending";
        this.hasRequestedWithdrawal = false;
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getFlatType() {
        return flatType;
    }

    public void requestWithdrawal() {
        this.hasRequestedWithdrawal = true;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public String getStatus() {
        return status;
    }

    public boolean hasRequestedWithdrawal() {
        return hasRequestedWithdrawal;
    }
} 

