package boundary;

import controller.ApplicationController;
import controller.user.ApplicantController;
import entity.application.Application;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import java.util.Map;
import java.util.Scanner;

public class ApplicationMain {
    ApplicationController applicationController = new ApplicationController();
    ApplicantController applicantController = new ApplicantController();
    PrintApplications printApplications = new PrintApplications();

    public void displayMenuOfficer(Scanner sc, Officer officer) {
        boolean running = true;
        while (running) {
            System.out.println("""
                ------------------------
                    Application Menu
                ------------------------
                1. Update applicant status
                2. Update applicant profile
                3. Generate receipt for bookings
                4. Exit
            """);
            System.out.print("Option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    
                }
                case 2 -> {

                }
                case 3 -> {

                }
                case 4 -> {
                    System.out.println("Exiting application menu...");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    public void displayMenuManager(Scanner sc, Manager manager) {
        boolean running = true;
        while (running) {
            System.out.println("""
                ------------------------
                    Application Menu
                ------------------------
                1. Approve application
                2. Reject application
                3. Filter applications
                4. Exit
            """);
            System.out.print("Option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    processApplication(sc, manager, true);
                }
                case 2 -> {
                    processApplication(sc, manager, false);
                }
                case 3 -> {
                    filterApplication(sc, manager);
                }
                case 4 -> {
                    System.out.println("Exiting application menu...");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private void updateApplicantStatus(Scanner sc, Officer officer){
        System.out.print("Enter the NRIC of the applicant to update: ");
        String nric = sc.next();
        // Retrieve applicant and update status
        Applicant applicant = applicantController.getAllApplicants().get(nric);
        officer.updateApplicantStatus(applicant);

        // Book flat
    }
    private void generateReceipt(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant to generate receipt for: ");
        String nric = sc.next();
        // Retrieve applicant and generate receipt
        Applicant applicant = applicantController.getAllApplicants().get(nric);
        officer.generateReceipt(applicant);
    }

    private void processApplication(Scanner sc, Manager manager, boolean isApproval) {
        if (manager.getCurrentProject() == null) {
            System.out.println("You are not managing any project.");
            return;
        }

        Map<String, Application> applications = manager.getCurrentProject().getApplications();
        if (applications.isEmpty()) {
            System.out.printf("No applications found for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return;
        }
        // Print list of applications
        printApplications.printMap(applications);

        System.out.println("Select applications to " + (isApproval ? "approve" : "reject") + " (NRIC). Type 0 to stop: ");
        String nric = "";
        while (!nric.equals("0")) {
            nric = sc.nextLine();
            // Retrieve application by NRIC
            Application application;
            if (applications.containsKey(nric)) {
                application = applications.get(nric);
            } else {
                System.out.println("Application not found.");
                continue;
            }
            // Reject application
            boolean success = isApproval
                    ? applicationController.approveApplication(application)
                    : applicationController.rejectApplication(application);
            if (success) {
                System.out.println("Application " + (isApproval ? "approved" : "rejected") + " successfully.");
            } else {
                System.out.println("Failed to " + (isApproval ? "approve" : "reject") + " application.");
            }
        }
    }

    private void filterApplication(Scanner sc, Manager manager) {
        if (manager.getCurrentProject() == null) {
            System.out.println("You are not managing any project.");
            return;
        }

        Map<String, Application> applications = manager.getCurrentProject().getApplications();
        if (applications.isEmpty()) {
            System.out.printf("No applications found for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return;
        }

        // Select filter
        
    }
}
