package boundary;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import enums.defColor;
import interfaces.IRegistrationMain;
import interfaces.IRegistrationService;
import java.util.*;
import printer.PrintRegistrations;
import util.Filter;

/**
 * Boundary class for Managers to manager registrations
 * This class handles the user interface for registration management
 */
public class RegistrationMain implements IRegistrationMain {
    PrintRegistrations printRegistrations = new PrintRegistrations();

    private final IRegistrationService registrationService;

    public RegistrationMain(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Display the registration management menu for the manager
     * @param manager Manager using the menu
     * @param sc Scanner object for user input
     */
    @Override
    public void displayMenu(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
                ==============================
                    Registration Main Page
                ------------------------------
                """ + defColor.BLUE + """
                1. View all registrations
                2. Approve registrations
                3. Reject registrations
                4. View pending registrations
                5. View approved registrations
                6. Exit
                """ + defColor.PURPLE +
                "==============================\n" + defColor.RESET);
            System.out.print("Option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 -> {
                    viewAllRegistrations();
                }
                case 2 -> {
                    processRegistrations(manager, sc, true);
                }
                case 3 -> {
                    processRegistrations(manager, sc, false);
                }
                case 4 -> {
                    viewPendingRegistrations(manager);
                }
                case 5 -> {
                    viewApprovedRegistrations(manager);
                }
                case 6 -> {
                    System.out.println("Exiting registration menu.");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option.");
                }
            }
        }
    }

    /**
     * Display list of all registrations from all projects in the BTOMS
     */
    private void viewAllRegistrations() {
        Map<String, List<Registration>> allRegistrations = registrationService.getAllRegistrations();
        if (allRegistrations == null) {
            System.out.println("No registrations found in system.");
            return;
        }
        System.out.println(defColor.YELLOW + "List of all registrations: ");
        printRegistrations.printMapList(allRegistrations);
    }

    /**
     * Retrieve all registrations for manager's projects
     * @param manager Manager of projects
     * @return A list of all registrations under manager's projects, null if there are none
     */
    private List<Registration> retrieveProjectRegistrations(Manager manager) {
        Map<String, BTOProject> projects = manager.getManagedProjects();
        if (projects == null) {
            System.out.println("You are not managing any project.");
            return null;
        }

        List<Registration> allRegistrations = new ArrayList<>();
        for (BTOProject project : projects.values()) {
            Map<String, Registration> projectRegistrations = project.getRegistrations();
            if (projectRegistrations != null) {
                allRegistrations.addAll(projectRegistrations.values());
            }
        }
        if (allRegistrations.isEmpty()) {
            System.out.printf("There are no registrations");
            return null;
        }
        return allRegistrations;
    }

    /**
     * Displays and returns list of pending registrations for manager's projects
     * @param manager Manager viewing pending registrations
     * @return List of pending registrations under manager, null if there are none
     */
    private List<Registration> viewPendingRegistrations(Manager manager) {
        List<Registration> projectRegistrations = retrieveProjectRegistrations(manager);
        if (projectRegistrations == null) {
            return null;
        }
        List<Registration> pendingRegistrations = Filter.filterPendingRegistrations(projectRegistrations);
        if (pendingRegistrations == null) {
            System.out.printf("No pending registrations for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return null;
        }
        System.out.printf(defColor.YELLOW + "List of pending registrations for %s: \n", manager.getCurrentProject().getProjectName());
        printRegistrations.printList(pendingRegistrations);
        return pendingRegistrations;
    }

    /**
     * Display list of approved registrations for the manager's projects
     * @param manager Manager viewing the registrations
     */
    private void viewApprovedRegistrations(Manager manager) {
        List<Registration> projectRegistrations = retrieveProjectRegistrations(manager);
        if (projectRegistrations == null) {
            return;
        }
        List<Registration> approvedRegistrations = Filter.filterApprovedRegistrations(projectRegistrations);
        if (approvedRegistrations == null || approvedRegistrations.isEmpty()) {
            System.out.printf("No approved registrations for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return;
        }
        System.out.printf(defColor.YELLOW + "List of approved registrations for %s: \n", manager.getCurrentProject().getProjectName());
        printRegistrations.printList(approvedRegistrations);
    }

    /**
     * Allow manager to process pending registrations by approving or rejecting them
     * @param manager Manager processing the registrations
     * @param sc Scanner object for user input
     * @param isApproval {@code true} to approve registration, {@code false} to reject registration
     */
    public void processRegistrations(Manager manager, Scanner sc, boolean isApproval) {
        // Get and print pending registrations for project
        List<Registration> pendingRegistrations = viewPendingRegistrations(manager);
        if (pendingRegistrations == null) {
            return;
        }
        // Convert list to map
        Map<Integer, Registration> pendingRegistrationsMap = new HashMap<>();
        for (Registration registration : pendingRegistrations) {
            pendingRegistrationsMap.put(registration.getID(), registration);
        }

        System.out.println("Select registrations to " + (isApproval ? "approve" : "reject") + " (ID). Type 0 to stop: ");
        int id = -1;
        while (id != 0) {
            try {
                id = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
                continue;
            }
            // Retrieve registration by ID
            Registration registration;
            if (pendingRegistrationsMap.containsKey(id)) {
                registration = pendingRegistrationsMap.get(id);
            } else {
                System.out.println("Registration not found.");
                continue;
            }
            // Reject registration
            String result = isApproval
                ? registrationService.approveRegistration(manager, registration)
                : registrationService.rejectRegistration(manager, registration);
            System.out.println(result);
        }
    }
}
