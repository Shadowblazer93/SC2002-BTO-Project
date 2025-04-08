package entity.project;

import entity.enquiry.Enquiry;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.FlatType;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.*;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
>>>>>>> 7218b3f46a25565672498628fda790c9ef29c1d8

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    // Keeps track of flatTypes and numUnits
    private Map<FlatType, Integer> unitCounts;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private Manager managerInCharge;
    private int availableOfficerSlots;
    private boolean visible;
    private Enquiry[] enquiries; 
<<<<<<< HEAD
    private Officer assignedOfficer;
    private List<Officer> pendingApplicants = new ArrayList<>();

=======
    private List<Registration> officerRegistrations;
>>>>>>> 7218b3f46a25565672498628fda790c9ef29c1d8

    public BTOProject(String projectName, Manager manager, String neighbourhood, 
                        Map<FlatType, Integer> unitCounts, LocalDate openingDate, LocalDate closingDate,
                        int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.availableOfficerSlots = 10;    // Max 10
        this.unitCounts = unitCounts; 
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visible = false;
        this.officerRegistrations = new ArrayList<>();
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
    
    public List<Officer> getPendingApplicants() {
        return pendingApplicants;
    }

    public List<Registration> getOfficerRegistrations() {
        return officerRegistrations;
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

    // Method to assign an officer to the project
    public void assignOfficer(Officer officer) {
        this.assignedOfficer = officer;
        System.out.println("Officer " + officer.getName() + " assigned to project " + this.getProjectName());
    }

    public void addPendingApplicant(Officer officer) {
        if (!pendingApplicants.contains(officer)) {
            pendingApplicants.add(officer);
            System.out.println("Officer " + officer.getName() + " has applied to " + this.projectName);
        } else {
            System.out.println("You have already applied for this project.");
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
