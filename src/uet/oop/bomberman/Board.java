package uet.oop.bomberman;

import uet.oop.bomberman.controllers.Keyboard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Board {
    public Keyboard keyboard;
    public static int offsetX;
    public static int score;
    public static int time;

    public static boolean nextMap;
    public static boolean reset;
    public static boolean interval;
    public static int timeToReset;
    public static int timeInterval;

    public static int MAX_BOMB;
    public static int FLAME_LENGTH;
    public static boolean breakWall;

    public Board(Keyboard keyboard) {
        this.keyboard = keyboard;
        MAX_BOMB = 2;
        FLAME_LENGTH = 2;
        score = 0;
        time = 6000;
        breakWall = false;
        nextMap = false;
        reset = false;
        interval = false;
        timeToReset = 60;
        timeInterval = 60 * 5;
    }
    public Entity getEntity(int x, int y) {
        Entity flameSegment = Bomber.getFlameSegment(x, y);
        if (flameSegment != null) return flameSegment;

        Entity bomb = Bomber.getBomb(x, y);
        if (bomb != null) return bomb;


        for (Entity entity : BombermanGame.stillObjects) {
            if (x >= entity.getX() && x < entity.getX() + Sprite.SCALED_SIZE
            && y >= entity.getY() && y < entity.getY() + Sprite.SCALED_SIZE) {
                return entity.getEntity();
            }
        }
        return null;
    }

    public Entity getBomber(int x, int y) {
        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Bomber && x >= entity.getX() && x < entity.getX() + Sprite.SCALED_SIZE
                    && y >= entity.getY() && y < entity.getY() + Sprite.SCALED_SIZE) {
                return entity.getEntity();
            }
        }
        return null;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }
}
