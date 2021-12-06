package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Phantom extends Actor {

    public Phantom(Cell cell) {

        super(cell);
        setHealth(5);
        setStrength(5);
        setArmor(0);

    }

    @Override
    public String getTileName() {
        return "phantom";
    }

    @Override
    public void move() {
        GameMap map = cell.getMap();
        Player player = map.getPlayer();
        if (!hasPlayerNeighbor()) {
            if (Math.abs(getX() - player.getX()) > Math.abs(getY() - player.getY())) {
                if (getX() > player.getX()) {
                    if (cell.getNeighbor(-1, 0).getActor() == null) {
                        move(-1, 0);
                    } else {
                        if (getY() > player.getY()) {
                            move(0, -1);
                        } else {
                            move(0, 1);
                        }
                    }
                } else {
                    if (cell.getNeighbor(1, 0).getActor() == null) {
                        move(1, 0);
                    } else {
                        if (getY() > player.getY()) {
                            move(0, -1);
                        } else {
                            move(0, 1);
                        }
                    }
                }

            } else {
                if (getY() > player.getY()) {
                    if (cell.getNeighbor(0, -1).getActor() == null) {
                        move(0, -1);
                    } else {
                        if (getX() > player.getX()) {
                            move(-1, 0);
                        } else {
                            move(1, 0);
                        }
                    }
                } else if (getY() < player.getY()) {
                    if (cell.getNeighbor(0, 1).getActor() == null) {
                        move(0, 1);
                    } else {
                        if (getX() > player.getX()) {
                            move(-1, 0);
                        } else {
                            move(1, 0);
                        }
                    }
                }
            }
        }
    }


    public boolean hasPlayerNeighbor() {
        return (cell.getNeighbor(1, 0) != null && cell.getNeighbor(1, 0).getActor() instanceof Player
                || cell.getNeighbor(0, 1) != null && cell.getNeighbor(0, 1).getActor() instanceof Player
                || cell.getNeighbor(-1, 0) != null && cell.getNeighbor(-1, 0).getActor() instanceof Player
                || cell.getNeighbor(0, -1) != null && cell.getNeighbor(0, -1).getActor() instanceof Player);
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }


}
