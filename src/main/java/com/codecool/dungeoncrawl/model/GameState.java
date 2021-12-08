package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private int gameId;
    private String currentMap;
    private String otherMap;
    private Date savedAt;
    private PlayerModel player;
    private String saveName;

    public GameState(String currentMap, String otherMap, Date savedAt, PlayerModel player, String saveName) {
        this.currentMap = currentMap;
        this.otherMap = otherMap;
        this.savedAt = savedAt;
        this.player = player;
        this.saveName = saveName;
    }




    public GameState(int gameId, String saveName) {
        this.gameId = gameId;
        this.saveName = saveName;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public String getOtherMap() {
        return otherMap;
    }

    public void setOtherMap(String otherMap) {
        this.otherMap = otherMap;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public String toString() {
        return "gameId: " + this.gameId + " saveName: " + this.saveName;
    }

    public int getGameId() {
        return gameId;
    }
}