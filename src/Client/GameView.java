package Client;

import javax.swing.*;
import java.awt.*;

import Client.DrawObject.*;

import java.util.ArrayList;

public class GameView extends JPanel implements Runnable {

    final int MAP_SIZE = 512;

    Client myClient;
    ClientMessageHandler clientListener;
    ArrayList<DrawObject> drawObjects = new ArrayList<>();

    // Panel size
    public int scale = 2;
    public static final int PANEL_SIZE_X = 1280;
    public static final int PANEL_SIZE_Y = 720;

    //position of cam as if in game
    public int cameraX;
    public int cameraY;

    //probably needs these for setting cam
    public int playerX;
    public int playerY;

    //offset for drawing objects in JPanel
    public int drawOffsetX;
    public int drawOffsetY;

    //amount dragged when click and drag to move cam (unlocked cam)
    public int cameraDragX;
    public int cameraDragY;

    public boolean dragging = false;
    public boolean locked = true;

    GameView(ClientMessageHandler clientListener, Client myClient) {
        this.clientListener = clientListener;
        this.myClient = myClient;

        JFrame frame = new JFrame("Game View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PANEL_SIZE_X, PANEL_SIZE_Y);
        frame.add(this);
        frame.setVisible(true);

        Inputs inputs = new Inputs(this, clientListener);

        this.addMouseListener(inputs);
        this.addMouseMotionListener(inputs);
        this.addKeyListener(inputs);
        this.setFocusable(true);

        cameraX = playerX;
        cameraY = playerY;
        drawOffsetX = calculateXOffset();
        drawOffsetY = calculateYOffset();

    }

    public void setPlayerXY(int x, int y) {
        playerX = x;
        playerY = y;
    }

    @Override
    protected void paintComponent(Graphics g) {

        //MapPartitionTest.Champion c = map.player;
        if (locked) {
            cameraX = playerX;
            cameraY = playerY;
        }
        //bake scale into draw offsets
        //scale is not baked into position (x,y) or size
        drawOffsetX = calculateXOffset();
        drawOffsetY = calculateYOffset();

        //System.out.println("Camera is at: " + camerax + ", " + cameray);

        //I don't know what this does
        super.paintComponent(g);

        //Draw
        g.drawRect(drawOffsetX, drawOffsetY, MAP_SIZE * scale, MAP_SIZE * scale);
        for (DrawObject d : drawObjects) {
            d.draw(g, drawOffsetX, drawOffsetY, scale);
        }

    }

    //calculate drawoffsetx
    public int calculateXOffset() {
        return (PANEL_SIZE_X / 2) - (cameraX * scale);
    }

    //calculate drawoffsety
    public int calculateYOffset() {
        return (PANEL_SIZE_Y / 2) - (cameraY * scale);
    }

    public void setScale(int scale) {
        this.scale = scale;
        repaint();
    }

    public void update() {
        repaint();
    }

    @Override
    public void run() {

        update();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
