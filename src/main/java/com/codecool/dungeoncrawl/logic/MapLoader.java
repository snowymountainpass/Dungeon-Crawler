package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int level) {

//        InputStream is = MapLoader.class.getResourceAsStream("/map1.txt");
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

                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '$':
                            cell.setType(CellType.SECRET_WALL);
                            break;
                        case ';':
                            cell.setType(CellType.VINE);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case ',':
                            cell.setType(CellType.FLOOR_FANCY);
                            break;
                        case 'w':
                            cell.setType(CellType.WATER);
                            break;
                        case 'W':
                            cell.setType(CellType.WATERFALL);
                            break;
                        case 't':
                            cell.setType(CellType.TORCH);
                            break;
                        case 'r':
                            cell.setType(CellType.RUBBLE);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (level == 1) {
                                map.setPlayer(new Player(cell));

                            }
                            break;
                        case '{':
                            cell.setType(CellType.FLOOR);
                            new Passive(cell);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Skeleton(cell));
                            break;

                        case 'j':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Juggernaut(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Scarab(cell));
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Phantom(cell));
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            Sword sword = new Sword(cell);
                            map.addItem(sword);
                            break;
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case 'H':
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                            break;

                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new StrengthPotion(cell);
                            break;

                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
