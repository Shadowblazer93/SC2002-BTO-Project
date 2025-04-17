package printer;

import entity.registration.Registration;
import entity.user.Officer;
import enums.defColor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PrintRegistrations implements Print<String, Registration> {
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

    @Override
    public void printMap(Map<String, Registration> registrationList) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
