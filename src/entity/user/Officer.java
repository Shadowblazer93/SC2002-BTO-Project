package entity.user;

import entity.project.BTOProject;
import entity.enquiry.Enquiry;
import enums.*;
import controller.ApplicationController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import entity.application.Application;
import entity.registration.Registration;
import java.time.LocalDate;
import java.util.Map;

public class Officer extends Applicant{
    private BTOProject assignedProject;
    private String username; // Declare username field

    //hdb officer is a subset of applicant
    public Officer(String nric, String name, String password, int age, String maritalStatus, BTOProject appliedProject, String applicationStatus, FlatType flatType, 
                   Enquiry[] Enquiries, int maxEnqID) {
        super(nric, name, age, maritalStatus, password);    // Applicant constructor
        this.setUserRole(UserRole.OFFICER);
        this.assignedProject = appliedProject;
        //super(nric, password, maxEnqID, applicationStatus, flatType, appliedProject, "Officer");
    }

    public BTOProject getAssignedProject() {
        return assignedProject;
    }

    /*public void applyForProject(BTOProject project) {
        // Check if this officer is already assigned to a project
        if (this.assignedProject != null) {
            System.out.println("You are already assigned to a project: " + assignedProject.getProjectName());
            return;
        }

        // Create registration
        RegistrationController registrationController = new RegistrationController();
        Registration registration = registrationController.createRegistration(this, project, LocalDate.now());
        // Apply to the given BTO project
        project.addRegistration(registration);  // Add registration to project
        registrationController.addRegistration(project.getProjectName(), registration);
        System.out.println("Application submitted to project: " + project.getProjectName());
    }*/

    
    public BTOProject viewHandledProject(){
        return this.assignedProject;
    }

    public void assignProject(BTOProject project) {
        this.assignedProject = project;
    }

    /*public void replyEnquiries(String enquiryID, String responseMessage) {
        // Find the enquiry based on ID
        Enquiry enquiry = assignedProject.getEnquiries().get(enquiryID);
        // If the enquiry is found, reply to it
        if (enquiry != null) {
            enquiry.reply(responseMessage);  // Set the response and update the status to CLOSED
            System.out.println("Response sent successfully.");
        } else {
            System.out.println("Enquiry with ID " + enquiryID + " not found.");
        }
    }*/
    
public void bookFlat(Applicant applicant, FlatType flatType) {
    // Check if the officer is assigned to a project
    if (assignedProject == null) {
        System.out.println("No project assigned to the officer.");
        return;
    }

    // Check if the applicant has already booked a flat
    if (applicant.getApplication() == null) {
        System.out.println("The applicant has not applied for any project.");
        return;
    }

    // Retrieve the project associated with the applicant's application
    BTOProject project = applicant.getApplication().getProject();
    if (project == null || !project.getProjectName().equals(assignedProject.getProjectName())) {
        System.out.println("The applicant's application is not linked to the officer's assigned project.");
        return;
    }

    // Check if the selected flat type is available
    Map<FlatType, Integer> unitCounts = project.getunitCounts();
    if (!unitCounts.containsKey(flatType) || unitCounts.get(flatType) <= 0) {
        System.out.println("The selected flat type is not available.");
        return;
    }

    // Book the flat
    unitCounts.put(flatType, unitCounts.get(flatType) - 1); // Decrease the count of available flats
    applicant.updateStatus("BOOKED"); // Update the applicant's status to "BOOKED"
    System.out.println("Flat successfully booked for applicant " + applicant.getName() + " in project " + project.getProjectName());
}

    public void viewProjectEnquiries() {    // Can use PrintEnquiries instead
    // Check if the officer is assigned to a project
        if (assignedProject == null) {
            System.out.println("No project assigned to the officer.");
            return;
        }

        // Get the list of all enquiries for the assigned project
        Enquiry[] enquiries = assignedProject.getEnquiries().values().toArray(new Enquiry[0]);
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
    public void updateApplicantStatus(Applicant applicant, ApplicationStatus status){
        ApplicationController.updateApplicantStatus(this, applicant, ApplicationStatus.BOOKED);
    }
    public void updateApplicantProfile(Applicant applicant){
        ApplicationController.updateApplicantProfile(this, applicant);
    }
    public void retrieveApplicantApplication(Applicant applicant){
        ApplicationController.retrieveApplicantApplication(this, applicant);
    }
    public void updateRemainingFlats(Applicant applicant){
        ApplicationController.updateRemainingFlats(this, applicant);
    }
    public void generateReceipt(Applicant applicant){
        ApplicationController.generateReceipt(this, applicant);
    }
}
