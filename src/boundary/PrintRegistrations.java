package boundary;

import entity.registration.Registration;
import entity.user.Officer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PrintRegistrations implements Print<String, Registration> {
    @Override
    public void printMapList(Map<String, List<Registration>> allRegistrations) {
        if (allRegistrations == null || allRegistrations.isEmpty()) {
            System.out.println("No registrations found");
            return;
        }

        System.out.println("Registration List");
        for (Entry<String, List<Registration>> entry : allRegistrations.entrySet()) {
            String project = entry.getKey();
            List<Registration> registrationList = entry.getValue();
            System.out.printf("Project: %s\n", project);
            printList(registrationList);
        }
    }

    @Override
    public void printList(List<Registration> registrationList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printMap(Map<String, Registration> registrationList) {
        if (registrationList == null || registrationList.isEmpty()) {
            System.out.println("No registrations found");
            return;
        }

        System.out.println("List of registrations:");
        for (Registration registration : registrationList.values()) {
            Officer officer = registration.getOfficer();
            System.out.printf(" - %s (%s)\n", officer.getName(), officer.getNRIC());
        }
    }
}
