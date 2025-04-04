package entity.user;

import entity.project.BTOProject;
import entity.enquiry.Enquiry;
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

    //hdb officer is a subset of applicant
    public Officer(String username, String password, String officerName, String officerNRIC, 
                   BTOProject appliedProject, String applicationStatus, String flatType, 
                   Enquiry[] Enquiries, int maxEnqID) {
        // Initialize the parent class (Applicant) with all necessary arguments
        super(appliedProject, applicationStatus, flatType, Enquiries, maxEnqID);
        
        // Initialize Officer-specific fields
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
    }
    
    public BTOProject viewHandledProject(){
        return this.assignedProject;
    }

    //register officer to a project
    public void assignToProject(BTOProject project){
        if (this.assignedProject != null) {
            System.out.println("Officer " + officerName + " is already assigned to project " + this.assignedProject.getProjectName());
        } else {
            this.assignedProject = project;
            project.assignOfficer(this);  // Assuming BTOProject has this method
            System.out.println("Officer " + officerName + " has been assigned to project " + project.getProjectName());
        }
    }

    private boolean hasAccessToApplication(Application application) {
        if (application == null) {
            return false;
        }
        
        return assignedProject != null && 
               application.getProject() != null && 
               application.getProject().getProjectName().equals(assignedProject.getProjectName());
    }
    public void replyEnquiries(int enquiryID, String responseMessage) {
        // Find the enquiry based on ID
        Enquiry enquiry = null;
        boolean found = false;

        // Look through all the enquiries in the project
        for (Enquiry enq : assignedProject.getEnquiries()) {
            if (enq.id == enquiryID) {
                enquiry = enq;
                found = true;
                break;
            }
        }

        // If the enquiry is found, reply to it
        if (found) {
            enquiry.reply(responseMessage);  // Set the response and update the status to CLOSED
            System.out.println("Response sent successfully.");
        } else {
            System.out.println("Enquiry with ID " + enquiryID + " not found.");
        }
    }
    
    public void viewProjectEnquiries() {
    // Check if the officer is assigned to a project
        if (assignedProject == null) {
            System.out.println("No project assigned to the officer.");
            return;
        }

        // Get the list of all enquiries for the assigned project
        Enquiry[] enquiries = assignedProject.getEnquiries();
        if (enquiries == null || enquiries.length == 0) {
            System.out.println("No enquiries for this project.");
        } else {
            System.out.println("Enquiries for project: " + assignedProject.getProjectName());
            for (Enquiry enq : enquiries) {
                // Directly use the 'view' method from Enquiry class to display the enquiry details
                enq.view();
                System.out.println("----------------------------");
            }
        }
    }


    public void updateRemainingFlats(Applicant applicant){
        String NRIC = applicant.getUserID();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different Project!");
        } else {
            FlatType flatType = application.getflatType();
            Map<FlatType,Integer> unitCounts = assignedProject.getunitCounts();
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
        Map<FlatType,Integer> unitCounts = project.getunitCounts();
        
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
            for (Map.Entry<FlatType, Integer> entry : unitCounts.entrySet()) {
                FlatType ProjectflatType = entry.getKey();
                int numRooms = ProjectflatType.getNumRooms();
                Integer units = entry.getValue();
                System.out.printf("%d-Room has %d units\n", numRooms, units);
            System.out.println("===========================");
            }
        }   
    }
}
