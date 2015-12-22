package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.enums.BonusType;
import com.fknstudio.game.model.interfaces.IBonus;

/**
 * Created by Vladimir on 17.12.2015.
 */
public class Bonus extends GameObject implements IBonus {

    private int value;
    private int timeLeft;
    private BonusType type;

    public Bonus(int x, int y, int timeLeft, int bonusValue, BonusType bonusType) {
        super(x, y);
        this.timeLeft = timeLeft;
        this.value=bonusValue;

        setType(bonusType);
    }

    @Override
    public int getValue() {
        return value;
    }


    @Override
    public long getTimeLeft() {
        return timeLeft;
    }

    @Override
    public void tick() {
        timeLeft--;
    }

    @Override
    public BonusType getType() {
        return type;
    }

    public void setType(BonusType type) {
        this.type = type;
    }
}
