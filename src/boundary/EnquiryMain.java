package boundary;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.*;
import enums.UserRole;
import enums.defColor;
import interfaces.IEnquiryMain;
import interfaces.IEnquiryService;
import java.util.Map;
import java.util.Scanner;
import printer.PrintEnquiries;

public class EnquiryMain implements IEnquiryMain {
    PrintEnquiries enquiryPrinter = new PrintEnquiries();

    private final IEnquiryService enquiryService;

    public EnquiryMain(IEnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @Override
    public void viewProjectEnquiries(Officer officer) {
        BTOProject project = officer.getAssignedProject();
        
        if (project == null) {
            System.out.println("No project assigned to the officer.");
            return;
        }
        
        System.out.println("Project Enquiries for " + project.getProjectName());
        enquiryPrinter.printMap(project.getEnquiries());
    }
    
    @Override
    public void displayMenuOfficer(Scanner sc, Officer officer) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ====================================
                        Enquiry Menu
            ------------------------------------
            """ + defColor.BLUE + """
            1. View enquiries (managed project)
            2. Reply enquiries (managed project)
            3. Exit
            """ + defColor.PURPLE + 
            "====================================\n" + defColor.RESET);
            System.out.print("Option: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1 -> viewManagedEnquiries(officer);
                case 2 -> replyEnquiry(sc, officer);
                case 3 -> {
                    System.out.println("Exiting enquiry menu...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select a valid option (1–3).");
            }
        }
    }
    

    public void displayMenuManager(Scanner sc, Manager manager) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ====================================
                        Enquiry Menu
            ------------------------------------
            """ + defColor.BLUE +       
            """
            1. View enquiries (all projects)
            2. View enquiries (managed project)
            3. Reply enquiries (managed project)
            4. Exit
            """ + defColor.PURPLE + 
            "====================================\n" + defColor.RESET);
            System.out.print("Option: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1 -> viewAllEnquiries();
                case 2 -> viewManagedEnquiries(manager);
                case 3 -> replyEnquiry(sc, manager);
                case 4 -> {
                    System.out.println("Exiting enquiry menu...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select a valid option (1–4).");
            }
        }
    }
    

    private void viewAllEnquiries() {
        Map<Integer, Enquiry> allEnquiries = enquiryService.getAllEnquiries();
        System.out.println("All Enquiries:");
        enquiryPrinter.printMap(allEnquiries);
    }

    private BTOProject viewManagedEnquiries(User user) {
        UserRole role = user.getUserRole();
        BTOProject project = null;
        switch (role) {
            case MANAGER -> {
                Manager manager = (Manager) user;
                project = manager.getCurrentProject();
            }
            case OFFICER -> {
                Officer officer = (Officer) user;
                project = officer.getAssignedProject();
            }
            default -> {
                System.out.println("Invalid user role. Cannot view enquiries.");
            }
        }
        
        if (project != null) {
            Map<Integer, Enquiry> enquiries = project.getEnquiries();
            if (enquiries.isEmpty()) {
                System.out.printf("No enquiries for project '%s'.\n", project.getProjectName());
                return null;
            }
            System.out.println("Project Enquiries for " + project.getProjectName());
            enquiryPrinter.printMap(enquiries);
        } else {
            System.out.println("You are not managing any project.");
        }

        return project;
    }

    private void replyEnquiry(Scanner sc, User user) {
        // Show all enquiries
        BTOProject project = viewManagedEnquiries(user);
        if (project == null) {
            return; // Error message already printed in viewManagedEnquiries()
        }

        System.out.print("Enter Enquiry ID to reply: ");
        int enquiryId;
        try {
            enquiryId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Enquiry ID. Must be a number.");
            return;
        }
        
        System.out.print("Enter reply: ");
        String reply = sc.nextLine();

        enquiryService.replyEnquiry(project, enquiryId, reply);
    }
}
