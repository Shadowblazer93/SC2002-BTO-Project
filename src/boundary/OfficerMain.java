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
import enums.FlatType;
import java.util.*;

public class OfficerMain {
    PrintProjects projectPrinter = new PrintProjects();
    EnquiryMain enquiryMain = new EnquiryMain();
    public static void main(String[] args) {

    }
    public OfficerMain(Officer officer, Scanner sc) {
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
            
            int choice = 0;
            boolean validInput = false;
            
            while (!validInput) {
                try {
                    System.out.print("Choose an option: ");
                    choice = sc.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    // Clear the invalid input
                    sc.next();
                }
            }

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
        System.out.println("Viewing registration status!");
        officer.getRegisteredProjects().forEach((projectName, project) -> {
            System.out.println("Project Name: " + projectName);
            
            //get the registration status of the officer
            Registration registration = project.getRegistrations().get(officer.getNRIC());
            if (registration != null) {
                System.out.println("Registration Status: " + registration.getStatus());
            } else {
                System.out.println("Registration Status: Not Found");
            }
        });
    }

    //NOT USED, IF NOT NEEDED CAN DELETE
    private void manageEnquiries(Scanner sc, Officer officer) {
        EnquiryMain enquiryMain = new EnquiryMain();
        EnquiryController enquiryController = new EnquiryController(); // or use class-level if available
    
        BTOProject project = officer.getAssignedProject();
        if (project == null) {
            System.out.println("No project assigned to the officer.");
            return; 
        }
    
        // Show all enquiries
        enquiryMain.viewProjectEnquiries(officer);
    
        // Prompt for enquiry ID and reply
        System.out.print("Enter Enquiry ID to reply: ");
        int enquiryId;
        try {
            enquiryId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Enquiry ID. Must be a number.");
            return;
        }
    
        System.out.print("Enter reply: ");
        String reply = sc.nextLine();
    
        // Send reply using project and ID
        enquiryController.replyEnquiry(project, enquiryId, reply);
    }
    

    private void updateApplicantStatus(Scanner sc, Officer officer) {
        sc.nextLine();
        
        System.out.print("Enter the NRIC of the applicant: ");
        String NRIC = sc.nextLine();
        
        Applicant applicant = ApplicantController.getApplicant(NRIC);
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
        
        Applicant applicant = ApplicantController.getApplicant(nric);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }
        officer.generateReceipt(applicant);
    }

    private void registerProject(Scanner sc, Officer officer) {
        // Check if eligible to register
        String message = OfficerController.registerProject(officer);
        if (!message.equals("success")) {
            System.out.println(message);
            return; // Only return early if validation fails
        }
        
        // View list of projects
        System.out.println("Available projects you can register for:");
        projectPrinter.printMap(BTOProjectController.getAllProjects());
        
        sc.nextLine();
        System.out.print("Enter project name to register: ");
        String projectName = sc.nextLine();
        
        BTOProject project = BTOProjectController.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        // Create registration
        Registration registration = RegistrationController.registerProject(officer, project);
        
        // Apply to the given BTO project
        project.addRegistration(registration);  // Add registration to project
        System.out.println("Registration submitted to project: " + project.getProjectName());
        System.out.println("Awaiting manager approval.");
    }

    private void bookFlat(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant: ");
        String nric = sc.next();
        // Clear buffer
        sc.nextLine();
        
        Applicant applicant = ApplicantController.getApplicant(nric);
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
        // Clear buffer
        sc.nextLine();
        
        FlatType flatType = FlatType.getFlatType(roomNumber);
        if (flatType == null) {
            System.out.println("Invalid flat type.");
            return;
        }
        
        OfficerController.bookFlat(officer, applicant, flatType);
    }
}





