package com.codecool.dungeoncrawl.logic;


public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    FLOOR_FANCY("floor_fancy"),
    WALL("wall"),
    SECRET_WALL("secret_wall"),
    VINE("vine"),
    TORCH("torch"),
    RUBBLE("rubble"),
    DOOR("door");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
