package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    protected int points;

    private boolean inInventory;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
        this.inInventory=false;
    }

    public Cell getCell() {
        return cell;
    }

    public int getPoints() {
        return points;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

}