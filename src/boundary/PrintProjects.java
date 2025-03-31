package boundary;

import entity.project.BTOProject;
import java.util.Map;

public class PrintProjects implements PrintList<BTOProject> {
    @Override
    public void print(Map<String, BTOProject> projectList) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("No projects created");
            return;
        }

        System.out.println("BTO Project List");
        for (BTOProject project : projectList.values()) {
            System.out.println(project.getProjectName());
        }
    }
}
