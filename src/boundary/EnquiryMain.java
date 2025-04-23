package boundary;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.*;
import enums.UserRole;
import enums.defColor;
import interfaces.IEnquiryMain;
import interfaces.IEnquiryService;
import java.util.Map;
import java.util.Scanner;
import printer.PrintEnquiries;

/**
 * EnquiryMain class provides the interface for managing enquiries in the BTO system.
 * Allows officers and managers to view and reply to enquiries for their assigned or managed projects.
 */
public class EnquiryMain implements IEnquiryMain {
    PrintEnquiries enquiryPrinter = new PrintEnquiries();

    private final IEnquiryService enquiryService;

    /**
     * Constructs an EnquiryMain object with the specified enquiry service.
     *
     * @param enquiryService Service for managing enquiries.
     */
    public EnquiryMain(IEnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    /**
     * Displays the enquiry menu for officers and handles user input.
     *
     * @param sc      Scanner for reading user input.
     * @param officer Officer interacting with the system.
     */
    @Override
    public void displayMenuOfficer(Scanner sc, Officer officer) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ====================================
                        Enquiry Menu
            ------------------------------------
            """ + defColor.BLUE + """
            1. View enquiries (managed project)
            2. Reply enquiries (managed project)
            3. Exit
            """ + defColor.PURPLE + 
            "====================================" + defColor.RESET);
            System.out.print("Option: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1 -> viewManagedEnquiries(officer);
                case 2 -> replyEnquiry(sc, officer);
                case 3 -> {
                    System.out.println("Exiting enquiry menu...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select a valid option (1–3).");
            }
        }
    }

    /**
     * Displays the enquiry menu for managers and handles user input.
     *
     * @param sc      Scanner for reading user input.
     * @param manager Manager interacting with the system.
     */
    @Override
    public void displayMenuManager(Scanner sc, Manager manager) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ====================================
                        Enquiry Menu
            ------------------------------------
            """ + defColor.BLUE +       
            """
            1. View enquiries (all projects)
            2. View enquiries (managed project)
            3. Reply enquiries (managed project)
            4. Exit
            """ + defColor.PURPLE + 
            "====================================" + defColor.RESET);
            System.out.print("Option: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1 -> viewAllEnquiries();
                case 2 -> viewManagedEnquiries(manager);
                case 3 -> replyEnquiry(sc, manager);
                case 4 -> {
                    System.out.println("Exiting enquiry menu...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select a valid option (1–4).");
            }
        }
    }

    /**
     * Displays all enquiries in the system.
     */
    private void viewAllEnquiries() {
        Map<Integer, Enquiry> allEnquiries = enquiryService.getAllEnquiries();
        System.out.println("All Enquiries:");
        enquiryPrinter.printMap(allEnquiries);
    }

    /**
     * Displays enquiries for the project managed by the specified user.
     *
     * @param user User managing the project (officer or manager).
     * @return The project being managed, or null if no project is assigned.
     */
    private BTOProject viewManagedEnquiries(User user) {
        UserRole role = user.getUserRole();
        BTOProject project = null;
        switch (role) {
            case MANAGER -> {
                Manager manager = (Manager) user;
                project = manager.getCurrentProject();
            }
            case OFFICER -> {
                Officer officer = (Officer) user;
                project = officer.getAssignedProject();
            }
            default -> {
                System.out.println("Invalid user role. Cannot view enquiries.");
            }
        }
        
        if (project != null) {
            Map<Integer, Enquiry> enquiries = project.getEnquiries();
            if (enquiries.isEmpty()) {
                System.out.printf("No enquiries for project '%s'.\n", project.getProjectName());
                return null;
            }
            System.out.println("Project Enquiries for " + project.getProjectName());
            enquiryPrinter.printMap(enquiries);
        } else {
            System.out.println("You are not managing any project.");
        }

        return project;
    }

    /**
     * Allows the user to reply to an enquiry for their managed project.
     *
     * @param sc   Scanner for reading user input.
     * @param user User managing the project (officer or manager).
     */
    private void replyEnquiry(Scanner sc, User user) {
        // Show all enquiries
        BTOProject project = viewManagedEnquiries(user);
        if (project == null) {
            return; // Error message already printed in viewManagedEnquiries()
        }

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

        enquiryService.replyEnquiry(project, enquiryId, reply);
    }
}
