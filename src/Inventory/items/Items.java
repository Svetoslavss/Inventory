package Inventory.items;

import Inventory.*;
import Inventory.category.Breakable;
import Inventory.category.Categorizable;
import Inventory.category.Perishable;
import Inventory.category.Sellable;

public abstract class Items implements Item, Categorizable, Breakable, Perishable, Sellable {
private String category;
protected boolean breakable;
private final boolean perishable;
private double price;

    public Items(double price, String category, boolean perishable) {
        this.price = price;
        this.category = category;
        this.breakable = perishable;
        this.perishable = perishable;
    }

    @Override
    public boolean isBreakable() {
        return breakable;
    }

    @Override
    public boolean isPerishable() {
        return perishable;
    }

    @Override
    public void setItemCategory(String category) {
        this.category = category;
    }

    @Override
    public String getItemCategory() {
        return category;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    //Abstract Methods to be implemented by subclass.

    @Override
    public abstract void handleBreakable();


    @Override
    public abstract String getItemDetails();

    @Override
    public abstract double calculateValue();

    @Override
    public abstract void handleExpiration();

    @Override
    public abstract void displayDescription();

}
