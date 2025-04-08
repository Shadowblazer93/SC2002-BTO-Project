package controller;

import entity.project.BTOProject;
import entity.user.Manager;
import enums.FlatType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BTOProjectController {
    // Hashmap to store projects
    private static Map<String, BTOProject> allProjects = new HashMap<>();

    public BTOProjectController() {
    }

    public Map<String, BTOProject> getAllProjects() {
        return allProjects;
    }

    public BTOProject createProject(Manager manager, String projectName, String neighbourhood, 
                                    Map<FlatType, Integer> unitCounts, LocalDate openingDate, 
                                    LocalDate closingDate, int availableOfficerSlots) {
        // Check if project exists
        if (allProjects.containsKey(projectName)) {
            return null;
        }

        BTOProject project = new BTOProject(projectName, manager, neighbourhood, unitCounts, openingDate, 
                                            closingDate, availableOfficerSlots);
        allProjects.put(projectName, project);
        manager.addProject(project);
        return project;
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

    public boolean editProjectName(Manager manager, String projectName, String newProjectName, BTOProject projectEdit) {
        allProjects.remove(projectName);    // Remove project in hashmap
        manager.deleteProject(projectEdit); // Remove project for manager
        projectEdit.setProjectName(newProjectName);
        allProjects.put(newProjectName, projectEdit);
        manager.addProject(projectEdit);
        return true;
    }

    public boolean editNeighbourhood(Manager manager, String projectName, String neighbourhood, BTOProject projectEdit) {
        projectEdit.setNeighbourhood(neighbourhood);
        return true;
    }

    public boolean editNumUnits(Manager manager, String projectName, FlatType flatType, int numUnits, BTOProject projectEdit) {
        projectEdit.setNumUnits(flatType, numUnits); // Assuming BTOProject has a method to set number of units
        return true;
    }

    public boolean editOpeningDate(Manager manager, String projectName, LocalDate openingDate, BTOProject projectEdit) {
        LocalDate closingDate = projectEdit.getClosingDate();
        if (openingDate.isAfter(closingDate)) {
            return false;
        }
        projectEdit.setOpeningDate(openingDate);
        return true;
    }

    public boolean editClosingDate(Manager manager, String projectName, LocalDate closingDate, BTOProject projectEdit) {
        LocalDate openingDate = projectEdit.getOpeningDate();
        if (closingDate.isBefore(openingDate)) {
            return false;
        }
        projectEdit.setClosingDate(closingDate);
        return true;
    }

    public boolean editVisibility(Manager manager, String projectName, boolean visible, BTOProject projectEdit) {
        projectEdit.setVisible(visible);
        return true;
    }
}
