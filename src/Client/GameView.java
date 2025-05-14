package Client;

import Message.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JPanel {

    Client myClient;
    ClientMessageHandler clientListener;

    GameView(ClientMessageHandler clientListener, Client myClient) {
        this.clientListener = clientListener;
        this.myClient = myClient;

        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                String text = "Clicked at: (" + x + ", " + y + ")";

                System.out.println("Clicked at: (" + x + ", " + y + ")");
                clientListener.sendMessage(new Message(text, Type.DUMMY_TWO));
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillOval(200, 150, 100, 100); // example object
    }
}
