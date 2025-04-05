package entity.application;

import entity.project.BTOProject;
import enums.ApplicationStatus;
import enums.FlatType;

public class Application {
    private String applicantNRIC;
    private BTOProject project;
    private FlatType flatType;
    private ApplicationStatus status;
    
    // Constructor, getters, setters
    public Application(String applicantNRIC, BTOProject project, FlatType flatType, ApplicationStatus status){
        this.applicantNRIC = applicantNRIC;
        this.project = project;
        this.flatType = flatType;
        this.status = status;
    }
    public String getApplicantNRIC(){
        return applicantNRIC;
    }
    public BTOProject getProject(){
        return project;
    }
    public FlatType getflatType(){
        return flatType;
    }
    public ApplicationStatus getStatus(){
        return status;
    }
}
