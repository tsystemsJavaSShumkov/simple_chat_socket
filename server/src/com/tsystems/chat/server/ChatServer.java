package com.tsystems.chat.server;

import com.tsystems.network.TCPConnectionFull;
import com.tsystems.network.TCPConnectionListener;

import java.util.ArrayList;
import java.util.List;

public class ChatServer implements TCPConnectionListener
{
  public static void main(String[] args)
  {
  }

  //all connection to server
  private final List<TCPConnectionFull> connections=new ArrayList<>();

  private ChatServerFull()
  {
    System.out.println("Server running...");
    //use ServerSocket here
  }

  //override your methods from Listener here
  //they must be synchronized like in TcpConnection class
}
