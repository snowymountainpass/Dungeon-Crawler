package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.concurrent.ThreadLocalRandom;

public class Skeleton extends Actor {

    private int burrow = 5;

    public Skeleton(Cell cell) {

        super(cell);
        this.setHealth(25);
        this.setStrength(5);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void move(){
        Cell nextCell;
        try {
            int x = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
            int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            if (burrow > 0) {
                if (x == 0) {
                    if (randomNum == 0) {
                        move(x, -1);
                    } else {
                        move(x, 1);
                    }
                } else {
                    move(x, 0);

                }

                burrow--;
            } else {
                if (x == 0) {
                    if (randomNum == 0) {
                        nextCell = cell.getNeighbor(x, -3);
                    } else {
                        nextCell = cell.getNeighbor(x, 3);
                    }
                } else {
                    nextCell = cell.getNeighbor(-3, 0);

                }
                if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.EMPTY) && nextCell.getActor() == null
                        && !nextCell.getType().equals(CellType.DOOR)
                        && !nextCell.getType().equals(CellType.TORCH)
                        && !nextCell.getType().equals(CellType.RUBBLE)
                        && !nextCell.getType().equals(CellType.SECRET_WALL)) {
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                } else {
                    cell.setActor(this);
                }
                burrow = 5;
            }
        } catch (Exception e) {
            burrow = 5;
        }

    }

    public void setBurrow(int b) {
        burrow = b;
    }

}
