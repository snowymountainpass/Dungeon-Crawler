package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import com.codecool.dungeoncrawl.logic.actors.Player;

import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class ModalHandler {
    private int gameId;

    public void saveGameModal(GameDatabaseManager dbManager, String currentMap, String otherMap, Player player) {
        TextField nameInput = new TextField();
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


        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String saveName = nameInput.getText();
                System.out.println(saveName);
                dbManager.saveGame(currentMap, otherMap, new Date(System.currentTimeMillis()), saveName, player);
                saveStage.close();
            }
        });
        cancel.setOnAction(event -> saveStage.close());
    }

    public void loadGameModal(GameDatabaseManager dbManager, ArrayList<String> savedGames) {
        VBox loadGamesLayout = new VBox();
        AtomicReference<PlayerModel> returnValue = new AtomicReference<>();

        for (int i = 0; i < savedGames.size(); i++) {
            Button save = new Button(savedGames.get(i));
            loadGamesLayout.getChildren().add(i, save);
            save.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    PlayerModel loadedPlayer;
                    String selectedSave = save.getText();
                    System.out.println(selectedSave);
                    loadedPlayer = dbManager.loadPlayer(selectedSave);
                    System.out.println(loadedPlayer);
                    returnValue.set(loadedPlayer);
                }
            });

        }
        Scene loadScene = new Scene(loadGamesLayout, 350, 350);
        Stage loadStage = new Stage();
        loadStage.setTitle("Load Game");
        loadStage.setScene(loadScene);
        loadStage.show();
    }

}

