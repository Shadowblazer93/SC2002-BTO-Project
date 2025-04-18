package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.*;
import enums.ApplicationStatus;
import enums.FlatType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for managing BTO projects
 * This class handles the creation, deletion, and management of BTO projects
 */
public class BTOProjectController {

    /**
     * Map to store all BTO projects
     * Key: Project name, Value: BTOProject object
     */
    private static Map<String, BTOProject> allProjects = new HashMap<>();

    /**
     * Get the map of all projects
     * @return Map of all projects
     */
    public static Map<String, BTOProject> getAllProjects() {
        return allProjects;
    }

    /**
     * Retrieve project from map by its name
     * @param projectName Name of BTOProject
     * @return BTOProject with specified name, or null if not found
     */
    public static BTOProject getProjectByName(String projectName) {
        return allProjects.get(projectName);
    }

    /**
     * Create a new BTO project if there isn't already a project with the same name
     * @param manager Manager creating the project
     * @param projectName Name of the project
     * @param neighbourhood Neighbourhood of the project
     * @param unitCounts Map of flat types to their unit counts
     * @param openingDate Opening date of the project
     * @param closingDate Closing date of the project
     * @param availableOfficerSlots Number of available officer slots for the project
     * @return BTOProject object if created successfully, null if project already exists
     */
    public static BTOProject createProject(Manager manager, String projectName, String neighbourhood, 
                                    Map<FlatType, Integer> unitCounts, Map<FlatType, Double> unitPrices, 
                                    LocalDate openingDate, LocalDate closingDate, int availableOfficerSlots) {
        if (allProjects.containsKey(projectName)) {
            return null;
        }

        BTOProject project = new BTOProject(projectName, manager, neighbourhood, unitCounts, unitPrices, openingDate, 
                                            closingDate, availableOfficerSlots);
        allProjects.put(projectName, project);
        manager.addProject(project);
        if (project.getOpeningDate().isAfter(LocalDate.now()) && project.getClosingDate().isAfter(LocalDate.now())) {
            manager.setCurrentProject(project); // Set current project if ongoing
        }
        return project;
    }

    /**
     * Delete project managed by a specific manager
     * @param manager Manager in charge of the project
     * @param projectName Name of the project to be deleted
     * @return True if project deleted successfully, false otherwise
     */
    public static boolean deleteProject(Manager manager, String projectName) {
        BTOProject projectDelete = manager.getManagedProjects().get(projectName);
        if (projectDelete == null) {
            return false;
        }

        allProjects.remove(projectName);
        manager.deleteProject(projectDelete);
        return true;
    }

    /**
     * Check if a project with the specified name exists in system
     * @param projectName Name of the project to check
     * @return True if project exists, false otherwise
     */
    public static boolean projectExist(String projectName) {
        return allProjects.containsKey(projectName);
    }

    /**
     * Check if a project is open for applications
     * @param project BTOProject to check
     * @return True if project is open, false otherwise
     */
    public static boolean isProjectOpen(BTOProject project) {
        LocalDate today = LocalDate.now();
        return project.getOpeningDate().isBefore(today) && project.getClosingDate().isAfter(today);
    }

    /**
     * Check if units are available for a specific flat type in a project
     * @param project BTOProject to check
     * @param flatType Type of flat to check
     * @return True if units are available, false otherwise
     */
    public static boolean flatTypeAvailable(BTOProject project, FlatType flatType) {
        return project.getUnitCounts().get(flatType) > 0;
    }

    /**
     * Check if the specified period overlaps with any existing projects managed by the manager
     * @param manager Manager of projects
     * @param openingDate Opening date of the new project
     * @param closingDate Closing date of the new project
     * @return True if there is an overlap, false otherwise
     */
    public static boolean checkOverlapPeriod(Manager manager, String projectName, LocalDate openingDate, LocalDate closingDate) {
        for (BTOProject project : manager.getManagedProjects().values()) {
            if (project.getProjectName().equals(projectName)) continue;
            if (project.getOpeningDate().isBefore(closingDate) && project.getClosingDate().isAfter(openingDate)) {
                return true;
            }
        }
        return false;
    }

    // Project editing methods

    /**
     * Edit name of a given project
     * @param manager Manager in charge of the project
     * @param currentName Current name of the project
     * @param newName New name for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if project name edited successfully, false otherwise
     */
    public static boolean editProjectName(Manager manager, String currentName, String newName, BTOProject projectEdit) {
        allProjects.remove(currentName);    // Remove project in hashmap
        manager.deleteProject(projectEdit); // Remove project for manager
        projectEdit.setProjectName(newName);
        allProjects.put(newName, projectEdit);
        manager.addProject(projectEdit);
        return true;
    }

    /**
     * Edit neighbourhood of a given project
     * @param neighbourhood New neighbourhood for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if neighbourhood edited successfully, false otherwise
     */
    public static boolean editNeighbourhood(String neighbourhood, BTOProject projectEdit) {
        projectEdit.setNeighbourhood(neighbourhood);
        return true;
    }

    /**
     * Edit unit counts of a given project
     * @param flatType Type of flat to edit
     * @param numUnits New number of units for the flat type
     * @param projectEdit BTOProject object to be edited
     * @return True if unit counts edited successfully, false otherwise
     */
    public static boolean editNumUnits(FlatType flatType, int numUnits, BTOProject projectEdit) {
        projectEdit.setNumUnits(flatType, numUnits);
        return true;
    }

    /**
     * Edit unit prices of a give project
     * @param flatType Type of flat to edit
     * @param price New price for the flat type
     * @param projectEdit BTOProject object to be edited
     * @return True if unit prices edited successfully, false otherwise
     */
    public static boolean editPrice(FlatType flatType, Double price, BTOProject projectEdit) {
        projectEdit.setPrice(flatType, price);
        return true;
    }

    /**
     * Edit project officer slots of a given project
     * @param officerSlots New number of officer slots for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if officer slots edited successfully, false otherwise
     */
    public static boolean editOfficerSlots(int officerSlots, BTOProject projectEdit) {
        int assignedOfficers = projectEdit.getAssignedOfficers().size();
        projectEdit.setAvailableOfficerSlots(assignedOfficers - officerSlots);
        return true;
    }

    /**
     * Edit opening date of a given project
     * @param openingDate New opening date for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if opening date edited successfully, false if it is after closing date
     */
    public static boolean editOpeningDate(LocalDate openingDate, BTOProject projectEdit) {
        LocalDate closingDate = projectEdit.getClosingDate();
        if (openingDate.isAfter(closingDate)) {
            return false;
        }
        projectEdit.setOpeningDate(openingDate);
        return true;
    }

    /**
     * Edit closing date of a given project
     * @param closingDate New closing date for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if closing date edited successfully, false if it is before opening date
     */
    public static boolean editClosingDate(LocalDate closingDate, BTOProject projectEdit) {
        LocalDate openingDate = projectEdit.getOpeningDate();
        if (closingDate.isBefore(openingDate)) {
            return false;
        }
        projectEdit.setClosingDate(closingDate);
        return true;
    }

    /**
     * Edit visibility of a given project
     * @param visible True if project is visible, false otherwise
     * @param projectEdit BTOProject object to be edited
     * @return True if visibility edited successfully, false otherwise
     */
    public static boolean editVisibility(boolean visible, BTOProject projectEdit) {
        projectEdit.setVisible(visible);
        return true;
    }

    // Project flat booking methods

    /**
     * Handle flat booking process based on user role
     * - Applicant: Reserve a flat and set application status to PENDING_BOOKING
     * - Officer: Confirm booking and set application status to BOOKED
     * @param application BTOApplication for booking
     * @param project BTOProject being booked
     * @param flatType Type of flat being booked
     * @param user User making the booking
     * @return True if booking successful, false otherwise
     */
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
