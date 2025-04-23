package database;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.*;
import enums.ApplicationStatus;
import enums.EnquiryStatus;
import enums.FlatType;
import enums.RegistrationStatus;
import interfaces.IApplicantService;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IManagerService;
import interfaces.IOfficerService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code ReadCSV} class provides static methods to load various CSV files.
 * Each method loads data from the CSV files into their respective controllers.
 * CSV files are loaded from the {@code src/database/} directory.
 * The methods assume that the CSV files are properly formated with correct headers and columns
 */
public class ReadCSV {
    
    private final IApplicantService applicantService;
    private final IOfficerService officerService;
    private final IManagerService managerService;
    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;
    private final IRegistrationService registrationService;

    public ReadCSV(IApplicantService applicantService, IOfficerService officerService, IManagerService managerService, 
                    IApplicationService applicationService, IEnquiryService enquiryService, 
                    IProjectService projectService, IRegistrationService registrationService) {
        this.applicantService = applicantService;
        this.officerService = officerService;
        this.managerService = managerService;
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.registrationService = registrationService;
    }

    /**
     * Loads Manager data from the {@code ManagerList.csv} CSV file and creates Manager objects.
     * Each manager's data is parsed from the file and passed to the ManagerController
     * @throws FileNotFoundException if the "ManagerList.csv" file cannot be found.
     */
    public void loadManager() {
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
                managerService.createManager(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads Officer data from the {@code OfficerList.csv} CSV file and creates Officer objects.
     * Each officer's data is parsed from the file and passed to the OfficerController
     * @throws FileNotFoundException if the "OfficerList.csv" file cannot be found.
     */
    public void loadOfficer() {
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
                officerService.createOfficer(nric, name, password, age, maritalStatus);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads Applicant data from the {@code ApplicantList.csv} CSV file and creates Applicant objects.
     * Each applicant's data is parsed from the file and passed to the ApplicantController
     * @throws FileNotFoundException if the "ApplicantList.csv" file cannot be found.
     */
    public void loadApplicant() {
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
                applicantService.createApplicant(nric, name, age, maritalStatus, password);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads BTO Project data from the {@code ProjectList.csv} CSV file and creates BTOProject objects.
     * Each project's data is parsed from the file and passed to the BTOProjectController
     * @throws FileNotFoundException if the "ProjectList.csv" file cannot be found.
     */
    public void loadProject() {
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
                Double twoRoomPrice = Double.parseDouble(data[4].trim());
                int threeRoomCount = Integer.parseInt(data[5].trim());
                Double threeRoomPrice = Double.parseDouble(data[6].trim());
                Map<FlatType, Integer> unitCounts = new HashMap<>();
                unitCounts.put(FlatType.TWO_ROOM, twoRoomCount);
                unitCounts.put(FlatType.THREE_ROOM, threeRoomCount);
                Map<FlatType, Double> unitPrices = new HashMap<>();
                unitPrices.put(FlatType.TWO_ROOM, twoRoomPrice);
                unitPrices.put(FlatType.THREE_ROOM, threeRoomPrice);
                LocalDate openingDate = LocalDate.parse(data[7].replace("\"", "").trim());
                LocalDate closingDate = LocalDate.parse(data[8].replace("\"", "").trim());
                int officerSlots = Integer.parseInt(data[9].trim());
                boolean visible = Boolean.parseBoolean(data[10].trim());
                String enquiries = data[11].replace("\"", "").trim();
                String applications = data[12].replace("\"", "").trim();
                String assignedOfficers = data[13].replace("\"", "").trim();
                String registrations = data[14].replace("\"", "").trim();

                // Get manager
                Manager manager = managerService.getManager(managerNRIC);
                if (manager == null) {
                    System.out.println("Manager not found for NRIC: " + managerNRIC);
                    continue; // or throw an exception
                }
                BTOProject project = projectService.createProject(manager, projectName, neighbourhood, unitCounts, unitPrices, openingDate, closingDate, officerSlots);
                if (visible) {  // Default visibility is false
                    project.setVisible(visible);
                }
                manager.getManagedProjects().put(projectName, project);
                if (LocalDate.now().isAfter(openingDate) && LocalDate.now().isBefore(closingDate)) {
                    manager.setCurrentProject(project);
                }

                // Parse enquiries
                String[] enquiryArray = enquiries.split("\\|");
                Map<Integer, Enquiry> allEnquiries = enquiryService.getAllEnquiries();
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
                    BTOApplication application = applicationService.getApplicationByNRIC(nric);
                    project.addApplication(application);
                }

                // Parse assigned officers
                String[] assignedOfficerArray = assignedOfficers.split("\\|");
                Map<String, Officer> allOfficers = officerService.getAllOfficers();
                for (String officerNRIC : assignedOfficerArray) {
                    officerNRIC = officerNRIC.trim();
                    if (officerNRIC.isEmpty()) continue;
                    project.addOfficer(allOfficers.get(officerNRIC));
                    
                    // Add to officer's assigned project
                    Officer officer = allOfficers.get(officerNRIC);
                    if (LocalDate.now().isAfter(project.getOpeningDate()) && LocalDate.now().isBefore(project.getClosingDate())) {
                        officer.assignProject(project);
                    }
                    //System.out.printf(defColor.RED + "%s: %s\n" + defColor.RESET, officer.getName(), projectName);
                }

                // Parse registrations
                String[] registrationsArray = registrations.split("\\|");
                List<Registration> registrationList = registrationService.getRegistrationsByProject(projectName);
                if (registrationList != null) {
                    // Convert list to map
                    Map<String, Registration> registrationMap = new HashMap<>();   // NRIC, Registration
                    for (Registration registration : registrationList) {
                        registrationMap.put(registration.getOfficer().getNRIC(), registration);
                    }
                    // Go thorugh nric in registrations
                    for (String nric : registrationsArray) {
                        if (nric.isEmpty()) continue;
                        Registration registration = registrationMap.get(nric);
                        if (registration == null) {
                            System.out.println("Registration not found for NRIC: " + nric);
                            continue;
                        }
                        project.addRegistration(registration);
                        // Add to officer's list of registrations
                        Officer officer = allOfficers.get(nric);
                        officer.addRegisteredProject(project);

                        // IMPORTANT: If approved, establish connections
                        if (registration.getStatus().equals(RegistrationStatus.APPROVED)) {
                            //System.out.println("CSV: Assigning officer " + officer.getName() + " to project " + projectName);
                            officer.assignProject(project);
                            project.getAssignedOfficers().add(officer);
                        }
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads Enquiry data from the {@code EnquiryList.csv} CSV file and creates Enquiry objects.
     * Each enquiry's data is parsed from the file and passed to the EnquiryController
     * @throws FileNotFoundException if the "EnquiryList.csv" file cannot be found.
     */
    public void loadEnquiry() {
        File file = new File("src/database/EnquiryList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }

            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                int id = Integer.parseInt(data[0].replace("\"", "").trim());
                String applicantNRIC = data[1].replace("\"", "").trim();
                String projectName = data[2].replace("\"", "").trim();
                String message = data[3].replace("\"", "").trim();
                String response = data[4].replace("\"", "").trim();
                EnquiryStatus status = EnquiryStatus.valueOf((data[5].replace("\"", "").trim()));

                Enquiry enquiry = enquiryService.createEnquiry(id, applicantNRIC, projectName, message, response, status);
                Applicant applicant = applicantService.getApplicant(applicantNRIC);
                if (applicant!=null) {applicant.addEnquiry(enquiry);}
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads BTO Application data from the {@code ApplicationList.csv} CSV file and creates BTOApplication objects.
     * Each application's data is parsed from the file and passed to the ApplicationController
     * @throws FileNotFoundException if the "ApplicationList.csv" file cannot be found.
     */
    public void loadBTOApplication() {
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
                FlatType flatType = FlatType.valueOf(((data[3].replace("\"", "").trim())));
                ApplicationStatus status = ApplicationStatus.valueOf((data[4].replace("\"", "").trim()));
                boolean withdrawal = Boolean.parseBoolean(data[5].replace("\"", "").trim());

                Applicant applicant = applicantService.getApplicant(applicantNRIC);
                if (applicant == null) {
                    applicant = officerService.getOfficer(applicantNRIC);
                }
                BTOApplication application = applicationService.createApplication(id, applicant, projectName, flatType, status, withdrawal);
                applicant.setApplication(application);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Loads Registration data from the {@code RegistrationList.csv} CSV file and creates Registration objects.
     * Each registration's data is parsed from the file and passed to the RegistrationController.
     * @throws FileNotFoundException if the "RegistrationList.csv" file cannot be found.
     */
    public void loadRegistration() {
        File file = new File("src/database/RegistrationList.csv");
        try (Scanner Reader = new Scanner(file)) {
            if (Reader.hasNextLine()) {
                Reader.nextLine();   // Skip header
            }
            
            while (Reader.hasNextLine()) {
                String line = Reader.nextLine();
                String[] data = line.split(",");
                
                // Parse data
                int id = Integer.parseInt(data[0].trim());
                String officerNRIC = data[1].replace("\"", "").trim();
                String projectName = data[2].replace("\"", "").trim();
                LocalDate registrationDate = LocalDate.parse(data[3].replace("\"", "").trim());
                RegistrationStatus status = RegistrationStatus.valueOf(data[4].replace("\"", "").trim());
                
                // Create registration
                Officer officer = officerService.getOfficer(officerNRIC);
                BTOProject project = projectService.getProjectByName(projectName);
                Registration registration = registrationService.createRegistration(id, officer, projectName, registrationDate, status);
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("Registration list not found.");
        }
    }
}
