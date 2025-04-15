package boundary;

import controller.ApplicationController;
import controller.BTOProjectController;
import controller.EnquiryController;
import controller.Filter;
import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ApplicantMain {
    PrintProjects projectPrinter = new PrintProjects();
    PrintEnquiries enquiryPrinter = new PrintEnquiries();

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
                    editEnquiry(sc, applicant);
                }
                
                case 8 -> {
                    deleteEnquiry(sc, applicant);
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
        // select project to enquire about
        viewProjectList();
        System.out.print("Project for enquiry: ");
        String projectName = sc.nextLine();
        BTOProject project = BTOProjectController.getProjectByName(projectName);
        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        // Enquiry message
        System.out.print("Enter enquiry message: ");
        String msg = sc.nextLine();
        EnquiryController.submitEnquiry(applicant, project, msg);
        System.out.println("Enquiry submitted successfully!");
    }

    private Map<Integer, Enquiry> viewEnquiries(Applicant applicant) {
        Map<Integer, Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            return null;
        }
        enquiryPrinter.printMap(enquiries);
        return enquiries;
    }

    private void deleteEnquiry(Scanner sc, Applicant applicant) {
        // Print enquiries submitted
        if (viewEnquiries(applicant) == null) {
            return;
        }
        // Select enquiry
        System.out.print("Enter submitted enquiry ID: ");
        int enqId = sc.nextInt();
        sc.nextLine();

        // Get enquiry
        Enquiry enquiry = EnquiryController.getEnquiryByID(enqId);
        if (enquiry == null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        }
        EnquiryController.deleteEnquiry(applicant, enquiry);
    }

    private void editEnquiry(Scanner sc, Applicant applicant) {
        // Print enquiries submitted
        if (viewEnquiries(applicant) == null) {
            return;
        }
        // Select enquiry
        System.out.print("Enter submitted enquiry ID: ");
        int enqId = sc.nextInt();
        sc.nextLine();
        // Get enquiry
        Enquiry enquiry = EnquiryController.getEnquiryByID(enqId);
        if (enquiry == null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        }
        // Edit enquiry message
        System.out.print("Enter new enquiry message: ");
        String newMessage = sc.nextLine();
        EnquiryController.editEnquiry(enquiry, newMessage);
        System.out.println("Enquiry message updated successfully!");
    }

    private void applyProject(Scanner sc, Applicant applicant) {
        if (applicant.getApplication()!=null) {
            System.out.println("You have already applied to a project.");
            return;
        }

        // Print list of projects
        viewProjectList();
        
        System.out.print("Enter name of project:");
        String projectName = sc.next();
        BTOProject project = BTOProjectController.getProjectByName(projectName);
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
