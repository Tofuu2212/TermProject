package Client;

/*
Current versio
we are not using client view, we are only using game view and it will
just choose 1 champ to work with 1 player

this window should allow the user to set their name and choose their champion,
then click a button when they're ready to play

 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Message.*;

import javax.swing.*;

public class ClientView extends JPanel {

    Client myClient;
    ClientMessageHandler clientListener;

    ClientView(ClientMessageHandler clientListener, Client myClient) {
        this.clientListener = clientListener;
        this.myClient = myClient;

        this.setLayout(new BorderLayout(10, 10));

        // Create text field
        JTextField textField = new JTextField();
        this.add(textField, BorderLayout.NORTH);

        // Create button
        JButton saveButton = new JButton("Send text to server");
        this.add(saveButton, BorderLayout.SOUTH);

        // Add action listener to button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                System.out.println(text);

                //Message message = new Message(text, Type.DUMMY_ONE);
                //myClient.sendMessage(message);

            }
        });

    }

}
