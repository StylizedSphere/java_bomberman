package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.Tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class Portal extends Tile {
    public Portal(int x, int y, Board board) {
        super(x, y, Sprite.portal.getFxImage(), board);
        belowImg = Sprite.grass.getFxImage();
    }

    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            if (!board.nextMap) Sound.win();
            board.nextMap = true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(belowImg, x - degree, y);
        gc.drawImage(img, x - degree, y);
    }

    public void update() {

    }
}
