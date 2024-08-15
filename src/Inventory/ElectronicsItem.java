package Inventory;

import Inventory.items.InventoryItem;

public class ElectronicsItem extends InventoryItem {
    private String brand;

    public ElectronicsItem(double price, String category, boolean breakable, boolean perishable, String itemId, int quantity, String brand , String name) {
        super(price, category, breakable, perishable, itemId, quantity , name);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
