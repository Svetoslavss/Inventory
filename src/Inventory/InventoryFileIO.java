package Inventory;

import Inventory.items.InventoryItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryFileIO {
    private static final String FILENAME = "inventory.txt";

    public static void saveInventory(List<InventoryItem> inventoryItems){
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))){
            for (InventoryItem item : inventoryItems){
                writer.println(item.getItemId() + "," +
                        item.getQuantity() + "," +
                        item.getItemCategory() + "," +
                        item.isBreakable() + "," +
                        item.isPerishable() + "," +
                        item.getPrice());
            }
            System.out.println("Inventory data saved successfully.");
        } catch (IOException e){
            System.out.println("Error saving inventory data: " + e.getMessage());
        }
    }
    public static List<InventoryItem> loadInventory() {
        List<InventoryItem> inventory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String itemId = data[0];
                int quantity = Integer.parseInt(data[1]);
                String category = data[2];
                boolean breakable = Boolean.parseBoolean(data[3]);
                boolean perishable = Boolean.parseBoolean(data[4]);
                double price = Double.parseDouble(data[5]);
                String name = data[6];
                InventoryItem item = new InventoryItem(price, category, breakable, perishable, itemId, quantity, name);
                inventory.add(item);
            }
            System.out.println("Inventory data loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading inventory data: " + e.getMessage());
        }
        return inventory;
    }
}
