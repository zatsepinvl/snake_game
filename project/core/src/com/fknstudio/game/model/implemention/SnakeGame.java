package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.enums.BonusType;
import com.fknstudio.game.model.enums.Direction;
import com.fknstudio.game.model.enums.GameState;
import com.fknstudio.game.model.interfaces.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vladimir on 17.12.2015.
 */
public class SnakeGame implements ISnakeGame {
    private static final int SNAKE_SIZE_DEFAULT = 10;
    private static final int MAX_FOOD_COUNT = 8;
    private static final int FOOD_LIVE_TIME = 100;
    private static final int FOOD_MAX_SIZE_BONUS = 2;
    private static final int FOOD_MIN_SIZE_BONUS = 1;
    private static final int FOOD_MAX_SPEED_BONUS = 2;
    private static final int FOOD_MIN_SPEED_BONUS = -1;
    private ISnake snake;
    private List<IBonus> bonuses;
    private int fieldW;
    private int fieldH;
    private GameState gameState;
    private Random random;

    public SnakeGame(int fieldWidth, int fieldHeight) {
        this.fieldW = fieldWidth;
        this.fieldH = fieldHeight;
        snake = new Snake(SNAKE_SIZE_DEFAULT, fieldW / 2, fieldH / 2);
        bonuses = new LinkedList<>();
        gameState = GameState.STARTED;
        random = new Random();

    }

    @Override
    public ISnake getSnake() {
        return snake;
    }

    @Override
    public List<IBonus> getBonuses() {
        return bonuses;
    }


    @Override
    public void setDirection(Direction direction) {
        snake.rotate(direction);
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }


    @Override
    public IBonus Tick() {
        if (gameState != GameState.FINISHED) {
            generateRandomFood();
            checkAndTickBonuses();
            snake.tick();
            if (checkIsGameOver()) {
                gameState = GameState.FINISHED;
            }
            return eatBonuses();
        }
        return null;
    }

    private void generateRandomFood() {
        if (bonuses.size() < MAX_FOOD_COUNT) {
            int temp = random.nextInt(2);
            int x = random.nextInt(fieldW - 1) + 1;
            int y = random.nextInt(fieldH - 1) + 1;
            IBonus food = null;
            switch (temp) {
                case 0: {
                    food = new SizeBonus(x, y, FOOD_LIVE_TIME, random.nextInt(FOOD_MAX_SIZE_BONUS - FOOD_MIN_SIZE_BONUS) + FOOD_MIN_SIZE_BONUS);
                    break;
                }
                case 1: {
                    food = new SpeedBonus(x, y, FOOD_LIVE_TIME, random.nextInt(FOOD_MAX_SPEED_BONUS - FOOD_MIN_SPEED_BONUS) + FOOD_MIN_SPEED_BONUS);
                    break;
                }
            }
            bonuses.add(food);

        }
    }

    private void checkAndTickBonuses() {
        Iterator<IBonus> iterator = bonuses.iterator();
        while (iterator.hasNext()) {
            IBonus bonus = iterator.next();
            bonus.tick();
            if (bonus.getLiveTime() <= 0) {
                iterator.remove();
            }
        }
    }

    private IBonus eatBonuses() {
        ISnakeBodyElement head = snake.getHead();
        Iterator<IBonus> iterator = bonuses.iterator();
        while (iterator.hasNext()) {
            IBonus bonus = iterator.next();
            if (isCollision(head, bonus)) {
                if (bonus.getBonusType() == BonusType.SIZE) {
                    snake.resize(bonus.getValue());
                }
                iterator.remove();
                return bonus;
            }
        }
        return null;
    }

    private boolean checkIsGameOver() {
        ISnakeBodyElement head = snake.getHead();
        if ((head.getX()) < 0 || (head.getX() > fieldW)) {
            return true;
        } else if ((head.getY() < 0) || (head.getY() > fieldH)) {
            return true;
        }
        ISnakeBodyElement pointer = head.getNext();
        while (pointer != null) {
            if (isCollision(head, pointer)) {
                return true;
            }
            pointer = pointer.getNext();
        }
        return false;
    }

    private boolean isCollision(IGameObject o1, IGameObject o2) {
        return (o1.getX() == o2.getX()) && (o1.getY() == o2.getY());
    }

}
