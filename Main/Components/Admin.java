package Main.Components;

import Inventory.*;
import Users.*;
import java.util.Scanner;

public class Admin {
	private InventoryManager inventoryManager;
	private UserManager userManager;

	public Admin() {
		this.inventoryManager = new InventoryManager();
		this.userManager = new UserManager();
	}

  public void adminOperations() {
      Scanner scanner = new Scanner(System.in);
      int choice;

      do {
          System.out.println("\u001B[95mAdmin Operations");
          System.out.println("1. Inventory Management System");
          System.out.println("2. User Management System");
          System.out.println("3. Exit\u001B[0m");

          System.out.print("\u001B[93mEnter choice: \u001B[0m");
          choice = scanner.nextInt();
          scanner.nextLine();

          switch (choice) {
              case 1:
                  inventoryManager.inventoryOperations();
                  break;
              case 2:
                  userManager.userManagementSystem();
                  break;
              case 3:
                  System.out.println("\u001B[92mExiting Admin Operations.\u001B[0m");
                  break;
              default:
                  System.out.println("\u001B[91mInvalid choice. Please enter a valid option.\u001B[0m");
                  break;
          }

      } while (choice != 3);
  }


	public static void main(String[] args) {
		Admin admin = new Admin();
		admin.adminOperations();
	}
}
