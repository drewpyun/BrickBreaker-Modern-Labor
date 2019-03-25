package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;

    //Starting Score
    private int score = 0;

    //Total Bricks when start game
    private int totalBricks = 51;

    //Speed of ball
    private Timer time;
    private int delay = 3;

    //Starting position
    private int playerX = 310;

    //Starting position of ball
    private int ballposX = 180;
    private int ballposY = 350;

    //Direction of ball
    private int ballxdir = -2;
    private int ballydir = 1;

    private MapGenerator map;


    //Constructor
    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }
    //TODO fix the left side of paddle; it bounces off when it shouldnt
    //TODO add intro screen
    //TODO stop paddle when game end
    //TODO make ball hitbox smaller (2,2)
    //TODO fix sometimes ball goes through the right side of blocks instead of reflecting
    //TODO add random reflection balldir
    //TODO 54:08
    public void paint(Graphics g) {
        //Background
        g.setColor(Color.pink);
        g.fillRect(1,2,692,592);

        //Borders
        g.setColor(Color.orange);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //Scores
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        g.drawString("Score: "+score, 550, 30);

        //GameOver Screen
        if(ballposY > 540 ) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: ", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to restart", 230, 300);
        }

        //Paddle
        g.setColor(Color.blue);
        g.fillRect(playerX+20, 550,130, 8);

        //Ball
        g.setColor(Color.magenta);
        g.fillOval(ballposX, ballposY, 10,10);

        //Generate Blocks
        map.draw((Graphics2D)g);
        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();

        //Bouncing off paddle and border
        if(play) {
            if(new Rectangle(ballposX, ballposY, 80, 20).
                    intersects(new Rectangle(playerX, 550, 100, 8) ) ) {
                ballydir = -ballydir;
            }

            for(int i = 0; i <map.map.length; i++ ) {
                for(int j  = 0; j<map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth + 80;
                        int bricky = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, bricky, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposY + 1 >= brickRect.x + brickRect.width) {
                                ballxdir = -ballxdir;
                            }
                            else {
                                ballydir = -ballydir;
                            }
                            break;
                        }
                    }
                }
            }

            ballposX += ballxdir;
            ballposY += ballydir;
            if(ballposY < 0 ) {
                ballydir = -ballydir;
            }

            if(ballposX < 0 ) {
                ballxdir = -ballxdir;
            }
            if(ballposX > 670 ) {
                ballxdir = -ballxdir;
            }
        }
        repaint();


    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >=575) {
                playerX = 575;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <5) {
                playerX = 5;
            }
            else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballxdir = -1;
                ballydir = 2;
            }
        }
    }
    public void moveRight() {
        play = true;
        playerX+=20;
    }
    public void moveLeft() {
        play = true;
        playerX-=20;
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
