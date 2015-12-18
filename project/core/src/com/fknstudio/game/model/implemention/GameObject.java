package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.interfaces.IGameObject;


/**
 * Created by Vladimir on 17.12.2015.
 */
public abstract class GameObject implements IGameObject {
    protected int x;
    protected int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
