package util;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Applicant;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.RegistrationStatus;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The filter class provides utility methods to filter and retrieve specific subsets of 
 * BTO Applications, BTO Projects, and registrations based on various criteria
 */
public class Filter {

    /**
     * Filter BTO Applicatoins based on the applicant's marital status and applied flat type
     * @param applications Map of BTOApplications
     * @param maritalStatus Marital status to filter by, or null to ignore
     * @param flatType Flat type to filter by, or null to ignore
     * @return Map of filtered applications where key is the applicant's NRIC
     */
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

    /**
     * Filter and return list of BTO projects that are visible to applicants
     * @param projects Map of BTO Projects
     * @return Sorted list of visible BTO Projects by project name
     */
    public static List<BTOProject> filterVisibleProjects(Map<String, BTOProject> projects) {
        return projects.values().stream()
                .filter(BTOProject::getVisibility)
                .sorted(Comparator.comparing(BTOProject::getProjectName))
                .collect(Collectors.toList());
    }

    /**
     * Filter and return list of BTOProjects that match an applicant's eligible flat types.
     * Only projects that are open and have available units are considered
     * @param projects Map aof BTOProjects
     * @param applicant Applicant whose eligible flat types are used for filtaering
     * @return Sorted list of projects matching applicant's eligible flat types
     */
    public static List<BTOProject> filterUserGroupProjects(Map<String, BTOProject> projects, Applicant applicant) {
        List<FlatType> flatTypes = applicant.getEligibleFlatTypes();
        if (flatTypes == null || flatTypes.isEmpty()) {
            return new ArrayList<>(); // No flat types available
        }
        return projects.values().stream()
            .filter(p -> 
                p.isOpen() &&
                flatTypes.stream().anyMatch((flatType) -> 
                    p.getUnitCounts().containsKey(flatType) && p.getUnitCounts().get(flatType) > 0
                )
            )
            .sorted(Comparator.comparing(BTOProject::getProjectName))
            .collect(Collectors.toList());
    }

    /**
     * Filater and return list of registrations that are pending approval
     * @param registrations List of all registrations
     * @return List of pending registrations
     */
    public static List<Registration> filterPendingRegistrations(List<Registration> registrations) {
        return registrations.stream()
                .filter(r -> r.getStatus().equals(RegistrationStatus.PENDING))
                .collect(Collectors.toList());
    }

    /**
     * Filter and return list of registrations that have been approved
     * @param registrations List of all registrations
     * @return List of approved registrations
     */
    public static List<Registration> filterApprovedRegistrations(List<Registration> registrations) {
        return registrations.stream()
                .filter(r -> r.getStatus().equals(RegistrationStatus.APPROVED))
                .collect(Collectors.toList());
    }

    /**
     * Filter and return list of BTOApplications that are pending and have not been withdrawn
     * @param applications Map of all BTOApplications
     * @return Sorted list of pending applications by their ID
     */
    public static List<BTOApplication> filterPendingApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.PENDING))  // Pending status and not withdrawals
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    /**
     * Filter and return laist of BTOApplications that have been withdrawn
     * @param applications Map of all BTOApplications
     * @return Sorted list of withdrawn applications by their ID
     */
    public static List<BTOApplication> filterWithdrawalApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(BTOApplication::getWithdrawal)
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    /**
     * Filter and return list of BTOApplications that are pending booking and have not been withdrawn
     * @param applications Map of all BTOApplications
     * @return Sorted list of application with PENDING_BOOKING status by their ID
     */
    public static List<BTOApplication> filterPendingBookingApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.PENDING_BOOKING))
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }

    /**
     * Filter and return list of BTOApplications that are booked and have not been withdrawn
     * @param applications Map of all BTOApplications
     * @return Sorted list of booked applications by their ID
     */
    public static List<BTOApplication> filterBookedApplications(Map<String, BTOApplication> applications) {
        return applications.values().stream()
            .filter(a -> !a.getWithdrawal() && a.getStatus().equals(ApplicationStatus.BOOKED))
            .sorted((a1, a2) -> Integer.compare(a1.getID(), a2.getID()))
            .collect(Collectors.toList());
    }
}