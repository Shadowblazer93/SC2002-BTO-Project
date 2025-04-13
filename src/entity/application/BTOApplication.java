package entity.application;

import enums.ApplicationStatus;
import enums.FlatType;
import entity.project.BTOProject;

public class BTOApplication {
    private String applicantNRIC;
    private BTOProject project;
    private FlatType flatType;
    private ApplicationStatus status;
    private boolean hasRequestedWithdrawal;

    public BTOApplication(String nric, BTOProject project, FlatType flatType) {
        this.applicantNRIC = nric;
        this.project = project;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.hasRequestedWithdrawal = false;
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public BTOProject getProject() {
        return project;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
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
