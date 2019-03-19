package com.tsystems.chat.client;

import com.tsystems.network.TCPConnection;
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

  private TCPConnection connection;

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

    try
    {
      connection=new TCPConnection(ClientWindow.this, "localhost", 8189);
    }
    catch (IOException e)
    {
      printMessage(e.getMessage());
    }
  }

  //to send message
  @Override public void actionPerformed(final ActionEvent e)
  {
    String msg = fieldInput.getText();
    if ("".equals(msg)) {
      return;
    }
    fieldInput.setText(null);
    connection.sendString(fieldNickname.getText() + ": " + msg);
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

  @Override public void onConnectionReady(final TCPConnection tcpConnection)
  {
    printMessage("Connection ready...");
  }

  @Override public void onReceiveString(final TCPConnection tcpConnection, final String value)
  {
    printMessage(value);
  }

  @Override public void onDisconnect(final TCPConnection tcpConnection)
  {
    printMessage("Connection closed");
  }

  @Override public void onException(final TCPConnection tcpConnection, final Exception e)
  {
    printMessage("Connection exception: " + e);
  }
}
