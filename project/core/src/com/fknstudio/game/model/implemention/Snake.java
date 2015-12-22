package com.fknstudio.game.model.implemention;

import com.fknstudio.game.model.enums.Direction;
import com.fknstudio.game.model.interfaces.ISnake;
import com.fknstudio.game.model.interfaces.ISnakeBodyElement;


/**
 * Created by Vladimir on 17.12.2015.
 */
public class Snake implements ISnake {
    private ISnakeBodyElement head;
    private Direction direction;
    private int kx;
    private int ky;

    private int growSegmentsLeft = 0;

    public Snake(int countBodyElements, int startX, int startY) {
        if (countBodyElements > 0) {
            head = new SnakeBodyElement(startX, startY);
            generateSnake(head, countBodyElements); // Generating initial snake
            kx = 0;
            ky = 1;
            this.direction = Direction.UP;
        }
    }

    private void generateSnake(ISnakeBodyElement pointer, int count) {
        for (int i = 0; i < count; i++) {
            ISnakeBodyElement snakeBodyElement = new SnakeBodyElement(pointer.getX(), pointer.getY() - 1);
            pointer.setNext(snakeBodyElement);
            pointer = pointer.getNext();
        }
    }

    @Override
    public ISnakeBodyElement getHead() {
        return head;
    }

    @Override
    public ISnakeBodyElement getTail() {
        ISnakeBodyElement tail = head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        return tail;
    }


    @Override
    public void rotate(Direction direction) {
        switch (direction) {
            case LEFT:
                if (this.direction != Direction.RIGHT) {
                    kx = -1;
                    ky = 0;
                    this.direction = direction;
                }
                break;
            case RIGHT:
                if (this.direction != Direction.LEFT) {
                    kx = 1;
                    ky = 0;
                    this.direction = direction;
                }
                break;
            case UP:
                if (this.direction != Direction.DOWN) {
                    kx = 0;
                    ky = 1;
                    this.direction = direction;
                }
                break;
            case DOWN:
                if (this.direction != Direction.UP) {
                    kx = 0;
                    ky = -1;
                    this.direction = direction;
                }
                break;
        }
    }


    @Override
    public void resize(int newLength) {
        growSegmentsLeft += newLength;
    }

    @Override
    public ISnakeBodyElement getAheadHead() {
        return new SnakeBodyElement(head.getX() + kx, head.getY() + ky);
    }

    private void growHead() {
        ISnakeBodyElement oldHead = head;
        head = new SnakeBodyElement(oldHead.getX() + kx, oldHead.getY() + ky);
        head.setNext(oldHead);
    }

    @Override
    public void tick() {
        if (growSegmentsLeft > 0) {
            growHead();
            growSegmentsLeft--;
        } else {
            move();
        }
    }

    private void move() {
        int nextX = head.getX();
        int nextY = head.getY();
        int tempX;
        int tempY;
        head.setX(head.getX() + kx);
        head.setY(head.getY() + ky);
        ISnakeBodyElement pointer = head.getNext();
        while (pointer != null) {
            tempX = pointer.getX();
            tempY = pointer.getY();
            pointer.setX(nextX);
            pointer.setY(nextY);
            nextX = tempX;
            nextY = tempY;
            pointer = pointer.getNext();
        }
    }
}
