package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.controllers.Keyboard;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.ai.Balloon;
import uet.oop.bomberman.entities.character.ai.Oneal;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.entities.tile.item.ItemBomb;
import uet.oop.bomberman.entities.tile.item.ItemBreakWall;
import uet.oop.bomberman.entities.tile.item.ItemFlame;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 16;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    private static List<String[]> levels = new ArrayList<>();
    private int currentLevelIndex;
    private String[] currentLevel;

    private Board board;
    private Keyboard keyboard;
    private Bomber bomber;

    private Stage mainStage;
    private Scene mainScene;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void goToMainScene() {
        mainStage.setScene(mainScene);
        mainStage.show();
        board.time = 6000;
    }

    public void goToIntervalScene() {
        URL path = Map.class.getResource("level2.png");
        Image image = new Image(path.toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(512);
        imageView.setFitWidth(720);
        imageView.setPreserveRatio(true);
        Group root = new Group(imageView);
        Scene scene = new Scene(root, 640, 480);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        mainScene = new Scene(root);
        mainStage = stage;
        mainStage.setTitle("BombermanGame 1983");

        goToMainScene();
        currentLevelIndex = 0;
        prepareAssets();

        Sound.theme_music();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                keyboard.update(mainScene);
                render();
                update();
            }
        };
        timer.start();
    }

    public void createMap() {
        levels.add(Map.level1);
        levels.add(Map.level2);
        currentLevel = levels.get(currentLevelIndex);
        for (int i = 0; i < Map.WIDTH; i++) {
            for (int j = 0; j < Map.HEIGHT; j++) {
                Entity object;
                if (Map.isWall(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Wall(i, j, board));
                }
                else if (Map.isBrick(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Grass(i, j, board), new Brick(i, j, board));
                }
                else if (Map.isPortal(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Portal(i, j, board), new Brick(i, j, board));
                }
                else if (Map.isFlameItem(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Grass(i, j, board), new ItemFlame(i, j, board), new Brick(i, j, board));
                }
                else if (Map.isBombItem(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Grass(i, j, board), new ItemBomb(i, j, board), new Brick(i, j, board));
                }
                else if (Map.isWallItem(currentLevel, i, j)) {
                    object = new LayeredTile(i, j, new Grass(i, j, board), new ItemBreakWall(i, j, board), new Brick(i, j, board));
                }
                else if (Map.isBalloon(currentLevel, i, j)) {
                    object = new Balloon(i, j, board);
                    entities.add(object);
                    object = new Grass(i, j, board);
                }
                else if (Map.isOneal(currentLevel, i, j)) {
                    object = new Oneal(i, j, board);
                    entities.add(object);
                    object = new Grass(i, j, board);
                }
                else {
                    object = new LayeredTile(i, j, new Grass(i, j, board));
                }
                stillObjects.add(object);
            }
        }
    }

    public void prepareAssets() {
        keyboard = new Keyboard();
        board = new Board(keyboard);
        createMap();
        bomber = new Bomber(1, 1, board);
        entities.add(bomber);
    }

    public void nextLevel() {
        entities = new ArrayList<>();
        stillObjects = new ArrayList<>();
        Bomber.bombs = new ArrayList<>();
        currentLevelIndex++;
    }

    public void reset() {
        entities = new ArrayList<>();
        stillObjects = new ArrayList<>();
        Bomber.bombs = new ArrayList<>();
        board.score = 0;
    }

    public void changeMap() {
        if (board.nextMap) {
            if (board.timeToReset < 0){
                nextLevel();
                prepareAssets();
                board.nextMap = false;
                board.timeToReset = 60;
                board.interval = true;
                goToIntervalScene();
            }
            else board.timeToReset--;
        }
        if (board.reset) {
            if (board.timeToReset < 0) {
                reset();
                prepareAssets();
                board.reset = false;
                board.timeToReset = 60;
            }
            else board.timeToReset--;
        }
        if (board.interval) {
            if (board.timeInterval < 0) {
                goToMainScene();
                board.timeInterval = 60;
                board.interval = false;
            }
            else board.timeInterval--;
        }
    }

    public void update() {
        changeMap();
        board.time-=2;

        entities.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        Iterator i = entities.iterator();
        Entity temp;
        while (i.hasNext()) {
            temp = (Entity) i.next();
            if (temp.isDestroyed()) {
                i.remove();
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 32 * 20 , 32 * 1);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + board.score + "\t\t\t\t\t\t\tTime: " + board.time/100,5, 30);

        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
