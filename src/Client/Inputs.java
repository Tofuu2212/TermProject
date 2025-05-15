package Client;

import Message.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inputs implements MouseListener, MouseMotionListener, KeyListener {

    GameView view;
    ClientMessageHandler clientListener;

    boolean debug = false;

    public Inputs(GameView view, ClientMessageHandler clientListener) {
        this.view = view;
        this.clientListener = clientListener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Mac cannot detect control + click
        //On macOS, Control + Click is interpreted by the OS as a right-click after processing the event.
        //But Java sees it as a left-click with the Control key held down
        //the solution is to use isControlDown() with left click
        if (SwingUtilities.isRightMouseButton(e) || (SwingUtilities.isLeftMouseButton(e) && e.isControlDown())) {

            //debug stuff
            if (debug) System.out.println("Right Mouse pressed");
            int x = e.getX();
            int y = e.getY();
            if (debug) System.out.println("Clicked on window at: (" + x + ", " + y + ")");

            //the real stuff, not sure clamping is necessary ??
            //also, clamps to 255 not 256 ermmm
            int clickedX = (e.getX() - view.drawOffsetX) / view.scale;
            clickedX = clickedX < 0 ? 0 : (clickedX >= view.MAP_SIZE ? view.MAP_SIZE - 1 : clickedX);
            int clickedY = (e.getY() - view.drawOffsetY) / view.scale;
            clickedY = clickedY < 0 ? 0 : (clickedY >= view.MAP_SIZE ? view.MAP_SIZE - 1 : clickedY);
            if (debug) System.out.println("Which in game is: (" + clickedX + ", " + clickedY + ")");

            Message m = new Message(42, false, clickedX, clickedY, Type.RIGHT_CLICK, Type.RIGHT_CLICK);
            if (debug) System.out.println("inputs: sending message " + m.id);
            clientListener.sendMessage(m);

        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (debug) System.out.println("Left Mouse pressed");
            view.dragging = true;
            view.cameraDragX = e.getX() - view.cameraX;
            view.cameraDragY = e.getY() - view.cameraY;
        } else {
            if (debug) System.out.println("you used middle click probably");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (debug) System.out.println("Mouse released");
        view.dragging = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged");
        if (view.dragging && !view.locked) {
            //does not clamp to MAP_SIZE - 1 because Model doesn't know it exists
            view.cameraX = e.getX() - view.cameraDragX;
            view.cameraX = view.cameraX < 0 ? 0 : (view.cameraX > view.MAP_SIZE ? view.MAP_SIZE : view.cameraX);
            view.cameraY = e.getY() - view.cameraDragY;
            view.cameraY = view.cameraY < 0 ? 0 : (view.cameraY > view.MAP_SIZE ? view.MAP_SIZE : view.cameraY);
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (debug) System.out.println("a key was pressed");
        if (code == KeyEvent.VK_W) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mouseLocation, view);
            System.out.println("Mouse in panel: " + mouseLocation);
            System.out.println("which in game is: " + (mouseLocation.getX() - view.drawOffsetX) / view.scale + ", " + (mouseLocation.getY() - view.drawOffsetY) / view.scale);

            //no message sending yet :D
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // Unused event methods
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
