package printer;

import entity.registration.Registration;
import entity.user.Officer;
import enums.defColor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of {@link PrintList} interface for printing {@link Registration} data
 * Provides methods to print registrations in list and map formats
 */
public class PrintRegistrations implements PrintList<Registration>, PrintMapList<String, Registration> {
    
    /**
     * Print formatted table of registrations.
     * Each entry displays the project name, registration ID, officer NRIC and registration status
     * @param allRegistrations Map where key is the project name and the value is a list of {@link Registration} objects
     */
    @Override
    public void printMapList(Map<String, List<Registration>> allRegistrations) {
        if (allRegistrations == null || allRegistrations.isEmpty()) {
            return;
        }

        System.out.println(defColor.YELLOW + "-".repeat(53));
        System.out.printf("| %-20s | %-3s | %-9s | %-8s |\n",
            "Project", "ID", "NRIC", "Status");
        for (Entry<String, List<Registration>> entry : allRegistrations.entrySet()) {
            System.out.println(defColor.YELLOW + "-".repeat(53));
            String project = entry.getKey();
            List<Registration> registrationList = entry.getValue();
            
            for (Registration registration : registrationList) {
                Officer officer = registration.getOfficer();
                System.out.printf("| %-20s | %-3d | %-9s | %-8s |\n",
                    project, registration.getID(), officer.getNRIC(), registration.getStatus());
            }
        }
        System.out.println(defColor.YELLOW + "-".repeat(53) + defColor.RESET);
    }

    /**
     * Print formatted table of registrations.
     * Each entry displays the project name, registration ID, officer NRIC and registration status
     * @param registrationList List of {@link Registration} objects
     */
    @Override
    public void printList(List<Registration> registrationList) {
        if (registrationList == null || registrationList.isEmpty()) {
            return;
        }

        System.out.println(defColor.YELLOW + "-".repeat(53));
        System.out.printf("| %-20s | %-3s | %-9s | %-8s |\n",
            "Project", "ID", "NRIC", "Status");
        for (Registration registration : registrationList) {
            System.out.println(defColor.YELLOW + "-".repeat(53));
            Officer officer = registration.getOfficer();
            System.out.printf("| %-20s | %-3d | %-9s | %-8s |\n",
                registration.getProjectName(), registration.getID(), officer.getNRIC(), registration.getStatus());
            
        }
        System.out.println(defColor.YELLOW + "-".repeat(53) + defColor.RESET);
    }

}
