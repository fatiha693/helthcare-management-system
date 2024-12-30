/**
 * Main class to demonstrate the Healthcare System functionality
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== Healthcare Management System Demo ===\n");
            
            HealthcareSystem system = HealthcareSystem.getInstance();
            
            // Demo login
            System.out.println("1. Testing Login:");
            System.out.println("Admin login successful: " + system.login("admin", "admin123"));
            
            // Register new patient
            System.out.println("\n2. Registering New Patient:");
            system.registerPatient("john", "john123");
            System.out.println("Patient 'john' registered");
            System.out.println("Patient login successful: " + system.login("john", "john123"));
            
            // Book appointment
            System.out.println("\n3. Booking Appointments:");
            system.bookAppointment("john", "Dr. Smith", "2024-03-20");
            system.bookAppointment("john", "Dr. Johnson", "2024-03-25");
            
            // Add medical record
            System.out.println("\n4. Adding Medical Records:");
            system.addMedicalRecord("john", "Fever", "Prescribed paracetamol");
            system.addMedicalRecord("john", "Headache", "Rest and hydration recommended");
            
            // Add medicine to inventory
            System.out.println("\n5. Managing Medicine Inventory:");
            system.addMedicine("Paracetamol", 100);
            system.addMedicine("Aspirin", 150);
            
            // Add prescription
            System.out.println("\n6. Creating Prescriptions:");
            system.addPrescription("john", "Paracetamol", 10);
            system.addPrescription("john", "Aspirin", 15);
            
            // Print all appointments
            System.out.println("\n=== Current Appointments ===");
            system.getAppointments().forEach(app -> 
                System.out.println("Patient: " + app.getPatientUsername() + 
                                 "\nDoctor: " + app.getDoctorName() + 
                                 "\nDate: " + app.getDate() + "\n"));
            
            // Print medical records
            System.out.println("=== Medical Records ===");
            system.getMedicalRecords().forEach(record -> 
                System.out.println("Patient: " + record.getPatientUsername() + 
                                 "\nDiagnosis: " + record.getDiagnosis() + 
                                 "\nTreatment: " + record.getTreatment() + "\n"));
            
            // Print prescriptions
            System.out.println("=== Prescriptions ===");
            system.getPrescriptions().forEach(prescription -> 
                System.out.println("Patient: " + prescription.getPatientUsername() + 
                                 "\nMedicine: " + prescription.getMedicine() + 
                                 "\nQuantity: " + prescription.getQuantity() + "\n"));
            
            // Print inventory
            System.out.println("=== Medicine Inventory ===");
            system.getMedicineInventory().forEach((medicine, quantity) -> 
                System.out.println(medicine + ": " + quantity + " units"));
            
            // Close database connection when done
            DatabaseManager.getInstance().closeConnection();
            
        } catch (Exception e) {
            System.err.println("\nError occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 