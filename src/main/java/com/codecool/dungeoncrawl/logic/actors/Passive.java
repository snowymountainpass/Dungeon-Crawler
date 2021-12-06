package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Passive extends Actor{

    public Passive(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "passive_target";
    }

}


