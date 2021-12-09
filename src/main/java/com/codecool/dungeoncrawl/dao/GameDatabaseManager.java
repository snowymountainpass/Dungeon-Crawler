package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.ApplicationProperties;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private String GameName = "GameName";
    private PlayerModel playerModel;
    private DataSource dataSource;
    private GameState gameState;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public PlayerModel loadPlayer(String playerName) {
        PlayerModel player = playerDao.get(playerName);
        return player;
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        ApplicationProperties properties = new ApplicationProperties();
        dataSource.setDatabaseName(properties.readProperty("database"));
        dataSource.setUser(properties.readProperty("user"));
        dataSource.setPassword(properties.readProperty("password"));

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void saveGame(String currentMap, String otherMap, Date savedAt, String saveName, Player player) {
        savePlayer(player);
        PlayerModel model = new PlayerModel(player);
        GameState gameState = new GameState(currentMap, otherMap,  savedAt, model, saveName);
        gameStateDao.add(gameState);
    }

    public ArrayList<String> getPlayerNames() {
        return playerDao.getAll();
    }
}
