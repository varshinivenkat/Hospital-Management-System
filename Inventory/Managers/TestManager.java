package Inventory.Managers;

import Inventory.Items.*;
import java.io.*;
import java.util.*;

public class TestManager {
  private List<Test> tests;

  public TestManager() {
    this.tests = readFromCSV();
  }

  private List<Test> readFromCSV() {
    List<Test> tests = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("Inventory/Files/tests.csv"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double cost = Double.parseDouble(parts[2]);
        String department = parts[3];

        Test test = new Test(id, name, cost, department);
        tests.add(test);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tests;
  }

  private void writeToCSV() {
    try (BufferedWriter writer_test = new BufferedWriter(new FileWriter("Inventory/Files/tests.csv"))) {
      for (Test test : tests) {
        String line = String.format("%d,%s,%.2f,%s",
            test.getId(), test.getName(), test.getCost(), test.getDept());
        writer_test.write(line);
        writer_test.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addTest() {
    Scanner s = new Scanner(System.in);

    System.out.println("\u001B[94mEnter details for the new test:\u001B[0m");

    int test_id = 2000 + tests.size() + 1;

    System.out.print("\u001B[95mName: \u001B[0m");
    String test_name = s.nextLine();

    System.out.print("\u001B[95mCost: \u001B[0m");
    double test_cost = s.nextDouble();

    System.out.print("\u001B[95mDepartment: \u001B[0m");
    String test_department = s.next();

    Test existingTest = findTestByName(test_name);

    if (existingTest != null) {
      System.out.println("\u001B[91mTest with name '" + test_name + "' already exists.\u001B[0m");
    } else {
      Test newTest = new Test(test_id, test_name, test_cost, test_department);
      tests.add(newTest);

      writeToCSV();
      System.out.println("\u001B[92mTest added successfully!\u001B[0m");
    }
  }

  public void displayAllTests() {
    for (Test test : tests) {
      test.displayDetails();
    }
  }

  public Test findTestByName(String name) {
    for (Test test : tests) {
      if (test.getName().equalsIgnoreCase(name)) {
        return test;
      }
    }
    return null;
  }

  public Test findTestById(int id) {
    for (Test test : tests) {
      if (test.getId() == id) {
        return test;
      }
    }
    return null;
  }

  public void removeTest(String name) {
    Test testToRemove = findTestByName(name);
    if (testToRemove != null) {
      tests.remove(testToRemove);
      writeToCSV();
      System.out.println("\u001B[92m" + name + " removed successfully!\u001B[0m");
    } else {
      System.out.println("\u001B[91m" + name + " not found.\u001B[0m");
    }
  }

  public void searchTestByName(String name) {
    Test test = findTestByName(name);

    if (test != null) {
      System.out.println("\u001B[92mTest found:\u001B[0m");
      test.displayDetails();
    } else {
      System.out.println("\u001B[91mTest with name '" + name + "' not found.\u001B[0m");
    }
  }

  public void searchTestById(int id) {
    Test test = findTestById(id);

    if (test != null) {
      System.out.println("\u001B[92mTest found:\u001B[0m");
      test.displayDetails();
    } else {
      System.out.println("\u001B[91mTest with ID " + id + " not found.\u001B[0m");
    }
  }

  public void searchTestByDepartment(String department) {
    System.out.println("\u001B[94mTests in Department '" + department + "':\u001B[0m");
    for (Test test : tests) {
      if (test.getDept().equalsIgnoreCase(department)) {
        test.displayDetails();
      }
    }
  }

  public void testOperations() {
    Scanner sc = new Scanner(System.in);
    int choice;
    do {
      System.out.println();
      System.out.println("\u001B[94mTest Management System\u001B[0m");
      System.out.println("\u001B[96m1. View all Tests\u001B[0m");
      System.out.println("\u001B[96m2. Add Test\u001B[0m");
      System.out.println("\u001B[96m3. Remove Test\u001B[0m");
      System.out.println("\u001B[96m4. Search Test\u001B[0m");
      System.out.println("\u001B[96m5. Exit\u001B[0m");
      System.out.println("\u001B[93mEnter your choice: \u001B[0m");
      choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {
        case 1:
          displayAllTests();
          break;
        case 2:
          addTest();
          break;
        case 3:
          System.out.println("\u001B[95m;Enter name of test: \u001B[0m");
          String name = sc.nextLine();
          removeTest(name);
          break;
        case 4:
          searchTestOptions();
          break;
        case 5:
          System.out.println("\u001B[91mExiting Test Management System.\u001B[0m");
          break;
        default:
          System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
      }
    } while (choice != 5);

  }

  private void searchTestOptions() {
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
          System.out.println("\u001B[95mEnter name of test: \u001B[0m");
          String name1 = sc.nextLine();
          searchTestByName(name1);
          break;
        case 2:
          System.out.println("\u001B[95mEnter ID of test: \u001B[0m");
          int id = sc.nextInt();
          sc.nextLine();
          searchTestById(id);
          break;
        case 3:
          System.out.println("\u001B[95mEnter department: \u001B[0m");
          String department = sc.next();
          searchTestByDepartment(department);
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
