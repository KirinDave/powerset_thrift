// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

package com.facebook.thrift.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Socket implementation of the TTransport interface. To be commented soon!
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
public class TSocket extends TIOStreamTransport {

  /**
   * Wrapped Socket object
   */
  private Socket socket_ = null;
  
  /**
   * Remote host
   */
  private String host_  = null;

  /**
   * Remote port
   */
  private int port_ = 0;

  /**
   * Socket timeout
   */
  private int timeout_ = 0;

  /**
   * Constructor that takes an already created socket.
   *
   * @param socket Already created socket object
   * @throws TTransportException if there is an error setting up the streams
   */
  public TSocket(Socket socket) throws TTransportException {
    socket_ = socket;
    try {
      socket_.setSoLinger(false, 0);
      socket_.setTcpNoDelay(true);
    } catch (SocketException sx) {
      sx.printStackTrace();
    }

    if (isOpen()) {
      try {
        inputStream_ = new BufferedInputStream(socket_.getInputStream(), 1024);
        outputStream_ = new BufferedOutputStream(socket_.getOutputStream(), 1024);
      } catch (IOException iox) {
        close();
        throw new TTransportException(TTransportException.NOT_OPEN, iox);
      }
    }
  }

  /**
   * Creates a new unconnected socket that will connect to the given host
   * on the given port.
   *
   * @param host Remote host
   * @param port Remote port
   */
  public TSocket(String host, int port) {
    this(host, port, 0);
  }

  /**
   * Creates a new unconnected socket that will connect to the given host
   * on the given port.
   *
   * @param host    Remote host
   * @param port    Remote port
   * @param timeout Socket timeout
   */
  public TSocket(String host, int port, int timeout) {
    host_ = host;
    port_ = port;
    timeout_ = timeout;
    initSocket();
  }

  /**
   * Initializes the socket object
   */
  private void initSocket() {
    socket_ = new Socket();
    try {
      socket_.setSoLinger(false, 0);
      socket_.setTcpNoDelay(true);
      socket_.setSoTimeout(timeout_);
    } catch (SocketException sx) {
      sx.printStackTrace();
    }
  }

  /**
   * Sets the socket timeout
   *
   * @param timeout Milliseconds timeout
   */
  public void setTimeout(int timeout) {
    timeout_ = timeout;
    try {
      socket_.setSoTimeout(timeout);
    } catch (SocketException sx) {
      sx.printStackTrace();
    }
  }

  /**
   * Returns a reference to the underlying socket.
   */
  public Socket getSocket() {
    if (socket_ == null) {
      initSocket();
    }
    return socket_;
  }

  /**
   * Checks whether the socket is connected.
   */
  public boolean isOpen() {
    if (socket_ == null) {
      return false;
    }
    return socket_.isConnected();
  }

  /**
   * Connects the socket, creating a new socket object if necessary.
   */
  public void open() throws TTransportException {
    if (isOpen()) {
      throw new TTransportException(TTransportException.ALREADY_OPEN, "Socket already connected.");
    }

    if (host_.length() == 0) {
      throw new TTransportException(TTransportException.NOT_OPEN, "Cannot open null host.");
    }
    if (port_ <= 0) {
      throw new TTransportException(TTransportException.NOT_OPEN, "Cannot open without port.");
    }

    if (socket_ == null) {
      initSocket();
    }

    try {
      socket_.connect(new InetSocketAddress(host_, port_));
      inputStream_ = new BufferedInputStream(socket_.getInputStream(), 1024);
      outputStream_ = new BufferedOutputStream(socket_.getOutputStream(), 1024);
    } catch (IOException iox) {
      close();
      throw new TTransportException(TTransportException.NOT_OPEN, iox);
    }
  }

  /**
   * Closes the socket.
   */
  public void close() {
    // Close the underlying streams
    super.close();

    // Close the socket
    if (socket_ != null) {
      try {
        socket_.close();
      } catch (IOException iox) {
        System.err.println("WARNING: exception closing socket: " +
                           iox.getMessage());
      }
      socket_ = null;
    }
  }

}
