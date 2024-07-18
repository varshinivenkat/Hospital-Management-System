package Main.Components;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import Users.Components.Patient;
import Users.Components.Doctor;
import Documents.Components.Appointment;
import Documents.Components.Prescription;

public class DoctorOperations {
  private Doctor doctor;
  private List<Patient> patients;
  private List<Doctor> doctors;
  private List<Prescription> prescriptions;
  private List<Appointment> appointments;

  public DoctorOperations(String username) {
    patients = readPatientsFromCSV();
    doctors = readDoctorsFromCSV();
    prescriptions = readPrescriptionsFromCSV();
    appointments = readAppointmentsFromCSV();
    this.doctor = getDoctorByUsername(username);
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
          if (parts[9].equals("")) {
            break;
          } else {
            String[] bookedPatientsArray = parts[9].split("\\|");

            for (String patientId : bookedPatientsArray) {
              bookedPatients.add(Integer.parseInt(patientId));
            }
          }
        } else {

          if (parts.length > 10) {

            if (parts[9].equals("")) {
              break;
            } else {
              String[] appointmentIdsArray = parts[10].split("\\|");
              for (String appointmentId : appointmentIdsArray) {
                appointmentIds.add(Integer.parseInt(appointmentId));
              }
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
    } catch (IOException e) {
      e.printStackTrace();
    }

    return appointments;
  }

  private Doctor getDoctorByUsername(String username) {
    for (Doctor doctor : doctors) {
      if (doctor.getUsername().equalsIgnoreCase(username)) {
        return doctor;
      }
    }
    return null;
  }

  private Patient findPatientById(int patientId) {
    for (Patient patient : patients) {
      if (patient.getId() == patientId) {
        return patient;
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

  public void viewAppointments() {
    List<Integer> appointmentIds = doctor.getAppointments();

    if (!appointmentIds.isEmpty()) {
      System.out.println("Appointments for " + doctor.getName() + ":");
      for (int appointmentId : appointmentIds) {
        Appointment appointment = findAppointmentById(appointmentId);

        if (appointment != null) {
          appointment.displayDetails();
        } else {
          System.out.println("\u001B[1;91mAppointment with ID " + appointmentId + " not found.\u001B[0m");

        }
      }
    } else {
      System.out.println("\u001B[91mNo appointments found for Dr. " + doctor.getName() + "\u001B[0m");

    }
  }

  public void viewPatientDetails() {
    List<Integer> bookedPatients = doctor.getBookedPatients();

    if (!bookedPatients.isEmpty()) {
      System.out.println("Booked Patients for " + doctor.getName() + ":");
      for (int patientId : bookedPatients) {
        Patient patient = findPatientById(patientId);

        if (patient != null) {
          System.out.println("Details for Patient " + patient.getName() + ":");
          patient.displayDetails();
        } else {
          System.out.println("Patient with ID " + patientId + " not found.");
        }
      }
    } else {
      System.out.println("No booked patients found for Doctor " + doctor.getName());
    }
  }

  public void createPrescription() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\u001B[96mEnter the patient ID for whom you want to create a prescription: \u001B[0m");
    int patientId = scanner.nextInt();
    scanner.nextLine();

    System.out.println("\u001B[96mEnter the appointment ID for which you want to create a prescription: \u001B[0m");
    int appointmentId = scanner.nextInt();
    scanner.nextLine();

    Patient patient = findPatientById(patientId);
    Appointment appointment = findAppointmentById(appointmentId);

    if (patient != null && appointment != null && appointment.getDoctorId() == doctor.getId()) {
      List<String> diagnosis = new ArrayList<>();
      List<String> medicines = new ArrayList<>();
      List<String> tests = new ArrayList<>();

      String input;

      System.out.println("\u001B[96mEnter diagnosis (type 'done' to finish): \u001B[0m");
      while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
        diagnosis.add(input);
      }

      System.out.println("\u001B[96mEnter medicines (type 'done' to finish): \u001B[0m");
      while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
        medicines.add(input);
      }

      System.out.println("\u001B[96mEnter tests (type 'done' to finish): \u001B[0m");
      while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
        tests.add(input);
      }

      String formattedDateTime = appointment.getDate().toString();
      int prescriptionId = 1001 + prescriptions.size();
      Prescription prescription = new Prescription(prescriptionId, formattedDateTime,
          patient.getName(), patient.getAge(), patient.getPhn_no(), patient.getGender(),
          doctor.getName(), doctor.getSpecialization(), diagnosis, medicines, tests);

      prescriptions.add(prescription);
      patient.addPrescriptionId(prescriptionId);

      patient.removeAppointmentId(appointmentId);
      appointments.remove(appointment);
      doctor.removeBookedPatientId(patientId);
      doctor.removeAppointmentId(appointmentId);

      writeToPrescriptionsCSV();
      writeToPatientsCSV();
      writeToAppointmentsCSV();
      writeToDoctorsCSV();

      System.out.println("\u001B[92mPrescription created successfully!\u001B[0m");
    } else {
      System.out.println("\u001B[91mInvalid patient or appointment ID. Prescription creation failed.\u001B[0m");
    }
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

  private void writeToPrescriptionsCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Documents/Files/prescriptions.csv"))) {
      for (Prescription prescription : prescriptions) {
        String formattedDateTime = prescription.getDate();
        String line = String.format("%d,%s,%s,%d,%d,%s,%s,%s,%s,%s,%s",
            prescription.getId(), formattedDateTime, prescription.getPatientName(),
            prescription.getPatientAge(), prescription.getPatientPhn_no(), prescription.getPatientGender(),
            prescription.getDocName(), prescription.getDocSpecialization(),
            String.join("|", prescription.getDiagonsis()),
            String.join("|", prescription.getMedicines()),
            String.join("|", prescription.getTests()));

        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void doctorOperations() {
    Scanner scanner = new Scanner(System.in);
    int choice;

    do {
      System.out.println("\u001B[35mDoctor Operations Menu:");
      System.out.println("1. View Appointments");
      System.out.println("2. View Patient Details");
      System.out.println("3. Create Prescription");
      System.out.println("4. Exit\u001B[0m");
      System.out.print("\u001B[33mEnter your choice: \u001B[0m");

      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          viewAppointments();
          break;
        case 2:
          viewPatientDetails();
          break;
        case 3:
          createPrescription();
          break;
        case 4:
          System.out.println("\u001B[92mExiting Doctor Operations.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }

      System.out.println();
    } while (choice != 4);

  }

  public static void main(String[] args) {
    String username = "anika.sharma";
    DoctorOperations doctorOperations = new DoctorOperations(username);
    doctorOperations.doctorOperations();
  }
}
