package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

public class MapSaver {
    public static String saveMap(GameMap map) {
        StringBuilder sb = new StringBuilder();
        int width = map.getWidth();
        int height = map.getHeight();
        sb.append(width).append(" ").append(height).append("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String currentCell = map.getCell(x, y).getTileName();
                switch (currentCell) {
                    case "empty":
                        sb.append(' ');
                        break;
                    case "wall":
                        sb.append('#');

                        break;
                    case "secret_wall":
                        sb.append('$');

                        break;
                    case "vine":
                        sb.append(';');

                        break;
                    case "floor":
                        sb.append('.');

                        break;
                    case "floor_fancy":
                        sb.append(',');

                        break;
                    case "torch":
                        sb.append('t');

                        break;
                    case "rubble":
                        sb.append('r');

                        break;
                    case "door":
                        sb.append('d');
                        break;
                    case "player":
                        sb.append('@');
                        break;
                    case "passive_target":
                        sb.append('{');
                        break;
                    case "skeleton":
                        sb.append('s');
                        break;

                    case "juggernaut":
                        sb.append('j');
                        break;
                    case "scarab":
                        sb.append('c');
                        break;
                    case "phantom":
                        sb.append('p');
                        break;
                    case "sword":
                        sb.append('S');
                        break;
                    case "key":
                        sb.append('K');
                        break;
                    case "shield":
                        sb.append('A');
                        break;
                    case "healthpotion":
                        sb.append('H');
                        break;

                    case "strengthpotion":
                        sb.append('h');
                        break;
                }
            }
            if (y != height - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
