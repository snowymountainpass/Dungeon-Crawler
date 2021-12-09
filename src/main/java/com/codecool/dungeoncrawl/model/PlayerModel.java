package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.UUID;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private int strength;
    private int armor;


    public PlayerModel() {
        super();

    }


    public PlayerModel(String playerName, int hp, int x, int y, int strength, int armor) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.strength = strength;
        this.armor = armor;

    }
    public PlayerModel(int hp, int x, int y, int strength, int armor) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.strength = strength;
        this.armor = armor;

    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();

        this.hp = player.getHealth();
        this.strength = player.getStrength();
        this.armor = player.getArmor();

    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }


}
