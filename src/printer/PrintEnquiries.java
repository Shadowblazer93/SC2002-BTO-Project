package printer;

import entity.enquiry.Enquiry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PrintEnquiries implements Print<Integer, Enquiry> {
    @Override
    public void printMapList(Map<Integer, List<Enquiry>> enquiryList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<Enquiry> enquiryList) {
        if (enquiryList == null || enquiryList.isEmpty()) {
            System.out.println("No enquiries found");
            return;
        }

        // Print sorted list of enquiries by project and ID
        enquiryList.sort(Comparator
            .comparing((Enquiry e) -> e.getProjectName())
            .thenComparing(Enquiry::getID));
        for (Enquiry enquiry : enquiryList) {
            System.out.printf(" - Project: %s | %d: %s\n", enquiry.getProjectName(), enquiry.getID(), enquiry.getMessage());
        }
    }

    @Override
    public void printMap(Map<Integer, Enquiry> enquiryList) {
        if (enquiryList == null || enquiryList.isEmpty()) {
            return;
        }

        List<Enquiry> sortedEnquiries = new ArrayList<>(enquiryList.values());
        // Print sorted list of enquiries by project and ID
        if (sortedEnquiries.size() > 1) {
            sortedEnquiries.sort(Comparator
                .comparing((Enquiry e) -> e.getProjectName())
                .thenComparing(Enquiry::getID));
        }

        String currentProject = "";
        System.out.println("-".repeat(167));
        System.out.printf("| %-30s | %-4s | %-60s | %-60s |", 
            "Project", "ID", "Enquiry", "Reply");
        for (Enquiry enquiry : sortedEnquiries) {
            if (!enquiry.getProjectName().equals(currentProject)) {
                currentProject = enquiry.getProjectName();
                System.out.println("-".repeat(167));
            }
            String message = wrapText(enquiry.getMessage(), 60);
            String reply = wrapText(enquiry.getResponse(), 60);
            System.out.printf("| %-30s | %-4d | %-60s | %-60s |\n", 
                enquiry.getProjectName(), enquiry.getID(), message, reply);
        }
        System.out.println("-".repeat(167));
    }

    private String wrapText(String text, int width) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        int lineLength = 0;

        for (String word : words) {
            if (lineLength + word.length() > width) {
                wrappedText.append("\n");
                lineLength = 0;
            }
            wrappedText.append(word).append(" ");
            lineLength += word.length() + 1;
        }

        return wrappedText.toString().trim();
    }
}
