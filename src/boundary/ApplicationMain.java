package boundary;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.*;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.defColor;
import interfaces.IApplicantService;
import interfaces.IApplicationMain;
import interfaces.IApplicationService;
import interfaces.IProjectService;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import printer.PrintBTOApplications;
import util.Filter;
import util.Receipt;

/**
 * Provides the user interface for application-related tasks such as:
 * - Officer: booking flats, generating receipts
 * - Manager: approving/rejecting applications and withdrawals, filtering
 *
 * Implements {@code IApplicationMain}.
 */
public class ApplicationMain implements IApplicationMain {

    /** Handles printing of application lists to console. */
    PrintBTOApplications printApplications = new PrintBTOApplications();

    private final IApplicantService applicantService;
    private final IApplicationService applicationService;
    private final IProjectService projectService;

    /**
     * Constructs the application main UI handler with required services.
     *
     * @param applicantService    Service for applicant-related operations
     * @param applicationService  Service for application-related operations
     * @param projectService      Service for project-related operations
     */
    public ApplicationMain(IApplicantService applicantService, IApplicationService applicationService, IProjectService projectService) {
        this.applicantService = applicantService;
        this.applicationService = applicationService;
        this.projectService = projectService;
    }

    /**
     * Displays the officer-facing application menu, allowing them to:
     * 1. Book a flat for a successful applicant
     * 2. Generate a receipt for a booking
     *
     * @param sc      The {@code Scanner} for user input
     * @param officer The logged-in officer
     */
    @Override
    public void displayMenuOfficer(Scanner sc, Officer officer) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ========================
                Application Menu
            ------------------------
            """ + defColor.BLUE+"""
            1. Book flat for applicant
            2. Generate receipt for bookings
            3. Exit
            """ + defColor.PURPLE + 
            "========================" + defColor.RESET);
            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                }
            }

            switch (choice) {
                case 1 -> {
                    bookFlat(sc, officer);
                }
                case 2 -> {
                    generateReceipt(sc, officer);
                }
                case 3 -> {
                    System.out.println("Exiting application menu...");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    /**
     * Displays the manager-facing application menu, allowing them to:
     * 1. Approve or reject applications
     * 2. Approve or reject withdrawals
     * 3. Filter applications by marital status and flat type
     *
     * @param sc      The {@code Scanner} for user input
     * @param manager The logged-in manager
     */
    @Override
    public void displayMenuManager(Scanner sc, Manager manager) {
        boolean running = true;
        while (running) {
            System.out.println(defColor.PURPLE + """
            ==================================
                     Application Menu
            ----------------------------------
            """ + defColor.BLUE + 
            """
            1. Approve applications
            2. Reject applications
            3. Approve applcation withdrawals
            4. Reject application withdrawals
            5. Filter applications
            6. Exit
            """ + defColor.PURPLE + 
            "==================================" + defColor.RESET);
            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                }
            }

            switch (choice) {
                case 1 -> {
                    processApplication(sc, manager, true);  // Approve applications
                }
                case 2 -> {
                    processApplication(sc, manager, false); // Reject applications
                }
                case 3 -> {
                    processWithdrawals(sc, manager, true);  // Approve withdrawals
                }
                case 4 -> {
                    processWithdrawals(sc, manager, false); // Reject withdrawals
                }
                case 5 -> {
                    filterApplication(sc, manager);
                }
                case 6 -> {
                    System.out.println("Exiting application menu...");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    /**
     * Books a flat for a successful applicant, selected by NRIC.
     * Verifies officer’s access to project and flat availability.
     *
     * @param sc      {@code Scanner} for user input
     * @param officer Officer booking the flat
     */
    private void bookFlat(Scanner sc, Officer officer) {
        // 1. Get project applications
        Map<String, BTOApplication> applications = retrieveApplications(officer);
        if (applications == null) {
            return; // Error message already printed in retrieveApplications()
        }
        // 2. Filter pending bookings
        List<BTOApplication> pendingBookings = Filter.filterPendingBookingApplications(applications);
        if (pendingBookings.isEmpty()) {
            System.out.println("No applicants applied for booking.");
            return;
        }
        System.out.println("Applications for booking:");
        printApplications.printList(pendingBookings);

        // 3. Select application
        System.out.print("Enter the NRIC of the applicant: ");
        String nric = sc.next();
        BTOApplication application = applicationService.getApplicationByNRIC(nric);
        if (application == null) {
            System.out.println("Applicant not found.");
            return;
        } else if (application.getStatus() != ApplicationStatus.PENDING_BOOKING) {
            System.out.println("Applicant did not apply for booking.");
            return;
        }

        // 4. Confirm booking for applicant
        boolean success = projectService.bookFlat(application, null, null, officer);
        if (success) {
            System.out.println("Flat successfully booked for " + application.getApplicant().getName() + "!");
            System.out.println("Flat type: " + application.getFlatType().getNumRooms() + "-Room");
            System.out.println("Project: " + application.getProjectName());
        } else {
            System.out.println("No more units available of type " + application.getFlatType().getNumRooms() + "-Room!");
        }
    }

    /**
     * Generates a booking receipt for a selected applicant, if the booking was successful.
     *
     * @param sc      {@code Scanner} for input
     * @param officer Officer generating the receipt
     */
    private void generateReceipt(Scanner sc, Officer officer) {
        // Print applications
        Map<String, BTOApplication> applications = retrieveApplications(officer);
        if (applications == null) {
            return; // Error message already printed in retrieveApplications()
        }
        List<BTOApplication> bookedApplications = Filter.filterBookedApplications(applications);
        if (bookedApplications.isEmpty()) {
            System.out.println("No booked applications.");
            return;
        }
        System.out.println("Booked applications:");
        printApplications.printList(bookedApplications);

        System.out.print("Enter the NRIC of the applicant to generate receipt for: ");
        String nric = sc.next();
        sc.nextLine();
        
        Applicant applicant = applicantService.getApplicant(nric);
        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }

        BTOApplication application = applicationService.getApplicationByNRIC(nric);
        if (application == null) {
            System.out.println("No application found for applicant with NRIC: " + applicant.getNRIC());
            return;
        }
        if (!applicationService.hasAccessToApplication(officer, application)) {
            System.out.println("Officer does not have access to this application!");
        } else {
            Receipt.applicantReceipt(applicant, application, projectService.getAllProjects());
        }
    }

    /**
     * Retrieves applications for the currently active project of a manager or officer.
     *
     * @param user The manager or officer user
     * @return Map of NRIC to {@code BTOApplication}, or {@code null} if invalid
     */
    private Map<String, BTOApplication> retrieveApplications(User user) {
        BTOProject project;
        switch(user.getUserRole()) {
            case MANAGER -> {
                Manager manager = (Manager) user;
                project =  manager.getCurrentProject();
            }
            case OFFICER -> {
                Officer officer = (Officer) user;
                project =  officer.getAssignedProject();
            }
            default -> {
                System.out.println("User is not a manager or officer.");
                return null;
            }
        }

        if (project == null) {
            System.out.println("You are not managing any project.");
            return null;
        }

        Map<String, BTOApplication> applications = project.getApplications();
        if (applications.isEmpty()) {
            System.out.printf("No applications found for your project '%s'.\n", project.getProjectName());
            return null;
        }
        return applications;
    }

    /**
     * Allows a manager to approve or reject applications.
     * Only works on applications with status {@code PENDING}.
     *
     * @param sc         Scanner for input
     * @param manager    Manager processing applications
     * @param isApproval {@code true} to approve, {@code false} to reject
     */
    private void processApplication(Scanner sc, Manager manager, boolean isApproval) {
        Map<String, BTOApplication> applications = retrieveApplications(manager);
        if (applications == null) {
            return; // Error message already printed in retrieveApplications()
        }
        // Filter pending applications
        List<BTOApplication> pendingApplications = Filter.filterPendingApplications(applications);
        if (pendingApplications.isEmpty()) {
            System.out.println("No applications to " + (isApproval ? "approve" : "reject") + ".");
            return;
        }
        System.out.println("Pending applications:");
        printApplications.printList(pendingApplications);

        // Select application
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
            // Approve/Reject application
            if (isApproval) {
                applicationService.approveApplication(application);
            } else {
                applicationService.rejectApplication(application);
            }
            System.out.println("Application " + (isApproval ? "approved" : "rejected") + " successfully.");
        }
    }

    /**
     * Allows a manager to approve or reject withdrawal requests from applicants.
     *
     * @param sc         Scanner for input
     * @param manager    Manager processing withdrawals
     * @param isApproval {@code true} to approve, {@code false} to reject
     */
    private void processWithdrawals(Scanner sc, Manager manager, boolean isApproval) {
        Map<String, BTOApplication> applications = retrieveApplications(manager);
        if (applications == null) {
            return; // Error message already printed in retrieveApplications()
        }
        // Filter withdrawal applications
        List<BTOApplication> withdrawalApplications = Filter.filterWithdrawalApplications(applications);
        if (withdrawalApplications.isEmpty()) {
            System.out.println("No withdrawals to " + (isApproval ? "approve" : "reject") + ".");
            return;
        }
        System.out.println("Pending withdrawal applications:");
        printApplications.printList(withdrawalApplications);
        
        // Select withdrawal application
        System.out.println("Select withdrawal to " + (isApproval ? "approve" : "reject") + " (NRIC). Type 0 to stop: ");
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

            if (!application.getWithdrawal()) {
                System.out.println("Applicant has not requested withdrawal.");
                continue;
            }
            if (application.getStatus() == ApplicationStatus.BOOKED || application.getStatus() == ApplicationStatus.SUCCESSFUL) {
                System.out.println("Application is already successful. Cannot approve/reject withdrawal.");
                continue;
            }

            // Approve/Reject application
            if (isApproval) {
                applicationService.approveWithdrawal(application);
            } else {
                applicationService.rejectWithdrawal(application);
            }
            System.out.println("Withdrawal " + (isApproval ? "approved" : "rejected") + " successfully.");
        }
        
    }

    /**
     * Allows a manager to filter applications based on marital status and flat type.
     * Results are printed using {@code PrintBTOApplications}.
     *
     * @param sc      Scanner for input
     * @param manager The manager using the filter feature
     */
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
        Map<String, BTOApplication> filteredApplications = Filter.filterApplications(applications, maritalStatus, flatType);
        System.out.println("Filtered applications:");
        printApplications.printMap(filteredApplications);
    }
}
