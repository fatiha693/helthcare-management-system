/**
 * Medical Record class following Single Responsibility Principle
 */
public class MedicalRecord {
    private String patientUsername;
    private String diagnosis;
    private String treatment;
    
    public MedicalRecord(String patientUsername, String diagnosis, String treatment) {
        this.patientUsername = patientUsername;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }
    
    // Getters
    public String getPatientUsername() { return patientUsername; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
} 