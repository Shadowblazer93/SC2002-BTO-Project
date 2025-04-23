package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.*;
import enums.ApplicationStatus;
import enums.FlatType;
import interfaces.IApplicationService;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles application-related operations such as applying for BTO, 
 * requesting withdrawals, and viewing application status.
 * Implements the IApplicationService interface. {@code IApplicationService}.
 */

public class ApplicationController implements IApplicationService {

    /** Stores all applications mapped by applicant NRIC. */
    private static Map<String, BTOApplication> applicationDatabase = new HashMap<>();   // NRIC + Application

    /** Tracks the number of applications created (auto-incrementing ID). */
    private static int applicationCount = 1;


    /**
     * Adds a new application to the database.
     * 
     * @param application The BTO application to be added.
     */
    @Override
    public void addApplication(BTOApplication application) {
        applicationDatabase.put(application.getApplicant().getNRIC(), application);
    }

    /**
     * Removes an application from the database.
     * 
     * @param application The application to be removed.
     */
    @Override
    public void removeApplication(BTOApplication application) {
        applicationDatabase.remove(application.getApplicant().getNRIC());
    }

    /**
     * Returns a map of all applications in the system.
     * 
     * @return Map of NRIC to BTOApplication objects.
     */
    @Override
    public Map<String, BTOApplication> getAllApplications() {
        return applicationDatabase;
    }

    /**
     * Retrieves an application based on applicant NRIC.
     * 
     * @param nric The NRIC of the applicant.
     * @return The corresponding BTO application, or {@code null} if not found.
     */
    @Override
    public BTOApplication getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }

    /**
     * Creates a new BTOApplication object, stores it, and returns it.
     * 
     * @param id         The ID to assign (0 to auto-increment).
     * @param applicant  The applicant creating the application.
     * @param projectName The name of the BTO project.
     * @param flatType   The type of flat selected.
     * @param status     Initial status of the application.
     * @param withdrawal Whether a withdrawal has been requested.
     * @return The created BTOApplication object.
     */
    @Override
    public BTOApplication createApplication(int id, Applicant applicant, String projectName, FlatType flatType, ApplicationStatus status, boolean withdrawal) {
        applicationCount = Math.max(applicationCount,id);
        BTOApplication application = new BTOApplication(applicationCount, applicant, projectName, flatType, status, withdrawal);
        applicationCount++;
        addApplication(application);
        return application;
    }

    /**
     * Checks if the applicant is eligible to apply a specified project based on age and marital status.
     *
     * @param applicant The applicant to check.
     * @param flatType  The type of flat they wish to apply for.
     * @return {@code true} if eligible, {@code false} otherwise.
     */
    @Override
    public boolean isEligible(Applicant applicant, FlatType flatType) {
        int age = applicant.getAge();
        String status = applicant.getMaritalStatus().toLowerCase();
        if (status.equals("single")) {
            return age >= 35 && flatType.equals(FlatType.TWO_ROOM);
        } else if (status.equals("married")) {
            return age >= 21;
        }
        return false;
    }

    /**
     * Applies a new application to a given BTO project for a given applicant.
     *
     * @param applicant The applicant applying for the flat.
     * @param project   The BTO project applied for.
     * @param flatType  The selected flat type.
     * @return The newly created application.
     */
    @Override
    public BTOApplication applyProject(Applicant applicant, BTOProject project, FlatType flatType) {
        // Create application
        BTOApplication application = createApplication(0,applicant, project.getProjectName(), flatType, ApplicationStatus.PENDING, false);
        applicant.setApplication(application);
        project.addApplication(application); 
        return application;
    }

    /**
     * Flags the application as withdrawn.
     *
     * @param applicant The applicant requesting withdrawal.
     */
    @Override
    public void requestWithdrawal(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        application.setWithdrawal(true);
    }

    /**
     * Approves a pending application by setting its status to SUCCESSFUL.
     *
     * @param application The application to approve.
     */
    @Override
    public void approveApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
    }

    /**
     * Rejects a pending application by setting its status to UNSUCCESSFUL.
     *
     * @param application The application to reject.
     */
    @Override
    public void rejectApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    /**
     * Approves a withdrawal request. Sets status to WITHDRAWN and 
     * removes the application from the applicant.
     *
     * @param application The application being withdrawn.
     */
    @Override
    public void approveWithdrawal(BTOApplication application) {
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.getApplicant().removeApplication(); // Remove application from applicant
    }

    /**
     * Rejects a withdrawal request and resets the withdrawal flag.
     *
     * @param application The application whose withdrawal was rejected.
     */
    @Override
    public void rejectWithdrawal(BTOApplication application) {
        application.setWithdrawal(false);   // Remove withdrawal request
    }

    /**
     * Checks if a given officer has access to the application, based on project assignment.
     *
     * @param officer     The officer attempting to access.
     * @param application The application in question.
     * @return {@code true} if officer is assigned to the same project, {@code false} otherwise.
     */
    @Override
    public boolean hasAccessToApplication(Officer officer, BTOApplication application) {
        if (application == null || officer == null) {
            return false;
        }
        
        BTOProject assignedProject = officer.getAssignedProject();
        return assignedProject != null && 
               application.getProjectName() != null && 
               application.getProjectName().equals(assignedProject.getProjectName());
    }
}
