package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Flame extends Entity {
    private int RADIUS;
    private int direction;
    private int leftLen;
    private int rightLen;
    private int upLen;
    private int downLen;
    private List<FlameSegment> flameSegments = new ArrayList<>();

    public Flame(int x, int y, int direction, int radius, Board board) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.RADIUS = radius;
        this.board = board;
        leftLen = 0; rightLen = 0; upLen = 0; downLen = 0;
        createFlameSegments();
    }

    public void update() {
        flameSegments.forEach(element -> element.update());
    }

    public void render(GraphicsContext gc) {
        flameSegments.forEach(element -> element.render(gc));
    }

    public void createFlameSegments() {
        calculateDistance();
        boolean isLast = false;
        for (int i = 1; i <= leftLen; i++) {
            isLast = i == leftLen ? true : false;
            flameSegments.add(new FlameSegment(x - i * 32, y, direction, isLast));
        }
        for (int i = 1; i <= rightLen; i++) {
            isLast = i == rightLen ? true : false;
            flameSegments.add(new FlameSegment(x + i * 32, y, direction, isLast));
        }
        for (int i = 1; i <= upLen; i++) {
            isLast = i == upLen ? true : false;
            flameSegments.add(new FlameSegment(x, y - i * 32, direction, isLast));
        }
        for (int i = 1; i <= downLen; i++) {
            isLast = i == downLen ? true : false;
            flameSegments.add(new FlameSegment(x, y + i * 32, direction, isLast));
        }

    }

    private void calculateDistance() {
        if (direction == 0) {
            while (upLen < RADIUS) {
                if (board.getEntity(x , y - (upLen + 1) * 32).collide(this)) break;
                upLen++;
            }
        }
        else if (direction == 1) {
            while (rightLen < RADIUS) {
                if (board.getEntity(x + (rightLen + 1) * 32, y).collide(this)) break;
                rightLen++;
            }
        }
        else if (direction == 2) {
            while (downLen < RADIUS) {
                if (board.getEntity(x, y + (downLen + 1) * 32).collide(this)) break;
                downLen++;
            }
        }
        else if (direction == 3) {
            while (leftLen < RADIUS) {
                if (board.getEntity(x - (leftLen + 1) * 32, y).collide(this)) break;
                leftLen++;
            }
        }
    }

    public boolean collide(Entity other) {
        if (other instanceof Bomber)
            System.out.println("Flame touched Bomber");
        if (other instanceof Enemy)
            ((Enemy)other).destroy();
        return false;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (FlameSegment flameSegment : flameSegments) {
            if (x >= flameSegment.getX() && x < flameSegment.getX() + Sprite.SCALED_SIZE
                    && y >= flameSegment.getY() && y < flameSegment.getY() + Sprite.SCALED_SIZE) {
                return flameSegment;
            }
        }
        return null;
    }
}