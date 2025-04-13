package controller.user;

import controller.ApplicationController;
import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import entity.user.Officer;
import enums.ApplicationStatus;
import enums.FlatType;
import java.util.HashMap;
import java.util.Map;

public class OfficerController {
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer

    public Officer createOfficer(String nric, String name, String password, int age, String maritalStatus) {
        Officer officer = new Officer(nric, name, password, age, maritalStatus);
        allOfficers.put(nric, officer);
        return officer;
    }

    public Officer getOfficer(String nric) {
        return allOfficers.get(nric);
    }

    public Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }

    // Check if officer is eligible for registration of project
    public String canRegisterProject(Officer officer) {   
        String message = "success";
        // Already assigned to project
        if (officer.getAssignedProject() != null) {
            message = "You are already assigned to a project: " + officer.getAssignedProject().getProjectName();
            return message;
        } else if (false) {
            // Check if have applied for any project as applicant
        }
        return message;
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
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different project!");
        } else {
            applicant.updateStatus(status);
            System.out.println("Applicant status updated!");
        }
    }

    public static void updateApplicantProfile(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
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
    }
    public static void updateRemainingFlats(Officer officer, Applicant applicant){
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
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

        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);

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
