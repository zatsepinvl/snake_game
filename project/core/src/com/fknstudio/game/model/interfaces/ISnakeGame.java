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

    GameState getGameState();
    int getScore();
    int getTotalTicks();

    void setDirection(Direction direction);
    void Tick();
    float getTickPause();
}
