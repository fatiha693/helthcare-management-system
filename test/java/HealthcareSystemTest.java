import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

/**
 * Test class for Healthcare System using JUnit 5
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HealthcareSystemTest {
    private static HealthcareSystem system;
    private static DatabaseManager dbManager;
    
    @BeforeAll
    static void setUp() {
        system = HealthcareSystem.getInstance();
        dbManager = DatabaseManager.getInstance();
    }
    
    @Test
    @Order(1)
    void testAdminLogin() {
        assertTrue(system.login("admin", "admin123"), "Admin login should succeed");
        assertFalse(system.login("admin", "wrongpass"), "Wrong password should fail");
    }
    
    @Test
    @Order(2)
    void testPatientRegistration() {
        String username = "testpatient";
        String password = "test123";
        
        system.registerPatient(username, password);
        assertTrue(system.login(username, password), "Patient login should succeed after registration");
    }
    
    @Test
    @Order(3)
    void testAppointmentBooking() {
        String patientUsername = "testpatient";
        String doctorName = "Dr. Test";
        String date = "2024-03-21";
        
        system.bookAppointment(patientUsername, doctorName, date);
        
        boolean appointmentFound = system.getAppointments().stream()
            .anyMatch(app -> app.getPatientUsername().equals(patientUsername) &&
                           app.getDoctorName().equals(doctorName) &&
                           app.getDate().equals(date));
                           
        assertTrue(appointmentFound, "Appointment should be found in the system");
    }
    
    @Test
    @Order(4)
    void testMedicalRecordManagement() {
        String patientUsername = "testpatient";
        String diagnosis = "Test Diagnosis";
        String treatment = "Test Treatment";
        
        system.addMedicalRecord(patientUsername, diagnosis, treatment);
        
        boolean recordFound = system.getMedicalRecords().stream()
            .anyMatch(record -> record.getPatientUsername().equals(patientUsername) &&
                              record.getDiagnosis().equals(diagnosis) &&
                              record.getTreatment().equals(treatment));
                              
        assertTrue(recordFound, "Medical record should be found in the system");
    }
    
    @Test
    @Order(5)
    void testMedicineInventory() {
        String medicine = "TestMed";
        int initialQuantity = 100;
        
        system.addMedicine(medicine, initialQuantity);
        assertEquals(initialQuantity, system.getMedicineInventory().get(medicine),
            "Medicine inventory should match added quantity");
            
        int prescriptionQuantity = 30;
        system.addPrescription("testpatient", medicine, prescriptionQuantity);
        
        assertEquals(initialQuantity - prescriptionQuantity, 
            system.getMedicineInventory().get(medicine),
            "Medicine inventory should be reduced after prescription");
    }
    
    @AfterAll
    static void tearDown() {
        // Clean up test data if needed
        try {
            DatabaseManager.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 