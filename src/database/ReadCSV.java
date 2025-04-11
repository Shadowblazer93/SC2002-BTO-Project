package database;

import controller.BTOProjectController;
import controller.user.ManagerController;
import entity.project.BTOProject;
import entity.user.Manager;
import enums.FlatType;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadCSV {
    public static void loadManager() {
        ManagerController managerController = new ManagerController();
        File managerFile = new File("src/database/ManagerList.csv");
        try (Scanner managerReader = new Scanner(managerFile)) {
            if (managerReader.hasNextLine()) {
                managerReader.nextLine();   // Skip header
            }

            while (managerReader.hasNextLine()) {
                String data = managerReader.nextLine();
                
                String[] managerData = data.split(",");
                String name = managerData[0].replace("\"", "").trim();
                String nric = managerData[1].replace("\"", "").trim();
                int age = Integer.parseInt(managerData[2].trim());
                String maritalStatus = managerData[3].replace("\"", "").trim();
                String password = managerData[4].replace("\"", "").trim();

                // Create Manager
                managerController.createManager(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void loadOfficer() {

    }

    public void loadApplicant() {

    }

    public static void loadProject() {
        BTOProjectController projectController = new BTOProjectController();
        ManagerController managerController = new ManagerController();
        File projectsFile = new File("src/database/ProjectList.csv");
        try (Scanner projectsReader = new Scanner(projectsFile)) {
            if (projectsReader.hasNextLine()) {
                projectsReader.nextLine();  // Skip header
            }

            while (projectsReader.hasNextLine()) {
                String data = projectsReader.nextLine();
                
                String[] projectData = data.split(",");
                String projectName = projectData[0].replace("\"", "").trim();
                String managerNRIC = projectData[1].replace("\"", "").trim();
                String neighbourhood = projectData[2].replace("\"", "").trim();
                int twoRoomCount = Integer.parseInt(projectData[3].trim());
                int threeRoomCount = Integer.parseInt(projectData[4].trim());
                Map<FlatType, Integer> unitCounts = new HashMap<>();
                unitCounts.put(FlatType.TWO_ROOM, twoRoomCount);
                unitCounts.put(FlatType.THREE_ROOM, threeRoomCount);
                LocalDate openingDate = LocalDate.parse(projectData[5].replace("\"", "").trim());
                LocalDate closingDate = LocalDate.parse(projectData[6].replace("\"", "").trim());
                int officerSlots = Integer.parseInt(projectData[7].trim());
                boolean visible = Boolean.parseBoolean(projectData[8].trim());

                // Get manager
                Manager manager = managerController.getManager(managerNRIC);
                if (manager == null) {
                    System.out.println("Manager not found for NRIC: " + managerNRIC);
                    continue; // or throw an exception
                }
                BTOProject project = projectController.createProject(manager, projectName, neighbourhood, unitCounts, openingDate, closingDate, officerSlots);
                manager.getManagedProjects().put(projectName, project);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void loadEnquiry() {

    }

    public void loadApplication() {

    }

    public void loadRegistration() {

    }
}
