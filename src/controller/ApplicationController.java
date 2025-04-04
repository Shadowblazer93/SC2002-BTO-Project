package controller;

import entity.application.Application;
import enums.ApplicationStatus;
import java.util.HashMap;
import java.util.Map;

public class ApplicationController {
    // Store applications by applicant NRIC
    private static Map<String, Application> applicationDatabase = new HashMap<>();
    
    // Add application to the database
    public static void addApplication(Application application) {
        applicationDatabase.put(application.getApplicantNRIC(), application);
    }
    
    // Retrieve application by NRIC
    public static Application getApplicationByNRIC(String nric) {
        return applicationDatabase.get(nric);
    }
}