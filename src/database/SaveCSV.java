package database;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
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
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The {@code SaveCSV} class provides static methods to save various entities into CSV files.
 * Each method retrieves data from the respective controllers and writes to a corresponding CSV file.
 * CSV files are saved in the {@code src/database/} directory
 */
public class SaveCSV {

    private final IApplicantService applicantService;
    private final IOfficerService officerService;
    private final IManagerService managerService;
    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;
    private final IRegistrationService registrationService;

    public SaveCSV(IApplicantService applicantService, IOfficerService officerService, IManagerService managerService, 
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
     * Save details of all BTOProjects as the {@code ProjectList.csv} CSV file.
     * Columns include project name, manager NRIC, project neighbourhood, units and prices for each flat type,
     * opening and closing dates, available officer slots, visibility, enqauiries and applications. assigned officers
     * and registrations
     */
    public void saveProject() {
        Map<String, BTOProject> allProjects = projectService.getAllProjects();
        File filePath = new File("src/database/ProjectList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("ProjectName,ManagerNRIC,Neighbourhood,2RoomCount,2RoomPrice,3RoomCount,3RoomPrice,OpeningDate,ClosingDate,AvailableOfficerSlots,Visible,Enquiries,Applications,AssignedOfficers,Registrations\n");

            for (BTOProject project: allProjects.values()) {
                String projectName = project.getProjectName();
                String managerNRIC = project.getManager().getNRIC();
                String neighbourhood = project.getNeighbourhood();
                Map<FlatType, Integer> unitCount = project.getUnitCounts();
                int twoRoom = unitCount.get(FlatType.TWO_ROOM);
                int threeRoom = unitCount.get(FlatType.THREE_ROOM);
                Map<FlatType, Double> unitPrices = project.getUnitPrices();
                Double twoRoomPrice = unitPrices.get(FlatType.TWO_ROOM);
                Double threeRoomPrice = unitPrices.get(FlatType.THREE_ROOM);
                LocalDate openingDate = project.getOpeningDate();
                LocalDate closingDate = project.getClosingDate();
                int availableOfficerSlots = project.getAvailableOfficerSlots();
                boolean visibile = project.getVisibility();

                Map<Integer, Enquiry> enquiryList = project.getEnquiries();
                StringBuilder enquiries = new StringBuilder();
                if (!enquiryList.isEmpty()) {
                    for (Map.Entry<Integer, Enquiry> entry : enquiryList.entrySet()) {
                        Enquiry enquiry = entry.getValue();
                        enquiries.append(enquiry.getID()).append("|");
                    }
                    if (enquiries.length() > 0) {
                        enquiries.setLength(enquiries.length() - 1);
                    }
                } else {
                    enquiries.append("");
                }

                Map<String, BTOApplication> applicationList = project.getApplications();
                StringBuilder applications = new StringBuilder();
                if (!applicationList.isEmpty()) {
                    for (Map.Entry<String, BTOApplication> entry : applicationList.entrySet()) {
                        BTOApplication application = entry.getValue();
                        applications.append(application.getApplicant().getNRIC()).append("|");
                    }
                    if (applications.length() > 0) {
                        applications.setLength(applications.length() - 1);
                    }
                } else {
                    applications.append("");
                }
                
                List<Officer> officerList = project.getAssignedOfficers();
                StringBuilder assignedOfficers = new StringBuilder();
                if (!officerList.isEmpty()) {
                    for (Officer officer : officerList) {
                        if (officer == null) {
                            continue;
                        }
                        assignedOfficers.append(officer.getNRIC()).append("|");
                    }
                    if (assignedOfficers.length() > 0) {
                        assignedOfficers.setLength(assignedOfficers.length() - 1);
                    }
                } else {
                    assignedOfficers.append("");
                }
                
                StringBuilder pendingRegistrations = new StringBuilder();
                Map<String, Registration> pendingRegistrationList = project.getRegistrations();
                if (!pendingRegistrationList.isEmpty()) {
                    for (Map.Entry<String, Registration> entry : pendingRegistrationList.entrySet()) {
                        Registration registration = entry.getValue();
                        pendingRegistrations.append(registration.getOfficer().getNRIC()).append("|");
                    }
                    if (pendingRegistrations.length() > 0) {
                        pendingRegistrations.setLength(pendingRegistrations.length() - 1);
                    }
                } else {
                    pendingRegistrations.append("");
                }
                

                writer.write(String.format("\"%s\",\"%s\",\"%s\",%d,%.2f,%d,%.2f,\"%s\",\"%s\",%d,%b,\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    projectName, managerNRIC, neighbourhood, twoRoom, twoRoomPrice, threeRoom, threeRoomPrice,
                    openingDate, closingDate, availableOfficerSlots, visibile,
                    enquiries, applications, assignedOfficers, pendingRegistrations));
            }
        } catch (IOException e) {
            System.out.println("Error saving projects");
        }
    }

    /**
     * Save details of all Managers as the {@code ManagerList.csv} CSV file.
     * Columns include name, NRIC, age, marital status and password
     */
    public void saveManagers() {
        Map<String, Manager> allManagers = managerService.getAllManagers();
        File filePath = new File("src/database/ManagerList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("Name,NRIC,Age,Marital Status,Password\n");

            for (Manager manager : allManagers.values()) {
                String name = manager.getName();
                String nric = manager.getNRIC();
                int age = manager.getAge();
                String maritalStatus = manager.getMaritalStatus();
                String password = manager.getPassword();

                writer.write(String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\"\n",
                    name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.out.println("Error saving managers");
        }
    }

    /**
     * Save details of all Applicants as the {@code ApplicantList.csv} CSV file.
     * Columns include name, NRIC, age, marital status and password
     */
    public void saveApplicants() {
        Map<String, Applicant> allApplicants = applicantService.getAllApplicants();
        File filePath = new File("src/database/ApplicantList.csv");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Name,NRIC,Age,Marital Status,Password\n");
            for (Applicant applicant : allApplicants.values()) {
                String name = applicant.getName();
                String nric = applicant.getNRIC();
                int age = applicant.getAge();
                String maritalStatus = applicant.getMaritalStatus();
                String password = applicant.getPassword();

                writer.write(String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\"\n",
                    name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.out.println("Error saving applicants");
        }
    }

    /**
     * Save details of all Officers as the {@code OfficerList.csv} CSV file.
     * Columns include name, NRIC, age, marital status and password
     */
    public void saveOfficers() {
        Map<String, Officer> allOfficers = officerService.getAllOfficers();
        File filePath = new File("src/database/OfficerList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("Name,NRIC,Age,Marital Status,Password\n");

            for (Officer officer : allOfficers.values()) {
                String name = officer.getName();
                String nric = officer.getNRIC();
                int age = officer.getAge();
                String maritalStatus = officer.getMaritalStatus();
                String password = officer.getPassword();

                writer.write(String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\"\n",
                    name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.out.println("Error saving officers");
        }
    }

    /**
     * Save details of all Enquiries as the {@code EnquiryList.csv} CSV file.
     * Columns include enquiry ID, applicant NRIC, project name, message, response and the enquiry status
     */
    public void saveEnquiries() {
        Map<Integer, Enquiry> allEnquiries = enquiryService.getAllEnquiries();
        File filePath = new File("src/database/EnquiryList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("ID,Applicant NRIC,ProjectName,Message,Response,Status\n");

            for (Enquiry enquiry : allEnquiries.values()) {
                int id = enquiry.getID();
                String applicantNRIC = enquiry.getApplicantNRIC();
                String projectName = enquiry.getProjectName();
                String message = enquiry.getMessage();
                String response = enquiry.getResponse();
                String status = enquiry.getStatus().toString();

                writer.write(String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    id, applicantNRIC, projectName, message, response, status));
            }
        } catch (IOException e) {
            System.out.println("Error saving enquiries");
        }
    }

    /**
     * Save details of all BTO Applications as the {@code ApplicationList.csv} CSV file.
     * Columns include application ID, applicant NRIC, project name, flat type, application status and
     * withdrawal status
     */
    public void saveBTOApplications() {
        Map<String, BTOApplication> allApplications = applicationService.getAllApplications();
        File filePath = new File("src/database/ApplicationList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("ID,Applicant,Project,FlatType(Rooms),Status,Withdrawal\n");

            for (BTOApplication application : allApplications.values()) {
                int id = application.getID();
                String applicant = application.getApplicant().getNRIC();
                String project = application.getProjectName();
                FlatType flatType = application.getFlatType();
                String status = application.getStatus().toString();
                boolean withdrawal = application.getWithdrawal();

                writer.write(String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",%b\n",
                    id, applicant, project, flatType, status, withdrawal));
            }
        } catch (IOException e) {
            System.out.println("Error saving applications");
        }
    }

    /**
     * Save details of all Registrations as the {@code RegistrationList.csv} CSV file.
     * Columns include registration ID, officer NRIC, project name, registration date and registration status
     */
    public void saveRegistrations() {
        Map<String, List<Registration>> allRegistrations = registrationService.getAllRegistrations();
        File filePath = new File("src/database/RegistrationList.csv");
        try (FileWriter writer = new FileWriter(filePath)) {
            // File header
            writer.write("ID,Officer,Project,RegistrationDate,Status\n");

            for (List<Registration> projectRegistrations : allRegistrations.values()) {
                for (Registration registration : projectRegistrations) {
                    int id = registration.getID();
                    String officer = registration.getOfficer().getNRIC();
                    String project = registration.getProjectName();
                    LocalDate registrationDate = registration.getRegistrationDate();
                    RegistrationStatus status = registration.getStatus();

                    writer.write(String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        id, officer, project, registrationDate, status));
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving registrations");
        }
    }
}
