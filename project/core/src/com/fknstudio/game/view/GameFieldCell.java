package com.fknstudio.game.view;

/**
 * Created by Alexander on 22/12/15.
 */
public class GameFieldCell {
    private CellType oldType;
    private CellType newType;

    private AnimationType animationType;
    private AnimationDirection animationDirection;

    public GameFieldCell() {
        setOldType(CellType.EMPTY);
        setNewType(CellType.EMPTY);

        setAnimationType(AnimationType.APPEAR);
        setAnimationDirection(AnimationDirection.UP);
    }

    public void ShiftTypeTo(CellType type) {
        oldType = newType;
        newType = type;
    }

    public CellType getOldType() {
        return oldType;
    }

    public void setOldType(CellType oldType) {
        this.oldType = oldType;
    }

    public CellType getNewType() {
        return newType;
    }

    public void setNewType(CellType newType) {
        this.newType = newType;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public AnimationDirection getAnimationDirection() {
        return animationDirection;
    }

    public void setAnimationDirection(AnimationDirection animationDirection) {
        this.animationDirection = animationDirection;
    }
}
