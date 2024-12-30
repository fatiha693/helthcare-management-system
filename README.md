# Healthcare Management System

A Java-based healthcare management system that demonstrates the implementation of SOLID principles, design patterns, and database integration. This system provides basic healthcare facility management features with a focus on patient care and medical inventory management.

## Features

1. **User Authentication**
   - Admin and Patient login functionality
   - Secure password handling
   - Role-based access control

2. **Patient Management**
   - Patient registration
   - Patient records management
   - Medical history tracking

3. **Appointment System**
   - Book appointments with doctors
   - Track appointment schedules
   - View appointment history

4. **Medical Records**
   - Create and store patient diagnoses
   - Track treatments
   - Maintain patient medical history

5. **Prescription Management**
   - Create digital prescriptions
   - Link prescriptions to patients
   - Track medicine quantities

6. **Medicine Inventory**
   - Track medicine stock
   - Automatic inventory updates
   - Low stock alerts

## Technical Implementation

- **Design Patterns Used**
  - Singleton Pattern (DatabaseManager, HealthcareSystem)
  - Factory Pattern (User creation)
  
- **SOLID Principles**
  - Single Responsibility Principle (Each class has one responsibility)
  - Open/Closed Principle (System can be extended without modification)
  - Liskov Substitution Principle (User inheritance)
  - Interface Segregation Principle (Minimal interfaces)
  
- **Database**
  - SQLite database for data persistence
  - Transaction management
  - Foreign key constraints

## Project Structure 

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- SQLite JDBC Driver
- JUnit 5 (for testing)
- SQLite Database Browser (optional, for viewing database)

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd healthcare-system
   ```

2. **Set Up Dependencies**
   ```bash
   # Create lib directory
   mkdir -p lib
   
   # Download SQLite JDBC driver
   curl -L "https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar" -o lib/sqlite-jdbc-3.36.0.3.jar
   
   # Make run script executable
   chmod +x run.sh
   ```

## Running the Application

1. **Using the Run Script**
   ```bash
   ./run.sh
   ```

2. **Default Credentials**
   - Admin Login:
     - Username: `admin`
     - Password: `admin123`

## System Features Demo

When you run the application, it will demonstrate:

1. **Admin Authentication**
   - Login with admin credentials

2. **Patient Management**
   - Register a new patient
   - Verify patient login

3. **Appointment Booking**
   - Schedule appointments with doctors
   - View appointment details

4. **Medical Records**
   - Add patient diagnoses
   - View medical history

5. **Prescription System**
   - Create prescriptions
   - Link medicines to patients

6. **Inventory Management**
   - Track medicine stock
   - Automatic updates after prescriptions

## Database Management

The system uses SQLite database (`healthcare.db`) which is created automatically on first run.

**Viewing Database Contents:** 

```bash
sqlite3 healthcare.db
```

## Common Commands

```bash
# List all tables
.tables

SELECT FROM users; # View users
SELECT FROM appointments; # View appointments
SELECT FROM medical_records; # View medical records
SELECT FROM prescriptions; # View prescriptions
SELECT FROM medicines; # View medicines
SELECT FROM inventory; # View inventory

# Exit SQLite
.exit
```
