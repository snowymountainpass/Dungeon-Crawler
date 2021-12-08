package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;


public class Main extends Application {
    int currentLevel = 1;

    private boolean gameLoaded = false;

    GridPane ui = new GridPane();


    GameMap map = MapLoader.loadMap(currentLevel);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label armorLabel = new Label();
    Label keyLabel = new Label();
    Label inventoryLabel = new Label();
    GameDatabaseManager dbManager;
    ModalHandler modal = new ModalHandler();
    Player player = map.getPlayer();

    Label currentPlayer = new Label("Player");

    Button saveButton = new Button("Save Game");
    Button loadButton = new Button("Load Game");
    Button closeButton = new Button("Close");

    Button enterNameButton = new Button("Enter");
    Label nameInputLabel = new Label("Enter your name: ");
    TextField playerNameField = new TextField();


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
                System.out.println("SAVE BUTTON CLICKED");
                modal.saveGameModal(dbManager, player);
            }
        });
        saveButton.setFocusTraversable(false);

        // TO BE IMPLEMENTED : SET FLAG LOADGAME TRUE
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("LOAD BUTTON CLICKED");
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
                    if (playerNameField.getText().equalsIgnoreCase("meow")) {
                        map.getPlayer().setHealth(1000);
                        map.getPlayer().setStrength(1000);
                    }
                    // "classes" ?
                    if (playerNameField.getText().contains("hero".toLowerCase(Locale.ROOT))) {
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


//    public void loadNextLevel(int level) {
//        this.map = MapLoader.loadMap(level);
//        this.move = new Move(this.map);
//        refresh();
//    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP, W:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(0, -1).getType() == CellType.DOOR) {

                    currentLevel++;
                    this.map = MapLoader.loadMap(currentLevel);
                    refresh();
                    break;

                }
                map.getPlayer().move(0, -1);
                enemyMove();
                refresh();

                break;
            case DOWN, S:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(0, 1).getType() == CellType.DOOR) {

                    currentLevel++;
                    this.map = MapLoader.loadMap(currentLevel);
                    refresh();
                    break;

                }

                map.getPlayer().move(0, 1);
                enemyMove();
                refresh();
                break;
            case LEFT, A:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(-1, 0).getType() == CellType.DOOR) {

                    currentLevel++;
                    this.map = MapLoader.loadMap(currentLevel);
                    refresh();
                    break;

                }

                map.getPlayer().move(-1, 0);
                enemyMove();
                refresh();
                break;
            case RIGHT, D:

                if (player.getInventory().getKeyInInventory() && map.getCell(player.getX(), player.getY()).getNeighbor(1, 0).getType() == CellType.DOOR) {

                    currentLevel++;
                    this.map = MapLoader.loadMap(currentLevel);
                    refresh();
                    break;

                }

                map.getPlayer().move(1, 0);
                enemyMove();
                refresh();
                break;
//            case S:
//                if (keyEvent.isControlDown()) {
//                    modal.saveGameModal(dbManager, player);
//                }
//                break;
        }
    }



    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
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
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    Cell cell = map.getCell(x, y);
                    if (cell.getActor() != null) {

                        Tiles.drawTile(context, cell.getActor(), x, y);

                    } else if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), x, y);
                    } else {
                        Tiles.drawTile(context, cell, x, y);
                    }
                }
            }
            healthLabel.setText("" + map.getPlayer().getHealth());
            strengthLabel.setText("" + map.getPlayer().getStrength());
            armorLabel.setText("" + map.getPlayer().getArmor());
            keyLabel.setText("" + map.getPlayer().getInventory().getKeyInInventory());
            inventoryLabel.setText("" + map.getPlayer().showInventory());
        }
    }
}


