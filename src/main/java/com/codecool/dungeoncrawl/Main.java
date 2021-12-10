package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends Application {
    int currentLevel = 1;

    private final int mapWidth = 30;
    private final int mapHeight = 25;

    private boolean gameLoaded = false;

    GridPane ui = new GridPane();


    GameMap map = MapLoader.loadMap(currentLevel);
    Canvas canvas = new Canvas(
            mapWidth * Tiles.TILE_WIDTH,
            mapHeight * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label armorLabel = new Label();
    Label keyLabel = new Label();
    Label inventoryLabel = new Label();

    GameDatabaseManager dbManager = new GameDatabaseManager();

    ModalHandler modal = new ModalHandler();

    Player player = map.getPlayer();

    Label currentPlayer = new Label("Player");

    Button saveButton = new Button("Save Game");
    Button loadButton = new Button("Load Game");
    Button closeButton = new Button("Close");

    Button enterNameButton = new Button("Enter");
    Label nameInputLabel = new Label("Enter your name: ");
    TextField playerNameField = new TextField();
    GameMap map1 = MapLoader.loadMap(1);
    GameMap map2 = MapLoader.loadMap(2);

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();

        ui.setPrefWidth(350);
        ui.setPadding(new Insets(10));

        ui.add(currentPlayer, 0, 0);

        ui.add(new Label("Health: "), 1, 1);
        ui.add(healthLabel, 2, 1);

        ui.add(new Label("Strength: "), 1, 2);
        ui.add(strengthLabel, 2, 2);

        ui.add(new Label("Armor: "), 1, 3);
        ui.add(armorLabel, 2, 3);

        ui.add(new Label("Key in inventory "), 1, 4);
        ui.add(keyLabel, 2, 4);

        ui.add(new Label("Inventory: "), 1, 5);
        ui.add(inventoryLabel, 2, 5);

        ui.add(new Label(""), 1, 6);
        ui.add(saveButton, 1, 7);

        ui.add(new Label(""), 1, 8);
        ui.add(loadButton, 1, 9);


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String currentMap = getCurrentMapAsString();
                String otherMap = getOtherMapAsString();
//                modal.saveGameModal(dbManager, currentMap, otherMap, player);
                saveGameModal(dbManager, currentMap, otherMap, player);
            }
        });
        saveButton.setFocusTraversable(false);

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ArrayList<String> savedGames = new ArrayList<>();
                savedGames = dbManager.getPlayerNames();
               loadGameModal(dbManager, savedGames);
            }
        });
        loadButton.setFocusTraversable(false);

        if (!gameLoaded) {
            ui.add(new Label(""), 0, 15);
            ui.add(nameInputLabel, 0, 17);
            playerNameField.setPrefWidth(150);
            ui.add(playerNameField, 1, 16);
            ui.add(enterNameButton, 1, 18);
            ui.add(closeButton, 2, 18);

            enterNameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    map.getPlayer().setName(playerNameField.getText());
                    currentPlayer.setText(playerNameField.getText());
                    ui.getChildren().remove(nameInputLabel);
                    ui.getChildren().remove(playerNameField);
                    ui.getChildren().remove(enterNameButton);
                    ui.getChildren().remove(closeButton);
                    if (playerNameField.getText().toLowerCase(Locale.ROOT).contains("meow".toLowerCase(Locale.ROOT))) {
                        map.getPlayer().setHealth(1000);
                        map.getPlayer().setStrength(1000);
                    }
                    if (playerNameField.getText().toLowerCase(Locale.ROOT).contains("hero".toLowerCase(Locale.ROOT))) {
                        map.getPlayer().setHealth(250);
                        map.getPlayer().setStrength(25);
                    }
                    gameLoaded = true;
                    canvas.requestFocus();
                }
            });
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    removeNameLabel();
                    canvas.requestFocus();
                }
            });

        }

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        if (map.getPlayer().isDead()) {
            borderPane.setCenter(new Text("You have died !"));
        }
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        canvas.requestFocus();
    }

    private void removeNameLabel() {
        ui.getChildren().remove(nameInputLabel);
        ui.getChildren().remove(playerNameField);
        ui.getChildren().remove(enterNameButton);
        ui.getChildren().remove(closeButton);
    }


    private void enemyMove() {
        for (Actor actor : map.getEnemies()) {
            actor.move();
        }
    }


    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        int passHealth = map.getPlayer().getHealth();
        int passArmor = map.getPlayer().getArmor();
        int passStrength = map.getPlayer().getStrength();
        Inventory passInventory = map.getPlayer().getInventory();


        switch (keyEvent.getCode()) {
            case UP, W:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(0, -1).getType() == CellType.DOOR) {

                    passDoor(passHealth, passArmor, passStrength, passInventory);
                    refresh();
                    break;

                }
                refresh();
                map.getPlayer().move(0, -1);
                enemyMove();
                refresh();

                break;
            case DOWN, S:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(0, 1).getType() == CellType.DOOR) {

                    passDoor(passHealth, passArmor, passStrength, passInventory);
                    refresh();
                    break;

                }
                refresh();
                map.getPlayer().move(0, 1);
                enemyMove();
                refresh();
                break;
            case LEFT, A:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(-1, 0).getType() == CellType.DOOR) {

                    passDoor(passHealth, passArmor, passStrength, passInventory);
                    refresh();
                    break;

                }
                refresh();
                map.getPlayer().move(-1, 0);
                enemyMove();
                refresh();
                break;
            case RIGHT, D:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(1, 0).getType() == CellType.DOOR) {

                    passDoor(passHealth, passArmor, passStrength, passInventory);
                    refresh();
                    break;

                }
                refresh();
                map.getPlayer().move(1, 0);
                enemyMove();
                refresh();
                break;

        }
    }

    private void passDoor(int passHealth, int passArmor, int passStrength, Inventory passInventory) {
        currentLevel++;
        player.getInventory().setKeyInInventory(false);
        this.map = MapLoader.loadMap(currentLevel);
        player.setHealth(passHealth);
        player.setArmor(passArmor);
        player.setStrength(passStrength);
        player.setInventory(passInventory);
        player.setCell(map.getCell(player.getX(), player.getY()));
        map.setPlayer(player);
        refresh();
        return;
    }


    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private String getCurrentMapAsString() {
        return MapSaver.saveMap(map);
    }

    private String getOtherMapAsString() {
        return (currentLevel == 1) ? MapSaver.saveMap(map2) : MapSaver.saveMap(map1);
    }

    private void refresh() {

        if (map.getPlayer().isDead()) {
            System.out.println("Player has died");
            map.getPlayer().getCell().setType(CellType.FLOOR);
            map.getPlayer().getCell().setActor(null);
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            context.fillText("You have died !", 250, 250);

        } else {
            context.setFill(Color.BLACK);
            int shiftX = 0;
            int shiftY = 0;

            if (map.getWidth() > 10) {
                if (map.getPlayer().getX() >= 10) {
                    shiftX = map.getPlayer().getX() - 10;
                }
                if (map.getPlayer().getY() >= 15) {
                    shiftY = map.getPlayer().getY() - 15;
                }

                if (map.getPlayer().getX() >= map.getWidth() - 5) {
                    shiftX = map.getWidth() - 20;
                }
                if (map.getPlayer().getY() >= map.getHeight() - 13) {
                    shiftY = map.getHeight() - 25;
                }
            }
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int x = 0; x < map.getWidth(); x++) {
                int relativeX = x - shiftX;
                for (int y = 0; y < map.getHeight(); y++) {
                    int relativeY = y - shiftY;
                    Cell cell = map.getCell(x, y);
                    if (cell.getActor() != null && !cell.getActor().isDead()) {

                        Tiles.drawTile(context, cell.getActor(), relativeX, relativeY);

                    } else if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), relativeX, relativeY);
                    } else {
                        Tiles.drawTile(context, cell, relativeX, relativeY);
                    }
                }
            }
            healthLabel.setText("" + map.getPlayer().getHealth());
            strengthLabel.setText("" + map.getPlayer().getStrength());
            armorLabel.setText("" + map.getPlayer().getArmor());
            keyLabel.setText("" + map.getPlayer().getInventory().getKeyInInventory());
            inventoryLabel.setText("" + map.getPlayer().showInventory());
            currentPlayer.setText(""+map.getPlayer().getName());
        }
    }

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
                dbManager.saveGame(currentMap, otherMap, new Date(System.currentTimeMillis()), saveName, player);
                saveStage.close();
            }
        });
        cancel.setOnAction(event -> saveStage.close());
    }

    public void loadGameModal(GameDatabaseManager dbManager, ArrayList<String> savedGames) {
        VBox loadGamesLayout = new VBox();
        Scene loadScene = new Scene(loadGamesLayout, 350, 350);
        Stage loadStage = new Stage();

        if (savedGames != null) {

            for (int i = 0; i < savedGames.size(); i++) {
                Button save = new Button(savedGames.get(i));
                loadGamesLayout.getChildren().add(i, save);
                save.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PlayerModel loadedPlayer;
                        String selectedSave = save.getText();
                        loadedPlayer = dbManager.loadPlayer(selectedSave);
                        player.setHealth(loadedPlayer.getHp());
                        player.setX(loadedPlayer.getX());
                        player.setY(loadedPlayer.getY());
                        player.setStrength(loadedPlayer.getStrength());
                        player.setArmor(loadedPlayer.getArmor());
                        player.setName(save.getText());
                        gameLoaded = true;
//                    refresh();
                        loadStage.close();

                    }
                });

            }
        } else System.out.println("No saves found");
        loadStage.setTitle("Load Game");
        loadStage.setScene(loadScene);
        loadStage.show();
    }
}


