package entity.user;

import controller.EnquiryController;
import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import enums.ApplicationStatus;
import enums.FlatType;
import enums.UserRole;
import java.util.Scanner;
import controller.BTOProjectController;

public class Applicant extends User { 
    private BTOApplication application;
    private FlatType flatType;
    BTOProjectController projectController = new BTOProjectController();
    // private List<Enquiry> enquiries;
    // private int maxEnqId;

    public Applicant(String nric, String name, int age, String maritalStatus, String password){
        super(nric, name, password, age, maritalStatus, UserRole.APPLICANT);
        this.application = null;

        //if (this.getAge()>=35 && maritalStatus=="single") {flatType = FlatType.TWO_ROOM;}
        //if (this.getAge()>=25 && maritalStatus=="married") {flatType = FlatType.THREE_ROOM;}
    }

    public FlatType getflatType(){
        return flatType;
    }

    public void updateFlatType(FlatType flatType){
        this.flatType = flatType;
    }

    public String getFlatTypeString() {
        if (flatType == FlatType.TWO_ROOM) { return "2-room";}
        else if (flatType == FlatType.THREE_ROOM) {return "3-room";}
        else {return "Unknown flat type";}
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

    public void projectView(BTOProject p) {System.out.println(p);}

    public void updateStatus(ApplicationStatus status) {
        if (this.application==null) {
            System.out.println("You have not applied for any project.");
            return;
        }
        this.application.setStatus(status);
        System.out.println("Application status updated to: " + status);
    }

    public void enquirySubmit(String msg) {
        if (application==null) {
            System.out.println("You have not applied for any project. Please apply for a project before submitting an enquiry.");
            return;
        }
        if (msg==null || msg.isEmpty()) {
            System.out.println("Enquiry message cannot be empty.");
            return;
        }

        // add id assignment to Enquiries
        EnquiryController.incrementEnquiryCount();
        System.out.println("Enter enquiry message: ");
        // Scanner sc = new Scanner(System.in);
        // String msg = sc.nextLine();
        Enquiry enq = new Enquiry(EnquiryController.getEnquiryCount(),super.getNRIC(),this.getApplication().getProject().getProjectName(),msg);
        EnquiryController.addEnquiry(enq);
        String projectName = this.getApplication().getProjectName();
        BTOProject project = projectController.getAllProjects().get(projectName);
        project.addEnquiry(enq);  // Add enquiry to project
        System.out.println("Enquiry submitted successfully!");
    }

    public void enquiryView() {
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
    }

    public void enquiryEdit(int enqID, String newMessage) {
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
    }

    public void enquiryDelete(int enqID) {
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
    }
}