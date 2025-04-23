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
 * Implements the IApplicationService interface.
 */

public class ApplicationController implements IApplicationService {
    private static Map<String, BTOApplication> applicationDatabase = new HashMap<>();   // NRIC + Application
    private static int applicationCount = 1;

    @Override
    public void addApplication(BTOApplication application) {
        applicationDatabase.put(application.getApplicant().getNRIC(), application);
    }

    @Override
    public void removeApplication(BTOApplication application) {
        applicationDatabase.remove(application.getApplicant().getNRIC());
    }

    @Override
    public Map<String, BTOApplication> getAllApplications() {
        return applicationDatabase;
    }

    @Override
    public BTOApplication getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }

    @Override
    public BTOApplication createApplication(int id, Applicant applicant, String projectName, FlatType flatType, ApplicationStatus status, boolean withdrawal) {
        applicationCount = Math.max(applicationCount,id);
        BTOApplication application = new BTOApplication(applicationCount, applicant, projectName, flatType, status, withdrawal);
        applicationCount++;
        addApplication(application);
        return application;
    }

    // Check eligibility for application
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

    @Override
    public BTOApplication applyProject(Applicant applicant, BTOProject project, FlatType flatType) {
        // Create application
        BTOApplication application = createApplication(0,applicant, project.getProjectName(), flatType, ApplicationStatus.PENDING, false);
        applicant.setApplication(application);
        project.addApplication(application); 
        return application;
    }

    @Override
    public void requestWithdrawal(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        application.setWithdrawal(true);
    }

    @Override
    public void approveApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
    }

    @Override
    public void rejectApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    @Override
    public void approveWithdrawal(BTOApplication application) {
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.getApplicant().removeApplication(); // Remove application from applicant
    }

    @Override
    public void rejectWithdrawal(BTOApplication application) {
        application.setWithdrawal(false);   // Remove withdrawal request
    }

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
