package com.tsystems.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

//common class for connection
public class TCPConnection
{
  //client socket
  private final Socket socket;
  //input thread
  private final Thread rxThread;
  //listener for events
  private final TCPConnectionListener eventListener;
  // reader from socket
  private final BufferedReader in;
  // writer for socket
  private final BufferedWriter out;

  //Constructor for client. Because client need to know ip and port
  public TCPConnection(final TCPConnectionListener eventListener, final String ipAddr, final int port)
  {

  }

  //Constructor for server. Server need only socket
  public TCPConnection(final Socket socket, final TCPConnectionListener eventListener)
  {
    this.socket=socket;
    this.eventListener=eventListener;
    //initialize in and out here

    //create new Thread for input
    rxThread = new Thread(new Runnable()
    {
      @Override public void run()
      {

        //do connection and receive here

        //while thread is not interrupted
        while (!rxThread.isInterrupted())
        {
          //do receive here
        }
        //think about exception handling and disconnection

      }
    });
    //starting new thread
    rxThread.start();
  }

  //to send string
  public synchronized void sendString(final String value)
  {
    //sending
  }

  //diconnection
  public synchronized void disconnect()
  {

  }

  //for logging
  @Override public String toString()
  {
    return "TCPConnectionFull{" + socket.getInetAddress() + ":" + socket.getPort() + "}";
  }
}
