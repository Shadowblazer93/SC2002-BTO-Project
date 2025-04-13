package entity.user;

import entity.application.Application;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import java.util.ArrayList;
import java.util.List;

import enums.ApplicationStatus;
import enums.FlatType;
import enums.UserRole;

import java.util.Scanner;

public class Applicant extends User { 
    private Application application;
    private FlatType flatType;
    private List<Enquiry> enquiries;
    private int maxEnqId;

    public Applicant(String UserID, String name, int age, String maritalStatus, String password){
        super(UserID, name, password, age, maritalStatus, UserRole.APPLICANT);
        this.application = null;
        // this.applicationStatus = null;
        this.enquiries = new ArrayList<>();
        this.maxEnqId = 0;

        if (this.getAge()>=35 && maritalStatus=="single") {flatType = FlatType.TWO_ROOM;}
        if (this.getAge()>=25 && maritalStatus=="married") {flatType = FlatType.THREE_ROOM;}
    }

    public FlatType getflatType(){
        return flatType;
    }

    public String getFlatTypeString() {
        if (flatType == FlatType.TWO_ROOM) { return "2-room";}
        else if (flatType == FlatType.THREE_ROOM) {return "3-room";}
        else {return "Unknown flat type";}
    }

    public Application getApplication(){
        return application;
    }

    public void projectView(BTOProject p) {System.out.println(p);}

    public void projectApply(BTOProject p) {
        if (this.application!=null) {
            System.out.println("You have already applied for a project. Withdraw your current application before applying for a new one.");
            return;
        }

        this.application = new Application(this.getNRIC(), p, this.flatType, ApplicationStatus.PENDING);
        p.setApplication(application);
        System.out.println("Application submitted successfully!");
    }

    public void projectWithdraw() {
        BTOProject p = this.application.getProject();
        this.application = null;
        p.setApplication(null);
        System.out.println("You have successfully withdrawn from the project.");
    }

    public void enquirySubmit(String msg) {
        // add id assignment to Enquiries
        this.maxEnqId+=1;
        System.out.println("Enter enquiry message: ");
        // Scanner sc = new Scanner(System.in);
        // String msg = sc.nextLine();
        Enquiry enq = new Enquiry(maxEnqId,super.getNRIC(),this.getApplication().getProject(),msg);
        this.enquiries.add(enq);
        System.out.println("Enquiry submitted successfully!");
    }

    public void enquiryView() {
        // input enquiry ID
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of enquiry you want to view: ");
        int enqId = sc.nextInt();
        sc.close();
    
        Enquiry enq = enquiries.stream()
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

        Enquiry enq = enquiries.stream()
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

        Enquiry enq = enquiries.stream()
            .filter(e -> e.id==enqId)
            .findFirst()
            .orElse(null);

        // check if enquiry exists
        if (enq==null) {
            System.out.println("Could not find an enquiry with that ID!");
            return;
        } else {
            enquiries.remove(enq);
            System.out.println("Enquiry deleted successfully!");
        }
    }
}