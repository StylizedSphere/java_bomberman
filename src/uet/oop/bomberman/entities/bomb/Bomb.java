package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomb extends Entity {
    private double timeToExplode = 120;
    private int timeAfter = 20;
    private int animate;
    private int MAX_ANIMATE = 7500;

    private Flame[] flames = new Flame[0];
    private boolean exploded = false;

    public Bomb() {}

    public Bomb( int xUnit, int yUnit, Board board) {
        this.x = xUnit - xUnit % Sprite.SCALED_SIZE;
        this.y = yUnit - yUnit % Sprite.SCALED_SIZE;
        img = Sprite.bomb.getFxImage();
        this.board = board;
    }


    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            if (exploded) {
                ((Bomber)entity).kill();
            }
            int diffX = entity.getX() - x;
            int diffY = entity.getY() - y;
            if (diffX <= 31 && diffX >= -22 && diffY <= 15 && diffY >= -28)
                return false;
            return true;
        }
        if (entity instanceof Enemy) {
            if (exploded) {
                ((Enemy)entity).explode();
            }
        }
        if (entity instanceof Flame && !exploded) {
            timeToExplode = 0;
        }
        return true;
    }

    private void chooseImg() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
        if(exploded) {
            img = Sprite.movingSprite(Sprite.bomb_exploded,Sprite.bomb_exploded1,Sprite.bomb_exploded2,animate,20).getFxImage();
        } else
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60).getFxImage();
    }

    private void explode() {
        exploded = true;
        timeToExplode = 0;

        flames = new Flame[4];
        for (int i = 0; i < flames.length; i++) {
            flames[i] = new Flame(x, y, i, board.FLAME_LENGTH, board);
        }
        Sound.explosion();
    }

    public boolean isExploded() {
        return exploded;
    }

    public FlameSegment flameAt(int x, int y) {
        for (int i = 0; i < flames.length; i++) {
            if(flames[i] == null) return null;
            FlameSegment e = flames[i].flameSegmentAt(x, y);
            if(e != null) return e;
        }
        return null;
    }

    public void renderFlames(GraphicsContext gc) {
        for (int i = 0; i < flames.length; i++) {
            flames[i].render(gc);
        }
    }

    public void updateFlames() {
        for (int i = 0; i < flames.length; i++) {
            flames[i].update();
        }
    }

    public void render(GraphicsContext gc) {
        chooseImg();
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(img, x - degree, y);
        if (exploded)
            renderFlames(gc);
    }
    public void update() {
        timeToExplode--;
        if (timeToExplode <= 0) {
            explode();
            timeToExplode = 120;
        }
        if (exploded) {
            timeAfter--;
            if (timeAfter <= 0) destroyed = true;
        }
        updateFlames();
    }
}
