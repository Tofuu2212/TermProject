package Client;

//NOTE: there will be a GameView probably later? or everything just in this view idk haha.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import Message.*;

import javax.swing.*;

public class ClientView extends JPanel {

    Client myClient;
    clientMessageHandler clientListener;

    ClientView(clientMessageHandler clientListener, Client myClient) {
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

                Message message = new Message(text, Type.DUMMY_ONE);
                myClient.sendMessage(message);

            }
        });

    }

}
