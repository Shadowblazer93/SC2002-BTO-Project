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

    public BTOProject createProject(Manager manager, String projectName, String neighbourhood) {
        // Check if project exists
        if (allProjects.containsKey(projectName)) {
            return null;
        }

        BTOProject project = new BTOProject(projectName, manager, neighbourhood);
        allProjects.put(projectName, project);
        manager.addProject(project);
        return project;
    }

    public boolean editProject(Manager manager, String projectName, String attribute, String value) {
        BTOProject projectEdit = manager.getManagedProjects().get(projectName);
        if (projectEdit == null) {  // Project not found
            return false;
        }

        switch (attribute) {
            case "NAME" -> {    // Edit project name
                allProjects.remove(projectName);    // Remove project in hashmap
                manager.deleteProject(projectEdit); // Remove project for manager
                projectEdit.setProjectName(value);
                allProjects.put(value, projectEdit);
                manager.addProject(projectEdit);
            }
            case "NEIGHBOURHOOD" -> { // Edit neighbourhood
                projectEdit.setNeighbourhood(value);
            }
            case "FLAT_TYPE" -> { // Edit flat type

            }
            case "NUM_UNITS" -> {

            }
            case "OPENING_DATE" -> {

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

    public boolean projectExist(String projectName) {
        return allProjects.containsKey(projectName);
    }
}
