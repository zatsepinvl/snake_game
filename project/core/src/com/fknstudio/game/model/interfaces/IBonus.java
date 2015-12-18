package com.fknstudio.game.model.interfaces;

import com.fknstudio.game.model.enums.BonusType;

/**
 * Created by Vladimir on 17.12.2015.
 */
public interface IBonus extends IGameObject, IDynamicGameObject {

    /**
     * @return the value that using depends on {@link BonusType}
     */
    int getValue();

    /**
     *
     * @return the value of live time in game ticks
     */
    long getLiveTime();

    BonusType getBonusType();
}
