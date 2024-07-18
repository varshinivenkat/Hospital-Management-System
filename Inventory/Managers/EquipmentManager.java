package Inventory.Managers;

import Inventory.Items.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class EquipmentManager {
  private List<Equipment> equipments;

  public EquipmentManager() {
	this.equipments = readFromCSV();
  }

  private List<Equipment> readFromCSV() {
	List<Equipment> equipments = new ArrayList<>();
	try (BufferedReader reader = new BufferedReader(new FileReader("Inventory/Files/equipments.csv"))) {
	  String line;
	  while ((line = reader.readLine()) != null) {
		String[] parts = line.split(",");
		int id = Integer.parseInt(parts[0]);
		String name = parts[1];
		double cost = Double.parseDouble(parts[2]);
		int stock = Integer.parseInt(parts[3]);
		String department = parts[4];

		Equipment equ = new Equipment(id, name, cost, stock, department);
		equipments.add(equ);
	  }
	} catch (IOException e) {
	  e.printStackTrace();
	}
	return equipments;
  }

  private void writeToCSV() {
	try (BufferedWriter writer_equ = new BufferedWriter(new FileWriter("Inventory/Files/equipments.csv"))) {
	  for (Equipment equ : equipments) {
		String line = String.format("%d,%s,%.2f,%d,%s",
			equ.getId(), equ.getName(), equ.getCost(), equ.getStock(), equ.getDept());
		writer_equ.write(line);
		writer_equ.newLine();
	  }
	} catch (IOException e) {
	  e.printStackTrace();
	}
  }

  public void addEquipment() {
	Scanner sca = new Scanner(System.in);
	System.out.println("\u001B[94mEnter details for the new Equipment:\u001B[0m");

	int equ_id = 2000 + equipments.size() + 1;

	System.out.print("\u001B[95mName: \u001B[0m");
	String equ_name = sca.nextLine();

	Equipment existingEquipment = findEquipmentByName(equ_name);

	if (existingEquipment != null) {
	  System.out.println("\u001B[91mEquipment with name '" + equ_name + "' already exists.\u001B[0m");
	} else {
	  System.out.print("\u001B[95mCost: \u001B[0m");
	  double equ_cost = sca.nextDouble();

	  System.out.print("\u001B[95mStock: \u001B[0m");
	  int equ_stock = sca.nextInt();

	  System.out.print("\u001B[95mDepartment: \u001B[0m");
	  String equ_department = sca.next();

	  Equipment newEquipment = new Equipment(equ_id, equ_name, equ_cost, equ_stock, equ_department);
	  equipments.add(newEquipment);

	  writeToCSV();

	  System.out.println("\u001B[92mEquipment added successfully!\u001B[0m");
	}

	sca.close();
  }

  public void displayAllEquipments() {
	for (Equipment equi : equipments) {
	  equi.displayDetails();
	}
  }

  public void addStockToEquipment() {
	Scanner sc = new Scanner(System.in);

	System.out.print("\u001B[95mEnter equipment name: \u001B[0m");
	String name = sc.nextLine();

	Equipment equipmentToUpdate = findEquipmentByName(name);

	if (equipmentToUpdate != null) {
	  System.out.print("\u001B[95mEnter additional stock quantity: \u001B[0m");
	  int additionalStock = sc.nextInt();

	  int currentStock = equipmentToUpdate.getStock();
	  equipmentToUpdate.setStock(currentStock + additionalStock);

	  writeToCSV();

	  System.out.println("\u001B[92mStock updated successfully!\u001B[0m");
	} else {
	  System.out.println("\u001B[91mEquipment with name '" + name + "' not found.\u001B[0m");
	}
  }

  private Equipment findEquipmentByName(String name) {
	for (Equipment equipment : equipments) {
	  if (equipment.getName().equalsIgnoreCase(name)) {
		return equipment;
	  }
	}
	return null;
  }

  private Equipment findEquipmentById(int id) {
	for (Equipment equipment : equipments) {
	  if (equipment.getId() == id) {
		return equipment;
	  }
	}
	return null;
  }

  public void removeEquipment(String name) {
	Equipment equipmentToRemove = findEquipmentByName(name);
	if (equipmentToRemove != null) {
	  equipments.remove(equipmentToRemove);
	  writeToCSV();
	  System.out.println("\u001B[92mEquipment removed successfully!\u001B[0m");
	} else {
	  System.out.println("\u001B[91m"+ name + "' not found.\u001B[0m");
	}
  }

  public void searchEquipmentByName(String name) {
	Equipment equipment = findEquipmentByName(name);

	if (equipment != null) {
	  System.out.println("\u001B[92mEquipment found:\u001B[0m");
	  equipment.displayDetails();
	} else {
	  System.out.println("\u001B[91mEquipment with name '" + name + "' not found.\u001B[0m");
	}
  }

  public void searchEquipmentById(int id) {
	Equipment equipment = findEquipmentById(id);

	if (equipment != null) {
	  System.out.println("\u001B[92mEquipment found:\u001B[0m");
	  equipment.displayDetails();
	} else {
	  System.out.println("\u001B[91mEquipment with ID " + id + " not found.\u001B[0m");
	}
  }

  public void searchEquipmentByDepartment(String department) {
	System.out.println("\u001B[94mEquipments in Department '" + department + "':\u001B[0m");
	for (Equipment equ : equipments) {
	  if (equ.getDept().equalsIgnoreCase(department)) {
		equ.displayDetails();
	  }
	}
  }

  public void equipmentOperations() {
	Scanner sc = new Scanner(System.in);
	int choice;
	do {
    System.out.println();
	  System.out.println("\u001B[94mEquipment Management System\u001B[0m");
	  System.out.println("\u001B[96m1. View all Equipments\u001B[0m");
	  System.out.println("\u001B[96m2. Add Equipment\u001B[0m");
	  System.out.println("\u001B[96m3. Add Stock to Equipment\u001B[0m");
	  System.out.println("\u001B[96m4. Remove Equipment\u001B[0m");
	  System.out.println("\u001B[96m5. Search Equipment\u001B[0m");
	  System.out.println("\u001B[96m6. Exit\u001B[96m");
	  System.out.println("\u001B[93mEnter your choice: \u001B[0rrrm");
	  choice = sc.nextInt();
	  sc.nextLine();

	  switch (choice) {
		case 1:
		  displayAllEquipments();
		  break;
		case 2:
		  addEquipment();
		  break;
		case 3:
		  addStockToEquipment();
		  break;
		case 4:
		  System.out.println("\u001B[95mEnter name of Equipment: \u001B[0m");
		  String name = sc.nextLine();
		  removeEquipment(name);
		  break;
		case 5:
		  searchEquipmentOptions();
		  break;
		case 6:
		  System.out.println("\u001B[91mExiting Equipment Management System.\u001B[0m");
		  break;
		default:
		  System.out.println("\u001B[91mInvalid choice. Please try again.\u001B[0m");
	  }
	} while (choice != 6);

  }

  private void searchEquipmentOptions() {
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
		  System.out.println("\u001B[95mEnter name of equipment: \u001B[0m");
		  String name1 = sc.nextLine();
		  searchEquipmentByName(name1);
		  break;
		case 2:
		  System.out.println("\u001B[95mEnter ID of equipment: \u001B[0m");
		  int id = sc.nextInt();
		  sc.nextLine();
		  searchEquipmentById(id);
		  break;
		case 3:
		  System.out.println("\u001B[95mEnter department: \u001B[0m");
		  String department = sc.next();
		  searchEquipmentByDepartment(department);
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