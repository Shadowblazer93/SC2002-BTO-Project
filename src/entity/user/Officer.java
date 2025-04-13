package entity.user;

import entity.project.BTOProject;
import entity.enquiry.Enquiry;
import enums.FlatType;
import controller.ApplicationController;
import controller.RegistrationController;
import entity.application.Application;
import entity.registration.Registration;
import java.time.LocalDate;
import java.util.Map;

public class Officer extends Applicant{
    private BTOProject assignedProject;
    private String username; // Declare username field

    //hdb officer is a subset of applicant
    public Officer(String username, String password, BTOProject appliedProject, String applicationStatus, String flatType, 
                   Enquiry[] Enquiries, int maxEnqID) {
        super(username, password, maxEnqID, applicationStatus, flatType);
        this.username = username; // Initialize username field
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

    private boolean hasAccessToApplication(Application application) {
        if (application == null) {
            return false;
        }
        
        return assignedProject != null && 
               application.getProject() != null && 
               application.getProject().getProjectName().equals(assignedProject.getProjectName());
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


    public void updateRemainingFlats(Applicant applicant){
        String NRIC = applicant.getNRIC();
        Application application = (Application) ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different Project!");
        } else {
            FlatType flatType = application.getflatType();
            Map<FlatType,Integer> unitCounts = assignedProject.getunitCounts();
            unitCounts.put(flatType, unitCounts.get(flatType) - 1); // Can use editNumUnits in BTOProjectController
        }
    }

    public Application retrieveApplicantApplication(Applicant applicant) {
        String NRIC = applicant.getNRIC();
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
        String NRIC = applicant.getNRIC();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateStatus("BOOKED");
            System.out.println("Applicant status updated!");
        }
    }

    public void updateApplicantProfile(Applicant applicant){
        String NRIC = applicant.getNRIC();
        Application application = ApplicationController.getApplicationByNRIC(NRIC);
        FlatType flatType = application.getflatType();
        
        if (!hasAccessToApplication(application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateflatType(flatType);
            System.out.println("Applicant profile updated!");
        }
    }

    public void generateReceipt(Applicant applicant){
        String NRIC = applicant.getNRIC();
        String name = applicant.getName();
        int age = applicant.getAge();
        String maritalStatus = applicant.getMaritalStatus();
        FlatType flatType = applicant.getflatType();

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
