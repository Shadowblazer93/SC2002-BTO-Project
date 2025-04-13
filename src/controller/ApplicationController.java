package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import enums.ApplicationStatus;
import enums.FlatType;
import entity.application.Application;
import java.util.HashMap;
import java.util.Map;
import entity.user.*;

public class ApplicationController {
    private static Map<String, BTOApplication> applicationDatabase = new HashMap<>();
    //private Manager projectManager;
    private User userManager;
    private BTOProjectController projectController;

    /*public ApplicationController(Manager projectManager, User userManager) {
        this.projectManager = projectManager;
        this.userManager = userManager;
    }*/

    public static void addApplication(BTOApplication application) {
        applicationDatabase.put(application.getApplicantNRIC(), application);
    }

    public static BTOApplication getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }

    public boolean applyForBTO(String nric, BTOProject projectID, String flatType) {
        if (applicationDatabase.containsKey(nric)) {
            System.out.println("You have already applied for a project.");
            return false;
        }

        if (!projectController.isProjectVisibleAndOpen(projectID)) {
            System.out.println("Project is not open or visible.");
            return false;
        }

        int age = userManager.getAge();
        String status = userManager.getMaritalStatus();

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
        addApplication(app);
        System.out.println("Application submitted successfully.");
        return true;
    }

    public void requestWithdrawal(String nric) {
        BTOApplication app = getApplicationByNRIC(nric);
        if (app == null) {
            System.out.println("No application found.");
            return;
        }
        app.requestWithdrawal();
        System.out.println("Withdrawal requested.");
    }

    public boolean approveApplication(BTOApplication application) {
        /*BTOApplication app = getApplicationByNRIC(nric);
        if (app != null && app.getStatus() == ApplicationStatus.PENDING) {
            if (projectController.hasAvailableFlat(app.getProjectID(), app.getFlatType())) {
                app.setStatus(ApplicationStatus.SUCCESSFUL);
                System.out.println("Application approved.");
            } else {
                app.setStatus(ApplicationStatus.UNSUCCESSFUL);
                System.out.println("Application rejected due to unavailability.");
            }
        }*/
        return true;
    }

    public boolean rejectApplication(BTOApplication application) {
        /*BTOApplication app = getApplicationByNRIC(nric);
        if (app != null && app.getStatus() == ApplicationStatus.PENDING) {
            app.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application rejected.");
        }*/
        return true;
    }

    public void approveWithdrawal(String nric) {
        BTOApplication app = getApplicationByNRIC(nric);
        if (app != null && app.hasRequestedWithdrawal()) {
            if (app.getStatus() == ApplicationStatus.BOOKED) {
                projectController.incrementFlatCount(app.getProject(), app.getFlatType());
                // incrementFlatCount() --> update the available flat count for the given flat type within the specified project
            }
            applicationDatabase.remove(nric);
            System.out.println("Withdrawal approved. Application removed.");
        }
    }

    public void rejectWithdrawal(String nric) {
        BTOApplication app = getApplicationByNRIC(nric);
        if (app != null && app.hasRequestedWithdrawal()) {
            app.cancelWithdrawal();
            System.out.println("Withdrawal rejected.");
        }
    }
}
