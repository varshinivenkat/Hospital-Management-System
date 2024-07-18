package Inventory.Items;
import java.time.*;

public class Medicine extends InventoryItem {
  private LocalDate mfd;
  private LocalDate expiry;
  private int stock;

  public Medicine(int id, String name, double cost, LocalDate mfd, LocalDate expiry, int stock, String dept) {
    super(id, name, cost, dept);
    this.mfd = mfd;
    this.expiry = expiry;
    this.stock = stock;
  }

  public LocalDate getMfd() {
    return this.mfd;
  }

  public LocalDate getExpiry() {
    return this.expiry;
  }

  public int getStock() {
    return this.stock;
  }

  public double getCost() {
    return this.cost;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public void displayDetails() {
    System.out.println("--------------------------------------------------");
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "ID", this.id);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Equipment", this.name);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Cost", this.cost);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Manufacture Date", this.mfd);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Expiry Date", this.expiry);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Department", this.dept);
    System.out.printf("\u001B[93m%-20s: \u001B[96m%s\u001B[0m%n", "Stock", this.stock);
    System.out.println("--------------------------------------------------");
  }

}
