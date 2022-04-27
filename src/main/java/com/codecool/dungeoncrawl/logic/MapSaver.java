package com.codecool.dungeoncrawl.logic;

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
                    case "empty" -> sb.append(' ');
                    case "wall" -> sb.append('#');
                    case "secret_wall" -> sb.append('$');
                    case "vine" -> sb.append(';');
                    case "floor" -> sb.append('.');
                    case "floor_fancy" -> sb.append(',');
                    case "torch" -> sb.append('t');
                    case "rubble" -> sb.append('r');
                    case "door" -> sb.append('d');
                    case "player" -> sb.append('@');
                    case "passive_target" -> sb.append('{');
                    case "skeleton" -> sb.append('s');
                    case "juggernaut" -> sb.append('j');
                    case "scarab" -> sb.append('c');
                    case "phantom" -> sb.append('p');
                    case "sword" -> sb.append('S');
                    case "key" -> sb.append('K');
                    case "shield" -> sb.append('A');
                    case "healthpotion" -> sb.append('H');
                    case "strengthpotion" -> sb.append('h');
                }
            }
            if (y != height - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
