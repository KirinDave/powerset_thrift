#!/usr/bin/env ruby
#
# Copyright (c) 2006- Facebook
# Distributed under the Thrift Software License
#
# See accompanying file LICENSE or visit the Thrift site at:
# http://developers.facebook.com/thrift/
#
# Author: Mark Slee <mcslee@facebook.com>
#
require('thrift/protocol/tprotocol')
require('thrift/protocol/tbinaryprotocol')
require('thrift/transport/ttransport')

class TServer

  def initialize(processor, serverTransport, transportFactory=nil, protocolFactory=nil)
    @processor = processor
    @serverTransport = serverTransport
    @transportFactory = transportFactory ? transportFactory : TTransportFactory.new()
    @protocolFactory = protocolFactory ? protocolFactory : TBinaryProtocolFactory.new()
  end
  
  def serve(); nil; end

end

class TSimpleServer < TServer

  def initialize(processor, serverTransport, transportFactory=nil, protocolFactory=nil)
    super(processor, serverTransport, transportFactory, protocolFactory)
  end

  def serve()
    @serverTransport.listen()
    while (true)
      client = @serverTransport.accept()
      trans = @transportFactory.getTransport(client)
      prot = @protocolFactory.getProtocol(trans)
      begin
        while (true)
          @processor.process(prot, prot)
        end
      rescue TTransportException => ttx
        print ttx,"\n"
      end
      trans.close()
    end
  end

end


