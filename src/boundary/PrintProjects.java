package boundary;

import entity.project.BTOProject;
import enums.FlatType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PrintProjects implements Print<String, BTOProject> {
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

        System.out.println("BTO Project List:");
        for (BTOProject project : sortedProjects) {
            System.out.printf(" - %s\n",project.getProjectName());
        }
    }

    // Overloading
    public void printVisibleProjects(Map<String, BTOProject> projectList) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("No projects created");
            return;
        }

        // Sort by project name
        List<BTOProject> sortedProjects = new ArrayList<>(projectList.values());
        sortedProjects.sort(Comparator
            .comparing((BTOProject p) -> p.getProjectName()));

        System.out.println("BTO Project List:");
        System.out.println("-".repeat(42));
        System.out.printf("| %-20s | %-6s | %-6s |\n", "Project Name", "2-Room", "3-Room");
        for (BTOProject project : sortedProjects) {
            if (project.isVisible()) {  // Check visibility of project
                Map<FlatType, Integer> unitCounts = project.getUnitCounts();
                System.out.printf("| %-20s | %-6d | %-6d |\n", project.getProjectName(), unitCounts.get(FlatType.TWO_ROOM), unitCounts.get(FlatType.THREE_ROOM));
            }
        }
        System.out.println("-".repeat(42));
    }

    @Override
    public void printMapList(Map<String, List<BTOProject>> projectList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<BTOProject> projectList) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
