package entity.user;

import entity.btoProject.BTOProject;
import entity.enquiry.Enquiry;

public class Applicant extends User { 
    private BTOProject appliedProject;
    private String applicationStatus;
    private String flatType;
    private Enquiry Enquiries[];

    public void projectView(BTOProject p) {}
    public void projectApply(BTOProject p) {}
    public void projectWithdraw(BTOProject p) {}
    public void enquirySubmit(Enquiry e) {}
    public void enquiryView(Enquiry e) {}
    public void enquiryEdit(Enquiry e) {}
    public void enquiryDelete(Enquiry e) {}
}