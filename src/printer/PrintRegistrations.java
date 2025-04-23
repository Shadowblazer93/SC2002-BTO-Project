package printer;

import entity.registration.Registration;
import entity.user.Officer;
import enums.defColor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of PrintList and PrintMapList interfaces for printing Registration data.
 * Provides methods to print registration information in various formats.
 */
public class PrintRegistrations implements PrintList<Registration>, PrintMapList<String, Registration> {
    
    /**
     * Prints a formatted table of registrations from a map structure.
     * Each entry displays the project name, registration ID, officer NRIC and registration status.
     * 
     * @param allRegistrations Map where key is the project name and value is list of Registration objects
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
     * Prints a formatted table of registrations from a list structure.
     * Each entry displays the project name, registration ID, officer NRIC and registration status.
     * 
     * @param registrationList List of Registration objects
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
