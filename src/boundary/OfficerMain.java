package boundary;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Officer;
import enums.defColor;
import interfaces.IApplicantService;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IOfficerService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;
import interfaces.IUserMain;
import java.util.*;
import printer.PrintBTOProjects;

public class OfficerMain implements IUserMain<Officer> {
    PrintBTOProjects projectPrinter = new PrintBTOProjects();
    EnquiryMain enquiryMain;
    ApplicationMain applicationMain;
    ApplicantMain applicantMain;

    private final IApplicantService applicantService;
    private final IOfficerService officerService;
    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;
    private final IRegistrationService registrationService;

    public OfficerMain(IApplicantService applicantService, IOfficerService officerService, IApplicationService applicationService, IEnquiryService enquiryService, 
                        IProjectService projectService, IRegistrationService registrationService) {
        this.applicantService = applicantService;
        this.officerService = officerService;
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.registrationService = registrationService;

        this.applicationMain = new ApplicationMain(applicantService, applicationService, projectService);
        this.enquiryMain = new EnquiryMain(enquiryService);
        this.applicantMain = new ApplicantMain(applicationService, enquiryService, projectService);
        
    }

    @Override
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
        BTOProject assignedProject = officer.getAssignedProject();
        boolean hasRegistrations = !officer.getRegisteredProjects().isEmpty() || assignedProject != null;
        
        if (!hasRegistrations) {
            System.out.println("You have not registered for any projects.");
            return;
        }
        
        System.out.println(defColor.YELLOW + "Viewing registration status!");
        
        // First check if officer is assigned to a project (which means approved)
        if (assignedProject != null) {
            System.out.println("Project Name: " + assignedProject.getProjectName());
            System.out.println("Registration Status: APPROVED");
            System.out.println("You are currently assigned to this project");
        }
        
        // Check other registered projects (pending or rejected)
        for (Map.Entry<String, BTOProject> entry : officer.getRegisteredProjects().entrySet()) {
            String projectName = entry.getKey();
            BTOProject project = entry.getValue();
            
            // Skip the project the officer is already assigned to
            if (assignedProject != null && projectName.equals(assignedProject.getProjectName())) {
                continue;
            }
            
            System.out.println("\nProject Name: " + projectName);
            
            // Look for registration in project's registration list
            boolean found = false;
            Map<String, List<Registration>> allRegistrations = registrationService.getAllRegistrations();
            List<Registration> projectRegistrations = allRegistrations.get(projectName);
            
            if (projectRegistrations != null) {
                for (Registration reg : projectRegistrations) {
                    if (reg.getOfficer().getNRIC().equals(officer.getNRIC())) {
                        System.out.println("Registration Status: " + reg.getStatus());
                        found = true;
                        break;
                    }
                }
            }
            
            if (!found) {
                System.out.println("Registration Status: Not Found");
            }
        }
        
        System.out.println(defColor.RESET);
    }
    
    private void registerProject(Scanner sc, Officer officer) {
        // 1. View list of projects
        System.out.println("Available projects you can register for:");
        projectPrinter.printMap(projectService.getAllProjects());
        // 2. Get selected project
        sc.nextLine();
        System.out.print("Enter project name to register: ");
        String projectName = sc.nextLine();
        BTOProject project = projectService.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        // 3. Check if eligible to register
        String message = officerService.registerProject(officer, project);
        if (!message.equals("success")) {
            System.out.println(message);
            return;
        }
        // 4. Create registration
        registrationService.registerProject(officer, project);
    
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





