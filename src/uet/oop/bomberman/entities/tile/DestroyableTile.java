package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Tile;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public abstract class DestroyableTile extends Tile {
    protected boolean isDestroyed;
    protected boolean exploding;

    protected int score;

    public DestroyableTile(int xUnit, int yUnit, Image img, Board board) {
        super(xUnit, yUnit, img, board);
        isDestroyed = false;
        exploding = false;
        belowImg = Sprite.grass.getFxImage();
        score = 0;
    }

    public boolean collide(Entity other) {
        if (isDestroyed()) return false;
        if (other instanceof Flame) {
            explode();
            return true;
        }
        if (other instanceof Bomber && board.breakWall) {
            explode();
            return true;
        }
        int leftA = x, leftB = other.getX();
        int rightA = x + Sprite.SCALED_SIZE, rightB = other.getX() + Sprite.SCALED_SIZE;
        int topA = y, topB = other.getY();
        int botA = y + Sprite.SCALED_SIZE, botB = other.getY() + Sprite.SCALED_SIZE;
        if (botA < topB || topA > botB || leftA > rightB || rightA < leftB) {return false;}
        return true;
    }

    public void destroy() {
        isDestroyed = true;
        img = belowImg;
        board.score += score;
    }

    public void explode() {
        exploding = true;
        Sound.mob_death();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public abstract void update();
}
