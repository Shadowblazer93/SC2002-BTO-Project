package controller.user;

import controller.ApplicationController;
import controller.BTOProjectController;
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
        // Rule 1: Cannot be applicant for same project
        if (officer.getAssignedProject() != null &&
            officer.getAssignedProject().getProjectName().equals(project.getProjectName())) {
            return "You have already applied for this project. Cannot register as officer.";
        }
    
        // Rule 2: Cannot be registered as officer for overlapping project
        for (BTOProject registered : officer.getRegisteredProjects().values()) {
            boolean overlaps = !(project.getClosingDate().isBefore(registered.getOpeningDate()) ||
                                 project.getOpeningDate().isAfter(registered.getClosingDate()));
            if (overlaps) {
                return "You are already registered as officer for another project in the same period.";
            }
        }
    
        return "success"; // Passed all checks
    }
    
    
    public static boolean hasAccessToApplication(Officer officer, BTOApplication application) {
        if (application == null || officer == null) {
            return false;
        }
        
        BTOProject assignedProject = officer.getAssignedProject();
        return assignedProject != null && 
               application.getProjectName() != null && 
               application.getProjectName().equals(assignedProject.getProjectName());
    }

    public static void updateApplicantStatus(Officer officer, Applicant applicant, ApplicationStatus status) {
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
        
        // Add this null check
        if (application == null) {
            System.out.println("No application found for applicant with NRIC: " + NRIC);
            return;
        }

        String projectName = application.getProjectName();
        BTOProject project = BTOProjectController.getProjectByName(projectName);
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

    public static void bookFlat(Officer officer, Applicant applicant, FlatType flatType) {
        // 1. Check if officer has assigned project
        BTOProject assignedProject = officer.getAssignedProject();
        if (assignedProject == null) {
            System.out.println("Officer has no assigned project!");
            return;
        }
        
        // 2. Check if applicant exists
        String NRIC = applicant.getNRIC();
        BTOApplication application = ApplicationController.getApplicationByNRIC(NRIC);
        
        // 3. If no application exists, create one
        if (application == null) {
            application = ApplicationController.applyProject(applicant, assignedProject, flatType);
        }
        
        // 4. Verify officer has permission
        if (!hasAccessToApplication(officer, application)) {
            System.out.println("Officer is assigned to a different project!");
            return;
        }
        
        // 5. Check flat availability
        Map<FlatType, Integer> unitCounts = assignedProject.getunitCounts();
        int availableUnits = unitCounts.getOrDefault(flatType, 0);
        
        if (availableUnits <= 0) {
            System.out.println("No more units available of type " + flatType.getNumRooms() + "-Room!");
            return;
        }
        
        // 6. Book the flat
        unitCounts.put(flatType, availableUnits - 1);
        application.setStatus(ApplicationStatus.BOOKED);
        applicant.updateFlatType(flatType);
        
        System.out.println("Flat successfully booked for " + applicant.getName() + "!");
        System.out.println("Flat type: " + flatType.getNumRooms() + "-Room");
        System.out.println("Project: " + assignedProject.getProjectName());
    }
}
