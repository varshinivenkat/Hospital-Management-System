package Inventory.Items;

abstract class InventoryItem {
	int id;
	String name;
	double cost;
	String dept;

	public InventoryItem(int id, String name, double cost, String dept) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.dept = dept;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public double getCost() {
		return this.cost;
	}

	public String getDept() {
		return this.dept;
	}

	public abstract void displayDetails();

}