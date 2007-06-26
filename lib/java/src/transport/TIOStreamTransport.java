// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

package com.facebook.thrift.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is the most commonly used base transport. It takes an InputStream
 * and an OutputStream and uses those to perform all transport operations.
 * This allows for compatibility with all the nice constructs Java already
 * has to provide a variety of types of streams.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
public class TIOStreamTransport extends TTransport {

  /** Underlying inputStream */
  protected InputStream inputStream_ = null;

  /** Underlying outputStream */
  protected OutputStream outputStream_ = null;

  /**
   * Subclasses can invoke the default constructor and then assign the input
   * streams in the open method.
   */
  protected TIOStreamTransport() {}

  /**
   * Input stream constructor.
   *
   * @param is Input stream to read from
   */
  public TIOStreamTransport(InputStream is) {
    inputStream_ = is;
  }

  /**
   * Output stream constructor.
   *
   * @param os Output stream to read from
   */
  public TIOStreamTransport(OutputStream os) {
    outputStream_ = os;
  }

  /**
   * Two-way stream constructor.
   *
   * @param is Input stream to read from
   * @param os Output stream to read from
   */ 
  public TIOStreamTransport(InputStream is, OutputStream os) {
    inputStream_ = is;
    outputStream_ = os;
  }

  /**
   * The streams must already be open at construction time, so this should
   * always return true.
   *
   * @return true
   */
  public boolean isOpen() {
    return true;
  }

  /**
   * The streams must already be open. This method does nothing.
   */
  public void open() throws TTransportException {}

  /**
   * Closes both the input and output streams.
   */
  public void close() {
    if (inputStream_ != null) {
      try {
        inputStream_.close();
      } catch (IOException iox) {
        System.err.println("WARNING: Error closing input stream: " +
                           iox.getMessage());
      }
      inputStream_ = null;
    }
    if (outputStream_ != null) {
      try {
        outputStream_.close();
      } catch (IOException iox) {
        System.err.println("WARNING: Error closing output stream: " +
                           iox.getMessage());
      }
      outputStream_ = null;
    }
  }

  /**
   * Reads from the underlying input stream if not null.
   */
  public int read(byte[] buf, int off, int len) throws TTransportException {
    if (inputStream_ == null) {
      throw new TTransportException(TTransportException.NOT_OPEN, "Cannot read from null inputStream");
    }
    try {
      return inputStream_.read(buf, off, len);
    } catch (IOException iox) {
      throw new TTransportException(TTransportException.UNKNOWN, iox);
    }
  }

  /**
   * Writes to the underlying output stream if not null.
   */
  public void write(byte[] buf, int off, int len) throws TTransportException {
    if (outputStream_ == null) {
      throw new TTransportException(TTransportException.NOT_OPEN, "Cannot write to null outputStream");
    }
    try {
      outputStream_.write(buf, off, len);
    } catch (IOException iox) {
      throw new TTransportException(TTransportException.UNKNOWN, iox);
    }
  }

  /**
   * Flushes the underlying output stream if not null.
   */
  public void flush() throws TTransportException {
    if (outputStream_ == null) {
      throw new TTransportException(TTransportException.NOT_OPEN, "Cannot flush null outputStream");
    }
    try {
      outputStream_.flush();
    } catch (IOException iox) {
      throw new TTransportException(TTransportException.UNKNOWN, iox);
    }
  }
}
