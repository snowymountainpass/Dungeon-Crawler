package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, strength, armor) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.setInt(5, player.getStrength());
            statement.setInt(6, player.getArmor());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE player SET player_name = ?, hp= ?, x = ?, y = ?, strength = ?, armor = ? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, player.getPlayerName());
            st.setInt(2, player.getHp());
            st.setInt(3, player.getX());
            st.setInt(4, player.getY());
            st.setInt(5, player.getStrength());
            st.setInt(6, player.getArmor());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PlayerModel get(int id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT player_name, hp, x, y, strength, armor FROM player WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }

            PlayerModel player = new PlayerModel(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
            player.setId(id);
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(String name) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT  hp, x, y, strength, armor FROM player WHERE player_name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }

            PlayerModel player = new PlayerModel(rs.getInt("hp"), rs.getInt("x"), rs.getInt("y"), rs.getInt("strength"), rs.getInt("armor"));
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT player_name FROM player";
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            ArrayList<String> playerNames = new ArrayList<String>();


            String firstEntry = resultSet.getString("player_name");
            playerNames.add(firstEntry);


            while (resultSet.next()) {
                String name = new String(resultSet.getString("player_name"));
                playerNames.add(name);
            }
            return playerNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
