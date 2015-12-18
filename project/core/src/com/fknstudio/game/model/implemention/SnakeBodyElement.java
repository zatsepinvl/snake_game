package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.interfaces.ISnakeBodyElement;

/**
 * Created by Vladimir on 17.12.2015.
 */
public class SnakeBodyElement extends GameObject implements ISnakeBodyElement {
    protected ISnakeBodyElement next;

    public SnakeBodyElement(int x, int y) {
        super(x,y);
    }

    @Override
    public ISnakeBodyElement getNext() {
        return next;
    }

    @Override
    public void setNext(ISnakeBodyElement element) {
        next = element;
    }
}
