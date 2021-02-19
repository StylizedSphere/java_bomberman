package uet.oop.bomberman.entities.character.ai;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    private boolean burning;

    public Oneal(int x, int y, Board board) {
        super(x, y, Sprite.oneal_right1.getFxImage(), board);
        burning = false;
        score = 10;
    }

    public void chooseImg() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
        if (exploding && !burning) img = Sprite.oneal_dead.getFxImage();
        else {
            if (burning) img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20).getFxImage();
            else {
                if (direction == 2)  img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 20).getFxImage();
                else if (direction == 1)  img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 20).getFxImage();
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
