package util;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import interfaces.IProjectService;
import java.util.Map;

/**
 * Utility class for generating booking receipts for BTO Application
 */
public class Receipt {

    private final IProjectService projectService;

    /**
     * Construct Receipt utility object with the specified projectService
     * @param projectService Service used to access BTOProject-related data
     */
    public Receipt(IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Prints booking receipt to console for a given applicant and their application
     * @param applicant
     * @param application
     * @param allProjects
     */
    public static void applicantReceipt(Applicant applicant, BTOApplication application, Map<String, BTOProject> allProjects) {
        String NRIC = applicant.getNRIC();
        String name = applicant.getName();
        int age = applicant.getAge();
        String maritalStatus = applicant.getMaritalStatus();
        FlatType flatType = applicant.getFlatType();

        String projectName = application.getProjectName();
        BTOProject project = allProjects.get(projectName);
        String neighbourhood = project.getNeighbourhood();
        Map<FlatType,Integer> unitCounts = project.getUnitCounts();
        
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
