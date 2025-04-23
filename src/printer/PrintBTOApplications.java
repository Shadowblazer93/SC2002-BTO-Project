package printer;

import entity.application.BTOApplication;
import entity.user.*;
import enums.ApplicationStatus;
import enums.defColor;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PrintList} interface for printing {@link BTOApplication} data
 * Provides methods to print BTO Applications data in list and map formats
 */
public class PrintBTOApplications implements PrintList<BTOApplication>, PrintMap<String, BTOApplication> {

    /**
     * Print list of BTOApplications with details such as NRIC, Flat Type and application status
     * @param applicationList List of BTOApplications to print
     */
    @Override
    public void printList(List<BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            return;
        }

        System.out.println(defColor.YELLOW + "-".repeat(43));
        System.out.printf("| %-9s | %-9s | %-15s |\n",
            "NRIC", "Flat Type", "Status");
        for (BTOApplication application : applicationList) {
            System.out.println(defColor.YELLOW + "-".repeat(43));
            Applicant applicant = application.getApplicant();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            ApplicationStatus status = application.getStatus();
            System.out.printf("| %-9s | %-9s | %-15s |\n", 
                applicant.getNRIC(), flatType, status);
        }
        System.out.println("-".repeat(43));
        System.out.print(defColor.RESET);
    }

    /**
     * Print map of BTOApplications with information including NRIC, Name, Age, Marital Status,
     * Project name, Flat Type
     * @param applicationList Map of applicant NRIC to their BTOApplication
     */
    @Override
    public void printMap(Map<String, BTOApplication> applicationList) {
        if (applicationList == null || applicationList.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        System.out.println(defColor.YELLOW + "-".repeat(95));
        System.out.printf("| %-9s | %-20s | %-3s | %-15s | %-20s | %-9s |\n",
            "NRIC", "Name", "Age", "Marital Status", "Project", "Flat Type");
        for (BTOApplication application : applicationList.values()) {
            System.out.println(defColor.YELLOW + "-".repeat(95));
            Applicant applicant = application.getApplicant();
            String project = application.getProjectName();
            String flatType = application.getFlatType().getNumRooms() + "-Room";
            System.out.printf("| %-9s | %-20s | %-3d | %-15s | %-20s | %-9s |\n", 
                applicant.getNRIC(), applicant.getName(), applicant.getAge(), applicant.getMaritalStatus(), 
                project, flatType);
        }
        System.out.println("-".repeat(95));
        System.out.print(defColor.RESET);
    }
    
}
