package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class StrengthPotion extends Item {

    public StrengthPotion(Cell cell) {
        super(cell);
    }

    public static void increaseStrength(Player player){
        player.setStrength(player.getStrength()+25);
    }

    @Override
    public String getTileName() {
        return "strengthpotion";
    }
}
