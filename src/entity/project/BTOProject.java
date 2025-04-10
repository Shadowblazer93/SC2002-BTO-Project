package entity.project;

import entity.enquiry.Enquiry;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.FlatType;
import java.time.LocalDate;
import java.util.*;

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    private Map<FlatType, Integer> unitCounts;          // Track of flatTypes and numUnits
    private LocalDate openingDate;
    private LocalDate closingDate;
    private final Manager managerInCharge;              // Manager in charge does not change
    private int availableOfficerSlots;
    private boolean visible;
    private Enquiry[] enquiries; 
    private List<Officer> assignedOfficers;                 // List of officers assigned to project
    private Map<String, Registration> pendingRegistrations; // Map of pending registrations (NRIC, Registration)

    public BTOProject(String projectName, Manager manager, String neighbourhood, 
                        Map<FlatType, Integer> unitCounts, LocalDate openingDate, LocalDate closingDate,
                        int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.unitCounts = unitCounts; 
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visible = false;
        this.assignedOfficers = new ArrayList<>();
        this.pendingRegistrations = new HashMap<>();
    }

    // Getters and setters

    public String getProjectName() {
        return projectName;
    }

    public Enquiry[] getEnquiries() {
        return this.enquiries; // Returns the array of enquiries
    }
    public String getNeighbourhood(){
        return neighbourhood;
    }
    public Map<FlatType,Integer> getunitCounts(){
        return unitCounts;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public Manager getManager() {
        return managerInCharge;
    }

    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    public boolean getVisibility() {
        return visible;
    }

    public List<Officer> getAssignedOfficers() {
        return assignedOfficers;
    }

    public Map<String, Registration> getPendingRegistrations() {
        return pendingRegistrations;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setNumUnits(FlatType flatType, int numUnits) {
        this.unitCounts.put(flatType, numUnits);
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Assign officer to project
    public void addOfficer(Registration registration) {
        Officer officer = registration.getOfficer();
        this.assignedOfficers.add(officer);             // Add to assigned officer list
        this.availableOfficerSlots--;
        String nric = officer.getNRIC();
        this.pendingRegistrations.remove(nric); // Remove registration from pending registrations
        System.out.println("Officer " + officer.getName() + " assigned to project " + this.getProjectName());
    }

    // Add officer to list of registrations
    public void addRegistration(Registration registration) {
        Officer officer = registration.getOfficer();
        pendingRegistrations.put(officer.getNRIC(), registration);  
    }

    // Remove officer from list of registrations (Rejection)
    public void removeRegistration(Registration registration) {
        Officer officer = registration.getOfficer();
        pendingRegistrations.remove(officer.getNRIC());
    }

    public void viewProjectEnquiries() {
    // Check if the officer is assigned to a project
        if (enquiries == null || enquiries.length == 0) {
            System.out.println("No enquiries for this project.");
        } else {
            System.out.println("Enquiries for project: " + this.getProjectName());
            for (Enquiry enq : enquiries) {
                // Directly use the 'view' method from Enquiry class to display the enquiry details
                System.out.println(enq);
                System.out.println("----------------------------");
            }
        }
    }

    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
        sb.append("BTO Project Details:\n")
        .append("Project Name: ").append(projectName).append("\n")
        .append("Neighbourhood: ").append(neighbourhood).append("\n")
        .append("Manager In Charge: ").append(managerInCharge.getName()).append("\n")
        .append("Opening Date: ").append(openingDate).append("\n")
        .append("Closing Date: ").append(closingDate).append("\n")
        .append("Available Officer Slots: ").append(availableOfficerSlots).append("\n")
        .append("Visibility: ").append(visible ? "Visible" : "Not Visible").append("\n")
        .append("Unit Counts: \n");

        for (Map.Entry<FlatType, Integer> entry : unitCounts.entrySet()) {
            sb.append(entry.getKey().getNumRooms()).append("-Room: ").append(entry.getValue()).append(" units\n");
        }

        return sb.toString();
    }
}
