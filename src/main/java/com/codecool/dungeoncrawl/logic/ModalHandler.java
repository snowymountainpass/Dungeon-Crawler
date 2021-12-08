package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import com.codecool.dungeoncrawl.logic.actors.Player;

import com.codecool.dungeoncrawl.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;


public class ModalHandler {
    private int gameId;

    public void saveGameModal(GameDatabaseManager dbManager, Player player, GameState state) {
        TextField nameInput = new TextField("Name");
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        VBox layout = new VBox(2);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(nameInput, save, cancel);
        Scene saveScene = new Scene(layout, 350, 150);
        Stage saveStage = new Stage();
        saveStage.setTitle("Save game state");
        saveStage.setScene(saveScene);
        saveStage.show();
        String saveName = nameInput.getText();
        save.setOnAction(event -> {
            dbManager.saveGame(player,state);
            saveStage.close();
        });
        cancel.setOnAction(event -> saveStage.close());
    }
}

