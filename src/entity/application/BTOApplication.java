package entity.application;

import java.util.HashMap;
import java.util.Map;

import entity.project.BTOProject;
import enums.ApplicationStatus;
import enums.FlatType;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;

public class BTOApplication {
    private String applicantNRIC;
    private String projectID;
    private String flatType;
    private ApplicationStatus status; // PENDING, SUCCESSFUL, UNSUCCESSFUL, BOOKED,
    private boolean hasRequestedWithdrawal;

    public BTOApplication(String nric, String projectID, String flatType) {
        this.applicantNRIC = nric;
        this.projectID = projectID;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.hasRequestedWithdrawal = false;
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getFlatType() {
        return flatType;
    }

    public void requestWithdrawal() {
        this.hasRequestedWithdrawal = true;
    }

    public void cancelWithdrawal() {
        this.hasRequestedWithdrawal = false;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public boolean hasRequestedWithdrawal() {
        return hasRequestedWithdrawal;
    }
}

class ApplicationManager {
    private Map<String, BTOApplication> applications;
    private Manager projectManager; // assumed to handle visibility, flat availability, etc.
    private Manager userManager; // assumed to store user details like age and marital status

    public ApplicationManager(ProjectManager projectManager, UserManager userManager) {
        this.applications = new HashMap<>();
        this.projectManager = projectManager;
        this.userManager = userManager;
    }

    public boolean applyForBTO(String nric, String projectID, String flatType) {
        if (applications.containsKey(nric)) {
            System.out.println("You have already applied for a project.");
            return false;
        }

        if (!projectManager.isProjectVisibleAndOpen(projectID)) {
            System.out.println("Project is not open or visible.");
            return false;
        }

        int age = userManager.getUserAge(nric);
        String status = userManager.getMaritalStatus(nric);

        if (status.equals("Single") && age < 35) {
            System.out.println("Single applicants must be 35 or older.");
            return false;
        }
        if (status.equals("Married") && age < 21) {
            System.out.println("Married applicants must be 21 or older.");
            return false;
        }
        if (status.equals("Single") && !flatType.equals("2-Room")) {
            System.out.println("Single applicants can only apply for 2-Room flats.");
            return false;
        }

        BTOApplication app = new BTOApplication(nric, projectID, flatType);
        applications.put(nric, app);
        System.out.println("Application submitted successfully.");
        return true;
    }

    public BTOApplication viewApplicationStatus(String nric) {
        return applications.get(nric);
    }

    public void requestWithdrawal(String nric) {
        BTOApplication app = applications.get(nric);
        if (app == null) {
            System.out.println("No application found.");
            return;
        }
        app.requestWithdrawal();
        System.out.println("Withdrawal requested.");
    }

    public void approveApplication(String nric) {
        BTOApplication app = applications.get(nric);
        if (app != null && app.getStatus() == ApplicationStatus.PENDING) {
            if (projectManager.hasAvailableFlat(app.getProjectID(), app.getFlatType())) {
                app.setStatus(ApplicationStatus.SUCCESSFUL);
                System.out.println("Application approved.");
            } else {
                app.setStatus(ApplicationStatus.UNSUCCESSFUL);
                System.out.println("Application rejected due to unavailability.");
            }
        }
    }

    public void rejectApplication(String nric) {
        BTOApplication app = applications.get(nric);
        if (app != null && app.getStatus() == ApplicationStatus.PENDING) {
            app.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application rejected.");
        }
    }

    public boolean bookFlat(String nric, String flatType) {
        BTOApplication app = applications.get(nric);
        if (app == null || app.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("Booking not allowed. You must have a successful application.");
            return false;
        }
        if (!projectManager.hasAvailableFlat(app.getProjectID(), flatType)) {
            System.out.println("Selected flat type is no longer available.");
            return false;
        }
        app.setStatus(ApplicationStatus.BOOKED);
        app.setFlatType(flatType);
        projectManager.decrementFlatCount(app.getProjectID(), flatType);
        ReceiptManager.generateReceipt(nric, app.getProjectID(), flatType, userManager);
        System.out.println("Flat booked successfully.");
        return true;
    }

    public void approveWithdrawal(String nric) {
        BTOApplication app = applications.get(nric);
        if (app != null && app.hasRequestedWithdrawal()) {
            if (app.getStatus() == ApplicationStatus.BOOKED) {
                projectManager.incrementFlatCount(app.getProjectID(), app.getFlatType());
            }
            applications.remove(nric);
            System.out.println("Withdrawal approved. Application removed.");
        }
    }

    public void rejectWithdrawal(String nric) {
        BTOApplication app = applications.get(nric);
        if (app != null && app.hasRequestedWithdrawal()) {
            app.cancelWithdrawal();
            System.out.println("Withdrawal rejected.");
        }
    }
} 
