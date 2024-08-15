package Inventory.category;

public interface Perishable {

    boolean isPerishable();
    void handleExpiration();
}
