package boundary;

import controller.Filter;
import controller.RegistrationController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import enums.defColor;
import java.util.*;
import printer.PrintRegistrations;


public class RegistrationMain {
    PrintRegistrations printRegistrations = new PrintRegistrations();

    public void displayMenu(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
                --------------------------
                  Registration Main Page
                --------------------------
                """ + defColor.BLUE +
                """
                1. View all registrations
                2. Approve registrations
                3. Reject registrations
                4. View pending registrations
                5. View approved registrations
                6. Exit
                """ + defColor.RESET);
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

    private void viewAllRegistrations() {
        Map<String, List<Registration>> allRegistrations = RegistrationController.getAllRegistrations();
        if (allRegistrations == null) {
            System.out.println("No registrations found in system.");
            return;
        }
        System.out.println(defColor.YELLOW + "List of all registrations: ");
        printRegistrations.printMapList(allRegistrations);
    }

    private List<Registration> retrieveProjectRegistrations(Manager manager) {
        Map<String, BTOProject> projects = manager.getManagedProjects();
        if (projects == null) {
            System.out.println("You are not managing any project.");
            return null;
        }

        List<Registration> allRegistrations = new ArrayList<>();
        for (BTOProject project : projects.values()) {
            Map<Integer, Registration> projectRegistrations = project.getRegistrations();
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

    

    public void processRegistrations(Manager manager, Scanner sc, boolean isApproval) {
        // Get and print pending registrations for project
        List<Registration> pendingRegistrations = viewPendingRegistrations(manager);
        if (pendingRegistrations == null) {
            return;
        }
        BTOProject project = manager.getCurrentProject();
        // Convert list to map
        Map<Integer, Registration> pendingRegistrationsMap = new HashMap<>();
        for (Registration registration : pendingRegistrations) {
            pendingRegistrationsMap.put(registration.getID(), registration);
        }

        System.out.println("Select registrations to " + (isApproval ? "approve" : "reject") + " (ID). Type 0 to stop: ");
        int id = -1;
        while (id != 0) {
            id = sc.nextInt();
            // Retrieve registration by NRIC
            Registration registration;
            if (pendingRegistrationsMap.containsKey(id)) {
                registration = pendingRegistrationsMap.get(id);
            } else {
                System.out.println("Registration not found.");
                continue;
            }
            // Reject registration
            String result = isApproval
                ? RegistrationController.approveRegistration(manager, registration)
                : RegistrationController.rejectRegistration(manager, registration);
            System.out.println(result);
        }
    }
}
