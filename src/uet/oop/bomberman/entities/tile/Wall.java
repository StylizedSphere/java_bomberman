package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Tile {

    public Wall(int x, int y, Board board) {
        super(x, y, Sprite.wall.getFxImage(), board);
    }

    public boolean collide(Entity entity) {
        return true;
    }

    @Override
    public void update() {

    }
}
