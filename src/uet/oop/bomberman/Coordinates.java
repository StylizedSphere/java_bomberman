package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Sprite;

public class Coordinates {
    public static int pixelToTile(int pixel) {
        return pixel * Sprite.SCALED_SIZE;
    }

    public static int tileToPixel(int tile) {
        return tile / Sprite.SCALED_SIZE;
    }
}
