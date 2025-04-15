package boundary;

import controller.BTOProjectController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import controller.user.OfficerController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Applicant;
import entity.user.Officer;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.RegistrationStatus; // Ensure FlatType is imported from the correct package
import java.time.LocalDate;
import java.util.*;
public class OfficerMain {
    PrintProjects projectPrinter = new PrintProjects();
    OfficerController officerController = new OfficerController();
    ApplicantController applicantController = new ApplicantController();
    EnquiryMain enquiryMain = new EnquiryMain();
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
                    1. View Registration status
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
                case 1 -> viewRegistrationStatus(officer);
                case 2 -> enquiryMain.displayMenuOfficer(sc, officer);
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
   

    private void viewRegistrationStatus(Officer officer) {
        Map<String, BTOProject> registered = officer.getRegisteredProjects();
        
        if (registered.isEmpty()) {
            System.out.println("You have not registered for any projects.");
            return;
        }
    
        System.out.println("Your project registrations:");
        for (BTOProject project : registered.values()) {
            String projectName = project.getProjectName();
            String status = "Pending";
            
            // Check if officer is approved
            if (officer.viewHandledProject() != null &&
                officer.viewHandledProject().getProjectName().equals(projectName)) {
                status = "Approved";
            }
    
            System.out.printf("- %s: %s\n", projectName, status);
        }
    }
    

    private void updateApplicantStatus(Scanner sc, Officer officer) {
        sc.nextLine(); // Consume the leftover newline from previous input
        
        System.out.print("Enter the NRIC of the applicant: ");
        String NRIC = sc.nextLine();
        
        Applicant applicant = applicantController.getApplicant(NRIC);
        if (applicant == null) {
            System.out.println("Applicant not found with NRIC: " + NRIC);
            return;
        }
        
        OfficerController.updateApplicantStatus(officer, applicant, ApplicationStatus.BOOKED);
    }

    private void generateReceipt(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant to generate receipt for: ");
        String nric = sc.next();
        sc.nextLine();
        
        Applicant applicant = applicantController.getApplicant(nric);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }
        officer.generateReceipt(applicant);
    }

    private void registerProject(Scanner sc, Officer officer) {
        // Check if eligible to register
        String message = officerController.registerProject(officer);
        if (!message.equals("success")) {
            System.out.println(message);
            return; // Only return early if validation fails
        }
        
        // View list of projects
        System.out.println("Available projects you can register for:");
        projectPrinter.printMap(BTOProjectController.getAllProjects());
        
        sc.nextLine(); // Clear buffer after previous nextInt()
        System.out.print("Enter project name to register: ");
        String projectName = sc.nextLine();
        
        BTOProject project = BTOProjectController.getAllProjects().get(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        // Create registration
        Registration registration = RegistrationController.createRegistration(RegistrationController.getRegistrationCount(), officer, project.getProjectName(), LocalDate.now(), RegistrationStatus.PENDING);
        
        // Apply to the given BTO project
        project.addRegistration(registration);  // Add registration to project
        System.out.println("Registration submitted to project: " + project.getProjectName());
        System.out.println("Awaiting manager approval.");
    }

    private void bookFlat(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant: ");
        String nric = sc.next();
        sc.nextLine(); // Clear buffer
        
        Applicant applicant = applicantController.getApplicant(nric);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }
        
        System.out.println("Available flat types:");
        for (FlatType type : FlatType.values()) {
            System.out.println(type.getNumRooms() + "-Room");
        }
        
        System.out.print("Enter flat type (2 or 3): ");
        int roomNumber = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        FlatType flatType = FlatType.getFlatType(roomNumber);
        if (flatType == null) {
            System.out.println("Invalid flat type.");
            return;
        }
        
        OfficerController.bookFlat(officer, applicant, flatType);
    }
}





