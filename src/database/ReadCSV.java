package database;

import controller.BTOProjectController;
import controller.EnquiryController;
import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
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
        File file = new File("src/database/ManagerList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();
                
                String[] managerData = line.split(",");
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

    public static void loadOfficer() {
        OfficerController officerController = new OfficerController();
        File file = new File("src/database/OfficerList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();
                
                String[] data = line.split(",");
                String name = data[0].replace("\"", "").trim();
                String nric = data[1].replace("\"", "").trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].replace("\"", "").trim();
                String password = data[4].replace("\"", "").trim();

                // Create Officer
                officerController.createOfficer(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadApplicant() {
        ApplicantController applicantController = new ApplicantController();
        File file = new File("src/database/ApplicantList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();
                
                String[] data = line.split(",");
                String name = data[0].replace("\"", "").trim();
                String nric = data[1].replace("\"", "").trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].replace("\"", "").trim();
                String password = data[4].replace("\"", "").trim();

                // Create Applicant
                applicantController.createApplicant(nric, name, age, maritalStatus, password);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadProject() {
        BTOProjectController projectController = new BTOProjectController();
        ManagerController managerController = new ManagerController();
        File file = new File("src/database/ProjectList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();  // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();
                
                String[] data = line.split(",");
                String projectName = data[0].replace("\"", "").trim();
                String managerNRIC = data[1].replace("\"", "").trim();
                String neighbourhood = data[2].replace("\"", "").trim();
                int twoRoomCount = Integer.parseInt(data[3].trim());
                int threeRoomCount = Integer.parseInt(data[4].trim());
                Map<FlatType, Integer> unitCounts = new HashMap<>();
                unitCounts.put(FlatType.TWO_ROOM, twoRoomCount);
                unitCounts.put(FlatType.THREE_ROOM, threeRoomCount);
                LocalDate openingDate = LocalDate.parse(data[5].replace("\"", "").trim());
                LocalDate closingDate = LocalDate.parse(data[6].replace("\"", "").trim());
                int officerSlots = Integer.parseInt(data[7].trim());
                boolean visible = Boolean.parseBoolean(data[8].trim());

                // Get manager
                Manager manager = managerController.getManager(managerNRIC);
                if (manager == null) {
                    System.out.println("Manager not found for NRIC: " + managerNRIC);
                    continue; // or throw an exception
                }
                BTOProject project = projectController.createProject(manager, projectName, neighbourhood, unitCounts, openingDate, closingDate, officerSlots);
                if (visible) {  // Default visibility is false
                    project.setVisible(visible);
                }
                manager.getManagedProjects().put(projectName, project);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadEnquiry() {
        EnquiryController enquiryController = new EnquiryController();
        File file = new File("src/database/EnquiryList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();

                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].replace("\"", "").trim());
                String applicantNRIC = data[1].replace("\"", "").trim();
                String projectName = data[2].replace("\"", "").trim();
                String message = data[3].replace("\"", "").trim();
                String response = data[4].replace("\"", "").trim();
                String status = data[5].replace("\"", "").trim();

                enquiryController.createEnquiry(id, applicantNRIC, projectName, message, response, status);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void loadApplication() {

    }

    public void loadRegistration() {

    }
}
