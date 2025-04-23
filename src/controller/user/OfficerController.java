package controller.user;

import entity.project.BTOProject;
import entity.user.Officer;
import interfaces.IOfficerService;
import java.util.HashMap;
import java.util.Map;

/**
 * OfficerController handles the creation and management of Officer entities.
 * It also manages project registration logic for officers.
 * Implements the IOfficerService interface.
 */
public class OfficerController implements IOfficerService {

    /**
     * A map storing all officers using their NRIC as the key.
     */
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer

    /**
     * Creates a new Officer and stores it in the system.
     *
     * @param nric NRIC of the officer
     * @param name Name of the officer
     * @param password Officer's login password
     * @param age Age of the officer
     * @param maritalStatus Marital status of the officer
     * @return The newly created Officer object
     */
    @Override
    public Officer createOfficer(String nric, String name, String password, int age, String maritalStatus) {
        Officer officer = new Officer(nric, name, password, age, maritalStatus);
        allOfficers.put(nric, officer);
        return officer;
    }

    /**
     * Retrieves an Officer by their NRIC.
     *
     * @param nric The NRIC of the officer
     * @return The Officer object if found, null otherwise
     */
    @Override
    public Officer getOfficer(String nric) {
        return allOfficers.get(nric);
    }

    /**
     * Returns a map of all registered officers in the system.
     *
     * @return Map of NRICs to Officer objects
     */
    @Override
    public Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }

    /**
     * Registers an officer to a BTO project based on several validation rules:
     * <ul>
     *   <li>Rule 0: Officer cannot be already assigned to another project</li>
     *   <li>Rule 1: Officer cannot be an applicant for the same project</li>
     *   <li>Rule 2: Officer cannot register multiple times for the same project</li>
     *   <li>Rule 3: Officer cannot register for a project that overlaps with previously registered projects</li>
     * </ul>
     *
     * @param officer The officer attempting to register
     * @param project The BTO project to register for
     * @return A message indicating the result of the registration attempt
     */
    @Override
    public String registerProject(Officer officer, BTOProject project) {
        // Rule 0: Cannot register if already assigned to a project
        if (officer.getAssignedProject() != null) {
            return "You are already assigned to project: " + officer.getAssignedProject().getProjectName() + 
                   ". Cannot register for another project.";
        }

        String projectName = project.getProjectName();

        // Rule 1: Cannot be applicant for project
        if (officer.getApplication() != null &&
            officer.getApplication().getProjectName() != null &&
            officer.getApplication().getProjectName().equals(project.getProjectName())) {
            return "You are already an applicant for this project. Cannot register as officer.";
        }

        // Rule 2: Cannot be registered for same project
        if (officer.getRegisteredProjects() != null &&
            officer.getRegisteredProjects().get(projectName) != null) {
            return "You have already registered for this project. Cannot register as officer.";
        }

        // Rule 3: Cannot be registered as officer for overlapping project
        for (BTOProject registered : officer.getRegisteredProjects().values()) {
            boolean overlaps = !(project.getClosingDate().isBefore(registered.getOpeningDate()) ||
                                 project.getOpeningDate().isAfter(registered.getClosingDate()));
            if (overlaps) {
                return "You are already registered as officer for another project in the same period.";
            }
        }

        return "success"; // Passed all checks
    }

}
