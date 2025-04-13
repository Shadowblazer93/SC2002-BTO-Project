package boundary;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import java.util.List;
import java.util.Map;

public class PrintApplications implements Print<BTOApplication> {
    @Override
    public void printMapList(Map<String, List<BTOApplication>> allApplications) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications");
            return;
        }

        System.out.println("List of applications:");
        System.out.println("-".repeat(96));
        System.out.printf("| %-9s | %-20s | %-3s | %-15s | %-20s | %-10s |\n",
            "NRIC", "Name", "Age", "Marital Status", "Project", "Flat Type");
        for (BTOApplication application : applicationList) {
            Applicant applicant = application.getApplicant();
            BTOProject project = application.getProject();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            System.out.printf("| %-9s | %-20s | %-3d | %-15s | %-20s | %-10s |\n", 
                applicant.getNRIC(), applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(), 
                project.getProjectName(), flatType);
        }
        System.out.println("-".repeat(96));
    }

    @Override
    public void printMap(Map<String, BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        for (BTOApplication application : applicationList.values()) {
            System.out.printf(" - NRIC: %s | Flat Type: %s\n", application.getApplicantNRIC(), application.getFlatType());
        }
    }
}
