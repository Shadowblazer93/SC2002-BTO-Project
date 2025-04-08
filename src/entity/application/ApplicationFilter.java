package entity.application;

import entity.project.BTOProject;

// to use the function: List<BTOApplication> allApps = getAllApplicationsSomehow();
// to use the function: List<BTOApplication> filtered = ApplicationFilter.filterByStatus(allApps, "Successful");

/* 
public interface ApplicationFilter {
    
}*/

import java.util.*;
import java.util.stream.Collectors;

public class ApplicationFilter {

    // Filter by Project ID
    public static List<BTOApplication> filterByProjectID(List<BTOApplication> applications, String projectID) {
        return applications.stream()
                .filter(app -> app.getProjectID().equalsIgnoreCase(projectID))
                .collect(Collectors.toList());
    }

    // Filter by Flat Type
    public static List<BTOApplication> filterByFlatType(List<BTOApplication> applications, String flatType) {
        return applications.stream()
                .filter(app -> app.getFlatType().equalsIgnoreCase(flatType))
                .collect(Collectors.toList());
    }

    // Filter by Application Status
    public static List<BTOApplication> filterByStatus(List<BTOApplication> applications, String status) {
        return applications.stream()
                .filter(app -> app.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // Filter by Withdrawal Request
    public static List<BTOApplication> filterByWithdrawalRequest(List<BTOApplication> applications, boolean requested) {
        return applications.stream()
                .filter(app -> app.hasRequestedWithdrawal() == requested)
                .collect(Collectors.toList());
    }

    // Composite filter: project + status + flat type (example)
    public static List<BTOApplication> filterByMultipleCriteria(List<BTOApplication> applications,
                                                                String projectID,
                                                                String status,
                                                                String flatType) {
        return applications.stream()
                .filter(app -> app.getProjectID().equalsIgnoreCase(projectID))
                .filter(app -> app.getStatus().equalsIgnoreCase(status))
                .filter(app -> app.getFlatType().equalsIgnoreCase(flatType))
                .collect(Collectors.toList());
    }
}