package entity.project;

import entity.user.Manager;
import enums.FlatType;
import java.time.LocalDate;

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    private FlatType flatType;
    private int numUnits;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private Manager managerInCharge;
    private int availableOfficerSlots;
    private boolean visible;

    public BTOProject(String projectName, Manager manager, String neighbourhood) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.numUnits = 0;
        this.managerInCharge = manager;
        this.availableOfficerSlots = 10;    // Max 10
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }
}
