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

/**
 * The {@code OfficerMain} class provides the main menu and interactions
 * for an HDB officer user. It allows officers to view project details,
 * manage applications and enquiries, register for projects, and view registration statuses.
 */
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

    /**
     * Constructs an {@code OfficerMain} object and initializes dependencies.
     *
     * @param applicantService Service for applicant-related operations
     * @param officerService Service for officer-related operations
     * @param applicationService Service for application handling
     * @param enquiryService Service for enquiry processing
     * @param projectService Service for managing BTO projects
     * @param registrationService Service for officer-project registrations
     */
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

    /**
     * Displays the main menu for an officer and handles input-based navigation.
     *
     * @param officer The currently logged-in officer
     * @param sc Scanner for reading user input
     */
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
                    sc.next();
                }
            }

            switch (choice) {
                case 1 -> viewProjectDetails(officer);
                case 2 -> applicationMain.displayMenuOfficer(sc, officer);
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

    /**
     * Displays the project details of the project assigned to the officer.
     *
     * @param officer The officer whose assigned project is to be viewed
     */
    private void viewProjectDetails(Officer officer) {
        BTOProject project = officer.getAssignedProject();
        if (project == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
        System.out.println(project);
    }

    /**
     * Displays the registration status of the officer across all BTO projects.
     *
     * @param officer The officer whose registration statuses are to be viewed
     */
    private void viewRegistrationStatus(Officer officer) {
        BTOProject assignedProject = officer.getAssignedProject();
        boolean hasRegistrations = !officer.getRegisteredProjects().isEmpty() || assignedProject != null;
        
        if (!hasRegistrations) {
            System.out.println("You have not registered for any projects.");
            return;
        }
        
        System.out.println(defColor.YELLOW + "Viewing registration status!");

        if (assignedProject != null) {
            System.out.println("Project Name: " + assignedProject.getProjectName());
            System.out.println("Registration Status: APPROVED");
            System.out.println("You are currently assigned to this project");
        }

        for (Map.Entry<String, BTOProject> entry : officer.getRegisteredProjects().entrySet()) {
            String projectName = entry.getKey();
            BTOProject project = entry.getValue();
            
            if (assignedProject != null && projectName.equals(assignedProject.getProjectName())) {
                continue;
            }

            System.out.println("\nProject Name: " + projectName);

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

    /**
     * Allows the officer to register for a BTO project if eligible.
     *
     * @param sc Scanner for user input
     * @param officer The officer attempting to register
     */
    private void registerProject(Scanner sc, Officer officer) {
        System.out.println("Available projects you can register for:");
        projectPrinter.printMap(projectService.getAllProjects());
        sc.nextLine();
        System.out.print("Enter project name to register: ");
        String projectName = sc.nextLine();
        BTOProject project = projectService.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        String message = officerService.registerProject(officer, project);
        if (!message.equals("success")) {
            System.out.println(message);
            return;
        }

        registrationService.registerProject(officer, project);

        System.out.println("Registration submitted to project: " + project.getProjectName());
        System.out.println("Awaiting manager approval.");
    }
}
