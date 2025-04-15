package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.*;
import enums.ApplicationStatus;
import enums.FlatType;
import java.util.HashMap;
import java.util.Map;

public class ApplicationController {
    private static Map<String, BTOApplication> applicationDatabase = new HashMap<>();   // NRIC + Application

    public static BTOApplication createApplication(Applicant applicant, String projectName, FlatType flatType, ApplicationStatus status, boolean withdrawal) {
        return new BTOApplication(applicant, projectName, flatType, status, withdrawal);
    }

    public static void addApplication(BTOApplication application) {
        applicationDatabase.put(application.getApplicant().getNRIC(), application);
    }

    public static void removeApplication(BTOApplication application) {
        applicationDatabase.remove(application.getApplicant().getNRIC());
    }

    public static Map<String, BTOApplication> getAllApplications() {
        return applicationDatabase;
    }

    public static BTOApplication getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }

    // Check eligibility for application
    public static boolean isEligible(Applicant applicant, FlatType flatType) {
        int age = applicant.getAge();
        String status = applicant.getMaritalStatus().toLowerCase();
        if (status.equals("single")) {
            return age >= 35 && flatType.equals(FlatType.TWO_ROOM);
        } else if (status.equals("married")) {
            return age >= 21;
        }
        return false;
    }

    public static BTOApplication applyProject(Applicant applicant, BTOProject project, FlatType flatType) {
        // Create application
        BTOApplication application = createApplication(applicant, project.getProjectName(), flatType, ApplicationStatus.PENDING, false);
        applicant.setApplication(application);
        addApplication(application);
        return application;
    }

    public static void requestWithdrawal(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        application.setWithdrawal(true);
    }

    public static void approveApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
    }

    public static void rejectApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    public static void approveWithdrawal(BTOApplication application) {
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.getApplicant().removeApplication(); // Remove application from applicant
    }

    public static void rejectWithdrawal(BTOApplication application) {
        application.setWithdrawal(false);   // Remove withdrawal request
    }
}
