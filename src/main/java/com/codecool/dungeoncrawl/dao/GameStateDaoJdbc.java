package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }
    @Override
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {


            String sql = "INSERT INTO game_state (current_map, other_map, saved_at, save_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setString(2, state.getOtherMap());
            statement.setDate(3, state.getSavedAt());
            statement.setString(4, state.getSaveName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {

        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}


