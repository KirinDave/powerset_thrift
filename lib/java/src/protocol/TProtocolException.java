// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

package com.facebook.thrift.protocol;

import com.facebook.thrift.TException;

/**
 * Protocol exceptions.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
public class TProtocolException extends TException {

  public static final int UNKNOWN = 0;
  public static final int INVALID_DATA = 1;
  public static final int NEGATIVE_SIZE = 2;
  public static final int SIZE_LIMIT = 3;

  protected int type_ = UNKNOWN;

  public TProtocolException() {
    super();
  }

  public TProtocolException(int type) {
    super();
    type_ = type;
  }

  public TProtocolException(int type, String message) {
    super(message);
    type_ = type;
  }

  public TProtocolException(String message) {
    super(message);
  }

  public TProtocolException(int type, Throwable cause) {
    super(cause);
    type_ = type;
  }

  public TProtocolException(Throwable cause) {
    super(cause);
  }

  public TProtocolException(String message, Throwable cause) {
    super(message, cause);
  }

  public TProtocolException(int type, String message, Throwable cause) {
    super(message, cause);
    type_ = type;
  }

  public int getType() {
    return type_;
  }

}
