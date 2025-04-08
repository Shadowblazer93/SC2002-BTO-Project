package entity.user;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import enums.FlatType;
import enums.UserRole;

import java.util.Scanner;

public class Applicant extends User { 
    private BTOProject appliedProject;
    private String applicationStatus;
    private FlatType flatType;
    private List<Enquiry> enquiries;
    private UserRole role = UserRole.APPLICANT;
    private int maxEnqId;

    public Applicant(String UserID, String name, int age, String maritalStatus, String password, BTOProject appliedProject, String applicationStatus){
        super(UserID, name, password, age, maritalStatus);
        this.appliedProject = appliedProject;
        this.applicationStatus = applicationStatus;
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

    public BTOProject getAppliedProject(){
        return appliedProject;
    }

    public void updateStatus(String newStatus){
        this.applicationStatus = newStatus;
    }

    // public void updateflatType(String newflatType){
    //     this.flatType = newflatType;
    // }

    public void projectView(BTOProject p) {System.out.println(p);}

    public void projectApply(BTOProject p) {
        if (this.appliedProject!=null) {
            System.out.println("You have already applied for a project. Withdraw your current application before applying for a new one.");
            return;
        }

        // BTOApplication application = new BTOApplication(this.getUserID(),this.getAppliedProject(),this.getFlatTypeString());
    }

    public void projectWithdraw(BTOProject p) {

    }

    public void enquirySubmit() {
        // add id assignment to Enquiries
        this.maxEnqId+=1;
        System.out.println("Enter enquiry message: ");
        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        Enquiry enq = new Enquiry(maxEnqId,super.getUserID(),this.appliedProject,msg);
        this.enquiries.add(enq);
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
        }

        System.out.println(enq);
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
        }
        enq.editMessage(newMessage);
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
        }

        enquiries.remove(enq);
    }
}