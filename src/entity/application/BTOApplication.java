package entity.application;

import entity.project.BTOProject;
import entity.user.Applicant;
import enums.ApplicationStatus;
import enums.FlatType;

public class BTOApplication {
    private Applicant applicant;
    private BTOProject project;
    private FlatType flatType;
    private ApplicationStatus status;
    private boolean withdrawal;    // Whether applicant has requested for withdrawal

    public BTOApplication(Applicant applicant, BTOProject project, FlatType flatType) {
        this.applicant = applicant;
        this.project = project;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.withdrawal = false;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public BTOProject getProject() {
        return project;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public boolean getWithdrawal() {
        return withdrawal;
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

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }
} 
