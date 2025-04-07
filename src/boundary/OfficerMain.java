package boundary;

import entity.user.Officer;
import entity.project.BTOProject;
import java.util.*;
public class OfficerMain {
    
    public static void main(String[] args) {

    }
    public OfficerMain(Officer officer){
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.printf("""
                    Hi %s
                    ------------------------------
                        HDB Officer Main Page
                    ------------------------------
                    1. View and manage flat bookings
                    2. View and reply to enquiries
                    3. Update applicant status
                    4. Generate receipt for bookings
                    5. Logout
                    ------------------------------
                    """, officer.getUserID());
            
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> manageFlatBookings(officer);
                case 2 -> manageEnquiries(officer);
                case 3 -> updateApplicantStatus(officer);
                case 4 -> generateReceipt(officer);
                case 5 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageFlatBookings(Officer officer){
        System.out.println("Managing flat bookings!");
        BTOProject project = officer.viewHandledProject();
        //print proj
        System.out.print(project);
    }
    private void manageEnquiries(Officer officer){
        System.out.println("Viewing and replying to enquiries...");
        BTOProject project = officer.viewHandledProject();
        if (project != null) {
            // Assuming the project has a method to get enquiries, this part may need adjustment
            System.out.println("Project Enquiries for " + project.getProjectName());
            // Display and reply to enquiries
        } else {
            System.out.println("No project assigned yet.");
        }
    }
    private void updateApplicantStatus(Officer officer){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the NRIC of the applicant to update: ");
        String nric = sc.next();
        // Retrieve applicant and update status
        officer.updateApplicantStatus(new Applicant(nric));
    }
    private void generateReceipt(Officer officer) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the NRIC of the applicant to generate receipt for: ");
        String nric = sc.next();
        // Retrieve applicant and generate receipt
        officer.generateReceipt(new Applicant(nric));
    }
}





