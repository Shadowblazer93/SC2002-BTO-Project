package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.*;
import enums.ApplicationStatus;
import enums.FlatType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BTOProjectController {
    // Hashmap to store projects
    private static Map<String, BTOProject> allProjects = new HashMap<>();

    public static Map<String, BTOProject> getAllProjects() {
        return allProjects;
    }

    public static BTOProject getProjectByName(String projectName) {
        return allProjects.get(projectName);
    }

    public static BTOProject createProject(Manager manager, String projectName, String neighbourhood, 
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
        if (project.getOpeningDate().isAfter(LocalDate.now()) && project.getClosingDate().isAfter(LocalDate.now())) {
            manager.setCurrentProject(project); // Set current project if ongoing
        }
        return project;
    }

    public static boolean deleteProject(Manager manager, String projectName) {
        BTOProject projectDelete = manager.getManagedProjects().get(projectName);
        if (projectDelete == null) {    // Project not found
            return false;
        }

        allProjects.remove(projectName);
        manager.deleteProject(projectDelete);

        return true;
    }

    public static boolean projectExist(String projectName) {
        return allProjects.containsKey(projectName);
    }

    // Check if project is open to applications
    public static boolean isProjectOpen(BTOProject project) {
        LocalDate today = LocalDate.now();
        return project.getOpeningDate().isBefore(today) && project.getClosingDate().isAfter(today);
    }

    // Check if flats available for flat type
    public static boolean flatTypeAvailable(BTOProject project, FlatType flatType) {
        return project.getUnitCounts().get(flatType) > 0;
    }

    /*
     * Methods to edit project details
     * @param manager       Manager in charge of project
     * @param (value)       Value to replace (E.g. neighbourhood, opening date, etc.)
     * @param projectEdit   Project to edit
     */
    public static boolean editProjectName(Manager manager, String currentName, String newName, BTOProject projectEdit) {
        allProjects.remove(currentName);    // Remove project in hashmap
        manager.deleteProject(projectEdit); // Remove project for manager
        projectEdit.setProjectName(newName);
        allProjects.put(newName, projectEdit);
        manager.addProject(projectEdit);
        return true;
    }
    public static boolean editNeighbourhood(String neighbourhood, BTOProject projectEdit) {
        projectEdit.setNeighbourhood(neighbourhood);
        return true;
    }
    public static boolean editNumUnits(FlatType flatType, int numUnits, BTOProject projectEdit) {
        projectEdit.setNumUnits(flatType, numUnits);
        return true;
    }
    public static boolean editOpeningDate(LocalDate openingDate, BTOProject projectEdit) {
        LocalDate closingDate = projectEdit.getClosingDate();
        if (openingDate.isAfter(closingDate)) {
            return false;
        }
        projectEdit.setOpeningDate(openingDate);
        return true;
    }
    public static boolean editClosingDate(LocalDate closingDate, BTOProject projectEdit) {
        LocalDate openingDate = projectEdit.getOpeningDate();
        if (closingDate.isBefore(openingDate)) {
            return false;
        }
        projectEdit.setClosingDate(closingDate);
        return true;
    }
    public static boolean editVisibility(boolean visible, BTOProject projectEdit) {
        projectEdit.setVisible(visible);
        return true;
    }

    // Applicant books flat in project
    public static boolean bookFlat(BTOApplication application, BTOProject project, FlatType flatType, User user) {
        switch (user.getUserRole()) {
            case APPLICANT -> {
                boolean success = project.decrementFlatCount(flatType);
                if (success) {
                    application.setStatus(ApplicationStatus.PENDING_BOOKING);
                    return true;
                } else {
                    return false;
                }
            }
            case OFFICER -> {
                // Flat count already decremented when applicant booked flat
                application.setStatus(ApplicationStatus.BOOKED);    // Set status as booked
                application.getApplicant().updateFlatType(flatType);// Update applicant's flat type
                return true;
            }
            default -> {
                return false;
            }
        }
        
    }
}
