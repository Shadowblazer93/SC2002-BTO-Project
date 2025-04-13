package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import enums.FlatType;
import java.util.*;
import java.util.stream.Collectors;

public class Filter {

    // Filter applications by marital status, flat type
    public static List<BTOApplication> filterApplications(Map<String, BTOApplication> applications, String maritalStatus, FlatType flatType) {
        return applications.values().stream()
            .filter(a -> {
                boolean match = true;
                if (maritalStatus != null) {
                    match &= a.getApplicant().getMaritalStatus().equalsIgnoreCase(maritalStatus);
                }
                if (flatType != null) {
                    match &= a.getFlatType().equals(flatType);
                }
                return match;
            })
            .collect(Collectors.toList());
    }

    public static List<BTOProject> filterVisibleProjects(Map<String, BTOProject> projects) {
        return projects.values().stream()
                .filter(BTOProject::isVisible)
                .sorted(Comparator.comparing(BTOProject::getProjectName))
                .collect(Collectors.toList());
    }


    // Filter by Project ID
    /*public static List<BTOApplication> filterByProjectID(List<BTOApplication> applications, String projectID) {
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
                .filter(app -> app.getStatus().name().equalsIgnoreCase(status))
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
                .filter(app -> app.getStatus().name().equalsIgnoreCase(status))
                .filter(app -> app.getFlatType().equalsIgnoreCase(flatType))
                .collect(Collectors.toList());
    }*/
}