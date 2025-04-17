package controller;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.registration.Registration;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.RegistrationStatus;
import java.util.*;
import java.util.stream.Collectors;

public class Filter {

    // Filter applications by marital status, flat type
    public static Map<String, BTOApplication> filterApplications(Map<String, BTOApplication> applications, String maritalStatus, FlatType flatType) {
        List<BTOApplication> applicationList = applications.values().stream()
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
        // Convert back to map
        Map<String, BTOApplication> filteredMap = new HashMap<>();
        for (BTOApplication application : applicationList) {
            filteredMap.put(application.getApplicant().getNRIC(), application);
        }
        return filteredMap;
    }

    public static List<BTOProject> filterVisibleProjects(Map<String, BTOProject> projects) {
        return projects.values().stream()
                .filter(BTOProject::isVisible)
                .sorted(Comparator.comparing(BTOProject::getProjectName))
                .collect(Collectors.toList());
    }

    public static List<Registration> filterPendingRegistrations(List<Registration> registrations) {
        return registrations.stream()
                .filter(r -> r.getStatus().equals(RegistrationStatus.PENDING))
                .collect(Collectors.toList());
    }

    public static List<Registration> filterApprovedRegistrations(List<Registration> registrations) {
        return registrations.stream()
                .filter(r -> r.getStatus().equals(RegistrationStatus.APPROVED))
                .collect(Collectors.toList());
    }

    public static List<BTOApplication> filterPendingApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.PENDING))  // Pending status and not withdrawals
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    public static List<BTOApplication> filterWithdrawalApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(BTOApplication::getWithdrawal)
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    public static List<BTOApplication> filterPendingBookingApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.PENDING_BOOKING))
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    public static List<BTOApplication> filterBookedApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.BOOKED))
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }
}