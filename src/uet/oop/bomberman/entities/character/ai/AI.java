package uet.oop.bomberman.entities.character.ai;

import java.util.Random;

public abstract class AI {

    protected Random random = new Random();
    // 0 down, 1 right, 2 left, 3 up

    public abstract int calculateDirection();
}
