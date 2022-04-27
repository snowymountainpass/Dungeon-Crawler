package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int level) {

        InputStream is = MapLoader.class.getResourceAsStream("/map"+level+".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine();

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ' -> cell.setType(CellType.EMPTY);
                        case '#' -> cell.setType(CellType.WALL);
                        case '$' -> cell.setType(CellType.SECRET_WALL);
                        case ';' -> cell.setType(CellType.VINE);
                        case '.' -> cell.setType(CellType.FLOOR);
                        case ',' -> cell.setType(CellType.FLOOR_FANCY);
                        case 't' -> cell.setType(CellType.TORCH);
                        case 'r' -> cell.setType(CellType.RUBBLE);
                        case 'd' -> cell.setType(CellType.DOOR);
                        case '@' -> {
                            cell.setType(CellType.FLOOR);
                            if (level == 1) {
                                map.setPlayer(new Player(cell));
                            }
                        }
                        case '{' -> {
                            cell.setType(CellType.FLOOR);
                            new Passive(cell);
                        }
                        case 's' -> {
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Skeleton(cell));
                        }
                        case 'j' -> {
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Juggernaut(cell));
                        }
                        case 'c' -> {
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Scarab(cell));
                        }
                        case 'p' -> {
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Phantom(cell));
                        }
                        case 'S' -> {
                            cell.setType(CellType.FLOOR);
                            Sword sword = new Sword(cell);
                            map.addItem(sword);
                        }
                        case 'K' -> {
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                        }
                        case 'A' -> {
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                        }
                        case 'H' -> {
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                        }
                        case 'h' -> {
                            cell.setType(CellType.FLOOR);
                            new StrengthPotion(cell);
                        }
                        default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
