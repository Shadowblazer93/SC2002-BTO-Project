package printer;

import entity.project.BTOProject;
import entity.user.Applicant;
import enums.FlatType;
import enums.defColor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PrintList} interface for printing {@link BTOProject} data
 * Provides methods to print BTOProject data in list and map formats
 */
public class PrintBTOProjects implements PrintMap<String, BTOProject> {
    
    /**
     * Print map of BTOProjects with information including name, available officer slots,
     * opening date, and closing date
     * @param projectList Map of project names to BTOProject instances
     */
    @Override
    public void printMap(Map<String, BTOProject> projectList) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("No projects created");
            return;
        }

        // Sort by project name
        List<BTOProject> sortedProjects = new ArrayList<>(projectList.values());
        sortedProjects.sort(Comparator
            .comparing((BTOProject p) -> p.getProjectName()));

        System.out.println(defColor.YELLOW + "BTO Project List:");
        System.out.println("-".repeat(68));
        System.out.printf("| %-20s | %-15s | %-10s | %-10s |\n", 
            "Project Name", "Available Slots", "Opening", "Closing");	
        for (BTOProject project : sortedProjects) {
            System.out.println("-".repeat(68));
            System.out.printf("| %-20s | %-15s | %-10s | %-10s |\n", 
                project.getProjectName(), project.getAvailableOfficerSlots(), project.getOpeningDate(), project.getClosingDate());
        }
        System.out.println("-".repeat(68) + defColor.RESET);
    }

    /**
     * Prints customised list of BTOProjects available for an applicant based on their
     * eligibility. Displays project names, unit availability and prices for each 
     * eligible flat type
     * @param projectList List of BTOProject instances to be printed
     * @param applicant Applicant whos eligibility will filter the displayed data
     */
    public void printList(List<BTOProject> projectList, Applicant applicant) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("There are no projects available.");
            return;
        }
        List<FlatType> eligibleTypes = applicant.getEligibleFlatTypes();
        if (eligibleTypes == null || eligibleTypes.isEmpty()) {
            System.out.println("You are not eligible for any flat types.");
            return;
        }
        System.out.println(defColor.YELLOW + "BTO Project List:");
        // Print header
        StringBuilder header = new StringBuilder();
        int width = 34;
        header.append(String.format("| %-30s |", "Project Name"));
        if (eligibleTypes.contains(FlatType.TWO_ROOM)) {
            header.append(String.format(" %-6s | %-13s |", "2-Room", "Price"));
            width += 25;
        }
    
        if (eligibleTypes.contains(FlatType.THREE_ROOM)) {
            header.append(String.format(" %-6s | %-13s |", "3-Room", "Price"));
            width += 25;
        }
        System.out.println("-".repeat(width));
        System.out.println(header);
        // Print projects
        for (BTOProject project : projectList) {
            System.out.println("-".repeat(width));
            System.out.printf("| %-30s |", project.getProjectName());
            Map<FlatType, Integer> unitCounts = project.getUnitCounts();
            Map<FlatType, Double> unitPrices = project.getUnitPrices();
            if (eligibleTypes.contains(FlatType.TWO_ROOM)) {
                System.out.printf(" %-6s | $%-12.2f |", unitCounts.get(FlatType.TWO_ROOM), unitPrices.get(FlatType.TWO_ROOM));
            }
            if (eligibleTypes.contains(FlatType.THREE_ROOM)) {
                System.out.printf(" %-6s | $%-12.2f |", unitCounts.get(FlatType.THREE_ROOM), unitPrices.get(FlatType.THREE_ROOM));
            }
            System.out.println();
        }
        System.out.println("-".repeat(width) + defColor.RESET);
    }
}
