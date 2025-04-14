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
            System.out.println("No applications");
            return;
        }

        System.out.println("List of applications:");
        System.out.println("-".repeat(96));
        System.out.printf("| %-9s | %-20s | %-3s | %-15s | %-20s | %-10s |\n",
            "NRIC", "Name", "Age", "Marital Status", "Project", "Flat Type");
        for (BTOApplication application : applicationList) {
            Applicant applicant = application.getApplicant();
            String project = application.getProjectName();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            System.out.printf("| %-9s | %-20s | %-3d | %-15s | %-20s | %-10s |\n", 
                applicant.getNRIC(), applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(), 
                project, flatType);
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
            if (application.getStatus() == ApplicationStatus.BOOKED || application.getStatus() == ApplicationStatus.SUCCESSFUL) {
                continue;   // Skip applications that are booked or successful
            }
            System.out.printf(" - NRIC: %s | Flat Type: %s\n", application.getApplicant().getNRIC(), application.getFlatType());
        }
    }

    public void printWithdrawals(Map<String, BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        for (BTOApplication application : applicationList.values()) {
            if (application.getStatus() == ApplicationStatus.BOOKED || application.getStatus() == ApplicationStatus.SUCCESSFUL || !application.getWithdrawal()) {
                continue;   // Skip applications that have not requested withdrawal
            }
            System.out.printf(" - NRIC: %s | Flat Type: %s\n", application.getApplicant().getNRIC(), application.getFlatType());
        }
    }
}
