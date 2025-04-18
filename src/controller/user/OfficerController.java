package controller.user;

import entity.project.BTOProject;
import entity.user.Officer;
import java.util.HashMap;
import java.util.Map;

public class OfficerController {
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer
    public static Officer createOfficer(String nric, String name, String password, int age, String maritalStatus) {
        Officer officer = new Officer(nric, name, password, age, maritalStatus);
        allOfficers.put(nric, officer);
        return officer;
    }

    public static Officer getOfficer(String nric) {
        return allOfficers.get(nric);
    }

    public static Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }

    public static String registerProject(Officer officer, BTOProject project) {
        String projectName = project.getProjectName();
        // Rule 1: Cannot be applicant for project
        if (officer.getApplication() != null &&
            officer.getApplication().getProjectName() != null &&
            officer.getApplication().getProjectName().equals(project.getProjectName())) {
            return "You are already an applicant for this project. Cannot register as officer.";
        }

        // Rule 2: Cannot be registered for same project
        if (officer.getRegisteredProjects() != null &&
            officer.getRegisteredProjects().get(projectName) != null) {
            return "You have already registered for this project. Cannot register as officer.";
        }
    
        // Rule 3: Cannot be registered as officer for overlapping project
        for (BTOProject registered : officer.getRegisteredProjects().values()) {
            boolean overlaps = !(project.getClosingDate().isBefore(registered.getOpeningDate()) ||
                                 project.getOpeningDate().isAfter(registered.getClosingDate()));
            if (overlaps) {
                return "You are already registered as officer for another project in the same period.";
            }
        }
    
        return "success"; // Passed all checks
    }
    
    
    /*public static boolean hasAccessToApplication(Officer officer, BTOApplication application) {
        if (application == null || officer == null) {
            return false;
        }
        
        BTOProject assignedProject = officer.getAssignedProject();
        return assignedProject != null && 
               application.getProjectName() != null && 
               application.getProjectName().equals(assignedProject.getProjectName());
    }*/

    /*public static void updateApplicantStatus(Officer officer, Applicant applicant, ApplicationStatus status) {
        if (applicant == null) {
            System.out.println("Cannot update status for null applicant");
            return;
        }
        
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (officer.getAssignedProject() == null) {
            System.out.println("Officer has no assigned project!");
        } else if (application == null || application.getProjectName() == null) {
            System.out.println("No valid application found for this applicant!");
        } else if (!officer.getAssignedProject().getProjectName().equals(application.getProjectName())) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateStatus(status);
            System.out.println("Applicant status updated!");
        }
    }*/

    /*public static void updateApplicantProfile(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        FlatType flatType = application.getFlatType();
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateFlatType(flatType);
            System.out.println("Applicant profile updated!");
        }
    }*/

    /*public static BTOApplication retrieveApplicantApplication(Officer officer, Applicant applicant) {
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        
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
    }*/
    /*public static void updateRemainingFlats(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        BTOProject assignedProject = officer.getAssignedProject();
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different Project!");
        } else {
            FlatType flatType = application.getFlatType();
            Map<FlatType,Integer> unitCounts = assignedProject.getUnitCounts();
            unitCounts.put(flatType, unitCounts.get(flatType) - 1); // Can use editNumUnits in BTOProjectController
        }
    }*/
    
}
