import com.facebook.thrift.TException;
import com.facebook.thrift.protocol.TBinaryProtocol;
import com.facebook.thrift.protocol.TProtocol;
import com.facebook.thrift.server.TServer;
import com.facebook.thrift.server.TSimpleServer;
import com.facebook.thrift.transport.TServerSocket;
import com.facebook.thrift.transport.TServerTransport;

// Generated code
import tutorial.*;
import shared.*;

import java.util.HashMap;

public class JavaServer {

  public static class CalculatorHandler implements Calculator.Iface {

    private HashMap<Integer,SharedStruct> log;

    public CalculatorHandler() {
      log = new HashMap<Integer, SharedStruct>();
    }

    public void ping() {
      System.out.println("ping()");
    }

    public int add(int n1, int n2) {
      System.out.println("add(" + n1 + "," + n2 + ")");
      return n1 + n2;
    }

    public int calculate(int logid, Work work) throws InvalidOperation {
      System.out.println("calculate(" + logid + ", {" + work.op + "," + work.num1 + "," + work.num2 + "})");
      int val = 0;
      switch (work.op) {
      case Operation.ADD:
        val = work.num1 + work.num2;
        break;
      case Operation.SUBTRACT:
        val = work.num1 - work.num2;
        break;
      case Operation.MULTIPLY:
        val = work.num1 * work.num2;
        break;
      case Operation.DIVIDE:
        if (work.num2 == 0) {
          InvalidOperation io = new InvalidOperation();
          io.what = work.op;
          io.why = "Cannot divide by 0";
          throw io;
        }
        val = work.num1 / work.num2;
        break;
      default:
        InvalidOperation io = new InvalidOperation();
        io.what = work.op;
        io.why = "Unknown operation";
        throw io;
      }

      SharedStruct entry = new SharedStruct();
      entry.key = logid;
      entry.value = Integer.toString(val);
      log.put(logid, entry);

      return val;
    }

    public SharedStruct getStruct(int key) {
      System.out.println("getStruct(" + key + ")");
      return log.get(key);
    }

    public void zip() {
      System.out.println("zip()");
    }

  }

  public static void main(String [] args) {
    try {
      CalculatorHandler handler = new CalculatorHandler();
      Calculator.Processor processor = new Calculator.Processor(handler);
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(processor, serverTransport);

      // Use this for a multithreaded server
      // server = new TThreadPoolServer(processor, serverTransport);

      System.out.println("Starting the server...");
      server.serve();

    } catch (Exception x) {
      x.printStackTrace();
    }
    System.out.println("done.");
  }
}
