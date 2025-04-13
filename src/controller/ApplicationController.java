package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import enums.ApplicationStatus;
import java.util.HashMap;
import java.util.Map;
import entity.user.*;

public class ApplicationController {
    private static Map<String, BTOApplication> applicationDatabase = new HashMap<>();   // NRIC + Application
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

    public boolean bookFlat(String nric, String flatType) {
        BTOApplication app = getApplicationByNRIC(nric);
        if (app == null || app.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("Booking not allowed. You must have a successful application.");
            return false;
        }
        if (!projectController.hasAvailableFlat(app.getProject(), flatType)) {
            System.out.println("Selected flat type is no longer available.");
            return false;
        }
        app.setStatus(ApplicationStatus.BOOKED);
        app.setFlatType(flatType);
        projectController.decrementFlatCount(app.getProject(), flatType);
        System.out.println("Flat booked successfully.");
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

    public static boolean hasAccessToApplication(Officer officer, BTOApplication application) {
        if (application == null || officer == null) {
            return false;
        }
        
        BTOProject assignedProject = officer.getAssignedProject();
        return assignedProject != null && 
               application.getProject() != null && 
               application.getProject().getProjectName().equals(assignedProject.getProjectName());
    }

    public static void updateApplicantStatus(Officer officer, Applicant applicant, ApplicationStatus status) {
        String NRIC = applicant.getNRIC();
        BTOApplication application = getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateStatus(status);
            System.out.println("Applicant status updated!");
        }
    }

    public static void updateApplicantProfile(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = getApplicationByNRIC(NRIC);
        FlatType flatType = application.getFlatType();
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateFlatType(flatType);
            System.out.println("Applicant profile updated!");
        }
    }

    public static BTOApplication retrieveApplicantApplication(Officer officer, Applicant applicant) {
        String NRIC = applicant.getNRIC();
        BTOApplication application = getApplicationByNRIC(NRIC);
        
        if (application == null) {
            System.out.println("No application found for NRIC: " + NRIC);
            return null;
        }
        
        if (hasAccessToApplication(officer, application)) {
            return application;
        } else {
            System.out.println("Officer does not have access to this application!");
            return null;
        }
    }

    public static void updateRemainingFlats(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = getApplicationByNRIC(NRIC);
        BTOProject assignedProject = officer.getAssignedProject();
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different Project!");
        } else {
            FlatType flatType = application.getFlatType();
            Map<FlatType,Integer> unitCounts = assignedProject.getunitCounts();
            unitCounts.put(flatType, unitCounts.get(flatType) - 1); // Can use editNumUnits in BTOProjectController
        }
    }

    public static void generateReceipt(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        String name = applicant.getName();
        int age = applicant.getAge();
        String maritalStatus = applicant.getMaritalStatus();
        FlatType flatType = applicant.getflatType();

        BTOApplication application = getApplicationByNRIC(NRIC);

        BTOProject project = application.getProject();
        String projectName = project.getProjectName();
        String neighbourhood = project.getNeighbourhood();
        Map<FlatType,Integer> unitCounts = project.getunitCounts();
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer does not have access to this application!");
        } else {
            System.out.println("===== BOOKING RECEIPT =====");
            System.out.println("Applicant name: " + name);
            System.out.println("NRIC: " + NRIC);
            System.out.println("Age: " + age);
            System.out.println("Marital status: " + maritalStatus);
            System.out.println("Flat type booked: " + flatType);
            System.out.println("Project name: " + projectName);
            System.out.println("Neighbourhood: " + neighbourhood);
            for (Map.Entry<FlatType, Integer> entry : unitCounts.entrySet()) {
                FlatType ProjectflatType = entry.getKey();
                int numRooms = ProjectflatType.getNumRooms();
                Integer units = entry.getValue();
                System.out.printf("%d-Room has %d units\n", numRooms, units);
            System.out.println("===========================");
            }
        }   
    }
}
