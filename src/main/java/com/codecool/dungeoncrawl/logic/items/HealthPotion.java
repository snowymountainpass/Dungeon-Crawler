package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class HealthPotion extends Item {

    public HealthPotion(Cell cell) {
        super(cell);
    }

    public static void increaseHealth(Player player){
        player.setHealth(player.getHealth()+25);
    }

    @Override
    public String getTileName() {
        return "healthpotion";
    }
}
