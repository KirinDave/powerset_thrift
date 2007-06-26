// Copyright (c) 2006- Facebook
// Distributed under the Thrift Software License
//
// See accompanying file LICENSE or visit the Thrift site at:
// http://developers.facebook.com/thrift/

package com.facebook.thrift;

import com.facebook.thrift.protocol.TField;
import com.facebook.thrift.protocol.TProtocol;
import com.facebook.thrift.protocol.TProtocolUtil;
import com.facebook.thrift.protocol.TStruct;
import com.facebook.thrift.protocol.TType;

/**
 * Application level exception
 *
 * @author Mark Slee <mcslee@facebook.com>
 */
public class TApplicationException extends TException {

  public static final int UNKNOWN = 0;
  public static final int UNKNOWN_METHOD = 1;
  public static final int INVALID_MESSAGE_TYPE = 2;
  public static final int WRONG_METHOD_NAME = 3;
  public static final int BAD_SEQUENCE_ID = 4;
  public static final int MISSING_RESULT = 5;

  protected int type_ = UNKNOWN;

  public TApplicationException() {
    super();
  }

  public TApplicationException(int type) {
    super();
    type_ = type;
  }

  public TApplicationException(int type, String message) {
    super(message);
    type_ = type;
  }

  public TApplicationException(String message) {
    super(message);
  }

  public int getType() {
    return type_;
  }

  public static TApplicationException read(TProtocol iprot) throws TException {
    TField field;
    TStruct struct = iprot.readStructBegin();

    String message = null;
    int type = UNKNOWN;

    while (true) {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id) {
      case 1:
        if (field.type == TType.STRING) {
          message = iprot.readString();
        } else { 
          TProtocolUtil.skip(iprot, field.type);
        }
        break;
      case 2:
        if (field.type == TType.I32) {
          type = iprot.readI32();
        } else { 
          TProtocolUtil.skip(iprot, field.type);
        }
        break;
      default:
        TProtocolUtil.skip(iprot, field.type);
        break;
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    return new TApplicationException(type, message);
  }

  public void write(TProtocol oprot) throws TException {
    TStruct struct = new TStruct("TApplicationException");
    TField field = new TField();
    oprot.writeStructBegin(struct);
    if (getMessage() != null) {
      field.name = "message";
      field.type = TType.STRING;
      field.id = 1;
      oprot.writeFieldBegin(field);
      oprot.writeString(getMessage());
      oprot.writeFieldEnd();
    }
    field.name = "type";
    field.type = TType.I32;
    field.id = 2;
    oprot.writeFieldBegin(field);
    oprot.writeI32(type_);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();

  }
}
