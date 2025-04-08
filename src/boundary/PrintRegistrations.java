package boundary;

import entity.registration.Registration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PrintRegistrations implements Print<Registration> {
    @Override
    public void printMapList(Map<String, List<Registration>> allRegistrations) {
        if (allRegistrations == null) {
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
        if (registrationList == null || registrationList.isEmpty()) {
            System.out.println("No registrations found");
            return;
        }

        System.out.println("Registration List");
        for (Registration registration : registrationList) {
            System.out.printf(" - %s: %s\n", registration.getOfficer().getName(), registration.getRegistrationStatus());
        }
    }

    @Override
    public void printMap(Map<String, Registration> registrationList) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
