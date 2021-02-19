package uet.oop.bomberman.controllers;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Keyboard {
    public boolean up, down, left, right, space;

    public void update(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.RIGHT) {
                    right = true;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    left = true;
                }
                if (event.getCode()== KeyCode.UP) {
                    up = true;
                }
                if (event.getCode()== KeyCode.DOWN) {
                    down = true;
                }
                if (event.getCode()== KeyCode.SPACE) {
                    space = true;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    right = false;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    left = false;
                }
                if (event.getCode() == KeyCode.UP) {
                    up = false;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    down = false;
                }
                if (event.getCode()== KeyCode.SPACE) {
                    space = false;
                }
            }
        });
    }
}