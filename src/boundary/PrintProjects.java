package boundary;

import entity.project.BTOProject;
import java.util.List;
import java.util.Map;

public class PrintProjects implements Print<String, BTOProject> {
    @Override
    public void printMap(Map<String, BTOProject> projectList) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("No projects created");
            return;
        }

        System.out.println("BTO Project List:");
        for (BTOProject project : projectList.values()) {
            System.out.printf(" - %s\n",project.getProjectName());
        }
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
