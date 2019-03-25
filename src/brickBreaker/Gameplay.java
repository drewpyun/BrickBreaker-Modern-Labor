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
    private int delay = 5;

    //Starting position
    private int playerX = 310;

    //Starting position of ball
    private int ballposX = 120;
    private int ballposY = 350;

    //Direction of ball
    private int ballxdir = -1;
    private int ballydir = -2;

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

    public void paint(Graphics g) {
        //Background
        g.setColor(Color.pink);
        g.fillRect(1,2,692,592);

        //Borders
        g.setColor(Color.orange);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //Paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550,100, 8);

        //Ball
        g.setColor(Color.magenta);
        g.fillOval(ballposX, ballposY, 20,20);

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
