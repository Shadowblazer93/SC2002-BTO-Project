package boundary;

import entity.application.BTOApplication;
import entity.user.Applicant;
import enums.ApplicationStatus;
import java.util.List;
import java.util.Map;

public class PrintApplications implements Print<String, BTOApplication> {
    @Override
    public void printMapList(Map<String, List<BTOApplication>> allApplications) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            return;
        }

        System.out.println("-".repeat(43));
        System.out.printf("| %-9s | %-9s | %-15s |\n",
            "NRIC", "Flat Type", "Status");
        for (BTOApplication application : applicationList) {
            Applicant applicant = application.getApplicant();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            ApplicationStatus status = application.getStatus();
            System.out.printf("| %-9s | %-9s | %-15s |\n", 
                applicant.getNRIC(), flatType, status);
        }
        System.out.println("-".repeat(43));
    }

    @Override
    public void printMap(Map<String, BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        System.out.println("-".repeat(95));
        System.out.printf("| %-9s | %-20s | %-3s | %-15s | %-20s | %-9s |\n",
            "NRIC", "Name", "Age", "Marital Status", "Project", "Flat Type");
        for (BTOApplication application : applicationList.values()) {
            Applicant applicant = application.getApplicant();
            String project = application.getProjectName();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            System.out.printf("| %-9s | %-20s | %-3d | %-15s | %-20s | %-9s |\n", 
                applicant.getNRIC(), applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(), 
                project, flatType);
        }
        System.out.println("-".repeat(95));
    }
}
