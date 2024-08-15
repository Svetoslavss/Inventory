package Inventory;
import Inventory.category.Categorizable;
import Inventory.items.InventoryItem;
import Inventory.order.Order;
import Inventory.order.OrderItem;
import Payment.Payments;

import java.io.*;
import java.util.*;

public class InventoryCLI {
    private final List<InventoryItem> inventory;
    private final List<Order> orders;
    private final Scanner sc;

    public InventoryCLI() {
        this.inventory = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    public void start(){
        boolean exit = false;
        while (!exit){
            displayMenu();

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    try {
                        addItem();
                    } catch (IOException e){
                        System.out.println("Error adding item: " + e.getMessage());
                    }
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    displayItems();
                    break;
                case 4:
                    categorizeItems();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    saveInventoryToFile();
                    break;
                case 7:
                    loadInventoryFromFile();
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nInventory Management System");
        System.out.println("1. Add item");
        System.out.println("2. Remove Item by ID");
        System.out.println("3. Display Items");
        System.out.println("4. Categorized Items");
        System.out.println("5. Place Order");
        System.out.println("6. Save Inventory to File");
        System.out.println("7. Load Inventory from File");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    public void addItem() throws IOException {
        System.out.print("Enter item ID: ");
        String itemID = sc.nextLine();
        System.out.print("Enter item category: ");
        String category = sc.nextLine();
        System.out.print("Enter item price: ");
        double price = sc.nextDouble();
        System.out.print("Enter item quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Is the item breakable(true/false) ");
        boolean breakable = sc.nextBoolean();
        System.out.print("Is the item perishable(true/false) ");
        boolean perishable = sc.nextBoolean();
        System.out.print("Item brand: ");
        String brand = sc.next();
        sc.nextLine();
        System.out.print("Item name: ");
        String name = sc.nextLine();
        ElectronicsItem newItem = new ElectronicsItem(price, category, breakable, perishable, itemID, quantity, brand , name);
        inventory.add(newItem);
        System.out.println("Items added successfully");
    }

    public void removeItem(){
        System.out.print("Enter item ID to remove: ");
        String itemID = sc.nextLine();
        InventoryItem removeItem = findItemById(itemID);
        if(removeItem != null){
            inventory.remove(removeItem);
            System.out.println("Item removed successfully");
        } else {
            System.out.println("Item with ID: " + itemID + " not found.");
        }
    }

    public void displayItems(){
        System.out.println("\nInventory items:");
        for (InventoryItem item : inventory){
            System.out.println(item.getItemDetails());
        }
    }

    public void categorizeItems(){
        System.out.print("Enter item ID to categorize: ");
        String itemID = sc.nextLine();
        InventoryItem item = findItemById(itemID);

        if (item != null && item instanceof Categorizable){
            System.out.print("Enter new category: ");
            String category = sc.nextLine();
            ((Categorizable) item).setItemCategory(category);
            System.out.println("Item categorized successfully.");
        } else {
            System.out.println("Item with ID " + itemID + " not found.");
        }
    }

    private void placeOrder(){
        System.out.println("\nPlacing order: ");
        Order order = new Order(new Date());

        String continueAdding;

        do {
            System.out.print("Enter ID to add to order: ");
            String itemID = sc.nextLine();
            InventoryItem addItem = findItemById(itemID);

            if (addItem != null){
                System.out.print("Enter quantity for item " + addItem.getName() + " : ");
                int quantity = sc.nextInt();
                sc.nextLine();

                if (addItem.getQuantity() >= quantity){
                    OrderItem orderItem = new OrderItem(addItem , quantity);
                    order.addOrderItems(orderItem);
                    System.out.println("Item added to order list successfully.");

                    addItem.setQuantity(addItem.getQuantity() - quantity);
                } else {
                    System.out.println("Insufficient quantity available for item " + addItem.getName() + ".");
                }
            } else {
                System.out.println("Item with ID " + itemID + " not found");
            }
            System.out.print("Do you want to add another item to the order ? (yes/no): ");
            continueAdding = sc.nextLine().trim().toLowerCase();
        }while (continueAdding.equals("yes"));

        float totalCost = (float) order.calculateTotal();

        System.out.println("\n Order Summary");
        System.out.println(order.getOrderDetails());
        System.out.println("Total Order Cost: $" + totalCost);

        System.out.print("Enter payment amount: $");
        double paymentAmount = sc.nextDouble();
        Payments payments = new Payments(paymentAmount);

        if (payments.validatePayment(totalCost)){
            double change = paymentAmount - totalCost;

            System.out.println("Payment successful. Change: $" + change + ", payment information " + payments.getPaymentDetails());

            order.setPayment(payments);

            orders.add(order);
        } else {
            System.out.println("Insufficient payment amount");
        }

        for (OrderItem items : order.getOrderItems()){
            InventoryItem itemToUpdate = items.getItem();
            itemToUpdate.setQuantity(itemToUpdate.getQuantity() + items.getQuantity());
        }
    }

    private InventoryItem findItemById(String itemID) {
    for (InventoryItem item : inventory){
        if (item.getItemId().equals(itemID)){
            return item;
        }
    }
    return null;
    }

    private void setInventoryToFile(){
        try (FileWriter writer = new FileWriter("inventory.txt")){
    for (InventoryItem item : inventory){
        writer.write(item.getItemDetails());
    }
            System.out.println("Inventory saved int a .txt File");
        } catch (IOException e){
            System.out.println("Error while trying to save the inventory in a File" + e.getMessage());
        }
    }

    private void loadInventoryFromFile(){
        try(BufferedReader br = new BufferedReader(new FileReader("inventory.txt"))) {
            inventory.clear();
            String line;

            while ((line = br.readLine()) != null){
        InventoryItem item = parseItem(line);
        inventory.add(item);
            }
        } catch (IOException e){
            System.out.println("Error while trying to read the inventory.txt file. " + e.getMessage());
        }
    }

    private InventoryItem parseItem(String line){
        String[] parts = line.split(",");
        String itemID = parts[0];
        String name = parts[1];
        String category = parts[2];
        double price = Double.parseDouble(parts[3]);
        int quantity = Integer.parseInt(parts[4]);
        boolean breakable = Boolean.parseBoolean(parts[5]);
        boolean perishable = Boolean.parseBoolean(parts[6]);
        String brand = parts[7];

        return new ElectronicsItem(price, category, breakable, perishable, itemID, quantity, brand, name);
    }

    private void saveInventoryToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("inventory.txt"))){
            for (InventoryItem item : inventory){
                writer.println(serializeItem(item));
            }
            System.out.println("Inventory saved to file successfully.");
        } catch (IOException e){
            System.out.println("Error while saving inventory to file " + e.getMessage());
        }
    }

    private String serializeItem(InventoryItem item) {
        // Serialize InventoryItem to a string
        // Format: itemID,name,category,price,quantity,breakable,perishable,brand
        return String.format("%s,%s,%s,%.2f,%d,%b,%b,%s",
                item.getItemId(), item.getName(), item.getItemCategory(), item.getPrice(), item.getQuantity(),
                item.isBreakable(), item.isPerishable(), ((ElectronicsItem) item).getBrand());
    }

    public static void main(String[] args){
        InventoryCLI cli = new InventoryCLI();
        cli.start();
    }
}
