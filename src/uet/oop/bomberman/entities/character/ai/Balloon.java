package uet.oop.bomberman.entities.character.ai;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    private boolean burning;

    public Balloon(int x, int y, Board board) {
        super(x, y, Sprite.balloom_right1.getFxImage(), board);
        burning = false;
        score = 5;
    }

    public void chooseImg() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
        if (exploding && !burning) img = Sprite.balloom_dead.getFxImage();
        else {
            if (burning) img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20).getFxImage();
            else {
                if (direction == 2)  img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 20).getFxImage();
                else if (direction == 1)  img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 20).getFxImage();
            }
        }
    }

    public void render(GraphicsContext gc) {
        chooseImg();
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(img, x - degree, y);
    }

    @Override
    public void update() {
        if (!exploding)
            move();
        else {
            animate++;
            if (timeToDestroy == 0) destroy();
            else if (timeToDestroy < 20) {burning = true;}
            timeToDestroy--;
        }
    }
}
