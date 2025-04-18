package entity.user;

import entity.project.BTOProject;
import enums.*;
import java.util.HashMap;
import java.util.Map;

public class Officer extends Applicant{
    private BTOProject assignedProject;
    private Map<String, BTOProject> registeredProjects;

    //hdb officer is a subset of applicant
    public Officer(String nric, String name, String password, int age, String maritalStatus) {
        super(nric, name, age, maritalStatus, password);    // Applicant constructor
        this.setUserRole(UserRole.OFFICER);
        assignedProject = null;
        registeredProjects = new HashMap<>();
    }

    public BTOProject getAssignedProject() {
        return assignedProject;
    }

    public void assignProject(BTOProject project) {
        this.assignedProject = project;
    }

    /*public void updateApplicantStatus(Applicant applicant, ApplicationStatus status){
        OfficerController.updateApplicantStatus(this, applicant, ApplicationStatus.BOOKED);
    }*/
    
    /*public void updateApplicantProfile(Applicant applicant){
        OfficerController.updateApplicantProfile(this, applicant);
    }*/
    
    /*public void retrieveApplicantApplication(Applicant applicant){
        OfficerController.retrieveApplicantApplication(this, applicant);
    }*/
    
    /*public void updateRemainingFlats(Applicant applicant){
        OfficerController.updateRemainingFlats(this, applicant);
    }*/
    
    /*public void generateReceipt(Applicant applicant){
        OfficerController.generateReceipt(this, applicant);
    }*/
    public void addRegisteredProject(BTOProject project) {
        registeredProjects.put(project.getProjectName(), project);
    }
    
    public Map<String, BTOProject> getRegisteredProjects() {
        return registeredProjects;
    }
    public boolean isAlreadyRegistered(String projectName) {
        return registeredProjects.containsKey(projectName);
    }
}
