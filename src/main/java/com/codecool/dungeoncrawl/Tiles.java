package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("secret_wall", new Tile(10, 17));
        tileMap.put("vine", new Tile(2, 2));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("floor_fancy", new Tile(7, 0));
        tileMap.put("water", new Tile(10, 4));
        tileMap.put("waterfall", new Tile(11, 4));
        tileMap.put("torch", new Tile(4, 15));
        tileMap.put("rubble", new Tile(19,1));
        tileMap.put("door", new Tile(3, 4));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("juggernaut", new Tile(27, 3));
        tileMap.put("scarab", new Tile(30, 5));
        tileMap.put("phantom", new Tile(26, 6));
        tileMap.put("passive_target", new Tile(5, 7));
        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("shield", new Tile(7, 26));
        tileMap.put("key", new Tile (16, 23));
        tileMap.put("healthpotion",new Tile(16,30));
        tileMap.put("strengthpotion",new Tile(26,23));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
