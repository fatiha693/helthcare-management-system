/**
 * Prescription class following Single Responsibility Principle
 */
public class Prescription {
    private String patientUsername;
    private String medicine;
    private int quantity;
    
    public Prescription(String patientUsername, String medicine, int quantity) {
        this.patientUsername = patientUsername;
        this.medicine = medicine;
        this.quantity = quantity;
    }
    
    // Getters
    public String getPatientUsername() { return patientUsername; }
    public String getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }
} 