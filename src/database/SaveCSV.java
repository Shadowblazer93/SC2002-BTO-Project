package database;

import controller.BTOProjectController;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Officer;
import enums.FlatType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SaveCSV {
    public static void saveProject() {
        BTOProjectController projectController = new BTOProjectController();
        Map<String, BTOProject> allProjects = projectController.getAllProjects();
        File filePath = new File("src/database/ProjectList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("ProjectName,ManagerNRIC,Neighbourhood,2RoomCount,3RoomCount,OpeningDate,ClosingDate,AvailableOfficerSlots,Visible,Enquiries,AssignedOfficers,PendingRegistrations\n");

            for (BTOProject project: allProjects.values()) {
                String projectName = project.getProjectName();
                String managerNRIC = project.getManager().getNRIC();
                String neighbourhood = project.getNeighbourhood();
                Map<FlatType, Integer> unitCount = project.getunitCounts();
                int twoRoom = unitCount.get(FlatType.TWO_ROOM);
                int threeRoom = unitCount.get(FlatType.THREE_ROOM);
                LocalDate openingDate = project.getOpeningDate();
                LocalDate closingDate = project.getClosingDate();
                int availableOfficerSlots = project.getAvailableOfficerSlots();
                boolean visibile = project.getVisibility();
                Enquiry[] enquiryList = project.getEnquiries();
                StringBuilder enquiries = new StringBuilder();
                if (enquiryList != null) {
                    for (Enquiry enquiry : enquiryList) {
                        enquiries.append(enquiry.getID()).append("|");
                    }
                    if (enquiries.length() > 0) {
                        enquiries.setLength(enquiries.length() - 1);
                    }
                } else {
                    enquiries.append("");
                }
                
                List<Officer> officerList = project.getAssignedOfficers();
                StringBuilder assignedOfficers = new StringBuilder();
                if (officerList != null) {
                    for (Officer officer : officerList) {
                        assignedOfficers.append(officer.getNRIC()).append("|");
                    }
                    if (assignedOfficers.length() > 0) {
                        assignedOfficers.setLength(assignedOfficers.length() - 1);
                    }
                } else {
                    assignedOfficers.append("");
                }
                
                StringBuilder pendingRegistrations = new StringBuilder();
                Map<String, Registration> pendingRegistrationList = project.getPendingRegistrations();
                if (pendingRegistrationList != null) {
                    for (Map.Entry<String, Registration> entry : pendingRegistrationList.entrySet()) {
                        Registration registration = entry.getValue();
                        pendingRegistrations.append(registration.getID()).append("|");
                    }
                    if (pendingRegistrations.length() > 0) {
                        pendingRegistrations.setLength(pendingRegistrations.length() - 1);
                    }
                } else {
                    pendingRegistrations.append("");
                }
                

                writer.write(String.format("\"%s\",\"%s\",\"%s\",%d,%d,\"%s\",\"%s\",%d,%b,\"%s\",\"%s\",\"%s\"\n",
                    projectName, managerNRIC, neighbourhood, twoRoom, threeRoom,
                    openingDate, closingDate, availableOfficerSlots, visibile,
                    enquiries, assignedOfficers, pendingRegistrations));
            }
        } catch (IOException e) {
            System.out.println("Error saving projects");
        }
    }
}
