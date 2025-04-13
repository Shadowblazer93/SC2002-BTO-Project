package boundary;

import controller.EnquiryController;
import entity.project.BTOProject;
import entity.user.*;
import java.util.Scanner;

public class EnquiryMain {
    EnquiryController enquiryController = new EnquiryController();

    public void viewProjectEnquiries(Officer officer) {
        PrintEnquiries enquiryPrinter = new PrintEnquiries();
        BTOProject project = officer.getAssignedProject();
        
        if (project == null) {
            System.out.println("No project assigned to the officer.");
            return;
        }
        
        System.out.println("Project Enquiries for " + project.getProjectName());
        enquiryPrinter.printMap(project.getEnquiries());
    }

    public void displayMenuOfficer(Scanner sc, Officer officer) {
        boolean running = true;
        while (running) {
            System.out.println("""
                ------------------------------------
                            Enquiry Menu
                ------------------------------------
                1. View enquiries (managed project)
                2. Reply enquiries (managed project)
                3. Exit
            """);
        }
    }

    public void displayMenuManager(Scanner sc, Manager manager) {
        boolean running = true;
        while (running) {
            System.out.println("""
                ------------------------------------
                            Enquiry Menu
                ------------------------------------
                1. View enquiries (all projects)
                2. View enquiries (managed project)
                3. Reply enquiries (managed project)
                4. Exit
            """);
        }
    }

    private void manageEnquiries(Scanner sc, Officer officer) {
        PrintEnquiries enquiryPrinter = new PrintEnquiries();
        System.out.println("Viewing and replying to enquiries...");
        BTOProject project = officer.viewHandledProject();
        if (project != null) {
            System.out.println("Project Enquiries for " + project.getProjectName());
            enquiryPrinter.printMap(project.getEnquiries());    // Print enquiries
            // Simulate replying to an enquiry
            System.out.print("Enter Enquiry ID to reply: ");
            String enquiryId = sc.nextLine();
            System.out.print("Enter reply: ");
            String reply = sc.nextLine();
            enquiryController.replyEnquiry(officer, enquiryId, reply);
        } else {
            System.out.println("No project assigned yet.");
        }
    }
}
