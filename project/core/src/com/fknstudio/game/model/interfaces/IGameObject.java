package com.fknstudio.game.model.interfaces;

/**
 * Created by Vladimir on 17.12.2015.
 */
public interface IGameObject {
    /**
     *
     * @return the coordinate of X axis of an abstract matrix game map
     */
    int getX();

    /**
     *
     * @return the coordinate of Y axis of an abstract matrix game map
     */
    int getY();

    void setX(int X);

    void setY(int Y);
}
