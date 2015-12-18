package com.fknstudio.game.model.interfaces;

import com.fknstudio.game.model.enums.Direction;
import com.fknstudio.game.model.enums.GameState;

import java.util.List;

/**
 * Created by Vladimir on 17.12.2015.
 */
public interface ISnakeGame {
    ISnake getSnake();

    List<IBonus> getBonuses();

    void setDirection(Direction direction);

    GameState getGameState();

    /**
     * @return bonus has been eaten by snake
     */
    IBonus Tick();

}
