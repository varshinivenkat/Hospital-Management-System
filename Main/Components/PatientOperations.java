package Main.Components;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import Users.Components.Patient;
import Users.Components.Doctor;
import Documents.Components.Appointment;
import Documents.Components.Prescription;

public class PatientOperations {
  private Patient patient;
  private List<Patient> patients;
  private List<Doctor> doctors;
  private List<Prescription> prescriptions;
  private List<Appointment> appointments;

  public PatientOperations(String username) {
    patients = readPatientsFromCSV();
    doctors = readDoctorsFromCSV();
    prescriptions = readPrescriptionsFromCSV();
    appointments = readAppointmentsFromCSV();
    this.patient = findPatientByUsername(username);
  }

  private List<Patient> readPatientsFromCSV() {
    List<Patient> patients = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("Users/Files/patients.csv"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String username = parts[2];
        String password = parts[3];
        int age = Integer.parseInt(parts[4]);
        String gender = parts[5];
        long phn_no = Long.parseLong(parts[6]);
        double height = Double.parseDouble(parts[7]);
        double weight = Double.parseDouble(parts[8]);

        String[] appointmentIdsArray = parts[9].split("\\|");
        List<Integer> appointmentIds = new ArrayList<>();
        for (String appointmentId : appointmentIdsArray) {
          appointmentIds.add(Integer.parseInt(appointmentId));
        }

        String[] prescriptionIdsArray = parts[10].split("\\|");
        List<Integer> prescriptionIds = new ArrayList<>();
        for (String prescriptionId : prescriptionIdsArray) {
          prescriptionIds.add(Integer.parseInt(prescriptionId));
        }

        Patient patient = new Patient(id, name, username, password, age, gender, phn_no, height, weight);
        patient.setAppointmentIds(appointmentIds);
        patient.setPrescriptionIds(prescriptionIds);
        patients.add(patient);
      }
      // System.out.println("Doctors loaded from CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return patients;
  }

  private List<Doctor> readDoctorsFromCSV() {
    List<Doctor> doctors = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("Users/Files/doctors.csv"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String username = parts[2];
        String password = parts[3];
        int age = Integer.parseInt(parts[4]);
        String gender = parts[5];
        long phn_no = Long.parseLong(parts[6]);
        String specialization = parts[7];
        String workingHours = parts[8];

        List<Integer> bookedPatients = new ArrayList<>();
        List<Integer> appointmentIds = new ArrayList<>();
        if (parts.length > 9) {
          String[] bookedPatientsArray = parts[9].split("\\|");

          for (String patientId : bookedPatientsArray) {
            bookedPatients.add(Integer.parseInt(patientId));
          }

        } else {

          if (parts.length > 10) {
            String[] appointmentIdsArray = parts[10].split("\\|");
            for (String appointmentId : appointmentIdsArray) {
              appointmentIds.add(Integer.parseInt(appointmentId));
            }

          }
        }
        Doctor doctor = new Doctor(id, name, username, password, age, gender, phn_no, specialization,
            workingHours);
        doctor.setBookedPatients(bookedPatients);
        doctor.setAppointments(appointmentIds);

        doctors.add(doctor);
      }
      // System.out.println("Doctors loaded from CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return doctors;
  }

  public static List<Prescription> readPrescriptionsFromCSV() {
    List<Prescription> prescriptions = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("Documents/Files/prescriptions.csv"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        if (parts.length >= 11) {
          int id = Integer.parseInt(parts[0]);
          String date = parts[1];
          String patientName = parts[2];
          int age = Integer.parseInt(parts[3]);
          long phoneNumber = Long.parseLong(parts[4]);
          String gender = parts[5];
          String doctorName = parts[6];
          String specialization = parts[7];

          List<String> symptoms = List.of(parts[8].split("\\|"));
          List<String> medicines = List.of(parts[9].split("\\|"));
          List<String> tests = List.of(parts[10].split("\\|"));

          Prescription prescription = new Prescription(id, date, patientName, age, phoneNumber, gender,
              doctorName, specialization, symptoms, medicines, tests);

          prescriptions.add(prescription);
        } else {
          System.out.println("");

        }
      }
      // System.out.println("Prescriptions loaded from CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return prescriptions;
  }

  public List<Appointment> readAppointmentsFromCSV() {
    List<Appointment> appointments = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("Documents/Files/appointments.csv"))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        int appointmentId = Integer.parseInt(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        LocalTime time = LocalTime.parse(parts[2]);
        String doctorName = parts[3];
        int doctorId = Integer.parseInt(parts[4]);
        String patientName = parts[5];
        int patientId = Integer.parseInt(parts[6]);

        Appointment appointment = new Appointment(appointmentId, date, time, doctorName, doctorId, patientName,
            patientId);
        appointments.add(appointment);
      }
      // System.out.println("Doctors loaded from CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return appointments;
  }

  private Patient findPatientByUsername(String username) {
    for (Patient patient : patients) {
      if (patient.getUsername().equalsIgnoreCase(username)) {
        return patient;
      }
    }
    return null;
  }

  public void viewAppointments() {
    List<Integer> appointmentIds = patient.getAppointmentIds();
    if (!appointmentIds.isEmpty()) {
      System.out.println("\u001B[92mAppointments for " + patient.getName() + ":\u001B[0m");
      for (int appointmentId : appointmentIds) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment != null) {
          appointment.displayDetails();
        } else {
          System.out.println("\u001B[91mAppointment with ID " + appointmentId + " not found.\u001B[0m");
        }
      }
    } else {
      System.out.println("\u001B[91mNo appointments found for " + patient.getName() + "\u001B[0m");
    }
  }

  public void viewPrescriptions() {
    if (patient != null) {
      List<Integer> prescriptionIds = patient.getPrescriptionIds();
      if (!prescriptionIds.isEmpty()) {
        System.out.println("\u001B[92mPrescriptions for " + patient.getName() + ":\u001B[0m");
        for (int prescriptionId : prescriptionIds) {
          Prescription prescription = findPrescriptionById(prescriptionId);
          if (prescription != null) {
            prescription.displayDetails();
          } else {
            System.out.println("\u001B[91mPrescription with ID " + prescriptionId + " not found.\u001B[0m");
          }
        }
      } else {
        System.out.println("\u001B[91mNo prescriptions found for " + patient.getName() + "\u001B[0m");
      }
    }
  }

  private Prescription findPrescriptionById(int prescriptionId) {
    for (Prescription prescription : prescriptions) {
      if (prescription.getId() == prescriptionId) {
        return prescription;
      }
    }
    return null;
  }

  private Appointment findAppointmentById(int appointmentId) {
    for (Appointment appointment : appointments) {
      if (appointment.getId() == appointmentId) {
        return appointment;
      }
    }
    return null;
  }

  private Doctor findDoctorById(int doctorId) {
    for (Doctor doctor : doctors) {
      if (doctor.getId() == doctorId) {
        return doctor;
      }
    }
    return null;
  }

  public void searchDoctorByName(String name) {
    boolean found = false;
    for (Doctor doctor : doctors) {
      if (doctor.getName().equalsIgnoreCase(name)) {
        System.out.println("\u001B[92mDoctor found:\u001B[0m");

        doctor.displayDetails();
        found = true;
        break;
      }
    }
    if (!found) {
      System.out.println("\u001B[91mDoctor with name " + name + " not found.\u001B[0m");

    }
  }

  public void searchDoctorsBySpecialisation(String specialization) {
    boolean found = false;
    System.out.println("Doctors with Specialization '" + specialization + "':");
    for (Doctor doctor : doctors) {
      if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
        doctor.displayDetails();
        found = true;
      }
    }
    if (!found) {
      System.out.println("\u001B[91mNo doctors found with specialization '" + specialization + "'.\u001B[0m");

    }
  }

  public void bookAppointment() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\u001B[96mEnter the ID of the doctor you want to book an appointment with: \u001B[0m");
    int doctorId = scanner.nextInt();
    scanner.nextLine();

    System.out.println("\u001B[96mEnter the date for the appointment (e.g., 2023-12-25): \u001B[0m");
    String dateStr = scanner.nextLine();
    LocalDate date = LocalDate.parse(dateStr);

    System.out.println("\u001B[96mEnter the time for the appointment (e.g., 10:30): \u001B[0m");
    String timeStr = scanner.nextLine();
    LocalTime time = LocalTime.parse(timeStr);

    int id = 7001 + appointments.size();

    String doctorName = findDoctorById(doctorId).getName();
    String patientName = patient.getName();
    Appointment newAppointment = new Appointment(id, date, time, doctorName, doctorId, patientName, patient.getId());
    appointments.add(newAppointment);
    patient.addAppointmentId(newAppointment.getId());
    findDoctorById(doctorId).addAppointmentId(newAppointment.getId());

    writeToAppointmentsCSV();
    writeToPatientsCSV();
    writeToDoctorsCSV();

    System.out.println("\u001B[92mAppointment booked successfully!\u001B[0m");
  }

  private void writeToAppointmentsCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Documents/Files/appointments.csv"))) {
      for (Appointment appointment : appointments) {
        String line = String.format("%d,%s,%s,%s,%d,%s,%d",
            appointment.getId(), appointment.getDate(), appointment.getTime(),
            appointment.getDoctorName(), appointment.getDoctorId(),
            appointment.getPatientName(), appointment.getPatientId());

        writer.write(line);
        writer.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeToPatientsCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users/Files/patients.csv"))) {
      for (Patient patient : patients) {
        StringBuilder line = new StringBuilder();
        line.append(String.format("%d,%s,%s,%s,%d,%s,%d,%.2f,%.2f,",
            patient.getId(), patient.getName(), patient.getUsername(), patient.getPassword(),
            patient.getAge(),
            patient.getGender(), patient.getPhn_no(), patient.getHeight(), patient.getWeight()));

        String appointmentIds = String.join("|",
            patient.getAppointmentIds().stream().map(Object::toString).toArray(String[]::new));
        line.append(appointmentIds).append(",");

        String prescriptionIds = String.join("|",
            patient.getPrescriptionIds().stream().map(Object::toString).toArray(String[]::new));
        line.append(prescriptionIds);

        writer.write(line.toString());
        writer.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeToDoctorsCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users/Files/doctors.csv"))) {
      for (Doctor doctor : doctors) {
        String bookedPatients = String.join("|",
            doctor.getBookedPatients().stream().map(Object::toString).toArray(String[]::new));
        String appointmentIds = String.join("|",
            doctor.getAppointments().stream().map(Object::toString).toArray(String[]::new));

        String line = String.format("%d,%s,%s,%s,%d,%s,%d,%s,%s,%s,%s",
            doctor.getId(), doctor.getName(), doctor.getUsername(), doctor.getPassword(),
            doctor.getAge(), doctor.getGender(), doctor.getPhn_no(), doctor.getSpecialization(),
            doctor.getWorkingHours(), bookedPatients, appointmentIds);

        writer.write(line);
        writer.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void patientOperations() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\u001B[95mPatient Operations Menu:");
      System.out.println("1. View Appointments");
      System.out.println("2. View Prescriptions");
      System.out.println("3. Search Doctors");
      System.out.println("4. Book Appointment");
      System.out.println("5. Exit\u001B[0m");
      System.out.print("\u001B[33mEnter your choice: \u001B[0m");
      choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          viewAppointments();
          break;
        case 2:
          viewPrescriptions();
          break;
        case 3:
          searchDoctors();
          break;
        case 4:
          bookAppointment();
          break;
        case 5:
          System.out.println("\u001B[92mExiting Patient Operations.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (choice != 5);
  }

  private void searchDoctors() {
    Scanner scanner = new Scanner(System.in);
    int searchChoice;
    do {
      System.out.println("\u001B[95mSearch Doctors Options:");
      System.out.println("1. Search by Name");
      System.out.println("2. Search by Specialisation");
      System.out.println("3. Back to Patient Operations\u001B[0m");
      System.out.print("\u001B[33mEnter your choice: \u001B[0m");
      searchChoice = scanner.nextInt();
      scanner.nextLine();
      switch (searchChoice) {
        case 1:
          System.out.print("\u001B[96mEnter Doctor's Name: \u001B[0m");
          String doctorName = scanner.nextLine();
          searchDoctorByName(doctorName);
          break;
        case 2:
          System.out.print("\u001B[96mEnter Doctor's Specialisation: \u001B[0m");
          String specialisation = scanner.nextLine();
          searchDoctorsBySpecialisation(specialisation);
          break;
        case 3:
          System.out.println("\u001B[92mBack to Patient Operations.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[1mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (searchChoice != 3);
  }

  public static void main(String[] args) {
    PatientOperations p = new PatientOperations("sameer.kapoor");
    p.patientOperations();
  }

}