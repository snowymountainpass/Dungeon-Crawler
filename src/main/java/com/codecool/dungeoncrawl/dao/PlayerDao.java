package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);
    void update(PlayerModel player) throws SQLException;
    PlayerModel get(int id);
    PlayerModel get(String name);
    ArrayList<String> getAll();
}
