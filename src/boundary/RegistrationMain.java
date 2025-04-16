package boundary;

import controller.Filter;
import controller.RegistrationController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import java.util.*;


public class RegistrationMain {
    PrintRegistrations printRegistrations = new PrintRegistrations();

    public void displayMenu(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.println("""
                --------------------------
                  Registration Main Page
                --------------------------
                1. View all registrations
                2. Approve registrations
                3. Reject registrations
                4. View pending registrations
                5. View approved registrations
                6. Exit
                """);
            System.out.print("Option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 -> {
                    // Print registrations for each project
                    printRegistrations.printMapList(RegistrationController.getAllRegistrations());
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

    private Map<String, Registration> retrieveProjectRegistrations(Manager manager) {
        BTOProject project = manager.getCurrentProject();
        if (project == null) {
            System.out.println("You are not managing any project.");
            return null;
        }
        Map<String, Registration> projectRegistrations = project.getRegistrations();
        if (projectRegistrations.isEmpty()) {
            System.out.printf("No registrations for your project '%s'.", project.getProjectName());
            return null;
        }
        return projectRegistrations;
    }

    private Map<String, Registration> viewPendingRegistrations(Manager manager) {
        Map<String, Registration> projectRegistrations = retrieveProjectRegistrations(manager);
        if (projectRegistrations.isEmpty()) {
            return null;
        }
        Map<String, Registration> pendingRegistrations = Filter.filterPendingRegistrations(projectRegistrations);
        if (pendingRegistrations.isEmpty()) {
            System.out.printf("No pending registrations for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return null;
        }
        printRegistrations.printMap(pendingRegistrations);
        return pendingRegistrations;
    }

    private void viewApprovedRegistrations(Manager manager) {
        Map<String, Registration> projectRegistrations = retrieveProjectRegistrations(manager);
        if (projectRegistrations == null) {
            return;
        }
        Map<String, Registration> approvedRegistrations = Filter.filterApprovedRegistrations(projectRegistrations);
        if (approvedRegistrations == null) {
            System.out.printf("No approved registrations for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return;
        }
        printRegistrations.printMap(approvedRegistrations);
    }

    

    public void processRegistrations(Manager manager, Scanner sc, boolean isApproval) {
        // Get and print pending registrations for project
        Map<String, Registration> pendingRegistrations = viewPendingRegistrations(manager);
        if (pendingRegistrations == null) {
            return;
        }
        BTOProject project = manager.getCurrentProject();

        System.out.println("Select registrations to " + (isApproval ? "approve" : "reject") + " (NRIC). Type 0 to stop: ");
        String nric = "";
        while (!nric.equals("0")) {
            nric = sc.nextLine();
            // Retrieve registration by NRIC
            Registration registration;
            if (pendingRegistrations.containsKey(nric)) {
                registration = pendingRegistrations.get(nric);
            } else {
                System.out.println("Registration not found.");
                continue;
            }
            // Reject registration
            String result = isApproval
                ? RegistrationController.approveRegistration(project, registration)
                : RegistrationController.rejectRegistration(project, registration);
            System.out.println(result);
        }
    }
}
