package boundary;

import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import controller.user.OfficerController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Officer;
import java.time.LocalDate;
import java.util.*;
public class OfficerMain {
    EnquiryMain enquiryMain = new EnquiryMain();
    ApplicationMain applicationMain = new ApplicationMain();
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
                    2. Manage enquiries
                    3. Manage applications
                    4. Register for project
                    5. Logout
                    ------------------------------
                    """, officer.getName());
            
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> manageFlatBookings(officer);
                case 2 -> enquiryMain.displayMenuOfficer(sc, officer);
                case 3 -> applicationMain.displayMenuOfficer(sc, officer);
                case 4 -> registerProject(sc, officer);
                case 5 -> {
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





