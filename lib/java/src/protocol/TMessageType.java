// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

package com.facebook.thrift.protocol;

/**
 * Message type constants in the Thrift protocol.
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
public final class TMessageType {
  public static final byte CALL  = 1;
  public static final byte REPLY = 2;
  public static final byte EXCEPTION = 3;
}
