package com.tsystems.chat.server;

import com.tsystems.network.TCPConnection;
import com.tsystems.network.TCPConnectionFull;
import com.tsystems.network.TCPConnectionListener;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements TCPConnectionListener
{
  public static void main(String[] args)
  {
    new ChatServer();
  }

  //all connection to server
  private final List<TCPConnection> connections=new ArrayList<>();

  private ChatServer()
  {
    System.out.println("Server running...");
    //use ServerSocket here
    try (ServerSocket serverSocket=new ServerSocket(8189))
    {
      new TCPConnection(serverSocket.accept(), this);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @Override public synchronized void onConnectionReady(final TCPConnection tcpConnection)
  {
    connections.add(tcpConnection);
    sendToAll(tcpConnection + " connected");
  }

  @Override public synchronized void onReceiveString(final TCPConnection tcpConnection, final String value)
  {
    System.out.println(value);
    sendToAll(tcpConnection + " : " +  value);
  }

  private void sendToAll(final String value)
  {
    for (TCPConnection connection : connections)
    {
      connection.sendString(value);
    }
  }

  @Override public  synchronized void onDisconnect(final TCPConnection tcpConnection)
  {
    connections.remove(tcpConnection);
    sendToAll(tcpConnection + " disconnected");
  }

  @Override public synchronized void onException(final TCPConnection tcpConnection, final Exception e)
  {
    System.out.println("TCPConnectionFull exception: " + e);
  }

  //override your methods from Listener here
  //they must be synchronized like in TcpConnection class
}
