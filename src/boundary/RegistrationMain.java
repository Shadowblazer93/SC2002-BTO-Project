package boundary;

import controller.RegistrationController;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import java.util.*;


public class RegistrationMain {
    PrintRegistrations printRegistrations = new PrintRegistrations();
    PrintProjects printProjects = new PrintProjects();
    RegistrationController registrationController = new RegistrationController();

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
                    // Print registrations for each project managed
                    printRegistrations.printMapList(RegistrationController.getAllRegistrations());
                }
                case 2 -> {
                    approveRegistrations(manager, sc);
                }
                case 3 -> {
                    rejectRegistrations(manager, sc);
                }
                case 4 -> {

                }
                case 5 -> {
                    
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

    private void approveRegistrations(Manager manager, Scanner sc) {
        BTOProject project = selectProject(manager, sc);
        if (project != null) {
            processRegistrations(project, sc, true);
        }
    }

    private void rejectRegistrations(Manager manager, Scanner sc) {
        BTOProject project = selectProject(manager, sc);
        if (project != null) {
            processRegistrations(project, sc, false);
        }
    }

    private BTOProject selectProject(Manager manager, Scanner sc) {
        // Print list of projects
        printProjects.printMap(manager.getManagedProjects());
        System.out.println("Select project: ");
        String projectName = sc.nextLine();
        BTOProject project = manager.getManagedProjects().get(projectName);
        if (project == null) {
            System.out.println("Project not found.");
        }
        return project;
    }

    public void processRegistrations(BTOProject project, Scanner sc, boolean isApproval) {
        // Get pending registrations for project
        Map<String, Registration> registrationList = project.getPendingRegistrations();
        if (registrationList.isEmpty()) {
            System.out.println("No pending registrations for this project.");
            return;
        }

        // Print registrations in the project
        printRegistrations.printMap(registrationList);

        System.out.println("Select registrations to " + (isApproval ? "approve" : "reject") + " (NRIC). Type 0 to stop: ");
        String nric = "";
        while (!nric.equals("0")) {
            nric = sc.nextLine();
            // Retrieve registration by NRIC
            Registration registration;
            if (registrationList.containsKey(nric)) {
                registration = registrationList.get(nric);
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
