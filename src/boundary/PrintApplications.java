package boundary;

import entity.application.Application;
import java.util.List;
import java.util.Map;

public class PrintApplications implements Print<Application> {
    @Override
    public void printMapList(Map<String, List<Application>> allApplications) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<Application> applicationList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printMap(Map<String, Application> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        System.out.println("List of applications:");
        for (Application application : applicationList.values()) {
            System.out.printf(" - NRIC: %s | Flat Type: %s\n", application.getApplicantNRIC(), application.getflatType());
        }
    }
}
