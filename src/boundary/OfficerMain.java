package boundary;

import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import controller.user.OfficerController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Applicant;
import entity.user.Officer;
import enums.ApplicationStatus;
import enums.FlatType; // Ensure FlatType is imported from the correct package
import java.time.LocalDate;
import java.util.*;
public class OfficerMain {
    PrintProjects projectPrinter = new PrintProjects();
    EnquiryController enquiryController = new EnquiryController();
    BTOProjectController projectController = new BTOProjectController();
    RegistrationController registrationController = new RegistrationController();
    OfficerController officerController = new OfficerController();
    ApplicantController applicantController = new ApplicantController();
    public static void main(String[] args) {

    }
    public OfficerMain(Officer officer, Scanner sc){
        boolean running = true;
        while (running) {
            System.out.printf("""
                    Hi %s
                    ------------------------------
                        HDB Officer Main Page
                    ------------------------------
                    1. View and manage flat bookings
                    2. View and reply to enquiries
                    3. Update applicant status
                    4. Generate receipt for bookings
                    5. Register for project
                    6. Book flat for applicant
                    7. Logout
                    ------------------------------
                    """, officer.getName());
            
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> manageFlatBookings(officer);
                case 2 -> manageEnquiries(sc, officer);
                case 3 -> updateApplicantStatus(sc, officer);
                case 4 -> generateReceipt(sc, officer);
                case 5 -> registerProject(sc, officer);
                case 6 -> bookFlat(sc, officer);
                case 7 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageFlatBookings(Officer officer){
        System.out.println("Managing flat bookings!");
        BTOProject project = officer.viewHandledProject();
        //print proj
        System.out.print(project);
    }
    private void manageEnquiries(Scanner sc, Officer officer) {
        EnquiryMain enquiryMain = new EnquiryMain();
        
        // Use the extracted method for viewing enquiries
        enquiryMain.viewProjectEnquiries(officer);
        
        // Continue with reply functionality
        System.out.print("Enter Enquiry ID to reply: ");
        String enquiryId = sc.nextLine();
        System.out.print("Enter reply: ");
        String reply = sc.nextLine();
        enquiryController.replyEnquiry(officer, enquiryId, reply);
    }

    private void updateApplicantStatus(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant: ");
        String NRIC = sc.nextLine();
        Applicant applicant = applicantController.getApplicant(NRIC);
        
        OfficerController.updateApplicantStatus(officer, applicant, ApplicationStatus.BOOKED);
    }

    private void registerProject(Scanner sc, Officer officer) {
        // Check if eligible to register
        String message = officerController.canRegisterProject(officer);
        if (message.equals("success")) {
            System.out.println(message);
            return;
        }
        // View list of projects
        projectPrinter.printMap(projectController.getAllProjects());
        System.out.print("Project to register: ");
        String projectName = sc.nextLine();
        BTOProject project = projectController.getAllProjects().get(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        // Create registration
        Registration registration = registrationController.createRegistration(officer, project, LocalDate.now());
        // Apply to the given BTO project
        project.addRegistration(registration);  // Add registration to project
        System.out.println("Application submitted to project: " + project.getProjectName());
    }
}





