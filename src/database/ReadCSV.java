package database;

import controller.ApplicationController;
import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import controller.user.*;
import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.*;
import enums.ApplicationStatus;
import enums.EnquiryStatus;
import enums.FlatType;
import enums.RegistrationStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReadCSV {

    public static void loadManager() {
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
                ManagerController.createManager(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadOfficer() {
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
                OfficerController.createOfficer(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadEnquiry() {
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
                EnquiryStatus status = parseEnquiryStatus(data[5].replace("\"", "").trim());

                EnquiryController.createEnquiry(id, applicantNRIC, projectName, message, response, status);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadApplicant() {
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
                Applicant applicant = ApplicantController.createApplicant(nric, name, age, maritalStatus, password);

                List<Enquiry> enquiries = EnquiryController.getEnquiriesByNRIC(nric);
                for (Enquiry e : enquiries) {applicant.addEnquiry(e);}
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadProject() {

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
                String enquiries = data[9].replace("\"", "").trim();
                String applications = data[10].replace("\"", "").trim();
                String assignedOfficers = data[11].replace("\"", "").trim();
                String pendingRegistrations = data[12].replace("\"", "").trim();

                // Get manager
                Manager manager = ManagerController.getManager(managerNRIC);
                if (manager == null) {
                    System.out.println("Manager not found for NRIC: " + managerNRIC);
                    continue; // or throw an exception
                }
                BTOProject project = BTOProjectController.createProject(manager, projectName, neighbourhood, unitCounts, openingDate, closingDate, officerSlots);
                if (visible) {  // Default visibility is false
                    project.setVisible(visible);
                }
                manager.getManagedProjects().put(projectName, project);
                if (LocalDate.now().isAfter(openingDate) && LocalDate.now().isBefore(closingDate)) {
                    manager.setCurrentProject(project);
                }

                // Parse enquiries
                String[] enquiryArray = enquiries.split("\\|");
                Map<Integer, Enquiry> allEnquiries = EnquiryController.getAllEnquiries();
                for (String enquiryID : enquiryArray) {
                    if (enquiryID.isEmpty()) continue; 
                    int id = Integer.parseInt(enquiryID.trim());
                    Enquiry enquiry = allEnquiries.get(id);
                    if (enquiry == null) {
                        System.out.println("Enquiry not found for ID: " + id);
                        continue;
                    }
                    project.addEnquiry(enquiry);
                }
                // Parse applications
                String[] applicationArray = applications.split("\\|");
                for (String nric : applicationArray) {
                    if (nric.isEmpty()) continue;
                    BTOApplication application = ApplicationController.getApplicationByNRIC(nric);
                    project.addApplication(application);
                }

                // Parse assigned officers
                String[] assignedOfficerArray = assignedOfficers.split("\\|");
                Map<String, Officer> allOfficers = OfficerController.getAllOfficers();
                for (String officerNRIC : assignedOfficerArray) {
                    officerNRIC = officerNRIC.trim();
                    project.addOfficer(allOfficers.get(officerNRIC));
                }

                // Parse pending registrations
                String[] pendingRegistrationsArray = pendingRegistrations.split("\\|");
                List<Registration> registrationList = RegistrationController.getRegistrationsByProject(projectName);
                if (registrationList != null) {
                    // Convert list to map
                    Map<Integer, Registration> registrationMap = new HashMap<>();
                    for (Registration registration : registrationList) {
                        registrationMap.put(registration.getID(), registration);
                    }
                    for (String registrationId : pendingRegistrationsArray) {
                        if (registrationId.isEmpty()) continue;
                        int id = Integer.parseInt(registrationId.trim());
                        Registration registration = registrationMap.get(id);
                        if (registration == null) {
                            System.out.println("Registration not found for ID: " + id);
                            continue;
                        }
                        project.addRegistration(registration);
                    }
                } else {
                    System.out.println("No registrations found for project: " + projectName);
                }
                
                
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadBTOApplication() {
        File file = new File("src/database/ApplicationList.csv");
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
                FlatType flatType = parseFlatType((data[3].replace("\"", "").trim()));
                ApplicationStatus status = parseApplicationStatus(data[4].replace("\"", "").trim());
                boolean withdrawal = Boolean.parseBoolean(data[5].replace("\"", "").trim());

                Applicant applicant = ApplicantController.getApplicant(applicantNRIC);
                ApplicationController.createApplication(id, applicant, projectName, flatType, status, withdrawal);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void loadRegistration() {

        File file = new File("src/database/RegistrationList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();

                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].replace("\"", "").trim());
                String officerNRIC = data[1].replace("\"", "").trim();
                String projectName = (data[2].replace("\"", "").trim());
                LocalDate registrationDate = LocalDate.parse(data[3].replace("\"", "").trim());
                RegistrationStatus status = parseRegistrationStatus(data[4].replace("\"", "").trim());

                Officer officer = OfficerController.getOfficer(officerNRIC);
                RegistrationController.createRegistration(id, officer, projectName, registrationDate, status);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static FlatType parseFlatType(String flatType) {
        switch (flatType.toUpperCase()) {
            case "TWO_ROOM" -> {
                return FlatType.TWO_ROOM;
            }
            case "THREE_ROOM" -> {
                return FlatType.THREE_ROOM;
            }
            default -> throw new IllegalArgumentException("Invalid flat type: " + flatType);
        }
    }

    private static ApplicationStatus parseApplicationStatus(String status) {
        switch (status.toUpperCase()) {
            case "PENDING" -> {
                return ApplicationStatus.PENDING;
            }
            case "SUCCESSFUL" -> {
                return ApplicationStatus.SUCCESSFUL;
            }
            case "UNSUCCESSFUL" -> {
                return ApplicationStatus.UNSUCCESSFUL;
            }
            case "BOOKED" -> {
                return ApplicationStatus.BOOKED;
            }
            case "WITHDRAWN" -> {
                return ApplicationStatus.WITHDRAWN;
            }
            default -> throw new IllegalArgumentException("Invalid application status: " + status);
        }
    }

    private static RegistrationStatus parseRegistrationStatus(String status) {
        switch (status.toUpperCase()) {
            case "PENDING" -> {
                return RegistrationStatus.PENDING;
            }
            case "APPROVED" -> {
                return RegistrationStatus.APPROVED;
            }
            case "REJECTED" -> {
                return RegistrationStatus.REJECTED;
            }
            default -> throw new IllegalArgumentException("Invalid registration status: " + status);
        }
    }

    private static EnquiryStatus parseEnquiryStatus(String status) {
        switch (status.toUpperCase()) {
            case "OPEN" -> {
                return EnquiryStatus.OPEN;
            }
            case "CLOSED" -> {
                return EnquiryStatus.CLOSED;
            }
            default -> throw new IllegalArgumentException("Invalid enquiry status: " + status);
        }
    }
}
