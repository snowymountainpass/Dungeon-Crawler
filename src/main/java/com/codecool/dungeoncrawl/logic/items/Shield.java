package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item {
    public Shield(Cell cell) {
        super(cell);
        super.points = 4;
    }

    @Override
    public String getTileName() {
        return "shield";
    }

}