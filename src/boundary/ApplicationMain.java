package boundary;

import controller.ApplicationController;
import controller.Filter;
import controller.user.ApplicantController;
import entity.application.BTOApplication;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import enums.FlatType;
import java.util.List;
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

        Map<String, BTOApplication> applications = manager.getCurrentProject().getApplications();
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
            BTOApplication application;
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

        Map<String, BTOApplication> applications = manager.getCurrentProject().getApplications();
        if (applications.isEmpty()) {
            System.out.printf("No applications found for your project '%s'.\n", manager.getCurrentProject().getProjectName());
            return;
        }

        // Select filter (marital status, flat type)
        int filter;
        System.out.println("""
            Filter marital status:
            1. Single
            2. Married
            3. All
            """);
        System.out.print("Select filter: ");
        filter = sc.nextInt();
        sc.nextLine();
        String maritalStatus = null;
        switch (filter) {
            case 1 -> maritalStatus = "single";
            case 2 -> maritalStatus = "married";
        }

        System.out.println("""
            Filter flat type:
            1. 2-Room
            2. 3-Room
            3. All
            """);
        System.out.print("Select filter: ");
        int choice = sc.nextInt();
        sc.nextLine();
        FlatType flatType = null;
        switch (choice) {
            case 1 -> flatType = FlatType.TWO_ROOM;
            case 2 -> flatType = FlatType.THREE_ROOM;
        }

        // Filter applications
        List<BTOApplication> filteredApplications = Filter.filterApplications(applications, maritalStatus, flatType);
        System.out.println("Filtered applications:");
        printApplications.printList(filteredApplications);
    }
}
