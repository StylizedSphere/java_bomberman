package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Tile {

    public Grass(int x, int y, Board board) {
        super(x, y, Sprite.grass.getFxImage(), board);
    }

    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {

    }
}
