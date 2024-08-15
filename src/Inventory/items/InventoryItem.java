package Inventory.items;

public class InventoryItem extends Items {
private String itemId;
private int quantity;
private String name;

    public InventoryItem(double price, String category, boolean breakable, boolean perishable, String itemId, int quantity , String name) {
        super(price, category, perishable);
        this.itemId = itemId;
        this.quantity = quantity;
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public void handleBreakable() {
        System.out.println("Handling breakage of inventory item: " + itemId);
    }

    @Override
    public String getItemDetails() {
        return "Inventory.Item ID " + itemId + ",Quantity " + quantity + ", Name " + name + ", Category " + getItemCategory();
    }

    @Override
    public double calculateValue() {
        return getPrice() * quantity;
    }


    @Override
    public void handleExpiration() {
        System.out.println("Handle expiration of perishable items in the inventory: " + itemId);
    }

    @Override
    public void displayDescription() {
        System.out.println("Inventory item description: " + getItemDetails());
    }
}
