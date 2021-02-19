package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class FlameSegment extends Entity {
    private int direction;
    private boolean lastSegment;
    
    private int animate;
    private int MAX_ANIMATE = 7500;

    public FlameSegment(int x, int y, int direction, boolean isLast) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.lastSegment = isLast;
        switch (direction) {
            case 0:
                if(!lastSegment) {
                    img = Sprite.explosion_vertical2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                }
                break;
            case 1:
                if(!lastSegment) {
                    img = Sprite.explosion_horizontal2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                }
                break;
            case 2:
                if(!lastSegment) {
                    img = Sprite.explosion_vertical2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical_down_last2.getFxImage();
                }
                break;
            case 3:
                if(!lastSegment) {
                    img = Sprite.explosion_horizontal2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal_left_last2.getFxImage();
                }
                break;
        }
    }

    public void render(GraphicsContext gc) {
        switch (direction) {
            case 0:
                if(!lastSegment) {
                    img = Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,animate,20).getFxImage();
                } else {
                    img = Sprite.movingSprite(Sprite.explosion_vertical_top_last,Sprite.explosion_vertical_top_last1,Sprite.explosion_vertical_top_last2,animate,20).getFxImage();
                }
                break;
            case 1:
                if(!lastSegment) {
                    img = Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,animate,20).getFxImage();
                } else {
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,Sprite.explosion_horizontal_right_last1,Sprite.explosion_horizontal_right_last2,animate,20).getFxImage();
                }
                break;
            case 2:
                if(!lastSegment) {
                    img = Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,animate,20).getFxImage();
                } else {
                    img = Sprite.movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last2,animate,20).getFxImage();

                }
                break;
            case 3:
                if(!lastSegment) {
                    img = Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,animate,20).getFxImage();
                } else {
                    //_sprite = Sprite.explosion_horizontal_left_last2;
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last2,animate,20).getFxImage();
                }
                break;
        }
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(img, x - degree, y);
    }

    public void update() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    public boolean collide(Entity e) {
        if (e instanceof Bomber){
            ((Bomber)e).kill();
        }
        if (e instanceof Enemy)
            ((Enemy)e).explode();
        return false;
    }
}
