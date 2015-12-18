package com.fknstudio.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fknstudio.game.model.enums.Direction;
import com.fknstudio.game.model.implemention.SnakeGame;
import com.fknstudio.game.model.interfaces.IBonus;
import com.fknstudio.game.model.interfaces.ISnake;
import com.fknstudio.game.model.interfaces.ISnakeBodyElement;
import com.fknstudio.game.model.interfaces.ISnakeGame;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont font;
    ISnake snake;
    ISnakeGame snakeGame;
    ShapeRenderer shapeRenderer;
    int timeFreeze = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        snakeGame = new SnakeGame(50, 50);
        snake = snakeGame.getSnake();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        if (timeFreeze++ == 4) {
            snakeGame.Tick();
            timeFreeze = 0;
        }
        int kx = Gdx.graphics.getWidth() / 50;
        int ky = Gdx.graphics.getHeight() / 50;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();
        shapeRenderer.setColor(Color.BLUE);
        ISnakeBodyElement pointer = snake.getHead();
        while (pointer != null) {
            shapeRenderer.rect(pointer.getX() * kx, pointer.getY() * ky, kx, ky);
            pointer = pointer.getNext();
        }
        for (IBonus bonus : snakeGame.getBonuses()) {
            switch (bonus.getBonusType()) {
                case SPEED:
                    shapeRenderer.setColor(Color.GOLD);
                    break;
                default:
                    shapeRenderer.setColor(Color.BLUE);
            }
            shapeRenderer.rect(bonus.getX() * kx, bonus.getY() * ky, kx, ky);
        }
        shapeRenderer.end();
    }
}
