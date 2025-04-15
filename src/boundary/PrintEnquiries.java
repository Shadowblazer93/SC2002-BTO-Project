package boundary;

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
            System.out.println("No enquiries found");
            return;
        }

        List<Enquiry> sortedEnquiries = new ArrayList<>(enquiryList.values());
        // Print sorted list of enquiries by project and ID
        if (sortedEnquiries.size() > 1) {
            sortedEnquiries.sort(Comparator
                .comparing((Enquiry e) -> e.getProjectName())
                .thenComparing(Enquiry::getID));
        }
        for (Enquiry enquiry : sortedEnquiries) {
            System.out.printf(" - Project: %s | %d: %s\n", enquiry.getProjectName(), enquiry.getID(), enquiry.getMessage());
        }
    }
}
