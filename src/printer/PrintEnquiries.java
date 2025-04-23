package printer;

import entity.enquiry.Enquiry;
import enums.defColor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PrintList} interface for printing {@link Enquiry} data
 * Provides methods to print enquiries in map format
 */
public class PrintEnquiries implements PrintMap<Integer, Enquiry>{

    /**
     * Prints formatted table of enquiries, sorted by project name and enquiry ID.
     * Each enquiry's message and reply are wrapped into 40-character lines for
     * readability.
     * @param enquiryList Map of enquiry IDs to {@link Enquiry} objects
     */
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

        System.out.println(defColor.YELLOW + "-".repeat(116));
        System.out.printf("| %-20s | %-3s | %-40s | %-40s |\n", 
            "Project", "ID", "Enquiry", "Reply");
        for (Enquiry enquiry : sortedEnquiries) {
            System.out.println("-".repeat(116));
            List<String> messageLines = wrapText(enquiry.getMessage(), 40);
            List<String> replyLines = wrapText(enquiry.getResponse(), 40);
            int maxLines = Math.max(messageLines.size(), replyLines.size());

            for (int i = 0; i < maxLines; i++) {
                String projectName = i == 0 ? enquiry.getProjectName() : "";
                String id = i == 0 ? String.valueOf(enquiry.getID()) : "";
                String message = i < messageLines.size() ? messageLines.get(i) : "";
                String reply = i < replyLines.size() ? replyLines.get(i) : "";
                System.out.printf("| %-20s | %-3s | %-40s | %-40s |\n", 
                    projectName, id, message, reply);
            }
        }
        System.out.println("-".repeat(116) + defColor.RESET);
    }

    /**
     * Helper method to wrap text into multiple lines with a specified width
     * @param text Original text to be wrapped
     * @param width Maximum number of characters per line
     * @return List of strings where each string is a wrapped line
     */
    private List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > width) {
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0);
            }
            currentLine.append(word).append(" ");
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
        return lines;
    }

}
