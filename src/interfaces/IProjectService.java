package interfaces;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Manager;
import entity.user.User;
import enums.FlatType;
import java.time.LocalDate;
import java.util.Map;

/**
 * Interface for managing BTO Projects
 */
public interface IProjectService {

    /**
     * Retrieve all BTO Projects
     * @return Map of BTO Project name to BTOProject object
     */
    Map<String, BTOProject> getAllProjects();

    /**
     * Retrieve specific project by its name
     * @param projectName Name of project
     * @return BTOProject object if found, null otherwise
     */
    BTOProject getProjectByName(String projectName);

    /**
     * Create new BTOProject with provided details
     * @param manager Manager creating the project
     * @param projectName Project name
     * @param neighbourhood Project neighbourhood
     * @param unitCounts Map of flat types to their number of units available
     * @param unitPrices Map of flat types to their unit prices
     * @param openingDate Project application opening date
     * @param closingDate Project application closing date
     * @param availableOfficerSlots Number of officers available for the project
     * @return Created BTOProject object
     */
    BTOProject createProject(Manager manager, String projectName, String neighbourhood, 
                            Map<FlatType, Integer> unitCounts, Map<FlatType, Double> unitPrices, 
                            LocalDate openingDate, LocalDate closingDate, int availableOfficerSlots);

    /**
     * Delete a BTOProject
     * @param manager Manager performing the deletion
     * @param projectName Name of project to delete
     * @return true if project successfully deleted, false otherwise
     */
    boolean deleteProject(Manager manager, String projectName);

    /**
     * Check if a project with the given name exists
     * @param projectName Project name
     * @return true if project exists, false otherwise
     */
    boolean projectExist(String projectName);

    /**
     * Check if a project is currently open for applications
     * @param project BTOProject object to check
     * @return true if current date is within project's application period, false otherwise
     */
    boolean isProjectOpen(BTOProject project);

    /**
     * Check if a flat type is available in the given project
     * @param project BTOProject object to check
     * @param flatType Flat Type to check
     * @return true if flat type is available, false otherwise
     */
    boolean flatTypeAvailable(BTOProject project, FlatType flatType);
    
    /**
     * Check for overlapping project periods created by the same manager
     * @param manager Manager creating/editing the project
     * @param projectName Project name
     * @param openingDate Proposed opening date
     * @param closingDate Proposed closing date
     * @return true if overlap exists, false otherwise
     */
    boolean checkOverlapPeriod(Manager manager, String projectName, LocalDate openingDate, LocalDate closingDate);

    /**
     * Handle flat booking process based on user role
     * - Applicant: Reserve a flat and set application status to PENDING_BOOKING
     * - Officer: Confirm booking and set application status to BOOKED
     * @param application BTOApplication for booking
     * @param project BTOProject being booked
     * @param flatType Type of flat being booked
     * @param user User making the booking
     * @return true if booking successful, false otherwise
     */
    boolean bookFlat(BTOApplication application, BTOProject project, FlatType flatType, User user);
    
    /**
     * Edit name of a given project
     * @param manager Manager in charge of the project
     * @param currentName Current name of the project
     * @param newName New name for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if project name edited successfully, false otherwise
     */
    boolean editProjectName(Manager manager, String currentName, String newName, BTOProject projectEdit);

    /**
     * Edit neighbourhood of a given project
     * @param neighbourhood New neighbourhood for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if neighbourhood edited successfully, false otherwise
     */
    boolean editNeighbourhood(String neighbourhood, BTOProject projectEdit);

    /**
     * Edit unit counts of a given project
     * @param flatType Type of flat to edit
     * @param numUnits New number of units for the flat type
     * @param projectEdit BTOProject object to be edited
     * @return True if unit counts edited successfully, false otherwise
     */
    boolean editNumUnits(FlatType flatType, int numUnits, BTOProject projectEdit);

    /**
     * Edit unit prices of a give project
     * @param flatType Type of flat to edit
     * @param price New price for the flat type
     * @param projectEdit BTOProject object to be edited
     * @return True if unit prices edited successfully, false otherwise
     */
    boolean editPrice(FlatType flatType, Double price, BTOProject projectEdit);

    /**
     * Edit project officer slots of a given project
     * @param officerSlots New number of officer slots for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if officer slots edited successfully, false otherwise
     */
    boolean editOfficerSlots(int officerSlots, BTOProject projectEdit);

    /**
     * Edit opening date of a given project
     * @param openingDate New opening date for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if opening date edited successfully, false if it is after closing date
     */
    boolean editOpeningDate(LocalDate openingDate, BTOProject projectEdit);

    /**
     * Edit closing date of a given project
     * @param closingDate New closing date for the project
     * @param projectEdit BTOProject object to be edited
     * @return True if closing date edited successfully, false if it is before opening date
     */
    boolean editClosingDate(LocalDate closingDate, BTOProject projectEdit);

    /**
     * Edit visibility of a given project
     * @param visible True if project is visible, false otherwise
     * @param projectEdit BTOProject object to be edited
     * @return True if visibility edited successfully, false otherwise
     */
    boolean editVisibility(boolean visible, BTOProject projectEdit);

} 
