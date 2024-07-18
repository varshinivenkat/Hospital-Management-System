package Inventory.Managers;

import Inventory.Items.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class MedicineManager {
  private List<Medicine> medicines;

  public MedicineManager() {
    this.medicines = readFromCSV();
  }

  private List<Medicine> readFromCSV() {
    List<Medicine> medicinesList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("Inventory/Files/medicines.csv"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double cost = Double.parseDouble(parts[2]);
        LocalDate mfd = LocalDate.parse(parts[3]);
        LocalDate expiry = LocalDate.parse(parts[4]);
        int stock = Integer.parseInt(parts[5]);
        String department = parts[6].trim();

        Medicine medicine = new Medicine(id, name, cost, mfd, expiry, stock, department);
        medicinesList.add(medicine);
      }
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
    return medicinesList;
  }

  private void writeToCSV() {
    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter("Inventory/Files/medicines.csv"))) {
      for (Medicine med : medicines) {
        String line = String.format("%d,%s,%.2f,%s,%s,%d,%s",
            med.getId(), med.getName(), med.getCost(), med.getMfd(), med.getExpiry(), med.getStock(),
            med.getDept());
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private LocalDate validateDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      System.out.println("\u001B[91mError: Invalid date format. Please use YYYY-MM-DD.\u001B[0m");
      throw e;
    }
  }

  private void addStock(Medicine existingMedicine, int additionalStock) {
    int currentStock = existingMedicine.getStock();
    existingMedicine.setStock(currentStock + additionalStock);
    writeToCSV();
  }

  private Medicine findMedicineByName(String name) {
    for (Medicine medicine : medicines) {
      if (medicine.getName().equalsIgnoreCase(name)) {
        return medicine;
      }
    }
    return null;
  }

  private Medicine findMedicineById(int id) {
    for (Medicine medicine : medicines) {
      if (medicine.getId() == id) {
        return medicine;
      }
    }
    return null;
  }

  public void displayAllMedicines() {
    for (Medicine med : medicines) {
      med.displayDetails();
    }
  }

  public void addMedicine() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\u001B[94mEnter details for the new medicine:\u001B[0m");

    System.out.print("\u001B[95mName: \u001B[0m");
    String name = sc.next();

    Medicine existingMedicine = findMedicineByName(name);

    if (existingMedicine != null) {
      System.out
          .println("\u001B[91mMedicine with name '" + name + "' already exists. Adding to stock instead.\u001B[0m");

      System.out.print("\u001B[95mAdditional Stock: \u001B[0m");
      int additionalStock = sc.nextInt();

      addStock(existingMedicine, additionalStock);

      System.out.println("\u001B[92mStock added successfully!\u001B[0m");
    } else {
      int id = 1000 + medicines.size() + 1;
      System.out.print("\u001B[95mCost: \u001B[m");
      double cost = sc.nextDouble();

      System.out.print("\u001B[95mManufacturing Date (YYYY-MM-DD): \u001B[0m");
      LocalDate mfd = validateDate(sc.next());

      if (mfd.isAfter(LocalDate.now())) {
        System.out
            .println("\u001B[91mError: Manufacturing date should be before or equal to the current date.\u001B[0m");
        return;
      }

      System.out.print("\u001B[95mExpiry Date (YYYY-MM-DD): \u001B[0m");
      LocalDate expiry = validateDate(sc.next());

      System.out.print("\u001B[95mStock: \u001B[0m");
      int stock = sc.nextInt();

      System.out.print("\u001B[95mDepartment: \u001B[0m");
      String department = sc.next();

      Medicine newMedicine = new Medicine(id, name, cost, mfd, expiry, stock, department);
      medicines.add(newMedicine);
      writeToCSV();
      System.out.println("\u001B[92mMedicine added successfully!\u001B[0m");
    }
  }

  public void addAdditionalStock() {
    Scanner sc = new Scanner(System.in);
    System.out.println("\u001B[94mEnter details of medicine:\u001B[0m");
    System.out.print("\u001B[95mName: \u001B[0m");
    String name = sc.nextLine();
    Medicine existingMedicine = findMedicineByName(name);
    if (existingMedicine == null) {
      System.out.println("\u001B[91mMedicine with name '" + name + "' does not exist.\u001B[0m");
      return;
    }
    System.out.print("\u001B[95mAdditional Stock: \u001B[0m");
    int additionalStock = sc.nextInt();
    addStock(existingMedicine, additionalStock);
    System.out.println("\u001B[92mStock added successfully!\u001B[0m");
  }

  public void removeMedicine(String name) {
    Medicine medicineToRemove = findMedicineByName(name);

    if (medicineToRemove != null) {
      medicines.remove(medicineToRemove);
      writeToCSV();
      System.out.println("\u001B[92m" + name + " removed successfully.\u001B[0m");
    } else {
      System.out.println("\u001B[91m" + name + " not found. No action taken.\u001B[0m");
    }
  }

  public void searchMedicineByName(String name) {
    Medicine medicine = findMedicineByName(name);

    if (medicine != null) {
      System.out.println("\u001B[92mMedicine found:\u001B[0m");
      medicine.displayDetails();
    } else {
      System.out.println("\u001B[91mMedicine with name '" + name + "' not found.\u001B[0m");
    }
  }

  public void searchMedicineById(int id) {
    Medicine medicine = findMedicineById(id);

    if (medicine != null) {
      System.out.println("\u001B[92mMedicine found:\u001B[0m");
      medicine.displayDetails();
    } else {
      System.out.println("\u001B[91mMedicine with ID " + id + " not found.\u001B[0m");
    }
  }

  public void searchMedicineByDepartment(String department) {
    System.out.println("\u001B[94mMedicines in Department '" + department + "':\u001B[0m");
    for (Medicine med : medicines) {
      if (med.getDept().equalsIgnoreCase(department)) {
        med.displayDetails();
      }
    }
  }

  public void displayExpiringMedicines() {
    System.out.println("\u001B[94mExpiring Medicines within 30 days:\u001B[0m");
    LocalDate currentDate = LocalDate.now();
    for (Medicine med : medicines) {
      if (med.getExpiry().isBefore(currentDate.plusDays(30))) {
        med.displayDetails();
      }
    }
  }

  public void displayLowStockMedicines() {
    System.out.println("\u001B[94mMedicines with Low Stock (Less than 20):\u001B[0m");
    for (Medicine med : medicines) {
      if (med.getStock() < 20) {
        med.displayDetails();
      }
    }
  }

  public void medicineOperations() {
    Scanner sc = new Scanner(System.in);
    int choice;
    System.out.println("==================================================");
    System.out.println("\u001B[94mMEDICINE MANAGEMENT SYSTEM\u001B[0m");
    System.out.println("==================================================");
    do {

      System.out.println("\u001B[96m1. View Medicines\u001B[0m");
      System.out.println("\u001B[96m2. Add Medicine\u001B[0m");
      System.out.println("\u001B[96m3. Remove Medicine\u001B[0m");
      System.out.println("\u001B[96m4. Search Medicine\u001B[0m");
      System.out.println("\u001B[96m5. Exit\u001B[0m");
      System.out.println("\u001B[93mEnter your choice: \u001B[0m");
      choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {
        case 1:
          viewMedicineOptions();
          break;
        case 2:
          addMedicineOptions();
          break;
        case 3:
          System.out.println("\u001B[95mEnter name of medicine: \u001B[0m");
          String name = sc.nextLine();
          removeMedicine(name);
          break;
        case 4:
          searchMedicineOptions();
          break;
        case 5:
          System.out.println("\u001B[91mExiting Medicine Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
      }
    } while (choice != 5);

  }

  private void viewMedicineOptions() {
    Scanner sc = new Scanner(System.in);
    int choice1;
    do {
      System.out.println();
      System.out.println("\u001B[96m1. View All Medicines\u001B[0m");
      System.out.println("\u001B[96m2. View Expiring Medicines\u001B[0m");
      System.out.println("\u001B[96m3. View Low Stock Medicines\u001B[0m");
      System.out.println("\u001B[96m4. Exit to Main Menu\u001B[0m");
      System.out.println("\u001B[93mEnter your choice: \u001B[0m");
      choice1 = sc.nextInt();
      sc.nextLine();

      switch (choice1) {
        case 1:
          displayAllMedicines();
          break;
        case 2:
          displayExpiringMedicines();
          break;
        case 3:
          displayLowStockMedicines();
          break;
        case 4:
          System.out.println("\u001B[91mExiting to Main Menu.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
      }
    } while (choice1 != 4);
  }

  private void addMedicineOptions() {
    Scanner sc = new Scanner(System.in);
    int choice2;
    do {
      System.out.println();
      System.out.println("\u001B[96m1. Add new Medicine\u001B[0m");
      System.out.println("\u001B[96m2. Add Additional Stock\u001B[0m");
      System.out.println("\u001B[96m3. Exit to Main Menu\u001B[0m");
      System.out.println("\u001B[93mEnter your choice: \u001B[0m");
      choice2 = sc.nextInt();
      sc.nextLine();

      switch (choice2) {
        case 1:
          addMedicine();
          break;
        case 2:
          addAdditionalStock();
          break;
        case 3:
          System.out.println("\u001B[91mExiting to Main Menu.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
      }
    } while (choice2 != 3);
  }

  private void searchMedicineOptions() {
    Scanner sc = new Scanner(System.in);
    int choice3;
    do {
      System.out.println();
      System.out.println("\u001B[96m1. Search by Name\u001B[0m");
      System.out.println("\u001B[96m2. Search by ID\u001B[0m");
      System.out.println("\u001B[96m3. Search by Department\u001B[0m");
      System.out.println("\u001B[96m4. Exit to Main Menu\u001B[0m");
      System.out.println("\u001B[93mEnter your choice: \u001B[0m");
      choice3 = sc.nextInt();
      sc.nextLine();

      switch (choice3) {
        case 1:
          System.out.println("\n\u001B[95mEnter name of medicine: \u001B[0m");
          String name1 = sc.nextLine();
          searchMedicineByName(name1);
          break;
        case 2:
          System.out.println("\n\u001B[95mEnter ID of medicine: \u001B[0m");
          int id = sc.nextInt();
          sc.nextLine();
          searchMedicineById(id);
          break;
        case 3:
          System.out.println("\n\u001B[95mEnter department: \u001B[0m");
          String department = sc.next();
          searchMedicineByDepartment(department);
          break;
        case 4:
          System.out.println("\u001B[91mExiting to Main Menu.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
      }
    } while (choice3 != 4);
  }
}