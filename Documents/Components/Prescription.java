package Documents.Components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Prescription {
  private int id;
  private String date;
  private String patientName;
  private int patientAge;
  private long patientPhn_no;
  private String patientGender;
  private String docName;
  private String docSpecialization;
  private List<String> diagonsis;
  private List<String> medicines;
  private List<String> tests;

  private static Map<String, Double> medicinesMap;
  private static Map<String, Double> testsMap;

  static {
    medicinesMap = new HashMap<>();
    testsMap = new HashMap<>();
    populateMap("Inventory/Files/medicines.csv", medicinesMap);
    populateMap("Inventory/Files/tests.csv", testsMap);
  }

  private static void populateMap(String filename, Map<String, Double> map) {
    try {
      Scanner sc = new Scanner(new File(filename));
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        map.put(parts[1], Double.parseDouble(parts[2]));
      }
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }

  public Prescription(int id, String date, String patientName, int patientAge, long patientPhn_no,
      String patientGender, String docName, String docSpecialization,
      List<String> diagonsis, List<String> medicines, List<String> tests) {
    this.id = id;
    this.date = date;
    this.patientName = patientName;
    this.patientAge = patientAge;
    this.patientPhn_no = patientPhn_no;
    this.patientGender = patientGender;
    this.docName = docName;
    this.docSpecialization = docSpecialization;
    this.diagonsis = diagonsis;
    this.medicines = medicines;
    this.tests = tests;
  }

  public int getId() {
    return id;
  }

  public String getDate() {
    return date;
  }

  public String getPatientName() {
    return patientName;
  }

  public int getPatientAge() {
    return patientAge;
  }

  public long getPatientPhn_no() {
    return patientPhn_no;
  }

  public String getPatientGender() {
    return patientGender;
  }

  public String getDocName() {
    return docName;
  }

  public String getDocSpecialization() {
    return docSpecialization;
  }

  public List<String> getDiagonsis() {
    return diagonsis;
  }

  public List<String> getMedicines() {
    return medicines;
  }

  public List<String> getTests() {
    return tests;
  }

  public static Map<String, Double> getMedicinesMap() {
    return medicinesMap;
  }

  public static void setMedicinesMap(Map<String, Double> medicinesMap) {
    Prescription.medicinesMap = medicinesMap;
  }

  public static Map<String, Double> getTestsMap() {
    return testsMap;
  }

  public static void setTestsMap(Map<String, Double> testsMap) {
    Prescription.testsMap = testsMap;
  }

  public void displayDetails() {
    System.out.println("\n\u001B[97m===============================================\u001B[0m");
    System.out.println("\u001B[97m                  HEALTH HUB                     \u001B[0m");
    System.out.println("\u001B[97m===============================================\u001B[0m\n");

    System.out.printf("\u001B[96m%-20s\u001B[0m: %s\n", "Date", date);
    System.out.println("\nPATIENT DETAILS:");
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%s\u001B[0m\n", "Name", patientName);
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%d\u001B[0m\n", "Age", patientAge);
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%d\u001B[0m\n", "Phone Number", patientPhn_no);
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%s\u001B[0m\n", "Gender", patientGender);

    System.out.println("\n\u001B[96mDOCTOR DETAILS:\u001B[0m");
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%s\u001B[0m\n", "Name", docName);
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93m%s\u001B[0m\n", "Specialisation", docSpecialization);

    System.out.println("\n-------------------------------------------------------------\n");

    System.out.println("DIAGNOSIS");

    for (String diagnosis : diagonsis) {
      System.out.println(diagnosis);
    }
    System.out.println("\n\u001B[95mMedicines Prescribed:\u001B[0m");
    displayItemsWithCost(medicines, medicinesMap);

    System.out.println("\n\u001B[95mTests Prescribed:\u001B[0m");
    displayItemsWithCost(tests, testsMap);

    System.out.println("\n--------------------------------------------------------------\n");

    double totalCost = calculateTotalCost();
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93mRs.%.2f\u001B[0m\n", "Subtotal", totalCost);

    double tax = totalCost * 0.1;
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93mRs.%.2f\u001B[0m\n", "Tax (10%)", tax);

    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93mRs.%.2f\u001B[0m\n", "Consultation Fee", 500.0);

    totalCost += tax + 500;
    System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93mRs.%.2f\u001B[0m\n", "Total Cost", totalCost);

    System.out.println("================================================================\n");
  }

  private void displayItemsWithCost(List<String> items, Map<String, Double> costMap) {
    for (String item : items) {
      double itemCost = costMap.getOrDefault(item, 0.0);
      System.out.printf("\u001B[96m%-20s\u001B[0m: \u001B[93mRs.%.2f\u001B[0m\n", item, itemCost);
    }
  }

  private double calculateTotalCost() {
    double totalCost = 0.0;
    for (String medicine : medicines) {
      totalCost += medicinesMap.getOrDefault(medicine, 0.0);
    }

    for (String test : tests) {
      totalCost += testsMap.getOrDefault(test, 0.0);
    }

    return totalCost;
  }

}
