package entity.user;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.UserRole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Applicant extends User { 
    private BTOApplication application;
    private FlatType flatType; 
    private List<FlatType> eligibleFlatTypes = new ArrayList<>();    // Store flat types that the applicant is eligible for
    private Map<Integer, Enquiry> enquiries; // Store applicant enquiries
    // private List<Enquiry> enquiries;
    // private int maxEnqId;

    public Applicant(String nric, String name, int age, String maritalStatus, String password){
        super(nric, name, password, age, maritalStatus, UserRole.APPLICANT);
        this.application = null;
        this.enquiries = new HashMap<>();

        if (this.getAge()>=35 && maritalStatus.toLowerCase().equals("single")) {
            eligibleFlatTypes.add(FlatType.TWO_ROOM);
        } else if (this.getAge()>=25 && maritalStatus.toLowerCase().equals("married")) {
            eligibleFlatTypes.add(FlatType.TWO_ROOM);
            eligibleFlatTypes.add(FlatType.THREE_ROOM);
        }
    }

    public List<FlatType> getEligibleFlatTypes(){
        return eligibleFlatTypes;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void updateFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public void setApplication(BTOApplication application) {
        this.application = application;
    }

    public BTOApplication getApplication(){
        return application;
    }

    public void removeApplication() {
        this.application = null;
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.put(enquiry.getID(), enquiry);
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry.getID());
    }

    public Enquiry getEnquiry(int id) {
        return enquiries.get(id);
    }

    public Map<Integer, Enquiry> getEnquiries() {
        return enquiries;
    }

    public void projectView(BTOProject p) {System.out.println(p);}

    public void updateStatus(ApplicationStatus status) {
        if (this.application==null) {
            System.out.println("You have not applied for any project.");
            return;
        }
        this.application.setStatus(status);
        System.out.println("Application status updated to: " + status);
    }

    /*public void enquirySubmit(String msg) {
        if (application==null) {
            System.out.println("You have not applied for any project. Please apply for a project before submitting an enquiry.");
            return;
        }
        if (msg==null || msg.isEmpty()) {
            System.out.println("Enquiry message cannot be empty.");
            return;
        }

        // add id assignment to Enquiries
        System.out.println("Enter enquiry message: ");
        // Scanner sc = new Scanner(System.in);
        // String msg = sc.nextLine();
        Enquiry enq = new Enquiry(EnquiryController.getEnquiryCount(),super.getNRIC(),this.getApplication().getProject().getProjectName(),msg);
        EnquiryController.addEnquiry(enq);
        String projectName = this.getApplication().getProjectName();
        BTOProject project = BTOProjectController.getProjectByName(projectName);
        project.addEnquiry(enq);  // Add enquiry to project
        System.out.println("Enquiry submitted successfully!");
    }*/

    /*public void enquiryView() {
        // input enquiry ID
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of enquiry you want to view: ");
        int enqId = sc.nextInt();
        sc.close();
    
        Enquiry enq = EnquiryController.getAllEnquiries().stream()
            .filter(e -> e.id==enqId)
            .findFirst()
            .orElse(null);

        // check if enquiry exists
        if (enq==null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        } else {System.out.println(enq);}
    }*/

    /*public void enquiryEdit(int enqID, String newMessage) {
        // input enquiry ID
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of enquiry you want to view: ");
        int enqId = sc.nextInt();
        sc.close();

        Enquiry enq = EnquiryController.getAllEnquiries().stream()
            .filter(e -> e.id==enqId)
            .findFirst()
            .orElse(null);

        // check if enquiry exists
        if (enq==null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        } else {
            enq.editMessage(newMessage);
            System.out.println("Enquiry message updated successfully!");
        }
    }*/

    /*public void enquiryDelete(int enqID) {
        // input enquiry ID
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of enquiry you want to view: ");
        int enqId = sc.nextInt();
        sc.close();

        Enquiry enq = EnquiryController.getAllEnquiries().stream()
            .filter(e -> e.id==enqId)
            .findFirst()
            .orElse(null);

        // check if enquiry exists
        if (enq==null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        } else {
            EnquiryController.removeEnquiry(enq);
            System.out.println("Enquiry deleted successfully!");
        }
    }*/
}