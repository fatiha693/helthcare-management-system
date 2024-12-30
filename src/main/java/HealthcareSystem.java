import java.util.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Singleton class for Healthcare System
 * Main system class that manages all operations
 */
public class HealthcareSystem {
    private static HealthcareSystem instance;
    private DatabaseManager dbManager;
    
    private HealthcareSystem() {
        dbManager = DatabaseManager.getInstance();
    }
    
    public static HealthcareSystem getInstance() {
        if (instance == null) {
            instance = new HealthcareSystem();
        }
        return instance;
    }
    
    // Authentication
    public boolean login(String username, String password) {
        try {
            return dbManager.validateUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // User Management
    public void registerPatient(String username, String password) {
        try {
            User user = new User(username, password, "PATIENT");
            dbManager.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Appointment Management
    public void bookAppointment(String patientUsername, String doctorName, String date) {
        try {
            dbManager.addAppointment(new Appointment(patientUsername, doctorName, date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Medical Record Management
    public void addMedicalRecord(String patientUsername, String diagnosis, String treatment) {
        try {
            dbManager.addMedicalRecord(patientUsername, diagnosis, treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Prescription Management
    public void addPrescription(String patientUsername, String medicine, int quantity) {
        try {
            dbManager.addPrescription(patientUsername, medicine, quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Inventory Management
    public void addMedicine(String medicine, int quantity) {
        try {
            dbManager.addMedicine(medicine, quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Getters for lists
    public List<Appointment> getAppointments() {
        try {
            return dbManager.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<MedicalRecord> getMedicalRecords() {
        try {
            return dbManager.getAllMedicalRecords();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public List<Prescription> getPrescriptions() {
        try {
            return dbManager.getAllPrescriptions();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public Map<String, Integer> getMedicineInventory() {
        try {
            return dbManager.getMedicineInventory();
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
} 