package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Inventory;
import com.codecool.dungeoncrawl.logic.items.HealthPotion;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.StrengthPotion;

import java.util.ArrayList;


public class Player extends Actor {

    private String name;
    private Inventory inventory;
    private ArrayList<Item> listOfPotions;
    private ArrayList<Item> listOfTools;
    private ArrayList<Item> listOfEquippedItems = new ArrayList<>();


    public Player(Cell cell) {

        super(cell);
        this.inventory = new Inventory();
        this.setHealth(100);
        this.setStrength(10);
        this.name="Test";
        this.listOfPotions = new ArrayList<>();
        this.listOfTools = new ArrayList<>();
    }

    public Inventory getInventory() {
        return inventory;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if ((nextCell.getType()== CellType.FLOOR || nextCell.getType()== CellType.FLOOR_FANCY || nextCell.getType() == CellType.SECRET_WALL) && nextCell.getActor() == null) {
            if (nextCell.getItem()!=null) {
                switch (nextCell.getItem().getTileName()){
                    case "key":
                        inventory.addItem(nextCell.getItem());
                        inventory.setKeyInInventory(true);
                        nextCell.setItem(null);
                        break;
                    case "sword":
                        Actor player = getCell().getActor();
                        Item sword = nextCell.getItem();
                        inventory.addItem(nextCell.getItem());
                        player.setStrength(player.getStrength()+sword.getPoints());
                        nextCell.setItem(null);
                        break;
                    case "shield":
                        inventory.addItem(nextCell.getItem());
                        getCell().getActor().increaseArmor(nextCell.getItem().getPoints());
                        nextCell.setItem(null);
                        break;

                    case "healthpotion":
                        HealthPotion.increaseHealth(this);
                        nextCell.setItem(null);
                        break;

                    case "strengthpotion":
                        StrengthPotion.increaseStrength(this);
                        nextCell.setItem(null);
                        break;
                }
            }
            getCell().setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        } else if ((nextCell.getType()== CellType.FLOOR || nextCell.getType()== CellType.FLOOR_FANCY || nextCell.getType() == CellType.SECRET_WALL) && nextCell.getActor() != null){
            Actor target = nextCell.getActor();
            while(target.getHealth() > 0) {
                fight(dx, dy, getCell());
            }
            getCell().setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        } else if (nextCell.getType() == CellType.DOOR && inventory.getKeyInInventory()){
            System.out.println("Ascending to second level [WIP]");
        }

        this.getCell().setActor(this);

    }

    public String showInventory(){
        String inventoryContains = getInventory().toString();
        return inventoryContains;
    }



    public String getTileName() {

        return "player";
    }
}
