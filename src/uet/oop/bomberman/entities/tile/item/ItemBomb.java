package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class ItemBomb extends Item {
    public ItemBomb(int x, int y, Board board) {
        super(x, y, Sprite.powerup_bombs.getFxImage(), board);
    }

    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            if (!destroyed) board.MAX_BOMB++;
            Sound.get_item();
        }
        destroyed = true;
        return false;
    }

    public void render(GraphicsContext gc) {
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(img, x - degree, y);
    }

    public void update() {

    }
}
