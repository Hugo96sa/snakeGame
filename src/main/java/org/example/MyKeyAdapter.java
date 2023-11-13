package org.example;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {
    GamePanel gamePanel;
    StateDirection stateDirection;

    public MyKeyAdapter(GamePanel gamePanel, StateDirection stateDirection) {
        this.gamePanel = gamePanel;
        this.stateDirection = stateDirection;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A)) && stateDirection.getDirection() != Direction.RIGHT) {
            stateDirection.setDirection(Direction.LEFT);
        } else if (((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D)) && stateDirection.getDirection() != Direction.LEFT) {
            stateDirection.setDirection(Direction.RIGHT);
        } else if (((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_W)) && stateDirection.getDirection() != Direction.DOWN) {
            stateDirection.setDirection(Direction.UP);
        } else if (((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_S)) && stateDirection.getDirection() != Direction.UP) {
            stateDirection.setDirection(Direction.DOWN);
        }
    }
}
