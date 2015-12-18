package com.fknstudio.game.model.interfaces;

/**
 * Created by Vladimir on 17.12.2015.
 */
public interface ISnakeBodyElement extends IGameObject {
    ISnakeBodyElement getNext();
    void setNext(ISnakeBodyElement element);
}
