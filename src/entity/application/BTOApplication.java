package entity.application;

import enums.ApplicationStatus;

public class BTOApplication {
    private String applicantNRIC;
    private String projectID;
    private String flatType;
    private ApplicationStatus status;
    private boolean hasRequestedWithdrawal;

    public BTOApplication(String nric, String projectID, String flatType) {
        this.applicantNRIC = nric;
        this.projectID = projectID;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
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

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public boolean hasRequestedWithdrawal() {
        return hasRequestedWithdrawal;
    }

    public void requestWithdrawal() {
        this.hasRequestedWithdrawal = true;
    }

    public void cancelWithdrawal() {
        this.hasRequestedWithdrawal = false;
    }
} 
