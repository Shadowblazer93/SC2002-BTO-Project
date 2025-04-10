package controller;

import entity.enquiry.Enquiry;
import java.util.ArrayList;
import java.util.List;

public class EnquiryController {
    private static List<Enquiry> allEnquiries = new ArrayList<>();  // Store all enquiries
    private static int enquiryCount = 0;                            // Track enquiry ID

    public int getEnquiryCount() {
        return enquiryCount;
    }
}
