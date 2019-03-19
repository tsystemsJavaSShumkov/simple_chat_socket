package com.tsystems.network;

import java.io.*;
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
  public TCPConnection(final TCPConnectionListener eventListener, final String ipAddr, final int port) throws IOException
  {
    this(new Socket(ipAddr, port), eventListener);
  }

  //Constructor for server. Server need only socket
  public TCPConnection(final Socket socket, final TCPConnectionListener eventListener) throws IOException
  {
    this.socket=socket;
    this.eventListener=eventListener;
    //initialize in and out here
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    //create new Thread for input
    rxThread = new Thread(new Runnable()
    {
      @Override public void run()
      {
        try
        {
          eventListener.onConnectionReady(TCPConnection.this);

          //while thread is not interrupted
          while (!rxThread.isInterrupted())
          {
            eventListener.onReceiveString(TCPConnection.this, in.readLine());
          }
        }
        catch (IOException e)
        {
          eventListener.onException(TCPConnection.this, e);
        }
        finally
        {
          eventListener.onDisconnect(TCPConnection.this);
        }

      }
    });
    //starting new thread
    rxThread.start();
  }

  //to send string
  public synchronized void sendString(final String value)
  {
    //sending
    try
    {
      out.write(value + "\r\n");
      out.flush();
    }
    catch (IOException e)
    {
      disconnect();
      eventListener.onException(TCPConnection.this, e);
    }
  }

  //diconnection
  public synchronized void disconnect()
  {
    rxThread.interrupt();
    try
    {
      socket.close();
      in.close();
      out.close();
    }
    catch (IOException e)
    {
      eventListener.onException(TCPConnection.this, e);
    }
  }

  //for logging
  @Override public String toString()
  {
    return "TCPConnectionFull{" + socket.getInetAddress() + ":" + socket.getPort() + "}";
  }
}
