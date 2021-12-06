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

    @Override
    public String toString() {
        String inventoryAsString="";
        if (inventoryList != null) {
            for (int i = 0; i < inventoryList.size(); i++) {
                inventoryAsString = inventoryAsString + inventoryList.get(i).getTileName()+"\n";
            }
            return inventoryAsString;
        }
        return "Empty";
    }

    public void addItem(Item item){
        inventoryList.add(item);
    }
}