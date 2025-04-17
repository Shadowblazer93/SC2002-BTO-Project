package printer;

import entity.project.BTOProject;
import enums.FlatType;
import enums.defColor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PrintBTOProjects implements Print<String, BTOProject> {
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

    @Override
    public void printMapList(Map<String, List<BTOProject>> projectList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<BTOProject> projectList) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("There are no projects available.");
            return;
        }
        System.out.println(defColor.YELLOW + "BTO Project List:");
        System.out.println("-".repeat(72));
        System.out.printf("| %-50s | %-6s | %-6s |\n", "Project Name", "2-Room", "3-Room");
        System.out.println("-".repeat(72));
        for (BTOProject project : projectList) {
            Map<FlatType, Integer> unitCounts = project.getUnitCounts();
            System.out.printf("| %-50s | %-6d | %-6d |\n", project.getProjectName(), unitCounts.get(FlatType.TWO_ROOM), unitCounts.get(FlatType.THREE_ROOM));
        }
        System.out.println("-".repeat(72) + defColor.RESET);
    }
}
