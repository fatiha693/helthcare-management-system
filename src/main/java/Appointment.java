/**
 * Appointment class following Single Responsibility Principle
 */
public class Appointment {
    private String patientUsername;
    private String doctorName;
    private String date;
    
    public Appointment(String patientUsername, String doctorName, String date) {
        this.patientUsername = patientUsername;
        this.doctorName = doctorName;
        this.date = date;
    }
    
    // Getters
    public String getPatientUsername() { return patientUsername; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
} 