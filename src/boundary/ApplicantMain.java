package boundary;

import controller.ApplicationController;
import controller.BTOProjectController;
import controller.Filter;
import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ApplicantMain {
    PrintProjects projectPrinter = new PrintProjects();

    public ApplicantMain(Applicant applicant, Scanner sc) {
        boolean running = true;
        while (running) {
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
            System.out.print("Option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    viewProjectList();
                }

                case 2 -> {
                    applyProject(sc, applicant);
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
                    submitEnquiry(sc, applicant);
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
                    running = false;
                }

                default -> System.out.println("Unknown choice!");
            }
        }
    }

    private void viewProjectList() {
        List<BTOProject> visibleProjects = Filter.filterVisibleProjects(BTOProjectController.getAllProjects());
        projectPrinter.printList(visibleProjects);
    }

    private void submitEnquiry(Scanner sc, Applicant applicant) {
        System.out.println("Enter enquiry message: ");
        String msg = sc.nextLine();
        applicant.enquirySubmit(msg);
    }

    private void applyProject(Scanner sc, Applicant applicant) {
        if (applicant.getApplication()!=null) {
            System.out.println("You have already applied to a project.");
            return;
        }

        // Print list of projects
        viewProjectList();
        
        System.out.println("Enter name of project:");
        String projectName = sc.next();

        Map<String, BTOProject> allProjects = BTOProjectController.getAllProjects();
        BTOProject project = allProjects.get(projectName);

        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        // Check if project is open
        if (!BTOProjectController.isProjectOpen(project) || !project.getVisibility()) {
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
        if (!BTOProjectController.flatTypeAvailable(project, flatType)) {
            System.out.println("No flats available for this type.");
            return;
        }

        // Check eligibility
        if(!ApplicationController.isEligible(applicant, flatType)) {
            System.out.println("You are not eligible to apply for this flat type.");
            return;
        }

        ApplicationController.applyProject(applicant, project, flatType);
        System.out.printf("You have successfully applied to %s for a %d-Room flat", project.getProjectName(), flatType.getNumRooms());
    }

    private void withdrawProject(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        if (application == null) {
            System.out.println("You have not applied to any project.");
        }
        else {
            ApplicationController.requestWithdrawal(applicant);
        }
    }
}
