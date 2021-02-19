package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Coordinates;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Board board;

    protected Image img;
    protected Image belowImg;
    protected boolean destroyed = false;

    public Entity() {}

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img, Board board) {
        this.x = Coordinates.pixelToTile(xUnit);
        this.y = Coordinates.pixelToTile(yUnit + 1);
        this.img = img;
        this.board = board;
    }

    public abstract void render(GraphicsContext gc);
    public abstract void update();
    public abstract boolean collide(Entity entity);
    public boolean isDestroyed() {
        return destroyed;
    }

    public Image getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setBelowImg(Image belowImg) {
        this.belowImg = belowImg;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Entity getEntity() {
        return this;
    };
}
