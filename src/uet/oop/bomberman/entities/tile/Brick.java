package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.Tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends DestroyableTile {
    private int animate;
    private int MAX_ANIMATE = 7500;
    private int timeToDestroy;

    public Brick(int x, int y, Board board) {
        super(x, y, Sprite.brick.getFxImage(), board);
        belowImg = Sprite.grass.getFxImage();
        animate = 0;
        timeToDestroy = 15;
    }

    public void render(GraphicsContext gc) {
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(belowImg, x - degree, y);
        gc.drawImage(img, x - degree, y);
    }

    public void update() {
        if (exploding) {
            animate++;
            if (timeToDestroy == 0) destroy();
            else timeToDestroy--;
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 20).getFxImage();
        }
    }
}
