package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    private Key key;

    private List<Actor> enemies = new ArrayList<>();

    private List<Item> items;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }

        this.items = new ArrayList<>();

    }

    public Cell getCell(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        } else {
            return cells[x][y];
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCell(int x, int y, CellType cellType) {
        cells[x][y] = new Cell(this, x, y, cellType);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addEnemy(Actor enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Actor enemy) {
        enemies.remove(enemy);
    }

    public List<Actor> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Actor> enemies) {
        this.enemies = enemies;

    }


}
