package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.ApplicationProperties;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }
    public void saveGameState(Player player,GameState gameState){
        PlayerModel modelPlayer = new PlayerModel(player);
        modelPlayer.setId(1);
        GameState model = new GameState(gameState.getCurrentMap(),gameState.getSavedAt(),modelPlayer);
        gameStateDao.add(model);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        String dbName = "test";
//        String user = "test";
//        String password = "test";
        ApplicationProperties properties = new ApplicationProperties();
        dataSource.setDatabaseName(properties.readProperty("database"));
        dataSource.setUser(properties.readProperty("user"));
        dataSource.setPassword(properties.readProperty("password"));

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void saveGame( Player player,GameState state) {
        savePlayer(player);
        saveGameState(player,state);
    }
}
