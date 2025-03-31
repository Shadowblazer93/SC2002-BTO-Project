package controller;

import entity.project.BTOProject;
import entity.user.Manager;
import java.util.HashMap;
import java.util.Map;

public class BTOProjectController {
    // Hashmap to store projects
    private static Map<String, BTOProject> allProjects = new HashMap<>();

    public BTOProjectController() {
    }

    public BTOProject createProject(Manager manager, String projectName) {
        BTOProject project = new BTOProject(projectName, manager);
        allProjects.put(projectName, project);
        manager.addProject(project);
        return project;
    }

    public boolean editProject(Manager manager, String projectName, int choice, String value) {
        BTOProject projectEdit = manager.getManagedProjects().get(projectName);
        if (projectEdit == null) {  // Project not found
            return false;
        }

        switch (choice) {
            case 1 -> { // Edit project name
                allProjects.remove(projectName);    // Remove project in hashmap
                projectEdit.setProjectName(value);
                allProjects.put(value, projectEdit);
            }
            case 2 -> { // Edit neighbourhood
                projectEdit.setNeighbourhood(value);
            }
            case 3 -> { // Edit flat type

            }
            default -> throw new AssertionError();
        }
        return true;
    }

    public boolean deleteProject(Manager manager, String projectName) {
        BTOProject projectDelete = manager.getManagedProjects().get(projectName);
        if (projectDelete == null) {    // Project not found
            return false;
        }

        allProjects.remove(projectName);
        manager.deleteProject(projectDelete);

        return true;
    }
}
