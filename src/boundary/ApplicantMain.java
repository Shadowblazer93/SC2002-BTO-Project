package boundary;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.ApplicationStatus;
import enums.EnquiryStatus;
import enums.FlatType;
import enums.defColor;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IProjectService;
import interfaces.IUserMain;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import printer.PrintBTOProjects;
import printer.PrintEnquiries;
import util.Filter;

/**
 * The ApplicantMain class provides the main interface for applicants to interact with the BTO system.
 * It allows applicants to view projects, apply for projects, manage enquiries, and book flats.
 */
public class ApplicantMain implements IUserMain<Applicant> {
    PrintBTOProjects projectPrinter = new PrintBTOProjects();
    PrintEnquiries enquiryPrinter = new PrintEnquiries();

    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;

    /**
     * Constructs an ApplicantMain object with the specified services.
     *
     * @param applicationService The service for managing applications.
     * @param enquiryService     The service for managing enquiries.
     * @param projectService     The service for managing projects.
     */
    public ApplicantMain(IApplicationService applicationService, IEnquiryService enquiryService, 
                         IProjectService projectService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
    }

    /**
     * Display  main menu for the applicant and handles user input.
     *
     * @param applicant The applicant interacting with the system.
     * @param sc        The scanner for reading user input.
     */
    @Override
    public void displayMenu(Applicant applicant, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.printf(defColor.PURPLE+"""
                    
                    Hi %s
                    ==============================
                          Applicant Main Page
                    ------------------------------
                    """ + defColor.BLUE + """
                    1. View project list
                    2. Apply to project
                    3. View Applied project
                    4. Book Flat
                    5. Withdraw from Project
                    6. View my enquiries
                    7. Submit Enquiry
                    8. Edit Enquiry
                    9. Delete Enquiry
                    10. Logout
                    """ + defColor.PURPLE + 
                    "==============================\n" + defColor.RESET, applicant.getName());
            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                }
            }

            switch (choice) {
                case 1 -> viewProjectList(applicant);
                case 2 -> applyProject(sc, applicant);
                case 3 -> viewAppliedProject(applicant);
                case 4 -> bookFlat(applicant);
                case 5 -> withdrawProject(applicant);
                case 6 -> viewEnquiries(applicant);
                case 7 -> submitEnquiry(sc, applicant);
                case 8 -> editEnquiry(sc, applicant);
                case 9 -> deleteEnquiry(sc, applicant);
                case 10 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Unknown choice!");
            }
        }
    }

    /**
     * Displays a list of projects available to the applicant.
     *
     * @param applicant The applicant viewing the project list.
     * @return True if projects are available, false otherwise.
     */
    private boolean viewProjectList(Applicant applicant) {
        List<BTOProject> visibleProjects = Filter.filterUserGroupProjects(projectService.getAllProjects(), applicant);
        if (visibleProjects == null || visibleProjects.isEmpty()) {
            System.out.println("No projects available.");
            return false;
        }
        projectPrinter.printList(visibleProjects, applicant);
        return true;
    }

    /**
     * Displays the details of the applicant's current application.
     *
     * @param applicant The applicant viewing their application.
     */
    private void viewAppliedProject(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        if (application==null) {
            System.out.println("You have not applied to any project.");
            return;
        }
        BTOProject project = projectService.getAllProjects().get(application.getProjectName());
        if (!project.getVisibility()) {
            System.out.println("Project details are no longer visible.");
        }
        System.out.println("Details of your application:");
        System.out.println(defColor.YELLOW + application);
    }

    /**
     * Allows the applicant to submit an enquiry about a project.
     *
     * @param sc        The scanner for reading user input.
     * @param applicant The applicant submitting the enquiry.
     */
    private void submitEnquiry(Scanner sc, Applicant applicant) {
        // select project to enquire about
        if (!viewProjectList(applicant)) {
            return;
        }
        System.out.print("Project for enquiry: ");
        String projectName = getValidStringInput(sc);
        BTOProject project = projectService.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        // Enquiry message
        System.out.print("Enter enquiry message: ");
        String msg = getValidStringInput(sc);
        enquiryService.submitEnquiry(applicant, project, msg);
        System.out.println("Enquiry submitted successfully!");
    }

    /**
     * Displays all enquiries submitted by the applicant.
     *
     * @param applicant The applicant viewing their enquiries.
     * @return A map of enquiries submitted by the applicant, or null if no enquiries exist.
     */
    private Map<Integer, Enquiry> viewEnquiries(Applicant applicant) {
        Map<Integer, Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("You have not submitted any enquiries.");
            return null;
        }
        enquiryPrinter.printMap(enquiries);
        return enquiries;
    }

    /**
     * Allows the applicant to delete an enquiry they have submitted.
     *
     * @param sc        The scanner for reading user input.
     * @param applicant The applicant deleting the enquiry.
     */
    private void deleteEnquiry(Scanner sc, Applicant applicant) {
        // Print enquiries submitted
        if (viewEnquiries(applicant) == null) {
            return;
        }
        // Select enquiry
        System.out.print("Enter submitted enquiry ID: ");
        int enqId = getValidIntegerInput(sc);
        sc.nextLine();

        // Get enquiry
        Enquiry enquiry = enquiryService.getEnquiryByID(enqId);
        if (enquiry == null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        }
        if (enquiry.getStatus()==EnquiryStatus.CLOSED) {
            System.out.println("Closed enquiries cannot be deleted.");
            return;
        }
        enquiryService.deleteEnquiry(applicant, enquiry);
    }

    /**
     * Allows the applicant to edit an enquiry they have submitted.
     *
     * @param sc        The scanner for reading user input.
     * @param applicant The applicant editing the enquiry.
     */
    private void editEnquiry(Scanner sc, Applicant applicant) {
        // Print enquiries submitted
        if (viewEnquiries(applicant) == null) {
            return;
        }
        // Select enquiry
        System.out.print("Enter submitted enquiry ID: ");
        int enqId = getValidIntegerInput(sc);
        sc.nextLine();
        // Get enquiry
        Enquiry enquiry = enquiryService.getEnquiryByID(enqId);
        if (enquiry == null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        }
        // Check if enquiry is closed
        if (enquiry.getStatus()==EnquiryStatus.CLOSED) {
            System.out.println("Closed enquiries cannot be edited.");
            return;
        }
        // Edit enquiry message
        System.out.print("Enter new enquiry message: ");
        String newMessage = getValidStringInput(sc);
        enquiryService.editEnquiry(enquiry, newMessage);
        System.out.println("Enquiry message updated successfully!");
    }

    /**
     * Allows the applicant to apply for a project.
     *
     * @param sc        The scanner for reading user input.
     * @param applicant The applicant applying for the project.
     */
    private void applyProject(Scanner sc, Applicant applicant) {
        if (applicant.getApplication()!=null) {
            System.out.println("You have already applied to a project.");
            return;
        }

        // Print list of projects
        if (!viewProjectList(applicant)) {
            return;
        }
        
        System.out.print("Enter name of project: ");
        String projectName = getValidStringInput(sc);
        BTOProject project = projectService.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        // Check if project is open
        if (!projectService.isProjectOpen(project) || !project.getVisibility()) {
            System.out.println("Project is not open for applications.");
            return;
        }

        System.out.print("""
            Select flat type to apply for
            1. 2-room
            2. 3-room
            """);
        int flatTypeInput = getValidIntegerInput(sc);
        sc.nextLine();
        FlatType flatType;
        switch (flatTypeInput) {
            case 1 -> flatType = FlatType.TWO_ROOM;
            case 2 -> flatType = FlatType.THREE_ROOM;
            default -> {
                System.out.println("Invalid flat type.");
                return;
            }
        }

        // Check if there are flats available
        if (!projectService.flatTypeAvailable(project, flatType)) {
            System.out.println("No flats available for this type.");
            return;
        }

        // Check eligibility
        if(!applicationService.isEligible(applicant, flatType)) {
            System.out.println("You are not eligible to apply for this flat type.");
            return;
        }

        applicationService.applyProject(applicant, project, flatType);
        System.out.printf("You have successfully applied to %s for a %d-Room flat", project.getProjectName(), flatType.getNumRooms());
    }

    /**
     * Allows the applicant to book a flat after their application is approved.
     *
     * @param applicant The applicant booking the flat.
     */
    private void bookFlat(Applicant applicant) {
        BTOApplication application = applicant.getApplication();

        if (application==null) {
            System.out.println("You have not applied to any project.");
            return;
        }

        if (application.getStatus()!=ApplicationStatus.SUCCESSFUL) {
            System.out.println("Your application has not been aprroved yet.");
            return;
        }

        String projectName = application.getProjectName();
        BTOProject project = projectService.getProjectByName(projectName);
        if (project==null) {
            System.out.println("The project you applied to does not exist.");
            return;
        }

        FlatType flatType = application.getFlatType();
        if (!projectService.flatTypeAvailable(project, flatType)) {
            System.out.println("No flats available for this type.");
            return;
        }

        boolean success = projectService.bookFlat(application, project, flatType, applicant);
        if (success) {
            System.out.println("Flat booked successfully!");
        } else {
            System.out.printf("No more units available for %d-Room flats in %s.", flatType.getNumRooms(), projectName);
        }
    }

    /**
     * Allows the applicant to withdraw their application from a project.
     *
     * @param applicant The applicant withdrawing their application.
     */
    private void withdrawProject(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        if (application == null) {
            System.out.println("You have not applied to any project.");
        }
        else {
            applicationService.requestWithdrawal(applicant);
        }
    }

    /**
     * Reads a valid integer input from the user.
     *
     * @param sc The scanner for reading user input.
     * @return A valid integer input.
     */
    private int getValidIntegerInput(Scanner sc) {
        while (true) {
            try {return sc.nextInt();}
            catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    /**
     * Reads a valid non-empty string input from the user.
     *
     * @param sc The scanner for reading user input.
     * @return A valid non-empty string input.
     */
    private String getValidStringInput(Scanner sc) {
        String input = sc.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Invalid input. Please enter a non-empty string.");
            input = sc.nextLine();
        }
        return input;
    }
}
