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

    public static void addApplication(BTOApplication application) {
        applicationDatabase.put(application.getApplicant().getNRIC(), application);
    }

    public Map<String, BTOApplication> getAllApplications() {
        return applicationDatabase;
    }

    public static BTOApplication getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }

    // Check eligibility for application
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

    public void applyProject(Applicant applicant, BTOProject project, FlatType flatType) {
        // Create application
        BTOApplication application = new BTOApplication(applicant, project, flatType);
        applicant.setApplication(application);
        addApplication(application);
    }

    public void requestWithdrawal(Applicant applicant) {
        BTOApplication application = applicant.getApplication();
        application.setWithdrawal(true);
    }

    public void approveApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
    }

    public void rejectApplication(BTOApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    public void approveWithdrawal(BTOApplication application) {
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.getApplicant().removeApplication(); // Remove application from applicant
    }

    public void rejectWithdrawal(BTOApplication application) {
        application.setWithdrawal(false);   // Remove withdrawal request
    }
}
