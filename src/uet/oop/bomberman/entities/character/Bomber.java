package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private Board board;
    public static List<Bomb> bombs = new ArrayList<>();

    private int timeBetweenPutBombs = 10;
    private int animate;
    private int MAX_ANIMATE = 7500;
    private static int VELOCITY = 4;
    private int vx;
    private int vy;
    private boolean exploding;
    private int timeToExplode;

    public Bomber(int x, int y, Board board) {
        super( x, y, Sprite.player_right.getFxImage(), board);
        this.board = board;
        animate = 0;
        exploding = false;
        timeToExplode = 15;
        board.offsetX = Coordinates.pixelToTile(x - BombermanGame.WIDTH/2);
    }

    public void move() {
        calculateMove();
        if (x + vx < 0 || x + vx > Coordinates.pixelToTile(Map.WIDTH - 1)
                || y + vy < 0 || y + vy > Coordinates.pixelToTile(Map.HEIGHT - 1)) {
            vx = 0;
            vy = 0;
        }
        collisionDetection();
        x += vx;
        y += vy;
        calculateOffset();
    }

    public boolean collide(Entity entity) {
        return false;
    }

    public boolean isDestroyed() {
        return false;
    }
    private void calculateMove() {
        if      (board.getKeyboard().up) {
            vy = -VELOCITY;
            vx = 0;
        }
        else if (board.getKeyboard().down) {
            vy = VELOCITY;
            vx = 0;
        }
        else if (board.getKeyboard().left) {
            vx = -VELOCITY;
            vy = 0;
        }
        else if (board.getKeyboard().right) {
            vx = VELOCITY;
            vy = 0;
        }
        else {
            vx = 0;
            vy = 0;
        }
    }

    private void collisionDetection() {
        int left = x + vx + 1;
        int right = x + vx + Sprite.SCALED_SIZE - 10;
        int top = y + vy + 16;
        int bot = y + vy + Sprite.SCALED_SIZE - 4;
        Entity topLeft = board.getEntity(left, top);
        Entity topRight = board.getEntity(left, bot);
        Entity botLeft = board.getEntity(right, top);
        Entity botRight = board.getEntity(right, bot);

        if (topLeft.collide(this) || topRight.collide(this)
        ||botLeft.collide(this) || botRight.collide(this)) {
            vx = 0;
            vy = 0;
        }
    }

    private void calculateOffset() {
        if (x < Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH/2))board.offsetX += vx;
        else board.offsetX = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH/2);
        if (board.offsetX >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            board.offsetX = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
    }

    public void chooseImg() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
        if (!exploding) {
            if      (board.getKeyboard().up)    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20).getFxImage();
            else if (board.getKeyboard().down)  img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20).getFxImage();
            else if (board.getKeyboard().left)  img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20).getFxImage();
            else if (board.getKeyboard().right) img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20).getFxImage();
            else                                img = Sprite.player_down.getFxImage();
        }
        else {
            if (timeToExplode > 0) {
                img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 20).getFxImage();
                timeToExplode--;
            }
            else {
                destroyed = true;
                img = Sprite.player_dead3.getFxImage();
            }
        }
    }

    private void placeBomb() {
        if (board.getKeyboard().space && timeBetweenPutBombs < 0 && bombs.size() < board.MAX_BOMB
                && getBomb(x + Sprite.DEFAULT_SIZE, y + Sprite.DEFAULT_SIZE) == null) {
            bombs.add(new Bomb(x + Sprite.DEFAULT_SIZE, y + Sprite.DEFAULT_SIZE, board));
            timeBetweenPutBombs = 10;
            Sound.bomb_set();
        }
    }

    private void clearBombs() {
        Iterator i = bombs.iterator();
        Bomb temp;
        while (i.hasNext()) {
            temp = (Bomb) i.next();
            if (temp.isDestroyed()) {
                i.remove();
            }
        }
    }

    public void kill() {
        animate = 0;
        exploding = true;
        board.reset = true;
        //Sound.bomber_dead();
    }

    public static Entity getBomb(int x, int y) {
        for (Entity entity : bombs) {
            if (x >= entity.getX() && x < entity.getX() + Sprite.SCALED_SIZE
                    && y >= entity.getY() && y < entity.getY() + Sprite.SCALED_SIZE) {
                return entity;
            }
        }
        return null;
    }

    public static Entity getFlameSegment(int x, int y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();

            Entity e = b.flameAt(x, y);
            if(e != null) {
                return e;
            }
        }
        return null;
    }

    private void updateBomb() {
        bombs.forEach(bomb -> bomb.update());
    }

    public void render(GraphicsContext gc) {
        for (Bomb bomb : bombs)
            bomb.render(gc);
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        gc.drawImage(img, x - degree, y);
    }

    @Override
    public void update() {
        if (board.time == 0) kill();

        timeBetweenPutBombs--;
        if (timeBetweenPutBombs < -1000) timeBetweenPutBombs = 0;
        chooseImg();
        if (!board.reset && !board.nextMap) {
            move();
            placeBomb();
        }
        updateBomb();
        clearBombs();
    }
}
