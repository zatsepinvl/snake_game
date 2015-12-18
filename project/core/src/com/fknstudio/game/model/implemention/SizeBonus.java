package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.enums.BonusType;

/**
 * Created by Vladimir on 18.12.2015.
 */
public class SizeBonus extends Bonus {
    public SizeBonus(int x, int y, int liveTime, int bonusValue) {
        super(x, y, liveTime, bonusValue);
    }

    @Override
    public BonusType getBonusType() {
        return BonusType.SIZE;
    }
}
