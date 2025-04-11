package boundary;

import controller.BTOProjectController;
import entity.project.BTOProject;
import entity.user.Applicant;
import java.util.Scanner;

public class ApplicantMain {
    public ApplicantMain(Applicant applicant, Scanner sc) {
        BTOProjectController projectController = new BTOProjectController();
        PrintProjects projectPrinter = new PrintProjects();
        int choice = 0;

        while (choice!=8) {
            System.out.printf("""
                    Hi %s
                    ------------------------------
                          Applicant Main Page
                    ------------------------------
                    1. View project list
                    2. Apply to project
                    3. View Applied project
                    4. Withdraw from Project
                    5. Submit Enquiry
                    6. Edit Enquiry
                    7. Delete Enquiry
                    8. Logout
                    ------------------------------
                    """, applicant.getName());
            
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    projectPrinter.printMap(projectController.getAllProjects());
                }
                case 2 -> {
                    applyProject(sc, applicant);
                }
                case 3 -> {
                    BTOProject appliedProject = applicant.getAppliedProject();

                    if (appliedProject==null) {System.out.println("You have not applied to any project.");}
                    else {System.out.println("Applied project:\n" + appliedProject);}
                }
                case 4 -> {
                    withdrawProject(sc, applicant);
                }
                case 5 -> {
                    System.out.println("Enter enquiry message: ");
                    String msg = sc.nextLine();
                    applicant.enquirySubmit(msg);
                }
                case 6 -> {
                    System.out.print("Enter submitted enquiry ID: ");
                    int enqid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new message: ");
                    String newMsg = sc.nextLine();
                    applicant.enquiryEdit(enqid,newMsg);
                }
                
                case 7 -> {
                    System.out.print("Enter submitted enquiry ID: ");
                    int enqId = sc.nextInt();
                    sc.nextLine();
                    applicant.enquiryDelete(enqId);
                }
                
                case 8 -> {
                    System.out.println("Logging out...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Unknown choice!");
            }
        }
    }

    private void applyProject(Scanner sc, Applicant applicant) {
        System.out.println("Enter name of project:");
        String projectName = sc.next();
        // Check if project exists
        // Check if project is open
        // Check if project is not full
        // Check if applicant has not applied to any other project
        // Check if applicant is eligible
        // Apply to project
    }

    private void withdrawProject(Scanner sc, Applicant applicant) {
        BTOProject projectToWithdraw = applicant.getAppliedProject();
        if (projectToWithdraw==null) {System.out.println("You have not applied to any project.");}
        else {
            applicant.projectWithdraw(projectToWithdraw);
        }
    }
}
