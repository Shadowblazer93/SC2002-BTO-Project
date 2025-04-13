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

    // Check if project is open to applications
    public boolean isProjectOpen(BTOProject project) {
        LocalDate today = LocalDate.now();
        return project.getOpeningDate().isBefore(today) && project.getClosingDate().isAfter(today);
    }

    // Check if flats available for flat type
    public boolean flatTypeAvailable(BTOProject project, FlatType flatType) {
        return project.getUnitCounts().get(flatType) > 0;
    }

    /*
     * Methods to edit project details
     * @param manager       Manager in charge of project
     * @param (value)       Value to replace (E.g. neighbourhood, opening date, etc.)
     * @param projectEdit   Project to edit
     */
    public boolean editProjectName(Manager manager, String currentName, String newName, BTOProject projectEdit) {
        allProjects.remove(currentName);    // Remove project in hashmap
        manager.deleteProject(projectEdit); // Remove project for manager
        projectEdit.setProjectName(newName);
        allProjects.put(newName, projectEdit);
        manager.addProject(projectEdit);
        return true;
    }
    public boolean editNeighbourhood(String neighbourhood, BTOProject projectEdit) {
        projectEdit.setNeighbourhood(neighbourhood);
        return true;
    }
    public boolean editNumUnits(FlatType flatType, int numUnits, BTOProject projectEdit) {
        projectEdit.setNumUnits(flatType, numUnits);
        return true;
    }
    public boolean editOpeningDate(LocalDate openingDate, BTOProject projectEdit) {
        LocalDate closingDate = projectEdit.getClosingDate();
        if (openingDate.isAfter(closingDate)) {
            return false;
        }
        projectEdit.setOpeningDate(openingDate);
        return true;
    }
    public boolean editClosingDate(LocalDate closingDate, BTOProject projectEdit) {
        LocalDate openingDate = projectEdit.getOpeningDate();
        if (closingDate.isBefore(openingDate)) {
            return false;
        }
        projectEdit.setClosingDate(closingDate);
        return true;
    }
    public boolean editVisibility(boolean visible, BTOProject projectEdit) {
        projectEdit.setVisible(visible);
        return true;
    }

    // Increment flat count by flat type for a given project
    public boolean incrementFlatCount(BTOProject projectName, String flatType) {
        if (projectName == null) {
            System.out.println("Project not found.");
            return false;
        }
        try {
            FlatType type = FlatType.valueOf(flatType.replace("-", "_").toUpperCase());
            int currentCount = projectName.getUnitCounts().getOrDefault(type, 0);
            projectName.getUnitCounts().put(type, currentCount + 1);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid flat type.");
            return false;
        }
    }

    // Decrement flat count by flat type for a given project
    public boolean decrementFlatCount(BTOProject projectName, String flatType) {
        if (projectName == null) {
            System.out.println("Project not found.");
            return false;
        }
        try {
            FlatType type = FlatType.valueOf(flatType.replace("-", "_").toUpperCase());
            int currentCount = projectName.getUnitCounts().getOrDefault(type, 0);
            if (currentCount <= 0) {
                System.out.println("No more units left of this flat type.");
                return false;
            }
            projectName.getUnitCounts().put(type, currentCount - 1);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid flat type.");
            return false;
        }
    }
}
