package entity.application;

import entity.user.Applicant;
import enums.ApplicationStatus;
import enums.FlatType;

public class BTOApplication {
    private int ID;
    private Applicant applicant;
    private String projectName;
    private FlatType flatType;
    private ApplicationStatus status;
    private boolean withdrawal;    // Whether applicant has requested for withdrawal

    public BTOApplication(int ID, Applicant applicant, String projectName, FlatType flatType, ApplicationStatus status, boolean withdrawal) {
        this.ID = ID;
        this.applicant = applicant;
        this.projectName = projectName;
        this.flatType = flatType;
        this.status = status;
        this.withdrawal = withdrawal;
    }

    public int getID() {
        return ID;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public String getProjectName() {
        return projectName;
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
