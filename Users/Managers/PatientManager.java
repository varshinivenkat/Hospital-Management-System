package Users.Managers;

import Users.Components.Patient;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class PatientManager {

  private List<Patient> patients;

  public PatientManager() {
    this.patients = readFromCSV();
  }

  private List<Patient> readFromCSV() {
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
      // System.out.println("Patients loaded from CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return patients;
  }

  private void writeToCSV() {
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
      System.out.println("\u001B[92mPatients written to CSV\u001B[0m");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isStrongPassword(String password) {
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

  public void addPatient(String name, String password) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\u001B[96mEnter details for the new patient:\u001B[0m");

    int id = 5000 + patients.size() + 1;

    System.out.print("\u001B[96mUsername: \u001B[0m");
    String username = sc.nextLine();

    System.out.print("\u001B[96mAge: \u001B[0m");
    int age = sc.nextInt();
    sc.nextLine();

    System.out.print("\u001B[96mGender: \u001B[0m");
    String gender = sc.nextLine();

    System.out.print("\u001B[96mPhone Number: \u001B[0m");
    long phn_no = sc.nextLong();
    sc.nextLine();

    System.out.print("\u001B[96mHeight: \u001B[0m");
    double height = sc.nextDouble();

    System.out.print("\u001B[96mWeight: \u001B[0m");
    double weight = sc.nextDouble();

    Patient newPatient = new Patient(id, name, username, password, age, gender, phn_no, height, weight);
    patients.add(newPatient);

    writeToCSV();
    System.out.println("\u001B[92mPatient added successfully!\u001B[0m");
  }

  public void displayAllPatients() {
    for (Patient patient : patients) {
      patient.displayDetails();
    }
  }

  public Patient findPatientByName(String name) {
    for (Patient patient : patients) {
      if (patient.getName().equalsIgnoreCase(name)) {
        return patient;
      }
    }
    return null;
  }

  public Patient findPatientById(int id) {
    for (Patient patient : patients) {
      if (patient.getId() == id) {
        return patient;
      }
    }
    return null;
  }

  public void searchPatientByName(String name) {
    for (Patient patient : patients) {
      if (patient.getName().equalsIgnoreCase(name)) {
        System.out.println("Patient found:");
        patient.displayDetails();
        return;
      }
    }
    System.out.println("\u001B[91mPatient with name " + name + " not found.\u001B[0m");

  }

  public void searchPatientById(int id) {
    for (Patient patient : patients) {
      if (patient.getId() == id) {
        System.out.println("\u001B[92mPatient found:\u001B[0m");

        patient.displayDetails();
        return;
      }
    }
    System.out.println("\u001B[91mPatient with ID " + id + " not found.\u001B[0m");

  }

  public void patientOperations() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\u001B[95mPatient Management System");
      System.out.println("1. View Patients");
      System.out.println("2. Add Patient");
      System.out.println("3. Search Patient");
      System.out.println("4. Exit\u001B[0m");
      System.out.print("\u001B[96mEnter your choice: \u001B[0m");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          displayAllPatients();
          break;
        case 2:
          System.out.print("\u001B[96mEnter name of patient: \u001B[0m");
          String name = scanner.nextLine();
          do {
            System.out.print("\u001B[96mEnter your password: \u001B[0m");
            String password = scanner.nextLine();
            if (isStrongPassword(password)) {
              addPatient(name, password);
              break;
            } else {
              System.out.println("\u001B[91mPassword is not strong. Please follow the criteria and reenter.\u001B[0m");
            }
          } while (true);
          break;
        case 3:
          searchPatientOptions();
          break;
        case 4:
          System.out.println("\u001B[92mExiting Patient Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (choice != 4);
  }

  public void searchPatientOptions() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\u001B[95mPatient Search Options:");
      System.out.println("1. Search by name");
      System.out.println("2. Search by ID");
      System.out.println("3. Back to Patient Management System\u001B[0m");
      System.out.print("\u001B[96mEnter your choice: \u001B[0m");
      choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          System.out.print("\u001B[96mEnter name of patient: \u001B[0m");
          String name = scanner.nextLine();
          searchPatientByName(name);
          break;
        case 2:
          System.out.print("\u001B[96mEnter ID of patient: \u001B[0m");
          int id = scanner.nextInt();
          searchPatientById(id);
          break;
        case 3:
          System.out.println("\u001B[92mBack to Patient Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (choice != 3);
  }

}
