package Inventory.Items;

import java.time.*;

public class Test extends InventoryItem {

  public Test(int id, String name, double cost, String dept) {
    super(id, name, cost, dept);
  }

  public void displayDetails() {
    System.out.println("-------------------------------------------------");
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "ID", this.id);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Service", this.name);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Cost", this.cost);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Department", this.dept);
    System.out.println("-------------------------------------------------");
  }

}