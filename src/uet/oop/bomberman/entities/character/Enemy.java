package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.ai.AI;
import uet.oop.bomberman.entities.character.ai.AILow;
import uet.oop.bomberman.entities.tile.DestroyableTile;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Enemy extends DestroyableTile {
    protected Board board;
    protected AI ai;
    protected int direction;
    protected int animate;
    protected int MAX_ANIMATE = 7500;
    protected int timeToDestroy = 15 * 4;
    protected static int VELOCITY = 1;
    protected int left;
    protected int right;
    protected int up;
    protected int down;
    protected int vx;
    protected int vy;

    public Enemy(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        this.board = board;
        animate = 0;
        ai = new AILow();
        direction = ai.calculateDirection();
    }
    
    public void calculateMove() {
        // 0 down, 1 right, 2 left, 3 up
        if (left <= 0 && right <= 0 && up <= 0 && down <= 0) {
            direction = ai.calculateDirection();
            if      (direction == 0) down = 32;
            else if (direction == 1) right = 32;
            else if (direction == 2) left = 32;
            else if (direction == 3) up = 32;
        }
    }

    public void move() {
        calculateMove();
        if (left > 0)           vx = -VELOCITY;
        else if (right > 0)    vx = VELOCITY;
        else if (up > 0)       vy = -VELOCITY;
        else if (down > 0)     vy = VELOCITY;
        collisionDetection();
        collideWithBomber();
        if      (down > 0) {
            down -= VELOCITY;
            y += vy;
        }
        else if (right > 0) {
            right -= VELOCITY;
            x += vx;
        }
        else if (left > 0) {
            left -= VELOCITY;
            x += vx;
        }
        else if (up > 0) {
            up -= VELOCITY;
            y += vy;
        }
    }

    public boolean collide(Entity entity) {
        if (entity instanceof Flame)
            exploding = true;
        return true;
    }

    private void collideWithBomber() {
        int l = x + vx + 10;
        int r = x + vx + Sprite.SCALED_SIZE - 1;
        int t = y + vy + 4;
        int b = y + vy + Sprite.SCALED_SIZE - 16;
        Entity topLeft = board.getBomber(l, t);
        Entity topRight = board.getBomber(l, b);
        Entity botLeft = board.getBomber(r, t);
        Entity botRight = board.getBomber(r, b);

        if (topLeft != null) {
            ((Bomber)topLeft).kill();
        }
        else if (topRight != null) {
            ((Bomber)topRight).kill();
        }
        else if (botLeft != null) {
            ((Bomber)botLeft).kill();
        }
        else if (botRight != null) {
            ((Bomber)botRight).kill();
        }
    }

    // 0 down, 1 right, 2 left, 3 up
    private void collisionDetection() {
        int l = x + vx;
        int r = x + vx + Sprite.SCALED_SIZE - 1;
        int t = y + vy;
        int b = y + vy + Sprite.SCALED_SIZE - 1;
        Entity topLeft = board.getEntity(l, t);
        Entity topRight = board.getEntity(l, b);
        Entity botLeft = board.getEntity(r, t);
        Entity botRight = board.getEntity(r, b);

        if (topLeft.collide(this) || topRight.collide(this)
                || botLeft.collide(this) || botRight.collide(this)) {
            vx = 0; vy = 0; left = 0; right = 0; up = 0; down = 0;
        }
    }

    public abstract void render(GraphicsContext gc);
    public abstract void update();
}