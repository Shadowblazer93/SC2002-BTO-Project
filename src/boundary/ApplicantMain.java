package boundary;

import controller.ApplicationController;
import controller.BTOProjectController;
import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import java.util.Map;
import java.util.Scanner;

public class ApplicantMain {
    BTOProjectController projectController = new BTOProjectController();
    PrintProjects projectPrinter = new PrintProjects();
    ApplicationController applicationController = new ApplicationController();

    public ApplicantMain(Applicant applicant, Scanner sc) {
        int choice = 0;

        while (choice!=9) {
            System.out.printf("""
                    
                    Hi %s
                    ------------------------------
                          Applicant Main Page
                    ------------------------------
                    1. View project list
                    2. Apply to project
                    3. View Applied project
                    4. Book Flat
                    5. Withdraw from Project
                    6. Submit Enquiry
                    7. Edit Enquiry
                    8. Delete Enquiry
                    9. Logout
                    ------------------------------
                    """, applicant.getName());
            
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    projectPrinter.printMap(projectController.getAllProjects());
                }

                case 2 -> {
                    applyProject(sc, applicant, projectController);
                }

                case 3 -> {
                    // BTOProject appliedProject = applicant.getAppliedProject();
                    BTOApplication application = applicant.getApplication();

                    if (application==null) {
                        System.out.println("You have not applied to any project.");
                        break;
                    }
                    
                    System.out.println("Applied project details:\n" + application);
                }

                case 4 -> {
                    
                }

                case 5 -> {
                    withdrawProject(applicant);
                }

                case 6 -> {
                    System.out.println("Enter enquiry message: ");
                    String msg = sc.nextLine();
                    applicant.enquirySubmit(msg);
                }

                case 7 -> {
                    System.out.print("Enter submitted enquiry ID: ");
                    int enqid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new message: ");
                    String newMsg = sc.nextLine();
                    applicant.enquiryEdit(enqid,newMsg);
                }
                
                case 8 -> {
                    System.out.print("Enter submitted enquiry ID: ");
                    int enqId = sc.nextInt();
                    sc.nextLine();
                    applicant.enquiryDelete(enqId);
                }
                
                case 9 -> {
                    System.out.println("Logging out...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Unknown choice!");
            }
        }
    }

    private void applyProject(Scanner sc, Applicant applicant, BTOProjectController controller) {
        if (applicant.getApplication()!=null) {
            System.out.println("You have already applied to a project.");
            return;
        }

        // Print list of projects
        projectPrinter.printVisibleProjects(controller.getAllProjects());
        
        System.out.println("Enter name of project:");
        String projectName = sc.next();

        Map<String, BTOProject> allProjects = controller.getAllProjects();
        BTOProject project = allProjects.get(projectName);

        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        // Check if project is open
        if (!projectController.isProjectOpen(project) || !project.getVisibility()) {
            System.out.println("Project is not open for applications.");
            return;
        }

        System.out.print("""
            Select flat type to apply for
            1. 2-room
            2. 3-room
            """);
        int flatTypeInput = sc.nextInt();
        sc.nextLine();
        FlatType flatType;
        switch (flatTypeInput) {
            case 1 -> flatType = FlatType.TWO_ROOM;
            case 2 -> flatType = FlatType.THREE_ROOM;
            default -> {
                System.out.println("Invalid flat type.");
                return;
            }
        }

        // Check if there are flats available
        if (!projectController.flatTypeAvailable(project, flatType)) {
            System.out.println("No flats available for this type.");
            return;
        }

        // Check eligibility
        if(!applicationController.isEligible(applicant, flatType)) {
            System.out.println("You are not eligible to apply for this flat type.");
            return;
        }

        applicationController.applyProject(applicant, project, flatType);
        System.out.printf("You have successfully applied to %s for a %d-Room flat", project.getProjectName(), flatType.getNumRooms());
    }

    private void withdrawProject(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        if (application == null) {
            System.out.println("You have not applied to any project.");
        }
        else {
            applicationController.requestWithdrawal(applicant);
        }
    }
}
