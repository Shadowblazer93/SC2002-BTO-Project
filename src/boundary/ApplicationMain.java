package boundary;

import controller.ApplicationController;
import controller.user.ApplicantController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import java.util.Scanner;

public class ApplicationMain {
    ApplicationController applicationController = new ApplicationController();
    ApplicantController applicantController = new ApplicantController();

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
        }
    }

    private void updateApplicantStatus(Scanner sc, Officer officer){
        System.out.print("Enter the NRIC of the applicant to update: ");
        String nric = sc.next();
        // Retrieve applicant and update status
        Applicant applicant = applicantController.getAllApplicants().get(nric);
        officer.updateApplicantStatus(applicant);
    }
    private void generateReceipt(Scanner sc, Officer officer) {
        System.out.print("Enter the NRIC of the applicant to generate receipt for: ");
        String nric = sc.next();
        // Retrieve applicant and generate receipt
        Applicant applicant = applicantController.getAllApplicants().get(nric);
        officer.generateReceipt(applicant);
    }
}
