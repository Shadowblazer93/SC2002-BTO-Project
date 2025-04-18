package boundary;

import controller.BTOProjectController;
import controller.RegistrationController;
import controller.user.OfficerController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Officer;
import enums.defColor;
import java.util.*;
import printer.PrintBTOProjects;

public class OfficerMain {
    PrintBTOProjects projectPrinter = new PrintBTOProjects();
    EnquiryMain enquiryMain = new EnquiryMain();
    ApplicationMain applicationMain = new ApplicationMain();
    ApplicantMain applicantMain = new ApplicantMain();

    public void displayMenu(Officer officer, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.printf(defColor.PURPLE+"""
                
                    Hi %s
                    ==============================
                        HDB Officer Main Page
                    ------------------------------
                    """ + defColor.BLUE + """
                    1. View project details
                    2. Manage applications
                    3. Manage enquiries
                    4. View Registration status
                    5. Register for project
                    6. Apply as applicant
                    7. Logout
                    """ + defColor.PURPLE + 
                    "==============================\n" + defColor.RESET, officer.getName());
            
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
                case 1 -> viewProjectDetails(officer);
                // Book flat, Generate receipt,
                case 2 -> applicationMain.displayMenuOfficer(sc, officer);
                // View enquiries, reply enquiries
                case 3 -> enquiryMain.displayMenuOfficer(sc, officer);
                case 4 -> viewRegistrationStatus(officer);
                case 5 -> registerProject(sc, officer);
                case 6 -> applicantMain.displayMenu(officer, sc);
                case 7 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewProjectDetails(Officer officer) {
        BTOProject project = officer.getAssignedProject();
        if (project == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
        System.out.println(project);
    }

    private void viewRegistrationStatus(Officer officer) {
        if (officer.getRegisteredProjects().isEmpty()) {
            System.out.println("You have not registered for any projects.");
            return;
        }
        System.out.println(defColor.YELLOW + "Viewing registration status!");
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
        System.out.println(defColor.RESET);
    }
    
    private void registerProject(Scanner sc, Officer officer) {
        // 1. View list of projects
        System.out.println("Available projects you can register for:");
        projectPrinter.printMap(BTOProjectController.getAllProjects());
        // 2. Get selected project
        sc.nextLine();
        System.out.print("Enter project name to register: ");
        String projectName = sc.nextLine();
        BTOProject project = BTOProjectController.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        // 3. Check if eligible to register
        String message = OfficerController.registerProject(officer, project);
        if (!message.equals("success")) {
            System.out.println(message);
            return;
        }
        // 4. Create registration
        RegistrationController.registerProject(officer, project);
    
        System.out.println("Registration submitted to project: " + project.getProjectName());
        System.out.println("Awaiting manager approval.");
    }
    

    /*private void bookFlat(Scanner sc, Officer officer) {
        // View applications to approve booking
        BTOProject project = officer.getAssignedProject();
        if (project == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
        Map<String, BTOApplication> applications = project.getApplications();
        if (applications.isEmpty()) {
            System.out.println("No applications to approve booking for.");
            return;
        }

        System.out.print("Enter the NRIC of the applicant: ");
        String nric = sc.next();

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
    }*/
}





