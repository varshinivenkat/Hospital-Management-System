package Inventory.Items;

import java.time.*;

public class Equipment extends InventoryItem {
  int stock;

  public Equipment(int id, String name, double cost, int stock, String dept) {
    super(id, name, cost, dept);
    this.stock = stock;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public void displayDetails() {
    System.out.println("---------------------------------------------------------------");
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "ID", this.id);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Equipment", this.name);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Cost", this.cost);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Department", this.dept);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Stock", this.stock);
    System.out.println("---------------------------------------------------------------");
  }

}