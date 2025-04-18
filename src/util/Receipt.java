package util;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import interfaces.IProjectService;
import java.util.Map;

public class Receipt {

    private final IProjectService projectService;

    public Receipt(IProjectService projectService) {
        this.projectService = projectService;
    }

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
