package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.LinkedList;

public class LayeredTile extends Entity {
    protected LinkedList<Entity> entities = new LinkedList<>();
    public LayeredTile(int xUnit, int yUnit, Entity ... entitiesInit) {
        x = xUnit;
        y = yUnit;
        for (int i = 0; i < entitiesInit.length; i++) {
            entities.add(entitiesInit[i]);
            if (i > 1 && entitiesInit[i] instanceof DestroyableTile) {
                ((DestroyableTile) entitiesInit[i]).setBelowImg(entitiesInit[i - 1].getImg());
            }
        }
    }

    public Entity getEntity() {
        return entities.getLast();
    }

    public boolean collide(Entity other) {
        return getEntity().collide(other);
    }

    public void render(GraphicsContext gc) {
        getEntity().render(gc);
    }

    public void update() {
        Entity top  = getEntity();

        if(top.isDestroyed())  {
            entities.removeLast();
        }
        getEntity().update();
    }

    public int getX() {
        return getEntity().getX();
    }

    public int getY() {
        return getEntity().getY();
    }

}
