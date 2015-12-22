package com.fknstudio.game.model.interfaces;

import com.fknstudio.game.model.enums.Direction;

/**
 * Created by Vladimir on 17.12.2015.
 */
public interface ISnake extends IDynamicGameObject {

    /**
     * Return the head
     */
    ISnakeBodyElement getHead();

    /**
     * Return the tail
     */
    ISnakeBodyElement getTail();

    /**
     * Rotate snake to the {@link Direction}
     */
    void rotate(Direction direction);


    /**
     * Resize snake body to count elements
     */
    void resize(int count);

    ISnakeBodyElement getAheadHead();
}
