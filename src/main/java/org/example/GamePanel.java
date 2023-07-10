package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int MAX_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static int DELAY = 200;
    int[][] snake = new int[2][MAX_UNITS];
    int[] apple = new int[2];
    int bodyLength = 5;
    StateDirection stateDirection;
    String font = "Calibri";
    int applesEaten;
    boolean running;
    Timer timer;
    Random random;
    boolean aux = false;

    GamePanel() {
        random = new Random();
        stateDirection = new StateDirection('R');
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter(this, stateDirection));
        playGame();
    }

    public void playGame() {
        addApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void addApple() {
        boolean breaker;
        do {
            breaker = false;
            apple[0] = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            apple[1] = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            for (int i = bodyLength; i >= 0; i--) {
                if ((apple[0] == snake[0][i]) && (apple[1] == snake[1][i])) {
                    System.out.println("Bounce!");
                    breaker = true;
                    break;
                }
            }
        } while (breaker);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public void move() {
        for (int i = bodyLength; i > 0; i--) {
            snake[0][i] = snake[0][i - 1];
            snake[1][i] = snake[1][i - 1];
        }

        switch (stateDirection.getDirection()) {
            case 'U' -> snake[1][0] = snake[1][0] - UNIT_SIZE;
            case 'R' -> snake[0][0] = snake[0][0] + UNIT_SIZE;
            case 'D' -> snake[1][0] = snake[1][0] + UNIT_SIZE;
            case 'L' -> snake[0][0] = snake[0][0] - UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((snake[0][0] == apple[0]) && (snake[1][0] == apple[1])) {
            System.out.println("Apple!");
            bodyLength++;
            applesEaten++;
            addApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyLength; i > 3; i--) {
            if ((snake[0][0] == snake[0][i]) && (snake[1][0] == snake[1][i])) {
                System.out.println("Bite!");
                running = false;
                break;
            }
        }
        if (snake[0][0] < 0 || snake[0][0] >= SCREEN_WIDTH || snake[1][0] < 0 || snake[1][0] >= SCREEN_HEIGHT) {
            System.out.println("Crash!");
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics g) {
        if (running) {
            // Grid
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            }
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Apple
            g.setColor(new Color(185, 90, 45));
            g.fillOval(apple[0], apple[1], UNIT_SIZE, UNIT_SIZE);

            // Snake
            for (int i = 0; i < bodyLength; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(snake[0][i], snake[1][i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 80));
//                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(snake[0][i], snake[1][i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.lightGray);
            g.setFont(new Font(font, Font.BOLD, 22));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont()
                    .getSize());
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font(font, Font.PLAIN, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont()
                .getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font(font, Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }
}
