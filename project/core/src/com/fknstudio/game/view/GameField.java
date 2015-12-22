package com.fknstudio.game.view;

import com.fknstudio.game.model.interfaces.IBonus;
import com.fknstudio.game.model.interfaces.ISnakeBodyElement;
import com.fknstudio.game.model.interfaces.ISnakeGame;

/**
 * Created by Alexander on 22/12/15.
 */
public class GameField {
    private int width;
    private int height;

    private int oldHeadX = 0;
    private int oldHeadY = 0;

    private int oldTailX = 0;
    private int oldTailY = 0;

    private GameFieldCell[][] field;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;

        field = new GameFieldCell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = new GameFieldCell();
            }
        }
    }

    public void applyNewModelState(ISnakeGame snakeGame) {
        // All to dark
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getCell(i, j).ShiftTypeTo(CellType.EMPTY);
                getCell(i, j).setAnimationType(AnimationType.APPEAR);
            }
        }

        // Snake
        ISnakeBodyElement element = snakeGame.getSnake().getHead();
        while (element != null) {
            getCell(element.getX(), element.getY()).setNewType(CellType.SNAKE);
            element = element.getNext();
        }

        // Food
        for (IBonus bonus : snakeGame.getBonuses()) {
            switch (bonus.getType()) {
                case SPEED:
                    getCell(bonus.getX(), bonus.getY()).setNewType(CellType.SPEEDFOOD);
                    break;
                case SIZE:
                    getCell(bonus.getX(), bonus.getY()).setNewType(CellType.GROWFOOD);
                    break;
                case SCORE:
                    getCell(bonus.getX(), bonus.getY()).setNewType(CellType.SCOREFOOD);
            }
        }

        // Head process
        int newHeadX = snakeGame.getSnake().getHead().getX();
        int newHeadY = snakeGame.getSnake().getHead().getY();

        getCell(newHeadX, newHeadY).setAnimationType(AnimationType.INFILL);

        if (newHeadX > oldHeadX) {
            getCell(newHeadX, newHeadY).setAnimationDirection(AnimationDirection.RIGHT);
        }
        if (newHeadX < oldHeadX) {
            getCell(newHeadX, newHeadY).setAnimationDirection(AnimationDirection.LEFT);
        }
        if (newHeadY > oldHeadY) {
            getCell(newHeadX, newHeadY).setAnimationDirection(AnimationDirection.UP);
        }
        if (newHeadY < oldHeadY) {
            getCell(newHeadX, newHeadY).setAnimationDirection(AnimationDirection.DOWN);
        }

        oldHeadX = newHeadX;
        oldHeadY = newHeadY;

        // Tail process
        int newTailX = snakeGame.getSnake().getTail().getX();
        int newTailY = snakeGame.getSnake().getTail().getY();

        getCell(oldTailX, oldTailY).setAnimationType(AnimationType.INFILL);

        if (newTailX > oldTailX) {
            getCell(oldTailX, oldTailY).setAnimationDirection(AnimationDirection.RIGHT);
        }
        if (newTailX < oldTailX) {
            getCell(oldTailX, oldTailY).setAnimationDirection(AnimationDirection.LEFT);
        }
        if (newTailY > oldTailY) {
            getCell(oldTailX, oldTailY).setAnimationDirection(AnimationDirection.UP);
        }
        if (newTailY < oldTailY) {
            getCell(oldTailX, oldTailY).setAnimationDirection(AnimationDirection.DOWN);
        }

        oldTailX = newTailX;
        oldTailY = newTailY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameFieldCell getCell(int i, int j) {
        return field[i][j];
    }
}
