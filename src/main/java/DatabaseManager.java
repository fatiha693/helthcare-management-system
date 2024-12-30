import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Database Manager class following Singleton pattern
 * Handles all database operations
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:healthcare.db";
    
    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            initializeTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeTables() throws SQLException {
        String[] createTableQueries = {
            // Users table
            """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            )
            """,
            // Appointments table
            """
            CREATE TABLE IF NOT EXISTS appointments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patient_username TEXT,
                doctor_name TEXT,
                appointment_date TEXT,
                FOREIGN KEY (patient_username) REFERENCES users(username)
            )
            """,
            // Medical Records table
            """
            CREATE TABLE IF NOT EXISTS medical_records (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patient_username TEXT,
                diagnosis TEXT,
                treatment TEXT,
                record_date TEXT DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (patient_username) REFERENCES users(username)
            )
            """,
            // Prescriptions table
            """
            CREATE TABLE IF NOT EXISTS prescriptions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patient_username TEXT,
                medicine TEXT,
                quantity INTEGER,
                prescription_date TEXT DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (patient_username) REFERENCES users(username)
            )
            """,
            // Medicine Inventory table
            """
            CREATE TABLE IF NOT EXISTS medicine_inventory (
                medicine_name TEXT PRIMARY KEY,
                quantity INTEGER DEFAULT 0
            )
            """
        };
        
        Statement stmt = connection.createStatement();
        for (String query : createTableQueries) {
            stmt.execute(query);
        }
        
        // Insert default admin if not exists
        PreparedStatement ps = connection.prepareStatement(
            "INSERT OR IGNORE INTO users (username, password, role) VALUES (?, ?, ?)");
        ps.setString(1, "admin");
        ps.setString(2, "admin123");
        ps.setString(3, "ADMIN");
        ps.executeUpdate();
    }
    
    // Database operations for Users
    public void addUser(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.executeUpdate();
    }
    
    public boolean validateUser(String username, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM users WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        return ps.executeQuery().next();
    }
    
    // Database operations for Appointments
    public void addAppointment(Appointment appointment) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO appointments (patient_username, doctor_name, appointment_date) VALUES (?, ?, ?)");
        ps.setString(1, appointment.getPatientUsername());
        ps.setString(2, appointment.getDoctorName());
        ps.setString(3, appointment.getDate());
        ps.executeUpdate();
    }
    
    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
        
        while (rs.next()) {
            appointments.add(new Appointment(
                rs.getString("patient_username"),
                rs.getString("doctor_name"),
                rs.getString("appointment_date")
            ));
        }
        return appointments;
    }
    
    // Database operations for Medical Records
    public void addMedicalRecord(String patientUsername, String diagnosis, String treatment) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO medical_records (patient_username, diagnosis, treatment) VALUES (?, ?, ?)");
        ps.setString(1, patientUsername);
        ps.setString(2, diagnosis);
        ps.setString(3, treatment);
        ps.executeUpdate();
    }
    
    public List<MedicalRecord> getAllMedicalRecords() throws SQLException {
        List<MedicalRecord> records = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_records");
        
        while (rs.next()) {
            records.add(new MedicalRecord(
                rs.getString("patient_username"),
                rs.getString("diagnosis"),
                rs.getString("treatment")
            ));
        }
        return records;
    }
    
    // Database operations for Prescriptions
    public void addPrescription(String patientUsername, String medicine, int quantity) throws SQLException {
        connection.setAutoCommit(false);
        try {
            // Add prescription
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO prescriptions (patient_username, medicine, quantity) VALUES (?, ?, ?)");
            ps.setString(1, patientUsername);
            ps.setString(2, medicine);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            
            // Update inventory
            ps = connection.prepareStatement(
                "UPDATE medicine_inventory SET quantity = quantity - ? WHERE medicine_name = ?");
            ps.setInt(1, quantity);
            ps.setString(2, medicine);
            ps.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM prescriptions");
        
        while (rs.next()) {
            prescriptions.add(new Prescription(
                rs.getString("patient_username"),
                rs.getString("medicine"),
                rs.getInt("quantity")
            ));
        }
        return prescriptions;
    }
    
    // Database operations for Medicine Inventory
    public void addMedicine(String medicine, int quantity) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT OR REPLACE INTO medicine_inventory (medicine_name, quantity) VALUES (?, ?)");
        ps.setString(1, medicine);
        ps.setInt(2, quantity);
        ps.executeUpdate();
    }
    
    public Map<String, Integer> getMedicineInventory() throws SQLException {
        Map<String, Integer> inventory = new HashMap<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM medicine_inventory");
        
        while (rs.next()) {
            inventory.put(
                rs.getString("medicine_name"),
                rs.getInt("quantity")
            );
        }
        return inventory;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 