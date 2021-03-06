package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> inventoryList;
    Boolean keyInInventory=false;
    public Inventory(List<Item> inventoryList) {
        this.inventoryList = new ArrayList<>();
        Boolean keyInInventory = false;
    }

    public Inventory() {
        inventoryList = new ArrayList<>();
    }

    public void setKeyInInventory(Boolean keyInInventory) {
        this.keyInInventory = keyInInventory;
    }

    public Boolean getKeyInInventory() {
        return keyInInventory;
    }

    public void setKey(boolean value) {
        this.keyInInventory = value;
    }

    @Override
    public String toString() {
        StringBuilder inventoryAsString= new StringBuilder();
        if (inventoryList != null) {
            for (Item item : inventoryList) {
                inventoryAsString.append(item.getTileName()).append("\n");
            }
            return inventoryAsString.toString();
        }
        return "Empty";
    }

    public void addItem(Item item){
        inventoryList.add(item);
    }
}