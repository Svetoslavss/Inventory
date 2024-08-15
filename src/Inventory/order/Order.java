package Inventory.order;

import Inventory.items.InventoryItem;
import Payment.Payments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private List<OrderItem> orderItems;
    private Date orderDate;
    private Payments payment;

    public Order(Date orderDate){
    this.orderDate = orderDate;
    this.orderItems = new ArrayList<>();
}

    public void addOrderItems(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }

    public List<OrderItem> getOrderItems(){
        return orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Payments getPayment() {
        return payment;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public double calculateTotal(){
        double total = 0.0;
        for (OrderItem items : orderItems){
            total += items.calculateItemTotal();
        }
        return total;
    }

    public String getOrderDetails(){
        StringBuilder details = new StringBuilder();
        details.append("Order Date: ").append(orderDate).append("\n");
        details.append("Items:\n");
        for (OrderItem orderItem : orderItems) {
            details.append(orderItem.getItem().getName())
                    .append("  - Quantity: ").append(orderItem.getItem().getQuantity())
                    .append("  - Price: ").append(orderItem.getItem().getPrice())
                    .append("  - Total: ").append(orderItem.calculateItemTotal())
                    .append("\n");
        }
        return details.toString();
    }
}

