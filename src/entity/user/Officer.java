package entity.user;

import entity.project.BTOProject;
import enums.FlatType;
import controller.ApplicationController;
import entity.application.Application;
import java.util.HashMap;
import java.util.Map;

public class Officer extends Applicant{
    // No need for these since UserID is NRIC and user's name is officer's name
    private String officerName;
    private String officerNRIC;
    private BTOProject assignedProject;

    public Officer (String username, String password, String officerName, String officerNRIC){
        super(username, password);
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
    }
    
    public BTOProject viewHandledProject(){
        return this.assignedProject;
    }

    //register officer to a project
    public void assignToProject(BTOProject project){
        this.assignedProject = project;
        project.assignOfficer(this);
    }

    private boolean hasAccessToApplication(Application application) {
        if (application == null) {
            return false;
        }
        
        return assignedProject != null && 
               application.getProject() != null && 
               application.getProject().getProjectName().equals(assignedProject.getProjectName());
    }

    public void updateRemainingFlats(Applicant applicant){
        String NRIC = applicant.getUserID();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different Project!");
        } else {
            String flatType = application.getflatType();
            Map<String,Integer> unitCounts = assignedProject.getunitCounts();
            unitCounts.put(flatType, unitCounts.get(flatType) - 1);
        }
    }

    public Application retrieveApplicantApplication(Applicant applicant) {
        String NRIC = applicant.getUserID();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (application == null) {
            System.out.println("No application found for NRIC: " + NRIC);
            return null;
        }
        
        if (hasAccessToApplication(application)) {
            return application;
        } else {
            System.out.println("Officer does not have access to this application!");
            return null;
        }
    }

    public void updateApplicantStatus(Applicant applicant) {
        String NRIC = applicant.getUserID();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateStatus("BOOKED");
            System.out.println("Applicant status updated!");
        }
    }

    public void updateApplicantProfile(Applicant applicant){
        String NRIC = applicant.getUserID();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        String flatType = application.getflatType();
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateflatType(flatType);
            System.out.println("Applicant profile updated!");
        }
    }

    public void generateReceipt(Applicant applicant){
        String NRIC = applicant.getUserID();
        String name = applicant.getName();
        int age = applicant.getAge();
        String maritalStatus = applicant.getMaritalStatus();
        String flatType = applicant.getflatType();

        Application application = ApplicationController.getApplicationByNRIC(NRIC);

        BTOProject project = application.getProject();
        String projectName = project.getProjectName();
        String neighbourhood = project.getNeighbourhood();
        Map<String,Integer> unitCounts = project.getunitCounts();
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer does not have access to this application!");
        } else {
            System.out.println("===== BOOKING RECEIPT =====");
            System.out.println("Applicant name: " + name);
            System.out.println("NRIC: " + NRIC);
            System.out.println("Age: " + age);
            System.out.println("Marital status: " + maritalStatus);
            System.out.println("Flat type booked: " + flatType);
            System.out.println("Project name: " + projectName);
            System.out.println("Neighbourhood: " + neighbourhood);
            for (Map.Entry<String, Integer> entry : unitCounts.entrySet()) {
                String ProjectflatType = entry.getKey();
                Integer units = entry.getValue();
                System.out.println(ProjectflatType + " has " + units + " units.");
            System.out.println("===========================");
    }
}
}