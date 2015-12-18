package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.interfaces.IBonus;

/**
 * Created by Vladimir on 17.12.2015.
 */
public abstract class Bonus extends GameObject implements IBonus {

    private int value;
    private int liveTime;

    public Bonus(int x, int y, int liveTime, int bonusValue) {
        super(x, y);
        this.liveTime = liveTime;
        this.value=bonusValue;
    }

    @Override
    public int getValue() {
        return value;
    }


    @Override
    public long getLiveTime() {
        return liveTime;
    }

    @Override
    public void tick() {
        liveTime--;
    }
}
