package boundary;

import entity.enquiry.Enquiry;
import java.util.List;
import java.util.Map;

public class PrintEnquiries implements Print<Enquiry> {
    @Override
    public void printMapList(Map<String, List<Enquiry>> enquiryList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printList(List<Enquiry> enquiryList) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void printMap(Map<String, Enquiry> enquiryList) {
        if (enquiryList == null || enquiryList.isEmpty()) {
            System.out.println("No enquiries found");
            return;
        }

        for (Enquiry enquiry : enquiryList.values()) {
            System.out.printf(" - %s: %s\n", enquiry.getID(), enquiry.getMessage());
        }
    }
}
