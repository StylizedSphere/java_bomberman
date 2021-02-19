package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Tile;

public abstract class Item extends Tile {
    public Item(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }
}
