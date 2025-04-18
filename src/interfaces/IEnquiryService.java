package interfaces;

import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.user.Applicant;
import enums.EnquiryStatus;
import java.util.List;
import java.util.Map;

public interface IEnquiryService {

    void addEnquiry(Enquiry enquiry);
    
    void removeEnquiry(Enquiry enquiry);
    
    Map<Integer, Enquiry> getAllEnquiries();
    
    Enquiry getEnquiryByID(int id);
    
    List<Enquiry> getEnquiriesByNRIC(String nric);

    Enquiry createEnquiry(int id, String applicantNRIC, String projectName, String message, 
                        String response, EnquiryStatus status);
    
    void submitEnquiry(Applicant applicant, BTOProject project, String message);

    void deleteEnquiry(Applicant applicant, Enquiry enquiry);
    
    void editEnquiry(Enquiry enquiry, String newMessage);
    
    void replyEnquiry(BTOProject project, int enquiryId, String reply);

}
