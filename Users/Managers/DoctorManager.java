package Users.Managers;

import Users.Components.Doctor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class DoctorManager {

  private List<Doctor> doctors;

  public DoctorManager() {
    this.doctors = readFromCSV();
  }

  private List<Doctor> readFromCSV() {
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
        if (parts.length > 9) {
          String[] bookedPatientsArray = parts[9].split("\\|");
          for (String patientId : bookedPatientsArray) {
            bookedPatients.add(Integer.parseInt(patientId));
          }
        }

        List<Integer> appointmentIds = new ArrayList<>();
        if (parts.length > 10) {
          String[] appointmentIdsArray = parts[10].split("\\|");
          for (String appointmentId : appointmentIdsArray) {
            appointmentIds.add(Integer.parseInt(appointmentId));
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

  private void writeToCSV() {
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
      // System.out.println("Doctors written to CSV");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addDoctor(String name, String password) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\u001B[96mEnter details for the new doctor:\u001B[0m");

    int id = 4000 + doctors.size() + 1;

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

    System.out.print("\u001B[96mSpecialization: \u001B[0m");
    String specialization = sc.nextLine();

    System.out.print("\u001B[96mWorking Hours: \u001B[0m");
    String workinghours = sc.nextLine();

    Doctor newDoctor = new Doctor(id, name, username, password, age, gender, phn_no, specialization, workinghours);
    doctors.add(newDoctor);

    writeToCSV();
    System.out.println("\u001B[92mDoctor added successfully!\u001B[0m");
  }

  public void displayAllDoctors() {
    for (Doctor doctor : doctors) {
      doctor.displayDetails();
    }
  }

  public Doctor findDoctorByName(String name) {
    for (Doctor doctor : doctors) {
      if (doctor.getName().equalsIgnoreCase(name)) {
        return doctor;
      }
    }
    return null;
  }

  public Doctor findDoctorById(int id) {
    for (Doctor doctor : doctors) {
      if (doctor.getId() == id) {
        return doctor;
      }
    }
    return null;
  }

  public void removeDoctor(String name) {
    Doctor doctorToRemove = findDoctorByName(name);
    if (doctorToRemove != null) {
      doctors.remove(doctorToRemove);
      writeToCSV();
      System.out.println("\u001B[1;32mDoctor removed successfully!\u001B[0m");
    } else {
      System.out.println("\u001B[91m" + name + " not found.\u001B[0m");

    }
  }

  public void searchDoctorByName(String name) {
    for (Doctor doctor : doctors) {
      if (doctor.getName().equalsIgnoreCase(name)) {
        System.out.println("Doctor found:");
        doctor.displayDetails();
        return;
      }
    }
    System.out.println("\u001B[91mDoctor with name " + name + " not found.\u001B[0m");

  }

  public void searchDoctorById(int id) {
    for (Doctor doctor : doctors) {
      if (doctor.getId() == id) {
        System.out.println("Doctor found:");
        doctor.displayDetails();
        return;
      }
    }
    System.out.println("\u001B[91mDoctor with ID " + id + " not found.\u001B[0m");

  }

  public void searchDoctorsBySpecialization(String specialization) {
    System.out.println("Doctors with Specialization '" + specialization + "':");
    for (Doctor doctor : doctors) {
      if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
        doctor.displayDetails();
      }
    }
  }

  public static boolean isStrongPassword(String password) {
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

  public void doctorOperations() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\u001B[95mDoctor Management System");
      System.out.println("1. View Doctors");
      System.out.println("2. Add Doctor");
      System.out.println("3. Remove Doctor");
      System.out.println("4. Search Doctor");
      System.out.println("5. Exit\u001B[0m");
      System.out.print("\u001B[93mEnter your choice: \u001B[0m");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          displayAllDoctors();
          break;
        case 2:
          System.out.print("\u001B[96mEnter name of doctor: \u001B[0m");
          String name = scanner.nextLine();
          do {
            System.out.print("\u001B[96mEnter your password: \u001B[0m");
            String password = scanner.nextLine();
            if (isStrongPassword(password)) {
              addDoctor(name, password);
              break;
            } else {
              System.out.println("\u001B[91mPassword is not strong. Please follow the criteria and reenter.\u001B[0m");
            }
          } while (true);
          break;

        case 3:
          System.out.print("\u001B[96mEnter name of doctor: \u001B[0m");
          String n = scanner.nextLine();
          removeDoctor(n);
          break;

        case 4:
          searchDoctorOptions();
          break;
        case 5:
          System.out.println("\u001B[92mExiting Doctor Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (choice != 5);
  }

  public void searchDoctorOptions() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\u001B[95mDoctor Search Options:");
      System.out.println("1. Search by name");
      System.out.println("2. Search by ID");
      System.out.println("3. Search by Specialization");
      System.out.println("4. Back to Doctor Management System\u001B[0m");
      System.out.print("\u001B[93mEnter your choice: \u001B[0m");
      choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          System.out.print("\u001B[96mEnter name of doctor: \u001B[0m");
          String name = scanner.nextLine();
          searchDoctorByName(name);
          break;
        case 2:
          System.out.print("\u001B[96mEnter ID of doctor: \u001B[0m");
          int id = scanner.nextInt();
          searchDoctorById(id);
          break;
        case 3:
          System.out.print("\u001B[96mEnter specialization of doctor: \u001B[0m");
          String specialization = scanner.nextLine();
          searchDoctorsBySpecialization(specialization);
          break;
        case 4:
          System.out.println("\u001B[92mBack to Doctor Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
          break;
      }
      System.out.println();
    } while (choice != 4);
  }
}
