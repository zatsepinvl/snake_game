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
    private static final int MAX_FOOD_COUNT = 10;
    private static final int FOOD_LIVE_TIME = 60;
    private static final int FOOD_MAX_SIZE_BONUS = 2;
    private static final int FOOD_MIN_SIZE_BONUS = 1;
    private static final int FOOD_MAX_SPEED_BONUS = 6;
    private static final int FOOD_MIN_SPEED_BONUS = 3;
    private static final int FOOD_MAX_SCORE_BONUS = 6;
    private static final int FOOD_MIN_SCORE_BONUS = 3;
    private ISnake snake;
    private List<IBonus> bonuses;
    private int fieldW;
    private int fieldH;
    private GameState gameState;
    private Random random;

    private int score = 0;
    private int totalTicks = 0;
    private float tickPause = 0.5f;

    private int speedBonusTimeLeft = 0;

    public SnakeGame(int fieldWidth, int fieldHeight) {
        this.fieldW = fieldWidth;
        this.fieldH = fieldHeight;
        snake = new Snake(SNAKE_SIZE_DEFAULT, fieldW / 2, fieldH / 2);
        bonuses = new LinkedList<IBonus>();
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
    public int getScore() {
        return score;
    }

    @Override
    public int getTotalTicks() {
        return totalTicks;
    }

    @Override
    public void Tick() {
        if (gameState != GameState.FINISHED) {
            // Checking for game over
            if (checkIsGameOver()) {
                gameState = GameState.FINISHED;
                return;
            }

            totalTicks++;
            if (tickPause > 0.05) {
                tickPause -= 0.002f;
            }

            // Processing food
            generateRandomFood();
            checkAndTickBonuses();


            // Processing bonuses
            if (speedBonusTimeLeft > 0) {
                speedBonusTimeLeft--;
            }

            // Checking for bonus ahead head
            IBonus eatenBonus = eatBonuses();
            if (eatenBonus != null) {
                switch (eatenBonus.getType()) {
                    case SIZE:
                        snake.resize(eatenBonus.getValue());
                        score += eatenBonus.getValue();
                        break;
                    case SPEED:
                        speedBonusTimeLeft += eatenBonus.getValue();
                        break;
                    case SCORE:
                        score += eatenBonus.getValue();
                        break;
                }
            }

            // Snake move process
            snake.tick();
        }
    }

    @Override
    public float getTickPause() {
        return tickPause + (speedBonusTimeLeft != 0 ? 0.5f : 0);
    }

    private void generateRandomFood() {
        if (bonuses.size() < MAX_FOOD_COUNT) {
            int temp = random.nextInt(10);
            int x = random.nextInt(fieldW - 1) + 1;
            int y = random.nextInt(fieldH - 1) + 1;
            IBonus food = null;
            switch (temp) {
                case 0: {   // Bonus probability is lower
                    food = new Bonus(x, y, FOOD_LIVE_TIME,
                            random.nextInt(FOOD_MAX_SPEED_BONUS - FOOD_MIN_SPEED_BONUS) + FOOD_MIN_SPEED_BONUS,
                            BonusType.SPEED);
                    break;
                }
                case 1: {
                    food = new Bonus(x, y, FOOD_LIVE_TIME,
                            random.nextInt(FOOD_MAX_SCORE_BONUS - FOOD_MIN_SCORE_BONUS) + FOOD_MIN_SCORE_BONUS,
                            BonusType.SCORE);
                    break;
                }
                default: {
                    food = new Bonus(x, y, FOOD_LIVE_TIME,
                            random.nextInt(FOOD_MAX_SIZE_BONUS - FOOD_MIN_SIZE_BONUS) + FOOD_MIN_SIZE_BONUS,
                            BonusType.SIZE);
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
            if (bonus.getTimeLeft() <= 0) {
                iterator.remove();
            }
        }
    }

    private IBonus eatBonuses() {
        ISnakeBodyElement behindHead = snake.getAheadHead();
        Iterator<IBonus> iterator = bonuses.iterator();
        while (iterator.hasNext()) {
            IBonus bonus = iterator.next();
            if (isCollision(behindHead, bonus)) {
                if (bonus.getType() == BonusType.SIZE) {
                    snake.resize(bonus.getValue());
                }
                iterator.remove();
                return bonus;
            }
        }
        return null;
    }

    private boolean checkIsGameOver() {
        // Check border collision
        ISnakeBodyElement aheadHead = snake.getAheadHead();
        if ((aheadHead.getX()) < 0 || (aheadHead.getX() > fieldW - 1)) {
            return true;
        } else if ((aheadHead.getY() < 0) || (aheadHead.getY() > fieldH - 1)) {
            return true;
        }

        // Check snake collision
        ISnakeBodyElement pointer = snake.getHead().getNext();
        while (pointer != null) {
            if (isCollision(snake.getHead(), pointer)) {
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
