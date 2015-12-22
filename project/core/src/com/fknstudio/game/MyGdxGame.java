package com.fknstudio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fknstudio.game.model.enums.Direction;
import com.fknstudio.game.model.enums.GameState;
import com.fknstudio.game.model.implemention.SnakeGame;
import com.fknstudio.game.model.interfaces.IBonus;
import com.fknstudio.game.model.interfaces.ISnakeBodyElement;
import com.fknstudio.game.model.interfaces.ISnakeGame;
import com.fknstudio.game.view.CellType;
import com.fknstudio.game.view.GameField;

import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {
    // Game model object
    ISnakeGame snakeGame;
    GameField gameField;
    Sound backgroundSound;

    // Tick time control
    float tickTimeLeft = 0;

    // Cell size
    int cellWidth;
    int cellHeight;

    // Game field size
    final int GAME_FIELD_WIDTH = 40;
    final int GAME_FIELD_HEIGHT = 20;

    // Colors
    final Color darkBackColor = new Color(0.12f, 0.42f, 0.26f, 0);
    final Color backColor = new Color(0.14f, 0.43f, 0.27f, 0);
    final Color snakeColor = new Color(1.0f, 0.95f, 1.0f, 0);
    final Color growFoodColor = new Color(0.5f, 0.7f, 0.6f, 0);
    final Color speedFoodColor = new Color(0.255f, 0.412f, 0.882f,0);
    final Color scoreFoodColor = new Color(0.69f, 0.25f ,0.21f,0);

    // Renders
    ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;


    @Override
    public void create() {
        newGame();

        // Cashing cell size
        cellWidth = Gdx.graphics.getWidth() / GAME_FIELD_WIDTH;
        cellHeight = Gdx.graphics.getHeight() / GAME_FIELD_HEIGHT;

        // Initializing font
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        //Initializing background sound
        new Thread(() -> {
            backgroundSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/dj_snake.mp3"));
            backgroundSound.loop();
        }).start();
    }

    private void newGame() {
        // Initializing models
        snakeGame = new SnakeGame(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
        gameField = new GameField(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
    }

    @Override
    public void render() {

        // Check for new game
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && snakeGame.getGameState() == GameState.FINISHED) {
            newGame();
        }

        // Process control keys
        processKeys();

        // Process tick
        tickTimeLeft -= Gdx.graphics.getDeltaTime();
        if (tickTimeLeft < 0) {
            snakeGame.Tick();
            tickTimeLeft = snakeGame.getTickPause();
            gameField.applyNewModelState(snakeGame);
        }

        // RENDER
        // Clear background
        Gdx.gl.glClearColor(0.1f, 0.34f, 0.21f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();

        // Render cells
        for (int i = 0; i < GAME_FIELD_WIDTH; i++) {
            for (int j = 0; j < GAME_FIELD_HEIGHT; j++) {
                Color begin = cellTypeToColor(gameField.getCell(i, j).getOldType(), i, j);
                Color end = cellTypeToColor(gameField.getCell(i, j).getNewType(), i, j);
                float progress = 1 - tickTimeLeft / snakeGame.getTickPause();

                switch (gameField.getCell(i, j).getAnimationType()) {
                    case APPEAR:
                        shapeRenderer.setColor(blendColors(begin, end, progress));
                        shapeRenderer.rect(i * cellWidth, j * cellHeight, cellWidth - 1, cellHeight - 1);
                        break;
                    case INFILL:
                        shapeRenderer.setColor(begin);
                        shapeRenderer.rect(i * cellWidth, j * cellHeight, cellWidth - 1, cellHeight - 1);
                        shapeRenderer.setColor(end);

                        switch (gameField.getCell(i, j).getAnimationDirection()) {
                            case UP:
                                shapeRenderer.rect(i * cellWidth, j * cellHeight, cellWidth - 1, (cellHeight - 1) * progress);
                                break;
                            case RIGHT:
                                shapeRenderer.rect(i * cellWidth, j * cellHeight, (cellWidth - 1) * progress, cellHeight - 1);
                                break;
                            case DOWN:
                                shapeRenderer.rect(i * cellWidth, j * cellHeight + cellHeight - 1 - (cellHeight - 1) * progress,
                                        cellWidth - 1, (cellHeight - 1) * progress);
                                break;
                            case LEFT:
                                shapeRenderer.rect(i * cellWidth + cellWidth - 1 - (cellWidth - 1) * progress, j * cellHeight,
                                        (cellWidth - 1) * progress, cellHeight - 1);
                                break;
                        }

                        break;
                }
            }
        }
        shapeRenderer.end();

        // Render text
        batch.begin();
        font.draw(batch, String.format("Score: %d", snakeGame.getScore()), 15, Gdx.graphics.getHeight() - 10);
        font.draw(batch, String.format("Time: %d", snakeGame.getTotalTicks()), 15, Gdx.graphics.getHeight() - 30);

        if (snakeGame.getGameState() == GameState.FINISHED) {
            font.draw(batch, String.format("Game over. Press space to restart.",
                    snakeGame.getTotalTicks()), 15, Gdx.graphics.getHeight() - 50);
        }

        batch.end();

    }

    private Color cellTypeToColor(CellType type, int x, int y) {
        switch (type) {
            case EMPTY:
                if ((x + y) % 2 == 0 && x % 2 == 0 && y % 2 == 0) {
                    return darkBackColor;
                } else {
                    return backColor;
                }
            case SNAKE:
                return snakeColor;
            case GROWFOOD:
                return growFoodColor;
            case SPEEDFOOD:
                return speedFoodColor;
            case SCOREFOOD:
                return scoreFoodColor;
            default:
                return Color.BLACK; // Unknown
        }
    }

    private Color blendColors(Color begin, Color end, float progress) {
        return new Color(Color.rgba8888(begin.r * (1 - progress) + end.r * progress,
                begin.g * (1 - progress) + end.g * progress,
                begin.b * (1 - progress) + end.b * progress,
                begin.a * (1 - progress) + end.a * progress));
    }

    private void processKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            snakeGame.setDirection(Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            snakeGame.setDirection(Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            snakeGame.setDirection(Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            snakeGame.setDirection(Direction.DOWN);
        }

    }
}
