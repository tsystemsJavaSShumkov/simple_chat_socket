package com.tsystems.chat.client;

import com.tsystems.network.TCPConnectionFull;
import com.tsystems.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener
{
  private static final Integer WIDTH = 600;
  private static final Integer HEIGHT = 400;

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(ClientWindow::new);
  }

  private final JTextArea log = new JTextArea();
  private final JTextField fieldNickname = new JTextField("Jhon Doe");
  private final JTextField fieldInput = new JTextField();

  private ClientWindow()
  {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);

    log.setEditable(false);
    log.setLineWrap(true);
    add(log, BorderLayout.CENTER);

    fieldInput.addActionListener(this);
    add(fieldInput, BorderLayout.SOUTH);
    add(fieldNickname, BorderLayout.NORTH);

    setVisible(true);

   // create connection here
  }

  //to send message
  @Override public void actionPerformed(final ActionEvent e)
  {
    String msg = fieldInput.getText();
    if ("".equals(msg)) {
      return;
    }
    fieldInput.setText(null);
   //send message here
  }

  //implement methods from listener here

  //for printing messages
  private synchronized void printMessage(String msg)
  {
    SwingUtilities.invokeLater(() -> {
      log.append(msg + "\n");
      log.setCaretPosition(log.getDocument().getLength());
    });
  }
}
