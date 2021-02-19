package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Map;

public abstract class Tile extends Entity {

    public Tile(int xUnit, int yUnit, Image img, Board board) {
        super(xUnit, yUnit, img, board);
    }

    public void render(GraphicsContext gc) {
        int degree = board.offsetX < 0 ? 0 : board.offsetX;
        if (degree >= Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH))
            degree = Coordinates.pixelToTile(Map.WIDTH - BombermanGame.WIDTH);
        gc.drawImage(img, x - degree, y);
    }

    public abstract void update();
}
