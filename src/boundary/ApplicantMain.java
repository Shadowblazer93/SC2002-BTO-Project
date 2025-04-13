package boundary;

import controller.BTOProjectController;
import entity.application.Application;
import entity.project.BTOProject;
import entity.user.Applicant;
import java.util.Scanner;
import java.util.Map;

public class ApplicantMain {
    public ApplicantMain(Applicant applicant, Scanner sc) {
        BTOProjectController projectController = new BTOProjectController();
        PrintProjects projectPrinter = new PrintProjects();
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
                    Application application = applicant.getApplication();

                    if (application==null) {
                        System.out.println("You have not applied to any project.");
                        break;
                    }
                    
                    System.out.println("Applied project details:\n" + application);
                }

                case 4 -> {
                    
                }

                case 5 -> {
                    withdrawProject(sc, applicant);
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
        System.out.println("Enter name of project:");
        String projectName = sc.next();

        Map<String, BTOProject> allProjects = controller.getAllProjects();
        BTOProject project = allProjects.get(projectName);

        if (applicant.getApplication()!=null) {
            System.out.println("You have already applied to a project.");
            return;
        }

        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        if (project.getApplication()!=null) {
            if (project.getApplication().getApplicantNRIC()==applicant.getNRIC()) {
                System.out.println("You have already applied to this project.");
            } else {
                System.out.println("Project has already been applied to by another applicant.");
            }
            return;
        }
        
        // Check if project exists
        // Check if project is open
        // Check if project is not full
        // Check if applicant has not applied to any other project
        // Check if applicant is eligible
        // Apply to project
    }

    private void withdrawProject(Scanner sc, Applicant applicant) {
        Application withdrawAppl = applicant.getApplication();
        if (withdrawAppl==null) {System.out.println("You have not applied to any project.");}
        else {
            applicant.projectWithdraw();
        }
    }
}
