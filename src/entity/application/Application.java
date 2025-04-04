package entity.application;

import entity.project.BTOProject;
import entity.user.Applicant;
import enums.ApplicationStatus;

public class Application {
    private String applicantNRIC;
    private BTOProject project;
    private String flatType;
    private ApplicationStatus status;
    
    // Constructor, getters, setters
    public Application(String applicantNRIC, BTOProject project, String flatType, ApplicationStatus status){
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
    public String getflatType(){
        return flatType;
    }
    public ApplicationStatus getStatus(){
        return status;
    }
}
